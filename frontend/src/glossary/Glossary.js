import React, {Component} from 'react';
import { Button, Row, InputGroup } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import { API_BASE_URL } from '../constants';
import './Glossary.css';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHashtag } from '@fortawesome/free-solid-svg-icons'

class Glossary extends Component {
    constructor(props){
        super(props);
        this.state = {
            topics: [],
            isLoading: false,
            input:''
        };
        this.loadTopicList = this.loadTopicList.bind(this);
    }

    loadTopicList(){
        let url = API_BASE_URL + "/topics";
        axios.get(url).then(res => {
            this.setState({
                topics: res.data,
                isLoading: false
            })
        }).catch(err => {
            console.log(err);
            this.setState({isLoading: false})
        });
    }

    componentDidMount() {
        this.loadTopicList(this.state.page, this.state.size);
    }

    onChangeHandler(e) {
        this.setState({
            input: e.target.value,
        })
    }

    render(){
        if (this.state.isLoading) {
            return <h1>isLoading!...</h1>
        }
        const topics = this.state.topics;
        const topicsView = topics.filter(topic => this.state.input === '' || topic.title.toLowerCase().indexOf(this.state.input) > -1).map((topic, topicIndex) => {
            return (
                <Row className="justify-content-center mb-1" key={topicIndex}>
                    <div className="card mb-3" style={{minWidth: "100%"}}>
                        <div className="row no-gutters align-items-center">
                            <div className="col-md-4">
                                <img src={topic.imageUrl} className="rounded imageSize" alt={topic.title}/>
                            </div>
                            <div className="col-md-8">
                                <div className="card-body">
                                    <h5 className="card-title text-info text-justify">{topic.title}</h5>
                                    <p className="card-text text-justify">{topic.description}</p>
                                    <p className="card-text text-justify">Tags<FontAwesomeIcon icon={faHashtag} />
                                        {topic.wikiData.map((wiki, wikiIndex) => {
                                            return <a key={wikiIndex} href={wiki} target="_blank" rel="noopener noreferrer" className="badge badge-pill badge-info">{wiki.substring(wiki.indexOf("Q"), wiki.length)}</a>
                                        })}
                                    </p>
                                    <div className="card-footer text-muted border">
                                        <p>
                                            <span className="badge badge-success">{topic.contentList.length}</span> Learning Path {' '}
                                            <span className="badge badge-warning">??</span> Questions {' '}
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
                <div className="row justify-content-center mt-5 mb-5">
                    <InputGroup>
                        <input
                            value={this.state.input}
                            placeholder="Search topics"
                            className="form-control searchInput" type="text" onChange={this.onChangeHandler.bind(this)} />
                    </InputGroup>
                </div>
                {topicsView}
            </div>
        )
    }
}

export default withRouter(Glossary);