import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { Input, Button, Alert, AlertIcon } from '@chakra-ui/react';
import './Login.css';
import doLogin from '../../apis/loginApi';

const Login = () => {
  const navigate = useNavigate();
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const onChangeId = (e) => {
    setId(e.target.value);
  }
  const onChangePassword = (e) => {
    setPassword(e.target.value);
  }

  const handleLogin = async () => {
    if (!id || !password) {
      setError('ID와 비밀번호를 모두 입력하세요.');
      return;
    }

    try {
      const result = await doLogin(id, password);
      const { accessToken, refreshToken } = result;

      if (accessToken && refreshToken) {
        localStorage.setItem('access', accessToken);
        localStorage.setItem('refresh', refreshToken);
        navigate('/home', { replace: true });
      } else {
        setError('로그인 실패: 잘못된 사용자 정보입니다.');
      }
    } catch (error) {
      setError('로그인 요청 중 오류가 발생했습니다.');
      console.error('로그인 에러:', error);
    }
  }

  return (
    <div className="Login">
      <h2>로그인 페이지</h2>

      {error && (
        <Alert status="error 발생">
          <AlertIcon />
          {error}
        </Alert>
      )}

      <div className='login-box'>

        <Input id='login-id-box' placeholder="ID를 입력하세요" backgroundColor={'white'}
          value={id} onChange={onChangeId} />

        <Input id='login-pw-box' placeholder="PW를 입력하세요" backgroundColor={'white'}
          type='password' value={password} onChange={onChangePassword} />

        <Button id="login-btn-1" colorScheme='purple'
          onClick={() => { navigate("/register") }}>
          회원가입
        </Button>

        <Button id="login-btn-2" colorScheme='purple'
          // onClick={() => {handleLogin}}>
          onClick={() => { console.log(id, password) }}>
          로그인
        </Button>
      </div>
    </div>
  );
};

export default Login;
