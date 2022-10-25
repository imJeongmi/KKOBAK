import React from "react";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Logo from "../../static/Logo.png";
import Home from "../../static/home.svg";
import Flag from "../../static/flag.svg";
import User from "../../static/user.svg";
import Settings from "../../static/settings.svg";

export default function SelectedListItem() {
  const [selectedIndex, setSelectedIndex] = React.useState(0);

  // event: React.MouseEvent<HTMLDivElement, MouseEvent>
  // index: number

  const handleListItemClick = (event, index) => {
    setSelectedIndex(index);
  };

  return (
    <Box
      sx={{
        width: "100%",
        maxWidth: 80,
        bgcolor: "background.paper",
        height: "100vh",
        borderRight: 1,
        borderColor: "#CDCDCD",
      }}
    >
      {/* 로고 위치 */}
      <List component="nav" aria-label="main" sx={{ marginBottom: "100px" }}>
        <ListItemButton
          sx={{ paddingLeft: "21px" }}
          selected={selectedIndex === 0}
          onClick={(event) => handleListItemClick(event, 0)}
        >
          <ListItemIcon>
            <img src={Logo} width={38} height={38} />
          </ListItemIcon>
        </ListItemButton>
      </List>

      {/* 화면 이동 nav 메인, 챌린지, 나의챌린지, 통계 등 */}
      <List
        component="nav"
        aria-label="secondary"
        sx={{ marginBottom: "330px" }}
      >
        <ListItemButton
          selected={selectedIndex === 1}
          onClick={(event) => handleListItemClick(event, 1)}
          sx={{ paddingLeft: "21px", marginBottom: "30px" }}
        >
          <Box>
            <ListItemIcon>
              <img src={Home} width={34} height={34} />
            </ListItemIcon>
            <ListItemText
              sx={{ paddingLeft: "5px" }}
              primary="메인"
              primaryTypographyProps={{ fontSize: "12px" }}
            />
          </Box>
        </ListItemButton>
        <ListItemButton
          selected={selectedIndex === 2}
          onClick={(event) => handleListItemClick(event, 2)}
          sx={{ paddingLeft: "21px", marginBottom: "30px" }}
        >
          <Box>
            <ListItemIcon>
              <img src={Flag} width={34} height={34} />
            </ListItemIcon>
            <ListItemText
              primary="챌린지"
              primaryTypographyProps={{ fontSize: "12px" }}
            />
          </Box>
        </ListItemButton>
        <ListItemButton
          selected={selectedIndex === 3}
          onClick={(event) => handleListItemClick(event, 3)}
          sx={{
            paddingLeft: "16px",
            paddingRight: "4px",
            marginBottom: "30px",
          }}
        >
          <Box>
            <ListItemIcon sx={{ paddingLeft: "5px" }}>
              <img src={User} width={34} height={34} />
            </ListItemIcon>
            <ListItemText
              primary="내챌린지"
              primaryTypographyProps={{ fontSize: "12px" }}
            />
          </Box>
        </ListItemButton>
      </List>
      {/* 세팅 위치 */}
      <List component="nav" aria-label="last">
        <ListItemButton
          sx={{ paddingLeft: "21px" }}
          selected={selectedIndex === 4}
          onClick={(event) => handleListItemClick(event, 4)}
        >
          <ListItemIcon>
            <img src={Settings} width={34} height={34} />
          </ListItemIcon>
        </ListItemButton>
      </List>
    </Box>
  );
}
