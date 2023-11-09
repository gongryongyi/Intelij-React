import {useEffect, useState} from "react";

function Header(){

    const [movieList, setMovieList] = useState();

    useEffect(() => {
        setMovieList();
    }, []);

    return(
        <header className="header">
            Welcome to Ohgiraffers Boxoffice
        </header>
    );
}

export default Header;