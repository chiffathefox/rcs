
import React, { useState, useEffect, useCallback } from "react";

import { api } from "../api";
import Configurations from "../components/configurations";
import Attributes from "../components/attributes";
import Preview from "../components/preview";


function Main(props) {
    const [configurations, setConfigurations] = useState([]);
    const [selectedConfigurationId, setSelectedConfigurationId] =
        useState(null);

    const [selectedVersion, setSelectedVersion] = useState(null);

    const selectedConfiguration = configurations.find(
        configuration => configuration.id === selectedConfigurationId) || null;

    const handleCreateConfigurationClicked = useCallback(() => {
        const configuration = {
            id: -1,
            version: 1,
            description: "",
        };

        const newConfigurations = configurations
            .map(configuration => Object.assign({}, configuration));

        newConfigurations.push(configuration);

        setConfigurations(newConfigurations);
        setSelectedConfigurationId(configuration.id);
        setSelectedVersion(configuration);
    }, [
        configurations,
        setConfigurations,
        setSelectedConfigurationId,
        setSelectedVersion,
    ]);

    const handleSelected = useCallback(configuration => {
        setSelectedConfigurationId(configuration.id);
        setSelectedVersion(configuration);
    }, [setSelectedConfigurationId]);

    const handleAddAttribute = useCallback((name, value) => {
        const newConfigurations = configurations.map(configuration => {
            const newConfiguration = Object.assign({}, configuration);

            if (selectedConfigurationId === configuration.id) {
                newConfiguration[name] = value;
            }

            return newConfiguration;
        });

        setConfigurations(newConfigurations);
    }, [configurations, selectedConfigurationId, setConfigurations]);

    const handleDeleteAttribute = useCallback(name => {
        const newConfigurations = configurations.map(configuration => {
            const newConfiguration = Object.assign({}, configuration);

            if (selectedConfigurationId === configuration.id) {
                delete newConfiguration[name];
            }

            return newConfiguration;
        });

        setConfigurations(newConfigurations);
    }, [configurations, selectedConfigurationId, setConfigurations]);

    useEffect(() => {
        api.getConfigurations()
            .then(setConfigurations)
            .catch(api.error);
    }, [setConfigurations]);

    const handleVersion = useCallback(version => {
        api.getChangelog(selectedConfiguration.id, version)
            .then(setSelectedVersion)
            .catch(api.error);
    }, [selectedConfiguration, setSelectedVersion]);

    const handleNextVersionClick = useCallback(
        () => handleVersion(selectedVersion.version + 1),
        [handleVersion, selectedVersion]
    );

    const handlePrevVersionClick = useCallback(
        () => handleVersion(selectedVersion.version - 1),
        [handleVersion, selectedVersion]
    );

    const maxVersion =
        selectedConfiguration ? selectedConfiguration.version : 1;

    const handleRemoveClick = useCallback(() => {
        const newConfigurations = configurations
            .filter(config => config.id !== selectedConfigurationId)
            .map(configuration => Object.assign({}, configuration));

        /* XXX: */
        api.deleteConfiguration(selectedConfigurationId).catch(api.error);
        setConfigurations(newConfigurations);
        setSelectedConfigurationId(null);
        setSelectedVersion(null);
    }, [
        configurations,
        setConfigurations,
        selectedConfigurationId,
        setSelectedConfigurationId,
        setSelectedVersion
    ]);

    const attributesConfiguration = 
        selectedConfiguration && selectedVersion &&
        selectedConfiguration.version === selectedVersion.version ?
            selectedConfiguration : selectedVersion;

    const handleSaveClick = useCallback(() => {
        const apiCall = selectedConfigurationId === -1 ?
            api.postConfiguration : api.putConfiguration;

        apiCall(selectedConfiguration)
            .then(configuration => {
                const newConfigurations = configurations
                    .filter(config => config.id !== selectedConfigurationId)
                    .map(configuration => Object.assign({}, configuration));

                newConfigurations.push(configuration);

                setConfigurations(newConfigurations);
                setSelectedConfigurationId(configuration.id);
                setSelectedVersion(configuration);
            }).catch(api.error);
    }, [
        configurations,
        setConfigurations, 
        selectedConfiguration,
        selectedConfigurationId,
        setSelectedConfigurationId,
        setSelectedVersion,
    ]);

    return (
        <>
            <Configurations
                configurations={configurations}
                onCreateClicked={handleCreateConfigurationClicked}
                onSelected={handleSelected}
                selectedConfiguration={selectedConfiguration}
            />
            <Attributes
                configuration={attributesConfiguration}
                maxVersion={maxVersion}
                onAdd={handleAddAttribute}
                onDeleteAttribute={handleDeleteAttribute}
                onNextVersionClick={handleNextVersionClick}
                onPrevVersionClick={handlePrevVersionClick}
                onRemoveClick={handleRemoveClick}
                onSaveClick={handleSaveClick}
                onValueChange={handleAddAttribute}
            />
            <Preview configuration={attributesConfiguration} />
        </>
    );
}


export default Main;

