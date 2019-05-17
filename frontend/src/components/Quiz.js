import React, {Component} from "react";
import {Question} from "./LearningPath";
import {ACCESS_TOKEN, API_BASE_URL} from "../util";
import axios from "axios";
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSync} from "@fortawesome/free-solid-svg-icons";

class Quiz extends Component {
    constructor(props){
        super(props);
        this.state = {
            questions:[]
        };
        this.loadQuestionsByContentId = this.loadQuestionsByContentId.bind(this);
    }

    loadQuestionsByContentId(){
        const url = API_BASE_URL + `/questions/${this.props.match.params.contentId}`;

        const REQUEST_HEADERS = {
            headers: { 'content-type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) }
        };

        axios.get(url, REQUEST_HEADERS)
            .then(res => {
                this.setState({
                    questions: res.data,
                })
            }).catch(err => {
            console.log(err);
        });
    }

    componentDidMount() {
        this.loadQuestionsByContentId();
    }

    render() {
        const questions = this.state.questions;
        return (

            questions.length > 0 && (
                <React.Fragment>
                    <div className="row mt-5">
                        <div className="col-md-12 text-center">
                            <Link to="/explore" className="disabled btn btn-outline-info">
                                <FontAwesomeIcon icon={faSync} /> Restart Quiz
                            </Link>
                        </div>
                    </div>

                    <div className="container mt-5">
                        <hr/>
                        {
                            questions.map((question, idx) => {
                                return (
                                    <Question
                                        key={idx}
                                        order={idx + 1}
                                        question={question}
                                        answered={false}
                                    />
                                )
                            })
                        }
                    </div>
                </React.Fragment>
            )
        )
    }
}

export default Quiz;