
import PropTypes from "prop-types";


const RcsPropTypes = {
    configuration: PropTypes.shape({
        id: PropTypes.number.isRequired,
        description: PropTypes.string.isRequired,
    }),
};

export default RcsPropTypes;
