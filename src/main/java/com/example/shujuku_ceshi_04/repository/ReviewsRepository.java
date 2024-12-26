package com.example.shujuku_ceshi_04.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.shujuku_ceshi_04.entity.Reviews;
import java.util.List;

@Repository
public class ReviewsRepository {
    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reviews saveReview(Reviews review) {
        String sql = "INSERT INTO Reviews (CustomerID, MenuID, ReviewContent, ReviewTime) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, review.getCustomerID(), review.getMenuID(), review.getReviewContent(), review.getReviewTime());
        return review;
    }

    public List<Reviews> findAllReviews() {
        String sql = "SELECT * FROM Reviews";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Reviews review = new Reviews();
            review.setReviewID(rs.getInt("ReviewID"));
            review.setCustomerID(rs.getInt("CustomerID"));
            review.setMenuID(rs.getInt("MenuID"));
            review.setReviewContent(rs.getString("ReviewContent"));
            review.setReviewTime(rs.getTimestamp("ReviewTime"));
            return review;
        });
    }

    // 其他与评论相关的数据库操作，如根据 ID 查询评论、更新评论信息等
}