import { baseApi } from "./baseApi";

export const fireApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    getFires: build.query({
      query: (payload) => ({
        url: "/fires",
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getFireById: build.query({
      query: (payload) => ({
        url: "/fires/" + payload.fireId,
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    createFire: build.mutation({
      query: (payload) => ({
        url: "/fires/",
        method: "POST",
        body: {
          description: payload.description,
          type: payload.type,
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

export const { useGetFiresQuery, useGetFireByIdQuery, useCreateFireMutation } =
  fireApi;
