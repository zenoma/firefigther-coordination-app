import { isFulfilled, isRejected } from "@reduxjs/toolkit";
import { toast } from "react-toastify";

export const rtkQueryErrorLogger = (api) => (next) => (action) => {
  // RTK Query uses `createAsyncThunk` from redux-toolkit under the hood, so we're able to utilize these matchers!

  //FIXME: Log a warning and show a toast! DEBUGGER UTILITIES

  
  // if (isFulfilled(action)) {
  //   console.log(action);
  // }

  if (isRejected(action)) {
    console.error(action);
  }

  return next(action);
};
