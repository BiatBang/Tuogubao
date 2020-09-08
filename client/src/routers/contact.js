import React from 'react';
import '../styles/App.css'
import axios from "axios";
import {rootUrl} from "../config/webConfig";

class Contact extends React.Component {

    state = {
        contacts: [],
    }

    componentDidMount() {
        axios.get(rootUrl + "/contact/retriveAll?userId=5f39dc0b53a53793bec683b5")
            .then(res => {
                // this.contacts.push(res.data);
                let contacts = [];
                res.data.forEach(cont => {
                    contacts.push(cont);
                })
                this.setState({contacts: contacts});
            })
    }

    async getContacts() {
        const res = await axios.get(rootUrl + "/contact/retriveAll?userId=5f39dc0b53a53793bec683b5");
        let contacts = [];
        res.data.forEach(cont => {
            contacts.push(cont);
        })
        return contacts;
    }

    render() {
        // const contItems = [];
        // this.getContacts().then(contacts => {
        //     contacts.forEach(cont => {
        //         contItems.push(<li>{cont.name}</li>)
        //     })
        // });

        const contItems = [];
        this.state.contacts.forEach(cont => {
            contItems.push(<li>{cont.name}</li>);
            contItems.push(<li>{cont.words}</li>);
            contItems.push(<div>-----------------------</div>);
        });

        return (
            <div>
                <div className="side-col side-col-3">
                    {contItems}
                </div>
                <div className="mid-col mid-col-7">

                </div>
            </div>
        )
    }
}

export default Contact;