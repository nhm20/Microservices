package com.example.companyms.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(Company company, Long id) {
        boolean updated = companyService.updateCompany(company, id);
        if (updated) {
            return ResponseEntity.ok("Company updated successfully");
        } else {
            return ResponseEntity.status(404).body("Company not found");
        }
    }

    @PostMapping
    public ResponseEntity<String> createCompany(Company company) {
        String response = companyService.createCompany(company);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Company> findCompanyById(@PathVariable Long id) {
        Company company = companyService.findCompanyById(id);
        if (company != null) {
            return ResponseEntity.ok(company);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long id) {
        boolean deleted = companyService.deleteCompanyById(id);
        if (deleted) {
            return ResponseEntity.ok("Company deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Company not found");
        }
    }

}
