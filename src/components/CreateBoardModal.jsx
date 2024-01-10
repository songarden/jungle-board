import React, { useState, useRef } from 'react'
import { useNavigate } from 'react-router-dom';
import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    Button,
    useDisclosure,
} from '@chakra-ui/react'
import createBoard from '../apis/createBoardApi';
import './CreateBoardModal.css';


function ModalForm({onCreateComplete, setBoardData, boardData}) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const navigate = useNavigate();
    const titleInput = useRef();
    const contentTextarea = useRef();

    const baseState = {
        title: "",
        content: "",
    }

    const [state, setState] = useState(baseState);

    const handleChangeState = (e) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        })
    }

    const onCreate = async (title, content) => {
        try {
            const response = await createBoard(title, content);
            setBoardData([...boardData]); // 느림. api 응답에 content 포함해서 받는게 베스트
            // navigate("/home");
        } catch (error) {
            console.error("게시글 생성 실패: ", error);
        }
    }

    const handleSubmit = () => {
        if (state.title.length < 2) {
            titleInput.current.focus();
            return;
        }

        if (state.content.length < 5) {
            contentTextarea.current.focus();
            return;
        }

        onCreate(state.title, state.content);
        onClose();

        onCreateComplete();
    }


    return (
        <>
            <Button onClick={onOpen}>글쓰기</Button>

            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>내용을 적어주세요</ModalHeader>
                    <div className="CreateBoard">                        
                        <div className='CreateBoard-title'>
                            <input
                                id='CreateBoard-title-id'
                                name='title'
                                ref={titleInput}
                                value={state.title}
                                onChange={handleChangeState}
                                placeholder='제목'
                            />
                        </div>
                        <div className='CreateBoard-content'>
                            <textarea
                                id='CreateBoard-content-id'
                                name="content"
                                ref={contentTextarea}
                                value={state.content}
                                onChange={handleChangeState}
                                placeholder='5자 이상 입력하세요'
                            />
                        </div>
                    </div>

                    <ModalFooter>
                        <Button colorScheme='blue' mr={3} onClick={onClose}>
                            닫기
                        </Button>
                        <Button variant='ghost' onClick={handleSubmit}> 
                            등록 
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    )
}

export default ModalForm;