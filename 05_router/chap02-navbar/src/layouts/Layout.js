import Header from "../components/Header";
import Navbar from "../components/Navbar";
import {Outlet} from "react-router-dom";

/* Outlet 컴포넌트는 라우팅 되는 컴포넌트를 대체하기 위한 마커의 역할을 한다.
* header, nav 등 공통적인 역할을 수행하는 영역은 layout에 위치를 지정하고
* outlet으로 라우팅 될 컴포넌트를 대체하기 위해 표기해두면 해당 영역만 변경 된다. */
function Layout(){
    return(
        <>
            <Header/>
            <Navbar/>
            <Outlet/>  {/* 요 안에 들어갈 내용들을 그때그때 바꾸겠다. */}
        </>
    );
}

export default Layout;