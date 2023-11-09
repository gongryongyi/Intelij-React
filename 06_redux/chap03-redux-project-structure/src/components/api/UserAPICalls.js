import {request} from "./Api";
import {login} from "../../modules/UserModules";

export function callLoginAPI(loginInfo){
     return async (dispatch, getState) => {
       const userList = await request('GET', '/user');

       /* id, password 일치 여부 확인
       * 서버에서 이루어져야 하는 로직이지만 여기서는 간단하게 확인 */
         const result = await userList.find(
           user => user.id === loginInfo.id && user.password === loginInfo.password

         );

         console.log('login result : ', result);

         dispatch(login(result));

     }
}
