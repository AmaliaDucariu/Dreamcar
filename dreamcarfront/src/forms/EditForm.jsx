import {Component} from "react";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import * as React from "react";
import forms from './forms.css';


class EditForm extends Component {
    state = {
        show: false,
        entries:this.props.entries,
        row: this.props.row,
    };

    handleClose = () => this.setState({show: false});
    handleShow = () => this.setState({show: true});
    handleChange = (event) => {
        const newValue = event.target.value;
        const propName = event.target.id;
        this.props.row[propName] = newValue;
        this.setState({row: this.props.row});
    };
    handleSave = async (event) => {
        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(this.state.row)
        };
        const response = await fetch(`http://localhost:8080/${this.props.requestPath}/${this.props.updateId}`, requestOptions);
        const data = await response.json();

        if (data === "OK") {
            this.props.rerenderParentCallback();
        }

        this.handleClose();
    };

    render() {
        return (
            <>
                <Button variant="outline-primary" size="sm" onClick={() => {this.handleShow()}}>Modifica</Button>


                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Modificare</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <form id="createForm">
                            {this.props.entries.map((entry) => (
                                <div key={entry}>
                                    <label className="label">{entry.split(/(?=[A-Z])/).join(" ").toLowerCase()}</label>
                                    <input id={entry} value={this.props.row[entry]} onChange={this.handleChange} className="input"/>
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

export default EditForm;