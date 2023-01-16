import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import {Component} from "react";
import {Button} from "reactstrap";
import CreateForm from "../forms/CreateForm";
import EditForm from "../forms/EditForm";
import {TextField} from "@mui/material";

class Oferte extends Component {

    state = {
        oferte: [],
        requestPath: "allOferte",
        bAdmin: this.props.bAdmin,
    };

    entries = this.props.bAdmin ?
        ["pretTinta", "timeout", "timeoutUOM", "produs", "nrBucati", "descriere"] :
        ["nrBucati", "pret"];

    // Request data
    async componentDidMount() {
        const oferteResp = await fetch('/allOferte');
        const oferteBody = await oferteResp.json();

        this.setState({
            oferte: oferteBody,
        });
    };

    // Handle delete
    deleteEntry = async (id) => {
        const result = await fetch(`http://localhost:8080/allOferte/${id}`, { method: 'DELETE' });

        if (result.status === 200) {
            this.setState({
                oferte: this.state.oferte.filter((prop) => prop.idOferta !== id)
            })
        }
    };

    // Rerender table after CRUD operations -> Request data
    rerenderParentCallback = async () => {
        const oferteResp = await fetch('/allOferte');
        const oferteBody = await oferteResp.json();

        this.setState({
            oferte: oferteBody,
        });
    };

    // Oferte Table
    render () {
        return (
            <div>
                <h2 className="table">
                    Oferte
                </h2>
                <TableContainer component={Paper} className="table">
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align="left">Actiuni</TableCell>
                                <TableCell align="left">Id Solicitare</TableCell>
                                <TableCell align="left">Nr. Bucati</TableCell>
                                <TableCell align="left">Pret</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.oferte.map((row) => (
                                <TableRow
                                    key={row.idOferta}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell align="left">
                                        {!this.state.bAdmin && row.username === this.props.username ? <Button variant="outline-secondary" size="sm" onClick={() => {
                                            this.deleteEntry(row.idOferta)
                                        }}>Sterge</Button> : null}
                                        {!this.state.bAdmin && row.username === this.props.username ? <EditForm row={row} updateId={row.idOferta} entries={this.entries}
                                                   requestPath={this.state.requestPath}
                                                   rerenderParentCallback={this.rerenderParentCallback.bind(this)}/> : null}
                                    </TableCell>
                                    <TableCell align="left">{row.solicitare}</TableCell>
                                    <TableCell align="left">{row.nrBucati}</TableCell>
                                    <TableCell align="left">{row.pret}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        )
    }
}

export default Oferte;