import * as React from "react";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import { Home, User, Flag, Settings } from "lucide-react";
import Logo from "../../static/Logo.png";

export default function SideNavBar() {
  return (
    <Box>
      <Box
        sx={{
          width: "80px",
          height: "100vh",
          borderRight: 1,
          borderColor: "gray",
        }}
        variant="permanent"
        anchor="left"
      >
        <List>
          <ListItem>
            <ListItemButton>
              <ListItemIcon>
                <img src={Logo} width={30} height={30} />
              </ListItemIcon>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton>
              <ListItemIcon>
                <Home color="#F17E7F" size={24} />
              </ListItemIcon>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton>
              <ListItemIcon>
                <User size={24} />
              </ListItemIcon>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton>
              <ListItemIcon>
                <Flag size={24} />
              </ListItemIcon>
            </ListItemButton>
          </ListItem>

          <ListItem>
            <ListItemButton>
              <ListItemIcon>
                <Settings size={24} />
              </ListItemIcon>
            </ListItemButton>
          </ListItem>
        </List>
      </Box>
    </Box>
  );
}
