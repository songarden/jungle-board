import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { Input, Button, Alert, AlertIcon } from '@chakra-ui/react';
import './Register.css';
import doRegister from '../../apis/registerApi';

const Register = () => {
    const navigate = useNavigate();
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');

    const [error, setError] = useState('');

    const onChangePassword = (e) => {
        setPassword(e.target.value);
    }
    const onChangeId = (e) => {
        setId(e.target.value);
    }
    const onChangeName = (e) => {
        setName(e.target.value);
    }
    const onChangeEmail = (e) => {
        setEmail(e.target.value);
    }

    const handleRegister = async () => {
        if (!id || !password) {
            setError('ID와 비밀번호를 모두 입력하세요.');
            console.log('id, pw 누락');
            return;
        }

        try {
            const result = await doRegister(id, password, name, email);
            console.log('회원가입 성공', result);
            navigate('/')
        } catch {
            setError('회원가입 중 오류 발생');
            console.error('회원가입 실패:', error);
        }
    }

    useEffect(() => {
        let timer;
        if (error) {
            timer = setTimeout(() => {
                setError(''); // 2초 후에 다시 에러 상태를 돌린다
            }, 2000);
        }
        return () => clearTimeout(timer); // 컴포넌트가 언마운트될 때 타이머도 정리
    }, [error]);

    return (
        <div className="Register">
            <h2>회원가입</h2>

            <div className='register-box'>
                <Input id='register-input-id' placeholder="ID를 입력하세요" backgroundColor={'white'}
                    value={id} onChange={onChangeId} />

                <Input id='register-input-pw' placeholder="PW를 입력하세요" backgroundColor={'white'}
                    type='password' value={password} onChange={onChangePassword} />

                <Input id='register-input-name' placeholder="이름을 입력하세요" backgroundColor={'white'}
                    value={name} onChange={onChangeName} />

                <Input id='register-input-email' placeholder="이메일을 입력하세요" backgroundColor={'white'}
                    value={email} onChange={onChangeEmail} />

                <Button id="login-btn-1" colorScheme='purple'
                    onClick={() => { navigate("/") }}>
                    돌아가기
                </Button>

                <Button id="register-btn-2" colorScheme='purple'
                    onClick={handleRegister}>                    
                    회원가입
                </Button>

                {error && (
                    <Alert status='error'>
                        <AlertIcon />
                        회원가입에 실패했습니다.
                    </Alert>
                )}
                
            </div>
        </div>
    )
}

export default Register;