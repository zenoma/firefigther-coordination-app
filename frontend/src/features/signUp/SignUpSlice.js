import { createSlice } from "@reduxjs/toolkit";

const initialState = {};

export const signUpSlice = createSlice({
  name: "login",
  initialState,
  reducers: {
    validSignUp(state, action) {
      state.user = action.payload.user;
      state.token = action.payload.serviceToken;
    },
    logout(state) {
      state.user = "";
      state.token = "";
    },
  },
  extraReducers: {},
});

// export const selectUser = (state) => state.login.user;
// export const selectToken = (state) => state.login.token;

// export const { validLogin, logout } = signUpSlice.actions;

export default signUpSlice.reducer;
