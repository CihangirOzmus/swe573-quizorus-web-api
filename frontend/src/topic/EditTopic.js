import React, { Component } from 'react';
import { ACCESS_TOKEN, API_BASE_URL } from "../constants";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Button from "react-bootstrap/Button";
import { createTopic } from '../util/APIUtils';
import { withRouter } from 'react-router-dom';
import toast from "toasted-notes";
import axios from "axios";

class EditTopic extends Component {
    constructor(props) {
        super(props);
        this.state = {
            title: '',
            description: '',
            imageUrl: '',
            topic: false
        };
        this.handleTitleChange = this.handleTitleChange.bind(this);
        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
        this.handleImageUrlChange = this.handleImageUrlChange.bind(this);
    }



    handleTitleChange(event) {
        const value = event.target.value;
        this.setState({ title: value })
    }

    handleDescriptionChange(event) {
        const value = event.target.value;
        this.setState({ description: value })
    }

    handleImageUrlChange(event) {
        const value = event.target.value;
        this.setState({ imageUrl: value })
    }

    handleSelect(event) {
        const wikiData = this.state.wikiData.slice();
        this.setState({
            wikiData: wikiData.concat(event.target.value)
        });
    }

    loadTopicById() {
        let url = API_BASE_URL + `/topics/topic/${this.props.match.params.topicId}`;

        const options = {
            method: 'GET',
            headers: { 'content-type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) },
            url
        };

        axios(options)
            .then(res => {
                this.setState({ topic: res.data })

            }).catch(err => {
            this.setState({ isLoading: false })
        });
    }

    componentDidMount() {
        this.loadTopicById();
    }

    render() {
        const vm = this.state;
        const props = this.props;

        return (

            vm.topic && (
                <React.Fragment>
                    <div className="sectionPadding">
                        <div className="container w-90 text-left">
                            <Formik
                                initialValues={{ title: vm.topic.title ? vm.topic.title : '', description: vm.topic.description ? vm.topic.description : '', imageUrl: vm.topic.imageUrl ? vm.topic.imageUrl : '' }}
                                validate={values => {
                                    let errors = {};

                                    if (!values.title) {
                                        errors.title = 'Topic Title is required';
                                    }

                                    if (!values.description) {
                                        errors.text = 'Topic Text is required';
                                    }

                                    return errors;
                                }}
                                onSubmit={(values, { setSubmitting }) => {
                                    setTimeout(() => {

                                        let topicId = vm.topic.id;
                                        const newTopic = {
                                            id: vm.topic.id,
                                            title: values.title,
                                            imageUrl: values.imageUrl,
                                            description: values.description,
                                        };

                                        createTopic(newTopic, topicId)
                                            .then(res => {
                                                toast.notify("Content updated successfully.", { position: "bottom-right" });
                                                props.history.push(`/topic/${topicId}`);
                                            }).catch(err => {
                                            toast.notify("Topic does not exist!", { position: "bottom-right" });
                                        });

                                        setSubmitting(false);
                                    }, 400);
                                }}
                            >
                                {({ isSubmitting }) => (
                                    <Form>
                                        <div className="form-group row text-left">
                                            <label htmlFor="topicTitle" className="col-sm-12 col-form-label">Topic <strong>Title</strong></label>
                                            <div className="col-sm-12">
                                                <Field type="text" name="title" id="topicTitle" placeholder="Topic title" className="form-control" />
                                                <ErrorMessage name="topicTitle" component="div" />
                                            </div>
                                        </div>
                                        <div className="form-group row text-left">
                                            <label htmlFor="topicImage" className="col-sm-12 col-form-label">Topic <strong>Image</strong></label>
                                            <div className="col-sm-12">
                                                <Field type="text" name="imageUrl" id="topicImage" placeholder="Topic image" className="form-control" />
                                                <ErrorMessage name="topicImage" component="div" />
                                            </div>
                                        </div>

                                        <div className="form-group row text-left">
                                            <label htmlFor="topicDescription" className="col-sm-12 col-form-label">Topic <strong>Body</strong> </label>
                                            <div className="col-sm-12">
                                                <Field type="text" component="textarea" rows="7" name="description" id="description" placeholder="Topic description" className="form-control" />
                                                <ErrorMessage name="topicDescription" component="div" />
                                            </div>
                                        </div>

                                        <Button variant="info" type="submit" block disabled={isSubmitting}>Save</Button>
                                    </Form>
                                )}
                            </Formik>
                        </div>
                    </div>
                </React.Fragment>
            )


        );
    }
}

export default withRouter(EditTopic);