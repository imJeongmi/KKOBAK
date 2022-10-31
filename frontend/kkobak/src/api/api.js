import axios from "axios";

import storage from "../helper/storage";

// configuration
const api = axios.create({
	baseURL: "https://kkobak.ml/api",
	headers: {
		"Content-Type": "application/json",
	},
});

api.interceptors.request.use(
	config => {
		const accessToken = storage.get("accessToken");
		if (accessToken) {
      config.headers["Authorization"] = accessToken;
    }
		return config;
	},
	error => {
		return Promise.reject(error);
	},
);

export default api;
