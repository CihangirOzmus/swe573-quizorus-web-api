import React, {Component} from 'react';
import {API_BASE_URL} from "../constants";
import axios from "axios";
import {Button, Container, Jumbotron} from "react-bootstrap";

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
        axios.get(url).then(res => {
            console.log(res.data);
            this.setState({topic : res.data})

        }).catch(err => {
            this.setState({isLoading: false})
        });
    }

    componentDidMount() {
        this.loadTopicById();
    }


    render() {
        return(
            <React.Fragment>
                <Jumbotron fluid>
                    <Container>
                        <h1>{this.state.topic.title}</h1>
                        <h3>{this.state.topic.description}</h3>
                        <Button variant="outline-info">Add Learning Path</Button>
                    </Container>
                </Jumbotron>
            </React.Fragment>
        )
    }
}

export default Topic;