
import EventEmitter from "events";

import { API_URI } from "./config";


class Api extends EventEmitter {
    constructor(baseUri) {
        super();
        this._baseUri = baseUri;
        this.loading = false;

        this.on("loading", loading => this.loading = loading);
    }

    _fetch = (endpoint, params = undefined) => {
        this.emit("loading", true);

        return fetch(this._baseUri + endpoint, params).then(response => {
            this.emit("loading", false);

            return response.json();
        });
    };

    error = error => {
        this.emit("loading", false);
        console.error("API request failed:", error);
    };

    getConfigurations = () => this._fetch("configurations");

    getChangelog = (configurationId, version) => 
        this._fetch(`changelogs/${configurationId}:${version}`);

    deleteConfiguration = configurationId => 
        this._fetch(`configurations/${configurationId}`, { method: "DELETE" });

    _saveConfiguration = (configuration, method) => {
        if (Api.validateConfiguration(configuration)) {
            return this._fetch("configurations", {
                method,
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(configuration),
            });
        }

        return Promise.reject(new Error("Configuration validation failed"));
    };

    postConfiguration = configuration => 
        this._saveConfiguration(configuration, "POST");

    putConfiguration = configuration =>
        this._saveConfiguration(configuration, "PUT");

    static SERVER_FIELDS = ["id", "version"];
    static REQUIRED_FIELDS = ["description"];

    static validateAttributeName(name) {
        if (name.length < 2) {
            return false;
        }

        return true;
    }

    static validateAttributeValue(value) {
        if (value.length === 0) {
            return false;
        }

        return true;
    }

    static validateConfiguration(configuration) {
        for (const [name, value] of Object.entries(configuration)) {
            if (!this.validateAttributeName(name) ||
                !this.validateAttributeValue(value)) {

                return false;
            }
        }

        return true;
    }
}

export const api = new Api(API_URI);
export default Api;
