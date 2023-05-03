import { baseApi } from "./baseApi";
import i18n from "i18next";

export const noticeApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    createNotice: build.mutation({
      query: (payload) => ({
        url: `/notices?lang=${i18n.language}`,
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
        url: `/notices?lang=${i18n.language}`,
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
