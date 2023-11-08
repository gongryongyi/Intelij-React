import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./layouts/Layout";
import Main from "./pages/Main";
import About from "./pages/About";
import Menu from "./pages/Menu";
import MenuDetails from "./pages/MenuDetails";
import MenuSearchResult from "./pages/MenuSearchResult";

function App() {
  return (
      <>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Layout/>}>
              <Route index element={<Main/>}/>
              <Route path="about" element={<About/>}/>
              <Route path="menu">
                  <Route index element={<Menu/>}/>  {/* /menu 라는 요청에 대한 기본 component */}
                <Route path=":menuCode" element={<MenuDetails/>}/> {/* /menu/XXX
                라는 오펑에 대한 component 뒤의 값은 menuCode 라는 변수로 해당 컴포넌트에서 꺼내서 사용*/}
                  <Route path="search" element={<MenuSearchResult/>}/>
              </Route>
            </Route>
          </Routes>
        </BrowserRouter>
      </>
  );
}

export default App;
