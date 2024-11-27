package com.android.server;

/* loaded from: classes.dex */
class ExplicitHealthCheckController {
    private static final java.lang.String TAG = "ExplicitHealthCheckController";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.content.ServiceConnection mConnection;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mEnabled;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.lang.Runnable mNotifySyncRunnable;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.util.function.Consumer<java.lang.String> mPassedConsumer;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.service.watchdog.IExplicitHealthCheckService mRemoteService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.util.function.Consumer<java.util.List<android.service.watchdog.ExplicitHealthCheckService.PackageConfig>> mSupportedConsumer;

    ExplicitHealthCheckController(android.content.Context context) {
        this.mContext = context;
    }

    public void setEnabled(boolean z) {
        synchronized (this.mLock) {
            try {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Explicit health checks ");
                sb.append(z ? "enabled." : "disabled.");
                android.util.Slog.i(TAG, sb.toString());
                this.mEnabled = z;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setCallbacks(java.util.function.Consumer<java.lang.String> consumer, java.util.function.Consumer<java.util.List<android.service.watchdog.ExplicitHealthCheckService.PackageConfig>> consumer2, java.lang.Runnable runnable) {
        synchronized (this.mLock) {
            try {
                if (this.mPassedConsumer != null || this.mSupportedConsumer != null || this.mNotifySyncRunnable != null) {
                    android.util.Slog.wtf(TAG, "Resetting health check controller callbacks");
                }
                java.util.Objects.requireNonNull(consumer);
                this.mPassedConsumer = consumer;
                java.util.Objects.requireNonNull(consumer2);
                this.mSupportedConsumer = consumer2;
                java.util.Objects.requireNonNull(runnable);
                this.mNotifySyncRunnable = runnable;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void syncRequests(final java.util.Set<java.lang.String> set) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mEnabled;
        }
        if (!z) {
            android.util.Slog.i(TAG, "Health checks disabled, no supported packages");
            this.mSupportedConsumer.accept(java.util.Collections.emptyList());
        } else {
            getSupportedPackages(new java.util.function.Consumer() { // from class: com.android.server.ExplicitHealthCheckController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.ExplicitHealthCheckController.this.lambda$syncRequests$3(set, (java.util.List) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncRequests$3(final java.util.Set set, final java.util.List list) {
        this.mSupportedConsumer.accept(list);
        getRequestedPackages(new java.util.function.Consumer() { // from class: com.android.server.ExplicitHealthCheckController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.ExplicitHealthCheckController.this.lambda$syncRequests$2(list, set, (java.util.List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncRequests$2(java.util.List list, java.util.Set set, java.util.List list2) {
        synchronized (this.mLock) {
            try {
                android.util.ArraySet arraySet = new android.util.ArraySet();
                java.util.Iterator it = list.iterator();
                while (it.hasNext()) {
                    arraySet.add(((android.service.watchdog.ExplicitHealthCheckService.PackageConfig) it.next()).getPackageName());
                }
                set.retainAll(arraySet);
                actOnDifference(list2, set, new java.util.function.Consumer() { // from class: com.android.server.ExplicitHealthCheckController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.ExplicitHealthCheckController.this.lambda$syncRequests$0((java.lang.String) obj);
                    }
                });
                actOnDifference(set, list2, new java.util.function.Consumer() { // from class: com.android.server.ExplicitHealthCheckController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.ExplicitHealthCheckController.this.lambda$syncRequests$1((java.lang.String) obj);
                    }
                });
                if (set.isEmpty()) {
                    android.util.Slog.i(TAG, "No more health check requests, unbinding...");
                    unbindService();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void actOnDifference(java.util.Collection<java.lang.String> collection, java.util.Collection<java.lang.String> collection2, java.util.function.Consumer<java.lang.String> consumer) {
        for (java.lang.String str : collection) {
            if (!collection2.contains(str)) {
                consumer.accept(str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: request, reason: merged with bridge method [inline-methods] */
    public void lambda$syncRequests$1(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                if (prepareServiceLocked("request health check for " + str)) {
                    android.util.Slog.i(TAG, "Requesting health check for package " + str);
                    try {
                        this.mRemoteService.request(str);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failed to request health check for package " + str, e);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cancel, reason: merged with bridge method [inline-methods] */
    public void lambda$syncRequests$0(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                if (prepareServiceLocked("cancel health check for " + str)) {
                    android.util.Slog.i(TAG, "Cancelling health check for package " + str);
                    try {
                        this.mRemoteService.cancel(str);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failed to cancel health check for package " + str, e);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void getSupportedPackages(final java.util.function.Consumer<java.util.List<android.service.watchdog.ExplicitHealthCheckService.PackageConfig>> consumer) {
        synchronized (this.mLock) {
            try {
                if (prepareServiceLocked("get health check supported packages")) {
                    android.util.Slog.d(TAG, "Getting health check supported packages");
                    try {
                        this.mRemoteService.getSupportedPackages(new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.ExplicitHealthCheckController$$ExternalSyntheticLambda5
                            public final void onResult(android.os.Bundle bundle) {
                                com.android.server.ExplicitHealthCheckController.lambda$getSupportedPackages$4(consumer, bundle);
                            }
                        }));
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failed to get health check supported packages", e);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getSupportedPackages$4(java.util.function.Consumer consumer, android.os.Bundle bundle) {
        java.util.ArrayList parcelableArrayList = bundle.getParcelableArrayList("android.service.watchdog.extra.supported_packages", android.service.watchdog.ExplicitHealthCheckService.PackageConfig.class);
        android.util.Slog.i(TAG, "Explicit health check supported packages " + parcelableArrayList);
        consumer.accept(parcelableArrayList);
    }

    private void getRequestedPackages(final java.util.function.Consumer<java.util.List<java.lang.String>> consumer) {
        synchronized (this.mLock) {
            try {
                if (prepareServiceLocked("get health check requested packages")) {
                    android.util.Slog.d(TAG, "Getting health check requested packages");
                    try {
                        this.mRemoteService.getRequestedPackages(new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.ExplicitHealthCheckController$$ExternalSyntheticLambda3
                            public final void onResult(android.os.Bundle bundle) {
                                com.android.server.ExplicitHealthCheckController.lambda$getRequestedPackages$5(consumer, bundle);
                            }
                        }));
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failed to get health check requested packages", e);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getRequestedPackages$5(java.util.function.Consumer consumer, android.os.Bundle bundle) {
        java.util.ArrayList<java.lang.String> stringArrayList = bundle.getStringArrayList("android.service.watchdog.extra.requested_packages");
        android.util.Slog.i(TAG, "Explicit health check requested packages " + stringArrayList);
        consumer.accept(stringArrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindService() {
        synchronized (this.mLock) {
            try {
                if (!this.mEnabled || this.mConnection != null || this.mRemoteService != null) {
                    if (!this.mEnabled) {
                        android.util.Slog.i(TAG, "Not binding to service, service disabled");
                    } else if (this.mRemoteService != null) {
                        android.util.Slog.i(TAG, "Not binding to service, service already connected");
                    } else {
                        android.util.Slog.i(TAG, "Not binding to service, service already connecting");
                    }
                    return;
                }
                android.content.ComponentName serviceComponentNameLocked = getServiceComponentNameLocked();
                if (serviceComponentNameLocked == null) {
                    android.util.Slog.wtf(TAG, "Explicit health check service not found");
                    return;
                }
                android.content.Intent intent = new android.content.Intent();
                intent.setComponent(serviceComponentNameLocked);
                this.mConnection = new android.content.ServiceConnection() { // from class: com.android.server.ExplicitHealthCheckController.1
                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                        android.util.Slog.i(com.android.server.ExplicitHealthCheckController.TAG, "Explicit health check service is connected " + componentName);
                        com.android.server.ExplicitHealthCheckController.this.initState(iBinder);
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(android.content.ComponentName componentName) {
                        android.util.Slog.i(com.android.server.ExplicitHealthCheckController.TAG, "Explicit health check service is disconnected " + componentName);
                        synchronized (com.android.server.ExplicitHealthCheckController.this.mLock) {
                            com.android.server.ExplicitHealthCheckController.this.mRemoteService = null;
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public void onBindingDied(android.content.ComponentName componentName) {
                        android.util.Slog.i(com.android.server.ExplicitHealthCheckController.TAG, "Explicit health check service binding is dead. Rebind: " + componentName);
                        com.android.server.ExplicitHealthCheckController.this.unbindService();
                        com.android.server.ExplicitHealthCheckController.this.bindService();
                    }

                    @Override // android.content.ServiceConnection
                    public void onNullBinding(android.content.ComponentName componentName) {
                        android.util.Slog.wtf(com.android.server.ExplicitHealthCheckController.TAG, "Explicit health check service binding is null?? " + componentName);
                    }
                };
                this.mContext.bindServiceAsUser(intent, this.mConnection, 1, android.os.UserHandle.SYSTEM);
                android.util.Slog.i(TAG, "Explicit health check service is bound");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unbindService() {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteService != null) {
                    this.mContext.unbindService(this.mConnection);
                    this.mRemoteService = null;
                    this.mConnection = null;
                }
                android.util.Slog.i(TAG, "Explicit health check service is unbound");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.content.pm.ServiceInfo getServiceInfoLocked() {
        java.lang.String servicesSystemSharedLibraryPackageName = this.mContext.getPackageManager().getServicesSystemSharedLibraryPackageName();
        if (servicesSystemSharedLibraryPackageName == null) {
            android.util.Slog.w(TAG, "no external services package!");
            return null;
        }
        android.content.Intent intent = new android.content.Intent("android.service.watchdog.ExplicitHealthCheckService");
        intent.setPackage(servicesSystemSharedLibraryPackageName);
        android.content.pm.ResolveInfo resolveService = this.mContext.getPackageManager().resolveService(intent, 132);
        if (resolveService == null || resolveService.serviceInfo == null) {
            android.util.Slog.w(TAG, "No valid components found.");
            return null;
        }
        return resolveService.serviceInfo;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.content.ComponentName getServiceComponentNameLocked() {
        android.content.pm.ServiceInfo serviceInfoLocked = getServiceInfoLocked();
        if (serviceInfoLocked == null) {
            return null;
        }
        android.content.ComponentName componentName = new android.content.ComponentName(serviceInfoLocked.packageName, serviceInfoLocked.name);
        if (!"android.permission.BIND_EXPLICIT_HEALTH_CHECK_SERVICE".equals(serviceInfoLocked.permission)) {
            android.util.Slog.w(TAG, componentName.flattenToShortString() + " does not require permission android.permission.BIND_EXPLICIT_HEALTH_CHECK_SERVICE");
            return null;
        }
        return componentName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initState(android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                if (!this.mEnabled) {
                    android.util.Slog.w(TAG, "Attempting to connect disabled service?? Unbinding...");
                    unbindService();
                    return;
                }
                this.mRemoteService = android.service.watchdog.IExplicitHealthCheckService.Stub.asInterface(iBinder);
                try {
                    this.mRemoteService.setCallback(new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.ExplicitHealthCheckController$$ExternalSyntheticLambda6
                        public final void onResult(android.os.Bundle bundle) {
                            com.android.server.ExplicitHealthCheckController.this.lambda$initState$6(bundle);
                        }
                    }));
                    android.util.Slog.i(TAG, "Service initialized, syncing requests");
                } catch (android.os.RemoteException e) {
                    android.util.Slog.wtf(TAG, "Could not setCallback on explicit health check service");
                }
                this.mNotifySyncRunnable.run();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initState$6(android.os.Bundle bundle) {
        java.lang.String string = bundle.getString("android.service.watchdog.extra.health_check_passed_package");
        if (!android.text.TextUtils.isEmpty(string)) {
            if (this.mPassedConsumer == null) {
                android.util.Slog.wtf(TAG, "Health check passed for package " + string + "but no consumer registered.");
                return;
            }
            this.mPassedConsumer.accept(string);
            return;
        }
        android.util.Slog.wtf(TAG, "Empty package passed explicit health check?");
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean prepareServiceLocked(java.lang.String str) {
        if (this.mRemoteService != null && this.mEnabled) {
            return true;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Service not ready to ");
        sb.append(str);
        sb.append(this.mEnabled ? ". Binding..." : ". Disabled");
        android.util.Slog.i(TAG, sb.toString());
        if (this.mEnabled) {
            bindService();
            return false;
        }
        return false;
    }
}
