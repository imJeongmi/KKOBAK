// import * as React from "react";
// import Box from "@mui/material/Box";
// import List from "@mui/material/List";
// import ListItemButton from "@mui/material/ListItemButton";
// import ListItem, { listItemClasses } from "@mui/material/ListItem";
// import ListItemIcon from "@mui/material/ListItemIcon";
// import ListItemText from "@mui/material/ListItemText";
// // import { Home, User, Flag, Settings } from "lucide-react";
// import Home from "../../static/home.svg";
// import User from "../../static/user.svg";
// import Flag from "../../static/flag.svg";
// import Settings from "../../static/settings.svg";
// import Logo from "../../static/Logo.png";

// export default function SideNavBar() {
//   return (
//     <Box>
//       <Box
//         sx={{
//           width: "80px",
//           height: "100vh",
//           borderRight: 1,
//           borderColor: "#CDCDCD",
//         }}
//       >
//         <List
//           sx={{
//             // selected and (selected + hover) states
//             "&& .Mui-selected, && .Mui-selected:hover": {
//               bgcolor: "red",
//               "&, & .MuiListItemIcon-root": {
//                 color: "pink",
//               },
//             },
//             // hover states
//             "& .MuiListItemButton-root:hover": {
//               bgcolor: "orange",
//               "&, & .MuiListItemIcon-root": {
//                 color: "yellow",
//               },
//             },
//           }}
//         >
//           <ListItemButton sx={{ paddingLeft: "21px" }}>
//             <img src={Logo} width={38} height={38} />
//           </ListItemButton>
//           <ListItemButton sx={{ paddingLeft: "26px" }}>
//             <img src={Home} width={24} height={24} />
//           </ListItemButton>
//           <ListItemButton sx={{ paddingLeft: "26px" }}>
//             <img src={Flag} width={24} height={24} />
//           </ListItemButton>
//           <ListItemButton sx={{ paddingLeft: "26px" }}>
//             <img src={User} width={24} height={24} />
//           </ListItemButton>
//           <ListItemButton sx={{ paddingLeft: "26px" }}>
//             <img src={Settings} width={24} height={24} />
//           </ListItemButton>
//         </List>
//       </Box>
//     </Box>
//   );
// }
