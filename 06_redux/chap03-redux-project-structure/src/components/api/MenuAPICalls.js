import {request} from "./Api";
import {getMenulist} from "../../modules/MenuModules";

export function callGetMenuListAPI(){
    /* 반환 되는 비동기 처리 함수가 dispatch의 매개변수로 전달 되면
    * redux-thunk 미들웨어에서 호출하게 된다. */
    return async (dispatch, getState) => {
        /* GET /menu API 호출 */
        const result = await request('GET', '/menu');
        console.log('getMenuList result :', result);

        /* API 호출을 통해 반환 받은 데이터를 Store에 저장하기 위해
        * 다시 dispatch(action객체) 를 호출한다. */
        dispatch(getMenulist(result));
    }
}