import React, {Component} from "react";
import {Question} from "./LearningPath";
import {ACCESS_TOKEN, API_BASE_URL} from "../util";
import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSync} from "@fortawesome/free-solid-svg-icons";
import toast from "toasted-notes";

class Quiz extends Component {
    constructor(props){
        super(props);
        this.state = {
            questions:[]
        };
        this.loadQuestionsByContentId = this.loadQuestionsByContentId.bind(this);
        this.handleRestartQuiz = this.handleRestartQuiz.bind(this);
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

    handleRestartQuiz(){
        console.log("quiz restart");

        const url = API_BASE_URL + `/quiz/${this.props.match.params.contentId}`;

        const REQUEST_HEADERS = {
            headers: { 'content-type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) }
        };

        axios.delete(url, REQUEST_HEADERS)
            .then(res => {
                toast.notify("Quiz restarted.", { position: "bottom-right" });
            }).catch(err => {
            console.log(err)
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
                            <button onClick={this.handleRestartQuiz} className="btn btn-outline-info">
                                <FontAwesomeIcon icon={faSync} /> Restart Quiz
                            </button>
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