import {combineReducers} from "redux";
import menuReducer from "./MenuModules";
import userReducer from "./UserModules";

const rootReducers = combineReducers({
    menuReducer, userReducer
});  //{사용할 reducer 애들 넣기}

export default rootReducers;