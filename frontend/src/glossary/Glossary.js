import React, {Component} from 'react';
import { FormControl, Button, Row, InputGroup } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import {API_BASE_URL, TOPIC_LIST_SIZE} from '../constants';
import './Glossary.css';

import axios from 'axios';

class Glossary extends Component {
    constructor(props){
        super(props);
        this.state = {
            topics: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false,
            filter: ''
        };
        this.loadTopicList = this.loadTopicList.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
        this.handleFilter = this.handleFilter.bind(this);
    }

    loadTopicList(page, size=TOPIC_LIST_SIZE){
        console.log("topics loaded!..")
        const topics = this.state.topics.slice();
        let url = API_BASE_URL + "/topics?page=" + page + "&size=" + size;
        axios.get(url).then(res => {
            console.log("===>>>");
            console.log(res.data);
            console.log("===>>>");

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

    componentDidMount() {
        this.loadTopicList(this.state.page, this.state.size);
    }


    handleLoadMore(){
        console.log("more topics loaded!..");
        this.loadTopicList(this.state.page + 1);
    }

    handleFilter(event){
        let filterText = event.target.value.toLowerCase().trim();
        if (filterText){
            this.setState({filterText});
        }
    }

    render(){
        if (this.state.isLoading) {
            return <h1>isLoading!...</h1>
        }
        console.log(this.state.filterText);
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
                                            <span className="badge badge-success">3x</span> Learning Path {' '}
                                            <span className="badge badge-warning">12x</span> Questions {' '}
                                            <span className="badge badge-light">Created by</span> @{topic.createdBy.username} {' '}
                                        </p>
                                        <Button variant="info" block>Enroll</Button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </Row>
            )
        });



        return (
            <div>
                <div className="row justify-content-center m-5">
                <InputGroup className="m-5 w-50">
                    <FormControl onChange={this.handleFilter}
                        placeholder="Search"
                    />
                </InputGroup>
                </div>
                {topicsView}
            </div>
        )
    }
}

export default withRouter(Glossary);