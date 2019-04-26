import React, {Component} from 'react';
import './App.css';
import {Route, withRouter, Switch} from 'react-router-dom';
import {getCurrentUser} from '../util/APIUtils';
import {ACCESS_TOKEN} from '../constants';

import Glossary from '../glossary/Glossary';
import CreateTopic from '../topic/CreateTopic';
import Login from '../user/Login';
import Signup from '../user/Signup';
import Home from '../common/Home';
import AppHeader from '../common/AppHeader';
import NotFound from '../common/NotFound';
import PrivateRoute from '../common/PrivateRoute';
import toast from 'toasted-notes'
import 'toasted-notes/src/styles.css';
import UserCreatedTopicList from "../topic/UserCreatedTopicList";
import UserEnrolledTopicList from "../topic/UserEnrolledTopicList";
import UserProfile from "../user/UserProfile";
import Topic from "../topic/Topic";
import AddContent from "../learningpath/AddContent";

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

    handleLogout() {
        localStorage.removeItem(ACCESS_TOKEN);

        this.setState({
            currentUser: null,
            isAuthenticated: false
        });

        this.props.history.push("/");

        toast.notify("You're successfully logged out.", { position : "bottom-right"});
    }

    handleLogin() {
        toast.notify("You're successfully logged in.", { position : "bottom-right"});
        this.loadCurrentUser();
        this.props.history.push("/glossary");
    }

    render() {
        if (this.state.isLoading) {
            return <h1>isLoading!...</h1>
        }

        return (
            <div className="App">
                <AppHeader
                   isAuthenticated={this.state.isAuthenticated}
                   currentUser={this.state.currentUser}
                   onLogout={this.handleLogout}/>

                <div className="container">
                    <Switch>

                        <Route exact path="/" component={Home}></Route>

                        <Route path="/glossary" component={Glossary}></Route>

                        <Route path="/login" 
                            render={(props) => <Login 
                                onLogin={this.handleLogin} 
                                {...props} />}>
                        </Route>

                        <Route path="/signup" component={Signup}></Route>

                        <PrivateRoute
                            exact path="/:username"
                            authenticated={this.state.isAuthenticated}
                            currentUser={this.state.currentUser}
                            component={UserProfile}
                        >
                        </PrivateRoute>

                        <PrivateRoute
                            path="/:username/topics/created"
                            authenticated={this.state.isAuthenticated}
                            currentUser={this.state.currentUser}
                            component={UserCreatedTopicList}
                        >
                        </PrivateRoute>

                        <PrivateRoute
                            path="/:username/topics/enrolled"
                            authenticated={this.state.isAuthenticated}
                            currentUser={this.state.currentUser}
                            component={UserEnrolledTopicList}
                        >
                        </PrivateRoute>

                        <PrivateRoute 
                            authenticated={this.state.isAuthenticated} 
                            path="/topic/new"
                            currentUser={this.state.currentUser}
                            component={CreateTopic}
                        ></PrivateRoute>

                        <PrivateRoute
                            authenticated={this.state.isAuthenticated}
                            path="/topic/:topicId/content"
                            component={AddContent}
                        ></PrivateRoute>

                        <PrivateRoute
                            authenticated={this.state.isAuthenticated}
                            path="/topic/:topicId"
                            component={Topic}
                        ></PrivateRoute>

                        <Route component={NotFound}></Route>

                    </Switch>
                </div>
            </div>
        );
    }
}

export default withRouter(App);
