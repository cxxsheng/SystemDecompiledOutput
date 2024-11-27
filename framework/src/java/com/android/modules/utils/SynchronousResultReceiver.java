package com.android.modules.utils;

/* loaded from: classes5.dex */
public final class SynchronousResultReceiver<T> implements android.os.Parcelable {
    private static final int QUEUE_THRESHOLD = 4;
    private static final java.lang.String TAG = "SynchronousResultReceiver";
    private java.util.concurrent.CompletableFuture<com.android.modules.utils.SynchronousResultReceiver.Result<T>> mFuture;
    private boolean mIsCompleted;
    private final boolean mLocal;
    com.android.modules.utils.ISynchronousResultReceiver mReceiver;
    private static final java.lang.Object sLock = new java.lang.Object();
    private static final java.util.concurrent.ConcurrentLinkedQueue<com.android.modules.utils.SynchronousResultReceiver> sAvailableReceivers = new java.util.concurrent.ConcurrentLinkedQueue<>();
    public static final android.os.Parcelable.Creator<com.android.modules.utils.SynchronousResultReceiver<?>> CREATOR = new android.os.Parcelable.Creator<com.android.modules.utils.SynchronousResultReceiver<?>>() { // from class: com.android.modules.utils.SynchronousResultReceiver.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.modules.utils.SynchronousResultReceiver<?> createFromParcel(android.os.Parcel parcel) {
            return new com.android.modules.utils.SynchronousResultReceiver<>(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.modules.utils.SynchronousResultReceiver<?>[] newArray(int i) {
            return new com.android.modules.utils.SynchronousResultReceiver[i];
        }
    };

    public static <T> com.android.modules.utils.SynchronousResultReceiver<T> get() {
        synchronized (sLock) {
            if (sAvailableReceivers.isEmpty()) {
                return new com.android.modules.utils.SynchronousResultReceiver<>();
            }
            com.android.modules.utils.SynchronousResultReceiver<T> poll = sAvailableReceivers.poll();
            poll.resetLocked();
            return poll;
        }
    }

    private SynchronousResultReceiver() {
        this.mFuture = new java.util.concurrent.CompletableFuture<>();
        this.mReceiver = null;
        this.mLocal = true;
        this.mIsCompleted = false;
    }

    private void releaseLocked() {
        this.mFuture = null;
        if (sAvailableReceivers.size() < 4) {
            sAvailableReceivers.add(this);
        }
    }

    private void resetLocked() {
        this.mFuture = new java.util.concurrent.CompletableFuture<>();
        this.mIsCompleted = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.concurrent.CompletableFuture<com.android.modules.utils.SynchronousResultReceiver.Result<T>> getFuture() {
        java.util.concurrent.CompletableFuture<com.android.modules.utils.SynchronousResultReceiver.Result<T>> completableFuture;
        synchronized (sLock) {
            completableFuture = this.mFuture;
        }
        return completableFuture;
    }

    public static class Result<T> implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<com.android.modules.utils.SynchronousResultReceiver.Result<?>> CREATOR = new android.os.Parcelable.Creator<com.android.modules.utils.SynchronousResultReceiver.Result<?>>() { // from class: com.android.modules.utils.SynchronousResultReceiver.Result.1
            @Override // android.os.Parcelable.Creator
            public com.android.modules.utils.SynchronousResultReceiver.Result<?> createFromParcel(android.os.Parcel parcel) {
                return new com.android.modules.utils.SynchronousResultReceiver.Result<>(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public com.android.modules.utils.SynchronousResultReceiver.Result<?>[] newArray(int i) {
                return new com.android.modules.utils.SynchronousResultReceiver.Result[i];
            }
        };
        private final java.lang.RuntimeException mException;
        private final T mObject;

        public Result(java.lang.RuntimeException runtimeException) {
            this.mObject = null;
            this.mException = runtimeException;
        }

        public Result(T t) {
            this.mObject = t;
            this.mException = null;
        }

        public T getValue(T t) {
            if (this.mException != null) {
                throw this.mException;
            }
            if (this.mObject == null) {
                return t;
            }
            return this.mObject;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeValue(this.mObject);
            parcel.writeValue(this.mException);
        }

        private Result(android.os.Parcel parcel) {
            this.mObject = (T) parcel.readValue(null);
            this.mException = (java.lang.RuntimeException) parcel.readValue(null);
        }
    }

    private void complete(com.android.modules.utils.SynchronousResultReceiver.Result<T> result) {
        com.android.modules.utils.ISynchronousResultReceiver iSynchronousResultReceiver;
        if (this.mIsCompleted) {
            throw new java.lang.IllegalStateException("Receiver has already been completed");
        }
        this.mIsCompleted = true;
        if (this.mLocal) {
            getFuture().complete(result);
            return;
        }
        synchronized (this) {
            iSynchronousResultReceiver = this.mReceiver;
        }
        if (iSynchronousResultReceiver != null) {
            try {
                iSynchronousResultReceiver.send(result);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failed to complete future");
            }
        }
    }

    public void send(T t) {
        complete(new com.android.modules.utils.SynchronousResultReceiver.Result<>(t));
    }

    public void propagateException(java.lang.RuntimeException runtimeException) {
        java.util.Objects.requireNonNull(runtimeException, "RuntimeException cannot be null");
        complete(new com.android.modules.utils.SynchronousResultReceiver.Result<>(runtimeException));
    }

    public com.android.modules.utils.SynchronousResultReceiver.Result<T> awaitResultNoInterrupt(java.time.Duration duration) throws java.util.concurrent.TimeoutException {
        java.util.Objects.requireNonNull(duration, "Null timeout is not allowed");
        long elapsedRealtimeNanos = android.os.SystemClock.elapsedRealtimeNanos();
        java.time.Duration duration2 = duration;
        while (!duration2.isNegative()) {
            try {
                com.android.modules.utils.SynchronousResultReceiver.Result<T> result = getFuture().get(duration2.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
                synchronized (sLock) {
                    releaseLocked();
                }
                return result;
            } catch (java.lang.InterruptedException e) {
                duration2 = duration.minus(java.time.Duration.ofNanos(android.os.SystemClock.elapsedRealtimeNanos() - elapsedRealtimeNanos));
            } catch (java.util.concurrent.ExecutionException e2) {
                throw new java.lang.AssertionError("Error receiving response", e2);
            }
        }
        synchronized (sLock) {
            releaseLocked();
        }
        throw new java.util.concurrent.TimeoutException();
    }

    private final class MyResultReceiver extends com.android.modules.utils.ISynchronousResultReceiver.Stub {
        private MyResultReceiver() {
        }

        @Override // com.android.modules.utils.ISynchronousResultReceiver
        public void send(com.android.modules.utils.SynchronousResultReceiver.Result result) {
            java.util.concurrent.CompletableFuture future = com.android.modules.utils.SynchronousResultReceiver.this.getFuture();
            if (future != null) {
                future.complete(result);
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        synchronized (this) {
            if (this.mReceiver == null) {
                this.mReceiver = new com.android.modules.utils.SynchronousResultReceiver.MyResultReceiver();
            }
            parcel.writeStrongBinder(this.mReceiver.asBinder());
        }
    }

    private SynchronousResultReceiver(android.os.Parcel parcel) {
        this.mFuture = new java.util.concurrent.CompletableFuture<>();
        this.mReceiver = null;
        this.mLocal = false;
        this.mIsCompleted = false;
        this.mReceiver = com.android.modules.utils.ISynchronousResultReceiver.Stub.asInterface(parcel.readStrongBinder());
    }
}
