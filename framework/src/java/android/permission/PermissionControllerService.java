package android.permission;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class PermissionControllerService extends android.app.Service {
    private static final long CAMERA_MIC_INDICATORS_NOT_PRESENT = 162547999;
    private static final java.lang.String LOG_TAG = android.permission.PermissionControllerService.class.getSimpleName();
    public static final java.lang.String SERVICE_INTERFACE = "android.permission.PermissionControllerService";

    public abstract void onCountPermissionApps(java.util.List<java.lang.String> list, int i, java.util.function.IntConsumer intConsumer);

    public abstract void onGetAppPermissions(java.lang.String str, java.util.function.Consumer<java.util.List<android.permission.RuntimePermissionPresentationInfo>> consumer);

    public abstract void onGetPermissionUsages(boolean z, long j, java.util.function.Consumer<java.util.List<android.permission.RuntimePermissionUsageInfo>> consumer);

    public abstract void onGetRuntimePermissionsBackup(android.os.UserHandle userHandle, java.io.OutputStream outputStream, java.lang.Runnable runnable);

    public abstract void onGrantOrUpgradeDefaultRuntimePermissions(java.lang.Runnable runnable);

    public abstract void onRevokeRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.Runnable runnable);

    public abstract void onRevokeRuntimePermissions(java.util.Map<java.lang.String, java.util.List<java.lang.String>> map, boolean z, int i, java.lang.String str, java.util.function.Consumer<java.util.Map<java.lang.String, java.util.List<java.lang.String>>> consumer);

    @java.lang.Deprecated
    public abstract void onSetRuntimePermissionGrantStateByDeviceAdmin(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.util.function.Consumer<java.lang.Boolean> consumer);

    @java.lang.Deprecated
    public void onRestoreRuntimePermissionsBackup(android.os.UserHandle userHandle, java.io.InputStream inputStream, java.lang.Runnable runnable) {
    }

    public void onStageAndApplyRuntimePermissionsBackup(android.os.UserHandle userHandle, java.io.InputStream inputStream, java.lang.Runnable runnable) {
        onRestoreRuntimePermissionsBackup(userHandle, inputStream, runnable);
    }

    @java.lang.Deprecated
    public void onRestoreDelayedRuntimePermissionsBackup(java.lang.String str, android.os.UserHandle userHandle, java.util.function.Consumer<java.lang.Boolean> consumer) {
    }

    public void onApplyStagedRuntimePermissionBackup(java.lang.String str, android.os.UserHandle userHandle, java.util.function.Consumer<java.lang.Boolean> consumer) {
        onRestoreDelayedRuntimePermissionsBackup(str, userHandle, consumer);
    }

    public void onUpdateUserSensitivePermissionFlags(int i, java.util.concurrent.Executor executor, java.lang.Runnable runnable) {
        throw new java.lang.AbstractMethodError("Must be overridden in implementing class");
    }

    public void onUpdateUserSensitivePermissionFlags(int i, java.lang.Runnable runnable) {
        onUpdateUserSensitivePermissionFlags(i, getMainExecutor(), runnable);
    }

    public void onSetRuntimePermissionGrantStateByDeviceAdmin(java.lang.String str, android.permission.AdminPermissionControlParams adminPermissionControlParams, java.util.function.Consumer<java.lang.Boolean> consumer) {
        throw new java.lang.AbstractMethodError("Must be overridden in implementing class");
    }

    @java.lang.Deprecated
    public void onOneTimePermissionSessionTimeout(java.lang.String str) {
        throw new java.lang.AbstractMethodError("Must be overridden in implementing class");
    }

    public void onOneTimePermissionSessionTimeout(java.lang.String str, int i) {
        onOneTimePermissionSessionTimeout(str);
    }

    public void onGetPlatformPermissionsForGroup(java.lang.String str, java.util.function.Consumer<java.util.List<java.lang.String>> consumer) {
        throw new java.lang.AbstractMethodError("Must be overridden in implementing class");
    }

    public void onGetGroupOfPlatformPermission(java.lang.String str, java.util.function.Consumer<java.lang.String> consumer) {
        throw new java.lang.AbstractMethodError("Must be overridden in implementing class");
    }

    @java.lang.Deprecated
    public void onRevokeSelfPermissionsOnKill(java.lang.String str, java.util.List<java.lang.String> list, java.lang.Runnable runnable) {
        throw new java.lang.AbstractMethodError("Must be overridden in implementing class");
    }

    public void onRevokeSelfPermissionsOnKill(java.lang.String str, java.util.List<java.lang.String> list, int i, java.lang.Runnable runnable) {
        onRevokeSelfPermissionsOnKill(str, list, runnable);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.lang.String getPrivilegesDescriptionStringForProfile(java.lang.String str) {
        throw new java.lang.AbstractMethodError("Must be overridden in implementing class");
    }

    @android.annotation.SystemApi
    public void onGetUnusedAppCount(java.util.function.IntConsumer intConsumer) {
        throw new java.lang.AbstractMethodError("Must be overridden in implementing class");
    }

    @android.annotation.SystemApi
    public void onGetHibernationEligibility(java.lang.String str, java.util.function.IntConsumer intConsumer) {
        throw new java.lang.AbstractMethodError("Must be overridden in implementing class");
    }

    /* renamed from: android.permission.PermissionControllerService$1, reason: invalid class name */
    class AnonymousClass1 extends android.permission.IPermissionController.Stub {
        AnonymousClass1() {
        }

        @Override // android.permission.IPermissionController
        public void revokeRuntimePermissions(android.os.Bundle bundle, boolean z, int i, java.lang.String str, final com.android.internal.infra.AndroidFuture androidFuture) {
            com.android.internal.util.Preconditions.checkNotNull(bundle, "bundleizedRequest");
            com.android.internal.util.Preconditions.checkNotNull(str);
            com.android.internal.util.Preconditions.checkNotNull(androidFuture);
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            for (java.lang.String str2 : bundle.keySet()) {
                com.android.internal.util.Preconditions.checkNotNull(str2);
                java.util.ArrayList<java.lang.String> stringArrayList = bundle.getStringArrayList(str2);
                com.android.internal.util.Preconditions.checkCollectionElementsNotNull(stringArrayList, "permissions");
                arrayMap.put(str2, stringArrayList);
            }
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
            try {
                com.android.internal.util.Preconditions.checkArgument(getCallingUid() == android.permission.PermissionControllerService.this.getPackageManager().getPackageInfo(str, 0).applicationInfo.uid);
                android.permission.PermissionControllerService.this.onRevokeRuntimePermissions(arrayMap, z, i, str, new java.util.function.Consumer() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.permission.PermissionControllerService.AnonymousClass1.lambda$revokeRuntimePermissions$1(com.android.internal.infra.AndroidFuture.this, (java.util.Map) obj);
                    }
                });
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        static /* synthetic */ void lambda$revokeRuntimePermissions$1(com.android.internal.infra.AndroidFuture androidFuture, java.util.Map map) {
            com.android.internal.util.CollectionUtils.forEach(map, new java.util.function.BiConsumer() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda7
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    android.permission.PermissionControllerService.AnonymousClass1.lambda$revokeRuntimePermissions$0((java.lang.String) obj, (java.util.List) obj2);
                }
            });
            androidFuture.complete(map);
        }

        static /* synthetic */ void lambda$revokeRuntimePermissions$0(java.lang.String str, java.util.List list) {
            com.android.internal.util.Preconditions.checkNotNull(str);
            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "permissions");
        }

        private void enforceSomePermissionsGrantedToCaller(java.lang.String... strArr) {
            for (java.lang.String str : strArr) {
                if (android.permission.PermissionControllerService.this.checkCallingPermission(str) == 0) {
                    return;
                }
            }
            throw new java.lang.SecurityException("At lest one of the following permissions is required: " + java.util.Arrays.toString(strArr));
        }

        @Override // android.permission.IPermissionController
        public void getRuntimePermissionBackup(android.os.UserHandle userHandle, android.os.ParcelFileDescriptor parcelFileDescriptor) {
            com.android.internal.util.Preconditions.checkNotNull(userHandle);
            com.android.internal.util.Preconditions.checkNotNull(parcelFileDescriptor);
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.GET_RUNTIME_PERMISSIONS);
            try {
                android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                try {
                    java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
                    android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
                    java.util.Objects.requireNonNull(countDownLatch);
                    permissionControllerService.onGetRuntimePermissionsBackup(userHandle, autoCloseOutputStream, new android.permission.PermissionControllerService$1$$ExternalSyntheticLambda0(countDownLatch));
                    countDownLatch.await();
                    autoCloseOutputStream.close();
                } catch (java.lang.Throwable th) {
                    try {
                        autoCloseOutputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.io.IOException e) {
                android.util.Log.e(android.permission.PermissionControllerService.LOG_TAG, "Could not open pipe to write backup to", e);
            } catch (java.lang.InterruptedException e2) {
                android.util.Log.e(android.permission.PermissionControllerService.LOG_TAG, "getRuntimePermissionBackup timed out", e2);
            }
        }

        @Override // android.permission.IPermissionController
        public void stageAndApplyRuntimePermissionsBackup(android.os.UserHandle userHandle, android.os.ParcelFileDescriptor parcelFileDescriptor) {
            com.android.internal.util.Preconditions.checkNotNull(userHandle);
            com.android.internal.util.Preconditions.checkNotNull(parcelFileDescriptor);
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.GRANT_RUNTIME_PERMISSIONS, android.Manifest.permission.RESTORE_RUNTIME_PERMISSIONS);
            try {
                android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
                try {
                    java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
                    android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
                    java.util.Objects.requireNonNull(countDownLatch);
                    permissionControllerService.onStageAndApplyRuntimePermissionsBackup(userHandle, autoCloseInputStream, new android.permission.PermissionControllerService$1$$ExternalSyntheticLambda0(countDownLatch));
                    countDownLatch.await();
                    autoCloseInputStream.close();
                } catch (java.lang.Throwable th) {
                    try {
                        autoCloseInputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.io.IOException e) {
                android.util.Log.e(android.permission.PermissionControllerService.LOG_TAG, "Could not open pipe to read backup from", e);
            } catch (java.lang.InterruptedException e2) {
                android.util.Log.e(android.permission.PermissionControllerService.LOG_TAG, "restoreRuntimePermissionBackup timed out", e2);
            }
        }

        @Override // android.permission.IPermissionController
        public void applyStagedRuntimePermissionBackup(java.lang.String str, android.os.UserHandle userHandle, com.android.internal.infra.AndroidFuture androidFuture) {
            com.android.internal.util.Preconditions.checkNotNull(str);
            com.android.internal.util.Preconditions.checkNotNull(userHandle);
            com.android.internal.util.Preconditions.checkNotNull(androidFuture);
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.GRANT_RUNTIME_PERMISSIONS, android.Manifest.permission.RESTORE_RUNTIME_PERMISSIONS);
            android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
            java.util.Objects.requireNonNull(androidFuture);
            permissionControllerService.onApplyStagedRuntimePermissionBackup(str, userHandle, new android.permission.PermissionControllerService$1$$ExternalSyntheticLambda6(androidFuture));
        }

        @Override // android.permission.IPermissionController
        public void getAppPermissions(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) {
            com.android.internal.util.Preconditions.checkNotNull(str, "packageName");
            com.android.internal.util.Preconditions.checkNotNull(androidFuture, "callback");
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.GET_RUNTIME_PERMISSIONS);
            android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
            java.util.Objects.requireNonNull(androidFuture);
            permissionControllerService.onGetAppPermissions(str, new android.permission.PermissionControllerService$1$$ExternalSyntheticLambda1(androidFuture));
        }

        @Override // android.permission.IPermissionController
        public void revokeRuntimePermission(java.lang.String str, java.lang.String str2) {
            com.android.internal.util.Preconditions.checkNotNull(str, "packageName");
            com.android.internal.util.Preconditions.checkNotNull(str2, "permissionName");
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
            java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
            android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
            java.util.Objects.requireNonNull(countDownLatch);
            permissionControllerService.onRevokeRuntimePermission(str, str2, new android.permission.PermissionControllerService$1$$ExternalSyntheticLambda0(countDownLatch));
            try {
                countDownLatch.await();
            } catch (java.lang.InterruptedException e) {
                android.util.Log.e(android.permission.PermissionControllerService.LOG_TAG, "revokeRuntimePermission timed out", e);
            }
        }

        @Override // android.permission.IPermissionController
        public void countPermissionApps(java.util.List<java.lang.String> list, int i, com.android.internal.infra.AndroidFuture androidFuture) {
            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "permissionNames");
            com.android.internal.util.Preconditions.checkFlagsArgument(i, 3);
            com.android.internal.util.Preconditions.checkNotNull(androidFuture, "callback");
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.GET_RUNTIME_PERMISSIONS);
            android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
            java.util.Objects.requireNonNull(androidFuture);
            permissionControllerService.onCountPermissionApps(list, i, new android.permission.PermissionControllerService$1$$ExternalSyntheticLambda4(androidFuture));
        }

        @Override // android.permission.IPermissionController
        public void getPermissionUsages(boolean z, long j, com.android.internal.infra.AndroidFuture androidFuture) {
            com.android.internal.util.Preconditions.checkArgumentNonnegative(j);
            com.android.internal.util.Preconditions.checkNotNull(androidFuture, "callback");
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.GET_RUNTIME_PERMISSIONS);
            android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
            java.util.Objects.requireNonNull(androidFuture);
            permissionControllerService.onGetPermissionUsages(z, j, new android.permission.PermissionControllerService$1$$ExternalSyntheticLambda1(androidFuture));
        }

        @Override // android.permission.IPermissionController
        public void setRuntimePermissionGrantStateByDeviceAdminFromParams(java.lang.String str, android.permission.AdminPermissionControlParams adminPermissionControlParams, com.android.internal.infra.AndroidFuture androidFuture) {
            com.android.internal.util.Preconditions.checkStringNotEmpty(str);
            if (adminPermissionControlParams.getGrantState() == 1) {
                enforceSomePermissionsGrantedToCaller(android.Manifest.permission.GRANT_RUNTIME_PERMISSIONS);
            }
            if (adminPermissionControlParams.getGrantState() == 2) {
                enforceSomePermissionsGrantedToCaller(android.Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
            }
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY);
            com.android.internal.util.Preconditions.checkNotNull(androidFuture);
            android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
            java.util.Objects.requireNonNull(androidFuture);
            permissionControllerService.onSetRuntimePermissionGrantStateByDeviceAdmin(str, adminPermissionControlParams, new android.permission.PermissionControllerService$1$$ExternalSyntheticLambda6(androidFuture));
        }

        @Override // android.permission.IPermissionController
        public void grantOrUpgradeDefaultRuntimePermissions(final com.android.internal.infra.AndroidFuture androidFuture) {
            com.android.internal.util.Preconditions.checkNotNull(androidFuture, "callback");
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY);
            android.permission.PermissionControllerService.this.onGrantOrUpgradeDefaultRuntimePermissions(new java.lang.Runnable() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.infra.AndroidFuture.this.complete(true);
                }
            });
        }

        @Override // android.permission.IPermissionController
        public void updateUserSensitiveForApp(int i, final com.android.internal.infra.AndroidFuture androidFuture) {
            com.android.internal.util.Preconditions.checkNotNull(androidFuture, "callback cannot be null");
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY);
            try {
                android.permission.PermissionControllerService.this.onUpdateUserSensitivePermissionFlags(i, new java.lang.Runnable() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.infra.AndroidFuture.this.complete(null);
                    }
                });
            } catch (java.lang.Exception e) {
                androidFuture.completeExceptionally(e);
            }
        }

        @Override // android.permission.IPermissionController
        public void notifyOneTimePermissionSessionTimeout(java.lang.String str, int i) {
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
            android.permission.PermissionControllerService.this.onOneTimePermissionSessionTimeout((java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str, "packageName cannot be null"), i);
        }

        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            com.android.internal.util.Preconditions.checkNotNull(fileDescriptor, "fd");
            com.android.internal.util.Preconditions.checkNotNull(printWriter, "writer");
            enforceSomePermissionsGrantedToCaller(android.Manifest.permission.GET_RUNTIME_PERMISSIONS);
            android.permission.PermissionControllerService.this.dump(fileDescriptor, printWriter, strArr);
        }

        @Override // android.permission.IPermissionController
        public void getPrivilegesDescriptionStringForProfile(java.lang.String str, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) {
            try {
                com.android.internal.util.Preconditions.checkStringNotEmpty(str);
                java.util.Objects.requireNonNull(androidFuture);
                enforceSomePermissionsGrantedToCaller(android.Manifest.permission.MANAGE_COMPANION_DEVICES);
                androidFuture.complete(android.permission.PermissionControllerService.this.getPrivilegesDescriptionStringForProfile(str));
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        @Override // android.permission.IPermissionController
        public void getPlatformPermissionsForGroup(java.lang.String str, com.android.internal.infra.AndroidFuture<java.util.List<java.lang.String>> androidFuture) {
            try {
                java.util.Objects.requireNonNull(str);
                java.util.Objects.requireNonNull(androidFuture);
                android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
                java.util.Objects.requireNonNull(androidFuture);
                permissionControllerService.onGetPlatformPermissionsForGroup(str, new android.permission.PermissionControllerService$1$$ExternalSyntheticLambda1(androidFuture));
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        @Override // android.permission.IPermissionController
        public void getGroupOfPlatformPermission(java.lang.String str, final com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) {
            try {
                java.util.Objects.requireNonNull(str);
                java.util.Objects.requireNonNull(androidFuture);
                android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
                java.util.Objects.requireNonNull(androidFuture);
                permissionControllerService.onGetGroupOfPlatformPermission(str, new java.util.function.Consumer() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.internal.infra.AndroidFuture.this.complete((java.lang.String) obj);
                    }
                });
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        @Override // android.permission.IPermissionController
        public void getUnusedAppCount(com.android.internal.infra.AndroidFuture androidFuture) {
            try {
                java.util.Objects.requireNonNull(androidFuture);
                enforceSomePermissionsGrantedToCaller(android.Manifest.permission.MANAGE_APP_HIBERNATION);
                android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
                java.util.Objects.requireNonNull(androidFuture);
                permissionControllerService.onGetUnusedAppCount(new android.permission.PermissionControllerService$1$$ExternalSyntheticLambda4(androidFuture));
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        @Override // android.permission.IPermissionController
        public void getHibernationEligibility(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) {
            try {
                java.util.Objects.requireNonNull(androidFuture);
                enforceSomePermissionsGrantedToCaller(android.Manifest.permission.MANAGE_APP_HIBERNATION);
                android.permission.PermissionControllerService permissionControllerService = android.permission.PermissionControllerService.this;
                java.util.Objects.requireNonNull(androidFuture);
                permissionControllerService.onGetHibernationEligibility(str, new android.permission.PermissionControllerService$1$$ExternalSyntheticLambda4(androidFuture));
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        @Override // android.permission.IPermissionController
        public void revokeSelfPermissionsOnKill(java.lang.String str, java.util.List<java.lang.String> list, int i, final com.android.internal.infra.AndroidFuture androidFuture) {
            try {
                java.util.Objects.requireNonNull(androidFuture);
                if (android.permission.PermissionControllerService.this.getPackageManager().getPackageUid(str, android.content.pm.PackageManager.PackageInfoFlags.of(0L)) != android.os.Binder.getCallingUid()) {
                    enforceSomePermissionsGrantedToCaller(android.Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
                }
                android.permission.PermissionControllerService.this.onRevokeSelfPermissionsOnKill(str, list, i, new java.lang.Runnable() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.infra.AndroidFuture.this.complete(null);
                    }
                });
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.permission.PermissionControllerService.AnonymousClass1();
    }
}
