import { Modal } from '@mui/material';
import { Box } from '@mui/system';
import React from 'react';

import CloseIcon from '@mui/icons-material/Close';
import Logo from "../../static/Logo.png";
import Input from 'component/atom/Input';
import TextButton from 'component/atom/TextButton';

const ModalStyle = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: '35%',
  height: '50%',
  bgcolor: 'white',
  borderRadius: '10px',
  border: 'none',
};

const LogoStyle = {
  bgcolor: "#E8F2F9",
  width: '100%',
  height: '20%',
  mt: 5,
  textAlign: 'center',
  display: 'flex',
  justifyContent: 'center',
}

export default function LoginModal() {
  const [open, setOpen] = React.useState(true);
  // const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false); 
  return (
    <Modal
      open={open}
      onClose={handleClose}>
      <Box sx={ModalStyle}>
        <CloseIcon onClick={handleClose} sx={{ m: 1, float: 'right' }}></CloseIcon>
        <Box sx={LogoStyle}>
          <img src={Logo} height="95%" object-fit="cover" />
        </Box>
        <Box sx={{ width: "80%", margin: "auto", textAlign: "center", mt: 2 }}>
          <Input type="text" placeholder="이메일"></Input>
          <Input type="password" placeholder="비밀번호"></Input>
          <TextButton size="m" onClick="">로그인</TextButton>
        </Box>
      </Box>
    </Modal>
  )
}