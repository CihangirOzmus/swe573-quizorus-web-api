import React, { Component } from 'react';
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import { login } from '../util/APIUtils';
import { Link } from 'react-router-dom';
import { ACCESS_TOKEN } from '../constants';
import toast from "toasted-notes";

class Login extends Component {
    constructor(props){
        super(props);
        this.form = React.createRef();
        this.state = {
            usernameOrEmail: '',
            password:''
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleUsernameOrEmailChange = this.handleUsernameOrEmailChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.validate = this.validate.bind(this);
    }

    handleUsernameOrEmailChange(event){
        this.setState({usernameOrEmail: event.target.value})
    }

    handlePasswordChange(event){
        this.setState({password: event.target.value})
    }

    validate() {

        return this.form.current.reportValidity();
    }

    handleSubmit(event) {
        event.preventDefault();
        const err = !this.validate();

        if (!err) {
            const loginRequest = this.state;
            login(loginRequest)
                .then(response => {
                    localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                    this.props.onLogin();
                }).catch(error => {
                if(error.status === 401) {
                    toast.notify('Your Username or Password is incorrect. Please try again!', { position : "top-right"});
                } else {
                    toast.notify('Sorry! Something went wrong. Please try again!', { position : "top-right"});
                }
            });
        }
    }

    render() {
        return (
            <div className="container w-50 mt-3">
                <h2 className="m-5">Login</h2>
                <Form ref={this.form} onSubmit={this.handleSubmit}>
                    <Form.Group className="row" controlId="formPlaintextUsernameOrEmail">
                        <Form.Label column sm="4">
                            Username/Email
                        </Form.Label>
                        <Col sm="8">
                            <Form.Control type="text" placeholder="username/email" required onChange={this.handleUsernameOrEmailChange}/>
                        </Col>
                    </Form.Group>

                    <Form.Group className="row" controlId="formPlaintextPassword" >
                        <Form.Label column sm="4">
                            Password
                        </Form.Label>
                        <Col sm="8">
                            <Form.Control type="password" placeholder="Password" required onChange={this.handlePasswordChange}/>
                        </Col>
                    </Form.Group>

                    <Button className="mt-4" variant="info" type="submit" block>
                        Login
                    </Button> Or <Link to="/signup">register now!</Link>
                </Form>
            </div>
        );
    }
}


export default Login;