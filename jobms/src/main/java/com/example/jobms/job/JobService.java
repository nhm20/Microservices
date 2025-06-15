package com.example.jobms.job;


import com.example.jobms.dto.JobWithCompanyDTO;
import com.example.jobms.external.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    JobRepo jobRepo;

    @Autowired
    RestTemplate restTemplate;

    private  List<Job> jobs=new ArrayList<>();

    public List<JobWithCompanyDTO> findAll() {

        List<Job> jobs = jobRepo.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs = new ArrayList<>();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobWithCompanyDTO convertToDto(Job job){

        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);
//        RestTemplate restTemplate=new RestTemplate();
//        Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(), Company.class);
        Company company = restTemplate.getForObject("http://COMPANYMS:8081/companies/" + job.getCompanyId(), Company.class);
        jobWithCompanyDTO.setCompany(company);
        return jobWithCompanyDTO;
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
