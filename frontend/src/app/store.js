import { configureStore } from "@reduxjs/toolkit";

import loginReducer from "../features/login/LoginSlice";
import { userApi } from "../api/userApi";
import { rtkQueryErrorLogger } from "../app/rtkQueryErrorHandler";

export const store = configureStore({
  reducer: {
    login: loginReducer,
    [userApi.reducerPath]: userApi.reducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(userApi.middleware).concat(rtkQueryErrorLogger),
});
