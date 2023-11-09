import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getMovieDetail} from "../api/MovieAPI";

function MovieDetail(){

    const {movieCd} = useParams();

    const [movie, setMovie] = useState();

    useEffect(() => {
      getMovieDetail(movieCd).then(data => setMovie(data));
    }, []);

    console.log(movie);

    return(
        <div className="content-col">
            {movie &&
            <>
                <h1>{movie.movieNm}({movie.movieNmEn})</h1>
                <div>러닝 타임 : {movie.showTm}분</div>
                <div>
                    국가 : {movie.nations.map(nation => <span>{nation.nationNm}</span>)}
                </div>
                <div>출연</div>
                <div>
                    {movie.actors.map(actor => {
                        return(
                            <div>
                                <span className='actor'>{actor.peopleNm}</span>
                                <span>{actor.cast && `-${actor.cast}역`}</span>
                            </div>

                        )
                    })}
                </div>
                <Link to="/movie">목록으로</Link>

            </>
            }
        </div>
    );
}

export default MovieDetail;