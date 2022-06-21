import { configureStore } from "@reduxjs/toolkit";

import loginReducer from "../features/login/LoginSlice";
import { baseApi } from "../api/baseApi";
import { rtkQueryErrorLogger } from "../app/rtkQueryErrorHandler";
import theme from "../features/theme/themeSlice";

export const store = configureStore({
  reducer: {
    theme: theme,
    login: loginReducer,
    [baseApi.reducerPath]: baseApi.reducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(baseApi.middleware).concat(rtkQueryErrorLogger),
});
