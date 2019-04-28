import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import {Button} from "react-bootstrap";
import {createContent} from "../util/APIUtils";
import toast from "toasted-notes";
import EditorField from '../texteditor/EditorField'

const AddContent = (props) => (
    <div>
        {props.match.params.topicId}
        <h2 className="mt-3 text-info">Content Creation</h2>
        <Formik
            initialValues={{ title: '', text: ''}}
            validate={values => {
                let errors = {};

                if(!values.title){
                    errors.title = 'Content Title is required';
                }

                if(!values.text){
                    errors.text = 'Content Text is required';
                }

                return errors;
            }}
            onSubmit={(values, { setSubmitting }) => {
                setTimeout(() => {

                    let topicId = props.match.params.topicId;
                    const newContent = {
                        title: values.title,
                        text: values.text
                    };

                    createContent(newContent, topicId)
                        .then(res => {
                            toast.notify("Content created successfully.", { position : "bottom-right"});
                            props.history.push(`/topic/${topicId}`);
                        }).catch(err => {
                            toast.notify("Topic does not exist!", { position : "bottom-right"});
                    });

                    setSubmitting(false);
                }, 400);
            }}
        >
            {({ isSubmitting }) => (
                <Form>
                    <div className="form-group row">
                        <label htmlFor="contentTitle" className="col-sm-2 col-form-label">Content Title:</label>
                        <div className="col-sm-10">
                            <Field type="text" name="title" id="contentTitle" placeholder="content title" className="form-control"/>
                            <ErrorMessage name="contentTitle" component="div" />
                        </div>
                    </div>

                    <div className="form-group row">
                        <label htmlFor="contentText" className="col-sm-2 col-form-label">Content Text: </label>
                        <div className="col-sm-10">
                            <Field name="text" component={EditorField} placeholder="Enter Content" row="20"/>
                            <ErrorMessage name="contentText" component="div" />
                        </div>
                    </div>

                    <Button variant="info" type="submit" block disabled={isSubmitting}>Create Content</Button>
                </Form>
            )}
        </Formik>
    </div>
);

export default AddContent;