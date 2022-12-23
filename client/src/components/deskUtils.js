import { isEmpty } from "lodash";

export const checkUserHasBooking = (user, desks) => desks
    .filter((desk) => !isEmpty(desk.booking))
    .some((desk) => desk.booking.user.id === user.id);
