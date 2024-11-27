package com.android.server.appop;

/* loaded from: classes.dex */
public class AppOpsCheckingServiceLoggingDecorator implements com.android.server.appop.AppOpsCheckingServiceInterface {
    private static final java.lang.String LOG_TAG = com.android.server.appop.AppOpsCheckingServiceLoggingDecorator.class.getSimpleName();

    @android.annotation.NonNull
    private final com.android.server.appop.AppOpsCheckingServiceInterface mService;

    public AppOpsCheckingServiceLoggingDecorator(@android.annotation.NonNull com.android.server.appop.AppOpsCheckingServiceInterface appOpsCheckingServiceInterface) {
        this.mService = appOpsCheckingServiceInterface;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void writeState() {
        android.util.Log.i(LOG_TAG, "writeState()");
        this.mService.writeState();
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void readState() {
        android.util.Log.i(LOG_TAG, "readState()");
        this.mService.readState();
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void shutdown() {
        android.util.Log.i(LOG_TAG, "shutdown()");
        this.mService.shutdown();
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void systemReady() {
        android.util.Log.i(LOG_TAG, "systemReady()");
        this.mService.systemReady();
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseIntArray getNonDefaultUidModes(int i, java.lang.String str) {
        android.util.Log.i(LOG_TAG, "getNonDefaultUidModes(uid = " + i + ")");
        return this.mService.getNonDefaultUidModes(i, str);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseIntArray getNonDefaultPackageModes(java.lang.String str, int i) {
        android.util.Log.i(LOG_TAG, "getNonDefaultPackageModes(packageName = " + str + ", userId = " + i + ") ");
        return this.mService.getNonDefaultPackageModes(str, i);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getUidMode(int i, java.lang.String str, int i2) {
        android.util.Log.i(LOG_TAG, "getUidMode(uid = " + i + ", op = " + i2 + ")");
        return this.mService.getUidMode(i, str, i2);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean setUidMode(int i, java.lang.String str, int i2, int i3) {
        android.util.Log.i(LOG_TAG, "setUidMode(uid = " + i + ", op = " + i2 + ", mode = " + i3 + ")");
        return this.mService.setUidMode(i, str, i2, i3);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getPackageMode(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        android.util.Log.i(LOG_TAG, "getPackageMode(packageName = " + str + ", op = " + i + ", userId = " + i2 + ")");
        return this.mService.getPackageMode(str, i, i2);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void setPackageMode(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3) {
        android.util.Log.i(LOG_TAG, "setPackageMode(packageName = " + str + ", op = " + i + ", mode = " + i2 + ", userId = " + i3 + ")");
        this.mService.setPackageMode(str, i, i2, i3);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removePackage(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.Log.i(LOG_TAG, "removePackage(packageName = " + str + ", userId = " + i + ")");
        return this.mService.removePackage(str, i);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void removeUid(int i) {
        android.util.Log.i(LOG_TAG, "removeUid(uid = " + i + ")");
        this.mService.removeUid(i);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void clearAllModes() {
        android.util.Log.i(LOG_TAG, "clearAllModes()");
        this.mService.clearAllModes();
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseBooleanArray getForegroundOps(int i, java.lang.String str) {
        android.util.Log.i(LOG_TAG, "getForegroundOps(uid = " + i + ")");
        return this.mService.getForegroundOps(i, str);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseBooleanArray getForegroundOps(java.lang.String str, int i) {
        android.util.Log.i(LOG_TAG, "getForegroundOps(packageName = " + str + ", userId = " + i + ")");
        return this.mService.getForegroundOps(str, i);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean addAppOpsModeChangedListener(com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener appOpsModeChangedListener) {
        android.util.Log.i(LOG_TAG, "addAppOpsModeChangedListener(listener = " + appOpsModeChangedListener + ")");
        return this.mService.addAppOpsModeChangedListener(appOpsModeChangedListener);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removeAppOpsModeChangedListener(com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener appOpsModeChangedListener) {
        android.util.Log.i(LOG_TAG, "removeAppOpsModeChangedListener(listener = " + appOpsModeChangedListener + ")");
        return this.mService.removeAppOpsModeChangedListener(appOpsModeChangedListener);
    }
}
