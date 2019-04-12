import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import logo from '../img/logo.png';
import {Nav, Navbar} from "react-bootstrap";

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
                    <Nav.Link as={Link} className="mr-5" to="/glossary">Glossary</Nav.Link>
                    {!this.props.currentUser && <Nav.Link as={Link} className="mr-2" to="/login">Login</Nav.Link>}
                    {!this.props.currentUser && <Nav.Link as={Link} className="mr-2" to="/signup">SignUp</Nav.Link>}
                </Nav>
        } else {
            menuItems =
                <Nav className="ml-auto">
                    <Nav.Link className="mr-5" as={Link} to="/glossary">Glossary</Nav.Link>
                    {this.props.currentUser && <Nav.Link as={Link} className="mr-2" to="#">Create Topic</Nav.Link>}
                    {this.props.currentUser && <Nav.Link as={Link} className="mr-2" to="#">Profile</Nav.Link>}
                    {this.props.currentUser && <Nav.Link as={Link} className="mr-2" onClick={this.props.onLogout} to="/">Logout</Nav.Link>}
                </Nav>
        }

        return (
            <Navbar bg="info" variant="dark" expand="lg">
                <Navbar.Brand>
                    <Link to="/" style={{ textDecoration: 'none', color: 'white'}} >
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