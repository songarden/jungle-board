import axios from 'axios';
import api from './networkService';

const doRegister = async(id, password, name, email) => {
    try {
        const response = await api.post('/sign/join', {
            userId: id,
            userInfo: password,
            userName: name,
            userEmail: email,
        });
        return response.data; // 서버 응답 반환
    } catch (error) {
        console.error("doRegister 에러:", error);
        // throw error; // 에러를 상위 컴포넌트에 전파
    }    
}

export default doRegister;