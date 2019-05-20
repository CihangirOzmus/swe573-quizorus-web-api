import React, { Component } from 'react';
import { ACCESS_TOKEN } from "../constants";
import axios from "axios/index";
import toast from "toasted-notes/lib/index";
import { Link } from "react-router-dom";
import { WikiLabels } from "./Wiki";
import { resolveEndpoint } from "../util/Helpers";
import Loading from '../common/Loading';
import {faBookmark} from "@fortawesome/free-solid-svg-icons/index";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome/index";
import {Table} from "react-bootstrap";

class UserEnrolledTopicList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topics: [],
            input: '',
            loading: true
        };
        this.loadUserEnrolledTopics = this.loadUserEnrolledTopics.bind(this);
    }

    loadUserEnrolledTopics() {
        let url = resolveEndpoint('getEnrolledTopicsByUserId', [{ "slug1": this.props.currentUser.id }]);

        const REQUEST_HEADERS = {
            headers: { 'content-type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) }
        };

        axios.get(url, REQUEST_HEADERS).then(res => {

            this.setState({
                topics: res.data,
                loading: false
            })
        }).catch(err => {
            toast.notify("Something went wrong!", { position: "bottom-right" });
            console.log(err)
        });
    }

    componentDidMount() {
        this.loadUserEnrolledTopics()
    }

    render() {

        const { topics, loading } = this.state;

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
                    </td>
                </tr>
            )
        });

        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>
                        <div className="row pt-5">
                            <div className="col-md-12 text-center">
                                <Link to="/explore" className="btn btn-outline-info">
                                    <FontAwesomeIcon icon={faBookmark} /> Check New Courses
                                </Link>
                            </div>
                        </div>

                        <div className="container-fluid">
                            {
                                topics.length === 0 && (<div className="mt-5 text-center">Nothing to show</div>)
                            }
                            <div className="row pt-5 pb-5">
                                <Table striped bordered hover>
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Image</th>
                                        <th>Title</th>
                                        <th style={{ width: '40%' }}>Short Description</th>
                                        <th>Wikidata</th>
                                        <th>#Learning Path</th>
                                        <th style={{ width: '20%' }}>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {topicsView}
                                    </tbody>
                                </Table>
                            </div>
                        </div>
                    </React.Fragment>)

                }
            </React.Fragment>

        )
    }
}

export default UserEnrolledTopicList;