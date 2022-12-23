import { useContext } from "react";
import { Route, Routes } from "react-router-dom";

import { Container } from "react-bootstrap";
import { isEmpty } from "lodash";

import "bootstrap/dist/css/bootstrap.min.css";
import './App.css';
import AppContext from "./context";
import LoginForm from "./components/LoginForm";
import SignupForm from "./components/SignupForm";
import VerificationGateway from "./components/VerificationGateway";

const App = () => {

  const { isLoggedIn, user} = useContext(AppContext);

  return (
    <Container>
      {(isLoggedIn && !isEmpty(user)) ? (
        <VerificationGateway/>
      ) : (
        <div className="landingContainer">
          <Routes>
            <Route element={<LoginForm/>} path="/"/>
            <Route element={<SignupForm/>} path="/signup"/>
          </Routes>
        </div>
      )}
    </Container>
  );
}


export default App;
