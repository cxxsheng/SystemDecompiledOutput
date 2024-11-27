package com.android.internal.infra;

/* loaded from: classes4.dex */
public interface ServiceConnector<I extends android.os.IInterface> {

    @java.lang.FunctionalInterface
    public interface Job<II, R> {
        R run(II ii) throws java.lang.Exception;
    }

    com.android.internal.infra.AndroidFuture<I> connect();

    com.android.internal.infra.AndroidFuture<java.lang.Void> post(com.android.internal.infra.ServiceConnector.VoidJob<I> voidJob);

    <R> com.android.internal.infra.AndroidFuture<R> postAsync(com.android.internal.infra.ServiceConnector.Job<I, java.util.concurrent.CompletableFuture<R>> job);

    <R> com.android.internal.infra.AndroidFuture<R> postForResult(com.android.internal.infra.ServiceConnector.Job<I, R> job);

    boolean run(com.android.internal.infra.ServiceConnector.VoidJob<I> voidJob);

    void setServiceLifecycleCallbacks(com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<I> serviceLifecycleCallbacks);

    void unbind();

    @java.lang.FunctionalInterface
    public interface VoidJob<II> extends com.android.internal.infra.ServiceConnector.Job<II, java.lang.Void> {
        void runNoResult(II ii) throws java.lang.Exception;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.internal.infra.ServiceConnector.Job
        /* bridge */ /* synthetic */ default java.lang.Void run(java.lang.Object obj) throws java.lang.Exception {
            return run2((com.android.internal.infra.ServiceConnector.VoidJob<II>) obj);
        }

        @Override // com.android.internal.infra.ServiceConnector.Job
        /* renamed from: run, reason: avoid collision after fix types in other method */
        default java.lang.Void run2(II ii) throws java.lang.Exception {
            runNoResult(ii);
            return null;
        }
    }

    public interface ServiceLifecycleCallbacks<II extends android.os.IInterface> {
        default void onConnected(II ii) {
        }

        default void onDisconnected(II ii) {
        }

        default void onBinderDied() {
        }
    }

    public static class Impl<I extends android.os.IInterface> extends java.util.ArrayDeque<com.android.internal.infra.ServiceConnector.Job<I, ?>> implements com.android.internal.infra.ServiceConnector<I>, android.content.ServiceConnection, android.os.IBinder.DeathRecipient, java.lang.Runnable {
        static final boolean DEBUG = false;
        private static final long DEFAULT_DISCONNECT_TIMEOUT_MS = 15000;
        private static final long DEFAULT_REQUEST_TIMEOUT_MS = 30000;
        static final java.lang.String LOG_TAG = "ServiceConnector.Impl";
        private final java.util.function.Function<android.os.IBinder, I> mBinderAsInterface;
        private final int mBindingFlags;
        protected final android.content.Context mContext;
        private final android.content.Intent mIntent;
        private final java.util.Queue<com.android.internal.infra.ServiceConnector.Job<I, ?>> mQueue = this;
        private final java.util.List<com.android.internal.infra.ServiceConnector.Impl<I>.CompletionAwareJob<I, ?>> mUnfinishedJobs = new java.util.ArrayList();
        private final android.os.Handler mMainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        private final android.content.ServiceConnection mServiceConnection = this;
        private final java.lang.Runnable mTimeoutDisconnect = this;
        private volatile com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<I> mServiceLifecycleCallbacks = null;
        private volatile I mService = null;
        private boolean mBinding = false;
        private boolean mUnbinding = false;
        private com.android.internal.infra.ServiceConnector.Impl<I>.CompletionAwareJob<I, I> mServiceConnectionFutureCache = null;
        private final android.os.Handler mHandler = getJobHandler();
        protected final java.util.concurrent.Executor mExecutor = new android.os.HandlerExecutor(this.mHandler);

        public Impl(android.content.Context context, android.content.Intent intent, int i, int i2, java.util.function.Function<android.os.IBinder, I> function) {
            this.mContext = context.createContextAsUser(android.os.UserHandle.of(i2), 0);
            this.mIntent = intent;
            this.mBindingFlags = i;
            this.mBinderAsInterface = function;
        }

        protected android.os.Handler getJobHandler() {
            return this.mMainHandler;
        }

        protected long getAutoDisconnectTimeoutMs() {
            return DEFAULT_DISCONNECT_TIMEOUT_MS;
        }

        protected long getRequestTimeoutMs() {
            return 30000L;
        }

        protected boolean bindService(android.content.ServiceConnection serviceConnection) {
            return this.mContext.bindService(this.mIntent, this.mBindingFlags | 1, this.mExecutor, serviceConnection);
        }

        protected I binderAsInterface(android.os.IBinder iBinder) {
            return this.mBinderAsInterface.apply(iBinder);
        }

        protected void onServiceUnbound() {
        }

        private void dispatchOnServiceConnectionStatusChanged(I i, boolean z) {
            com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<I> serviceLifecycleCallbacks = this.mServiceLifecycleCallbacks;
            if (serviceLifecycleCallbacks != null) {
                if (z) {
                    serviceLifecycleCallbacks.onConnected(i);
                } else {
                    serviceLifecycleCallbacks.onDisconnected(i);
                }
            }
            onServiceConnectionStatusChanged(i, z);
        }

        protected void onServiceConnectionStatusChanged(I i, boolean z) {
        }

        @Override // com.android.internal.infra.ServiceConnector
        public boolean run(com.android.internal.infra.ServiceConnector.VoidJob<I> voidJob) {
            return enqueue(voidJob);
        }

        @Override // com.android.internal.infra.ServiceConnector
        public com.android.internal.infra.AndroidFuture<java.lang.Void> post(com.android.internal.infra.ServiceConnector.VoidJob<I> voidJob) {
            return postForResult((com.android.internal.infra.ServiceConnector.Job) voidJob);
        }

        @Override // com.android.internal.infra.ServiceConnector
        public <R> com.android.internal.infra.ServiceConnector.Impl<I>.CompletionAwareJob<I, R> postForResult(com.android.internal.infra.ServiceConnector.Job<I, R> job) {
            com.android.internal.infra.ServiceConnector.Impl<I>.CompletionAwareJob<I, R> completionAwareJob = new com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob<>();
            completionAwareJob.mDelegate = (com.android.internal.infra.ServiceConnector.Job) java.util.Objects.requireNonNull(job);
            enqueue((com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob) completionAwareJob);
            return completionAwareJob;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public <R> com.android.internal.infra.AndroidFuture<R> postAsync(com.android.internal.infra.ServiceConnector.Job<I, java.util.concurrent.CompletableFuture<R>> job) {
            com.android.internal.infra.ServiceConnector.Impl<I>.CompletionAwareJob<I, ?> completionAwareJob = new com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob<>();
            completionAwareJob.mDelegate = (com.android.internal.infra.ServiceConnector.Job) java.util.Objects.requireNonNull(job);
            completionAwareJob.mAsync = true;
            enqueue((com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob) completionAwareJob);
            return completionAwareJob;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public synchronized com.android.internal.infra.AndroidFuture<I> connect() {
            if (this.mServiceConnectionFutureCache == null) {
                this.mServiceConnectionFutureCache = new com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob<>();
                this.mServiceConnectionFutureCache.mDelegate = new com.android.internal.infra.ServiceConnector.Job() { // from class: com.android.internal.infra.ServiceConnector$Impl$$ExternalSyntheticLambda1
                    @Override // com.android.internal.infra.ServiceConnector.Job
                    public final java.lang.Object run(java.lang.Object obj) {
                        return com.android.internal.infra.ServiceConnector.Impl.lambda$connect$0((android.os.IInterface) obj);
                    }
                };
                I i = this.mService;
                if (i != null) {
                    this.mServiceConnectionFutureCache.complete(i);
                } else {
                    enqueue((com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob) this.mServiceConnectionFutureCache);
                }
            }
            return this.mServiceConnectionFutureCache;
        }

        static /* synthetic */ android.os.IInterface lambda$connect$0(android.os.IInterface iInterface) throws java.lang.Exception {
            return iInterface;
        }

        private void enqueue(com.android.internal.infra.ServiceConnector.Impl<I>.CompletionAwareJob<I, ?> completionAwareJob) {
            if (!enqueue((com.android.internal.infra.ServiceConnector.Job) completionAwareJob)) {
                completionAwareJob.completeExceptionally(new java.lang.IllegalStateException("Failed to post a job to handler. Likely " + this.mHandler.getLooper() + " is exiting"));
            }
        }

        private boolean enqueue(final com.android.internal.infra.ServiceConnector.Job<I, ?> job) {
            cancelTimeout();
            return this.mHandler.post(new java.lang.Runnable() { // from class: com.android.internal.infra.ServiceConnector$Impl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.infra.ServiceConnector.Impl.this.lambda$enqueue$1(job);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: enqueueJobThread, reason: merged with bridge method [inline-methods] */
        public void lambda$enqueue$1(com.android.internal.infra.ServiceConnector.Job<I, ?> job) {
            cancelTimeout();
            if (this.mUnbinding) {
                completeExceptionally(job, new java.lang.IllegalStateException("Service is unbinding. Ignoring " + job));
                return;
            }
            if (!this.mQueue.offer(job)) {
                completeExceptionally(job, new java.lang.IllegalStateException("Failed to add to queue: " + job));
                return;
            }
            if (isBound()) {
                processQueue();
            } else if (!this.mBinding) {
                if (bindService(this.mServiceConnection)) {
                    this.mBinding = true;
                } else {
                    completeExceptionally(job, new java.lang.IllegalStateException("Failed to bind to service " + this.mIntent));
                }
            }
        }

        private void cancelTimeout() {
            this.mMainHandler.removeCallbacks(this.mTimeoutDisconnect);
        }

        void completeExceptionally(com.android.internal.infra.ServiceConnector.Job<?, ?> job, java.lang.Throwable th) {
            com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob completionAwareJob = (com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob) castOrNull(job, com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob.class);
            if (completionAwareJob != null) {
                completionAwareJob.completeExceptionally(th);
            }
            if (completionAwareJob == null) {
                android.util.Log.e(LOG_TAG, "Job failed: " + job, th);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        static <BASE, T extends BASE> T castOrNull(BASE base, java.lang.Class<T> cls) {
            if (cls.isInstance(base)) {
                return base;
            }
            return null;
        }

        private void processQueue() {
            I i;
            while (true) {
                com.android.internal.infra.ServiceConnector.Job<I, ?> poll = this.mQueue.poll();
                if (poll != null) {
                    com.android.internal.infra.ServiceConnector.Impl<I>.CompletionAwareJob<I, ?> completionAwareJob = (com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob) castOrNull(poll, com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob.class);
                    try {
                        i = this.mService;
                    } catch (java.lang.Throwable th) {
                        completeExceptionally(poll, th);
                    }
                    if (i == null) {
                        return;
                    }
                    java.lang.Object run = poll.run(i);
                    if (completionAwareJob != null) {
                        if (completionAwareJob.mAsync) {
                            this.mUnfinishedJobs.add(completionAwareJob);
                            ((java.util.concurrent.CompletionStage) run).whenComplete(completionAwareJob);
                        } else {
                            completionAwareJob.complete(run);
                        }
                    }
                } else {
                    maybeScheduleUnbindTimeout();
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void maybeScheduleUnbindTimeout() {
            if (this.mUnfinishedJobs.isEmpty() && this.mQueue.isEmpty()) {
                scheduleUnbindTimeout();
            }
        }

        private void scheduleUnbindTimeout() {
            long autoDisconnectTimeoutMs = getAutoDisconnectTimeoutMs();
            if (autoDisconnectTimeoutMs > 0) {
                this.mMainHandler.postDelayed(this.mTimeoutDisconnect, autoDisconnectTimeoutMs);
            }
        }

        private boolean isBound() {
            return this.mService != null;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public void unbind() {
            this.mUnbinding = true;
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.internal.infra.ServiceConnector$Impl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.infra.ServiceConnector.Impl.this.unbindJobThread();
                }
            });
        }

        @Override // com.android.internal.infra.ServiceConnector
        public void setServiceLifecycleCallbacks(com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<I> serviceLifecycleCallbacks) {
            this.mServiceLifecycleCallbacks = serviceLifecycleCallbacks;
        }

        void unbindJobThread() {
            cancelTimeout();
            I i = this.mService;
            boolean z = i != null;
            if (z || this.mBinding) {
                try {
                    this.mContext.unbindService(this.mServiceConnection);
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Slog.e(LOG_TAG, "Failed to unbind: " + e);
                }
            }
            if (z) {
                dispatchOnServiceConnectionStatusChanged(i, false);
                i.asBinder().unlinkToDeath(this, 0);
                this.mService = null;
            }
            this.mBinding = false;
            this.mUnbinding = false;
            synchronized (this) {
                if (this.mServiceConnectionFutureCache != null) {
                    this.mServiceConnectionFutureCache.cancel(true);
                    this.mServiceConnectionFutureCache = null;
                }
            }
            cancelPendingJobs();
            if (z) {
                onServiceUnbound();
            }
        }

        protected void cancelPendingJobs() {
            while (true) {
                com.android.internal.infra.ServiceConnector.Job<I, ?> poll = this.mQueue.poll();
                if (poll != null) {
                    com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob completionAwareJob = (com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob) castOrNull(poll, com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob.class);
                    if (completionAwareJob != null) {
                        completionAwareJob.cancel(false);
                    }
                } else {
                    return;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            if (this.mUnbinding) {
                android.util.Log.i(LOG_TAG, "Ignoring onServiceConnected due to ongoing unbinding: " + this);
                return;
            }
            I binderAsInterface = binderAsInterface(iBinder);
            this.mService = binderAsInterface;
            this.mBinding = false;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "onServiceConnected " + componentName + ": ", e);
            }
            dispatchOnServiceConnectionStatusChanged(binderAsInterface, true);
            processQueue();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            this.mBinding = true;
            I i = this.mService;
            if (i != null) {
                dispatchOnServiceConnectionStatusChanged(i, false);
                this.mService = null;
            }
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(android.content.ComponentName componentName) {
            binderDied();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mService = null;
            unbind();
            dispatchOnBinderDied();
        }

        private void dispatchOnBinderDied() {
            com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<I> serviceLifecycleCallbacks = this.mServiceLifecycleCallbacks;
            if (serviceLifecycleCallbacks != null) {
                serviceLifecycleCallbacks.onBinderDied();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            onTimeout();
        }

        private void onTimeout() {
            unbind();
        }

        @Override // java.util.AbstractCollection
        public java.lang.String toString() {
            java.lang.StringBuilder append = new java.lang.StringBuilder("ServiceConnector@").append(java.lang.System.identityHashCode(this) % 1000).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START).append(this.mIntent).append(", user: ").append(this.mContext.getUser().getIdentifier()).append(")[").append(stateToString());
            if (!this.mQueue.isEmpty()) {
                append.append(", ").append(this.mQueue.size()).append(" pending job(s)");
            }
            if (!this.mUnfinishedJobs.isEmpty()) {
                append.append(", ").append(this.mUnfinishedJobs.size()).append(" unfinished async job(s)");
            }
            return append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).toString();
        }

        public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
            printWriter.append((java.lang.CharSequence) str).append("ServiceConnector:").println();
            printWriter.append((java.lang.CharSequence) str).append("  ").append((java.lang.CharSequence) java.lang.String.valueOf(this.mIntent)).println();
            printWriter.append((java.lang.CharSequence) str).append("  ").append("userId: ").append((java.lang.CharSequence) java.lang.String.valueOf(this.mContext.getUser().getIdentifier())).println();
            printWriter.append((java.lang.CharSequence) str).append("  ").append("State: ").append((java.lang.CharSequence) stateToString()).println();
            printWriter.append((java.lang.CharSequence) str).append("  ").append("Pending jobs: ").append((java.lang.CharSequence) java.lang.String.valueOf(this.mQueue.size())).println();
            printWriter.append((java.lang.CharSequence) str).append("  ").append("Unfinished async jobs: ").append((java.lang.CharSequence) java.lang.String.valueOf(this.mUnfinishedJobs.size())).println();
        }

        private java.lang.String stateToString() {
            if (this.mBinding) {
                return "Binding...";
            }
            if (this.mUnbinding) {
                return "Unbinding...";
            }
            if (isBound()) {
                return "Bound";
            }
            return "Unbound";
        }

        private void logTrace() {
            android.util.Log.i(LOG_TAG, "See stacktrace", new java.lang.Throwable());
        }

        class CompletionAwareJob<II, R> extends com.android.internal.infra.AndroidFuture<R> implements com.android.internal.infra.ServiceConnector.Job<II, R>, java.util.function.BiConsumer<R, java.lang.Throwable> {
            boolean mAsync = false;
            private java.lang.String mDebugName;
            com.android.internal.infra.ServiceConnector.Job<II, R> mDelegate;

            CompletionAwareJob() {
                setTimeoutHandler(com.android.internal.infra.ServiceConnector.Impl.this.getJobHandler());
                long requestTimeoutMs = com.android.internal.infra.ServiceConnector.Impl.this.getRequestTimeoutMs();
                if (requestTimeoutMs > 0) {
                    orTimeout(requestTimeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS);
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.function.BiConsumer
            public /* bridge */ /* synthetic */ void accept(java.lang.Object obj, java.lang.Throwable th) {
                accept2((com.android.internal.infra.ServiceConnector.Impl.CompletionAwareJob<II, R>) obj, th);
            }

            private static /* synthetic */ boolean lambda$new$0(java.lang.StackTraceElement stackTraceElement) {
                return !stackTraceElement.getClassName().contains(com.android.internal.infra.ServiceConnector.class.getName());
            }

            @Override // com.android.internal.infra.ServiceConnector.Job
            public R run(II ii) throws java.lang.Exception {
                return this.mDelegate.run(ii);
            }

            @Override // com.android.internal.infra.AndroidFuture, java.util.concurrent.CompletableFuture, java.util.concurrent.Future
            public boolean cancel(boolean z) {
                if (z) {
                    android.util.Log.w(com.android.internal.infra.ServiceConnector.Impl.LOG_TAG, "mayInterruptIfRunning not supported - ignoring");
                }
                return super.cancel(z) || com.android.internal.infra.ServiceConnector.Impl.this.mQueue.remove(this);
            }

            @Override // java.util.concurrent.CompletableFuture
            public java.lang.String toString() {
                return this.mDelegate + " wrapped into " + super.toString();
            }

            /* renamed from: accept, reason: avoid collision after fix types in other method */
            public void accept2(R r, java.lang.Throwable th) {
                if (th != null) {
                    completeExceptionally(th);
                } else {
                    complete(r);
                }
            }

            @Override // com.android.internal.infra.AndroidFuture
            protected void onCompleted(R r, java.lang.Throwable th) {
                super.onCompleted(r, th);
                if (com.android.internal.infra.ServiceConnector.Impl.this.mUnfinishedJobs.remove(this)) {
                    com.android.internal.infra.ServiceConnector.Impl.this.maybeScheduleUnbindTimeout();
                }
            }
        }
    }

    public static class NoOp<T extends android.os.IInterface> extends com.android.internal.infra.AndroidFuture<java.lang.Object> implements com.android.internal.infra.ServiceConnector<T> {
        public NoOp() {
            completeExceptionally(new java.lang.IllegalStateException("ServiceConnector is a no-op"));
        }

        @Override // com.android.internal.infra.ServiceConnector
        public boolean run(com.android.internal.infra.ServiceConnector.VoidJob<T> voidJob) {
            return false;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public com.android.internal.infra.AndroidFuture<java.lang.Void> post(com.android.internal.infra.ServiceConnector.VoidJob<T> voidJob) {
            return this;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public <R> com.android.internal.infra.AndroidFuture<R> postForResult(com.android.internal.infra.ServiceConnector.Job<T, R> job) {
            return this;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public <R> com.android.internal.infra.AndroidFuture<R> postAsync(com.android.internal.infra.ServiceConnector.Job<T, java.util.concurrent.CompletableFuture<R>> job) {
            return this;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public com.android.internal.infra.AndroidFuture<T> connect() {
            return this;
        }

        @Override // com.android.internal.infra.ServiceConnector
        public void unbind() {
        }

        @Override // com.android.internal.infra.ServiceConnector
        public void setServiceLifecycleCallbacks(com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<T> serviceLifecycleCallbacks) {
        }
    }
}
