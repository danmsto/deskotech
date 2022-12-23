import { useState } from "react";
import { useNavigate } from "react-router-dom";

import axios from "axios";
import { isEmpty } from "lodash";

import App from "./App";
import AppContext from "./context";
import toastr from "./customToastr";
import { getDateObject } from "./components/dateUtils";

const AxiosClient = () => {
  const baseUrl =
    !process.env.NODE_ENV || process.env.NODE_ENV === "development"
      ? "http://localhost:8080/api"
      : "";

  const [token, setToken] = useState(undefined);
  const [user, setUser] = useState(undefined);
  const [desks, setDesks] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [selectedDate, setSelectedDate] = useState(new Date());
  const navigate = useNavigate();

  const logout = () => {
    setToken(undefined);
    setIsLoggedIn(false);
    navigate("/");
  };

  const apiCall = (method, url, data, rest = {}) => {
    return axios({
      method,
      url: `${baseUrl}${url}`,
      data,
      headers: {
        authorization: !isEmpty(token) ? `Bearer ${token}` : null,
      },
      ...rest,
    }).catch((error) => {
      if (error.response.status === 401 && isLoggedIn) {
        logout();
        toastr["error"]("Your login has expired");
      } else {
        throw error;
      }
    });
  };

  const login = ({ username, password }) => {
    return apiCall("post", "/auth/login", {}, { auth: { username, password } });
  };

  const signup = (signupFormData) => {
    return axios({
      method: "post",
      url: `${baseUrl}/auth/signup`,
      headers: {
        "content-type": "multipart/form-data",
        authorization: !isEmpty(token) ? `Bearer ${token}` : null,
      },
      data: signupFormData,
    });
  };

  const getDesks = () => {
    return apiCall("get", "/desks", {}, {}).then(
      ({ data }) =>
        new Promise((resolve) => {
          setDesks(data);
          resolve();
        })
    );
  };

  const getDesksWithBookings = (dateValue) => {
    const date = getDateObject(dateValue);
    return apiCall(
      "get",
      "/desks/with-bookings",
      {},
      { params: { ...date } }
    ).then(
      ({ data }) =>
        new Promise((resolve) => {
          setDesks(data);
          resolve();
        })
    );
  };

  const getUsers = () => {
    return apiCall('get', '/users')
  }

  const createBooking = (deskId, dateValue) => {
    const date = getDateObject(dateValue);
    return apiCall("post", "/bookings", { deskId, ...date }, {}).then(() =>
      getDesksWithBookings(dateValue)
    );
  };

  const deleteBooking = (bookingId, dateValue) => {
    return apiCall("delete", `/bookings/${bookingId}`, {}).then(() =>
      getDesksWithBookings(dateValue)
    );
  };

  const adminCreateBooking = (userId, deskId, dateValue) => {
    const date = getDateObject(dateValue);

    return apiCall(
      "post",
      "/admin/bookings",
      { userId, deskId, ...date },
      {}
    ).then(() => getDesksWithBookings(dateValue));
  };

  const adminDeleteBooking = (bookingId, dateValue) => {
    return apiCall("delete", `/admin/bookings/${bookingId}`, {}).then(() =>
      getDesksWithBookings(dateValue)
    );
  };

  const verifyEmail = (verifyToken) => {
    return apiCall("get", `/auth/verify/${verifyToken}`, {})
  }

  const resendVerification = () => {
    return apiCall("post", `/auth/verify/newtoken`, {})
  }

  const client = {
    apiCall,
    login,
    signup,
    getDesks,
    getDesksWithBookings,
    createBooking,
    deleteBooking,
    adminCreateBooking,
    adminDeleteBooking,
    getUsers,
    verifyEmail,
    resendVerification,
  };

  return (
    <AppContext.Provider
      value={{
        client,
        logout,
        baseUrl,
        token,
        setToken,
        isLoggedIn,
        setIsLoggedIn,
        user,
        setUser,
        desks,
        setDesks,
        bookings,
        setBookings,
        selectedDate,
        setSelectedDate,
      }}
    >
      <App />
    </AppContext.Provider>
  );
};

export default AxiosClient;
