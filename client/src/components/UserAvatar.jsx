import { useContext } from "react";
import AppContext from "../context";
import { Avatar } from "@mui/material";

const UserAvatar = ({ avatarId, rotation, border, height, width }) => {
  const { baseUrl } = useContext(AppContext);
  return (
    <Avatar sx={{height:`${height}`, width:`${width}`}} src={`${baseUrl}/avatar/${avatarId}`} style={{ transform: `rotate(${rotation}deg)`, border: `3px ${border} solid` }}/>
  )

};

export default UserAvatar;