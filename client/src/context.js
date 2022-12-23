import { createContext } from 'react';

const AppContext = createContext({
  client: {},
  baseUrl: "",
  token: "",
  setToken: () => {},
  isLoggedIn: false,
  setIsLoggedIn: () => {},
  user: {
    admin: false
  },
  setUser: () => {},
  logout: () => {},
  desks: [],
  setDesks: () => {},
  bookings: [],
  setBookings: () => {},
  selectedDate: new Date(),
  setSelectedDate: () => {},
});

export default AppContext;
