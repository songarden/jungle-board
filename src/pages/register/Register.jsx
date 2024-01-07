import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { Input, Button, Alert, AlertIcon } from '@chakra-ui/react';
import './Register.css';
import doRegister from '../../apis/registerApi';

const Register = () => {
    const navigate = useNavigate();    
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const onChangePassword = (e) => {
        setPassword(e.target.value);
    }
    const onChangeId = (e) => {
        setId(e.target.value);
    }

    const handleRegister = async () => {
        if (!id || !password) {
            setError('ID와 비밀번호를 모두 입력하세요.');
            return;
        }

        try {
            const result = await doRegister(id, password);
            console.log('회원가입 성공', result);
            navigate('/')
        } catch {
            setError('회원가입 중 오류 발생');
            console.error('회원가입 실패:', error);
        }
    }

    return (
        <div className="Register">
            <h2>회원가입 페이지</h2>

            <div className='register-box'>
                <Input id='register-id-box' placeholder="ID를 입력하세요" backgroundColor={'white'}
                    value={id} onChange={onChangeId} />

                <Input id='register-pw-box' placeholder="PW를 입력하세요" backgroundColor={'white'}
                    type='password' value={password} onChange={onChangePassword} />

                <Button id="login-btn-1" colorScheme='purple'
                    onClick={() => { navigate("/") }}>
                    돌아가기
                </Button>

                <Button id="register-btn-2" colorScheme='purple'
                    // onClick={() => {handleRegister}}>
                    onClick={() => { console.log(id, password); navigate("/") }}>
                    회원가입
                </Button>
            </div>
        </div>
    )
}

export default Register;