import { baseApi } from "./baseApi";

export const organizationApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    getOrganizations: build.query({
      query: (token) => ({
        url: "/organizations",
        headers: {
          Authorization: "Bearer " + token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getOrganizationsByOrganizationType: build.query({
      query: (payload) => ({
        url: "/organizations?organizationTypeName=" + payload.organizationTypeName,
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    createOrganization: build.mutation({
      query: (payload) => ({
        url: "/organizations/",
        method: "POST",
        body: {
          code: payload.code,
          name: payload.name,
          headquartersAddress: payload.headquartersAddress,
          coordinates: {
            lon: payload.coordinates.lng,
            lat: payload.coordinates.lat,
          },
          organizationTypeId: payload.organizationTypeId,
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

export const { useGetOrganizationsQuery, useCreateOrganizationMutation, useGetOrganizationsByOrganizationTypeQuery } =
  organizationApi;
