import React from 'react';
import image_1 from '../img/improve1.jpg'
import { Link } from "react-router-dom";

const Home = (props) => (
            <React.Fragment>
                <div className="mt-5 mb-5 text-left sectionPadding">
                    <div className="container">
                        <div className="row align-items-center">
                            <div className="col-md-6">
                                <h4 className="mt-5 mb-5">Welcome to <strong className="strong">Quizorus</strong></h4>
                                <p>
                                    Quizorus is a free-to-use knowledge sharing platform. Find your path and start learning.
                                <br /><br />
                                    <Link className="btn btn-outline-info btn-xl" to="/login">Login to Start</Link>
                                </p>
                            </div>
                            <div className="col-md-1"></div>
                            <div className="col-md-5">
                                <img src={image_1} className="img-fluid rounded-circle" alt="" />
                            </div>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        );

export default Home;