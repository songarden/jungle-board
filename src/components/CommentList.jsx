import React, {useState, useEffect} from 'react';
import './Comment';

const CommentList = ({ comments }) => {
    const memberId = localStorage.getItem("memberId");
    useEffect(()=>{
        console.log('comments: ', comments)
        comments.forEach((e)=>{
            if(e.commentUser == memberId){
                console.log(`${e.commentContent}는 내가 쓴거임!`);
            }
            
        })
    },[])
    return (
        <div className="CommentList">
            {comments.map((comment) => (
                <div key={comment.commentId} className="comment-item">
                    <div className="comment-user">
                        {comment.commentUserName}
                    </div>
                    <div className="comment-date">
                        {new Date(comment.commentDate).toLocaleString()}
                    </div>
                    <div className="comment-content">
                        {comment.commentContent}
                    </div>
                </div>
            ))}
        </div>
    );
};

export default CommentList;
