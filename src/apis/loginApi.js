import axios from 'axios';

const doLogin = async(id, password) => {
    try {
        const response = await axios.post('api/sign/login', {
            userId: id, 
            userInfo: password,
        });
        return response.data; // 서버 응답 반환
    } catch (error) {
        console.error('doLogin 에러:', error);
        // throw error; // 에러를 상위 컴포넌트로 전파
    }
}

export default doLogin;