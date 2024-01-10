import axios from 'axios';
import api from './networkService';

const getAllBoard = async() => {
    try {
        const response = await api.get('/boards'); 
        // console.log('getAllBoard: ',response.data.data);
        return response.data.data; // 서버 응답 반환
    } catch (error) {
        console.error('getAllBoard 에러:', error);
        // throw error; // 에러를 상위 컴포넌트로 전파
    }
}

export default getAllBoard;