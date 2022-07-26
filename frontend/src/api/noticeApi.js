import { baseApi } from "./baseApi";

export const noticeApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    createNotice: build.mutation({
      query: (payload) => ({
        url: "/notices",
        method: "POST",
        body: {
          body: payload.body,
          coordinates: { lon: payload.coordinates.longitude, lat: payload.coordinates.latitude },
        },
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getMyNotices: build.query({
      query: (payload) => ({
        url: "/notices",
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

export const { useCreateNoticeMutation, useGetMyNoticesQuery } = noticeApi;
