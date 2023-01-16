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

class Solicitari extends Component {

    state = {
        solicitari: [],
        oferte: [],
        bAdmin: this.props.bAdmin
    }
    entries = this.props.bAdmin ?
        ["pretTinta", "timeout", "timeoutUOM", "produs", "nrBucati", "descriere"] :
        ["nrBucati", "pret"];

    // Request data
    async componentDidMount() {
        const solicitariResp = await fetch('/allSolicitari');
        const solicitariBody = await solicitariResp.json();
        const oferteResp = await fetch('/allOferte');
        const oferteBody = await oferteResp.json();

        this.setState({
            solicitari: solicitariBody,
            oferte: oferteBody,
        });
    };

    // Handle delete
    deleteEntry = async (id) => {
        const result = await fetch(`http://localhost:8080/allSolicitari/${id}`, { method: 'DELETE' });

        if (result.status === 200) {
            this.setState({
                solicitari: this.state.solicitari.filter((prop) => prop.solicitare !== id)
            })
        }
    };

    // Rerender table after CRUD operations -> Request data
    rerenderParentCallback = async () => {
        const solicitariResp = await fetch('/allSolicitari');
        const solicitariBody = await solicitariResp.json();
        const oferteResp = await fetch('/allOferte');
        const oferteBody = await oferteResp.json();

        this.setState({
            solicitari: solicitariBody,
            oferte: oferteBody,
        });
    };

    // Solicitari Table
    render () {
        return (
            <div>
                <h2 className="table">
                    Solicitari
                </h2>
                <TableContainer component={Paper} className="table">

                    {this.state.bAdmin ? <CreateForm entries={this.entries} requestPath={"allSolicitari"}
                                 rerenderParentCallback={this.rerenderParentCallback.bind(this)}/> : null}
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align="left">Actiuni</TableCell>
                                <TableCell align="left">Id Solicitare</TableCell>
                                <TableCell align="left">Produs</TableCell>
                                <TableCell align="left">Nr. Bucati</TableCell>
                                <TableCell align="left">Descriere</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.solicitari.map((row) => (
                                <TableRow
                                    key={row.solicitare}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell align="left">
                                        {!this.state.bAdmin ? <CreateForm entries={this.entries} requestPath={"allOferte"} bAdmin={this.state.bAdmin} username={this.props.username} solicitare={row.solicitare}
                                                                         rerenderParentCallback={this.rerenderParentCallback.bind(this)}/> : null}
                                        {this.state.bAdmin ? <Button variant="outline-secondary" size="sm" onClick={() => {
                                            this.deleteEntry(row.solicitare)
                                        }}>Sterge</Button> : null}
                                        {this.state.bAdmin && !this.state.oferte.find(oferta => oferta.solicitare === row.solicitare) ? <EditForm row={row} updateId={row.solicitare} entries={this.entries}
                                                   requestPath={this.state.requestPath}
                                                   rerenderParentCallback={this.rerenderParentCallback.bind(this)}/> : null}
                                    </TableCell>
                                    <TableCell align="left">{row.solicitare}</TableCell>
                                    <TableCell align="left">{row.produs}</TableCell>
                                    <TableCell align="left">{row.nrBucati}</TableCell>
                                    <TableCell align="left">{row.descriere}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>

        )
    }
}

export default Solicitari;