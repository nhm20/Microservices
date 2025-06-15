package com.example.jobms.job;

import com.example.jobms.dto.JobWithCompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping("")
    public ResponseEntity<List<JobWithCompanyDTO>>findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<String> createJob(@RequestBody Job job) {
         jobService.createJob(job);
         return new ResponseEntity<>("Job created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.findJobById(id);
        if (job != null) {
            return new ResponseEntity<>(job, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        boolean res = jobService.deleteById(id);
        if (res) {
            return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
        }
    }

     @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job job) {
         boolean res = jobService.updateJob(id, job);
         if (res) {
             return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
}
