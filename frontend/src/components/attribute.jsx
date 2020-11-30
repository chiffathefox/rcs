
import React, { useCallback } from "react";
import PropTypes from "prop-types";

import Api from "../api";


const makeClassName = isValid => isValid ? "input" : "input invalid";


function Attribute(props) {
    const { 
        disabled,
        name, 
        value,
        onAdd,
        onDelete,
        onNameChange,
        onValueChange
    } = props;

    const isNameValid = Api.validateAttributeName(name);
    const isValueValid = Api.validateAttributeValue(value);
    const nameClassName = makeClassName(isNameValid);
    const valueClassName = makeClassName(isValueValid);
    const handleClick = useCallback(() => {
        if (onDelete) {
            onDelete(name, value);
        } else if (onAdd && isNameValid && isValueValid) {
            onAdd(name, value);
        }
    }, [onAdd, onDelete, name, value, isNameValid, isValueValid]);

    const handleNameChange = useCallback(
        event => onNameChange(name, event.target.value),
        [onNameChange, name]
    );

    const handleValueChange = useCallback(
        event => onValueChange(name, event.target.value),
        [onValueChange, name]
    );

    const isServerField = Api.SERVER_FIELDS.indexOf(name) !== -1;
    const buttonDisabled = !!disabled || isServerField ||
        Api.REQUIRED_FIELDS.indexOf(name) !== -1;

    const buttonClasses = ["button-icon", "fa"];

    if (onAdd) {
        buttonClasses.push("fa-plus");
    } else if (onDelete) {
        buttonClasses.push("fa-times");
    }

    const buttonClassName = buttonClasses.join(" ");

    return (
        <div className="attribute">
            <button 
                className={buttonClassName}
                disabled={buttonDisabled}
                onClick={handleClick}
                type="button"
            />
            <input
                className={nameClassName}
                disabled={!!disabled || !!onDelete}
                onChange={handleNameChange}
                placeholder="Attribute name"
                type="text"
                value={name}
            />
            <input
                className={valueClassName}
                disabled={!!disabled || isServerField}
                onChange={handleValueChange}
                placeholder="Attribute value"
                type="text"
                value={value}
            />
        </div>
    );
}


Attribute.propTypes = {
    disabled: PropTypes.bool,
    name: PropTypes.string.isRequired,
    onAdd: PropTypes.func,
    onDelete: PropTypes.func,
    onNameChange: PropTypes.func,
    onValueChange: PropTypes.func.isRequired,
    value: PropTypes.string.isRequired,
};


export default Attribute;
