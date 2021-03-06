import React, { Component } from 'react';
import { REQUEST_HEADERS } from "../constants";
import axios from "axios/index";
import toast from "toasted-notes/lib/index";
import { Row, Tab, Button } from "react-bootstrap";
import { Link, withRouter } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome/index'
import { faPlus, faEdit } from '@fortawesome/free-solid-svg-icons/index'
import PageHeader from "../common/PageHeader";
import { PathNavigator, PathTabs } from "./LearningPath";
import { resolveEndpoint } from "../util/Helpers";
import Loading from '../common/Loading';
import { WikiLabels } from "./Wiki";

class Topic extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topic: {
                contentList: []
            },
            activeTab: '',
            loading: true
        };
        this.loadTopicById = this.loadTopicById.bind(this);
        this.togglePublish = this.togglePublish.bind(this)
    }


    loadTopicById() {
        let url = resolveEndpoint('getTopicById', [{ "slug1": this.props.match.params.topicId }]);
        axios.get(url, REQUEST_HEADERS)
            .then(res => {
                this.setState({
                    topic: res.data,
                    activeTab: res.data.contentList.length > 0 ? res.data.contentList[0].id : '',
                    loading: false
                })
            }).catch(err => {
                toast.notify("Something went wrong!", { position: "top-right" });
                console.log(err)
            });
    }

    togglePublish(topicId, publish) {

        let url = resolveEndpoint('toggleTopicPublish', []);
        let reqObj = {
            topicId: topicId,
            publish: publish
        };

        this.setState({ loading: true });

        axios.post(url, reqObj, REQUEST_HEADERS)
            .then(res => {
                this.setState({ loading: false });
                toast.notify("Status changed", { position: "bottom-right" });
                this.loadTopicById()
            }).catch(err => {
                this.setState({ loading: false });
                toast.notify("Content must have at least one question", { position: "top" });
            });
    }

    componentDidMount() {
        this.loadTopicById();
    }

    render() {

        const { topic, activeTab, loading } = this.state;
        const { editable } = this.props;

        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>
                        <PageHeader title="Details">

                        </PageHeader>

                        {
                            editable && (
                                <Button
                                    className="btn btn-success fullWidth"
                                    variant={topic.published ? 'danger' : 'warning'}
                                    onClick={() => this.togglePublish(topic.id, !topic.published)}>
                                    {topic.published ? 'Unpublish' : 'Publish This Topic'}
                                </Button>
                            )
                        }

                        <div className="bg-alt sectionPadding text-left">
                            <div className="container">
                                <div className="row">
                                    <div className="col-md-8">
                                        <h4 className="mb-4"><strong>{topic.title}</strong>
                                            {editable && (
                                                <Link className="btn btn-outline-primary btn-sm ml-2 inlineBtn" to={`/topic/${topic.id}/edit`}>
                                                    <FontAwesomeIcon icon={faEdit} />
                                                </Link>
                                            )}
                                        </h4>
                                        <p>
                                            {topic.description}
                                        </p>
                                        <WikiLabels wikis={topic.wikiData} />

                                    </div>
                                    <div className="col-md-4">
                                        <img src={topic.imageUrl} className="img-fluid" alt={topic.title} />
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="container mt-5">
                            <div className="row col-md-12 text-left">
                                <h4>
                                    Learning <strong>Path</strong>
                                    {editable && (
                                        <Link className="btn btn-success btn-sm ml-2 inlineBtn" to={`/topic/${topic.id}/content`}>
                                            <FontAwesomeIcon icon={faPlus} /> Content
                                </Link>)}

                                </h4>
                            </div>
                        </div>
                        {
                            activeTab && (
                                <Tab.Container id="list-group-tabs-example" defaultActiveKey={activeTab}>
                                    <div className="container mt-5 text-left" >
                                        <Row>
                                            <PathNavigator contents={topic.contentList} linkable={!editable} />
                                            {editable && (
                                                <PathTabs contents={topic.contentList} editable={editable} handleRefresh={() => this.loadTopicById()} />
                                            )}
                                        </Row>
                                    </div>
                                </Tab.Container>
                            )
                        }
                    </React.Fragment>
                )}
            </React.Fragment>
        )
    }
}

export default withRouter(Topic);