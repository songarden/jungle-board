import axios from 'axios';
import api from './networkService';

const doLogout = async () => {
    const refreshToken = localStorage.getItem("refresh");

    try {
        // axios는 delete 요청을 보낼 때 data로 body를 감싸줌. 
        const response = await api.delete('/logout', { 
            data: {refreshToken: refreshToken}
        }); 
        
        localStorage.removeItem("access");
        localStorage.removeItem("refresh");
        localStorage.removeItem("memberId");
        
        console.log(`로그아웃 완료: ${localStorage}`);         
        return response.data;
    } catch (error) {
        console.error("로그아웃 실패: ", error);
    }
}

export default doLogout;