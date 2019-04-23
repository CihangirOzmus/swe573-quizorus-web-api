import React, {Component} from 'react';

class UserEnrolledTopicList extends Component{
    render() {
        return(
            <h1 className="text-danger">Enrolled Topics by @{this.props.currentUser.username}</h1>
        )
    }
}

export default UserEnrolledTopicList;