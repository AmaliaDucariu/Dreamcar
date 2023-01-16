import React, {Component} from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
    faBars,
    faTimes,
    faCodePullRequest,
    faMoneyCheckDollar,
    faUserLock
} from "@fortawesome/free-solid-svg-icons";

import "./styles.css";

class SideBar extends Component {
    state = {
        isOpen: false
    };

    handleTrigger = () => this.setState({isOpen: !this.state.isOpen});

    render () {
        return (
            <div className="SideBar">
                <div className="page">

                    <div className={`sidebar ${this.state.isOpen ? "sidebar--open" : ""}`}>
                        <div className="trigger" onClick={this.handleTrigger}>
                            <FontAwesomeIcon icon={this.state.isOpen ? faTimes : faBars} />
                        </div>

                        <div className="sidebar-position" id="Solicitari" onClick={(event) => this.props.onClick(event.currentTarget.id)}>
                            <FontAwesomeIcon icon={faCodePullRequest} />
                            <span>Solicitari</span>
                        </div>

                        <div className="sidebar-position" id="Oferte" onClick={(event) => this.props.onClick(event.currentTarget.id)}>
                            <FontAwesomeIcon icon={faMoneyCheckDollar} />
                            <span>Oferte</span>
                        </div>

                        <div className="sidebar-position" id="Logout" onClick={(event) => this.props.onClick(event.currentTarget.id)}>
                            <FontAwesomeIcon icon={faUserLock} />
                            <span>Logout</span>
                        </div>

                    </div>
                </div>
            </div>
        )
    }
}

export default SideBar;


