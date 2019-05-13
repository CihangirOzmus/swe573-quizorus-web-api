import React, {Component} from 'react';
import PageHeader from "../components/PageHeader";

class UserProfile extends Component{
    render() {
        return(
            <React.Fragment>
                <PageHeader title="User Profile" />
                <div className="container text-center">
                    <h1 className="text-danger">@{this.props.currentUser.username} Profile</h1>
                    <p className="text-danger">Work in progress!</p>
                </div>
            </React.Fragment>
        )
    }
}

export default UserProfile;