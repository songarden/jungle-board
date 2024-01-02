package com.example.jungleboarding.comment;

import com.example.jungleboarding.board.Board;
import com.example.jungleboarding.board.BoardDto;
import com.example.jungleboarding.board.BoardRepository;
import com.example.jungleboarding.responce.ResponseStatus;
import com.example.jungleboarding.user.User;
import com.example.jungleboarding.user.UserRepository;
import com.example.jungleboarding.util.DtoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;


    public DtoList<CommentDto> getAllCmtByBoardId(Integer boardId) {
        DtoList<CommentDto> commentDtoList = new DtoList<>(commentRepository.findAllByCommentBoard(boardId));
        return commentDtoList;
    }

    public DtoList<CommentDto> getAllCmtByUserId(String memberId) {
        DtoList<CommentDto> commentDtoList = new DtoList<>(commentRepository.findAllByCommentUser(memberId));
        return commentDtoList;
    }

    public ResponseStatus createCmt(String memberId, Integer boardId, CommentDto commentDto) {
        Optional<User> checkUser = userRepository.findById(memberId);
        if(checkUser.isEmpty()){
            return ResponseStatus.NOT_FOUND;
        }
        Optional<Board> checkBoard = boardRepository.findById(boardId);
        if(checkBoard.isEmpty()){
            return ResponseStatus.NOT_FOUND;
        }
        commentDto.setCommentBoard(boardId);
        commentDto.setCommentUser(memberId);
        commentDto.setCommentDate(LocalDateTime.now());
        commentRepository.save(commentDto.toEntity());

        return ResponseStatus.CREATE_DONE;
    }

    public ResponseStatus updateCmt(Integer commentId, String memberId, Integer boardId, CommentDto commentDto) {
        Optional<User> checkUser = userRepository.findById(memberId);
        if(checkUser.isEmpty()){
            return ResponseStatus.NOT_FOUND;
        }
        Optional<Board> checkBoard = boardRepository.findById(boardId);
        if(checkBoard.isEmpty()){
            return ResponseStatus.NOT_FOUND;
        }
        Optional<Comment> checkComment = commentRepository.findByCommentBoardAndCommentUser(boardId,memberId);
        if(checkComment.isEmpty()){
            return ResponseStatus.NOT_FOUND;
        }
        CommentDto updateCmtDto = new CommentDto(checkComment.get());
        updateCmtDto.setCommentContent(commentDto.commentContent);
        updateCmtDto.setCommentDate(LocalDateTime.now());
        commentRepository.save(updateCmtDto.toEntity());

        return ResponseStatus.OK;
    }

    public ResponseStatus deleteCmt(Integer commentId, String memberId, Integer boardId) {
        Optional<User> checkUser = userRepository.findById(memberId);
        if(checkUser.isEmpty()){
            return ResponseStatus.NOT_FOUND;
        }
        Optional<Board> checkBoard = boardRepository.findById(boardId);
        if(checkBoard.isEmpty()){
            return ResponseStatus.NOT_FOUND;
        }
        Optional<Comment> checkComment = commentRepository.findById(commentId);
        if(checkComment.isEmpty()){
            return ResponseStatus.NOT_FOUND;
        }
        CommentDto deleteCmtDto = new CommentDto(checkComment.get());
        BoardDto thisBoardDto = new BoardDto(checkBoard.get());
        if(deleteCmtDto.commentBoard != thisBoardDto.boardId){
            return ResponseStatus.NOT_ACCEPTABLE;
        }
        if(!deleteCmtDto.commentUser.equals(memberId) && !thisBoardDto.boardUser.equals(memberId)){
            return ResponseStatus.NOT_ACCEPTABLE;
        }
        commentRepository.delete(deleteCmtDto.toEntity());

        return ResponseStatus.OK;
    }

    public ResponseStatus deleteAllCmt() {
        commentRepository.deleteAll();

        return ResponseStatus.OK;
    }
}
