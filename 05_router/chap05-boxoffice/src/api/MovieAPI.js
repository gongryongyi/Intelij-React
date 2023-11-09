/* 일별 박스오피스 정보 조회 */

const BASE_URL = 'http://www.kobis.or.kr/kobisopenapi/webservice/rest/';
const API_KEY = '29a4e66e46a7a75d817e2d5b472f0934';
/* 어제 날짜를 포멧에 맞춰서 반환하는 함수 */
const getDateFormat = () => {
const today = new Date();
const year = today.getFullYear();
const month = today.getMonth() + 1 < 10 ? '0' + (today.getMonth() + 1) : today.getMonth() + 1;
const date = today.getDate() - 1 < 10 ? '0' + (today.getDate() - 1) : today.getDate() -1;

return `${year}${month}${date}`;
}

/* 일별 박스오피스 정보 조회(어제 기준 10개의 영화)*/
export async function getMovieList() {

    const url = `${BASE_URL}boxoffice/searchDailyBoxOfficeList.json?key=${API_KEY}&targetDt=${getDateFormat()}`;
    const reponse = await fetch(url);  //await 없으면 안됨
    const data = await reponse.json();

    console.log(data);

    return data.boxOfficeResult.dailyBoxOfficeList;

}

/* 영화 코드를 전달하여 영화 상세 정보 조회 */
export async function getMovieDetail(movieCd){
    const url = `${BASE_URL}movie/searchMovieInfo.json?key=${API_KEY}&movieCd=${movieCd}`;
    const response = await fetch(url);
    const data = await response.json();

    return data.movieInfoResult.movieInfo;

    console.log(data)
}