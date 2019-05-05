import React, { Component } from 'react';
import { ACCESS_TOKEN, API_BASE_URL } from "../constants";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import { createTopic } from '../util/APIUtils';
import { withRouter } from 'react-router-dom';
import toast from "toasted-notes";
import wdk from "wikidata-sdk";
import axios from "axios";
import { Row } from "react-bootstrap";

class EditTopic extends Component {
    constructor(props) {
        super(props);
        this.timer = null;
        this.state = {
            title: '',
            description: '',
            imageUrl: '',
            wikiDataSearch: [],
            wikiData: [],
            topic: false
        };
        this.handleTitleChange = this.handleTitleChange.bind(this);
        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
        this.handleKeywordChange = this.handleKeywordChange.bind(this);
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

    handleKeywordChange(event) {
        clearTimeout(this.timer);

        const value = event.target.value;
        if (value !== '') {
            this.timer = setTimeout(() => {
                const url = wdk.searchEntities(value, 'en', 5, 'json');
                axios.get(url)
                    .then(response => {
                        if (response.data.search.length > 0) {
                            this.setState({ wikiDataSearch: response.data.search })
                            toast.notify("Found in WikiData!", { position: "top-right" })
                        } else {
                            toast.notify("Keyword can not found!", { position: "top-right" });
                        }
                    })
            }, 1000)
        } else {
            this.setState({ wikiDataSearch: [] })
        }
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
        const wikidatas = this.state.wikiDataSearch;
        const wikidataResultList = wikidatas.map((wiki, wikiIndex) => {
            return (
                // if the description is empty, empty row seen, try domates
                <Row key={wikiIndex} className="border-bottom border-info p-1 m-1 text-left">
                    {wiki.description && (
                        <React.Fragment>
                            <Col md="1"><Form.Check onChange={this.handleSelect}
                                                    type="checkbox"
                                                    id="default-checkbox"
                                                    value={wiki.concepturi}
                            /></Col>
                            <Col md="9">{wiki.description}</Col>
                            <Col md="2"><a href={wiki.concepturi} target="_blank" rel="noopener noreferrer">Visit</a></Col>
                        </React.Fragment>
                    )}
                </Row>
            )
        });

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
                                                        toast.notify("Content updated successfully.", { position: "top-right" });
                                                        props.history.push(`/topic/${topicId}`);
                                                    }).catch(err => {
                                                    toast.notify("Topic does not exist!", { position: "top-right" });
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