import React, { useState } from 'react';
import {
  Button,
  Drawer,
  DrawerBody,
  DrawerFooter,
  DrawerHeader,
  DrawerOverlay,
  DrawerContent,
  DrawerCloseButton,
  Avatar, AvatarBadge, AvatarGroup,
  useDisclosure,
} from '@chakra-ui/react';
import '../pages/home/Home.css';
import MyPage from '../pages/MyPage/MyPage';

function DrawerForm({ isLoggedIn, onLoggedOut, onEdit, onDelete, onEditComplete, setBoardData, boardData }) {
  const [size, setSize] = React.useState('xl')
  const { isOpen, onOpen, onClose } = useDisclosure()

  const handleClickMyPage = (newSize) => {
    setSize(newSize)
    onOpen()
  }

  return (
    <>
      <div className='mypage-btn'>      
        <AvatarGroup spacing='1rem'>
          <Avatar bg='teal.500' onClick={() => handleClickMyPage(size)} m={4} />
        </AvatarGroup>
      </div>


      <Drawer onClose={onClose} isOpen={isOpen} size={size}>
        <DrawerOverlay />        
        <DrawerContent background='rgb(85, 83, 83)' className='mypage-content-wrap'>        
          <DrawerCloseButton />
          <DrawerBody>
            <div className='mypage-body-wrap'>
              <MyPage
                isLoggedIn={isLoggedIn}
                onLoggedOut={onLoggedOut}
                onEdit={onEdit}
                onDelete={onDelete}
                onEditComplete={onEditComplete}
                setBoardData={setBoardData}
                boardData={boardData}
              />
            </div>
          </DrawerBody>
        </DrawerContent>
      </Drawer>
    </>
  )
}

export default DrawerForm;