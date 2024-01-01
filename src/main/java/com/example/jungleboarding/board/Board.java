package com.example.jungleboarding.board;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "board_table")
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Integer boardId;

    @Column(name = "board_name")
    private String boardName;

    @Column(name = "board_content")
    private String boardContent;

    @Column(name = "board_user")
    private String boardUser;

    @CreatedDate
    @Column(name = "board_date")
    private LocalDateTime boardDate;

    @Builder
    public Board(Integer boardId, String boardName, String boardContent, String boardUser, LocalDateTime boardDate) {
        this.boardId = boardId;
        this.boardName = boardName;
        this.boardContent = boardContent;
        this.boardUser = boardUser;
        this.boardDate = boardDate;
    }
}
