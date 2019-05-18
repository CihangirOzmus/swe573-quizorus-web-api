import React, { Component } from 'react';
import image_1 from '../img/improve1.jpg';
import { Link } from "react-router-dom";

export default class Home extends Component {

    render() {
        return (
            <React.Fragment>
                <div className="mt-5 mb-5 text-left sectionPadding">
                    <div className="container">
                        <div className="row align-items-center">
                            <div className="col-md-6">
                                <h4 className="mt-5 mb-5">This is <strong className="strong">your space</strong></h4>
                                <p>
                                    Quizorus is a free-to-use knowledge sharing platform. Find your path and start learning.
                                <br /><br />
                                    <Link className="btn btn-outline-primary btn-xl" to="/explore">Get Started</Link>

                                </p>
                            </div>
                            <div className="col-md-1"></div>
                            <div className="col-md-5">
                                <img src={image_1} className="img-fluid" alt="" />
                            </div>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        )
    }
}
