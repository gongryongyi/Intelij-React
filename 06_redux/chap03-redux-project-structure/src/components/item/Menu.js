import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {callGetMenuAPI, callGetMenuListAPI} from "../api/MenuAPICalls";

function Menu({id}){

    const dispatch = useDispatch();
    const {menu} = useSelector(state => state.menuReducer);

    useEffect(() => {
        /* Menu 1개의 정보 호출하는 API 함수를 action으로 전달 */
        dispatch(callGetMenuAPI(id));  //Menu(id)의 id가 전달
    }, []);
    return(
        <>
            {menu &&
            <>
                <h3>메뉴 이름 : {menu.menuName}</h3>
                <h3>메뉴 가격 : {menu.menuPrice}</h3>
                <h3>메뉴 종류 : {menu.categoryName}</h3>
                <h3>메뉴 상세 : {menu.detail.description}</h3>
                <img src={menu.detail.image} style={{maxWidth : 500}} alt={menu.menuName}/>
            </>
            }
        </>
    );
}

export default Menu;