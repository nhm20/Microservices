package com.project.first_job_app.review;


import com.project.first_job_app.company.Company;
import com.project.first_job_app.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private CompanyService companyService;

    public List<Review> findAllReviews(Long companyId) {
        return reviewRepo.findByCompanyId(companyId);
    }

    public boolean createReview(Review review, Long companyId) {
        Company company=companyService.findCompanyById(companyId);
        if(company!=null){
            review.setCompany(company);
            reviewRepo.save(review);
            return true;
        }
        else {
            return false;
        }
    }
    public Review findReviewById(Long companyId,Long reviewId){
        List<Review>reviews= reviewRepo.findByCompanyId(companyId);
        return reviews.stream().filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }
    public boolean updateReview(Long companyId,Long reviewId, Review updatedReview) {
        if(companyService.findCompanyById(companyId)!=null){
            updatedReview.setCompany(companyService.findCompanyById(companyId));
            updatedReview.setId(reviewId);
            if (reviewRepo.existsById(reviewId)) {
                reviewRepo.save(updatedReview);
                return true;
            } else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean deleteReviewById(Long companyId, Long reviewId) {
        if (companyService.findCompanyById(companyId) != null && reviewRepo.existsById(reviewId)) {
                Review review=reviewRepo.findById(reviewId).orElse(null);
                Company company=review.getCompany();
                company.getReviews().remove(review);
                companyService.updateCompany(company,companyId);
                reviewRepo.deleteById(reviewId);
                return true;
        }
        return false;
    }
}


















