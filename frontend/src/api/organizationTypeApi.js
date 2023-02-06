import { baseApi } from "./baseApi";

export const organizationTypeApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    getOrganizationTypes: build.query({
      query: (token) => ({
        url: "/organizationTypes",
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

export const { useGetOrganizationTypesQuery } = organizationTypeApi;
