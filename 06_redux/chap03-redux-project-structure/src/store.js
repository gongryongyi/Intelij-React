import {applyMiddleware, legacy_createStore as createStore} from "redux";
import rootReducers from "./modules";
import ReduxThunk from 'redux-thunk';
import ReduxLogger from 'redux-logger'
import {composeWithDevTools} from "redux-devtools-extension";

const store = createStore(
    rootReducers,  //이렇게 State를 다룰것이다
    composeWithDevTools(applyMiddleware(ReduxThunk, ReduxLogger))  //ReduxLogger = 액션 객체를 넘길때만 동작
);


export default store;