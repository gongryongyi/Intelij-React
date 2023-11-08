import {createActions, handleActions} from "redux-actions";

/* 초기 값 */
const initialState = {};

/* 액션 타입 */
const GET_MENULIST = 'menu/GET_MENULIST';

/* 액션 함수 */
export const {menu : {getMenulist}} = createActions({
    [GET_MENULIST] : (result) => ({menuList : result}) // (payload)
});

/* 리듀서 함수 */
const menuReducer = handleActions({
    [GET_MENULIST] : (state, {payload}) => payload
}, initialState);

export default menuReducer;