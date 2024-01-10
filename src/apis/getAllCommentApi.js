import axios from 'axios';
import api from './networkService';

const getAllComment = async(boardId) => {
    try {
        const response = await api.get(`/cmts/board-id?v=${boardId}`);        
        return response.data; // 서버 응답 반환
    } catch (error) {
        console.error('getAllComment 에러:', error);
        // throw error; // 에러를 상위 컴포넌트로 전파
    }
}

export default getAllComment;