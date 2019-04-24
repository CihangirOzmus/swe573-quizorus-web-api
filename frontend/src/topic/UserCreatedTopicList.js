import React, {Component} from 'react';
import {ACCESS_TOKEN, API_BASE_URL, TOPIC_LIST_SIZE} from "../constants";
import axios from "axios";
import {Badge, Button, Table} from "react-bootstrap";
import {Link} from "react-router-dom";

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
        this.handleDeleteTopicById = this.handleDeleteTopicById.bind(this);
    }

    loadUserCreatedTopics(page, size=TOPIC_LIST_SIZE){
        const username = this.props.currentUser.username;
        let url = API_BASE_URL + `/users/${username}/topics/?page=${page}&size=${size}`;

        axios.get(url).then(res => {
            this.setState({
                topics: res.data.content,
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

    handleDeleteTopicById(topicIdToDelete){
        let url = API_BASE_URL + `/topics/topic/${topicIdToDelete}`;

        const options = {
            method: 'DELETE',
            headers: { 'content-type': 'application/json', 'Authorization' : 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)},
            url
        };

        axios(options)
            .then(res => {
                this.loadUserCreatedTopics(this.state.page, this.state.size)
            }).catch(err => {
                console.log(err)
        });

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
                <tr key={topicIndex}>
                    <td>{topicIndex+1 === 1 ? <Badge variant="success">New</Badge> : topicIndex + 1}</td>
                    <td>{topic.title}</td>
                    <td>{topic.description}</td>
                    <td>{topic.wikiData.map((wiki) => {
                        return <a href={wiki} target="_blank" rel="noopener noreferrer" className="badge badge-pill badge-info">{wiki.substring(wiki.indexOf("Q"), wiki.length)}</a>
                    })}</td>
                    <td>???</td>
                    <td>???</td>
                    <td>
                        <Link className="btn btn-outline-info" to={`/topic/${topic.id}`}>Details</Link>
                        <Button className="ml-2" variant="outline-danger" onClick={() => this.handleDeleteTopicById(topic.id)}>Delete</Button>
                    </td>
                </tr>
            )
        });

        return(
            <div>
                <h1 className="text-danger m-5">Created Topics by @{this.props.currentUser.username}</h1>
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Title</th>
                        <th>Short Description</th>
                        <th>Wikidata</th>
                        <th>#Learning Path</th>
                        <th>#Quiz</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {topicsView}
                    </tbody>
                </Table>
            </div>
        )
    }
}

export default UserCreatedTopicList;