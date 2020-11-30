
import React, { useCallback } from "react";
import PropTypes from "prop-types";

import RcsPropTypes from "../rcs-prop-types";


function Configuration(props) {
    const { configuration, onClick, selected } = props;
    const handleClick = useCallback(() => {
        onClick(configuration);
    }, [onClick, configuration]);

    const classes = ["conf-entry"];

    if (selected) {
        classes.push("selected");
    }

    const className = classes.join(" ");

    return (
        <a
            className={className}
            href={"#/" + configuration.id}
            onClick={handleClick}
        >
            <p className="id">
                {configuration.id}
            </p>
            <p>
                {configuration.description}
            </p>
        </a>
    );
}


Configuration.propTypes = {
    configuration: RcsPropTypes.configuration.isRequired,
    onClick: PropTypes.func.isRequired,
    selected: PropTypes.bool.isRequired,
};


export default Configuration;
