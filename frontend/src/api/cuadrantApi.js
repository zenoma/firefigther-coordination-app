import { baseApi } from "./baseApi";

export const cuadrantApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    getCuadrantsByScale: build.query({
      query: (payload) => ({
        url: "/cuadrants?scale=" + payload.scale,
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

export const { useGetCuadrantsByScaleQuery } = cuadrantApi;
