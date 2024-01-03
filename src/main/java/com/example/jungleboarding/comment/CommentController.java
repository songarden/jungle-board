package com.example.jungleboarding.comment;

import com.example.jungleboarding.annotation.UserAuthorize;
import com.example.jungleboarding.responce.Response;
import com.example.jungleboarding.responce.ResponseDto;
import com.example.jungleboarding.responce.ResponseStatus;
import com.example.jungleboarding.util.DtoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cmts")
@UserAuthorize
public class CommentController {

    @Autowired
    CommentService commentService;

    @ResponseBody
    @GetMapping("/boards/{boardId}")
    public Response<CommentDto> apiGetAllCmtByBoardId(@PathVariable("boardId") Integer boardId){
        DtoList<CommentDto> cmtDtoList = commentService.getAllCmtByBoardId(boardId);
        return cmtDtoList.toResponse();
    }

    @ResponseBody
    @GetMapping("/users/{memberId}")
    public Response<CommentDto> apiGetAllCmtByUserId(@PathVariable("memberId") String memberId){
        DtoList<CommentDto> cmtDtoList = commentService.getAllCmtByUserId(memberId);
        return cmtDtoList.toResponse();
    }

    @ResponseBody
    @PostMapping("/users/{memberId}/board-id")
    public Response<Integer> apiCreateCmt(@PathVariable("memberId") String memberId,
                                          @RequestParam("v") Integer boardId,
                                          @RequestBody CommentDto commentDto){
        ResponseStatus createResponse = commentService.createCmt(memberId,boardId,commentDto);

        return new ResponseDto<Integer>(createResponse, createResponse.getCode()).toResponse();
    }

    @ResponseBody
    @PutMapping("/{commentId}/user-and-board")
    public Response<Integer> apiUpdateCmt(@PathVariable("commentId") Integer commentId,
                                          @RequestParam("v1") String memberId,
                                          @RequestParam("v2") Integer boardId,
                                          @RequestBody CommentDto commentDto){
        ResponseStatus updateResponse = commentService.updateCmt(commentId,memberId,boardId,commentDto);
        return new ResponseDto<Integer>(updateResponse, updateResponse.getCode()).toResponse();
    }

    @ResponseBody
    @DeleteMapping("/{commentId}/user-and-board")
    public Response<Integer> apiDeleteCmt(@PathVariable("commentId") Integer commentId,
                                          @RequestParam("v1") String memberId,
                                          @RequestParam("v2") Integer boardId){
        ResponseStatus deleteResponse = commentService.deleteCmt(commentId,memberId,boardId);

        return new ResponseDto<Integer>(deleteResponse, deleteResponse.getCode()).toResponse();
    }

    /* DB 초기화 API */
    @ResponseBody
    @DeleteMapping("")
    public Response<Integer> apiDeleteAll(){
        ResponseStatus deleteResponse = commentService.deleteAllCmt();

        return new ResponseDto<Integer>(deleteResponse,deleteResponse.getCode()).toResponse();
    }
}
