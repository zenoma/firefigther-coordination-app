import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

var URL = process.env.REACT_APP_BACKEND_URL;

export const baseApi = createApi({
  baseQuery: fetchBaseQuery({ baseUrl: URL }),
  endpoints: () => ({}),
});
