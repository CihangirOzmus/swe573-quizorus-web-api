import React, { Component } from 'react';
import {ACCESS_TOKEN, API_BASE_URL} from "../util";
import axios from "axios/index";
import { Link } from "react-router-dom";
import { WikiLabels } from "./Wiki";
import { Table} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome/index";
import {faBookmark} from "@fortawesome/free-solid-svg-icons/index";

class UserEnrolledTopicList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topics: [],
            isLoading: false,
            input: ''
        };
        this.loadUserEnrolledTopics = this.loadUserEnrolledTopics.bind(this);

    }

    loadUserEnrolledTopics() {
        const url = API_BASE_URL + `/topics/enrolled/${this.props.currentUser.id}`;

        const REQUEST_HEADERS = {
            headers: { 'content-type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) }
        };

        axios.get(url, REQUEST_HEADERS)
            .then(res => {
                this.setState({
                    topics: res.data,
                    isLoading: false
                })
            }).catch(err => {
                this.setState({ isLoading: false })
            });
    }

    componentDidMount() {
        this.loadUserEnrolledTopics()
    }

    render() {

        const topics = this.state.topics;

        const topicsView = topics.map((topic, topicIndex) => {
            return (
                <tr key={topicIndex}>
                    <td>{topicIndex+1}</td>
                    <td>
                        <img src={topic.imageUrl} alt="" style={{ width: '100px' }}/>
                    </td>
                    <td>{topic.title}</td>
                    <td>{topic.description}</td>
                    <td>
                        <WikiLabels wikis={topic.wikiData} />
                    </td>
                    <td>{topic.contentList.length}</td>
                    <td>
                        <Link className="btn btn btn-outline-info" to={`/topic/view/${topic.id}`}>Details</Link>
                        <Link className="disabled btn btn-outline-warning ml-2" to={`/topic/${topic.id}`}>Statistics</Link>
                    </td>
                </tr>
            )
        });

        return (
            <React.Fragment>
                {/*<PageHeader title="Enrolled Topic List" />*/}

                <div className="row mt-5">
                    <div className="col-md-12 text-center">
                        <Link to="/explore" className="btn btn-outline-info">
                            <FontAwesomeIcon icon={faBookmark} /> Check New Courses
                        </Link>
                    </div>
                </div>

                <div className="container-fluid">
                    <div className="mt-5 mb-5">
                        <Table striped bordered hover>
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Image</th>
                                <th>Title</th>
                                <th style={{ width: '30%' }}>Short Description</th>
                                <th>Wikidata</th>
                                <th>#Learning Path</th>
                                <th style={{ width: '30%' }}>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                                {topicsView}
                            </tbody>
                        </Table>
                    </div>
                </div>
            </React.Fragment>

        )
    }
}

export default UserEnrolledTopicList;