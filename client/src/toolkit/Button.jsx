import { Button as BSButton } from "react-bootstrap";

const Button = ({ small, children, ...rest }) => {
  return (
    <BSButton
      className={`smallTopMargin me-2 ${small ? "btn-sm" : "btn-lg"} btn-success`}
      {...rest}
    >
      {children}
    </BSButton>
  );
};

export default Button;