import {BrowserRouter, Route, Routes} from "react-router-dom";//react-router-dom 라이브러리에서 {BrowserRouter} 꺼낸다.
import Main from "./pages/Main";
import About from "./pages/About";
import Menu from "./pages/Menu";

function App() {
  return (
    <>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Main/>}/>
                <Route path="/about" element={<About/>}/>
                <Route path="/menu" element={<Menu/>}/>
            </Routes>
        </BrowserRouter>
    </>
  );
}

export default App;  //다른 파일에서 import 하기 위해서는 이게 필요함
