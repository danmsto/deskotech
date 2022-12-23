import { Form, Formik } from "formik";
import * as Yup from "yup";
import FormTextField from "./FormTextField";
import { useContext, useState } from "react";
import AppContext from "../context";
import { Button } from "react-bootstrap";
import "toastr/build/toastr.min.css";

import toastr from "../customToastr";

const VerificationForm = () => {

  const { client, setUser, logout } = useContext(AppContext);

  const handleSubmit = (values, setSubmitting) => {
    client.verifyEmail(values.verificationToken)
      .then(({ data }) => {
        setUser(data)
        toastr["success"]("Email verified!");
      })
      .catch((error) => {
        setSubmitting(false);
        toastr["error"]("Cannot verify your email");
      })
  }
  const resendVerification = (e) => {
    e.preventDefault();
    client.resendVerification()
      .then(() => {
        toastr["success"]("A new verification token has been sent to your email")
      })
  }

  return (
    <Formik
      initialValues={{
        verificationToken: "",
      }}
      onSubmit={(values, { setSubmitting }) => {
        handleSubmit(values, setSubmitting)
      }}
      validationSchema={Yup.object().shape({
        verificationToken: Yup.string()
          .required("Verification token is required"),
      })}
    >
      {({ isValid, isSubmitting, errors }) => (
        <Form>
          <h3>Please verify your email address</h3>
          <FormTextField
            label="Please check your email for your verification token and enter here:"
            type="text"
            name="verificationToken"
            isInvalid={!!errors.verificationToken}
            feedback={errors.verificationToken}
          />
          <Button
            className="smallTopMargin"
            disabled={!isValid || isSubmitting}
            variant="success"
            as="input"
            size="lg"
            type="submit"
            value="Verify"
          />
          <Button
            className="smallTopMargin mx-2"
            disabled={isSubmitting}
            variant="success"
            as="input"
            size="lg"
            type="button"
            value="Resend"
            onClick={resendVerification}
          />
          <Button
            className="smallTopMargin mx-2"
            as="input"
            size="lg"
            type="button"
            value="Logout"
            onClick={logout}
          />
        </Form>
      )}
    </Formik>
  );
};

export default VerificationForm;