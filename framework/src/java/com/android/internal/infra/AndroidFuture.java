package com.android.internal.infra;

/* loaded from: classes4.dex */
public class AndroidFuture<T> extends java.util.concurrent.CompletableFuture<T> implements android.os.Parcelable {
    private static final boolean DEBUG = false;
    private static android.os.Handler sMainHandler;
    private java.util.function.BiConsumer<? super T, ? super java.lang.Throwable> mListener;
    private java.util.concurrent.Executor mListenerExecutor;
    private final java.lang.Object mLock;
    private final com.android.internal.infra.IAndroidFuture mRemoteOrigin;
    private android.os.Handler mTimeoutHandler;
    private static final java.lang.String LOG_TAG = com.android.internal.infra.AndroidFuture.class.getSimpleName();
    private static final java.util.concurrent.Executor DIRECT_EXECUTOR = new android.app.PendingIntent$$ExternalSyntheticLambda0();
    private static final java.lang.StackTraceElement[] EMPTY_STACK_TRACE = new java.lang.StackTraceElement[0];
    public static final android.os.Parcelable.Creator<com.android.internal.infra.AndroidFuture> CREATOR = new android.os.Parcelable.Creator<com.android.internal.infra.AndroidFuture>() { // from class: com.android.internal.infra.AndroidFuture.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.infra.AndroidFuture createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.infra.AndroidFuture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.infra.AndroidFuture[] newArray(int i) {
            return new com.android.internal.infra.AndroidFuture[i];
        }
    };

    public AndroidFuture() {
        this.mLock = new java.lang.Object();
        this.mListenerExecutor = DIRECT_EXECUTOR;
        this.mTimeoutHandler = getMainHandler();
        this.mRemoteOrigin = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    AndroidFuture(android.os.Parcel parcel) {
        this.mLock = new java.lang.Object();
        this.mListenerExecutor = DIRECT_EXECUTOR;
        this.mTimeoutHandler = getMainHandler();
        if (parcel.readBoolean()) {
            if (parcel.readBoolean()) {
                completeExceptionally(readThrowable(parcel));
            } else {
                complete(parcel.readValue(null));
            }
            this.mRemoteOrigin = null;
            return;
        }
        this.mRemoteOrigin = com.android.internal.infra.IAndroidFuture.Stub.asInterface(parcel.readStrongBinder());
    }

    private static android.os.Handler getMainHandler() {
        if (sMainHandler == null) {
            sMainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        }
        return sMainHandler;
    }

    public static <U> com.android.internal.infra.AndroidFuture<U> completedFuture(U u) {
        com.android.internal.infra.AndroidFuture<U> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        androidFuture.complete(u);
        return androidFuture;
    }

    @Override // java.util.concurrent.CompletableFuture
    public boolean complete(T t) {
        boolean complete = super.complete(t);
        if (complete) {
            onCompleted(t, null);
        }
        return complete;
    }

    @Override // java.util.concurrent.CompletableFuture
    public boolean completeExceptionally(java.lang.Throwable th) {
        boolean completeExceptionally = super.completeExceptionally(th);
        if (completeExceptionally) {
            onCompleted(null, th);
        }
        return completeExceptionally;
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        boolean cancel = super.cancel(z);
        if (cancel) {
            try {
                get();
                throw new java.lang.IllegalStateException("Expected CancellationException");
            } catch (java.util.concurrent.CancellationException e) {
                onCompleted(null, e);
            } catch (java.lang.Throwable th) {
                throw new java.lang.IllegalStateException("Expected CancellationException", th);
            }
        }
        return cancel;
    }

    protected void onCompleted(T t, java.lang.Throwable th) {
        java.util.function.BiConsumer<? super T, ? super java.lang.Throwable> biConsumer;
        cancelTimeout();
        synchronized (this.mLock) {
            biConsumer = this.mListener;
            this.mListener = null;
        }
        if (biConsumer != null) {
            callListenerAsync(biConsumer, t, th);
        }
        if (this.mRemoteOrigin != null) {
            try {
                this.mRemoteOrigin.complete(this);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Failed to propagate completion", e);
            }
        }
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public com.android.internal.infra.AndroidFuture<T> whenComplete(java.util.function.BiConsumer<? super T, ? super java.lang.Throwable> biConsumer) {
        return whenCompleteAsync((java.util.function.BiConsumer) biConsumer, DIRECT_EXECUTOR);
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public com.android.internal.infra.AndroidFuture<T> whenCompleteAsync(final java.util.function.BiConsumer<? super T, ? super java.lang.Throwable> biConsumer, java.util.concurrent.Executor executor) {
        com.android.internal.util.Preconditions.checkNotNull(biConsumer);
        com.android.internal.util.Preconditions.checkNotNull(executor);
        synchronized (this.mLock) {
            if (!isDone()) {
                final java.util.function.BiConsumer<? super T, ? super java.lang.Throwable> biConsumer2 = this.mListener;
                if (biConsumer2 != null && executor != this.mListenerExecutor) {
                    super.whenCompleteAsync((java.util.function.BiConsumer) biConsumer, executor);
                    return this;
                }
                this.mListenerExecutor = executor;
                if (biConsumer2 != null) {
                    biConsumer = new java.util.function.BiConsumer() { // from class: com.android.internal.infra.AndroidFuture$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            com.android.internal.infra.AndroidFuture.lambda$whenCompleteAsync$0(biConsumer2, biConsumer, obj, (java.lang.Throwable) obj2);
                        }
                    };
                }
                this.mListener = biConsumer;
                return this;
            }
            T t = null;
            try {
                th = null;
                t = get();
            } catch (java.util.concurrent.ExecutionException e) {
                th = e.getCause();
            } catch (java.lang.Throwable th) {
                th = th;
            }
            callListenerAsync(biConsumer, t, th);
            return this;
        }
    }

    static /* synthetic */ void lambda$whenCompleteAsync$0(java.util.function.BiConsumer biConsumer, java.util.function.BiConsumer biConsumer2, java.lang.Object obj, java.lang.Throwable th) {
        callListener(biConsumer, obj, th);
        callListener(biConsumer2, obj, th);
    }

    private void callListenerAsync(final java.util.function.BiConsumer<? super T, ? super java.lang.Throwable> biConsumer, final T t, final java.lang.Throwable th) {
        if (this.mListenerExecutor == DIRECT_EXECUTOR) {
            callListener(biConsumer, t, th);
        } else {
            this.mListenerExecutor.execute(new java.lang.Runnable() { // from class: com.android.internal.infra.AndroidFuture$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.infra.AndroidFuture.callListener(biConsumer, t, th);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static <TT> void callListener(java.util.function.BiConsumer<? super TT, ? super java.lang.Throwable> biConsumer, TT tt, java.lang.Throwable th) {
        try {
            biConsumer.accept(tt, th);
        } catch (java.lang.Throwable th2) {
            try {
                if (th == null) {
                    biConsumer.accept(null, th2);
                } else {
                    th2.addSuppressed(th);
                    throw th2;
                }
            } catch (java.lang.Throwable th3) {
                android.util.Log.e(LOG_TAG, "Failed to call whenComplete listener. res = " + tt, th3);
            }
        }
    }

    @Override // java.util.concurrent.CompletableFuture
    public com.android.internal.infra.AndroidFuture<T> orTimeout(long j, java.util.concurrent.TimeUnit timeUnit) {
        this.mTimeoutHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.internal.infra.AndroidFuture$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.infra.AndroidFuture.this.triggerTimeout();
            }
        }, this, timeUnit.toMillis(j));
        return this;
    }

    void triggerTimeout() {
        cancelTimeout();
        if (!isDone()) {
            completeExceptionally(new java.util.concurrent.TimeoutException());
        }
    }

    public com.android.internal.infra.AndroidFuture<T> cancelTimeout() {
        this.mTimeoutHandler.removeCallbacksAndMessages(this);
        return this;
    }

    public com.android.internal.infra.AndroidFuture<T> setTimeoutHandler(android.os.Handler handler) {
        cancelTimeout();
        this.mTimeoutHandler = (android.os.Handler) com.android.internal.util.Preconditions.checkNotNull(handler);
        return this;
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public <U> com.android.internal.infra.AndroidFuture<U> thenCompose(java.util.function.Function<? super T, ? extends java.util.concurrent.CompletionStage<U>> function) {
        return thenComposeAsync((java.util.function.Function) function, DIRECT_EXECUTOR);
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public <U> com.android.internal.infra.AndroidFuture<U> thenComposeAsync(java.util.function.Function<? super T, ? extends java.util.concurrent.CompletionStage<U>> function, java.util.concurrent.Executor executor) {
        return new com.android.internal.infra.AndroidFuture.ThenComposeAsync(this, function, executor);
    }

    private static class ThenComposeAsync<T, U> extends com.android.internal.infra.AndroidFuture<U> implements java.util.function.BiConsumer<java.lang.Object, java.lang.Throwable>, java.lang.Runnable {
        private final java.util.concurrent.Executor mExecutor;
        private volatile java.util.function.Function<? super T, ? extends java.util.concurrent.CompletionStage<U>> mFn;
        private volatile T mSourceResult = null;

        ThenComposeAsync(com.android.internal.infra.AndroidFuture<T> androidFuture, java.util.function.Function<? super T, ? extends java.util.concurrent.CompletionStage<U>> function, java.util.concurrent.Executor executor) {
            this.mFn = (java.util.function.Function) com.android.internal.util.Preconditions.checkNotNull(function);
            this.mExecutor = (java.util.concurrent.Executor) com.android.internal.util.Preconditions.checkNotNull(executor);
            androidFuture.whenComplete((java.util.function.BiConsumer) this);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.function.BiConsumer
        public void accept(java.lang.Object obj, java.lang.Throwable th) {
            if (th != null) {
                completeExceptionally(th);
            } else if (this.mFn != null) {
                this.mSourceResult = obj;
                this.mExecutor.execute(this);
            } else {
                complete(obj);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                java.util.concurrent.CompletionStage completionStage = (java.util.concurrent.CompletionStage) com.android.internal.util.Preconditions.checkNotNull(this.mFn.apply(this.mSourceResult));
                this.mFn = null;
                completionStage.whenComplete(this);
            } catch (java.lang.Throwable th) {
                try {
                    completeExceptionally(th);
                } finally {
                    this.mFn = null;
                }
            }
        }
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public <U> com.android.internal.infra.AndroidFuture<U> thenApply(java.util.function.Function<? super T, ? extends U> function) {
        return thenApplyAsync((java.util.function.Function) function, DIRECT_EXECUTOR);
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public <U> com.android.internal.infra.AndroidFuture<U> thenApplyAsync(java.util.function.Function<? super T, ? extends U> function, java.util.concurrent.Executor executor) {
        return new com.android.internal.infra.AndroidFuture.ThenApplyAsync(this, function, executor);
    }

    private static class ThenApplyAsync<T, U> extends com.android.internal.infra.AndroidFuture<U> implements java.util.function.BiConsumer<T, java.lang.Throwable>, java.lang.Runnable {
        private final java.util.concurrent.Executor mExecutor;
        private final java.util.function.Function<? super T, ? extends U> mFn;
        private volatile T mSourceResult = null;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.function.BiConsumer
        public /* bridge */ /* synthetic */ void accept(java.lang.Object obj, java.lang.Throwable th) {
            accept2((com.android.internal.infra.AndroidFuture.ThenApplyAsync<T, U>) obj, th);
        }

        ThenApplyAsync(com.android.internal.infra.AndroidFuture<T> androidFuture, java.util.function.Function<? super T, ? extends U> function, java.util.concurrent.Executor executor) {
            this.mExecutor = (java.util.concurrent.Executor) com.android.internal.util.Preconditions.checkNotNull(executor);
            this.mFn = (java.util.function.Function) com.android.internal.util.Preconditions.checkNotNull(function);
            androidFuture.whenComplete((java.util.function.BiConsumer) this);
        }

        /* renamed from: accept, reason: avoid collision after fix types in other method */
        public void accept2(T t, java.lang.Throwable th) {
            if (th != null) {
                completeExceptionally(th);
            } else {
                this.mSourceResult = t;
                this.mExecutor.execute(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                complete(this.mFn.apply(this.mSourceResult));
            } catch (java.lang.Throwable th) {
                completeExceptionally(th);
            }
        }
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.CompletionStage
    public <U, V> com.android.internal.infra.AndroidFuture<V> thenCombine(java.util.concurrent.CompletionStage<? extends U> completionStage, java.util.function.BiFunction<? super T, ? super U, ? extends V> biFunction) {
        return new com.android.internal.infra.AndroidFuture.ThenCombine(this, completionStage, biFunction);
    }

    static /* synthetic */ java.lang.Object lambda$thenCombine$2(java.lang.Object obj, java.lang.Void r1) {
        return obj;
    }

    public com.android.internal.infra.AndroidFuture<T> thenCombine(java.util.concurrent.CompletionStage<java.lang.Void> completionStage) {
        return (com.android.internal.infra.AndroidFuture<T>) thenCombine((java.util.concurrent.CompletionStage) completionStage, (java.util.function.BiFunction) new java.util.function.BiFunction() { // from class: com.android.internal.infra.AndroidFuture$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                return com.android.internal.infra.AndroidFuture.lambda$thenCombine$2(obj, (java.lang.Void) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ThenCombine<T, U, V> extends com.android.internal.infra.AndroidFuture<V> implements java.util.function.BiConsumer<java.lang.Object, java.lang.Throwable> {
        private final java.util.function.BiFunction<? super T, ? super U, ? extends V> mCombineResults;
        private volatile T mResultT = null;
        private volatile java.util.concurrent.CompletionStage<? extends U> mSourceU;

        ThenCombine(java.util.concurrent.CompletableFuture<T> completableFuture, java.util.concurrent.CompletionStage<? extends U> completionStage, java.util.function.BiFunction<? super T, ? super U, ? extends V> biFunction) {
            this.mSourceU = (java.util.concurrent.CompletionStage) com.android.internal.util.Preconditions.checkNotNull(completionStage);
            this.mCombineResults = (java.util.function.BiFunction) com.android.internal.util.Preconditions.checkNotNull(biFunction);
            completableFuture.whenComplete((java.util.function.BiConsumer) this);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.function.BiConsumer
        public void accept(java.lang.Object obj, java.lang.Throwable th) {
            if (th != null) {
                completeExceptionally(th);
                return;
            }
            if (this.mSourceU != null) {
                this.mResultT = obj;
                this.mSourceU.whenComplete(new java.util.function.BiConsumer() { // from class: com.android.internal.infra.AndroidFuture$ThenCombine$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj2, java.lang.Object obj3) {
                        com.android.internal.infra.AndroidFuture.ThenCombine.this.lambda$accept$0(obj2, (java.lang.Throwable) obj3);
                    }
                });
            } else {
                try {
                    complete(this.mCombineResults.apply(this.mResultT, obj));
                } catch (java.lang.Throwable th2) {
                    completeExceptionally(th2);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$accept$0(java.lang.Object obj, java.lang.Throwable th) {
            this.mSourceU = null;
            accept(obj, th);
        }
    }

    public static <T> com.android.internal.infra.AndroidFuture<T> supply(java.util.function.Supplier<T> supplier) {
        return supplyAsync((java.util.function.Supplier) supplier, DIRECT_EXECUTOR);
    }

    public static <T> com.android.internal.infra.AndroidFuture<T> supplyAsync(java.util.function.Supplier<T> supplier, java.util.concurrent.Executor executor) {
        return new com.android.internal.infra.AndroidFuture.SupplyAsync(supplier, executor);
    }

    private static class SupplyAsync<T> extends com.android.internal.infra.AndroidFuture<T> implements java.lang.Runnable {
        private final java.util.function.Supplier<T> mSupplier;

        SupplyAsync(java.util.function.Supplier<T> supplier, java.util.concurrent.Executor executor) {
            this.mSupplier = supplier;
            executor.execute(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                complete(this.mSupplier.get());
            } catch (java.lang.Throwable th) {
                completeExceptionally(th);
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        boolean isDone = isDone();
        parcel.writeBoolean(isDone);
        if (isDone) {
            try {
                T t = get();
                parcel.writeBoolean(false);
                parcel.writeValue(t);
                return;
            } catch (java.lang.Throwable th) {
                parcel.writeBoolean(true);
                writeThrowable(parcel, unwrapExecutionException(th));
                return;
            }
        }
        parcel.writeStrongBinder(new com.android.internal.infra.IAndroidFuture.Stub() { // from class: com.android.internal.infra.AndroidFuture.1
            @Override // com.android.internal.infra.IAndroidFuture
            public void complete(com.android.internal.infra.AndroidFuture androidFuture) {
                boolean completeExceptionally;
                try {
                    completeExceptionally = com.android.internal.infra.AndroidFuture.this.complete(androidFuture.get());
                } catch (java.lang.Throwable th2) {
                    completeExceptionally = com.android.internal.infra.AndroidFuture.this.completeExceptionally(com.android.internal.infra.AndroidFuture.this.unwrapExecutionException(th2));
                }
                if (!completeExceptionally) {
                    android.util.Log.w(com.android.internal.infra.AndroidFuture.LOG_TAG, "Remote result " + androidFuture + " ignored, as local future is already completed: " + com.android.internal.infra.AndroidFuture.this);
                }
            }
        }.asBinder());
    }

    java.lang.Throwable unwrapExecutionException(java.lang.Throwable th) {
        if (!(th instanceof java.util.concurrent.ExecutionException)) {
            return th;
        }
        return th.getCause();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void writeThrowable(android.os.Parcel parcel, java.lang.Throwable th) {
        boolean z = th != 0;
        parcel.writeBoolean(z);
        if (!z) {
            return;
        }
        boolean z2 = (th instanceof android.os.Parcelable) && th.getClass().getClassLoader() == android.os.Parcelable.class.getClassLoader();
        parcel.writeBoolean(z2);
        if (z2) {
            parcel.writeParcelable((android.os.Parcelable) th, 1);
            return;
        }
        parcel.writeString(th.getClass().getName());
        parcel.writeString(th.getMessage());
        java.lang.StackTraceElement[] stackTrace = th.getStackTrace();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int min = java.lang.Math.min(stackTrace != null ? stackTrace.length : 0, 5);
        for (int i = 0; i < min; i++) {
            if (i > 0) {
                sb.append('\n');
            }
            sb.append("\tat ").append(stackTrace[i]);
        }
        parcel.writeString(sb.toString());
        writeThrowable(parcel, th.getCause());
    }

    private static java.lang.Throwable readThrowable(android.os.Parcel parcel) {
        java.lang.Throwable th;
        if (!parcel.readBoolean()) {
            return null;
        }
        if (parcel.readBoolean()) {
            return (java.lang.Throwable) parcel.readParcelable(android.os.Parcelable.class.getClassLoader());
        }
        java.lang.String readString = parcel.readString();
        java.lang.String str = parcel.readString() + '\n' + parcel.readString();
        try {
            java.lang.Class<?> cls = java.lang.Class.forName(readString, true, android.os.Parcelable.class.getClassLoader());
            if (java.lang.Throwable.class.isAssignableFrom(cls)) {
                th = (java.lang.Throwable) cls.getConstructor(java.lang.String.class).newInstance(str);
            } else {
                android.util.EventLog.writeEvent(1397638484, "186530450", -1, "");
                th = new java.lang.RuntimeException(readString + ": " + str);
            }
        } catch (java.lang.Throwable th2) {
            java.lang.RuntimeException runtimeException = new java.lang.RuntimeException(readString + ": " + str);
            runtimeException.addSuppressed(th2);
            th = runtimeException;
        }
        th.setStackTrace(EMPTY_STACK_TRACE);
        java.lang.Throwable readThrowable = readThrowable(parcel);
        if (readThrowable != null) {
            th.initCause(readThrowable);
        }
        return th;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
