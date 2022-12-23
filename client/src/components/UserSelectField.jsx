import { useState, useEffect, useContext } from "react";

import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";

import AppContext from "../context";
import { checkUserHasBooking } from "./deskUtils";
import { ListItem, ListItemIcon, ListItemText } from "@mui/material";
import UserAvatar from "./UserAvatar";
import "./Dashboard.css";

const UserSelectField = ({ setUserId }) => {
  const { client, user, desks} = useContext(AppContext);
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(user);

  const usersWithNoBooking = users.filter((u) => u.admin || !checkUserHasBooking(u, desks));

  useEffect(() => {
    client.getUsers().then(({ data }) => {
      setUsers(data);
    });
  }, []);

  return (
    <Autocomplete
      onChange={(e, newValue) => {
        setSelectedUser(newValue);
        setUserId(newValue.id)
      }}
      value={selectedUser}
      selectOnFocus
      clearOnBlur

      handleHomeEndKeys
      options={usersWithNoBooking}
      getOptionLabel={(option) => {
        if (option.inputValue) {
          return option.inputValue;
        }
        return `${option.firstName} ${option.surname}`;
      }}
      renderOption={(props, option) => (
        <ListItem {...props} disablePadding >
          <ListItemIcon className="avatarList">
            <UserAvatar avatarId={option.avatarId}/>
          </ListItemIcon>
          <ListItemText primary={`${option.firstName} ${option.surname}`}/>
        </ListItem>
      )}
      fullWidth
      renderInput={(params) => <TextField sx={{ input: {color: "whitesmoke"} }} {...params} label="Select User"/>}
    />
  );
};

export default UserSelectField;
