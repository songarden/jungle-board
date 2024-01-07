import axios from 'axios';

const doLogin = async(id, password) => {
    const result = await axios.post('로그인쪽 url', {id, password});
    return result.data;
}

export default doLogin;