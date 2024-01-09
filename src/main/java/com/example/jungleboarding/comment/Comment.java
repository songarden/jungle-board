package com.example.jungleboarding.comment;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "comment_table")
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "comment_user")
    private String commentUser;

    @Column(name = "comment_user_name")
    private String commentUserName;

    @Column(name = "comment_board")
    private Integer commentBoard;

    @CreatedDate
    @Column(name = "comment_date")
    private LocalDateTime commentDate;

    @Builder
    public Comment(Integer commentId, String commentContent, String commentUser, String commentUserName, Integer commentBoard, LocalDateTime commentDate) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commentUser = commentUser;
        this.commentUserName = commentUserName;
        this.commentBoard = commentBoard;
        this.commentDate = commentDate;
    }
}
