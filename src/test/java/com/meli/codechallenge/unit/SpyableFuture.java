package com.meli.codechallenge.unit;

import com.google.api.core.ApiFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

class SpyableFuture<V> implements ApiFuture<V> {

    private V value = null;
    private Throwable exception = null;

    public SpyableFuture(V value) {
        this.value = value;
    }

    public <V> SpyableFuture(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public V get() throws ExecutionException {
        if (exception != null) {
            throw new ExecutionException(exception);
        }
        return value;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws ExecutionException {
        return get();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void addListener(Runnable listener, Executor executor) {
        executor.execute(listener);
    }
}
