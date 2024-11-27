package com.android.internal.telephony;

/* loaded from: classes5.dex */
public abstract class PackageChangeReceiver extends android.content.BroadcastReceiver {
    private static android.os.HandlerThread sHandlerThread;
    static final android.content.IntentFilter sPackageIntentFilter = new android.content.IntentFilter();
    android.content.Context mRegisteredContext;

    static {
        sPackageIntentFilter.addAction(android.content.Intent.ACTION_PACKAGE_ADDED);
        sPackageIntentFilter.addAction(android.content.Intent.ACTION_PACKAGE_REMOVED);
        sPackageIntentFilter.addAction(android.content.Intent.ACTION_PACKAGE_CHANGED);
        sPackageIntentFilter.addAction(android.content.Intent.ACTION_QUERY_PACKAGE_RESTART);
        sPackageIntentFilter.addAction(android.content.Intent.ACTION_PACKAGE_RESTARTED);
        sPackageIntentFilter.addDataScheme("package");
    }

    public void register(android.content.Context context, android.os.Looper looper, android.os.UserHandle userHandle) {
        if (this.mRegisteredContext != null) {
            throw new java.lang.IllegalStateException("Already registered");
        }
        if (looper == null) {
            looper = getStaticLooper();
        }
        android.os.Handler handler = new android.os.Handler(looper);
        if (userHandle != null) {
            context = context.createContextAsUser(userHandle, 0);
        }
        this.mRegisteredContext = context;
        this.mRegisteredContext.registerReceiver(this, sPackageIntentFilter, null, handler);
    }

    public void unregister() {
        if (this.mRegisteredContext == null) {
            throw new java.lang.IllegalStateException("Not registered");
        }
        this.mRegisteredContext.unregisterReceiver(this);
        this.mRegisteredContext = null;
    }

    private static synchronized android.os.Looper getStaticLooper() {
        android.os.Looper looper;
        synchronized (com.android.internal.telephony.PackageChangeReceiver.class) {
            if (sHandlerThread == null) {
                sHandlerThread = new android.os.HandlerThread(com.android.internal.telephony.PackageChangeReceiver.class.getSimpleName());
                sHandlerThread.start();
            }
            looper = sHandlerThread.getLooper();
        }
        return looper;
    }

    public void onPackageAdded(java.lang.String str) {
    }

    public void onPackageRemoved(java.lang.String str) {
    }

    public void onPackageUpdateFinished(java.lang.String str) {
    }

    public void onPackageModified(java.lang.String str) {
    }

    public void onHandleForceStop(java.lang.String[] strArr, boolean z) {
    }

    public void onPackageDisappeared() {
    }

    public void onPackageAppeared() {
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        java.lang.String action = intent.getAction();
        if (android.content.Intent.ACTION_PACKAGE_ADDED.equals(action)) {
            java.lang.String packageName = getPackageName(intent);
            if (packageName != null) {
                if (intent.getBooleanExtra(android.content.Intent.EXTRA_REPLACING, false)) {
                    onPackageUpdateFinished(packageName);
                    onPackageModified(packageName);
                } else {
                    onPackageAdded(packageName);
                }
                onPackageAppeared();
                return;
            }
            return;
        }
        if (android.content.Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
            java.lang.String packageName2 = getPackageName(intent);
            if (packageName2 != null) {
                if (!intent.getBooleanExtra(android.content.Intent.EXTRA_REPLACING, false)) {
                    onPackageRemoved(packageName2);
                }
                onPackageDisappeared();
                return;
            }
            return;
        }
        if (android.content.Intent.ACTION_PACKAGE_CHANGED.equals(action)) {
            java.lang.String packageName3 = getPackageName(intent);
            if (packageName3 != null) {
                onPackageModified(packageName3);
                return;
            }
            return;
        }
        if (android.content.Intent.ACTION_QUERY_PACKAGE_RESTART.equals(action)) {
            onHandleForceStop(intent.getStringArrayExtra(android.content.Intent.EXTRA_PACKAGES), false);
        } else if (android.content.Intent.ACTION_PACKAGE_RESTARTED.equals(action)) {
            onHandleForceStop(new java.lang.String[]{getPackageName(intent)}, true);
        }
    }

    java.lang.String getPackageName(android.content.Intent intent) {
        android.net.Uri data = intent.getData();
        if (data != null) {
            return data.getSchemeSpecificPart();
        }
        return null;
    }
}
