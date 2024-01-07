import axios from 'axios';

const doRegister = async(id, password) => {
    try {
        const response = await axios.post('회원가입 URL', {
            id,
            password,
        });
        return response.data; // 서버 응답 반환
    } catch (error) {
        console.error("doRegister 에러:", error);
        throw error; // 에러를 상위 컴포넌트에 전파
    }
    
}

export default doRegister;