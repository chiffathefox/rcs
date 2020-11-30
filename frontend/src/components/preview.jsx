
import React from "react";

import RcsPropTypes from "../rcs-prop-types";
import dalek from "../images/dalek.png";
import dalekMlp from "../images/dalek-mlp.png";
import maze from "../images/maze.png";
import darkly from "../images/darkly.jpg";
import arcWelding from "../images/arc-welding.jpg";
import materials from "../images/materials.jpg";
import spotWelding from "../images/spot-welding.jpg";


const IMG_SOURCE_MAP = {
    [dalek]: {
        dalek: "true",
    },
    [dalekMlp]: {
        dalek: "true",
        pony: "true",
    },
    [maze]: {
        westworld: "true",
        maze: "true",
    },
    [darkly]: {
        darkly: "true",
    },
    [arcWelding]: {
        manufacturer: "yaskawa",
        series: "motoman",
        welding: "true",
        "arc welding": "true",
    },
    [materials]: {
        manufacturer: "yaskawa",
        series: "motoman",
        welding: "false",
        "materials handling": "true",
        robot: "true",
        automation: "true",
        category: "automation",
    },
    [spotWelding]: {
        manufacturer: "yaskawa",
        series: "motoman",
        welding: "true",
        "arc welding": "false",
        "spot welding": "false",
    },

};


function Preview(props) {
    const { configuration } = props;
    let imgNode;

    if (configuration) {
        let matchedCount = 0;
        let match;

        for (const [source, values] of Object.entries(IMG_SOURCE_MAP)) {
            let count = 0;

            for (const [name, value] of Object.entries(values)) {
                count += configuration[name] === value;
            }

            if (count > matchedCount) {
                matchedCount = count;
                match = source;
            }
        }

        imgNode = (
            <img 
                alt="" 
                src={match}
            />
        );
    }

    return (
        <div className="conf-container conf-preview">
            {imgNode}
        </div>
    );
}


Preview.propTypes = {
    configuration: RcsPropTypes.configuration,
};


export default Preview;
