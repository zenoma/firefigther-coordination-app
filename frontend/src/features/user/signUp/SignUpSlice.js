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

export default signUpSlice.reducer;
