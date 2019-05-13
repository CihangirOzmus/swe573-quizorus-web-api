import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHashtag } from '@fortawesome/free-solid-svg-icons'

export class WikiLabel extends Component {
    render() {
        const { wiki } = this.props;
        return (
            <a href={wiki} target="_blank" rel="noopener noreferrer" className="badge badge-pill badge-info">
                <FontAwesomeIcon icon={faHashtag} /> {wiki.substring(wiki.indexOf("Q"), wiki.length)}
            </a>
        )

    }
}

export class WikiLabels extends Component {
    render() {
        const wikiData = this.props.wikis
        return (
            <React.Fragment>
                {
                    wikiData && (
                        <p className="card-text text-justify">
                            {wikiData.map((wiki, idx) => {
                                return <WikiLabel key={idx} wiki={wiki} />
                            })}
                        </p>
                    )
                }
            </React.Fragment>
        )
    }
}