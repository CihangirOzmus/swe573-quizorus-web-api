import React from 'react';
import logo from '../img/logo.png';
import {Button, Form, FormControl, Nav, Navbar, NavDropdown} from "react-bootstrap";

export default class AppNavBar extends React.Component {
    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        this.state = {
            isOpen: false
        };
    }
    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }
    render() {
        return (
            <Navbar bg="light" expand="lg">
                <Navbar.Brand href="#home">
                    <img
                        src={logo}
                        width="30"
                        height="30"
                        className="d-inline-block align-top"
                        alt="quizorus logo"
                    />
                    Quizorus</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="ml-auto">
                        <Nav.Link className="mr-5" href="#">Glossary</Nav.Link>
                        <Nav.Link className="mr-2" href="#">Login</Nav.Link>
                        <Nav.Link className="mr-2" href="#">SignUp</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}