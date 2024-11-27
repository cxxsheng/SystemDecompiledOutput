package com.android.server.location.provider.proxy;

/* loaded from: classes2.dex */
public class ProxyLocationProvider extends com.android.server.location.provider.AbstractLocationProvider implements com.android.server.servicewatcher.ServiceWatcher.ServiceListener<com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo> {
    private static final java.lang.String EXTRA_LOCATION_TAGS = "android:location_allow_listed_tags";
    private static final java.lang.String LOCATION_TAGS_SEPARATOR = ";";
    private static final long RESET_DELAY_MS = 10000;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo mBoundServiceInfo;
    final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final java.util.ArrayList<java.lang.Runnable> mFlushListeners;
    final java.lang.Object mLock;
    final java.lang.String mName;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    com.android.server.location.provider.proxy.ProxyLocationProvider.Proxy mProxy;
    private volatile android.location.provider.ProviderRequest mRequest;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    java.lang.Runnable mResetter;
    final com.android.server.servicewatcher.ServiceWatcher mServiceWatcher;

    @android.annotation.Nullable
    public static com.android.server.location.provider.proxy.ProxyLocationProvider create(android.content.Context context, java.lang.String str, java.lang.String str2, int i, int i2) {
        com.android.server.location.provider.proxy.ProxyLocationProvider proxyLocationProvider = new com.android.server.location.provider.proxy.ProxyLocationProvider(context, str, str2, i, i2);
        if (proxyLocationProvider.checkServiceResolves()) {
            return proxyLocationProvider;
        }
        return null;
    }

    private ProxyLocationProvider(android.content.Context context, java.lang.String str, java.lang.String str2, int i, int i2) {
        super(com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, null, null, java.util.Collections.emptySet());
        this.mLock = new java.lang.Object();
        this.mFlushListeners = new java.util.ArrayList<>(0);
        this.mContext = context;
        this.mServiceWatcher = com.android.server.servicewatcher.ServiceWatcher.create(context, str, com.android.server.servicewatcher.CurrentUserServiceSupplier.createFromConfig(context, str2, i, i2), this);
        this.mName = str;
        this.mProxy = null;
        this.mRequest = android.location.provider.ProviderRequest.EMPTY_REQUEST;
    }

    private boolean checkServiceResolves() {
        return this.mServiceWatcher.checkServiceResolves();
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceListener
    public void onBind(android.os.IBinder iBinder, com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo) throws android.os.RemoteException {
        android.location.provider.ILocationProvider asInterface = android.location.provider.ILocationProvider.Stub.asInterface(iBinder);
        synchronized (this.mLock) {
            try {
                this.mProxy = new com.android.server.location.provider.proxy.ProxyLocationProvider.Proxy();
                this.mBoundServiceInfo = boundServiceInfo;
                asInterface.setLocationProviderManager(this.mProxy);
                android.location.provider.ProviderRequest providerRequest = this.mRequest;
                if (!providerRequest.equals(android.location.provider.ProviderRequest.EMPTY_REQUEST)) {
                    asInterface.setRequest(providerRequest);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceListener
    public void onUnbind() {
        int i;
        java.lang.Runnable[] runnableArr;
        synchronized (this.mLock) {
            try {
                this.mProxy = null;
                this.mBoundServiceInfo = null;
                if (this.mResetter == null) {
                    this.mResetter = new com.android.server.location.provider.proxy.ProxyLocationProvider.AnonymousClass1();
                    com.android.server.FgThread.getHandler().postDelayed(this.mResetter, 10000L);
                }
                runnableArr = (java.lang.Runnable[]) this.mFlushListeners.toArray(new java.lang.Runnable[0]);
                this.mFlushListeners.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        for (java.lang.Runnable runnable : runnableArr) {
            runnable.run();
        }
    }

    /* renamed from: com.android.server.location.provider.proxy.ProxyLocationProvider$1, reason: invalid class name */
    class AnonymousClass1 implements java.lang.Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mLock) {
                try {
                    if (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mResetter == this) {
                        com.android.server.location.provider.proxy.ProxyLocationProvider.this.setState(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.proxy.ProxyLocationProvider$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final java.lang.Object apply(java.lang.Object obj) {
                                com.android.server.location.provider.AbstractLocationProvider.State lambda$run$0;
                                lambda$run$0 = com.android.server.location.provider.proxy.ProxyLocationProvider.AnonymousClass1.lambda$run$0((com.android.server.location.provider.AbstractLocationProvider.State) obj);
                                return lambda$run$0;
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.State lambda$run$0(com.android.server.location.provider.AbstractLocationProvider.State state) {
            return com.android.server.location.provider.AbstractLocationProvider.State.EMPTY_STATE;
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onStart() {
        this.mServiceWatcher.register();
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onStop() {
        this.mServiceWatcher.unregister();
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onSetRequest(final android.location.provider.ProviderRequest providerRequest) {
        this.mRequest = providerRequest;
        this.mServiceWatcher.runOnBinder(new com.android.server.servicewatcher.ServiceWatcher.BinderOperation() { // from class: com.android.server.location.provider.proxy.ProxyLocationProvider$$ExternalSyntheticLambda1
            @Override // com.android.server.servicewatcher.ServiceWatcher.BinderOperation
            public final void run(android.os.IBinder iBinder) {
                com.android.server.location.provider.proxy.ProxyLocationProvider.lambda$onSetRequest$0(providerRequest, iBinder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onSetRequest$0(android.location.provider.ProviderRequest providerRequest, android.os.IBinder iBinder) throws android.os.RemoteException {
        android.location.provider.ILocationProvider.Stub.asInterface(iBinder).setRequest(providerRequest);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onFlush(final java.lang.Runnable runnable) {
        this.mServiceWatcher.runOnBinder(new com.android.server.servicewatcher.ServiceWatcher.BinderOperation() { // from class: com.android.server.location.provider.proxy.ProxyLocationProvider.2
            @Override // com.android.server.servicewatcher.ServiceWatcher.BinderOperation
            public void run(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.location.provider.ILocationProvider asInterface = android.location.provider.ILocationProvider.Stub.asInterface(iBinder);
                synchronized (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mLock) {
                    com.android.server.location.provider.proxy.ProxyLocationProvider.this.mFlushListeners.add(runnable);
                }
                asInterface.flush();
            }

            @Override // com.android.server.servicewatcher.ServiceWatcher.BinderOperation
            public void onError(java.lang.Throwable th) {
                synchronized (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mLock) {
                    com.android.server.location.provider.proxy.ProxyLocationProvider.this.mFlushListeners.remove(runnable);
                }
                runnable.run();
            }
        });
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public void onExtraCommand(int i, int i2, final java.lang.String str, final android.os.Bundle bundle) {
        this.mServiceWatcher.runOnBinder(new com.android.server.servicewatcher.ServiceWatcher.BinderOperation() { // from class: com.android.server.location.provider.proxy.ProxyLocationProvider$$ExternalSyntheticLambda0
            @Override // com.android.server.servicewatcher.ServiceWatcher.BinderOperation
            public final void run(android.os.IBinder iBinder) {
                com.android.server.location.provider.proxy.ProxyLocationProvider.lambda$onExtraCommand$1(str, bundle, iBinder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onExtraCommand$1(java.lang.String str, android.os.Bundle bundle, android.os.IBinder iBinder) throws android.os.RemoteException {
        android.location.provider.ILocationProvider.Stub.asInterface(iBinder).sendExtraCommand(str, bundle);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        this.mServiceWatcher.dump(printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Proxy extends android.location.provider.ILocationProviderManager.Stub {
        Proxy() {
        }

        public void onInitialize(final boolean z, final android.location.provider.ProviderProperties providerProperties, @android.annotation.Nullable java.lang.String str) {
            synchronized (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mLock) {
                try {
                    if (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mProxy != this) {
                        return;
                    }
                    if (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mResetter != null) {
                        com.android.server.FgThread.getHandler().removeCallbacks(com.android.server.location.provider.proxy.ProxyLocationProvider.this.mResetter);
                        com.android.server.location.provider.proxy.ProxyLocationProvider.this.mResetter = null;
                    }
                    java.lang.String[] strArr = new java.lang.String[0];
                    if (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mBoundServiceInfo.getMetadata() != null) {
                        java.lang.String string = com.android.server.location.provider.proxy.ProxyLocationProvider.this.mBoundServiceInfo.getMetadata().getString(com.android.server.location.provider.proxy.ProxyLocationProvider.EXTRA_LOCATION_TAGS);
                        if (!android.text.TextUtils.isEmpty(string)) {
                            strArr = string.split(com.android.server.location.provider.proxy.ProxyLocationProvider.LOCATION_TAGS_SEPARATOR);
                            android.util.Log.i(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.proxy.ProxyLocationProvider.this.mName + " provider loaded extra attribution tags: " + java.util.Arrays.toString(strArr));
                        }
                    }
                    final android.util.ArraySet arraySet = new android.util.ArraySet(strArr);
                    final android.location.util.identity.CallerIdentity fromBinderUnsafe = android.location.util.identity.CallerIdentity.fromBinderUnsafe(com.android.server.location.provider.proxy.ProxyLocationProvider.this.mBoundServiceInfo.getComponentName().getPackageName(), str);
                    com.android.server.location.provider.proxy.ProxyLocationProvider.this.setState(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.proxy.ProxyLocationProvider$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            com.android.server.location.provider.AbstractLocationProvider.State lambda$onInitialize$0;
                            lambda$onInitialize$0 = com.android.server.location.provider.proxy.ProxyLocationProvider.Proxy.lambda$onInitialize$0(z, providerProperties, fromBinderUnsafe, arraySet, (com.android.server.location.provider.AbstractLocationProvider.State) obj);
                            return lambda$onInitialize$0;
                        }
                    });
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.State lambda$onInitialize$0(boolean z, android.location.provider.ProviderProperties providerProperties, android.location.util.identity.CallerIdentity callerIdentity, android.util.ArraySet arraySet, com.android.server.location.provider.AbstractLocationProvider.State state) {
            return com.android.server.location.provider.AbstractLocationProvider.State.EMPTY_STATE.withAllowed(z).withProperties(providerProperties).withIdentity(callerIdentity).withExtraAttributionTags(arraySet);
        }

        public void onSetProperties(android.location.provider.ProviderProperties providerProperties) {
            synchronized (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mLock) {
                try {
                    if (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mProxy != this) {
                        return;
                    }
                    com.android.server.location.provider.proxy.ProxyLocationProvider.this.setProperties(providerProperties);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onSetAllowed(boolean z) {
            synchronized (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mLock) {
                try {
                    if (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mProxy != this) {
                        return;
                    }
                    com.android.server.location.provider.proxy.ProxyLocationProvider.this.setAllowed(z);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onReportLocation(android.location.Location location) {
            synchronized (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mLock) {
                try {
                    if (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mProxy != this) {
                        return;
                    }
                    com.android.server.location.provider.proxy.ProxyLocationProvider.this.reportLocation(android.location.LocationResult.wrap(new android.location.Location[]{location}));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onReportLocations(java.util.List<android.location.Location> list) {
            synchronized (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mLock) {
                try {
                    if (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mProxy != this) {
                        return;
                    }
                    com.android.server.location.provider.proxy.ProxyLocationProvider.this.reportLocation(android.location.LocationResult.wrap(list));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onFlushComplete() {
            java.lang.Runnable runnable;
            synchronized (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mLock) {
                try {
                    if (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mProxy != this) {
                        return;
                    }
                    if (com.android.server.location.provider.proxy.ProxyLocationProvider.this.mFlushListeners.isEmpty()) {
                        runnable = null;
                    } else {
                        runnable = com.android.server.location.provider.proxy.ProxyLocationProvider.this.mFlushListeners.remove(0);
                    }
                    if (runnable != null) {
                        runnable.run();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
