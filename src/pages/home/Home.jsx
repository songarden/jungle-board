import React, { useState, useEffect } from 'react';
import './Home.css';
import ItemList from "../Items/ItemList";
import DrawerForm from '../../components/DrawerForm';
import ModalForm from '../../components/CreateBoardModal';

import getAllBoard from "../../apis/getAllBoardApi";
import editBoard from '../../apis/editBoardApi';
import deleteBoard from '../../apis/deleteBoardApi';

import { Button } from '@chakra-ui/react'

const Home = ({ isLoggedIn, onLoggedOut }) => {
    // 게시글들: home에서 관리
    const [boardData, setBoardData] = useState([]);
    const [updateCount, setUpdateCount] = useState(0);
    const [boardLength, setBoardLength] = useState(0);

    const [editComplete, setEditComplete] = useState(false);
    const [createComplete, setCreateComplete] = useState(false);
    const [trigger, setTrigger] = useState(false);

    useEffect(() => {
        setTrigger(!trigger);       
        getAllBoardData(); 
        console.log("에딧 or 생성");
    }, [editComplete, createComplete]);

    const onEditComplete = () => {
        setEditComplete(!editComplete);
    }

    const onCreateComplete = () => {
        setCreateComplete(!createComplete);
    }

    const getAllBoardData = async () => {
        const response = await getAllBoard();
        if (response){
            response.sort((a, b) => b.boardId - a.boardId); // 게시물 목록을 boardId 기준 내림차순으로 정렬
            setBoardLength(response.length);
        }
        setBoardData(response);
        console.log('boardData');
    }

    // 마운트시 모든 게시글 데이터 불러오기
    useEffect(() => {
        getAllBoardData();
    }, []);

    // 게시글 수정, 삭제시마다 게시글 데이터 불러오기
    // useEffect(() => {
    //     console.log('updateCount: ', updateCount);
    //     getAllBoardData();
    // }, [updateCount]);

    const onEdit = async (boardId, title, content) => {
        try {
            await editBoard(boardId, title, content);            
        } catch (error) {
            console.error("onEdit 에러: ", error);
        }        

        getAllBoardData();
        setBoardData([...boardData]);
    }
    
    const onDelete = async (boardId) => {
        try {
            await deleteBoard(boardId); 
        } catch (error) {
            console.error("onDelete 에러: ", error);
        }
        getAllBoardData();
    }

    return (
        <div className="Home">
            <h2 className='home-title'> 게시판 </h2>            
            <DrawerForm
                isLoggedIn={isLoggedIn}
                onLoggedOut={onLoggedOut}
                onEdit={onEdit}
                onDelete={onDelete}
                onEditComplete={onEditComplete}
                setBoardData={setBoardData}
                boardData={boardData}
            />
            <div className="number-item">
                {boardLength}개의 게시물이 있습니다.
            </div>
            <ModalForm 
                onCreateComplete={onCreateComplete}
                setBoardData={setBoardData}
                boardData={boardData}
            />
            <div className="item-list">
                <ItemList
                    itemList={boardData}
                    onEdit={onEdit}
                    onDelete={onDelete}
                    onEditComplete={onEditComplete}
                    setBoardData={setBoardData}
                    boardData={boardData}
                />
            </div>
        </div>
    )
}

export default Home;