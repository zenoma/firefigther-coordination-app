import { baseApi } from "./baseApi";

export const vehicleApi = baseApi.injectEndpoints({
  endpoints: (build) => ({
    getVehicles: build.query({
      query: (payload) => ({
        url: "/vehicles",
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getVehiclesByOrganizationId: build.query({
      query: (payload) => ({
        url: "/vehicles?organizationId=" + payload.organizationId,
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    getVehiclesById: build.query({
      query: (payload) => ({
        url: "/vehicles/" + payload.vehicleId,
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    createVehicle: build.mutation({
      query: (payload) => ({
        url: "/vehicles/",
        method: "POST",
        body: {
          vehiclePlate: payload.vehiclePlate,
          type: payload.type,
          organizationId: payload.organizationId,
        },
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    updateVehicle: build.mutation({
      query: (payload) => ({
        url: "/vehicles/" + payload.vehicleId,
        method: "PUT",
        body: {
          vehiclePlate: payload.vehiclePlate,
          type: payload.type,
        },
        headers: {
          Authorization: "Bearer " + payload.token,
        },
      }),
      transformResponse: (response, meta, arg) => {
        return response;
      },
    }),
    deleteVehiclebyId: build.mutation({
      query: (payload) => ({
        url: "/vehicles/" + payload.vehicleId,
        method: "DELETE",
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
  useGetVehiclesQuery,
  useGetVehiclesByOrganizationIdQuery,
  useGetVehiclesByIdQuery,
  useCreateVehicleMutation,
  useUpdateVehicleMutation,
  useDeleteVehiclebyIdMutation,
} = vehicleApi;
