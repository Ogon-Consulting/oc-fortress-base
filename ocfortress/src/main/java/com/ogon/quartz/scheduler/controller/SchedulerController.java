package com.ogon.quartz.scheduler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ogon.quartz.scheduler.entity.SchedulerEntity;
import com.ogon.quartz.scheduler.jobs.ExecuteTestSuite;
import com.ogon.quartz.scheduler.listener.QuartzJobListener;
import com.ogon.quartz.scheduler.payload.SchedulerInputData;
import com.ogon.quartz.scheduler.service.SchedulerService;
import com.ogon.quartz.scheduler.utility.JobExecutionHelper;
import org.quartz.*;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class SchedulerController {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleTestCaseJob(@RequestBody SchedulerInputData inputData) {
        try {
            // Register job listener
            JobExecutionHelper jobListener = new JobExecutionHelper();
            scheduler.getListenerManager().addJobListener(jobListener);
            String nextJobId = String.format("%02d", schedulerService.getNextJobId());
            String jobId = "Scheduler-" + nextJobId;
            JobDetail jobDetail = buildJobDetail(inputData, jobId);
            String isCronScheduler = inputData.getIsCronscheduler();
            String jobCategory = inputData.getCategory();
            Trigger trigger;
            String triggerType;
            String message;

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode response = objectMapper.createObjectNode();

            if ("Yes".equalsIgnoreCase(isCronScheduler)) {
                trigger = buildCronJobTrigger(jobDetail, inputData.getCronschedulerConfig(), jobId);
                triggerType = "Scheduled";
                message = "Recurring Job Scheduled";
            } else {
                ZonedDateTime zonedDateTime = ZonedDateTime.of(inputData.getScheduleDate(), inputData.getZoneTime());
                if(zonedDateTime.isBefore(ZonedDateTime.now())){
                    message = "Error in schedule process. Schedule Date and Time cannot be a past date ";
                    response.put("status", "Failed");
                    response.put("message", message);
                    return new ResponseEntity<>(response.toString(), HttpStatus.OK);
                }
                trigger = buildJobTrigger(jobDetail, zonedDateTime, jobId);
                triggerType = "UnScheduled";
                message = "Job scheduled for testcases at " + inputData.getScheduleDate();
            }
            scheduler.getListenerManager().addJobListener(
                    new QuartzJobListener()
            );
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
            buildSchedulerDetails(trigger, triggerType, jobCategory);
            response.put("status", "Success");
            response.put("message", message);
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (SchedulerException se) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode response = objectMapper.createObjectNode();
            response.put("status", "Failed");
            response.put("message", "Error in scheduling job. Please try again later.");
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllActiveSchedule")
    public ResponseEntity<String> getAllActiveSchedule() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<SchedulerEntity> schedulerEntities = schedulerService.getAllActiveScheduledJobs();
            ArrayNode jobsNode = objectMapper.createArrayNode();
            for (SchedulerEntity schedulerEntity : schedulerEntities) {
                ObjectNode dataNode = objectMapper.createObjectNode();
                dataNode.put("jobName", schedulerEntity.getJobName());
                dataNode.put("description", schedulerEntity.getDescription());
                dataNode.put("category", schedulerEntity.getCategory());
                dataNode.put("jobType", schedulerEntity.getTriggerType());
                dataNode.put("prevTriggerTime", schedulerEntity.getPrevFireTime());
                dataNode.put("nextTriggerTime", schedulerEntity.getNextFireTime());
                dataNode.put("status", schedulerEntity.getTriggerState());
                dataNode.put("scheduledAt", schedulerEntity.getStartTime());
                jobsNode.add(dataNode);
            }
            ObjectNode response = objectMapper.createObjectNode();
            response.set("scheduledjobs", jobsNode);
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception ex) {
            ObjectNode response = objectMapper.createObjectNode();
            response.set("scheduledjobs", response);
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getAllSchedule")
    public ResponseEntity<String> getAllSchedule() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<SchedulerEntity> schedulerEntities = schedulerService.findAllScheduledJobs();
            ArrayNode jobsNode = objectMapper.createArrayNode();
            for (SchedulerEntity schedulerEntity : schedulerEntities) {
                ObjectNode dataNode = objectMapper.createObjectNode();
                dataNode.put("jobName", schedulerEntity.getJobName());
                dataNode.put("description", schedulerEntity.getDescription());
                dataNode.put("category", schedulerEntity.getCategory());
                dataNode.put("jobType", schedulerEntity.getTriggerType());
                dataNode.put("prevTriggerTime", schedulerEntity.getPrevFireTime());
                dataNode.put("nextTriggerTime", schedulerEntity.getNextFireTime());
                dataNode.put("status", schedulerEntity.getTriggerState());
                dataNode.put("scheduledAt", schedulerEntity.getStartTime());
                jobsNode.add(dataNode);
            }
            ObjectNode response = objectMapper.createObjectNode();
            response.set("scheduledjobs", jobsNode);
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception ex) {
            ObjectNode response = objectMapper.createObjectNode();
            response.set("scheduledjobs", response);
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private JobDetail buildJobDetail(SchedulerInputData inputData, String jobId) {

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("carrierId", inputData.getCarrierId());
        jobDataMap.put("stateCd", inputData.getStateCd());
        jobDataMap.put("lob", inputData.getLob());
        jobDataMap.put("category", inputData.getCategory());
        jobDataMap.put("scheduledItems", inputData.getScheduleItemsList());
        jobDataMap.put("executedBy", inputData.getExecutedBy());
        jobDataMap.put("loginUserId", inputData.getLoginUserId());
        jobDataMap.put("loginPassword",inputData.getLoginPassword());

        return JobBuilder.newJob(ExecuteTestSuite.class)
                .withIdentity(jobId)
                .withDescription("Scheduler for " + jobId)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt, String jobId) {

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobId, "Trigger-" + jobId)
                .withDescription("Trigger for " + jobId)
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }

    private Trigger buildCronJobTrigger(JobDetail jobDetail, String cronScheduleData, String jobId) {

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobId, "TestSuite Trigger")
                .withDescription("Trigger for TestSuite")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronScheduleData))
                .build();
    }

    private void buildSchedulerDetails(Trigger trigger, String triggerType, String jobCategory) {
        SchedulerEntity schedulerEntity = new SchedulerEntity();
        schedulerEntity.setJobName(trigger.getJobKey().getName());
        schedulerEntity.setDescription(trigger.getDescription());
        schedulerEntity.setCategory(jobCategory);
        schedulerEntity.setPrevFireTime(trigger.getPreviousFireTime() == null ? "" : trigger.getPreviousFireTime().toString());
        schedulerEntity.setNextFireTime(trigger.getNextFireTime() == null ? "" : trigger.getNextFireTime().toString());
        schedulerEntity.setTriggerState("WAITING");
        schedulerEntity.setTriggerType(triggerType);
        schedulerService.insertJobRecord(schedulerEntity);
    }

    @GetMapping("/unschedule")
    public ResponseEntity<String> deleteSchedule(@RequestParam String jobName) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            TriggerKey triggerKey = new TriggerKey(jobName, "TestSuite Trigger");
            boolean isTriggerExists = scheduler.checkExists(triggerKey);
            if (!isTriggerExists) {
                SchedulerEntity schedulerEntity = schedulerService.findSchedulerJob(jobName);
                if(schedulerEntity != null) {
                    schedulerService.deleteScheduleJob(jobName);
                }
                ObjectNode response = objectMapper.createObjectNode();
                response.put("message", "Job " + jobName + " Deleted");
                return new ResponseEntity<>(response.toString(), HttpStatus.OK);
            }
            boolean isDeleted = deleteTrigger(jobName, triggerKey);
            ObjectNode response = objectMapper.createObjectNode();
            if (isDeleted) {
                response.put("message", "Job " + jobName + " Deleted");
                return new ResponseEntity<>(response.toString(), HttpStatus.OK);
            } else {
                response.put("message", "Job " + jobName + " Delete Failed. Could be already deleted");
                return new ResponseEntity<>(response.toString(), HttpStatus.OK);
            }
        } catch (Exception ex) {
            ObjectNode response = objectMapper.createObjectNode();
            response.put("message", "Job " + jobName + " Delete Failed");
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteTrigger(String jobName, TriggerKey triggerKey) {
        try {
            boolean isDeleted = scheduler.unscheduleJob(triggerKey);
            schedulerService.deleteScheduleJob(jobName);
            if (isDeleted) {
                return true;
            } else {
                return false;
            }
        } catch (SchedulerException e) {
            Logger.error(e);
            return false;
        }
    }

    public void deleteSchedulerJob(String jobName){
        schedulerService.deleteScheduleJob(jobName);
    }

}
