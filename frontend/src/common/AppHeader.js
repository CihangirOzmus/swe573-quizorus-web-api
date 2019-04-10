import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import logo from '../img/logo.png';
import {Nav, Navbar} from "react-bootstrap";

class AppHeader extends Component {

    constructor(props) {
        super(props);
        this.handleMenuClick = this.handleMenuClick.bind(this);

        this.toggle = this.toggle.bind(this);
        this.state = {
            isOpen: false
        };
    }

    handleMenuClick({key}) {
        if (key === "logout") {
            this.props.onLogout();
        }
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
                    <Nav.Link as={Link} className="mr-5" to="/glossary">Glossary</Nav.Link>
                    {!this.props.currentUser && <Nav.Link as={Link} className="mr-2" to="/login">Login</Nav.Link>}
                    {!this.props.currentUser && <Nav.Link as={Link} className="mr-2" to="/signup">SignUp</Nav.Link>}
                </Nav>
        } else {
            menuItems =
                <Nav className="ml-auto">
                    <Nav.Link className="mr-5" href="/glossary">Glossary</Nav.Link>
                    {this.props.currentUser && <Nav.Link as={Link} className="mr-2" href="#CT">Create Topic</Nav.Link>}
                    {this.props.currentUser && <Nav.Link as={Link} className="mr-2" href="#P">Profile</Nav.Link>}
                    {this.props.currentUser && <Nav.Link as={Link} className="mr-2" href="#L">Logout</Nav.Link>}
                </Nav>
        }

        return (
            <Navbar bg="info" variant="dark" expand="lg">
                <Navbar.Brand>
                    <Link style={{ textDecoration: 'none', color: 'white'}} to="/" >
                        <img
                            src={logo}
                            width="30"
                            height="30"
                            className="d-inline-block align-top"
                            alt="quizorus logo"
                        />
                        Quizorus
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