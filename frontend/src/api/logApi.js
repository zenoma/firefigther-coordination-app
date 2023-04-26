import { baseApi } from "./baseApi";

export const logApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    getFireLogsByFireId: build.query({
      query: (payload) => ({
        url:
          "/logs/fires/" +
          payload.fireId +
          "?startDate=" +
          payload.startDate +
          "&endDate=" +
          payload.endDate,
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getTeamLogsByQuadrantId: build.query({
      query: (payload) => ({
        url:
          "/logs/teams?quadrantId=" +
          payload.quadrantId +
          "&startDate=" +
          payload.startDate +
          "&endDate=" +
          payload.endDate,
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getVehicleLogsByQuadrantId: build.query({
      query: (payload) => ({
        url:
          "/logs/vehicles?quadrantId=" +
          payload.quadrantId +
          "&startDate=" +
          payload.startDate +
          "&endDate=" +
          payload.endDate,
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
  useGetFireLogsByFireIdQuery,
  useGetTeamLogsByQuadrantIdQuery,
  useGetVehicleLogsByQuadrantIdQuery,
} = logApi;
