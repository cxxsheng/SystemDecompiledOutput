package com.android.server.appop;

/* loaded from: classes.dex */
public class AppOpsServiceTestingShim implements com.android.server.appop.AppOpsCheckingServiceInterface {
    private com.android.server.appop.AppOpsCheckingServiceInterface mNewImplementation;
    private com.android.server.appop.AppOpsCheckingServiceInterface mOldImplementation;

    public AppOpsServiceTestingShim(com.android.server.appop.AppOpsCheckingServiceInterface appOpsCheckingServiceInterface, com.android.server.appop.AppOpsCheckingServiceInterface appOpsCheckingServiceInterface2) {
        this.mOldImplementation = appOpsCheckingServiceInterface;
        this.mNewImplementation = appOpsCheckingServiceInterface2;
    }

    private void signalImplDifference(java.lang.String str) {
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void writeState() {
        this.mOldImplementation.writeState();
        this.mNewImplementation.writeState();
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void readState() {
        this.mOldImplementation.readState();
        this.mNewImplementation.readState();
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void shutdown() {
        this.mOldImplementation.shutdown();
        this.mNewImplementation.shutdown();
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void systemReady() {
        this.mOldImplementation.systemReady();
        this.mNewImplementation.systemReady();
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseIntArray getNonDefaultUidModes(int i, java.lang.String str) {
        android.util.SparseIntArray nonDefaultUidModes = this.mOldImplementation.getNonDefaultUidModes(i, str);
        android.util.SparseIntArray nonDefaultUidModes2 = this.mNewImplementation.getNonDefaultUidModes(i, str);
        if (!java.util.Objects.equals(nonDefaultUidModes, nonDefaultUidModes2)) {
            signalImplDifference("getNonDefaultUidModes");
        }
        return nonDefaultUidModes2;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseIntArray getNonDefaultPackageModes(java.lang.String str, int i) {
        android.util.SparseIntArray nonDefaultPackageModes = this.mOldImplementation.getNonDefaultPackageModes(str, i);
        android.util.SparseIntArray nonDefaultPackageModes2 = this.mNewImplementation.getNonDefaultPackageModes(str, i);
        if (!java.util.Objects.equals(nonDefaultPackageModes, nonDefaultPackageModes2)) {
            signalImplDifference("getNonDefaultPackageModes");
        }
        return nonDefaultPackageModes2;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getUidMode(int i, java.lang.String str, int i2) {
        int uidMode = this.mOldImplementation.getUidMode(i, str, i2);
        int uidMode2 = this.mNewImplementation.getUidMode(i, str, i2);
        if (uidMode != uidMode2) {
            signalImplDifference("getUidMode");
        }
        return uidMode2;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean setUidMode(int i, java.lang.String str, int i2, int i3) {
        boolean uidMode = this.mOldImplementation.setUidMode(i, str, i2, i3);
        boolean uidMode2 = this.mNewImplementation.setUidMode(i, str, i2, i3);
        if (uidMode != uidMode2) {
            signalImplDifference("setUidMode");
        }
        return uidMode2;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getPackageMode(java.lang.String str, int i, int i2) {
        int packageMode = this.mOldImplementation.getPackageMode(str, i, i2);
        int packageMode2 = this.mNewImplementation.getPackageMode(str, i, i2);
        if (packageMode != packageMode2) {
            signalImplDifference("getPackageMode");
        }
        return packageMode2;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void setPackageMode(java.lang.String str, int i, int i2, int i3) {
        this.mOldImplementation.setPackageMode(str, i, i2, i3);
        this.mNewImplementation.setPackageMode(str, i, i2, i3);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removePackage(java.lang.String str, int i) {
        boolean removePackage = this.mOldImplementation.removePackage(str, i);
        boolean removePackage2 = this.mNewImplementation.removePackage(str, i);
        if (removePackage != removePackage2) {
            signalImplDifference("removePackage");
        }
        return removePackage2;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void removeUid(int i) {
        this.mOldImplementation.removeUid(i);
        this.mNewImplementation.removeUid(i);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void clearAllModes() {
        this.mOldImplementation.clearAllModes();
        this.mNewImplementation.clearAllModes();
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseBooleanArray getForegroundOps(int i, java.lang.String str) {
        android.util.SparseBooleanArray foregroundOps = this.mOldImplementation.getForegroundOps(i, str);
        android.util.SparseBooleanArray foregroundOps2 = this.mNewImplementation.getForegroundOps(i, str);
        if (!java.util.Objects.equals(foregroundOps, foregroundOps2)) {
            signalImplDifference("getForegroundOps");
        }
        return foregroundOps2;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public android.util.SparseBooleanArray getForegroundOps(java.lang.String str, int i) {
        android.util.SparseBooleanArray foregroundOps = this.mOldImplementation.getForegroundOps(str, i);
        android.util.SparseBooleanArray foregroundOps2 = this.mNewImplementation.getForegroundOps(str, i);
        if (!java.util.Objects.equals(foregroundOps, foregroundOps2)) {
            signalImplDifference("getForegroundOps");
        }
        return foregroundOps2;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean addAppOpsModeChangedListener(com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener appOpsModeChangedListener) {
        boolean addAppOpsModeChangedListener = this.mOldImplementation.addAppOpsModeChangedListener(appOpsModeChangedListener);
        boolean addAppOpsModeChangedListener2 = this.mNewImplementation.addAppOpsModeChangedListener(appOpsModeChangedListener);
        if (addAppOpsModeChangedListener != addAppOpsModeChangedListener2) {
            signalImplDifference("addAppOpsModeChangedListener");
        }
        return addAppOpsModeChangedListener2;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removeAppOpsModeChangedListener(com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener appOpsModeChangedListener) {
        boolean removeAppOpsModeChangedListener = this.mOldImplementation.removeAppOpsModeChangedListener(appOpsModeChangedListener);
        boolean removeAppOpsModeChangedListener2 = this.mNewImplementation.removeAppOpsModeChangedListener(appOpsModeChangedListener);
        if (removeAppOpsModeChangedListener != removeAppOpsModeChangedListener2) {
            signalImplDifference("removeAppOpsModeChangedListener");
        }
        return removeAppOpsModeChangedListener2;
    }
}
