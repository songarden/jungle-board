import React, { useState, useEffect } from 'react'
import {
  Button,
  Input,
} from '@chakra-ui/react';
import getAllComment from '../apis/getAllCommentApi';
import postComment from '../apis/postCommentApi';
import CommentList from './CommentList';
import './Comment.css';


const Comment = ({ boardId }) => {
  const [comment, setComment] = useState("");
  const [openComment, setOpenComment] = useState(false); // 댓글 목록 볼건지 여부

  const [commentList, setCommentList] = useState([]); // 현재 게시물의 댓글들
  const [numberOfComment, setNumberOfComment] = useState(0); // 댓글 수

  // 새로운 게시물 열람할 때마다 댓글 목록 업데이트
  useEffect(() => {
    const updateComments = async () => {
      try {
        const response = await getAllComment(boardId);
        setCommentList(response.data || []);
        setNumberOfComment(response.data.length);
      } catch (error) {
        console.error('댓글 불러오기 실패:', error);
      }
    }

    updateComments();
  });

  const onChangeComment = (e) => {
    setComment(e.target.value);
  }

  const handleSubmitComment = async () => {
    const memberId = localStorage.getItem('memberId');
    if (!comment.trim()) {
      alert('댓글을 입력해주세요.');
      return;
    }

    // 댓글 등록(POST) 요청 API 호출
    try {
      await postComment(memberId, boardId, comment);
      setComment(""); // 입력 필드 초기화
      const updatedComments = await getAllComment(boardId); // 댓글 목록 갱신
      setCommentList(updatedComments.data || []);
    } catch (error) {
      console.error('댓글 등록 실패: ', error);
    }
  }

  const viewComment = () => {
    // 댓글 버튼 클릭하면 댓글 목록 보이도록 하는 함수. useEffect 활용.
    setOpenComment(!openComment);
  }


  return (
    <div className="Comment">
      <div className="Comment-input-box">
        <textarea
          id='Comment-input'
          placeholder="댓글을 입력하세요"          
          value={comment}
          onChange={onChangeComment}
        />
        <Button
          id="Comment-post-btn"          
          onClick={handleSubmitComment}
        >
          등록
        </Button>
      </div>
      <div className='Comment-view-box'>
        <Button
          colorScheme='whiteAlpha'
          id="Comment-btn"
          onClick={viewComment}
        >
          {numberOfComment}개의 댓글
        </Button>
        {openComment && <CommentList comments={commentList} />}
      </div>
    </div>
  )
}

export default Comment