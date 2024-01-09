package com.example.jungleboarding.comment;

import com.example.jungleboarding.util.DataTransferObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto implements DataTransferObject<Comment> {
    public Integer commentId;
    public String commentContent;
    public String commentUser;
    public String commentUserName;
    public Integer commentBoard;
    public LocalDateTime commentDate;

    public CommentDto(Integer commentId, String commentContent, String commentUser, String commentUserName, Integer commentBoard, LocalDateTime commentDate) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commentUser = commentUser;
        this.commentUserName = commentUserName;
        this.commentBoard = commentBoard;
        this.commentDate = commentDate;
    }

    public CommentDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.commentContent = comment.getCommentContent();
        this.commentUser = comment.getCommentUser();
        this.commentUserName = comment.getCommentUserName();
        this.commentBoard = comment.getCommentBoard();
        this.commentDate = comment.getCommentDate();
    }

    @Override
    public Comment toEntity() {
        return Comment
                .builder()
                .commentId(this.commentId)
                .commentContent(this.commentContent)
                .commentUser(this.commentUser)
                .commentUserName(this.commentUserName)
                .commentBoard(this.commentBoard)
                .commentDate(this.commentDate)
                .build();
    }

    @Override
    public DataTransferObject<Comment> toDto(Comment comment) {
        return new CommentDto(comment.getCommentId(),
                comment.getCommentContent(),
                comment.getCommentUser(),
                comment.getCommentUserName(),
                comment.getCommentBoard(),
                comment.getCommentDate());
    }
}
