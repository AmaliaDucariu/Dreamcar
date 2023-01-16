import React, { Component } from 'react'

class Form extends Component {

    state = {
        errorMessages: {
            message: "",
            name: ""
        },
        isSubmitted: false,
        dbUsers: []
    };

    errors = {
        uname: "invalid username",
        pass: "invalid password"
    };

    async componentDidMount() {
        const userResp = await fetch('/allUtilizatori');
        const userBody = await userResp.json();

        this.setState({
            dbUsers: userBody,
        });
    };

    renderErrorMessage = (name) => {
        if (name === this.state.errorMessages.name) {
            return <div className="error">{this.state.errorMessages.message}</div>
        }
    }

    handleSubmit = (event) => {
        //Prevent page reload
        event.preventDefault();

        const {uname, pass} = document.forms[0];

        // Find user login info
        const userData = this.state.dbUsers.find((user) => user.username === uname.value);

        // Compare user info
        if (userData) {
            if (userData.parola !== pass.value) {
                // Invalid password
                this.setState({errorMessages: {
                        name: "pass",
                        message: this.errors.pass
                    }});
            } else {
                let bAdmin = userData.rol === 1;
                let username = userData.username;

                this.props.rerenderParentCallback(bAdmin, true, username)
            }
        } else {
            // Username not found
            this.setState({errorMessages: {
                    name: "uname",
                    message: this.errors.uname
                }});
        }

        if (this.state.isSubmitted) {

        }
    }

    render() {
        return (
            <div className="app">
                <div className="login-form">
                    <div className="title">Sign In</div>
                        <div className="form">
                            <form onSubmit={this.handleSubmit}>
                                <div className="input-container">
                                    <label>Username </label>
                                    <input type="text" name="uname" required />
                                    {this.renderErrorMessage("uname")}
                                </div>
                                <div className="input-container">
                                    <label>Password </label>
                                    <input type="password" name="pass" required />
                                    {this.renderErrorMessage("pass")}
                                </div>
                                <div className="button-container">
                                    <input type="submit" />
                                </div>
                            </form>
                        </div>
                </div>
            </div>
        )
    }
}

export default Form