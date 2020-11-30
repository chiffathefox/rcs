
import React from "react";
import PropTypes from "prop-types";

import logo from "../images/logo.svg";
import "../scss/app.scss";

function Header(props) {
    const classes = ["logo"];

    if (props.loading) {
        classes.push("loading");
    }

    const className = classes.join(" ");

    return (
        <header>
            <div className={className}>
                <img 
                    alt="logo" 
                    src={logo}
                />
                <span>
                    {"Robot Configuration System"}
                </span>
            </div>
        </header>
    );
}


Header.propTypes = {
    loading: PropTypes.bool.isRequired,
};

export default Header;
