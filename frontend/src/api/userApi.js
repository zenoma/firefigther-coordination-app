// Import the RTK Query methods from the React-specific entry point
import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

var URL = process.env.REACT_APP_BACKEND_URL;

export const userApi = createApi({
  reducerPath: "api",
  baseQuery: fetchBaseQuery({
    baseUrl: URL + "/users",
  }),
  endpoints: (build) => ({
    login: build.mutation({
      query: (payload) => ({
        url: "/login",
        method: "POST",
        body: payload,
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    signUp: build.mutation({
      query: (payload) => ({
        url: "/signUp",
        method: "POST",
        body: payload,
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    changePassowrd: build.mutation({
      query: (payload) => ({
        url: "/" + payload.id + "/changePassword",
        method: "POST",
        body: { oldPassword: payload.oldPassword, newPassword: payload.newPassword },
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
  }),
});

export const { useLoginMutation, useSignUpMutation, useChangePassowrdMutation } = userApi;
