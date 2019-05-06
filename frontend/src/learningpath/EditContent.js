import React, { Component } from 'react';
import { ACCESS_TOKEN, API_BASE_URL } from "../constants";
import axios from "axios";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { Button } from "react-bootstrap";
import { createContent } from "../util/APIUtils";
import toast from "toasted-notes";
import EditorField from "../texteditor/EditorField"

class EditContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            content: false,
            isLoading: false
        };
        this.loadContentById = this.loadContentById.bind(this);

    }

    loadContentById() {
        let url = API_BASE_URL + `/contents/${this.props.match.params.contentId}`;

        const options = {
            method: 'GET',
            headers: { 'content-type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) },
            url
        };

        axios(options)
            .then(res => {
                this.setState({ content: res.data })
            }).catch(err => {
            this.setState({ isLoading: false })
        });

    }

    componentDidMount() {
        this.loadContentById();
    }
    render() {
        const props = this.props;
        const vm = this.state;

        return (
            vm.content && (
                <div>
                    <div className="sectionPadding">
                        <div className="container text-left ">
                            <Formik
                                initialValues={{ title: vm.content.title, text: vm.content.text }}
                                validate={values => {
                                    let errors = {};

                                    if (!values.title) {
                                        errors.title = 'Content Title is required';
                                    }

                                    if (!values.text) {
                                        errors.text = 'Content Text is required';
                                    }

                                    return errors;
                                }}
                                onSubmit={(values, { setSubmitting }) => {
                                    setTimeout(() => {

                                        let topicId = vm.content.topicId;
                                        const newContent = {
                                            id: vm.content.id,
                                            title: values.title,
                                            text: values.text
                                        };

                                        createContent(newContent, topicId)
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
                                            <label htmlFor="contentTitle" className="col-sm-12 col-form-label">Content <strong>Title</strong></label>
                                            <div className="col-sm-12">
                                                <Field type="text" name="title" id="contentTitle" placeholder="content title" className="form-control" />
                                                <ErrorMessage name="contentTitle" component="div" />
                                            </div>
                                        </div>

                                        <div className="form-group row text-left">
                                            <label htmlFor="contentText" className="col-sm-12 col-form-label">Content <strong>Text</strong> </label>
                                            <div className="col-sm-12">
                                                <Field name="text" component={EditorField} placeholder="Enter Content" row="20" />
                                                <ErrorMessage name="contentText" component="div" />
                                            </div>
                                        </div>

                                        <Button variant="info" type="submit" block disabled={isSubmitting}>Save</Button>
                                    </Form>
                                )}
                            </Formik>
                        </div>
                    </div>
                </div>
            )
        )
    };

}

export default EditContent;