package com.project.first_job_app.job;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    JobRepo jobRepo;

    private  List<Job> jobs=new ArrayList<>();

    public List<Job> findAll() {
        return jobRepo.findAll();
    }

    public String createJob(Job job) {
        jobRepo.save(job);
        return "Job saved successfully";
    }

    public Job findJobById(Long id) {
       return jobRepo.findById(id).orElse(null);
    }

    public boolean deleteById(Long id) {
        try{
            jobRepo.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional=jobRepo.findById(id);
        if (jobOptional.isPresent()){
            Job job=jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            return true;
        }
        return false;
    }

}
