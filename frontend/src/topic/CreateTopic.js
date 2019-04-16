import React, { Component } from 'react';
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import { createTopic } from '../util/APIUtils';
import toast from "toasted-notes";

class CreateTopic extends Component {
    constructor(props){
        super(props);
        this.form = React.createRef();
        this.state = {
            title: '',
            description:''
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleTitleChange = this.handleTitleChange.bind(this);
        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const newTopic = {
            title: this.state.title,
            description: this.state.description
        }

        createTopic(newTopic)
        .then(response => {
            toast.notify("Topic created successfully.", { position : "top-right"});
            this.props.history.push("/");
        }).catch(error => {
            if(error.status === 401) {
                this.props.handleLogout();    
            } else {
                toast.notify('Sorry! Something went wrong. Please try again!', { position : "top-right"});  
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

    render() {
        return (
            <div className="container w-50 mt-3">
                <h2 className="m-5">New Topic</h2>
                <Form ref={this.form} onSubmit={this.handleSubmit}>
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

                    <Button className="mt-4" variant="info" type="submit" block>
                        Create Topic
                    </Button>
                </Form>
            </div>
        );
    }
}

export default CreateTopic;