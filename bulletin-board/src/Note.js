import React, { Component } from 'react';
import {FaPencilAlt, FaTrashAlt, FaSave} from 'react-icons/fa'

class Note extends Component {
  constructor(props) {
    super(props)
    this.state = {
      editing: false
    }
    this.edit = this.edit.bind(this)
    this.remove = this.remove.bind(this)
    this.save = this.save.bind(this)
    this.renderDisplay = this.renderDisplay.bind(this)
    this.renderForm = this.renderForm.bind(this)
    this.randomBetween = this.randomBetween.bind(this)
  }

  // Define the location and rotation of each note before mounting
  componentWillMount() {
    this.style = {
      right: this.randomBetween(0, window.innerWidth - 150, 'px'),
      top: this.randomBetween(0, window.innerHeight - 150, 'px'),
      transform: `rotate(${this.randomBetween(-25, 25, 'deg')})`
    }
  }

  // Define the behaviour after rendering
  componentDidUpdate() {
    let textArea;
    if(this.state.editing) {
      textArea = this._newText
      textArea.focus();
      textArea.select();
    }
  }

  // Should use build-in PureComponent
  // https://reactjs.org/docs/react-component.html#shouldcomponentupdate
  shouldComponentUpdate(nextProps, nextState) {
    return (
      this.props.children !== nextProps.children || this.state !== nextState
    )
  }

  randomBetween(x, y, s) {
    return x + Math.ceil(Math.random() * (y-x)) + s;
  }

  edit() {
    this.setState({
      editing: true
    })
  }

  remove() {
    this.props.onRemove(this.props.id)
  }

  save(e) {
    e.preventDefault()
    this.props.onChange(this._newText.value, this.props.id)
    this.setState({
      editing: false
    })
  }

  renderForm() {
    return (
      <div className="note" style={this.style}>
        <form onSubmit={this.save}>
        {/* Should use React.createRef() instead of callback? */}
          <textarea ref={input => this._newText = input}
                    defaultValue={this.props.children}/>
          <button id="save" onClick={this.save}><FaSave /></button>
        </form>
      </div>
    )
  }

  renderDisplay() {
    return (
      <div className="note" style={this.style}>
        <p>{this.props.children}</p>
        <span>
          <button id="edit" onClick={this.edit}><FaPencilAlt /></button>
          <button id="remove" onClick={this.remove}><FaTrashAlt /></button>
        </span>
      </div>
    )
  }

  // Specify the different Virtual DOM based on the 'editing' state
  render() {
    return this.state.editing ? this.renderForm() : this.renderDisplay();
  }
}

export default Note

