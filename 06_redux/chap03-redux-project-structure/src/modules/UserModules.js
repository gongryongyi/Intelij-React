/* 초기값 */
import {createActions, handleActions} from "redux-actions";

const intialState = {};

/* 액션 타입 */
const LOGIN = 'user/LOGIN';

export const {user : {login}} = createActions({
    [LOGIN]: (res) => ({res})
});

/* 리듀서 함수 */
const userReducer = handleActions({
    [LOGIN] : (state, {payload : {res}}) => {

        /* res 값이 있을 경우 id, password 일치로 user 객체가 반환 된 상황이므로
        * localStorage에 로그인 상태를 저장한다. */
        if(res) {
             localStorage.setItem('isLogin', true);
        }else {
            res = {message : 'LOGIN_FAIL'}
        }

        return res;
    }
}, intialState);

export default userReducer;

