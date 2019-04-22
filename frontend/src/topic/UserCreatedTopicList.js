import React, {Component} from 'react';
import {API_BASE_URL, TOPIC_LIST_SIZE} from "../constants";

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
    }

    loadUserCreatedTopics(page, size=TOPIC_LIST_SIZE){
        const topics = this.state.topics.slice();
        let url = API_BASE_URL + "/topics/{usernamegelicek:)}?page=" + page + "$size=" + size;

    }

    render() {
        return(
            <h1 className="text-danger">Created Topic List</h1>
        )
    }
}

export default UserCreatedTopicList;