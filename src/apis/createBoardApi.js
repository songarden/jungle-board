import axios from 'axios';
import api from './networkService';

const createBoard = async(title, content) => {    
    const memberId = localStorage.getItem("memberId");

    try {
        const response = await api.post(`/boards/${memberId}`, {        
            boardName: title, 
            boardContent: content,
        });
        return response; // 서버 응답 반환
    } catch (error) {
        console.log('createBoard 에러:', error);
        // throw error; // 에러를 상위 컴포넌트로 전파
    }
}

export default createBoard;