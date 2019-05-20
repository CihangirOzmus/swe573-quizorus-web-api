import React, {Component} from 'react';
import {resolveEndpoint} from "../util/Helpers";
import {ACCESS_TOKEN} from "../constants";
import axios from "axios/index";
import toast from "toasted-notes/lib/index";

class UserProfile extends Component{
    constructor(props){
        super(props);
        this.state = {
            profile: {
                answers: [],
                createdTopics: [],
                enrolledTopics: []
            },
            input: '',
            loading: true
        };
        this.loadUserProfile = this.loadUserProfile.bind(this);
    }

    loadUserProfile(){
        let url = resolveEndpoint('getUserProfile', [{ "slug1": this.props.currentUser.username }]);

        const REQUEST_HEADERS = {
            headers: { 'content-type': 'application/json', 'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN) }
        };

        axios.get(url, REQUEST_HEADERS).then(res => {
            console.log(res.data)
            this.setState({
                profile: res.data,
                loading: false
            })
        }).catch(err => {
            toast.notify("Something went wrong!", { position: "bottom-right" });
            console.log(err)
        });
    }

    componentDidMount() {
        this.loadUserProfile();
    }

    render() {
        return(
            <React.Fragment>
                <div className="container text-center sectionPadding">
                    <h2 className="text-muted pt-5 pb-5">Welcome {this.props.currentUser.name}</h2>
                    <div className="row">
                        <div className="col-4">
                            <p className="circle-font text-muted">Created Topic</p>
                        </div>
                        <div className="col-4">
                            <p className="circle-font text-muted">Enrolled Topic</p>
                        </div>
                        <div className="col-4">
                            <p className="circle-font text-muted">Answered Question</p>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-4">
                            <p className="circle bg-success">{this.state.profile.createdTopics.length}</p>
                        </div>
                        <div className="col-4">
                            <p className="circle bg-info">{this.state.profile.enrolledTopics.length}</p>
                        </div>
                        <div className="col-4">
                            <p className="circle bg-secondary">{this.state.profile.answers.length}</p>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

export default UserProfile;