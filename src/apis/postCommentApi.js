import axios from 'axios';
import api from './networkService';

const postComment = async(memberId, boardId, commentContent) => {
    try {
        const response = 
        await api.post(`/cmts/member-and-board?v1=${memberId}&v2=${boardId}`, {        
            commentContent: commentContent,             
        });
        return response.data; // 서버 응답 반환
    } catch (error) {
        console.error('postComment 에러:', error);        
    }
}

export default postComment;