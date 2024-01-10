import axios from 'axios';

// Axios 인스턴스 세팅
const api = axios.create({
    baseURL: 'http://54.180.149.138:8080/api',
    timeout: 1000, // 요청 타임아웃: 1초
});

// api 인스턴스 요청 인터셉터
api.interceptors.request.use(
    (config) => {
        // 요청 전 실행할 로직 (-> 헤더에 토큰 추가)
        const token = localStorage.getItem('access');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`; 
        }        
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);


/* 
token 만료 응답이 왔을 경우 별도의 axios 인스턴스를 생성하여 토큰 갱신 요청을 처리.
이렇게 하는 이유는 원래의 api 인스턴스의 인터셉터에 의해 토큰 갱신 요청 자체가 가로채지지 않도록 하기 위함임
*/
const refreshApi = axios.create({
    baseURL: 'http://54.180.149.138:8080/api',
    timeout: 1000, // 요청 타임아웃: 1초
});

// refreshApi 인스턴스 요청 인터셉터
refreshApi.interceptors.request.use(
    (config) => {
        // 요청 전 실행할 로직 (-> 헤더에 토큰 추가)
        const accessToken = localStorage.getItem('access');
        const refreshToken = localStorage.getItem('refresh');
        if (accessToken && refreshToken) {
            config.headers['Authorization'] = `Bearer ${accessToken}`;
            config.headers['Refresh-Token'] = `Bearer ${refreshToken}`;
        }
        // console.log('재요청: ', JSON.stringify(config, null, 2));
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);


// // 응답 인터셉터
// api.interceptors.response.use(
//     (response) => {
//         // 응답 받은 후 실행할 로직
//         // console.log('응답: ', JSON.stringify(response, null, 2));
//         return response;
//     },
//     async (error) => {
//         //error.config는 Axios 요청에 대한 원본 설정을 포함하고 있다. 
//         // 이를 originalRequest 변수에 저장함으로써, 나중에 동일한 요청을 재시도할 때 이 정보를 사용한다.
//         const originalRequest = error.config;
//         if ((error.response.status === 403)
//             && !originalRequest._retry) {
//             originalRequest._retry = true; // 무한 재요청 방지를 위한 플래그
//             const refreshToken = localStorage.getItem('refresh');
            
//             try {
//                 console.log('accessToken 재발급 시도');                
//                 // refreshToken으로 새 accessToken 요청
//                 const response = await refreshApi.post(originalRequest);
//                 console.log('accessToken 재발급 성공!');

//                 // 새로 발급받은 accessToken으로 localStorage를 갱신한다. 
//                 const newAccessToken = response.data.accessToken;
//                 localStorage.setItem('access', newAccessToken);
                                
//                 return response;
//             } catch (refreshError) {
//                 // 토큰 갱신 실패 처리
//                 console.log('refresh token을 통한 access token 갱신 실패');
//                 return Promise.reject(refreshError);
//             }
//         }
//         return Promise.reject(error);
//     }
// );


export default api;
