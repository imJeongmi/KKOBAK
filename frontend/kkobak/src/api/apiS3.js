import axios from "axios";

import storage from "../helper/storage";

// configuration
const apiS3 = axios.create({
  baseURL: "https://kkobak.ml/api",
  headers: {
    "Content-Type": "multipart/form-data",
  },
});

apiS3.interceptors.request.use(
  config => {
    const accessToken = storage.get("accessToken");
    if (accessToken) {
      config.headers["accessToken"] = accessToken;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  },
);

export default  apiS3 ;
