import React, { Component } from 'react';
import { login } from '../util/APIUtils';
import { Link } from 'react-router-dom';
import { ACCESS_TOKEN } from '../constants';

import { notification } from 'antd';
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";


class Login extends Component {
    render() {
        return (
            <div className="container w-50 mt-3">
                <h2 className="m-5">Login</h2>
                <Form>
                    <Form.Group className="row" controlId="formPlaintextUsernameOrEmail">
                        <Form.Label column sm="3">
                            Username/Email
                        </Form.Label>
                        <Col sm="9">
                            <Form.Control type="text" placeholder="username" />
                        </Col>
                    </Form.Group>

                    <Form.Group className="row" controlId="formPlaintextPassword">
                        <Form.Label column sm="3">
                            Password
                        </Form.Label>
                        <Col sm="9">
                            <Form.Control type="password" placeholder="Password" />
                        </Col>
                    </Form.Group>

                    <Button className="mt-4" variant="primary" type="submit" block>
                        Login
                    </Button>
                </Form>
            </div>
        );
    }
}


export default Login;