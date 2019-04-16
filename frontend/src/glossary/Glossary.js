import React, {Component} from 'react';
import { CardDeck, Card, Form, FormControl, Button } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import { API_BASE_URL } from '../constants';
import axios from 'axios';

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

    loadTopicList(page, size){
        console.log("topics loaded!..")
        let url = API_BASE_URL + "/topics?page=" + page + "&size=" + size;
        // axios.get(url).then(res => {
        //     console.log(res.data)
        //     this.setState({topics: [...this.state.topics, res.data.content]})
        // })
    }

    handleLoadMore(){
        console.log("more topics loaded!..")
    }

    componentDidMount() {
        this.loadTopicList(this.state.page, this.state.size);
    }

    render(){
        if (this.state.isLoading) {
            return <h1>isLoading!...</h1>
        }

        return (
            <div>
                <Form inline className="row justify-content-center m-5">
                    <FormControl type="text" placeholder="Search" className="mr-sm-2" />
                    <Button variant="outline-info">Search</Button>
                </Form>
                {this.state.topics.content}
                <CardDeck>
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

export default withRouter(Glossary);