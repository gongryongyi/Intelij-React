import {Link} from "react-router-dom";

function Main(){

    return(
        <Link to="/movie">
            <div className="content-row">
                <h1>박스오피스 영화 순위 보러 가기</h1>
            </div>
        </Link>
    );
}

export default Main;