import {combineReducers} from "redux";
import menuReducer from "./MenuModules";

const rootReducers = combineReducers({
    menuReducer
});  //{사용할 reducer 애들 넣기}

export default rootReducers;