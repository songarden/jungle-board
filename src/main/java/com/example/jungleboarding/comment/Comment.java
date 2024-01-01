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
    private Long commentId;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "comment_user")
    private String commentUser;

    @Column(name = "comment_board")
    private Long commentBoard;

    @CreatedDate
    @Column(name = "comment_date")
    private LocalDateTime commentDate;

    public Comment(Long commentId, String commentContent, String commentUser, Long commentBoard, LocalDateTime commentDate) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commentUser = commentUser;
        this.commentBoard = commentBoard;
        this.commentDate = commentDate;
    }
}
