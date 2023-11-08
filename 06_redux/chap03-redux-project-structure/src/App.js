import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./layout/Layout";
import Main from "./pages/Main";
import Menus from "./pages/Menus";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout/>}>
            <Route index element={<Main/>}/>
            <Route path="menu" element={<Menus/>}/>
        </Route>
      </Routes>
    </BrowserRouter>

  );
}

export default App;
