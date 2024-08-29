package com.ogon.quartz.scheduler.listener;

import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.quartz.scheduler.service.SchedulerService;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.tinylog.Logger;

public class QuartzJobListener implements JobListener {
    public static final String LISTENER_NAME = "QuartzJobListener";

    @Override
    public String getName() {
        return LISTENER_NAME;
    }

    public SchedulerService getSchedulerService() {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        return appCtx.getBean("schedulerService", SchedulerService.class);
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        Logger.info("Job : " + jobName + " is going to start...");
        SchedulerService schedulerService = getSchedulerService();
        schedulerService.updateSchedulerStatus(jobName, "TRIGGERED");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("jobExecutionVetoed");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context,
                               JobExecutionException jobException) {
        try {
            String jobName = context.getJobDetail().getKey().getName();
            Logger.info("Job : " + jobName + " Executed...");
            Trigger trigger = context.getTrigger();

            TriggerKey triggerKey = trigger.getKey();
            Scheduler scheduler = context.getScheduler();

            // Get the trigger state from the scheduler
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);

            // Convert the trigger state to a readable format
            String triggerStateString = convertTriggerStateToString(triggerState);

            SchedulerService schedulerService = getSchedulerService();
            String prevFireTime = context.getTrigger().getPreviousFireTime() == null ? "" : context.getTrigger().getPreviousFireTime().toString();
            String nextFireTime = context.getTrigger().getNextFireTime() == null ? "" : context.getTrigger().getNextFireTime().toString();
            String startTime = context.getTrigger().getStartTime() == null ? "" : context.getTrigger().getStartTime().toString();
            String endTime = context.getTrigger().getEndTime() == null ? "" : context.getTrigger().getEndTime().toString();

            schedulerService.updateJobDetails(prevFireTime, nextFireTime, triggerStateString, jobName, startTime, endTime);
            if (jobException != null && !jobException.getMessage().isEmpty()) {
                throw new RuntimeException(jobException.getMessage());
            }
        } catch (Exception e) {
            Logger.error(e);
        }

    }

    private String convertTriggerStateToString(Trigger.TriggerState triggerState) {
        return switch (triggerState) {
            case NORMAL -> "NORMAL";
            case PAUSED -> "PAUSED";
            case COMPLETE -> "COMPLETE";
            case ERROR -> "ERROR";
            case BLOCKED -> "BLOCKED";
            case NONE -> "NONE";
        };
    }
}
