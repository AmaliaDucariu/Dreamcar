import React, {Component} from 'react';
import './App.css';
import SideNavBar from "./components/SideBar";
import Solicitari from "./components/Solicitari";
import Oferte from "./components/Oferte.jsx";
import Form from "./forms/Form.jsx"

class Application extends Component {
    state = {
        bSolicitari: true,
        bOferte: false,
        sId: "Solicitari",
        bAdmin: false,
        successfullLogin: false,
        username: ""
    };

    displayPage = (sId) => {
        let page;
        switch (sId) {
            case "Solicitari":
                page = <Solicitari bAdmin={this.state.bAdmin} username={this.state.username}/>;
                if (!this.state.bSolicitari) {
                    this.setState({
                        bSolicitari: true,
                        bOferte: false,
                        sId: "Solicitari"
                    });
                }
                break;
            case "Oferte":
                page = <Oferte bAdmin={this.state.bAdmin} username={this.state.username}/>;
                if (!this.state.bOferte) {
                    this.setState({
                        bSolicitari: false,
                        bOferte: true,
                        sId: "Oferte"
                    });
                }
                break;
            case "Logout":
                    this.setState({
                        successfullLogin: false
                    });
                break;
            default:
                page = null;
                break;
        }

        return page;

    }

    // Rerender table after CRUD operations -> Request data
    rerenderParentCallback = async (bAdmin, successfullLogin, username) => {
        this.setState({
            bAdmin,
            successfullLogin,
            username
        });
    };

    render () {
        return (
            this.state.successfullLogin ?
                <div>
                    <div>
                        <header>
                        </header>
                        <SideNavBar onClick={this.displayPage}/>
                    </div>
                    {this.displayPage(this.state.sId)}
            </div> : <Form rerenderParentCallback={this.rerenderParentCallback.bind(this)}/>
        )
    }
}

export default Application;
