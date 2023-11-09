import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callLoginAPI} from "../api/UserAPICalls";
import {useNavigate} from "react-router-dom";

function LoginForm(){

    const isLogin = !!localStorage.getItem('isLogin');  //!! boolean으로 타입변환하기위한 느낌표

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const result = useSelector(state => state.userReducer);

    const [loginInfo, setLoginInfo] = useState({
        id : '',
        password : ''
    });

    useEffect(() => {
        if (isLogin){
            navigate('/')
        }else if (result?.message === 'LOGIN_FAIL'){
            alert('아이디와 비밀번호를 확인해주세요!');
            setLoginInfo({
               id: '',
               password: ''
            });
        }
    }, [result]); //[result] 변수 또는 상태가 변경될 때만 useEffect 함수가 실행됩니다.

    const onChangeHandler = e =>{
        setLoginInfo({
            ...loginInfo,
            [e.target.name] : e.target.value  //현재 이벤트가 일어난 대상에 대한 정보
        })
    }

    const onClickHandler = () => {
        dispatch(callLoginAPI(loginInfo));
    }

    return(
        <div>
            <label>ID : </label>
            <input
                type="text"
                name="id"
                onChange = {onChangeHandler}
                value={loginInfo.id}
            />
            <label>PASSWORD : </label>
            <input
                type="password"
                name="password"
                onChange={onChangeHandler}
                value={loginInfo.password}
            />
            <button onClick={onClickHandler}>로그인</button>

        </div>

    );
}

export default LoginForm;