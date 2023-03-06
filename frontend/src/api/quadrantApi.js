import { baseApi } from "./baseApi";

export const quadrantApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    getQuadrantsByScale: build.query({
      query: (payload) => ({
        url: "/quadrants?scale=" + payload.scale,
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getQuadrantById: build.query({
      query: (payload) => ({
        url: "/quadrants/" + payload.quadrantId,
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

export const { useGetQuadrantsByScaleQuery, useGetQuadrantByIdQuery } =
  quadrantApi;
