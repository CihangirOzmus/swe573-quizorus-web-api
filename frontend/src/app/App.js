import React, {Component} from 'react';
import './App.css';
import {Route, withRouter, Switch} from 'react-router-dom';
import {getCurrentUser} from '../util/APIUtils';
import {ACCESS_TOKEN} from '../constants';

import Glossary from '../glossary/Glossary';
import CreateTopic from '../topic/CreateTopic';
import Login from '../user/Login';
import Signup from '../user/Signup';

import AppHeader from '../common/AppHeader';

import PrivateRoute from '../common/PrivateRoute';
import toast from 'toasted-notes'
import 'toasted-notes/src/styles.css';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: null,
            isAuthenticated: false,
            isLoading: false
        };
        this.handleLogout = this.handleLogout.bind(this);
        this.loadCurrentUser = this.loadCurrentUser.bind(this);
        this.handleLogin = this.handleLogin.bind(this);
    }

    loadCurrentUser() {
        this.setState({
            isLoading: true
        });

        getCurrentUser()
            .then(response => {
                this.setState({
                    currentUser: response,
                    isAuthenticated: true,
                    isLoading: false
                });
            }).catch(error => {
            this.setState({
                isLoading: false
            });
        });
    }

    componentDidMount() {
        this.loadCurrentUser();
    }

    handleLogout(redirectTo = "/") {
        localStorage.removeItem(ACCESS_TOKEN);

        this.setState({
            currentUser: null,
            isAuthenticated: false
        });

        this.props.history.push(redirectTo);

        toast.notify("You're successfully logged out.", { position : "top-right"});
    }

    handleLogin() {
        toast.notify("You're successfully logged in.", { position : "top-right"});
        this.loadCurrentUser();
        this.props.history.push("/");
    }

    render() {
        if (this.state.isLoading) {
            return <h1>isLoading!...</h1>
        }

        return (
            <div className="App">
                <AppHeader isAuthenticated={this.state.isAuthenticated}
                           currentUser={this.state.currentUser}
                           onLogout={this.handleLogout}/>

                <div className="container">
                    <Switch>
                        <Route exact path="/"
                            render={(props) => <Glossary 
                                isAuthenticated={this.state.isAuthenticated} 
                                currentUser={this.state.currentUser} 
                                handleLogout={this.handleLogout} 
                                {...props} />}>
                        </Route>

                        <Route path="/login" 
                            render={(props) => <Login 
                                onLogin={this.handleLogin} 
                                {...props} />}>
                        </Route>

                        <Route path="/signup" component={Signup}></Route>

                        <PrivateRoute 
                            authenticated={this.state.isAuthenticated} 
                            path="/createtopic" 
                            component={CreateTopic} 
                            handleLogout={this.handleLogout}
                        ></PrivateRoute>



                        
                    </Switch>
                </div>
            </div>
        );
    }
}

export default withRouter(App);
