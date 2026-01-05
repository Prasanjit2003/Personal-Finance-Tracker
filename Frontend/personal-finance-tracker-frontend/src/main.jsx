import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";

/* Bootstrap */
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";

import "./styles/variables.css";
import "./styles/global.css";
import "./styles/light.css";
import "./styles/dark.css";


ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
