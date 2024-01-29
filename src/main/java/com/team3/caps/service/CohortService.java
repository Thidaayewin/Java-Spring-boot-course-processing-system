package com.team3.caps.service;

import com.team3.caps.model.Cohort;
import com.team3.caps.repository.CohortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CohortService {

    private CohortRepository cohortRepository;

    @Autowired
    private CohortService(CohortRepository cohortRepository){
        this.cohortRepository = cohortRepository;
    }

    public List<Cohort> findAll(){
        return cohortRepository.findAll();
    }


}
