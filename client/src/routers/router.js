import React from 'react';
import axios from 'axios';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import {rootUrl} from '../config/webConfig'
import '../styles/App.css'
import Main from "./main"
import Contact from "./contact"

class MainRouter extends React.Component{
    state = {};

    // async fetchData() {
    //     const res = await axios.get(rootUrl + "/main/2")
    //     return await res;
    // }

    buttonTest() {
        alert("button correct");
    }

    componentDidMount() {
        // this.fetchData()
        //     .then(res => {
        //         console.log(res);
        //         this.setState({message: res.data});
        //     })
        //     .catch(err => { /*...handle the error...*/});
    }

    render() {
        return (
            <div>
                <Router>
                    <div className="nav-bar">
                        <Link to="/main" style={{marginLeft: "10px"}}>Tuogubao</Link>
                        <Link to="/contact" className="user-link">User</Link>
                    </div>

                    <Switch>
                        <Route exact path="/contact">
                            <Contact />
                        </Route>
                        <Route exact path="/main">
                            <Main />
                        </Route>
                    </Switch>
                </Router>
            </div>
        )
    }
}

export default MainRouter;