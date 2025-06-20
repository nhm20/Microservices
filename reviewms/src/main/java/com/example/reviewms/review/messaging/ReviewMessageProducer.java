package com.example.reviewms.review.messaging;

import com.example.reviewms.review.Review;
import com.example.reviewms.review.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class ReviewMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendReviewMessage(Review review) {
        ReviewMessage reviewMessage=new ReviewMessage();
        reviewMessage.setId(reviewMessage.getId());
        reviewMessage.setTitle(reviewMessage.getTitle());
        reviewMessage.setDescription(reviewMessage.getDescription());
        reviewMessage.setRating(reviewMessage.getRating());
        reviewMessage.setCompanyId(reviewMessage.getCompanyId());
        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);

    }

}
