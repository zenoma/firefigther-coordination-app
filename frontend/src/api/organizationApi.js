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
  }),
});

export const { useGetOrganizationsQuery } = organizationApi;
