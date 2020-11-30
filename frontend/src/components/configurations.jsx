
import React, { useCallback } from "react";
import PropTypes from "prop-types";

import Configuration from "../components/configuration";
import RcsPropTypes from "../rcs-prop-types";


function Configurations(props) {
    const {
        configurations,
        selectedConfiguration,
        onCreateClicked,
        onSelected,
    } = props;
    const handleClick = useCallback(configuration => {
        if (selectedConfiguration === null ||
            selectedConfiguration.id !== configuration.id) {

            onSelected(configuration);
        }
    }, [selectedConfiguration, onSelected]);

    const configurationNodes = configurations.map(configuration => (
        <Configuration
            configuration={configuration}
            key={configuration.id}
            onClick={handleClick}
            selected={selectedConfiguration !== null &&
                    selectedConfiguration.id === configuration.id}
        />
    ));

    return (
        <div className="conf-container conf-list">
            <button 
                className="button-icon fa fa-plus"
                onClick={onCreateClicked}
                type="button"
            />
            {configurationNodes}
        </div>
    );
}


Configurations.propTypes = {
    configurations: PropTypes.arrayOf(RcsPropTypes.configuration),
    onCreateClicked: PropTypes.func.isRequired,
    onSelected: PropTypes.func.isRequired,
    selectedConfiguration: RcsPropTypes.configuration,
};


export default Configurations;
