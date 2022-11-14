import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Box } from "@mui/system";
import { Modal } from "@mui/material";

import CloseIcon from "@mui/icons-material/Close";
import Logo from "../../static/Logo.png";
import Input from "component/atom/Input";
import TextButton from "component/atom/TextButton";
import Text from "component/atom/Text";

import RefreshIcon from "static/refresh.png";
import ProfileImage from "component/atom/ProfileImage";

const ModalStyle = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "50%",
  height: "60%",
  bgcolor: "white",
  borderRadius: "10px",
  border: "none",
};

const LogoStyle = {
  bgcolor: "#E8F2F9",
  width: "100%",
  height: "15%",
  my: 5,
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
};

const IconStyle = {
  position: "absolute",
  left: "58%",
  top: "66%",
};

const BoxStyle = {
  textAlign: "center",
  margin: "2vh auto",
};

export default function EditProfileModal() {
  const navigate = useNavigate();

  const [open, setOpen] = React.useState(true);
  const handleClose = () => setOpen(false);

  const [num, setNum] = useState("");
  const [nickname, setNickname] = useState("");
  const [message, setMessage] = useState("")

  function onChangeNickname(e) {
    setNickname(e.target.value);
  }

  function onClickRefresh() {
    setNum(Math.floor(Math.random() * 10));
  }

  function onClickChange() {
    if (!nickname) {
      setMessage("닉네임을 입력해주세요.");
      setTimeout(() => setMessage(""), 1500);
      return;
    }

    // 프로필 변경 api
  }

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={ModalStyle}>
        <CloseIcon
          onClick={handleClose}
          sx={{ m: 1, float: "right" }}
        ></CloseIcon>
        <Box sx={LogoStyle}>
          <img alt="logo" src={Logo} height="80%" />
        </Box>
        
        <Box sx={BoxStyle}>
          <ProfileImage type="rounded" num={num}></ProfileImage>
          <Box sx={IconStyle} onClick={onClickRefresh}>
            <img src={RefreshIcon} alt="refresh" width="25px" />
          </Box>
        </Box>

        <Box sx={{ width: "65%", margin: "auto", textAlign: "center", mt: 2 }}>
          <Input
            type="text"
            placeholder="닉네임"
            onChange={onChangeNickname}
          ></Input>


          <Box onClick={onClickChange} sx={{ mt: "20px" }}>
            <TextButton size="m" my="15px">
              프로필 변경
            </TextButton>
          </Box>

          <Text size="s">{message}</Text>
        </Box>
      </Box>
    </Modal>
  );
}
