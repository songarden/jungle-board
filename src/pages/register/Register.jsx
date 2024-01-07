import React, {useState} from 'react';
import { useNavigate } from "react-router-dom"; 
import './Register.css';
import { Input, Button } from '@chakra-ui/react';

const Register = () => {
    const navigate = useNavigate();

    const [id, setId] = useState('');
    const [pw, setPw] = useState('');
    const onChangeId = (e) => {
        setId(e.target.value);
    }
    const onChangePw = (e) => {
        setPw(e.target.value);    
    }
    
    return (
        <div className="Register">
            <h2>회원가입 페이지</h2>            
            <Input placeholder="ID를 입력하세요" value={id} onChange={onChangeId} />
            <Input placeholder="PW를 입력하세요" type='password' value={pw} onChange={onChangePw} />                 

            <div className='register-box'>
                <Button id="register-btn-1" colorScheme='green'
                onClick={()=>{navigate("/")}}> 
                    돌아가기
                </Button>

                {/* 로그인 로직 필요 */}
                <Button id="register-btn-2" colorScheme='green'
                onClick={()=>{navigate("/")}}> 
                    회원가입 하기
                </Button>
            </div>            
        </div>
    )
}

export default Register;