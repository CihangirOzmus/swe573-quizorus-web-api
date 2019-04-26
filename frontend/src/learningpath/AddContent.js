import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import {Button} from "react-bootstrap";

const AddContent = () => (
    <div>
        <h2 className="mt-3 text-info">Content Creation</h2>
        <Formik
            initialValues={{ contentText: '', contentTitle: ''}}
            validate={values => {
                let errors = {};

                if(!values.contentText){
                    errors.contentText = 'Content Text is required';
                }

                if(!values.contentTitle){
                    errors.contentTitle = 'Content Title is required';
                }

                return errors;
            }}
            onSubmit={(values, { setSubmitting }) => {
                setTimeout(() => {
                    alert(JSON.stringify(values, null, 2));
                    setSubmitting(false);
                }, 400);
            }}
        >
            {({ isSubmitting }) => (
                <Form>
                    <div className="form-group row">
                        <label htmlFor="contentTitle" className="col-sm-2 col-form-label">Content Title:</label>
                        <div className="col-sm-10">
                            <Field type="text" name="contentTitle" id="contentTitle" placeholder="content title" className="form-control"/>
                            <ErrorMessage name="contentTitle" component="div" />
                        </div>
                    </div>

                    <div className="form-group row">
                        <label htmlFor="contentText" className="col-sm-2 col-form-label">Content Text: </label>
                        <div className="col-sm-10">
                            <Field name="contentText" component="textarea" rows="20" className="form-control" placeholder="content text"/>
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