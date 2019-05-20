import React, { Component } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik/dist/index';
import { Button } from "react-bootstrap";
import { createContent } from "../util/APIUtils";
import toast from "toasted-notes/lib/index";
import EditorField from '../common/EditorField'
import Loading from '../common/Loading';

class AddContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true
        };
    }

    componentDidMount() {
        this.setState({ loading: false })
    }

    render() {
        const { loading } = this.state
        const props = this.props
        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>
                        <div className="pageHeader text-left">
                            <div className="container">
                                <div className="row">
                                    <div className="col-md-12">
                                        Add Content
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="sectionPadding">
                            <div className="container text-left ">
                                <div className="row">
                                    <div className="col-md-12">
                                        <Formik
                                            initialValues={{ title: '', text: '' }}
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
                                                this.setState({ loading: true })
                                                setTimeout(() => {

                                                    let topicId = props.match.params.topicId;
                                                    const newContent = {
                                                        topicId: topicId,
                                                        title: values.title,
                                                        text: values.text
                                                    };

                                                    createContent(newContent, topicId)
                                                        .then(res => {
                                                            toast.notify("Content created successfully.", { position: "top-right" });
                                                            props.history.push(`/topic/${topicId}`);
                                                        }).catch(err => {
                                                            this.setState({ loading: false })
                                                            toast.notify("Something went wrong!", { position: "top-right" });
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
                                                        <label htmlFor="contentText" className="col-sm-12 col-form-label">Content <strong>Body</strong> </label>
                                                        <div className="col-sm-12">
                                                            <Field name="text" component={EditorField} placeholder="Enter Content" row="20" />
                                                            <ErrorMessage name="contentText" component="div" />
                                                        </div>
                                                    </div>

                                                    <Button variant="success" type="submit" block disabled={isSubmitting}>Save</Button>
                                                </Form>
                                            )}
                                        </Formik>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </React.Fragment>
                )}
            </React.Fragment>
        )
    }
}

export default AddContent;