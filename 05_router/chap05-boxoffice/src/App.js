
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./layouts/Layout";
import Main from "./pages/Main";
import MovieList from "./pages/MovieList";
import MovieDetail from "./pages/MovieDetail";

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Layout/>}>
               <Route index element={<Main/>}/>  {/*index =  가장 기본으로 쓰겠다. */}
                  <Route path="movie">
                      <Route index element={<MovieList/>}/>
                      <Route path=":movieCd" element={<MovieDetail/>}/>
                  </Route>
                  </Route>
          </Routes>
      </BrowserRouter>

  );
}

export default App;
