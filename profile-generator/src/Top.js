import React, { Component } from 'react';
import Profile from './Profile';
import './Top.css';

class Top extends Component {
  constructor(props) {
    super(props)
    this.state = {
      profiles: []
    }
    this.add = this.add.bind(this)
    this.fetchProfile = this.fetchProfile.bind(this)
    this.eachProfile = this.eachProfile.bind(this)
    this.nextId = this.nextId.bind(this)
  }

  async fetchProfile() {
    const profile = await fetch(`https://randomuser.me/api/`)
      .then(r => r.json())
      .then(r => {
        console.log(r)
        return {
          id: this.nextId(),
          name: r.results[0].name,
          pic: r.results[0].picture.large,
          detail: r.results[0].email
        }
      });
    return profile;
  }

  eachProfile(profile) {
    return (
      <Profile key={profile.id}
               id={profile.id}
               name={profile.name}
               pic={profile.pic}
               detail={profile.detail}>
      </Profile>
    )
  }

  nextId() {
    this.uniqueId = this.uniqueId || 0;
    return this.uniqueId++;
  }

  async add() {
    const profile = await this.fetchProfile();
    this.setState(prevState => ({
      profiles: [
        ...prevState.profiles,
        profile
      ]
    }))
  }


  render() {
    return (
      <div>
        <p>
          <h1>Profile Generator</h1>
          <button onClick={this.add}>Generate!</button>
        </p>
        {this.state.profiles.map(this.eachProfile)}
      </div>
    )
  }
}

export default Top;
