package com.example.jungleboarding.board;

import com.example.jungleboarding.util.DataTransferObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
public class BoardDto implements DataTransferObject<Board>  {

    public Integer boardId;
    public String boardName;
    public String boardContent;
    public String boardUser;
    public LocalDateTime boardDate;

    public BoardDto(Integer boardId,
                    String boardName,
                    String boardContent,
                    String boardUser,
                    LocalDateTime boardDate) {
        this.boardId = boardId;
        this.boardName = boardName;
        this.boardContent = boardContent;
        this.boardUser = boardUser;
        this.boardDate = boardDate;
    }

    public BoardDto(Board board){
        this.boardId = board.getBoardId();
        this.boardName = board.getBoardName();
        this.boardContent = board.getBoardContent();
        this.boardUser = board.getBoardUser();
        this.boardDate = board.getBoardDate();
    }

    @Override
    public Board toEntity() throws NoSuchAlgorithmException {
        return Board.builder()
                .boardId(this.boardId)
                .boardName(this.boardName)
                .boardContent(this.boardContent)
                .boardUser(this.boardUser)
                .boardDate(this.boardDate)
                .build();
    }

    @Override
    public DataTransferObject<Board> toDto(Board board) {
        return new BoardDto(board.getBoardId(),
                board.getBoardName(),
                board.getBoardContent(),
                board.getBoardUser(),
                board.getBoardDate());
    }
}
