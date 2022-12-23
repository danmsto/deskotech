import { Modal } from "react-bootstrap";
import { useContext } from "react";
import { useState } from "react";
import { isEmpty } from "lodash";

import ModalBooker from "./ModalBooker";
import AppContext from "../context";
import UserAvatar from "./UserAvatar";

const Desk = ({ desk, userHasBookingToday, rotation }) => {
  const { user } = useContext(AppContext);
  const hasBooking = !isEmpty(desk.booking);

  const status = hasBooking
    ? "Is booked by " +
    desk.booking.user.firstName +
    " " +
    desk.booking.user.surname +
    " (" +
    desk.booking.user.email +
    ")"
    : "Available";


  const ownedByUser = (hasBooking && desk.booking.user.id === user.id) 
    ? "#EA7601" : "#323F48";


  const [show, setShow] = useState(false);
  const handleShow = () => setShow(true);
  const handleClose = () => setShow(false);

  return (
    <>
      <div className={"deskContainer clickable"} onClick={handleShow} style={{ width: "100%", height: "100%", transform: `rotate(${rotation.deg}deg)` }}>
        {desk.booking &&
          <UserAvatar className="statusFlag" width={"67%"} height={"67%"} avatarId={desk.booking.user.avatarId} rotation={rotation.deg} border={ownedByUser} />
        }
      </div>


      <Modal
        show={show}
        onHide={handleClose}
        size="lg"
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <ModalBooker
          desk={desk}
          status={status}
          userHasBookingToday={userHasBookingToday}
          hasBooking={hasBooking}
          handleClose={handleClose}
        />
      </Modal>
    </>
  );
};

export default Desk;
