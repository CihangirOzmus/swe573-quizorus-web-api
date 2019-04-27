import React, {Component} from 'react';
import {ACCESS_TOKEN, API_BASE_URL} from "../constants";
import axios from "axios";
import {Col, Jumbotron, ListGroup, Row, Tab} from "react-bootstrap";
import {Link} from "react-router-dom";

class Topic extends Component{
    constructor(props){
        super(props);
        this.state = {
            topic: {
                contentList:[]
            },
            isLoading: false
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
        if (this.state.isLoading) {
            return <h1>isLoading!...</h1>
        }

        const topic = this.state.topic;
        const contentList = this.state.topic.contentList;
        console.log(contentList);

        const contentListLeftColumn = contentList.map((content, contentId) => {
            return (
                <ListGroup.Item key={contentId} action eventKey={content.id}>
                    {content.title}
                </ListGroup.Item>
            )
        });

        const contentListRightColumn = contentList.map((content, contentId) => {
            return (
                <Tab.Pane key={contentId} eventKey={content.id}>
                    <p>{content.text}</p>
                </Tab.Pane>
            )
        });

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

                <Tab.Container id="list-group-tabs-example" defaultActiveKey="0">
                    <Row>
                        <Col sm={3}>
                            <ListGroup>
                                {contentListLeftColumn}
                            </ListGroup>
                        </Col>
                        <Col sm={9}>
                            <Tab.Content>
                                {contentListRightColumn}
                            </Tab.Content>
                        </Col>
                    </Row>
                </Tab.Container>

            </React.Fragment>
        )
    }
}

export default Topic;