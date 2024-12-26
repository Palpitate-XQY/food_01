package com.example.shujuku_ceshi_04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.shujuku_ceshi_04.entity.Reviews;
import com.example.shujuku_ceshi_04.repository.ReviewsRepository;
import java.util.Date;
import java.util.List;

@Service
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;

    @Autowired
    public ReviewsService(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    @Transactional
    public Reviews saveReview(Reviews review) {
        return reviewsRepository.saveReview(review);
    }

    public List<Reviews> findAllReviews() {
        return reviewsRepository.findAllReviews();
    }

    // 根据评论 ID 查询评论信息
    public Reviews findReviewById(int reviewId) {
        String sql = "SELECT * FROM Reviews WHERE ReviewID =?";
        return reviewsRepository.jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Reviews review = new Reviews();
            review.setReviewID(rs.getInt("ReviewID"));
            review.setCustomerID(rs.getInt("CustomerID"));
            review.setMenuID(rs.getInt("MenuID"));
            review.setReviewContent(rs.getString("ReviewContent"));
            review.setReviewTime(rs.getTimestamp("ReviewTime"));
            return review;
        }, reviewId);
    }

    // 根据顾客 ID 查询顾客的所有评论
    public List<Reviews> findReviewsByCustomerId(int customerId) {
        String sql = "SELECT * FROM Reviews WHERE CustomerID =?";
        return reviewsRepository.jdbcTemplate.query(sql, (rs, rowNum) -> {
            Reviews review = new Reviews();
            review.setReviewID(rs.getInt("ReviewID"));
            review.setCustomerID(rs.getInt("CustomerID"));
            review.setMenuID(rs.getInt("MenuID"));
            review.setReviewContent(rs.getString("ReviewContent"));
            review.setReviewTime(rs.getTimestamp("ReviewTime"));
            return review;
        }, customerId);
    }

    // 根据菜品 ID 查询菜品的所有评论
    public List<Reviews> findReviewsByMenuItemId(int menuId) {
        String sql = "SELECT * FROM Reviews WHERE MenuID =?";
        return reviewsRepository.jdbcTemplate.query(sql, (rs, rowNum) -> {
            Reviews review = new Reviews();
            review.setReviewID(rs.getInt("ReviewID"));
            review.setCustomerID(rs.getInt("CustomerID"));
            review.setMenuID(rs.getInt("MenuID"));
            review.setReviewContent(rs.getString("ReviewContent"));
            review.setReviewTime(rs.getTimestamp("ReviewTime"));
            return review;
        }, menuId);
    }

    // 其他与评论相关的业务逻辑，如删除评论、统计评论数量等
}