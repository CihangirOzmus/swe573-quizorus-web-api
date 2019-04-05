import React, { Component } from 'react';
import './App.css';
import AppNavBar from "../common/AppNavBar";

class App extends Component {
    constructor(props){
        super(props);
        this.state = {
            isLoading: false
        }
    }
  render() {
      if(this.state.isLoading) {
          return <h1>isLoading!...</h1>
      }
    return (
      <div className="App">
        <AppNavBar/>
        <h1 className="m-5">Welcome To Quizorus</h1>
      </div>
    );
  }
}

export default App;
