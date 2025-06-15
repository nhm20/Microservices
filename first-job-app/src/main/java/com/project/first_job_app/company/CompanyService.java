package com.project.first_job_app.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepo companyRepo;

    public List<Company> getAllCompanies(){
        return companyRepo.findAll();
    }
    public boolean updateCompany(Company company,Long id){
        if (companyRepo.existsById(id)) {
            company.setId(id);
            companyRepo.save(company);
            return true;
        }
        return false;
    }

    public String createCompany(Company company) {
        companyRepo.save(company);
        return "Company created successfully";
    }

    public Company findCompanyById(Long id) {
        return companyRepo.findById(id).orElse(null);
    }
    public boolean deleteCompanyById(Long id) {
        if (companyRepo.existsById(id)) {
            companyRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
