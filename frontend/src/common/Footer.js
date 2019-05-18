import React, { Component } from 'react';

class Footer extends Component {

    render() {
        return (
            <React.Fragment>
                <div className="myFooter">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-6 text-left">
                                Made by Cihangir Ozmus
                            </div>
                            <div className="col-md-6 text-right">
                                Bogazici University &copy; 2019
                            </div>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

export default Footer;