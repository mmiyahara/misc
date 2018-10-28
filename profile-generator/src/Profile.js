import React, { Component } from 'react';

class Profile extends Component {
  constructor(props) {
    super(props)
    this.state = {}
  }

  render() {
    return (
      <div className="profile-card">
        <img className="profile-img" src={this.props.pic} alt="Avatar"></img>
        <div className="profile-container">
          <h4 className="profile-name"><b>{this.props.name.first} {this.props.name.last}</b></h4>
          <p className="profile-detail">{this.props.detail}</p>
        </div>
      </div>
    )
  }
}

export default Profile;
