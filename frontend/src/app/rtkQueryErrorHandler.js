import { isFulfilled, isRejectedWithValue } from "@reduxjs/toolkit";
import { toast } from "react-toastify";

/**
 * Log a warning and show a toast!
 */
export const rtkQueryErrorLogger = (api) => (next) => (action) => {
  // RTK Query uses `createAsyncThunk` from redux-toolkit under the hood, so we're able to utilize these matchers!

  if (isRejectedWithValue(action)) {
    toast.error(action.payload.data.globalError);
    toast.error(action.payload.data.fieldErrors[0].message);
  }

  return next(action);
};
