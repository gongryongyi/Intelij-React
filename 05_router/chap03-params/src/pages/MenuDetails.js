import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getMenuDetail} from "../api/MenuAPI";

function MenuDetails(){

    /* react router dom 의 useParams 라는 hook을 이용해서 path variable을 읽어올 수 있다.
    * 이 때 구조분해 할당 하는 이름은 route에 설정한 ":menuCode" 로 설정 되어야 한다. */
    const {menuCode} = useParams();

    /* menuCode를 이용해서 menu 하나에 대한 정보를 요청하는 API를 호출한다. */
    const [menu, setMenu] = useState();

    useEffect(() => {
        setMenu(getMenuDetail(menuCode));
    }, []);



    return(
        <>
        {menu &&
            <>
                <h2>메뉴 상세 페이지</h2>
                <h2>메뉴 이름 : {menu.menuName}</h2>
                <h3>메뉴 가격 : {menu.menuPrice}</h3>
                <h3>메뉴 종류 : {menu.categoryName}</h3>
                <h3>메뉴 설명 : {menu.detail.description}</h3>
                <img src = {menu.detail.image} style={{maxWidth: 500}} alt={menu.menuName}/>
            </>
        }
         </>
    );
}

export default MenuDetails;