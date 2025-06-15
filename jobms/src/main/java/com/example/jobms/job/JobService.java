package com.example.jobms.job;


import com.example.jobms.dto.JobDTO;
import com.example.jobms.external.Company;
import com.example.jobms.external.Review;
import com.example.jobms.mapper.JobMapper;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
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

    private List<Job> jobs = new ArrayList<>();

    public List<JobDTO> findAll() {

        List<Job> jobs = jobRepo.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobDTO convertToDto(Job job) {

//        RestTemplate restTemplate=new RestTemplate();
//        Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(), Company.class);
        Company company = restTemplate.getForObject("http://COMPANYMS:8081/companies/" + job.getCompanyId(), Company.class);
        ResponseEntity<List<Review>>reviewResponse= restTemplate.exchange("http://REVIEWMS:8083/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Review>>(){

                });

        List<Review> reviews = reviewResponse.getBody();

        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company,reviews);
//        jobDTO.setCompany(company);
        return jobDTO;
    }

    public String createJob(Job job) {
        jobRepo.save(job);
        return "Job saved successfully";
    }

    public JobDTO findJobById(Long id) {
        Job job = jobRepo.findById(id).orElse(null);
        return convertToDto(job);
    }

    public boolean deleteById(Long id) {
        try {
            jobRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepo.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
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
