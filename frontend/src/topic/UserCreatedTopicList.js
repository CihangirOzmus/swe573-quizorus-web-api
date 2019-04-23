import React, {Component} from 'react';
import {API_BASE_URL, TOPIC_LIST_SIZE} from "../constants";
import axios from "axios";
import {Button, Row} from "react-bootstrap";

class UserCreatedTopicList extends Component{
    constructor(props){
        super(props);
        this.state = {
            topics: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadUserCreatedTopics = this.loadUserCreatedTopics.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
    }

    loadUserCreatedTopics(page, size=TOPIC_LIST_SIZE){
        const username = this.props.currentUser.username;
        const topics = this.state.topics.slice();
        let url = API_BASE_URL + `/users/${username}/topics/?page=${page}&size=${size}`;

        axios.get(url).then(res => {
            this.setState({
                topics: topics.concat(res.data.content),
                page: res.data.page,
                size: res.data.size,
                totalElements: res.data.totalElements,
                totalPages: res.data.totalPages,
                last: res.data.last,
                isLoading: false
            })
        }).catch(err => {
            this.setState({isLoading: false})
        });
    }

    handleLoadMore(){
        console.log("more topics loaded!..");
        this.loadTopicList(this.state.page + 1);
    }

    componentDidMount() {
        this.loadUserCreatedTopics(this.state.page, this.state.size)
    }

    render() {
        if (this.state.isLoading) {
            return <h1>isLoading!...</h1>
        }
        const topics = this.state.topics;
        const topicsView = topics.map((topic, topicIndex) => {
            return (
                <Row className="justify-content-center mb-1" key={topicIndex}>
                    <div className="card mb-3" style={{minWidth: "100%"}}>
                        <div className="row no-gutters align-items-center">
                            <div className="col-md-4">
                                <img src={"https://via.placeholder.com/200x200"} className="rounded" alt={topic.title}/>
                            </div>
                            <div className="col-md-8">
                                <div className="card-body">
                                    <h5 className="card-title text-info text-justify">{topic.title}</h5>
                                    <p className="card-text text-justify">{topic.description}</p>
                                    <p className="card-text text-justify text-danger">{topic.wikiData}</p>
                                    <div className="card-footer text-muted border">
                                        <p>
                                            <span className="badge badge-success">??</span> Learning Path {' '}
                                            <span className="badge badge-warning">??</span> Questions {' '}
                                            <span className="badge badge-light">Created by</span> @{topic.createdBy.username} {' '}
                                        </p>
                                        <Button variant="info" block>Details</Button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </Row>
            )
        });

        return(
            <div>
                <h1 className="text-danger m-5">Created Topics by @{this.props.currentUser.username}</h1>
                {topicsView}
            </div>
        )
    }
}

export default UserCreatedTopicList;