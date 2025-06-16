package com.example.jobms.job;


import com.example.jobms.clients.CompanyClient;
import com.example.jobms.clients.ReviewClient;
import com.example.jobms.dto.JobDTO;
import com.example.jobms.external.Company;
import com.example.jobms.external.Review;
import com.example.jobms.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;

    int attempt=0;


//    @CircuitBreaker(name="companyBreaker",fallbackMethod = "companyBreakerFallback")

//    @Retry(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
        @RateLimiter(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt number: " + ++attempt);

        List<Job> jobs = jobRepo.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public List<String> companyBreakerFallback(Exception e) {
        List<String> list = new ArrayList<>();
        list.add("Company service is down, please try again later");
        return list;
    }

    private JobDTO convertToDto(Job job) {

        Company company=companyClient.getCompanyById(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());


        ResponseEntity<List<Review>>reviewResponse= restTemplate.exchange("http://REVIEWMS:8083/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Review>>(){

                });

        List<Review> reviewsRes = reviewResponse.getBody();

        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company,reviewsRes);
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
