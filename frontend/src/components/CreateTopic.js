import React, { Component } from 'react';
import { createTopic } from '../util/APIUtils';
import toast from "toasted-notes/lib/index";
import wdk from "wikidata-sdk";
import axios from "axios/index";
import { Row, Form, Col, Button } from "react-bootstrap";
import { withRouter } from "react-router-dom";
import PageHeader from "../common/PageHeader";
import Loading from '../common/Loading';
import loadingGif from '../img/loading.gif'

class CreateTopic extends Component {
    constructor(props) {
        super(props);
        this.timer = null;
        this.state = {
            title: '',
            description: '',
            imageUrl: '',
            wikiDataSearch: [],
            selectedWikis: [],
            loading: true,
            loadingWiki: false
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleTitleChange = this.handleTitleChange.bind(this);
        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
        this.handleKeywordChange = this.handleKeywordChange.bind(this);
        this.handleImageUrlChange = this.handleImageUrlChange.bind(this);
        this.addWiki = this.addWiki.bind(this);
        this.removeWiki = this.removeWiki.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();

        const newTopic = {
            title: this.state.title,
            description: this.state.description,
            wikiData: this.state.selectedWikis,
            imageUrl: this.state.imageUrl
        };

        if (this.state.selectedWikis.length === 0) {
            toast.notify(<span className="text-danger">You must select at least one Wiki.</span>, { position: "bottom-right" });
        } else {
            this.setState({ loading: true })
            createTopic(newTopic)
                .then(response => {
                    toast.notify("Topic created successfully.", { position: "bottom-right" });
                    this.props.history.push(`/${this.props.currentUser.username}/topics/created`);
                }).catch(error => {
                    if (error.status === 401) {
                        this.props.handleLogout();
                    } else {
                        this.setState({ loading: false })
                        toast.notify("Something went wrong!", { position: "bottom-right" });
                    }
                });
        }
    }

    componentDidMount() {
        this.setState({ loading: false })
    }

    handleTitleChange(event) {
        const value = event.target.value;
        this.setState({ title: value })
    }

    handleDescriptionChange(event) {
        const value = event.target.value;
        this.setState({ description: value })
    }

    handleImageUrlChange(event) {
        const value = event.target.value;
        this.setState({ imageUrl: value })
    }

    handleKeywordChange(event) {
        clearTimeout(this.timer);

        this.setState({ loadingWiki: true })

        const value = event.target.value;
        if (value !== '') {
            this.timer = setTimeout(() => {
                const url = wdk.searchEntities(value, 'en', 15, 'json');
                axios.get(url)
                    .then(response => {
                        this.setState({ loadingWiki: false });
                        if (response.data.search.length > 0) {
                            this.setState({ wikiDataSearch: response.data.search });
                            toast.notify("Found in WikiData!", { position: "bottom-right" })
                        } else {
                            toast.notify("Keyword can not found!", { position: "bottom-right" });
                        }
                    })
            }, 1000)
        } else {
            this.setState({ wikiDataSearch: [] })
        }
    }

    addWiki(wiki) {
        const { selectedWikis } = this.state;

        let match = false;

        selectedWikis.map((currentWiki, idx) => {
            if (currentWiki.id === wiki.id) {
                match = true;
                return true;
            }
            return false;
        });

        if (match) {
            this.removeWiki(wiki.id)
        } else {
            const newWiki = {
                conceptUri: wiki.concepturi,
                description: wiki.description,
                id: wiki.id,
                label: wiki.label
            };


            this.setState({
                selectedWikis: selectedWikis.concat(newWiki)
            });
        }
    }

    removeWiki(wikiId) {
        const { selectedWikis } = this.state;

        let filteredWikis = selectedWikis.filter(
            obj => obj.id !== wikiId
        );

        this.setState({
            selectedWikis: filteredWikis
        });

    }

    render() {
        const { wikiDataSearch, selectedWikis, loading, loadingWiki } = this.state;

        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>
                        <PageHeader title="Create a Topic">

                        </PageHeader>

                        <div className="sectionPadding">
                            <div className="container">
                                <div className="row">
                                    <div className="col-md-12">
                                        <Form onSubmit={this.handleSubmit}>
                                            <Form.Group className="row" >
                                                <Form.Label column sm="12">
                                                    Title
                                                </Form.Label>
                                                <Col sm="12">
                                                    <Form.Control
                                                        type="text"
                                                        placeholder="Topic Title"
                                                        onChange={this.handleTitleChange}
                                                        required
                                                    />
                                                </Col>
                                            </Form.Group>

                                            <Form.Group className="row" >
                                                <Form.Label column sm="12">
                                                    Main Image Url
                                                </Form.Label>
                                                <Col sm="12">
                                                    <Form.Control
                                                        type="text"
                                                        placeholder="Enter Image URL"
                                                        onChange={this.handleImageUrlChange}
                                                        required
                                                    />
                                                </Col>
                                            </Form.Group>

                                            <Form.Group className="row" >
                                                <Form.Label column sm="12">
                                                    Description
                                                </Form.Label>
                                                <Col sm="12">
                                                    <Form.Control
                                                        as="textarea"
                                                        rows="5"
                                                        placeholder="Short Description"
                                                        onChange={this.handleDescriptionChange}
                                                        required
                                                    />
                                                </Col>
                                            </Form.Group>

                                            {selectedWikis.length > 0 && (
                                                <div>
                                                    Added Wiki:
                                                    <ul>
                                                        {selectedWikis.map((wiki, idx) => {
                                                            return (
                                                                <li key={idx}>
                                                                    {wiki.label} - {wiki.description} <span onClick={() => this.removeWiki(wiki.id)} className="ml-2 removeWikiLabel badge badge-pill badge-danger">Remove</span>
                                                                </li>
                                                            )
                                                        })}

                                                    </ul>
                                                </div>
                                            )}


                                            <Form.Group className="row"  >
                                                <Form.Label column sm="12">
                                                    {loadingWiki ? (<span><img src={loadingGif} width="30" alt="" /> Searching WikiData...</span>) : 'Keyword'}
                                                </Form.Label>
                                                <Col sm="12">
                                                    <Form.Control
                                                        type="text"
                                                        placeholder="Wiki Keywords"
                                                        onChange={this.handleKeywordChange}
                                                    />
                                                </Col>
                                            </Form.Group>

                                            {wikiDataSearch.length > 0 && (
                                                wikiDataSearch.map((wiki, wikiIndex) => {
                                                    if (wiki.description) {
                                                        return (
                                                            <Row key={wikiIndex}
                                                                 className="border-bottom border-info p-1 m-1">
                                                                {wiki.description && (
                                                                    <React.Fragment>
                                                                        <Col md="1">
                                                                            <Form.Check
                                                                                onChange={() => this.addWiki(wiki)}
                                                                                type="checkbox"
                                                                                id="default-checkbox"
                                                                                value={wiki}
                                                                            />
                                                                        </Col>
                                                                        <Col md="9">{wiki.description}</Col>
                                                                        <Col md="2">
                                                                            <a href={wiki.concepturi} target="_blank"
                                                                               rel="noopener noreferrer">Visit</a>
                                                                        </Col>
                                                                    </React.Fragment>
                                                                )}
                                                            </Row>
                                                        )
                                                    }
                                                    return (null)
                                                })
                                            )}
                                            <Button className="mt-4" variant="success" type="submit" block>
                                                Create Topic
                                    </Button>
                                        </Form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </React.Fragment>
                )}
            </React.Fragment>
        );
    }
}

export default withRouter(CreateTopic);