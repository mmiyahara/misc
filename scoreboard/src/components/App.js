import React, { Component } from 'react';
import Header from './Header';
import Player from './Player';
import AddPlayerForm from './AddPlayerForm';

class App extends Component {
  state = {
    players: [
      {
        name: "Sato",
        id: 1,
        score: 0
      },
      {
        name: "Yanagi",
        id: 2,
        score: 0
      },
      {
        name: "Matsu",
        id: 3,
        score: 0
      },
      {
        name: "James",
        id: 4,
        score: 0
      }
    ]
  };

  // player id counter
  prevPlayerId = 4;

  handleScoreChange = (index, delta) => {
    this.setState( prevState => ({
      players: prevState.players.map( (player, i) => {
        if (index === i) {
          player.score += delta;
        }
        return player
      })
    }));
  }

  handleRemovePlayer = (id) => {
    this.setState( prevState => {
      return {
        players: prevState.players.filter(p => p.id !== id)
      };
    });
  }

  handleAddPlayer = (name) => {
    if (name === '') return;
    this.setState(prevState => {
      return {
        players: [
          ...prevState.players,
          {
            name: name,
            id: this.prevPlayerId += 1,
            score: 0
          }
        ]
      }
    })
  }

  getHighScore = () => {
    const scores = this.state.players.map(player => player.score);
    return Math.max(...scores);
  }

  render() {
    const highScore = this.getHighScore();

    return (
      <div className="scoreboard">
        <Header players={this.state.players} />
  
        {/* Players list */}
        {this.state.players.map((player, index) =>
          <Player 
            name={player.name}
            score={player.score}
            id={player.id}
            key={player.id.toString()} 
            index={index}
            isHighScore={player.score !== 0 && player.score === highScore}
            changeScore={this.handleScoreChange}
            removePlayer={this.handleRemovePlayer}
          />
        )}

        <AddPlayerForm addPlayer={this.handleAddPlayer}/>
      </div>
    );
  }
}

export default App;
