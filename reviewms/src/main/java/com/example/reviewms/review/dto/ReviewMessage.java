package com.example.reviewms.review.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewMessage {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private Long companyId;
}
