import api from './networkService';

const editBoard = async( boardId, title, content ) => {    
    try {
        const response = await api.put(`/boards/board-id?v=${boardId}`, { 
            boardName: title, 
            boardContent: content,
        });
        console.log('응답 확인:', response);
        return response; // 서버 응답 반환
    } catch (error) {
        console.log('editBoard 에러:', error);
        // throw error; // 에러를 상위 컴포넌트로 전파
    }
}

export default editBoard;