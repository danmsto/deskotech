import { useContext, useState } from "react";

import { Formik, Form } from "formik";
import * as Yup from 'yup';

import AppContext from "../context";
import { Alert, Button, Form as BootstrapForm } from "react-bootstrap";
import FormTextField from "./FormTextField";
import { Link, useNavigate } from "react-router-dom";
import { Image } from "react-bootstrap";

import LogInGraphic from "../media/logInGraphic.png";

const SignupForm = () => {
  const { client, setToken, setUser, setIsLoggedIn } = useContext(AppContext)
  const [error, setError] = useState(undefined);
  const navigate = useNavigate();

  const handleSubmit = (values, setSubmitting) => {
    setSubmitting(true);
    setError(undefined);
    const formData = new FormData();
    formData.append('firstName', values.firstName);
    formData.append('surname', values.surname);
    formData.append('email', values.email);
    formData.append('username', values.username);
    formData.append('password', values.password);
    formData.append('avatarFile', values.avatarFile);
    client.signup(formData)
        .then(({ data }) => {
          setToken(data.token);
          setUser(data.user);
          setIsLoggedIn(true);
          setSubmitting(false);
          navigate("/");
        })
        .catch((error) => {
          setSubmitting(false);
          const reason = error?.response?.data?.message || "Something went wrong!";
          setError(reason);
        })
  }

  return (
    <div className="islandContainer">
      <div className="graphicContainer">
        <Image className="graphic" src={LogInGraphic} alt="Loading..." />
      </div>
      <Formik
          initialValues={{
            firstName: "",
            surname: "",
            email: "",
            username: "",
            password: "",
            avatarFile: null,
          }}
          validationSchema={Yup.object().shape({
            firstName: Yup.string()
                .required("Firstname is required"),
            surname: Yup.string()
                .required("Surname is required"),
            email: Yup.string().email()
                .required("Email is required"),
            username: Yup.string()
                .required("Username is required"),
            password: Yup.string()
                .required("Password is required")
                .min(8, "Must be at least 8 characters"),
          })}
          onSubmit={(values, { setSubmitting }) => handleSubmit(values, setSubmitting)}>
        {({ isValid, isSubmitting, values, setValues, errors }) => (
            <Form className="formContainer">
              {error && (
                  <Alert variant="danger">{error}</Alert>
              )}
              <h3>Sign up for a new account</h3>
              <FormTextField
                  label="First name"
                  type="text"
                  name="firstName"
                  isInvalid={!!errors.firstName}
                  feedback={errors.firstName}
              />
              <FormTextField
                  label="Surname"
                  type="text"
                  name="surname"
                  isInvalid={!!errors.surname}
                  feedback={errors.surname}
              />
              <FormTextField
                  label="Email"
                  type="email"
                  name="email"
                  isInvalid={!!errors.email}
                  feedback={errors.email}
              />
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
              <BootstrapForm.Group
                  onChange={(e) => {
                    setValues({
                      ...values,
                      avatarFile: e.target.files[0]
                    })
                  }}
              >
                <BootstrapForm.Label>Choose Avatar Image</BootstrapForm.Label>
                <BootstrapForm.Control accept="image/jpeg, image/png" type="file" />
              </BootstrapForm.Group>
              <div className="btnContainer mediumTopMargin">
                <Button
                    disabled={!isValid || isSubmitting}
                    variant="success"
                    as="input"
                    size="lg"
                    type="submit"
                    value="Sign Up"
                />
                <Link to={"/"}>
                  <Button
                    size="lg">
                    Cancel
                  </Button>
                </Link>
              </div>
            </Form>
        )}
      </Formik>
    </div>
  );
};

export default SignupForm;