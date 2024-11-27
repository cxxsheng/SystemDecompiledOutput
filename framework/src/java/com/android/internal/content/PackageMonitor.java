package com.android.internal.content;

/* loaded from: classes4.dex */
public abstract class PackageMonitor extends android.content.BroadcastReceiver {
    public static final int PACKAGE_PERMANENT_CHANGE = 3;
    public static final int PACKAGE_TEMPORARY_CHANGE = 2;
    public static final int PACKAGE_UNCHANGED = 0;
    public static final int PACKAGE_UPDATING = 1;
    static final java.lang.String TAG = "PackageMonitor";
    java.lang.String[] mAppearingPackages;
    int mChangeType;
    java.lang.String[] mDisappearingPackages;
    private java.util.concurrent.Executor mExecutor;
    java.lang.String[] mModifiedComponents;
    java.lang.String[] mModifiedPackages;
    final android.content.IntentFilter mPackageFilt;
    com.android.internal.content.PackageMonitor.PackageMonitorCallback mPackageMonitorCallback;
    android.content.Context mRegisteredContext;
    android.os.Handler mRegisteredHandler;
    boolean mSomePackagesChanged;
    int mChangeUserId = -10000;
    java.lang.String[] mTempArray = new java.lang.String[1];

    public PackageMonitor() {
        boolean isCore = android.os.UserHandle.isCore(android.os.Process.myUid());
        this.mPackageFilt = new android.content.IntentFilter();
        this.mPackageFilt.addAction(android.content.Intent.ACTION_QUERY_PACKAGE_RESTART);
        this.mPackageFilt.addDataScheme("package");
        if (isCore) {
            this.mPackageFilt.setPriority(1000);
        }
    }

    public void register(android.content.Context context, android.os.Looper looper, boolean z) {
        register(context, looper, null, z);
    }

    public void register(android.content.Context context, android.os.Looper looper, android.os.UserHandle userHandle, boolean z) {
        register(context, userHandle, looper == null ? com.android.internal.os.BackgroundThread.getHandler() : new android.os.Handler(looper));
    }

    public void register(android.content.Context context, android.os.UserHandle userHandle, android.os.Handler handler) {
        android.content.pm.PackageManager packageManager;
        if (this.mRegisteredContext != null) {
            throw new java.lang.IllegalStateException("Already registered");
        }
        this.mRegisteredContext = context;
        this.mRegisteredHandler = (android.os.Handler) java.util.Objects.requireNonNull(handler);
        if (userHandle != null) {
            context.registerReceiverAsUser(this, userHandle, this.mPackageFilt, null, this.mRegisteredHandler);
        } else {
            context.registerReceiver(this, this.mPackageFilt, null, this.mRegisteredHandler);
        }
        if (this.mPackageMonitorCallback == null && (packageManager = this.mRegisteredContext.getPackageManager()) != null) {
            this.mExecutor = new android.os.HandlerExecutor(this.mRegisteredHandler);
            this.mPackageMonitorCallback = new com.android.internal.content.PackageMonitor.PackageMonitorCallback(this);
            packageManager.registerPackageMonitorCallback(this.mPackageMonitorCallback, userHandle != null ? userHandle.getIdentifier() : this.mRegisteredContext.getUserId());
        }
    }

    public android.os.Handler getRegisteredHandler() {
        return this.mRegisteredHandler;
    }

    public void unregister() {
        if (this.mRegisteredContext == null) {
            throw new java.lang.IllegalStateException("Not registered");
        }
        this.mRegisteredContext.unregisterReceiver(this);
        android.content.pm.PackageManager packageManager = this.mRegisteredContext.getPackageManager();
        if (packageManager != null && this.mPackageMonitorCallback != null) {
            packageManager.unregisterPackageMonitorCallback(this.mPackageMonitorCallback);
        }
        this.mPackageMonitorCallback = null;
        this.mRegisteredContext = null;
        this.mExecutor = null;
    }

    public void onBeginPackageChanges() {
    }

    public void onPackageAdded(java.lang.String str, int i) {
    }

    public void onPackageRemoved(java.lang.String str, int i) {
    }

    public void onPackageRemovedAllUsers(java.lang.String str, int i) {
    }

    public void onPackageUpdateStarted(java.lang.String str, int i) {
    }

    public void onPackageUpdateFinished(java.lang.String str, int i) {
    }

    public boolean onPackageChanged(java.lang.String str, int i, java.lang.String[] strArr) {
        if (strArr != null) {
            for (java.lang.String str2 : strArr) {
                if (str.equals(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void onPackageChangedWithExtras(java.lang.String str, android.os.Bundle bundle) {
    }

    public boolean onHandleForceStop(android.content.Intent intent, java.lang.String[] strArr, int i, boolean z, android.os.Bundle bundle) {
        return onHandleForceStop(intent, strArr, i, z);
    }

    public boolean onHandleForceStop(android.content.Intent intent, java.lang.String[] strArr, int i, boolean z) {
        return false;
    }

    public void onUidRemoved(int i) {
    }

    public void onPackagesAvailable(java.lang.String[] strArr) {
    }

    public void onPackagesUnavailable(java.lang.String[] strArr) {
    }

    public void onPackagesSuspended(java.lang.String[] strArr) {
    }

    public void onPackagesUnsuspended(java.lang.String[] strArr) {
    }

    public void onPackageDisappeared(java.lang.String str, int i) {
    }

    public void onPackageDisappearedWithExtras(java.lang.String str, android.os.Bundle bundle) {
    }

    public void onPackageAppeared(java.lang.String str, int i) {
    }

    public void onPackageAppearedWithExtras(java.lang.String str, android.os.Bundle bundle) {
    }

    public void onPackageModified(java.lang.String str) {
    }

    public void onPackageUnstopped(java.lang.String str, int i, android.os.Bundle bundle) {
    }

    public boolean didSomePackagesChange() {
        return this.mSomePackagesChanged;
    }

    public int isPackageAppearing(java.lang.String str) {
        if (this.mAppearingPackages != null) {
            for (int length = this.mAppearingPackages.length - 1; length >= 0; length--) {
                if (str.equals(this.mAppearingPackages[length])) {
                    return this.mChangeType;
                }
            }
            return 0;
        }
        return 0;
    }

    public boolean anyPackagesAppearing() {
        return this.mAppearingPackages != null;
    }

    public int isPackageDisappearing(java.lang.String str) {
        if (this.mDisappearingPackages != null) {
            for (int length = this.mDisappearingPackages.length - 1; length >= 0; length--) {
                if (str.equals(this.mDisappearingPackages[length])) {
                    return this.mChangeType;
                }
            }
            return 0;
        }
        return 0;
    }

    public boolean anyPackagesDisappearing() {
        return this.mDisappearingPackages != null;
    }

    public boolean isReplacing() {
        return this.mChangeType == 1;
    }

    public boolean isPackageModified(java.lang.String str) {
        if (this.mModifiedPackages != null) {
            for (int length = this.mModifiedPackages.length - 1; length >= 0; length--) {
                if (str.equals(this.mModifiedPackages[length])) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean isComponentModified(java.lang.String str) {
        if (str == null || this.mModifiedComponents == null) {
            return false;
        }
        for (int length = this.mModifiedComponents.length - 1; length >= 0; length--) {
            if (str.equals(this.mModifiedComponents[length])) {
                return true;
            }
        }
        return false;
    }

    public void onSomePackagesChanged() {
    }

    public void onFinishPackageChanges() {
    }

    public void onPackageDataCleared(java.lang.String str, int i) {
    }

    public void onPackageStateChanged(java.lang.String str, int i) {
    }

    public int getChangingUserId() {
        return this.mChangeUserId;
    }

    java.lang.String getPackageName(android.content.Intent intent) {
        android.net.Uri data = intent.getData();
        if (data != null) {
            return data.getSchemeSpecificPart();
        }
        return null;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        lambda$postHandlePackageEvent$0(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postHandlePackageEvent(final android.content.Intent intent) {
        if (this.mExecutor != null) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.internal.content.PackageMonitor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.content.PackageMonitor.this.lambda$postHandlePackageEvent$0(intent);
                }
            });
        }
    }

    /* renamed from: doHandlePackageEvent, reason: merged with bridge method [inline-methods] */
    public void lambda$postHandlePackageEvent$0(android.content.Intent intent) {
        this.mChangeUserId = intent.getIntExtra(android.content.Intent.EXTRA_USER_HANDLE, -10000);
        if (this.mChangeUserId == -10000) {
            android.util.Slog.w(TAG, "Intent broadcast does not contain user handle: " + intent);
            return;
        }
        onBeginPackageChanges();
        this.mAppearingPackages = null;
        this.mDisappearingPackages = null;
        int i = 0;
        this.mSomePackagesChanged = false;
        this.mModifiedComponents = null;
        java.lang.String action = intent.getAction();
        if (android.content.Intent.ACTION_PACKAGE_ADDED.equals(action)) {
            java.lang.String packageName = getPackageName(intent);
            int intExtra = intent.getIntExtra(android.content.Intent.EXTRA_UID, 0);
            this.mSomePackagesChanged = true;
            if (packageName != null) {
                this.mAppearingPackages = this.mTempArray;
                this.mTempArray[0] = packageName;
                if (intent.getBooleanExtra(android.content.Intent.EXTRA_REPLACING, false)) {
                    this.mModifiedPackages = this.mTempArray;
                    this.mChangeType = 1;
                    onPackageUpdateFinished(packageName, intExtra);
                    onPackageModified(packageName);
                } else {
                    this.mChangeType = 3;
                    onPackageAdded(packageName, intExtra);
                }
                onPackageAppearedWithExtras(packageName, intent.getExtras());
                onPackageAppeared(packageName, this.mChangeType);
            }
        } else if (android.content.Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
            java.lang.String packageName2 = getPackageName(intent);
            int intExtra2 = intent.getIntExtra(android.content.Intent.EXTRA_UID, 0);
            if (packageName2 != null) {
                this.mDisappearingPackages = this.mTempArray;
                this.mTempArray[0] = packageName2;
                if (intent.getBooleanExtra(android.content.Intent.EXTRA_REPLACING, false)) {
                    this.mChangeType = 1;
                    onPackageUpdateStarted(packageName2, intExtra2);
                    if (intent.getBooleanExtra(android.content.Intent.EXTRA_ARCHIVAL, false)) {
                        onPackageModified(packageName2);
                    }
                } else {
                    this.mChangeType = 3;
                    this.mSomePackagesChanged = true;
                    onPackageRemoved(packageName2, intExtra2);
                    if (intent.getBooleanExtra(android.content.Intent.EXTRA_REMOVED_FOR_ALL_USERS, false)) {
                        onPackageRemovedAllUsers(packageName2, intExtra2);
                    }
                }
                onPackageDisappearedWithExtras(packageName2, intent.getExtras());
                onPackageDisappeared(packageName2, this.mChangeType);
            }
        } else if (android.content.Intent.ACTION_PACKAGE_CHANGED.equals(action)) {
            java.lang.String packageName3 = getPackageName(intent);
            int intExtra3 = intent.getIntExtra(android.content.Intent.EXTRA_UID, 0);
            this.mModifiedComponents = intent.getStringArrayExtra(android.content.Intent.EXTRA_CHANGED_COMPONENT_NAME_LIST);
            if (packageName3 != null) {
                this.mModifiedPackages = this.mTempArray;
                this.mTempArray[0] = packageName3;
                this.mChangeType = 3;
                if (onPackageChanged(packageName3, intExtra3, this.mModifiedComponents)) {
                    this.mSomePackagesChanged = true;
                }
                onPackageChangedWithExtras(packageName3, intent.getExtras());
                onPackageModified(packageName3);
            }
        } else if (android.content.Intent.ACTION_PACKAGE_DATA_CLEARED.equals(action)) {
            java.lang.String packageName4 = getPackageName(intent);
            int intExtra4 = intent.getIntExtra(android.content.Intent.EXTRA_UID, 0);
            if (packageName4 != null) {
                onPackageDataCleared(packageName4, intExtra4);
            }
        } else {
            if (android.content.Intent.ACTION_QUERY_PACKAGE_RESTART.equals(action)) {
                this.mDisappearingPackages = intent.getStringArrayExtra(android.content.Intent.EXTRA_PACKAGES);
                this.mChangeType = 2;
                if (onHandleForceStop(intent, this.mDisappearingPackages, intent.getIntExtra(android.content.Intent.EXTRA_UID, 0), false, intent.getExtras())) {
                    setResultCode(-1);
                }
            } else if (android.content.Intent.ACTION_PACKAGE_RESTARTED.equals(action)) {
                this.mDisappearingPackages = new java.lang.String[]{getPackageName(intent)};
                this.mChangeType = 2;
                onHandleForceStop(intent, this.mDisappearingPackages, intent.getIntExtra(android.content.Intent.EXTRA_UID, 0), true, intent.getExtras());
            } else if (android.content.Intent.ACTION_UID_REMOVED.equals(action)) {
                onUidRemoved(intent.getIntExtra(android.content.Intent.EXTRA_UID, 0));
            } else if (android.content.Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE.equals(action)) {
                java.lang.String[] stringArrayExtra = intent.getStringArrayExtra(android.content.Intent.EXTRA_CHANGED_PACKAGE_LIST);
                this.mAppearingPackages = stringArrayExtra;
                this.mChangeType = intent.getBooleanExtra(android.content.Intent.EXTRA_REPLACING, false) ? 1 : 2;
                this.mSomePackagesChanged = true;
                if (stringArrayExtra != null) {
                    onPackagesAvailable(stringArrayExtra);
                    while (i < stringArrayExtra.length) {
                        onPackageAppeared(stringArrayExtra[i], this.mChangeType);
                        i++;
                    }
                }
            } else if (android.content.Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE.equals(action)) {
                java.lang.String[] stringArrayExtra2 = intent.getStringArrayExtra(android.content.Intent.EXTRA_CHANGED_PACKAGE_LIST);
                this.mDisappearingPackages = stringArrayExtra2;
                this.mChangeType = intent.getBooleanExtra(android.content.Intent.EXTRA_REPLACING, false) ? 1 : 2;
                this.mSomePackagesChanged = true;
                if (stringArrayExtra2 != null) {
                    onPackagesUnavailable(stringArrayExtra2);
                    while (i < stringArrayExtra2.length) {
                        onPackageDisappeared(stringArrayExtra2[i], this.mChangeType);
                        i++;
                    }
                }
            } else if (android.content.Intent.ACTION_PACKAGES_SUSPENDED.equals(action)) {
                java.lang.String[] stringArrayExtra3 = intent.getStringArrayExtra(android.content.Intent.EXTRA_CHANGED_PACKAGE_LIST);
                this.mSomePackagesChanged = true;
                onPackagesSuspended(stringArrayExtra3);
            } else if (android.content.Intent.ACTION_PACKAGES_UNSUSPENDED.equals(action)) {
                java.lang.String[] stringArrayExtra4 = intent.getStringArrayExtra(android.content.Intent.EXTRA_CHANGED_PACKAGE_LIST);
                this.mSomePackagesChanged = true;
                onPackagesUnsuspended(stringArrayExtra4);
            } else if (android.content.Intent.ACTION_PACKAGE_UNSTOPPED.equals(action)) {
                java.lang.String packageName5 = getPackageName(intent);
                this.mAppearingPackages = new java.lang.String[]{packageName5};
                this.mChangeType = 2;
                onPackageUnstopped(packageName5, intent.getIntExtra(android.content.Intent.EXTRA_UID, 0), intent.getExtras());
            }
        }
        if (this.mSomePackagesChanged) {
            onSomePackagesChanged();
        }
        onFinishPackageChanges();
        this.mChangeUserId = -10000;
    }

    private static final class PackageMonitorCallback extends android.os.IRemoteCallback.Stub {
        private final java.lang.ref.WeakReference<com.android.internal.content.PackageMonitor> mMonitorWeakReference;

        PackageMonitorCallback(com.android.internal.content.PackageMonitor packageMonitor) {
            this.mMonitorWeakReference = new java.lang.ref.WeakReference<>(packageMonitor);
        }

        @Override // android.os.IRemoteCallback
        public void sendResult(android.os.Bundle bundle) throws android.os.RemoteException {
            onHandlePackageMonitorCallback(bundle);
        }

        private void onHandlePackageMonitorCallback(android.os.Bundle bundle) {
            android.content.Intent intent = (android.content.Intent) bundle.getParcelable(android.content.pm.PackageManager.EXTRA_PACKAGE_MONITOR_CALLBACK_RESULT, android.content.Intent.class);
            if (intent == null) {
                android.util.Log.w(com.android.internal.content.PackageMonitor.TAG, "No intent is set for PackageMonitorCallback");
                return;
            }
            com.android.internal.content.PackageMonitor packageMonitor = this.mMonitorWeakReference.get();
            if (packageMonitor != null) {
                packageMonitor.postHandlePackageEvent(intent);
            }
        }
    }
}
