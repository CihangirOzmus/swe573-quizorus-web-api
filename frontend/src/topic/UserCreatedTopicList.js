import React, { Component } from 'react';
import { REQUEST_HEADERS } from "../constants";
import axios from "axios";
import toast from "toasted-notes";
import {Button, Table} from "react-bootstrap";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPlus } from '@fortawesome/free-solid-svg-icons'
import { WikiLabels } from "../components/Wiki";
import { resolveEndpoint } from "../util/Helpers";
import Loading from '../components/Loading';

class UserCreatedTopicList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topics: [],
            input: '',
            loading: true
        };
        this.loadUserCreatedTopics = this.loadUserCreatedTopics.bind(this);
        this.handleDeleteTopicById = this.handleDeleteTopicById.bind(this);
    }

    loadUserCreatedTopics() {
        let url = resolveEndpoint('getTopicsByUserId', [{ "slug1": this.props.currentUser.username }]);

        axios.get(url, REQUEST_HEADERS).then(res => {
            this.setState({
                topics: res.data,
                loading: false
            })
        }).catch(err => {
            toast.notify("Something went wrong!", { position: "top-right" });
            console.log(err)
        });
    }

    handleDeleteTopicById(topicIdToDelete) {
        let url = resolveEndpoint('deleteTopicById', [{ "slug1": topicIdToDelete }]);

        axios.delete(url, REQUEST_HEADERS)
            .then(res => {
                this.loadUserCreatedTopics()
            }).catch(err => {
                toast.notify("Something went wrong!", { position: "top-right" });
                console.log(err)
            });

    }

    componentDidMount() {
        this.loadUserCreatedTopics()
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
                        <Link className="btn btn-outline-info" to={`/topic/${topic.id}`}>Details</Link>
                        <Link className="disabled btn btn-outline-warning ml-2" to={`/topic/${topic.id}`}>Statistics</Link>
                        <Button className="ml-2" variant="outline-danger" onClick={() => this.handleDeleteTopicById(topic.id)}>Delete</Button>
                    </td>
                </tr>
            )
        });

        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>

                        <div className="container-fluid">
                            <div className="row mt-5">
                                <div className="col-md-12 text-center">
                                    <Link to="/topic/new" className="btn btn-outline-info">
                                        <FontAwesomeIcon icon={faPlus} /> Create a Topic
                            </Link>
                                </div>
                            </div>

                            <div className="row mt-5 mb-5">
                                <Table striped bordered hover responsive>
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Image</th>
                                        <th>Title</th>
                                        <th style={{ width: '30%' }}>Short Description</th>
                                        <th>WikiData</th>
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
                    </React.Fragment>)

                }
            </React.Fragment>

        )
    }
}

export default UserCreatedTopicList;