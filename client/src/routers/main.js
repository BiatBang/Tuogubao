import React from 'react';
import '../styles/App.css'

class Main extends React.Component{
    state = {};

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
                <div className="welcome-title"> Good To Be Alive Today!</div>
                <div className="main-block">
                    <div className="side-col side-col-3">
                    </div>
                    <div className="info-block">
                        It's good to send the location of your diary to someone you Know
                    </div>
                </div>
            </div>
        )
    }
}

export default Main;