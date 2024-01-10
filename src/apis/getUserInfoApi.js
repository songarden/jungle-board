import api from './networkService';

const getUserInfo = async(memberId) => {
    try {
        const response = await api.get(`/users/${memberId}`); 
        console.log('getUserInfo: ',response.data.data);
        return response.data.data; 
    } catch (error) {
        console.error('getUserInfo 에러:', error);
        // throw error; // 에러를 상위 컴포넌트로 전파
    }
}

export default getUserInfo;