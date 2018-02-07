'use strict';

var React = require('react');

var AssignOutputDropdown = React.createClass({
    PLACEHOLDER: "placeholder",
    getInitialState() {
        return {
            selectedOutput: this.PLACEHOLDER
        };
    },
    _formatOutput(output) {
        return <option key={output.id} value={output.id}>{output.title}</option>;
    },
    _handleUpdate(evt) {
        this.setState({selectedOutput: evt.target.value});
    },
    _handleClick(evt) {
        this.props.onSubmit(this.state.selectedOutput);
        this.setState({selectedOutput: this.PLACEHOLDER});
    },
    render() {
        var outputs = this.props.outputs;
        var outputList = (outputs.length > 0 ? outputs.map(this._formatOutput) : <option disabled>No outputs available</option>);
        return (
            <div className="output-add">
                <div className="form-inline">
                    <select value={this.state.selectedOutput} name="outputId" className="form-control" onChange={this._handleUpdate}>
                        <option value={this.PLACEHOLDER} disabled>Select existing output</option>
                        {outputList}
                    </select>
                    &nbsp;
                    <button ref="submitButton" type="button" disabled={this.state.selectedOutput === this.PLACEHOLDER}
                            id="add-existing-output" className="btn btn-success" onClick={this._handleClick}>
                        Assign existing Output
                    </button>
                </div>
            </div>
        );
    }
});
module.exports = AssignOutputDropdown;
