import {Navigate} from "react-router-dom";

function MyPage(){

    /* 로그인 되지 않은 상태에서 MyPage를 볼 수 없으므로 상태 체크 후
    * 로그인 되어 있지 않다면 다른 페이지로 라우팅 하도록 한다. */
    const isLogin = false;

    if (!isLogin){  //로그인 되어있지 않다면
        return <Navigate to="/login"/>
    }
    return(
        <div>
            <h1>마이페이지</h1>
        </div>
    );
}

export default MyPage;