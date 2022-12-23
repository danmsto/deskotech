import { useContext } from "react";
import AppContext from "../context";
import VerificationForm from "./VerificationForm";
import Dashboard from "./Dashboard";
import { Box } from "@mui/material";

const VerificationGateway = () => {

  const { user } = useContext(AppContext)

  if (!user.verified) {
    return (
      <Box pt={5}>
        <VerificationForm />
      </Box>
    )
  }
  return <Dashboard />

}

export default VerificationGateway;