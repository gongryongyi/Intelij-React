import {applyMiddleware, legacy_createStore as createStore} from "redux";
import rootReducers from "./modules";
import ReduxThunk from 'redux-thunk';
import ReduxLogger from 'redux-logger'
import {composeWithDevTools} from "redux-devtools-extension";

const store = createStore(
    rootReducers,
    composeWithDevTools(applyMiddleware(ReduxThunk, ReduxLogger))  //ReduxLogger = 액션 객체를 넘길때만 동작
);


export default store;