import {Component} from "react";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import forms from './forms.css';


class CreateForm extends Component {
    state = {
        show: false,
        entries: this.props.entries,
        bAdmin: this.props.bAdmin
    };

    handleClose = () => this.setState({show: false});
    handleShow = () => this.setState({show: true});

    handleSave = async (event) => {
        let payload = this.props.entries.reduce(
            function (obj, currentProp) {
                obj[document.getElementById(currentProp).id] = document.getElementById(currentProp).value;
                return obj;
            }, {});

        if (this.props.requestPath === "allOferte") {
            Object.assign(payload, {
                solicitare: this.props.solicitare,
                username: this.props.username
            });
        }

        if (this.props.requestPath === "allSolicitari") {
            Object.assign(payload, {
                dataLansare: new Date()
            });
        }

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        };
        const response = await fetch(`http://localhost:8080/${this.props.requestPath}`, requestOptions);
        const data = await response.json();

        if (data === "CREATED") {
            this.props.rerenderParentCallback();
        }

        this.handleClose();
    }

    render() {
        return (
            <>
            <Button variant="outline-primary" onClick={this.handleShow}>
                Inserare
            </Button>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Inserare</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form id="createForm">
                        {this.props.entries.map((entry) => (
                            <div key={entry}>
                                <label className="label">{entry.split(/(?=[A-Z])/).join(" ").toLowerCase()}</label>
                                <input id={entry} className="input"/>
                            </div>
                        ))}

                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button size="sm" variant="outline-secondary" onClick={this.handleClose}>
                        Inchide
                    </Button>
                    <Button size="sm" variant="outline-primary" onClick={this.handleSave}>
                        Salveaza
                    </Button>
                </Modal.Footer>
            </Modal>
                </>
        );
    }
}

export default CreateForm;