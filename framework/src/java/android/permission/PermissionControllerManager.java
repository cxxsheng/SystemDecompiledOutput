package android.permission;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class PermissionControllerManager {
    private static final int CHUNK_SIZE = 4096;
    public static final int COUNT_ONLY_WHEN_GRANTED = 1;
    public static final int COUNT_WHEN_SYSTEM = 2;

    @android.annotation.SystemApi
    public static final int HIBERNATION_ELIGIBILITY_ELIGIBLE = 0;

    @android.annotation.SystemApi
    public static final int HIBERNATION_ELIGIBILITY_EXEMPT_BY_SYSTEM = 1;

    @android.annotation.SystemApi
    public static final int HIBERNATION_ELIGIBILITY_EXEMPT_BY_USER = 2;

    @android.annotation.SystemApi
    public static final int HIBERNATION_ELIGIBILITY_UNKNOWN = -1;
    public static final int REASON_INSTALLER_POLICY_VIOLATION = 2;
    public static final int REASON_MALWARE = 1;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final com.android.internal.infra.ServiceConnector<android.permission.IPermissionController> mRemoteService;
    private static final java.lang.String TAG = android.permission.PermissionControllerManager.class.getSimpleName();
    private static final long REQUEST_TIMEOUT_MILLIS = android.os.Build.HW_TIMEOUT_MULTIPLIER * 60000;
    private static final long UNBIND_TIMEOUT_MILLIS = android.os.Build.HW_TIMEOUT_MULTIPLIER * android.app.job.JobInfo.MIN_BACKOFF_MILLIS;
    private static final java.lang.Object sLock = new java.lang.Object();
    private static android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Thread>, com.android.internal.infra.ServiceConnector<android.permission.IPermissionController>> sRemoteServices = new android.util.ArrayMap<>(1);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CountPermissionAppsFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HibernationEligibilityFlag {
    }

    public interface OnCountPermissionAppsResultCallback {
        void onCountPermissionApps(int i);
    }

    public interface OnGetAppPermissionResultCallback {
        void onGetAppPermissions(java.util.List<android.permission.RuntimePermissionPresentationInfo> list);
    }

    public interface OnPermissionUsageResultCallback {
        void onPermissionUsageResult(java.util.List<android.permission.RuntimePermissionUsageInfo> list);
    }

    public static abstract class OnRevokeRuntimePermissionsCallback {
        public abstract void onRevokeRuntimePermissions(java.util.Map<java.lang.String, java.util.List<java.lang.String>> map);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Reason {
    }

    public PermissionControllerManager(android.content.Context context, final android.os.Handler handler) {
        synchronized (sLock) {
            android.util.Pair<java.lang.Integer, java.lang.Thread> pair = new android.util.Pair<>(java.lang.Integer.valueOf(context.getUserId()), handler.getLooper().getThread());
            com.android.internal.infra.ServiceConnector<android.permission.IPermissionController> serviceConnector = sRemoteServices.get(pair);
            if (serviceConnector == null) {
                android.content.Intent intent = new android.content.Intent(android.permission.PermissionControllerService.SERVICE_INTERFACE);
                java.lang.String permissionControllerPackageName = context.getPackageManager().getPermissionControllerPackageName();
                intent.setPackage(permissionControllerPackageName);
                android.content.pm.ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
                if (resolveService == null) {
                    java.lang.String str = "No PermissionController package (" + permissionControllerPackageName + ") for user " + context.getUserId();
                    android.util.Log.wtf(TAG, str);
                    throw new java.lang.IllegalStateException(str);
                }
                com.android.internal.infra.ServiceConnector.Impl<android.permission.IPermissionController> impl = new com.android.internal.infra.ServiceConnector.Impl<android.permission.IPermissionController>(android.app.ActivityThread.currentApplication(), new android.content.Intent(android.permission.PermissionControllerService.SERVICE_INTERFACE).setComponent(resolveService.getComponentInfo().getComponentName()), 0, context.getUserId(), new java.util.function.Function() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda31
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return android.permission.IPermissionController.Stub.asInterface((android.os.IBinder) obj);
                    }
                }) { // from class: android.permission.PermissionControllerManager.1
                    @Override // com.android.internal.infra.ServiceConnector.Impl
                    protected android.os.Handler getJobHandler() {
                        return handler;
                    }

                    @Override // com.android.internal.infra.ServiceConnector.Impl
                    protected long getRequestTimeoutMs() {
                        return android.permission.PermissionControllerManager.REQUEST_TIMEOUT_MILLIS;
                    }

                    @Override // com.android.internal.infra.ServiceConnector.Impl
                    protected long getAutoDisconnectTimeoutMs() {
                        return android.permission.PermissionControllerManager.UNBIND_TIMEOUT_MILLIS;
                    }
                };
                sRemoteServices.put(pair, impl);
                serviceConnector = impl;
            }
            this.mRemoteService = serviceConnector;
        }
        this.mContext = context;
        this.mHandler = handler;
    }

    private void enforceSomePermissionsGrantedToSelf(java.lang.String... strArr) {
        for (java.lang.String str : strArr) {
            if (this.mContext.checkSelfPermission(str) == 0) {
                return;
            }
        }
        throw new java.lang.SecurityException("At lest one of the following permissions is required: " + java.util.Arrays.toString(strArr));
    }

    public void revokeRuntimePermissions(final java.util.Map<java.lang.String, java.util.List<java.lang.String>> map, final boolean z, final int i, java.util.concurrent.Executor executor, final android.permission.PermissionControllerManager.OnRevokeRuntimePermissionsCallback onRevokeRuntimePermissionsCallback) {
        com.android.internal.util.Preconditions.checkNotNull(executor);
        com.android.internal.util.Preconditions.checkNotNull(onRevokeRuntimePermissionsCallback);
        com.android.internal.util.Preconditions.checkNotNull(map);
        for (java.util.Map.Entry<java.lang.String, java.util.List<java.lang.String>> entry : map.entrySet()) {
            com.android.internal.util.Preconditions.checkNotNull(entry.getKey());
            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(entry.getValue(), "permissions");
        }
        enforceSomePermissionsGrantedToSelf(android.Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda14
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$revokeRuntimePermissions$0;
                lambda$revokeRuntimePermissions$0 = android.permission.PermissionControllerManager.this.lambda$revokeRuntimePermissions$0(map, z, i, (android.permission.IPermissionController) obj);
                return lambda$revokeRuntimePermissions$0;
            }
        }).whenCompleteAsync((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda15
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$revokeRuntimePermissions$1(android.permission.PermissionControllerManager.OnRevokeRuntimePermissionsCallback.this, (java.util.Map) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.concurrent.CompletableFuture lambda$revokeRuntimePermissions$0(java.util.Map map, boolean z, int i, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        android.os.Bundle bundle = new android.os.Bundle();
        for (java.util.Map.Entry entry : map.entrySet()) {
            bundle.putStringArrayList((java.lang.String) entry.getKey(), new java.util.ArrayList<>((java.util.Collection) entry.getValue()));
        }
        com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iPermissionController.revokeRuntimePermissions(bundle, z, i, this.mContext.getPackageName(), androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$revokeRuntimePermissions$1(android.permission.PermissionControllerManager.OnRevokeRuntimePermissionsCallback onRevokeRuntimePermissionsCallback, java.util.Map map, java.lang.Throwable th) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (th != null) {
                android.util.Log.e(TAG, "Failure when revoking runtime permissions " + map, th);
                onRevokeRuntimePermissionsCallback.onRevokeRuntimePermissions(java.util.Collections.emptyMap());
            } else {
                onRevokeRuntimePermissionsCallback.onRevokeRuntimePermissions(map);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setRuntimePermissionGrantStateByDeviceAdmin(final java.lang.String str, final android.permission.AdminPermissionControlParams adminPermissionControlParams, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        java.util.Objects.requireNonNull(adminPermissionControlParams, "Admin control params must not be null.");
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda16
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$setRuntimePermissionGrantStateByDeviceAdmin$2(str, adminPermissionControlParams, (android.permission.IPermissionController) obj);
            }
        }).whenCompleteAsync((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda17
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$setRuntimePermissionGrantStateByDeviceAdmin$3(str, consumer, (java.lang.Boolean) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$setRuntimePermissionGrantStateByDeviceAdmin$2(java.lang.String str, android.permission.AdminPermissionControlParams adminPermissionControlParams, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iPermissionController.setRuntimePermissionGrantStateByDeviceAdminFromParams(str, adminPermissionControlParams, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$setRuntimePermissionGrantStateByDeviceAdmin$3(java.lang.String str, java.util.function.Consumer consumer, java.lang.Boolean bool, java.lang.Throwable th) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (th != null) {
                android.util.Log.e(TAG, "Error setting permissions state for device admin " + str, th);
                consumer.accept(false);
            } else {
                consumer.accept(java.lang.Boolean.valueOf(java.lang.Boolean.TRUE.equals(bool)));
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void getRuntimePermissionBackup(final android.os.UserHandle userHandle, java.util.concurrent.Executor executor, final java.util.function.Consumer<byte[]> consumer) {
        com.android.internal.util.Preconditions.checkNotNull(userHandle);
        com.android.internal.util.Preconditions.checkNotNull(executor);
        com.android.internal.util.Preconditions.checkNotNull(consumer);
        enforceSomePermissionsGrantedToSelf(android.Manifest.permission.GET_RUNTIME_PERMISSIONS);
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda20
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture receiveBytes;
                receiveBytes = com.android.internal.infra.RemoteStream.receiveBytes(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda36
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingConsumer
                    public final void acceptOrThrow(java.lang.Object obj2) {
                        android.permission.IPermissionController.this.getRuntimePermissionBackup(r2, (android.os.ParcelFileDescriptor) obj2);
                    }
                });
                return receiveBytes;
            }
        }).whenCompleteAsync((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda21
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$getRuntimePermissionBackup$6(consumer, (byte[]) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ void lambda$getRuntimePermissionBackup$6(java.util.function.Consumer consumer, byte[] bArr, java.lang.Throwable th) {
        if (th != null) {
            android.util.Log.e(TAG, "Error getting permission backup", th);
            consumer.accept(libcore.util.EmptyArray.BYTE);
        } else {
            consumer.accept(bArr);
        }
    }

    public void stageAndApplyRuntimePermissionsBackup(final byte[] bArr, final android.os.UserHandle userHandle) {
        com.android.internal.util.Preconditions.checkNotNull(bArr);
        com.android.internal.util.Preconditions.checkNotNull(userHandle);
        enforceSomePermissionsGrantedToSelf(android.Manifest.permission.GRANT_RUNTIME_PERMISSIONS, android.Manifest.permission.RESTORE_RUNTIME_PERMISSIONS);
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda12
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture sendBytes;
                sendBytes = com.android.internal.infra.RemoteStream.sendBytes((com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.os.ParcelFileDescriptor>) new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda28
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingConsumer
                    public final void acceptOrThrow(java.lang.Object obj2) {
                        android.permission.IPermissionController.this.stageAndApplyRuntimePermissionsBackup(r2, (android.os.ParcelFileDescriptor) obj2);
                    }
                }, bArr);
                return sendBytes;
            }
        }).whenComplete((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda13
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$stageAndApplyRuntimePermissionsBackup$9((java.lang.Void) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    static /* synthetic */ void lambda$stageAndApplyRuntimePermissionsBackup$9(java.lang.Void r1, java.lang.Throwable th) {
        if (th != null) {
            android.util.Log.e(TAG, "Error sending permission backup", th);
        }
    }

    public void applyStagedRuntimePermissionBackup(final java.lang.String str, final android.os.UserHandle userHandle, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkNotNull(userHandle);
        com.android.internal.util.Preconditions.checkNotNull(executor);
        com.android.internal.util.Preconditions.checkNotNull(consumer);
        enforceSomePermissionsGrantedToSelf(android.Manifest.permission.GRANT_RUNTIME_PERMISSIONS, android.Manifest.permission.RESTORE_RUNTIME_PERMISSIONS);
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda8
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$applyStagedRuntimePermissionBackup$10(str, userHandle, (android.permission.IPermissionController) obj);
            }
        }).whenCompleteAsync((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$applyStagedRuntimePermissionBackup$11(str, consumer, (java.lang.Boolean) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$applyStagedRuntimePermissionBackup$10(java.lang.String str, android.os.UserHandle userHandle, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iPermissionController.applyStagedRuntimePermissionBackup(str, userHandle, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$applyStagedRuntimePermissionBackup$11(java.lang.String str, java.util.function.Consumer consumer, java.lang.Boolean bool, java.lang.Throwable th) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (th != null) {
                android.util.Log.e(TAG, "Error restoring delayed permissions for " + str, th);
                consumer.accept(true);
            } else {
                consumer.accept(java.lang.Boolean.valueOf(java.lang.Boolean.TRUE.equals(bool)));
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dump(final java.io.FileDescriptor fileDescriptor, final java.lang.String[] strArr) {
        try {
            this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda24
                @Override // com.android.internal.infra.ServiceConnector.Job
                public final java.lang.Object run(java.lang.Object obj) {
                    java.util.concurrent.CompletableFuture runAsync;
                    runAsync = com.android.internal.infra.AndroidFuture.runAsync(com.android.internal.util.FunctionalUtils.uncheckExceptions(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda30
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.permission.IPermissionController.this.asBinder().dump(r2, r3);
                        }
                    }), com.android.internal.os.BackgroundThread.getExecutor());
                    return runAsync;
                }
            }).get(REQUEST_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Could not get dump", e);
        }
    }

    public void getAppPermissions(final java.lang.String str, final android.permission.PermissionControllerManager.OnGetAppPermissionResultCallback onGetAppPermissionResultCallback, final android.os.Handler handler) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkNotNull(onGetAppPermissionResultCallback);
        if (handler == null) {
            handler = this.mHandler;
        }
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda37
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$getAppPermissions$14(str, (android.permission.IPermissionController) obj);
            }
        }).whenComplete((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda38
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.os.Handler.this.post(new java.lang.Runnable() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda40
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.permission.PermissionControllerManager.lambda$getAppPermissions$15(r1, r2, r3);
                    }
                });
            }
        });
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$getAppPermissions$14(java.lang.String str, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iPermissionController.getAppPermissions(str, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getAppPermissions$15(java.lang.Throwable th, android.permission.PermissionControllerManager.OnGetAppPermissionResultCallback onGetAppPermissionResultCallback, java.util.List list) {
        if (th != null) {
            android.util.Log.e(TAG, "Error getting app permission", th);
            onGetAppPermissionResultCallback.onGetAppPermissions(java.util.Collections.emptyList());
        } else {
            onGetAppPermissionResultCallback.onGetAppPermissions(com.android.internal.util.CollectionUtils.emptyIfNull(list));
        }
    }

    public void revokeRuntimePermission(final java.lang.String str, final java.lang.String str2) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkNotNull(str2);
        this.mRemoteService.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda27
            @Override // com.android.internal.infra.ServiceConnector.VoidJob
            public final void runNoResult(java.lang.Object obj) {
                ((android.permission.IPermissionController) obj).revokeRuntimePermission(str, str2);
            }
        });
    }

    public void countPermissionApps(final java.util.List<java.lang.String> list, final int i, final android.permission.PermissionControllerManager.OnCountPermissionAppsResultCallback onCountPermissionAppsResultCallback, final android.os.Handler handler) {
        com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "permissionNames");
        com.android.internal.util.Preconditions.checkFlagsArgument(i, 3);
        com.android.internal.util.Preconditions.checkNotNull(onCountPermissionAppsResultCallback);
        if (handler == null) {
            handler = this.mHandler;
        }
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda6
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$countPermissionApps$18(list, i, (android.permission.IPermissionController) obj);
            }
        }).whenComplete((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.os.Handler.this.post(new java.lang.Runnable() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda29
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.permission.PermissionControllerManager.lambda$countPermissionApps$19(r1, r2, r3);
                    }
                });
            }
        });
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$countPermissionApps$18(java.util.List list, int i, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iPermissionController.countPermissionApps(list, i, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$countPermissionApps$19(java.lang.Throwable th, android.permission.PermissionControllerManager.OnCountPermissionAppsResultCallback onCountPermissionAppsResultCallback, java.lang.Integer num) {
        if (th != null) {
            android.util.Log.e(TAG, "Error counting permission apps", th);
            onCountPermissionAppsResultCallback.onCountPermissionApps(0);
        } else {
            onCountPermissionAppsResultCallback.onCountPermissionApps(num.intValue());
        }
    }

    public void getPermissionUsages(final boolean z, final long j, java.util.concurrent.Executor executor, final android.permission.PermissionControllerManager.OnPermissionUsageResultCallback onPermissionUsageResultCallback) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(j);
        com.android.internal.util.Preconditions.checkNotNull(executor);
        com.android.internal.util.Preconditions.checkNotNull(onPermissionUsageResultCallback);
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda10
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$getPermissionUsages$21(z, j, (android.permission.IPermissionController) obj);
            }
        }).whenCompleteAsync((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$getPermissionUsages$22(android.permission.PermissionControllerManager.OnPermissionUsageResultCallback.this, (java.util.List) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$getPermissionUsages$21(boolean z, long j, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iPermissionController.getPermissionUsages(z, j, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getPermissionUsages$22(android.permission.PermissionControllerManager.OnPermissionUsageResultCallback onPermissionUsageResultCallback, java.util.List list, java.lang.Throwable th) {
        if (th != null) {
            android.util.Log.e(TAG, "Error getting permission usages", th);
            onPermissionUsageResultCallback.onPermissionUsageResult(java.util.Collections.emptyList());
        } else {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                onPermissionUsageResultCallback.onPermissionUsageResult(com.android.internal.util.CollectionUtils.emptyIfNull(list));
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void grantOrUpgradeDefaultRuntimePermissions(java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda34
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$grantOrUpgradeDefaultRuntimePermissions$23((android.permission.IPermissionController) obj);
            }
        }).whenCompleteAsync((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda35
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$grantOrUpgradeDefaultRuntimePermissions$24(consumer, (java.lang.Boolean) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$grantOrUpgradeDefaultRuntimePermissions$23(android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iPermissionController.grantOrUpgradeDefaultRuntimePermissions(androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$grantOrUpgradeDefaultRuntimePermissions$24(java.util.function.Consumer consumer, java.lang.Boolean bool, java.lang.Throwable th) {
        if (th != null) {
            android.util.Log.e(TAG, "Error granting or upgrading runtime permissions", th);
            consumer.accept(false);
        } else {
            consumer.accept(java.lang.Boolean.valueOf(java.lang.Boolean.TRUE.equals(bool)));
        }
    }

    @java.lang.Deprecated
    public void getPrivilegesDescriptionStringForProfile(final java.lang.String str, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.CharSequence> consumer) {
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda2
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$getPrivilegesDescriptionStringForProfile$25(str, (android.permission.IPermissionController) obj);
            }
        }).whenCompleteAsync((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$getPrivilegesDescriptionStringForProfile$26(consumer, (java.lang.String) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$getPrivilegesDescriptionStringForProfile$25(java.lang.String str, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        iPermissionController.getPrivilegesDescriptionStringForProfile(str, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getPrivilegesDescriptionStringForProfile$26(java.util.function.Consumer consumer, java.lang.String str, java.lang.Throwable th) {
        if (th != null) {
            android.util.Log.e(TAG, "Error from getPrivilegesDescriptionStringForProfile", th);
            consumer.accept(null);
        } else {
            consumer.accept(str);
        }
    }

    public void updateUserSensitive() {
        updateUserSensitiveForApp(-1);
    }

    public void updateUserSensitiveForApp(final int i) {
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda0
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$updateUserSensitiveForApp$27(i, (android.permission.IPermissionController) obj);
            }
        }).whenComplete((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$updateUserSensitiveForApp$28(i, (java.lang.Void) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$updateUserSensitiveForApp$27(int i, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iPermissionController.updateUserSensitiveForApp(i, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$updateUserSensitiveForApp$28(int i, java.lang.Void r3, java.lang.Throwable th) {
        if (th != null) {
            android.util.Log.e(TAG, "Error updating user_sensitive flags for uid " + i, th);
        }
    }

    public void notifyOneTimePermissionSessionTimeout(final java.lang.String str, final int i) {
        this.mRemoteService.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda39
            @Override // com.android.internal.infra.ServiceConnector.VoidJob
            public final void runNoResult(java.lang.Object obj) {
                ((android.permission.IPermissionController) obj).notifyOneTimePermissionSessionTimeout(str, i);
            }
        });
    }

    public void getPlatformPermissionsForGroup(final java.lang.String str, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.util.List<java.lang.String>> consumer) {
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda18
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$getPlatformPermissionsForGroup$30(str, (android.permission.IPermissionController) obj);
            }
        }).whenCompleteAsync((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda19
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$getPlatformPermissionsForGroup$31(str, consumer, (java.util.List) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$getPlatformPermissionsForGroup$30(java.lang.String str, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture<java.util.List<java.lang.String>> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        iPermissionController.getPlatformPermissionsForGroup(str, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getPlatformPermissionsForGroup$31(java.lang.String str, java.util.function.Consumer consumer, java.util.List list, java.lang.Throwable th) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (th != null) {
                android.util.Log.e(TAG, "Failed to get permissions of " + str, th);
                consumer.accept(new java.util.ArrayList());
            } else {
                consumer.accept(list);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void getGroupOfPlatformPermission(final java.lang.String str, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.String> consumer) {
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda4
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$getGroupOfPlatformPermission$32(str, (android.permission.IPermissionController) obj);
            }
        }).whenCompleteAsync((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$getGroupOfPlatformPermission$33(str, consumer, (java.lang.String) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$getGroupOfPlatformPermission$32(java.lang.String str, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        iPermissionController.getGroupOfPlatformPermission(str, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getGroupOfPlatformPermission$33(java.lang.String str, java.util.function.Consumer consumer, java.lang.String str2, java.lang.Throwable th) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (th != null) {
                android.util.Log.e(TAG, "Failed to get group of " + str, th);
                consumer.accept(null);
            } else {
                consumer.accept(str2);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void getUnusedAppCount(java.util.concurrent.Executor executor, final java.util.function.IntConsumer intConsumer) {
        com.android.internal.util.Preconditions.checkNotNull(executor);
        com.android.internal.util.Preconditions.checkNotNull(intConsumer);
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda32
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$getUnusedAppCount$34((android.permission.IPermissionController) obj);
            }
        }).whenCompleteAsync((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda33
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$getUnusedAppCount$35(intConsumer, (java.lang.Integer) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$getUnusedAppCount$34(android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iPermissionController.getUnusedAppCount(androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getUnusedAppCount$35(java.util.function.IntConsumer intConsumer, java.lang.Integer num, java.lang.Throwable th) {
        if (th != null) {
            android.util.Log.e(TAG, "Error getting unused app count", th);
            intConsumer.accept(0);
        } else {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                intConsumer.accept(num.intValue());
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void getHibernationEligibility(final java.lang.String str, java.util.concurrent.Executor executor, final java.util.function.IntConsumer intConsumer) {
        com.android.internal.util.Preconditions.checkNotNull(executor);
        com.android.internal.util.Preconditions.checkNotNull(intConsumer);
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda25
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                return android.permission.PermissionControllerManager.lambda$getHibernationEligibility$36(str, (android.permission.IPermissionController) obj);
            }
        }).whenCompleteAsync((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda26
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.lambda$getHibernationEligibility$37(intConsumer, (java.lang.Integer) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ java.util.concurrent.CompletableFuture lambda$getHibernationEligibility$36(java.lang.String str, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iPermissionController.getHibernationEligibility(str, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getHibernationEligibility$37(java.util.function.IntConsumer intConsumer, java.lang.Integer num, java.lang.Throwable th) {
        if (th != null) {
            android.util.Log.e(TAG, "Error getting hibernation eligibility", th);
            intConsumer.accept(-1);
        } else {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                intConsumer.accept(num.intValue());
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void revokeSelfPermissionsOnKill(final java.lang.String str, final java.util.List<java.lang.String> list) {
        this.mRemoteService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda22
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$revokeSelfPermissionsOnKill$38;
                lambda$revokeSelfPermissionsOnKill$38 = android.permission.PermissionControllerManager.this.lambda$revokeSelfPermissionsOnKill$38(str, list, (android.permission.IPermissionController) obj);
                return lambda$revokeSelfPermissionsOnKill$38;
            }
        }).whenComplete((java.util.function.BiConsumer<? super R, ? super java.lang.Throwable>) new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda23
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.permission.PermissionControllerManager.this.lambda$revokeSelfPermissionsOnKill$39(list, str, (java.lang.Void) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.concurrent.CompletableFuture lambda$revokeSelfPermissionsOnKill$38(java.lang.String str, java.util.List list, android.permission.IPermissionController iPermissionController) throws java.lang.Exception {
        com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iPermissionController.revokeSelfPermissionsOnKill(str, list, this.mContext.getDeviceId(), androidFuture);
        return androidFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$revokeSelfPermissionsOnKill$39(java.util.List list, java.lang.String str, java.lang.Void r5, java.lang.Throwable th) {
        if (th != null) {
            android.util.Log.e(TAG, "Failed to self revoke " + java.lang.String.join(",", list) + " for package " + str + ", and device " + this.mContext.getDeviceId(), th);
        }
    }
}
