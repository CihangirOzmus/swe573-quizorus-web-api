import React, { Component } from 'react';
import { API_BASE_URL, REQUEST_HEADERS } from "../constants";
import axios from "axios";
import {Row, Tab, Button} from "react-bootstrap";
import { Link, withRouter } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEdit } from '@fortawesome/free-solid-svg-icons'
import PageHeader from "../components/PageHeader";
import toast from "toasted-notes";
import {PathNavigator} from "../components/LearningPath";

class TopicPreview extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topic: {
                contentList: []
            },
            enrolled: [],
            activeTab: ''
        };
        this.loadTopicById = this.loadTopicById.bind(this);
        this.getEnrolledTopicsByUserId = this.getEnrolledTopicsByUserId.bind(this);
        this.search = this.search.bind(this);
    }

    enrollUserToTopic(topicId) {
        const url = API_BASE_URL + `/topics/${topicId}/enroll/${this.props.currentUser.username}`;
        axios.post(url, null, REQUEST_HEADERS)
            .then(res => {
                toast.notify("Enrolled successfully.", { position: "bottom-right" });
                this.props.history.push(`/topic/view/${topicId}`)
            }).catch(err => {
                console.log(err)
            });
    }

    loadTopicById() {
        const url = API_BASE_URL + `/topics/topic/${this.props.match.params.topicId}`;

        axios.get(url, REQUEST_HEADERS).then(res => {
                this.setState({
                    topic: res.data,
                    activeTab: res.data.contentList.length > 0 ? res.data.contentList[0].id : ''
                })
            }).catch(err => {
                console.log(err)
            });
    }

    getEnrolledTopicsByUserId() {
        const url = API_BASE_URL + `/topics/enrolled/${this.props.currentUser.id}`;

        axios.get(url, REQUEST_HEADERS)
            .then(res => {
                this.setState({
                    enrolled: res.data
                });
                this.resolveEnrollment()
            }).catch(err => {
                console.log(err)
            });
    }

    componentDidMount() {
        this.loadTopicById();
        this.getEnrolledTopicsByUserId();
    }

    search(topicId, enrolled) {
        for (let i = 0; i < enrolled.length; i++) {
            if (enrolled[i].id === topicId) {
                return true;
            }
        }
    }

    resolveEnrollment() {
        const { topic, enrolled } = this.state;
        const result = this.search(topic.id, enrolled);
        if (result === true) {
            toast.notify("Welcome back!", { position: "bottom-right" });
            this.props.history.push(`/topic/view/${topic.id}`)
        }
    }

    render() {

        const { topic, activeTab } = this.state;
        const { editable } = this.props;

        return (
            <React.Fragment>
                <PageHeader title="Preview"></PageHeader>

                <div className="container">
                    <div className="row">
                        <div className="col-12 text-center mt-5 mb-5">
                            <Button
                                variant="outline-info"
                                onClick={() => this.enrollUserToTopic(topic.id)}>
                                Enroll To This Topic
                            </Button>
                        </div>
                    </div>
                </div>

                <div className="bg-alt sectionPadding text-left">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-2">

                            </div>
                            <div className="col-md-4">
                                <img src={topic.imageUrl} className="img-fluid rounded" alt={topic.title} />
                            </div>
                            <div className="col-md-4">
                                <h4 className="mb-4"><strong>{topic.title}</strong>
                                    {editable && (
                                        <Link className="btn btn-outline-primary btn-sm ml-2 inlineBtn" to={`/topic/${topic.id}/edit`}>
                                            <FontAwesomeIcon icon={faEdit} />
                                        </Link>
                                    )}
                                </h4>
                                <p>
                                    {topic.description}
                                </p>
                            </div>
                            <div className="col-md-2">

                            </div>
                        </div>
                    </div>
                </div>

                <hr/>

                <div className="container">
                    <div className="row col-md-12 text-left">
                        <h4>
                            Learning <strong>Path</strong>
                        </h4>
                    </div>
                </div>
                {
                    activeTab && (
                        <Tab.Container id="list-group-tabs-example" defaultActiveKey={activeTab}>
                            <div className="container-fluid mt-5 mb-5 text-left" >
                                <Row>
                                    <PathNavigator contents={topic.contentList} />
                                    <Tab.Content>
                                        {topic.contentList.map((content, contentId) => {
                                            return (
                                                <Tab.Pane key={contentId} eventKey={content.id}>
                                                    <div>
                                                        {content.text && (<div className="text-left" dangerouslySetInnerHTML={{ __html: content.text.substring(0,120) + "..."}}></div>)}
                                                        <Button
                                                            variant="info"
                                                            onClick={() => this.enrollUserToTopic(topic.id)}>
                                                            Enroll To Continue
                                                        </Button>
                                                    </div>
                                                </Tab.Pane>
                                            )
                                        })}
                                    </Tab.Content>
                                </Row>
                            </div>
                        </Tab.Container>
                    )
                }
            </React.Fragment>
        )
    }
}

export default withRouter(TopicPreview);