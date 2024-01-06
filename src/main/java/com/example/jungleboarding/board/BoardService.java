package com.example.jungleboarding.board;

import com.example.jungleboarding.responce.ResponseStatus;
import com.example.jungleboarding.user.User;
import com.example.jungleboarding.user.UserRepository;
import com.example.jungleboarding.util.DtoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    public DtoList<BoardDto> getAllBoards() {
        DtoList<BoardDto> allBoardDtoList = new DtoList<>(boardRepository.findAll());
        return allBoardDtoList;
    }

    public BoardDto getBoardByBoardId(Integer boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        if(board.isEmpty()){
            return null;
        }
        return new BoardDto(board.get());
    }

    public ResponseStatus createBoard(String memberId, BoardDto boardDto) {
        Optional<User> optMember = userRepository.findById(memberId);
        if(optMember.isEmpty()) {
            return ResponseStatus.NOT_FOUND;
        }
        boardDto.setBoardUser(memberId);

        boardRepository.save(boardDto.toEntity());

        return ResponseStatus.CREATE_DONE;
    }

    /* 수정된 게시물 내용을 그대로 대입 */
    public ResponseStatus updateBoard(Integer boardId, BoardDto boardDto) {
        Optional<Board> checkBoard = boardRepository.findById(boardId);
        if(checkBoard.isEmpty()){
            return ResponseStatus.NOT_FOUND;
        }
        BoardDto updateBoardDto = new BoardDto(checkBoard.get());
        updateBoardDto.setBoardName(boardDto.boardName);
        updateBoardDto.setBoardContent(boardDto.boardContent);
        updateBoardDto.setBoardDate(LocalDateTime.now());

        boardRepository.save(updateBoardDto.toEntity());

        return ResponseStatus.OK;
    }

    public ResponseStatus deleteBoard(Integer boardId) {
        Optional<Board> checkBoard = boardRepository.findById(boardId);
        if(checkBoard.isEmpty()){
            return ResponseStatus.NOT_FOUND;
        }
        BoardDto deleteBoardDto = new BoardDto(checkBoard.get());
        boardRepository.delete(deleteBoardDto.toEntity());

        return ResponseStatus.OK;
    }

    /* Database 초기화용 Service */
    public ResponseStatus deleteAllBoard() {
        boardRepository.deleteAll();
        return ResponseStatus.OK;
    }
}
