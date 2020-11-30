
import React, { useState, useEffect } from "react";

import Header from "../components/header";
import Main from "../containers/main";
import { api } from "../api";

import "../scss/app.scss";


function App() {
    const [loading, setLoading] = useState(api.loading);

    useEffect(() => {
        api.on("loading", setLoading);
        setLoading(api.loading);

        return () => api.removeListener("loading", setLoading);
    }, [setLoading]);

    return (
        <div className="app">
            <Header loading={loading} />
            <div className="main">
                <Main />
            </div>
        </div>
    );
}


export default App;
