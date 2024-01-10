import { useState, useEffect } from 'react';
import { Textarea, Button } from '@chakra-ui/react'
import "./Items.css";

const Item = ({ boardName, boardDate, boardContent, boardId, onEdit, onDelete, onMyPage, onEditComplete, setBoardData, boardData }) => {
    const [isEdit, setIsEdit] = useState(false);
    const [Content, setContent] = useState(boardContent);
    const [localContent, setLocalContent] = useState(""); 

    useEffect(() => {
        setLocalContent(boardContent);
    }, [Content]); 

    const toggleIsEdit = () => {
        setIsEdit(!isEdit);
    }

    const handleCancelEdit = () => {
        setIsEdit(false);
        setLocalContent(boardContent);
    }

    const handleSubmitBtn = async () => {
        try {
            await onEdit(boardId, boardName, localContent);
            setIsEdit(false); 
            setContent(localContent);
        } catch (error) {
            console.error('handleSubmitBtn 에러:', error);
        }

        onEditComplete();
        console.log(boardData);
        // setBoardData([...boardData]);
    }

    const handleDeleteBtn = async () => {
        if (window.confirm(`게시물을 정말 삭제하시겠습니까?`)) {
            try {
                await onDelete(boardId);
            } catch (error) {
                console.error('handleDeleteBtn 에러:', error);
            }
        }
    }

    return (
        <div className="Item">
            <div className="info">
                <span className="title">
                    제목: {boardName}
                </span>
                <br />
                <span className="date">
                    {new Date(boardDate).toLocaleString()}
                </span>
            </div>
            <div className="content">
                {isEdit ?
                    <Textarea
                        className='content-textarea'
                        value={localContent}
                        onChange={(e) => {
                            setLocalContent(e.target.value);
                        }}
                    /> : <>
                        {Content}
                    </>
                }
            </div>
            {onMyPage && (
                isEdit ? (
                    <div className='UpdateContent'>
                        <Button
                            onClick={handleCancelEdit}>
                            수정 취소
                        </Button>
                        <Button
                            colorScheme='green'
                            onClick={handleSubmitBtn}>
                            수정 완료
                        </Button>
                    </div>
                ) : (
                    <div className='UpdateContent'>
                        <Button 
                        colorScheme='green'
                        onClick={toggleIsEdit}>
                            수정하기
                        </Button>
                        <Button 
                        colorScheme='pink'
                        onClick={handleDeleteBtn}>
                            삭제하기
                        </Button>
                    </div>
                ))}
        </div>
    )
}

export default Item;