package com.example.jobms.mapper;

import com.example.jobms.dto.JobDTO;
import com.example.jobms.external.Company;
import com.example.jobms.external.Review;
import com.example.jobms.job.Job;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(Job job, Company company, List<Review> reviews) {
        JobDTO jobDTO =new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);
        jobDTO.setReview(reviews);
        return jobDTO;
    }
}