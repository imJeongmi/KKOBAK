import React, { useState } from "react";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Logo from "../../static/Logo.png";
import Home from "@mui/icons-material/Home";
import Flag from "@mui/icons-material/Flag";
import Person from "@mui/icons-material/Person";
import Settings from "@mui/icons-material/Settings";
import AppBar from "@mui/material/AppBar";
import { Link } from "react-router-dom";

export default function SelectedListItem() {
  const [selectedIndex, setSelectedIndex] = useState(1);

  const handleListItemClick = (event, index) => {
    setSelectedIndex(index);
    // console.log(index);
  };

  return (
    <AppBar
      position="absolute"
      sx={{
        width: "100%",
        maxWidth: 80,
        bgcolor: "background.paper",
        height: "100vh",
        borderRight: 1,
        borderColor: "#CDCDCD",
        left: 0,
      }}
    >
      {/* 로고 위치 sx={{ flex: 1 }} */}
      <List
        component="nav"
        aria-label="main"
        sx={{
          flex: 1,
          "&& .Mui-selected, && .Mui-selected:hover": {
            bgcolor: "white",
            "&, & .MuiListItemIcon-root": {
              color: "#F17E7F",
            },
          },
          // hover states
          "& .MuiListItemButton-root:hover": {
            bgcolor: "white",
            "&, & .MuiListItemIcon-root": {
              color: "#F17E7F",
            },
          },
        }}
      >
        <ListItemButton
          component={Link}
          to="/setting"
          // href="/setting"
          sx={{ paddingLeft: "21px" }}
          selected={selectedIndex === 0}
          onClick={(event) => handleListItemClick(event, 0)}
        >
          <ListItemIcon>
            <img src={Logo} width={35} height={35} />
          </ListItemIcon>
        </ListItemButton>
      </List>
      <Box sx={{ flex: 2 }} />

      {/* 화면 이동 nav 메인, 챌린지, 나의챌린지, 통계 등 */}
      <List
        component="nav"
        aria-label="secondary"
        sx={{
          flex: 10, // selected and (selected + hover) states
          "&& .Mui-selected, && .Mui-selected:hover": {
            bgcolor: "white",
            "&, & .MuiListItemIcon-root": {
              color: "#F17E7F",
            },
          },
          // hover states
          "& .MuiListItemButton-root:hover": {
            bgcolor: "white",
            "&, & .MuiListItemIcon-root": {
              color: "#F17E7F",
            },
          },
        }}
      >
        <ListItemButton
          component={Link}
          to="/"
          // href="/"
          selected={selectedIndex === 1}
          onClick={(event) => handleListItemClick(event, 1)}
          sx={{ paddingLeft: "21px", marginBottom: "30px", color: "white" }}
        >
          <Box>
            <ListItemIcon sx={{ marginLeft: "3px" }}>
              {/* <img src={Home} width={28} height={28} /> */}
              <Home></Home>
            </ListItemIcon>
            <ListItemText
              sx={{ paddingLeft: "5px" }}
              primary="메인"
              primaryTypographyProps={{ fontSize: "12px" }}
            />
          </Box>
        </ListItemButton>

        <ListItemButton
          component={Link}
          to="/myChallenge"
          // href="/myChallenge"
          selected={selectedIndex === 2}
          onClick={(event) => handleListItemClick(event, 2)}
          sx={{ paddingLeft: "14px", marginBottom: "30px", color: "white" }}
        >
          <Box>
            <ListItemIcon sx={{ paddingLeft: "8px" }}>
              <Flag />
            </ListItemIcon>
            <ListItemText
              primary="내챌린지"
              primaryTypographyProps={{ fontSize: "12px" }}
            />
          </Box>
        </ListItemButton>
        {/* <ListItemButton
          component={Link}
          to="/Statistics"
          // href="/Statistics"
          selected={selectedIndex === 3}
          onClick={(event) => handleListItemClick(event, 3)}
          sx={{
            paddingLeft: "24px",
            paddingRight: "4px",
            marginBottom: "30px",
            color: "white",
          }}
        >
          <Box>
            <ListItemIcon sx={{ marginLeft: "-2px" }}>
              <Person />
            </ListItemIcon>
            <ListItemText
              primary="통계"
              primaryTypographyProps={{ fontSize: "12px" }}
            />
          </Box>
        </ListItemButton> */}
      </List>
      {/* 세팅 위치 */}
      <List
        component="nav"
        aria-label="last"
        sx={{
          flex: 1,
          "&& .Mui-selected, && .Mui-selected:hover": {
            bgcolor: "white",
            "&, & .MuiListItemIcon-root": {
              color: "#F17E7F",
            },
          },
          // hover states
          "& .MuiListItemButton-root:hover": {
            bgcolor: "white",
            "&, & .MuiListItemIcon-root": {
              color: "#F17E7F",
            },
          },
        }}
      >
        <ListItemButton
          component={Link}
          to="/setting"
          // href="/setting"
          sx={{ paddingLeft: "24px" }}
          selected={selectedIndex === 4}
          onClick={(event) => handleListItemClick(event, 4)}
        >
          <ListItemIcon>
            <Settings />
          </ListItemIcon>
        </ListItemButton>
      </List>
    </AppBar>
  );
}
