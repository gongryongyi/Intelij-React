import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./layouts/Layout";
import MyPage from "./pages/MyPage";
import Login from "./pages/Login";
import Main from "./pages/Main";
import Error from "./pages/Error";


function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout/>}>
            <Route index element={<Main/>}/>
            <Route path="mypage" element={<MyPage/>}/>
            <Route path="login" element={<Login/>}/>
          </Route>
            <Route path="*" element={<Error/>}></Route>  {/* 위에 정의 되어있는 Route 외의 모든 애들은 error 뜨게 한다. */}
        </Routes>
      </BrowserRouter>
  );
}

export default App;
