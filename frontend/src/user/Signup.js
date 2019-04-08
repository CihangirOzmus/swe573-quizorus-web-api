import React, { Component } from 'react';
import { signup, checkUsernameAvailability, checkEmailAvailability } from '../util/APIUtils';
import { Link } from 'react-router-dom';
import {
    NAME_MIN_LENGTH, NAME_MAX_LENGTH,
    USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH,
    EMAIL_MAX_LENGTH,
    PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH
} from '../constants';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";

class Signup extends Component {
    render() {
        return (
            <div className="container w-50 mt-3">
                <h2 className="m-5">SignUp</h2>
                <Form>
                    <Form.Group className="row" controlId="formPlaintextFullName">
                        <Form.Label column sm="3">
                            Full Name
                        </Form.Label>
                        <Col sm="9">
                            <Form.Control type="text" placeholder="first and last names" />
                        </Col>
                    </Form.Group>

                    <Form.Group className="row" controlId="formPlaintextUsername">
                        <Form.Label column sm="3">
                            Username
                        </Form.Label>
                        <Col sm="9">
                            <Form.Control type="text" placeholder="username" />
                        </Col>
                    </Form.Group>

                    <Form.Group className="row" controlId="formPlaintextEmail">
                        <Form.Label column sm="3">
                            Email
                        </Form.Label>
                        <Col sm="9">
                            <Form.Control type="email" placeholder="email@example.com" />
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
                        Submit
                    </Button>
                </Form>
            </div>
        );
    }
}

export default Signup;