import React from "react";
import { Link } from "react-router-dom";
import PageNotFound from "../app/assets/images/PageNotFound.png";
class NotFoundPage extends React.Component {
  render() {
    return (
      <div>
        <img src={PageNotFound} alt="PageNotFound" />
        <p style={{ textAlign: "center" }}>
          <Link to="/">Go to Home </Link>
        </p>
      </div>
    );
  }
}
export default NotFoundPage;
