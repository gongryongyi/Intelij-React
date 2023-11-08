import {useEffect, useState} from "react";
import {getMenuList} from "../api/MenuAPI";  // 상위 디렉토리 ..
import boxStyle from './Menu.module.css';
import * as PropTypes from "prop-types";
import MenuItem from "../components/MenuItem";
import {useNavigate} from "react-router-dom";  // 현재 디렉토리 .

/* 일반 CSS 파일을 이용하면 작업물을 합쳤을 때 class name conflict로 인해 디자인이 변경
* module.css 파일은 class 명에 랜덤한 문자를 붙여서 이름을 지어주어 class name confilct를 방지한다. */





function Menu(){

    const [menuList, setMenuList] = useState();
    const [searchVaule, setSearchValue] = useState('');  //오류 떠서 초기값 넣어준거다

    /* react router dom의 useNavigate hook을 호출하면 하나의 함수가 반환 된다.
    * 해당 함수에 router로 요청하는 주소를 전달하면서 호출하면 url이 변경 된다. */
    const navigate = useNavigate();

    useEffect(() => {
        /* MenuAPI.js 파일을 만들어서 API 호출 함수를 모아둔다. */
       setMenuList(getMenuList());
    }, []);

/* 렌더링 할때 useEffect를 거치지 않고 먼저 retrun 문을 거치는데 그때 menuList가 없어서 오류가 난다. 그러므로 menuList && 를 사용해야한다. */

    const onClickHandler = () => navigate(`/menu/search?menuName=${searchVaule}`); //navigate 함수 호출
    return(
        <div>
            <h1>메뉴목록</h1>

            <div>
                <input
                    type="search"
                    name="menuName"
                    value={searchVaule}
                    onChange={e => setSearchValue(e.target.value)}
                />
                <button
                    onClick={onClickHandler}
                >
                    검색
                </button>
            </div>

            <div className={boxStyle.MenuBox}>
                {menuList && menuList.map(menu => <MenuItem key={menu.menuCode} menu={menu}/>)}
            </div>
        </div>
    );
}

export default Menu;