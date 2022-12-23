import { useContext, useState } from "react";

import {
  ModalBody,
  ModalHeader,
  ModalTitle,
} from "react-bootstrap";
import "toastr/build/toastr.min.css";

import toastr from "../customToastr";
import Button from "../toolkit/Button";
import AppContext from "../context";
import { getToday, isBeforeToday } from "./dateUtils";
import UserSelectField from "./UserSelectField";
import { isNil } from "lodash";

const ModalBooker = ({
  desk,
  userHasBookingToday,
  status,
  hasBooking,
  handleClose,
}) => {
  const { user, selectedDate, client } = useContext(AppContext);

  const canDelete =
    ((hasBooking && desk.booking.user.id == user.id) ||
    (user.admin && hasBooking)) &&  !isBeforeToday(selectedDate, getToday());
  const canCreate =
    ((!hasBooking && !userHasBookingToday) || (!hasBooking && user.admin)) && !isBeforeToday(selectedDate, getToday());

  const [userId, setUserId] = useState(user.id);

  const createSuccess = () => {
    toastr["success"]("Booking made!");
    handleClose();
  };
  const createFailure = (error) => {
    const reason = error?.response?.data?.message || "Unable to book";
    toastr["error"](reason);
  };

  const deleteSuccess = () => {
    toastr["success"]("Booking successfully deleted");
    handleClose();
  };
  const deleteFailure = (error) => {
    const reason = error?.response?.data?.message || "Unable to delete booking";
    toastr["error"](reason);
  };

  const createBooking = () => {
    client
      .createBooking(desk.id, selectedDate)
      .then(createSuccess)
      .catch(createFailure);
  };

  const adminCreateBooking = () => {
    client
      .adminCreateBooking(userId, desk.id, selectedDate)
      .then(createSuccess)
      .catch(createFailure);
  };

  const handleBook = user.admin ? adminCreateBooking : createBooking;

  const deleteBooking = () => {
    client
      .deleteBooking(desk.booking.id, selectedDate)
      .then(deleteSuccess)
      .catch(deleteFailure);
  };

  const adminDeleteBooking = () => {
    client
      .adminDeleteBooking(desk.booking.id, selectedDate)
      .then(deleteSuccess)
      .catch(deleteFailure);
  };

  const handleDelete = user.admin ? adminDeleteBooking : deleteBooking;

  return (
    <div className="modalContainer">
      <ModalHeader closeButton className="foreground">
        <ModalTitle>Desk: {desk.deskNumber}</ModalTitle>
      </ModalHeader>
      <ModalBody className="modalCustom background">
        <p>Status: {status}</p>
        {(user.admin && !hasBooking) && <UserSelectField setUserId={setUserId} />}
        {canCreate && <Button disabled={user.admin && isNil(userId)} onClick={handleBook}>Create Booking</Button>}
        {canDelete && <Button onClick={handleDelete}>Cancel Booking</Button>}
      </ModalBody>
    </div>
  );
};

export default ModalBooker;
