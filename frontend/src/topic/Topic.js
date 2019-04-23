import React, {Component} from 'react';

class Topic extends Component{
    render() {
        return(
            <h1 className="text-danger">Topic Page for {this.props.match.params.topicId}</h1>
        )
    }
}

export default Topic;