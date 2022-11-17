import * as React from 'react';
import Box from '@mui/material/Box';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import Tooltip from '@mui/material/Tooltip';
import Settings from '@mui/icons-material/Settings';
import Logout from '@mui/icons-material/Logout';
import ProfileImage from './ProfileImage';
import Text from './Text';
import { useNavigate } from 'react-router-dom';
import storage from 'helper/storage';
import EditProfileModal from 'component/module/EditProfileModal';


export default function ProfileMenu({ nickName, imgurl }) {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [modalOpen, setModalOpen] = React.useState(false);
  const navigate = useNavigate();

  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  function clickLogout(e) {
    e.preventDefault();
    storage.remove("accessToken");
    navigate("/");
    window.location.reload();
  }

  function clickSetProfile(e) {
    e.preventDefault();
    setModalOpen(true)
  }

  return (
    <React.Fragment>
      <Box sx={{ display: 'flex', alignItems: 'center', textAlign: 'center', justifyContent: "flex-end" }}>
        <Tooltip title="프로필">
          <Box
            onClick={handleClick}
            aria-controls={open ? 'account-menu' : undefined}
            aria-haspopup="true"
            aria-expanded={open ? 'true' : undefined}
            sx={{ display: "flex", alignItems: "end"}}>
            <Text size="17px" weight="bold" mt="30" my="15">
              {"안녕하세요\u00A0"}
            </Text>
            <Text size="m" weight="bold" mt="30" my="15" color="blue">
              {` ${nickName}`}
            </Text>
            <Text size="17px" weight="bold" mt="30" my="15">
              님
            </Text>
            <Box sx={{mb: "10px", ml: 1 }}>
              <ProfileImage type="small" num={Number(imgurl)}></ProfileImage>
            </Box>
          </Box>
        </Tooltip>
      </Box>
      <Menu
        anchorEl={anchorEl}
        id="account-menu"
        open={open}
        onClose={handleClose}
        onClick={handleClose}
        PaperProps={{
          elevation: 0,
          sx: {
            overflow: 'visible',
            filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
            mt: 1.5,
            '& .MuiAvatar-root': {
              width: 32,
              height: 32,
              ml: -0.5,
              mr: 1,
            },
            '&:before': {
              content: '""',
              display: 'block',
              position: 'absolute',
              top: 0,
              right: 14,
              width: 10,
              height: 10,
              bgcolor: 'background.paper',
              transform: 'translateY(-50%) rotate(45deg)',
              zIndex: 0,
            },
          },
        }}
        transformOrigin={{ horizontal: 'right', vertical: 'top' }}
        anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
      >
        <MenuItem onClick={clickSetProfile}>
          <ListItemIcon>
            <Settings fontSize="small" />
          </ListItemIcon>
          프로필 변경
        </MenuItem>
        <MenuItem onClick={clickLogout}>
          <ListItemIcon>
            <Logout fontSize="small" />
          </ListItemIcon>
          로그아웃
        </MenuItem>
      </Menu>
      <EditProfileModal open={modalOpen} setOpen={setModalOpen} nickname={nickName} imgurl={imgurl}></EditProfileModal>
    </React.Fragment>
  );
}