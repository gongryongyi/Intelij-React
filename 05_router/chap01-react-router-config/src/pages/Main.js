import {Link} from "react-router-dom";

function Main(){
    return(
        <div>
            <h1>메인</h1>
            <nav>
                <Link to="/">Home</Link>  {/*Link = a 태그임*/}
                <Link to="/about">소개</Link>
                <Link to="/menu">메뉴목록</Link>
            </nav>
        </div>
    );
}


export default Main;