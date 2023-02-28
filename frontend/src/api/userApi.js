import { baseApi } from "./baseApi";

export const userApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    getUsers: build.query({
      query: (payload) => ({
        url: "/users/",
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    login: build.mutation({
      query: (payload) => ({
        url: "/users/login",
        method: "POST",
        body: payload,
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    loginFromToken: build.mutation({
      query: (payload) => ({
        url: "/users/loginFromServiceToken",
        method: "POST",
        headers: {
          Authorization: "Bearer " + payload.token,
        },
        body: payload,
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    signUp: build.mutation({
      query: (payload) => ({
        url: "/users/signUp",
        method: "POST",
        body: payload,
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    changePassword: build.mutation({
      query: (payload) => ({
        url: "/users/" + payload.id + "/changePassword",
        method: "POST",
        body: {
          oldPassword: payload.oldPassword,
          newPassword: payload.newPassword,
        },
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

export const {
  useGetUsersQuery,
  useLoginMutation,
  useSignUpMutation,
  useChangePasswordMutation,
  useLoginFromTokenMutation,
} = userApi;
