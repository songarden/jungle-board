import api from './networkService';

const deleteBoard = async( boardId ) => {       
    try {
        const response = await api.delete(`/boards/board-id?v=${boardId}`);
        return response; // 서버 응답 반환
    } catch (error) {
        console.log('deleteBoard 에러:', error);
        // throw error; // 에러를 상위 컴포넌트로 전파
    }
}

export default deleteBoard;