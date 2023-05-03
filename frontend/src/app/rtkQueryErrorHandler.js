import { isFulfilled, isRejected } from "@reduxjs/toolkit";
import { toast } from "react-toastify";

export const rtkQueryErrorLogger = (api) => (next) => (action) => {
  // RTK Query uses `createAsyncThunk` from redux-toolkit under the hood, so we're able to utilize these matchers!
  if (isRejected(action) && action.error && action.error.message) {
    const { status, data } = action.payload;


    if (data && data.globalError) {
      const errorMessage = data.globalError;
      console.error(errorMessage);
      toast.error(errorMessage);
    }

    if (status === "FETCH_ERROR") {
      console.error(action);
      toast.error("500: Internal Server Error");
    }
  }

  return next(action);
};
