import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./layouts/Layout";
import Main from "./pages/Main";
import About from "./pages/About";
import Menu from "./pages/Menu";

function App() {
  return (
      <>
          <BrowserRouter>
              <Routes>
                  <Route path="/" element={<Layout/>}>
                      <Route index element={<Main/>}/>
                      <Route path="about" element={<About/>}/>  {/* 그냥 about 만 써도 되는 이유는 맨 위에 이미 / 가 있기 때문이다. */}
                      <Route path="menu" element={<Menu/>}/>
                  </Route>
              </Routes>
          </BrowserRouter>
      </>
  );
}

export default App;
