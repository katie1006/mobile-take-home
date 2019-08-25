package com.katie.shla.utils;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class that serves as a union of a result value and an exception. When the download
 * task has completed, either the result value or exception can be a non-null value.
 * This allows you to pass exceptions to the UI thread that were thrown during doInBackground().
 */
public class AsyncResult<T> {
    public final List<T> results = new ArrayList<>();
    @Nullable
    public Exception exception;
}
