package com.katie.shla.utils;

import java.util.List;

public interface AsyncCallback<T> {

    /**
     * Indicates that the callback handler needs to update its appearance or information based on
     * the result of the task. Expected to be called from the main thread.
     */
    void onResult(List<T> result);

    void onError();

    /**
     * This will be called after {@link #onResult(List)} and {@link #onError()}. However, this can
     * also be called without those two methods, indicating that task was cancelled
     * intentionally because certain criteria is not met.
     */
    void onFinish();
}
