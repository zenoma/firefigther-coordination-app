import React from 'react';
import {useHistory} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';

const BackLink = () => {

    const history = useHistory();

    if (history.length <= 1) {
        return null;
    } 
    
    return (

        <button type="button" className="btn btn-link" 
            onClick={() => history.goBack()}>

            <FormattedMessage id='project.global.buttons.back'/>

        </button>

    );

};

export default BackLink;
