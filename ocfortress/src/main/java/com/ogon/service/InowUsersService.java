package com.ogon.service;

import com.ogon.entity.InowUsersEntity;
import com.ogon.repository.InowUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InowUsersService {

    @Autowired
    private InowUsersRepo inowUsersRepo;

    public List<InowUsersEntity> getAllInowUsers(){
        return inowUsersRepo.findAll();
    }

    public void deleteAllInowUsers(){
        inowUsersRepo.deleteAll();
    }

    public void addInowUser(InowUsersEntity inowUsersEntity){
        inowUsersRepo.save(inowUsersEntity);
    }
}
