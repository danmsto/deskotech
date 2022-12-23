import { useContext, useState } from "react";

import { Formik, Form } from "formik";
import * as Yup from 'yup';

import AppContext from "../context";
import { Alert, Button } from "react-bootstrap";
import FormTextField from "./FormTextField";
import { Link, useNavigate } from "react-router-dom";
import { Image } from "react-bootstrap";

import SignUpGraphic from "../media/signUpGraphic.png";


const LoginForm = () => {
  const { client, setToken, setUser, setIsLoggedIn } = useContext(AppContext)
  const [error, setError] = useState(undefined);
  const navigate = useNavigate();

  const handleSubmit = (values, setSubmitting) => {
    setSubmitting(true);
    setError(undefined)
    client.login(values)
      .then(({ data }) => {
        setToken(data.token);
        setUser(data.user);
        setIsLoggedIn(true);
        setSubmitting(false);
        navigate("/");
      })
      .catch((error) => {
        setSubmitting(false);
        const reason = error?.response?.data?.message || "Incorrect login details";
        setError(reason);
      })
  }

  return (
    <div className="islandContainer">
      <Formik
        initialValues={{
          username: "",
          password: "",
        }}
        validationSchema={Yup.object().shape({
          username: Yup.string()
            .required("Username is required"),
          password: Yup.string()
            .required("Password is required"),
        })}
        onSubmit={(values, { setSubmitting }) => handleSubmit(values, setSubmitting)}>
        {({ isValid, isSubmitting, errors, }) => (
          <Form className="formContainer p-5">
            {error && (
              <Alert variant="danger">{error}</Alert>
            )}
            <h3>Login</h3>
            <FormTextField
              label="Username"
              type="text"
              name="username"
              isInvalid={!!errors.username}
              feedback={errors.username}
            />
            <FormTextField
              label="Password"
              type="password"
              name="password"
              isInvalid={!!errors.password}
              feedback={errors.password}
            />
            <div  className="btnContainer mediumTopMargin">
              <Button
                disabled={!isValid || isSubmitting}
                variant="success"
                as="input"
                size="lg"
                type="submit"
                value="Login"
              />
              <Link to={"/signup"}>
                <Button
                size="lg">
                  Sign Up
                </Button>
              </Link>
            </div>
          </Form>
        )}
      </Formik>
      <div className="graphicContainer">
        <h1 className="textContainer">deskotech</h1>
        <Image className="graphic" src={SignUpGraphic} alt="Loading..." />
      </div>
    </div>
  );
};

export default LoginForm;