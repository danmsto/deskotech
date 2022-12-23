import React from "react";

import { Form, InputGroup } from "react-bootstrap";
import { Field } from "formik";

const FormTextField = ({ label, name, ...rest }) => (
  <Field name={name}>
    {({ field, form }) => {
      const isValid = !form.errors[field.name];
      const isInvalid = form.touched[field.name] && !isValid;
      return (
        <Form.Group>
          <Form.Label>{label}</Form.Label>
          <InputGroup>
            <Form.Control
              {...field}
              isValid={form.touched[field.name] && isValid}
              isInvalid={isInvalid}
              feedback={form.errors[field.name]}
              {...rest}
            />

            <Form.Control.Feedback type="invalid">
              {form.errors[field.name]}
            </Form.Control.Feedback>
          </InputGroup>
        </Form.Group>
      );
    }}
  </Field>
);

FormTextField.defaultProps = {
  inputGroupPrepend: null
};

export default FormTextField;
