import React, { Component } from 'react';
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import { createTopic } from '../util/APIUtils';
import toast from "toasted-notes";
import wdk from "wikidata-sdk";
import axios from "axios";
import {Badge, Row} from "react-bootstrap";

class CreateTopic extends Component {
    constructor(props){
        super(props);
        this.timer = null;
        this.state = {
            title: '',
            description:'',
            imageUrl:'',
            wikiDataSearch: [],
            wikiData:[]
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleTitleChange = this.handleTitleChange.bind(this);
        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
        this.handleImageUrlChange = this.handleImageUrlChange.bind(this);
        this.handleKeywordChange = this.handleKeywordChange.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();

        const newTopic = {
            title: this.state.title,
            description: this.state.description,
            imageUrl: this.state.imageUrl,
            wikiData: this.state.wikiData
        };

        createTopic(newTopic)
        .then(response => {
            toast.notify("Topic created successfully.", { position : "bottom-right"});
            this.props.history.push(`/${this.props.currentUser.username}/topics/created`);
        }).catch(error => {
            if(error.status === 401) {
                this.props.handleLogout();    
            } else {
                toast.notify('Sorry! Something went wrong. Please try again!', { position : "bottom-right"});
            }
        });

    }

    handleTitleChange(event){
        const value = event.target.value;
        this.setState({title : value})
    }

    handleDescriptionChange(event){
        const value = event.target.value;
        this.setState({description : value})
    }

    handleImageUrlChange(event){
        const value = event.target.value;
        this.setState({imageUrl : value})
    }

    handleKeywordChange(event){
        clearTimeout(this.timer);

        const value = event.target.value;
        if ( value !== ''){
            this.timer = setTimeout(() => {
                const url = wdk.searchEntities(value, 'en', 5, 'json');
                axios.get(url)
                    .then(response => {
                        if (response.data.search.length > 0){
                            this.setState({wikiDataSearch : response.data.search})
                            toast.notify("Found in WikiData!", { position : "bottom-right"})
                        } else {
                            toast.notify("Keyword can not found!", { position : "bottom-right"});
                        }
                    })
            }, 1000)
        } else {
            this.setState({wikiDataSearch : []})
        }
    }

    handleSelect(event){
        const wikiData = this.state.wikiData.slice();
        this.setState({
            wikiData: wikiData.concat(event.target.value)
        });
    }

    render() {
        const wikidatas = this.state.wikiDataSearch;
        const wikidataResultList = wikidatas.map((wiki, wikiIndex) => {
            return (
                // if the description is empty, empty row seen, try domates
                <Row key={wikiIndex} className="border border-info p-1 m-1 text-left">
                    {wiki.description && (
                        <React.Fragment>
                            <Col md="1"><Form.Check onChange={this.handleSelect}
                                type="checkbox"
                                id="default-checkbox"
                                value={wiki.concepturi}
                            /></Col>
                            <Col md="9"><Badge variant="secondary">Description: </Badge> {wiki.description}</Col>
                            <Col md="2"><Badge variant="secondary">Url: </Badge> <a href={wiki.concepturi} target="_blank" rel="noopener noreferrer">WikiData Page</a></Col>
                        </React.Fragment>
                    )}
                </Row>
            )
        });

        return (
            <div className="container w-90 mt-3">
                <h2 className="m-5">New Topic</h2>
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group className="row" controlId="formPlaintextUsernameOrEmail">
                        <Form.Label column sm="4">
                            Title
                        </Form.Label>
                        <Col sm="8">
                            <Form.Control 
                                type="text" 
                                placeholder="Topic Title"
                                onChange={this.handleTitleChange}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group className="row" controlId="formPlaintextPassword" >
                        <Form.Label column sm="4">
                            Description
                        </Form.Label>
                        <Col sm="8">
                            <Form.Control
                                type="text" 
                                placeholder="Short Description"
                                onChange={this.handleDescriptionChange}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group className="row" controlId="formPlaintextImageUrl" >
                        <Form.Label column sm="4">
                            Image URL
                        </Form.Label>
                        <Col sm="8">
                            <Form.Control
                                type="text"
                                placeholder="Image URL"
                                onChange={this.handleImageUrlChange}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group className="row" controlId="formPlaintextPassword" >
                        <Form.Label column sm="4">
                            Keyword
                        </Form.Label>
                        <Col sm="8">
                            <Form.Control
                                type="text"
                                placeholder="Keyword"
                                onChange={this.handleKeywordChange}
                            />
                        </Col>
                    </Form.Group>

                    {wikidataResultList}

                    <Button className="mt-4" variant="info" type="submit" block>
                        Create Topic
                    </Button>
                </Form>
            </div>
        );
    }
}

export default CreateTopic;