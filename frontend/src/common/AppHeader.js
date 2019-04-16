import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import './AppHeader.css';
import logo from '../img/logo.png';
import {Nav, Navbar, NavDropdown} from "react-bootstrap";

class AppHeader extends Component {

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
        let menuItems;
        if (!this.props.currentUser) {
            menuItems =
                <Nav className="ml-auto">
                    <Nav.Link as={Link} className="mr-2" to="/glossary">Glossary</Nav.Link>
                    <Nav.Link as={Link} className="mr-2" to="/login">Login</Nav.Link>
                    <Nav.Link as={Link} className="mr-2" to="/signup">SignUp</Nav.Link>
                </Nav>
        } else {
            menuItems =
                <Nav className="ml-auto mr-5">
                    <Nav.Link className="mr-2" as={Link} to="/glossary">Glossary</Nav.Link>
                    <Nav.Link as={Link} className="mr-2" to="/createtopic">Create Topic</Nav.Link>
                    <NavDropdown title={this.props.currentUser.username} id="basic-nav-dropdown">
                        <NavDropdown.Item as={Link} to="/">Profile</NavDropdown.Item>
                        <NavDropdown.Item as={Link} to="/">Enrolled Topics</NavDropdown.Item>
                        <NavDropdown.Item as={Link} to="/">Created Topics</NavDropdown.Item>
                        <NavDropdown.Item as={Link} onClick={this.props.onLogout} to="/" >Logout</NavDropdown.Item>
                    </NavDropdown>
                </Nav>
        }

        return (
            <Navbar bg="info" variant="dark" expand="lg">
                <Navbar.Brand className="ml-5">
                    <Link to="/" style={{ textDecoration: 'none', color: 'white'}}>
                        <img
                            src={logo}
                            width="30"
                            height="30"
                            className="d-inline-block align-top"
                            alt="quizorus logo"
                        />
                        <span className="ml-2 font-weight-bold" >Quizorus</span>
                    </Link>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    {menuItems}
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

export default withRouter(AppHeader);