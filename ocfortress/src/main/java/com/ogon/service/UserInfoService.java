package com.ogon.service;

import com.ogon.entity.UserInfoEntity;
import com.ogon.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepo userInfoRepo;

    public List<UserInfoEntity> getAllUserInfo(){
        return userInfoRepo.findAll();
    }

    public UserInfoEntity getUserInfo(String userName){
        return userInfoRepo.findByUserName(userName);
    }

    public List<UserInfoEntity> searchUserContainsText(String searchText){
        return userInfoRepo.searchUserContainsText(searchText);
    }

    public List<UserInfoEntity> searchUserStartsWithText(String searchText){
        return userInfoRepo.searchUserStartsWithText(searchText);
    }

    public List<UserInfoEntity> searchUsersByRole(String role){
        return userInfoRepo.findByRole(role);
    }

    public String getUserRole(String userId){
        UserInfoEntity userInfoEntity = userInfoRepo.findByUserName(userId);
        return userInfoEntity.getRole();
    }

    public String getUserPassword(String userName){
        UserInfoEntity userInfoEntity = userInfoRepo.findByUserName(userName);
        return userInfoEntity.getPassword();
    }

    public int updateUserPassword(String userName, String password, String updatedDate){
        return userInfoRepo.updateUserPassword(userName,password,updatedDate);
    }

    public void updateUser(UserInfoEntity userInfoEntity){
        userInfoRepo.save(userInfoEntity);
    }

    public int deleteUser(String userName){
        return userInfoRepo.deleteUser(userName);
    }

    public void addUserInfo(UserInfoEntity userInfoEntity){
        userInfoRepo.save(userInfoEntity);
    }

    public String verifyUserInfo(String userName, String password){
        UserInfoEntity userInfoEntity = userInfoRepo.findByUserName(userName);
        if(userInfoEntity != null){
            if(userInfoEntity.getPassword().equalsIgnoreCase(password)){
                return "Success";
            }else{
                return "Failed";
            }
        }else{
            return "Failed";
        }
    }

}
