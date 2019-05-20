import React, { Component } from 'react'

class PageHeader extends Component {

    render() {
        const props = this.props;
        return (
            <div className="pageHeader text-center">
                <div className="container">
                    {props.title}
                </div>
            </div>
        )

    }
}

export default PageHeader;