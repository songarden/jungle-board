package com.example.jungleboarding.board;

import com.example.jungleboarding.responce.Response;
import com.example.jungleboarding.responce.ResponseDto;
import com.example.jungleboarding.responce.ResponseStatus;
import com.example.jungleboarding.util.DtoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/boards")
@RestController
public class BoardController {
    @Autowired
    BoardService boardService;


    @ResponseBody
    @GetMapping("")
    public Response<BoardDto> apiGetAllBoards(){
        DtoList<BoardDto> allBoards = boardService.getAllBoards();

        return allBoards.toResponse();
    }

    @ResponseBody
    @GetMapping("/board-id")
    public Response<BoardDto> apiGetBoardByBoardId(@RequestParam("v") Integer boardId){
        BoardDto boardDto = boardService.getBoardByBoardId(boardId);
        ResponseStatus findResponse = ResponseStatus.FOUND;
        if(boardDto == null){
            findResponse = ResponseStatus.NOT_FOUND;
        }
        return new ResponseDto<BoardDto>(findResponse,boardDto).toResponse();
    }

    @ResponseBody
    @PostMapping("/{memberId}")
    public  Response<Integer> apiCreateBoard(@PathVariable("memberId") String memberId
            ,@RequestBody BoardDto boardDto){
        ResponseStatus createResponse = boardService.createBoard(memberId, boardDto);
        return new ResponseDto<Integer>(createResponse, createResponse.getCode()).toResponse();
    }

    @ResponseBody
    @PutMapping("/{memberId}/board-id")
    public Response<Integer> apiUpdateBoard(@PathVariable("memberId") String memberId,
            @RequestParam("v") Integer boardId, @RequestBody BoardDto boardDto){
        ResponseStatus updateResponse = boardService.updateBoard(memberId,boardId,boardDto);

        return new ResponseDto<Integer>(updateResponse, updateResponse.getCode()).toResponse();
    }

    @ResponseBody
    @DeleteMapping("/{memberId}/board-id")
    public Response<Integer> apiDeleteBoard(@PathVariable("memberId") String memberId,
                                            @RequestParam("v") Integer boardId){
        ResponseStatus deleteResponse = boardService.deleteBoard(memberId,boardId);

        return new ResponseDto<Integer>(deleteResponse, deleteResponse.getCode()).toResponse();
    }

    /* Database 초기화용 API */
    @ResponseBody
    @DeleteMapping("")
    public Response<Integer> apiDeleteAll(){
        ResponseStatus deleteResponse = boardService.deleteAllBoard();
        return new ResponseDto<Integer>(deleteResponse,deleteResponse.getCode()).toResponse();
    }
}
