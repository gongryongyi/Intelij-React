import {createActions, handleActions} from "redux-actions";

/* 초기 값 */
const initialState = {};

/* 액션 타입 */
const GET_MENULIST = 'menu/GET_MENULIST';
const GET_MENU = 'menu/GET_MENU';

/* 액션 함수 */
export const {menu : {getMenulist, getMenu}} = createActions({
    [GET_MENULIST] : (result) => ({menuList : result}), // (payload)
    //{getMenulist} 이 객체 안에 [GET_MENULIST] : (result) => ({menuList : result}) 이게 들어가 있는 거임
    [GET_MENU] : (result) => ({menu : result})
});

/* 리듀서 함수 =>  전달된 action을 store에 어떻게 저장할 것인지를 여기에다가 정의 하는 것이다. */
const menuReducer = handleActions({
    [GET_MENULIST] : (state, {payload}) => payload,  // ({menuList : result}) = payload 즉 payload를 store에 저장하겠다. 반환값이 store에 저장된다
    [GET_MENU] : (state, {payload}) => payload       //({...state, }) -> 원래 있던 상태도 유지하고 싶으면 이렇게 쓰면됨
}, initialState);

export default menuReducer;