import React, {Component} from 'react';
import {ACCESS_TOKEN, API_BASE_URL} from "../constants";
import axios from "axios";
import {Jumbotron} from "react-bootstrap";
import {Link} from "react-router-dom";

class Topic extends Component{
    constructor(props){
        super(props);
        this.state = {
            topic: {}
        };
        this.loadTopicById = this.loadTopicById.bind(this);
    }

    loadTopicById(){
        let url = API_BASE_URL + `/topics/topic/${this.props.match.params.topicId}`;

        const options = {
            method: 'GET',
            headers: { 'content-type': 'application/json', 'Authorization' : 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)},
            url
        };

        axios(options)
            .then(res => {
                this.setState({topic : res.data})
            }).catch(err => {
                this.setState({isLoading: false})
            });
    }

    componentDidMount() {
        this.loadTopicById();
    }


    render() {
        const topic = this.state.topic;
        return(
            <React.Fragment>
                <Jumbotron>
                    <h1 className="font-weight-light">{topic.title}</h1>
                    <p className="font-italic">
                        {topic.description}
                    </p>
                    <p>
                        <Link className="btn btn-outline-info" to={`/topic/${topic.id}/content`}>Add Learning Path</Link>
                    </p>
                </Jumbotron>
            </React.Fragment>
        )
    }
}

export default Topic;