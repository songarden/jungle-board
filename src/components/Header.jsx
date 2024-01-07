import { Tabs, TabList, Tab } from '@chakra-ui/react'
import { useNavigate } from "react-router-dom";

const Header = () => {
    const navigate = useNavigate();

    return (
        <div className="Header">
            <Tabs align='end' variant='soft-rounded' colorScheme='green'>
                <TabList>
                    <Tab onClick={()=>{console.log("clicked 1")}}>
                        글쓰기
                    </Tab>
                    <Tab onClick={()=>{console.log("clicked 2")}}>
                        내가 쓴 글
                    </Tab>
                    <Tab onClick={()=>{navigate("/", {replace:true})}}>
                        로그아웃
                    </Tab>
                </TabList>
            </Tabs>
        </div>
    )
}

export default Header;