import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./layout/Layout";
import Main from "./pages/Main";
import Menus from "./pages/Menus";
import MenuDetail from "./pages/MenuDetail";
import Login from "./pages/Login";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout/>}>

            <Route index element={<Main/>}/>
            <Route path="menu">
                <Route index element={<Menus/>}/>
                <Route path=":id" element={<MenuDetail/>}/>  {/*: 동적*/}
            </Route>
            <Route path="login" element={<Login/>}/>
        </Route>
      </Routes>
    </BrowserRouter>

  );
}

export default App;
