import React, {Component} from 'react';
import { TOPIC_LIST_SIZE } from '../constants';
import { getAllTopics, getUserCreatedTopics, getUserVotedPolls } from '../util/APIUtils';
import { CardDeck, Card, Form, FormControl, Button } from 'react-bootstrap';
import Home from '../common/Home';

class Glossary extends Component {
    constructor(props){
        super(props);
        this.state = {
            topics: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        }
        this.loadTopicList = this.loadTopicList.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
    }

    loadTopicList(page = 0, size = TOPIC_LIST_SIZE) {
        let promise;
        if(this.props.username) {
            if(this.props.type === 'USER_CREATED_POLLS') {
                promise = getUserCreatedTopics(this.props.username, page, size);
            } else if (this.props.type === 'USER_VOTED_POLLS') {
                promise = getUserVotedPolls(this.props.username, page, size);                               
            }
        } else {
            promise = getAllTopics(page, size);
        }

        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise            
        .then(response => {
            const polls = this.state.polls.slice();
            const currentVotes = this.state.currentVotes.slice();

            this.setState({
                polls: polls.concat(response.content),
                page: response.page,
                size: response.size,
                totalElements: response.totalElements,
                totalPages: response.totalPages,
                last: response.last,
                currentVotes: currentVotes.concat(Array(response.content.length).fill(null)),
                isLoading: false
            })
        }).catch(error => {
            this.setState({
                isLoading: false
            })
        });  
        
    }

    componentDidMount() {
        this.loadTopicList();
    }

    componentDidUpdate(nextProps) {
        if(this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.setState({
                polls: [],
                page: 0,
                size: 10,
                totalElements: 0,
                totalPages: 0,
                last: true,
                currentVotes: [],
                isLoading: false
            });    
            this.loadTopicList();
        }
    }

    handleLoadMore() {
        this.loadTopicList(this.state.page + 1);
    }

    render(){
        return (
            <div>
                <Home />
                <Form inline className="row justify-content-center m-5">
                    <FormControl type="text" placeholder="Search" className="mr-sm-2" />
                    <Button variant="outline-info">Search</Button>
                </Form>

                <CardDeck className="m-2">
                    <Card>
                        <Card.Img variant="top" src="holder.js/100px160" />
                        <Card.Body>
                        <Card.Title>Topic title</Card.Title>
                        <Card.Text>
                            This is a wider card with supporting text below as a natural lead-in to
                            additional content. This content is a little bit longer.
                        </Card.Text>
                        </Card.Body>
                        <Card.Footer>
                        <small className="text-muted">Created by @Username</small>
                        </Card.Footer>
                        <Button variant="info" size="sm" block className="mb-2">Enroll</Button>
                    </Card>
                    <Card>
                        <Card.Img variant="top" src="holder.js/100px160" />
                        <Card.Body>
                        <Card.Title>Topic title</Card.Title>
                        <Card.Text>
                            This card has supporting text below as a natural lead-in to additional
                            content.
                        </Card.Text>
                        </Card.Body>
                        <Card.Footer>
                        <small className="text-muted">Created by @Username</small>
                        </Card.Footer>
                        <Button variant="info" size="sm" block className="mb-2">Enroll</Button>
                    </Card>
                    <Card>
                        <Card.Img variant="top" src="holder.js/100px160" />
                        <Card.Body>
                        <Card.Title>Topic title</Card.Title>
                        <Card.Text>
                            This is a wider card with supporting text below as a natural lead-in to
                            additional content. This card has even longer content than the first to
                            show that equal height action.
                        </Card.Text>
                        </Card.Body>
                        <Card.Footer>
                        <small className="text-muted">Created by @Username</small>
                        </Card.Footer>
                        <Button variant="info" size="sm" block className="mb-2">Enroll</Button>
                    </Card>
                </CardDeck> 
            </div>
        )
    }
}

export default Glossary;