package com.example.jungleboarding.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Object> findAllByCommentBoard(Integer boardId);

    List<Object> findAllByCommentUser(String memberId);

    Optional<Comment> findByCommentBoardAndCommentUser(Integer boardId, String memberId);
}
