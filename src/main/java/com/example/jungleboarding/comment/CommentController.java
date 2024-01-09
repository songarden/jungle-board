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
    @GetMapping("/board-id")
    public Response<CommentDto> apiGetAllCmtByBoardId(@RequestParam("v") Integer boardId){
        DtoList<CommentDto> cmtDtoList = new DtoList<>(commentService.getAllCmtByBoardId(boardId));
        return cmtDtoList.toResponse();
    }

    @ResponseBody
    @GetMapping("/member-id")
    public Response<CommentDto> apiGetAllCmtByUserId(@RequestParam("v") String memberId){
        DtoList<CommentDto> cmtDtoList = commentService.getAllCmtByUserId(memberId);
        return cmtDtoList.toResponse();
    }

    @ResponseBody
    @PostMapping("/member-and-board")
    public Response<Integer> apiCreateCmt(@RequestParam("v1") String memberId,
                                          @RequestParam("v2") Integer boardId,
                                          @RequestBody CommentDto commentDto){
        ResponseStatus createResponse = commentService.createCmt(memberId,boardId,commentDto);

        return new ResponseDto<Integer>(createResponse, createResponse.getCode()).toResponse();
    }

    @ResponseBody
    @PutMapping("/cmt-id")
    public Response<Integer> apiUpdateCmt(@RequestParam("v") Integer commentId,
                                          @RequestBody CommentDto commentDto){
        ResponseStatus updateResponse = commentService.updateCmt(commentId,commentDto);
        return new ResponseDto<Integer>(updateResponse, updateResponse.getCode()).toResponse();
    }

    @ResponseBody
    @DeleteMapping("/cmt-id")
    public Response<Integer> apiDeleteCmt(@RequestParam("v") Integer commentId){
        ResponseStatus deleteResponse = commentService.deleteCmt(commentId);

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
