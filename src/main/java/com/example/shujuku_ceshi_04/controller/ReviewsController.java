package com.example.shujuku_ceshi_04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.shujuku_ceshi_04.entity.Reviews;
import com.example.shujuku_ceshi_04.service.ReviewsService;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {
    private final ReviewsService reviewsService;

    @Autowired
    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    // 保存顾客评价信息接口
    @PostMapping("/save")
    public ResponseEntity<Reviews> saveReview(@RequestBody Reviews review) {
        Reviews savedReview = reviewsService.saveReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    // 查询所有顾客评价信息接口
    @GetMapping("/all")
    public ResponseEntity<List<Reviews>> getAllReviews() {
        List<Reviews> reviews = reviewsService.findAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // 根据评价 ID 查询评价信息接口
    @GetMapping("/{reviewId}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable int reviewId) {
        Reviews review = reviewsService.findReviewById(reviewId);
        if (review!= null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 根据顾客 ID 查询顾客的所有评价接口
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Reviews>> getReviewsByCustomerId(@PathVariable int customerId) {
        List<Reviews> reviews = reviewsService.findReviewsByCustomerId(customerId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // 根据菜品 ID 查询菜品的所有评价接口
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<Reviews>> getReviewsByMenuItemId(@PathVariable int menuId) {
        List<Reviews> reviews = reviewsService.findReviewsByMenuItemId(menuId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}