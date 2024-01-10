import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import {
  Input,
  Button,
  Alert,
  AlertIcon,
  AlertTitle,
  AlertDescription,
} from '@chakra-ui/react';
import './Login.css';
import doLogin from '../../apis/LoginApi';

const Login = ({onLoggedIn}) => {
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
      const { memberId, userId, userRoles, accessToken, refreshToken } = result.data;

      // 로그인 성공
      if (accessToken && refreshToken && userId) {
        localStorage.setItem('access', accessToken);
        localStorage.setItem('refresh', refreshToken);
        localStorage.setItem('memberId', memberId);

        navigate('/home', { replace: true });
        onLoggedIn();
        console.log('로그인 성공');
      } else {
        setError('로그인 실패: 잘못된 사용자 정보입니다.');
      }
    } catch (error) {
      setError('로그인 요청 중 오류가 발생했습니다.');
      console.error('로그인 에러:', error);
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
    <div className="Login">
      <h2>로그인 페이지</h2>

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
          onClick={handleLogin}>
          로그인
        </Button>

        {error && (
          <Alert status='error'>
            <AlertIcon />
            로그인에 실패했습니다. ID 또는 PW를 확인하세요.
          </Alert>
        )}
        
      </div>
    </div>
  );
};

export default Login;
