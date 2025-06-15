package com.example.jobms.dto;

import com.example.jobms.external.Company;
import com.example.jobms.job.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobWithCompanyDTO {
    private Job job;
    private Company company;
}
