
import React, { useCallback, useState } from "react";
import PropTypes from "prop-types";

import RcsPropTypes from "../rcs-prop-types";
import Attribute from "../components/attribute";


function Attributes(props) {
    const { 
        configuration, 
        onAdd, 
        onDeleteAttribute, 
        onRemoveClick,
        onSaveClick,
        onValueChange,
        onNextVersionClick,
        onPrevVersionClick,
        maxVersion
    } = props;

    const [newAttributeName, setNewAttributeName] = useState("");
    const [newAttributeValue, setNewAttributeValue] = useState("");
    const handleNewAttributeNameChange = useCallback(
        (_, name) => setNewAttributeName(name),
        [setNewAttributeName]
    );

    const handleNewAttributeValueChange = useCallback(
        (_, value) => setNewAttributeValue(value),
        [setNewAttributeValue]
    );

    const handleAdd = useCallback((name, value) => {
        onAdd(name, value);
        setNewAttributeName("");
        setNewAttributeValue("");
    }, [onAdd, setNewAttributeName, setNewAttributeValue]);

    let contentNodes;

    if (configuration) {
        const isChangelog = configuration.version !== maxVersion;
        let newAttributeNode;

        if (!isChangelog) {
            newAttributeNode = (
                <Attribute
                    name={newAttributeName}
                    onAdd={handleAdd}
                    onNameChange={handleNewAttributeNameChange}
                    onValueChange={handleNewAttributeValueChange}
                    value={newAttributeValue}
                />
            );
        }

        const attributeNodes = Object.entries(configuration)
            .sort((a, b) => a[0].localeCompare(b[0]))
            .map(([name, value]) => (
                <Attribute
                    disabled={isChangelog}
                    key={name}
                    name={name}
                    onDelete={onDeleteAttribute}
                    onValueChange={onValueChange}
                    value={value.toString()}
                />
            ));


        contentNodes = (
            <>
                <div className="version-nav">
                    <button 
                        className="button-icon fa fa-chevron-left"
                        disabled={configuration.version <= 1}
                        onClick={onPrevVersionClick}
                        type="button"
                    />
                    <span>
                        {"ver. " + configuration.version}
                    </span>
                    <button 
                        className="button-icon fa fa-chevron-right"
                        disabled={configuration.version >= maxVersion}
                        onClick={onNextVersionClick}
                        type="button"
                    />
                </div>
                <div className="act-nav">
                    <button 
                        className="button-icon fa fa-save"
                        disabled={isChangelog}
                        onClick={onSaveClick}
                        type="button"
                    />
                    <button 
                        className="button-icon fa fa-trash"
                        disabled={isChangelog}
                        onClick={onRemoveClick}
                        type="button"
                    />
                </div>
                {newAttributeNode}
                {attributeNodes}
            </>
        );
    }

    return (
        <div className="conf-container conf-attributes">
            {contentNodes}
        </div>
    );
}


Attributes.propTypes = {
    configuration: RcsPropTypes.configuration,
    maxVersion: PropTypes.number.isRequired,
    onAdd: PropTypes.func.isRequired,
    onDeleteAttribute: PropTypes.func.isRequired,
    onNextVersionClick: PropTypes.func.isRequired,
    onPrevVersionClick: PropTypes.func.isRequired,
    onRemoveClick: PropTypes.func.isRequired,
    onSaveClick: PropTypes.func.isRequired,
    onValueChange: PropTypes.func.isRequired,
};


export default Attributes;
