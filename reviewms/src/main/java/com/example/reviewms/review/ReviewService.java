package com.example.reviewms.review;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;


    public List<Review> findAllReviews(Long companyId) {
        return reviewRepo.findByCompanyId(companyId);
    }

    public boolean createReview(Review review, Long companyId) {
        if(companyId!=null  && review!=null){
            review.setCompanyId(companyId);
            reviewRepo.save(review);
            return true;
        }
        else {
            return false;
        }
    }
    public Review findReviewById(Long reviewId){
       return reviewRepo.findById(reviewId).orElse(null);
    }
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Review review=reviewRepo.findById(reviewId).orElse(null);
        if(review!=null){
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            reviewRepo.save(review);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteReviewById(Long reviewId) {
        Review review= reviewRepo.findById(reviewId).orElse(null);
        if(review!=null){
            reviewRepo.delete(review);
            return true;
        }
        else {
            return false;
        }
    }
}


















