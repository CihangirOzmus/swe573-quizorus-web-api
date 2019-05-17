import React from 'react';
import { withRouter } from 'react-router-dom';
import { Button } from 'react-bootstrap';

const NotFound = (props) => (
            <div className="page-not-found">
                <h1 className="title">
                    404
                </h1>
                <div className="desc">
                    The Page you're looking for was not found.
                </div>
                <Button variant="info" size="sm" type="submit" block className="mb-2" onClick={props.history.goBack}>Go Back</Button>
            </div>
        );

export default withRouter(NotFound);