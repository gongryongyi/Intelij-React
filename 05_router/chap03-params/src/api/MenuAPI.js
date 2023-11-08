import menus from '../data/menu-detail.json';

/* 전체 메뉴 리스트 조회용 API */
export function getMenuList(){

    return menus;
}

/* 메뉴 코드를 이용하여 메뉴 한 개의 상세 내용 조회 */
export function getMenuDetail(menuCode){
    return menus.find(menu => menu.menuCode == menuCode);
}

/* 메뉴명 전달 받아 메뉴 이름을 포함 하고 있는 메뉴 목록 조회 */
export function getSearchMenu(menuName){
    return menus.filter(menu => menu.menuName.match(menuName));  //true 인 애들을 배열로 받기 위해서 filter 사용
}