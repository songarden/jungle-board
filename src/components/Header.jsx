import { Tabs, TabList, Tab } from '@chakra-ui/react'
import { useNavigate } from "react-router-dom";
import ModalForm from './CreateBoardModal';

const Header = ({}) => {
    const navigate = useNavigate();

    return (
        <div className="Header">
            {/* <Tabs align='end' variant='soft-rounded' colorScheme='purple'>
                <TabList>
                    <Tab onClick={()=>navigate('/home')}> 🏠 </Tab>
                    <Tab onClick={()=>{navigate("/createBoard")}}>
                        글쓰기
                    </Tab>
                </TabList>
            </Tabs> */}
            <ModalForm />
        </div>
    )
}

export default Header;