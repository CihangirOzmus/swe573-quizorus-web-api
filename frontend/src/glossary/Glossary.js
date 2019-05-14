import React, { Component } from 'react';
import { Row, InputGroup } from 'react-bootstrap';
import { Link, withRouter } from 'react-router-dom';
import { API_BASE_URL } from '../constants';
import PageHeader from "../components/PageHeader";
import { WikiLabels } from "../components/Wiki";
import axios from 'axios';

class Glossary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topics: [],
            input: ''
        };
        this.loadTopicList = this.loadTopicList.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
    }

    loadTopicList() {
        let url = API_BASE_URL + "/topics";

        axios.get(url).then(res => {
            this.setState({
                topics: res.data
            })
        }).catch(err => {
            console.log(err)
        });
    }

    handleSearch(e) {
        this.setState({
            input: e.target.value,
        })
    }

    componentDidMount() {
        this.loadTopicList();
    }


    render() {
        const topics = this.state.topics;

        return (
            <React.Fragment>
                {/*<PageHeader title="Glossary" />*/}

                <div className="container">
                    <div className="row  mt-5 mb-5">
                        <div className="col-md-12">
                            <InputGroup>
                                <input value={this.state.input} placeholder="Search topics" className="form-control searchInput" type="text" onChange={this.handleSearch} />
                            </InputGroup>
                        </div>
                    </div>
                    <div className="col-md-12">
                        {topics.filter(topic => this.state.input === '' || topic.title.toLowerCase().indexOf(this.state.input) > -1).map((topic, topicIndex) => {
                            return (
                                <Row className="mb-1" key={topicIndex}>
                                    <div className="card mb-4" style={{ minWidth: "100%" }}>
                                        <div className="row no-gutters ">
                                            <div className="col-md-4">
                                                <div className="clear p-4">
                                                    <img src={topic.imageUrl} className="img-fluid fullWidth mb-4" alt={topic.title} />
                                                    <Link className="btn btn-sm btn-info fullWidth" to={`/topic/preview/${topic.id}`}>Details</Link>
                                                </div>
                                            </div>
                                            <div className="col-md-8">
                                                <div className="card-body text-left">
                                                    <h5 className="card-title text-info text-justify mb-1">{topic.title} </h5>
                                                    <small className="text-left">Includes <strong>{topic.contentList.length}</strong> learning path</small>
                                                    <hr />
                                                    <p className="card-text text-justify">{topic.description}</p>
                                                    <WikiLabels
                                                        wikis={topic.wikiData}
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </Row>
                            )
                        })}
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

export default withRouter(Glossary);