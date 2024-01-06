package com.example.jungleboarding.comment;

import com.example.jungleboarding.util.DtoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findAllByCommentBoard(Integer commentBoard);

    List<Comment> findAllByCommentUser(String commentUser);

    Optional<Comment> findByCommentBoardAndCommentUser(Integer boardId, String memberId);
}
