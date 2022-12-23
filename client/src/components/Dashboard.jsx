import { useContext } from "react";
import { Route, Routes } from "react-router-dom";

import { Col, Container, Navbar, Row } from "react-bootstrap";

import Button from "../toolkit/Button";
import DatePicker from "./DatePicker";
import DeskViewer from "./DeskViewer";
import AppContext from "../context";
import "./Dashboard.css";
import UserAvatar from "./UserAvatar";

const Dashboard = () => {
  const { user, logout } = useContext(AppContext);
  return (
    <Container>
      <div className="topbar accent">
        <div className="userStatus">
          <Row>
            <Col className = "avatarCol">
              <UserAvatar width={"10vh"} height={"10vh"} border={"#EA7601"} avatarId={user.avatarId} />
            </Col>
            <Col>
              <Row className = "paraRow">
              <p className="mb-0" style={{wordWrap: "break"}} >{`Welcome ${user.username}!`}</p>
              </Row>
              <Row className = "buttonRow m-0">
                <Button className="logoutBtn" small onClick={logout}>Logout</Button>
              </Row>
            </Col>
          </Row>
        </div>
        <DatePicker/>
      </div>

      <Routes>
        <Route path="/" element={
          <>
            <DeskViewer />
          </>
        }></Route>
      </Routes>
    </Container>
  );
};

export default Dashboard;