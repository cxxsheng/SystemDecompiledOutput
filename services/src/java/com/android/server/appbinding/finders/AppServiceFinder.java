package com.android.server.appbinding.finders;

/* loaded from: classes.dex */
public abstract class AppServiceFinder<TServiceType, TServiceInterfaceType extends android.os.IInterface> {
    protected static final boolean DEBUG = false;
    protected static final java.lang.String TAG = "AppBindingService";
    protected final android.content.Context mContext;
    protected final android.os.Handler mHandler;
    protected final java.util.function.BiConsumer<com.android.server.appbinding.finders.AppServiceFinder, java.lang.Integer> mListener;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.lang.String> mTargetPackages = new android.util.SparseArray<>(4);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.content.pm.ServiceInfo> mTargetServices = new android.util.SparseArray<>(4);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.lang.String> mLastMessages = new android.util.SparseArray<>(4);

    public abstract TServiceInterfaceType asInterface(android.os.IBinder iBinder);

    @android.annotation.NonNull
    public abstract java.lang.String getAppDescription();

    public abstract int getBindFlags(com.android.server.appbinding.AppBindingConstants appBindingConstants);

    @android.annotation.NonNull
    protected abstract java.lang.String getServiceAction();

    protected abstract java.lang.Class<TServiceType> getServiceClass();

    @android.annotation.NonNull
    protected abstract java.lang.String getServicePermission();

    @android.annotation.Nullable
    public abstract java.lang.String getTargetPackage(int i);

    public AppServiceFinder(android.content.Context context, java.util.function.BiConsumer<com.android.server.appbinding.finders.AppServiceFinder, java.lang.Integer> biConsumer, android.os.Handler handler) {
        this.mContext = context;
        this.mListener = biConsumer;
        this.mHandler = handler;
    }

    protected boolean isEnabled(com.android.server.appbinding.AppBindingConstants appBindingConstants) {
        return true;
    }

    public void startMonitoring() {
    }

    public void onUserRemoved(int i) {
        synchronized (this.mLock) {
            this.mTargetPackages.delete(i);
            this.mTargetServices.delete(i);
            this.mLastMessages.delete(i);
        }
    }

    @android.annotation.Nullable
    public final android.content.pm.ServiceInfo findService(int i, android.content.pm.IPackageManager iPackageManager, com.android.server.appbinding.AppBindingConstants appBindingConstants) {
        synchronized (this.mLock) {
            try {
                this.mTargetPackages.put(i, null);
                this.mTargetServices.put(i, null);
                this.mLastMessages.put(i, null);
                if (!isEnabled(appBindingConstants)) {
                    this.mLastMessages.put(i, "feature disabled");
                    android.util.Slog.i("AppBindingService", getAppDescription() + " feature disabled");
                    return null;
                }
                java.lang.String targetPackage = getTargetPackage(i);
                if (targetPackage == null) {
                    this.mLastMessages.put(i, "Target package not found");
                    android.util.Slog.w("AppBindingService", getAppDescription() + " u" + i + " Target package not found");
                    return null;
                }
                this.mTargetPackages.put(i, targetPackage);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                android.content.pm.ServiceInfo findService = com.android.server.appbinding.AppBindingUtils.findService(targetPackage, i, getServiceAction(), getServicePermission(), getServiceClass(), iPackageManager, sb);
                if (findService == null) {
                    this.mLastMessages.put(i, sb.toString());
                    return null;
                }
                java.lang.String validateService = validateService(findService);
                if (validateService != null) {
                    this.mLastMessages.put(i, validateService);
                    android.util.Log.e("AppBindingService", validateService);
                    return null;
                }
                this.mLastMessages.put(i, "Valid service found");
                this.mTargetServices.put(i, findService);
                return findService;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected java.lang.String validateService(android.content.pm.ServiceInfo serviceInfo) {
        return null;
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("App type: ");
        printWriter.print(getAppDescription());
        printWriter.println();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mTargetPackages.size(); i++) {
                try {
                    int keyAt = this.mTargetPackages.keyAt(i);
                    printWriter.print(str);
                    printWriter.print("  User: ");
                    printWriter.print(keyAt);
                    printWriter.println();
                    printWriter.print(str);
                    printWriter.print("    Package: ");
                    printWriter.print(this.mTargetPackages.get(keyAt));
                    printWriter.println();
                    printWriter.print(str);
                    printWriter.print("    Service: ");
                    printWriter.print(this.mTargetServices.get(keyAt));
                    printWriter.println();
                    printWriter.print(str);
                    printWriter.print("    Message: ");
                    printWriter.print(this.mLastMessages.get(keyAt));
                    printWriter.println();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void dumpSimple(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mTargetPackages.size(); i++) {
                try {
                    int keyAt = this.mTargetPackages.keyAt(i);
                    printWriter.print("finder,");
                    printWriter.print(getAppDescription());
                    printWriter.print(",");
                    printWriter.print(keyAt);
                    printWriter.print(",");
                    printWriter.print(this.mTargetPackages.get(keyAt));
                    printWriter.print(",");
                    printWriter.print(this.mTargetServices.get(keyAt));
                    printWriter.print(",");
                    printWriter.print(this.mLastMessages.get(keyAt));
                    printWriter.println();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
