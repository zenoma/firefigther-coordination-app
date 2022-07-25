import { baseApi } from "./baseApi";

export const teamApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    getTeams: build.query({
      query: (payload) => ({
        url: "/teams?code=",
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getTeamsByOrganizationId: build.query({
      query: (payload) => ({
        url: "/teams?organizationId=" + payload.organizationId,
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getTeamsById: build.query({
      query: (payload) => ({
        url: "/teams/" + payload.teamId,
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getUsersById: build.query({
      query: (payload) => ({
        url: "/teams/" + payload.teamId + "/users",
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getMyTeam: build.query({
      query: (payload) => ({
        url: "/teams/myTeam",
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
  useGetTeamsQuery,
  useGetTeamsByOrganizationIdQuery,
  useGetTeamsByIdQuery,
  useGetUsersByIdQuery,
  useGetMyTeamQuery,
} = teamApi;
