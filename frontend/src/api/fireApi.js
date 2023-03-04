import { baseApi } from "./baseApi";

export const fireApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    getFires: build.query({
      query: (payload) => ({
        url: "/fire",
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

export const { useGetFiresQuery } = fireApi;
