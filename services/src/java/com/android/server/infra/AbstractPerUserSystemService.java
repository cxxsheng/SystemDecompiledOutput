package com.android.server.infra;

/* loaded from: classes2.dex */
public abstract class AbstractPerUserSystemService<S extends com.android.server.infra.AbstractPerUserSystemService<S, M>, M extends com.android.server.infra.AbstractMasterSystemService<M, S>> {

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDisabled;
    public final java.lang.Object mLock;
    protected final M mMaster;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.pm.ServiceInfo mServiceInfo;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSetupComplete;
    protected final java.lang.String mTag = getClass().getSimpleName();
    protected final int mUserId;

    protected AbstractPerUserSystemService(@android.annotation.NonNull M m, @android.annotation.NonNull java.lang.Object obj, int i) {
        this.mMaster = m;
        this.mLock = obj;
        this.mUserId = i;
        updateIsSetupComplete(i);
    }

    private void updateIsSetupComplete(int i) {
        this.mSetupComplete = "1".equals(android.provider.Settings.Secure.getStringForUser(getContext().getContentResolver(), "user_setup_complete", i));
    }

    @android.annotation.NonNull
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("not overridden");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handlePackageUpdateLocked(@android.annotation.NonNull java.lang.String str) {
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean isEnabledLocked() {
        return (!this.mSetupComplete || this.mServiceInfo == null || this.mDisabled) ? false : true;
    }

    protected final boolean isDisabledByUserRestrictionsLocked() {
        return this.mDisabled;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean updateLocked(boolean z) {
        boolean isEnabledLocked = isEnabledLocked();
        if (this.mMaster.verbose) {
            android.util.Slog.v(this.mTag, "updateLocked(u=" + this.mUserId + "): wasEnabled=" + isEnabledLocked + ", mSetupComplete=" + this.mSetupComplete + ", disabled=" + z + ", mDisabled=" + this.mDisabled);
        }
        updateIsSetupComplete(this.mUserId);
        this.mDisabled = z;
        if (this.mMaster.mServiceNameResolver != null && this.mMaster.mServiceNameResolver.isConfiguredInMultipleMode()) {
            if (this.mMaster.debug) {
                android.util.Slog.d(this.mTag, "Should not end up in updateLocked when isConfiguredInMultipleMode is true");
            }
        } else {
            updateServiceInfoLocked();
        }
        return isEnabledLocked != isEnabledLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final android.content.ComponentName updateServiceInfoLocked() {
        android.content.ComponentName[] updateServiceInfoListLocked = updateServiceInfoListLocked();
        if (updateServiceInfoListLocked == null || updateServiceInfoListLocked.length == 0) {
            return null;
        }
        return updateServiceInfoListLocked[0];
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final android.content.ComponentName[] updateServiceInfoListLocked() {
        if (this.mMaster.mServiceNameResolver == null) {
            return null;
        }
        if (!this.mMaster.mServiceNameResolver.isConfiguredInMultipleMode()) {
            return new android.content.ComponentName[]{getServiceComponent(getComponentNameLocked())};
        }
        java.lang.String[] serviceNameList = this.mMaster.mServiceNameResolver.getServiceNameList(this.mUserId);
        if (serviceNameList == null) {
            return null;
        }
        android.content.ComponentName[] componentNameArr = new android.content.ComponentName[serviceNameList.length];
        for (int i = 0; i < serviceNameList.length; i++) {
            componentNameArr[i] = getServiceComponent(serviceNameList[i]);
        }
        return componentNameArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0062 A[Catch: all -> 0x0035, Exception -> 0x0099, TRY_ENTER, TryCatch #2 {Exception -> 0x0099, blocks: (B:10:0x0062, B:12:0x006e, B:19:0x009b, B:21:0x00a3), top: B:8:0x0060, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x009b A[Catch: all -> 0x0035, Exception -> 0x0099, TryCatch #2 {Exception -> 0x0099, blocks: (B:10:0x0062, B:12:0x006e, B:19:0x009b, B:21:0x00a3), top: B:8:0x0060, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.ComponentName getServiceComponent(java.lang.String str) {
        android.content.ComponentName componentName;
        android.content.pm.ServiceInfo serviceInfo;
        synchronized (this.mLock) {
            if (android.text.TextUtils.isEmpty(str)) {
                componentName = null;
                serviceInfo = null;
            } else {
                try {
                    componentName = android.content.ComponentName.unflattenFromString(str);
                    try {
                        serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 0L, this.mUserId);
                        if (serviceInfo == null) {
                            android.util.Slog.e(this.mTag, "Bad service name: " + str);
                        }
                    } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                        e = e;
                        android.util.Slog.e(this.mTag, "Error getting service info for '" + str + "': " + e);
                        serviceInfo = null;
                        if (serviceInfo == null) {
                        }
                        return componentName;
                    }
                } catch (android.os.RemoteException | java.lang.RuntimeException e2) {
                    e = e2;
                    componentName = null;
                }
            }
            try {
                if (serviceInfo == null) {
                    this.mServiceInfo = newServiceInfoLocked(componentName);
                    if (this.mMaster.debug) {
                        android.util.Slog.d(this.mTag, "Set component for user " + this.mUserId + " as " + componentName + " and info as " + this.mServiceInfo);
                    }
                } else {
                    this.mServiceInfo = null;
                    if (this.mMaster.debug) {
                        android.util.Slog.d(this.mTag, "Reset component for user " + this.mUserId + ":" + str);
                    }
                }
            } catch (java.lang.Exception e3) {
                android.util.Slog.e(this.mTag, "Bad ServiceInfo for '" + str + "': " + e3);
                this.mServiceInfo = null;
            }
        }
        return componentName;
    }

    public final int getUserId() {
        return this.mUserId;
    }

    public final M getMaster() {
        return this.mMaster;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final int getServiceUidLocked() {
        if (this.mServiceInfo == null) {
            if (this.mMaster.verbose) {
                android.util.Slog.v(this.mTag, "getServiceUidLocked(): no mServiceInfo");
                return -1;
            }
            return -1;
        }
        return this.mServiceInfo.applicationInfo.uid;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    protected final java.lang.String getComponentNameLocked() {
        return this.mMaster.mServiceNameResolver.getServiceName(this.mUserId);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    protected final java.lang.String getComponentNameForMultipleLocked(java.lang.String str) {
        java.lang.String[] serviceNameList = this.mMaster.mServiceNameResolver.getServiceNameList(this.mUserId);
        for (int i = 0; i < serviceNameList.length; i++) {
            if (str.equals(serviceNameList[i])) {
                return serviceNameList[i];
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public final boolean isTemporaryServiceSetLocked() {
        return this.mMaster.mServiceNameResolver.isTemporary(this.mUserId);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final void resetTemporaryServiceLocked() {
        this.mMaster.mServiceNameResolver.resetTemporaryService(this.mUserId);
    }

    @android.annotation.Nullable
    public final android.content.pm.ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    @android.annotation.Nullable
    public final android.content.ComponentName getServiceComponentName() {
        android.content.ComponentName componentName;
        synchronized (this.mLock) {
            componentName = this.mServiceInfo == null ? null : this.mServiceInfo.getComponentName();
        }
        return componentName;
    }

    @android.annotation.Nullable
    public final java.lang.String getServicePackageName() {
        android.content.ComponentName serviceComponentName = getServiceComponentName();
        if (serviceComponentName == null) {
            return null;
        }
        return serviceComponentName.getPackageName();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public final java.lang.CharSequence getServiceLabelLocked() {
        if (this.mServiceInfo == null) {
            return null;
        }
        return this.mServiceInfo.loadSafeLabel(getContext().getPackageManager(), com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 5);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public final android.graphics.drawable.Drawable getServiceIconLocked() {
        if (this.mServiceInfo == null) {
            return null;
        }
        return this.mServiceInfo.loadIcon(getContext().getPackageManager());
    }

    protected final void removeSelfFromCache() {
        synchronized (this.mMaster.mLock) {
            this.mMaster.removeCachedServiceListLocked(this.mUserId);
        }
    }

    public final boolean isDebug() {
        return this.mMaster.debug;
    }

    public final boolean isVerbose() {
        return this.mMaster.verbose;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public final int getTargedSdkLocked() {
        if (this.mServiceInfo == null) {
            return 0;
        }
        return this.mServiceInfo.applicationInfo.targetSdkVersion;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final boolean isSetupCompletedLocked() {
        return this.mSetupComplete;
    }

    protected final android.content.Context getContext() {
        return this.mMaster.getContext();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void dumpLocked(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("User: ");
        printWriter.println(this.mUserId);
        if (this.mServiceInfo != null) {
            printWriter.print(str);
            printWriter.print("Service Label: ");
            printWriter.println(getServiceLabelLocked());
            printWriter.print(str);
            printWriter.print("Target SDK: ");
            printWriter.println(getTargedSdkLocked());
        }
        if (this.mMaster.mServiceNameResolver != null) {
            printWriter.print(str);
            printWriter.print("Name resolver: ");
            this.mMaster.mServiceNameResolver.dumpShort(printWriter, this.mUserId);
            printWriter.println();
        }
        printWriter.print(str);
        printWriter.print("Disabled by UserManager: ");
        printWriter.println(this.mDisabled);
        printWriter.print(str);
        printWriter.print("Setup complete: ");
        printWriter.println(this.mSetupComplete);
        if (this.mServiceInfo != null) {
            printWriter.print(str);
            printWriter.print("Service UID: ");
            printWriter.println(this.mServiceInfo.applicationInfo.uid);
        }
        printWriter.println();
    }
}
