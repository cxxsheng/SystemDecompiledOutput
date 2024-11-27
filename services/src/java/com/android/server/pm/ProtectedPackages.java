package com.android.server.pm;

/* loaded from: classes2.dex */
public class ProtectedPackages {

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private java.lang.String mDeviceOwnerPackage;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mDeviceOwnerUserId;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private final java.lang.String mDeviceProvisioningPackage;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private final android.util.SparseArray<java.util.Set<java.lang.String>> mOwnerProtectedPackages = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.util.SparseArray<java.lang.String> mProfileOwnerPackages;

    public ProtectedPackages(android.content.Context context) {
        this.mDeviceProvisioningPackage = context.getResources().getString(android.R.string.config_defaultTranslationService);
    }

    public synchronized void setDeviceAndProfileOwnerPackages(int i, java.lang.String str, android.util.SparseArray<java.lang.String> sparseArray) {
        this.mDeviceOwnerUserId = i;
        android.util.SparseArray<java.lang.String> sparseArray2 = null;
        if (i == -10000) {
            str = null;
        }
        this.mDeviceOwnerPackage = str;
        if (sparseArray != null) {
            sparseArray2 = sparseArray.clone();
        }
        this.mProfileOwnerPackages = sparseArray2;
    }

    public synchronized void setOwnerProtectedPackages(int i, @android.annotation.Nullable java.util.List<java.lang.String> list) {
        try {
            if (list == null) {
                this.mOwnerProtectedPackages.remove(i);
            } else {
                this.mOwnerProtectedPackages.put(i, new android.util.ArraySet(list));
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private synchronized boolean hasDeviceOwnerOrProfileOwner(int i, java.lang.String str) {
        if (str == null) {
            return false;
        }
        if (this.mDeviceOwnerPackage != null && this.mDeviceOwnerUserId == i && str.equals(this.mDeviceOwnerPackage)) {
            return true;
        }
        if (this.mProfileOwnerPackages != null) {
            if (str.equals(this.mProfileOwnerPackages.get(i))) {
                return true;
            }
        }
        return false;
    }

    public synchronized java.lang.String getDeviceOwnerOrProfileOwnerPackage(int i) {
        if (this.mDeviceOwnerUserId == i) {
            return this.mDeviceOwnerPackage;
        }
        if (this.mProfileOwnerPackages == null) {
            return null;
        }
        return this.mProfileOwnerPackages.get(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x000f, code lost:
    
        if (isOwnerProtectedPackage(r2, r3) != false) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized boolean isProtectedPackage(int i, java.lang.String str) {
        boolean z;
        if (str != null) {
            if (!str.equals(this.mDeviceProvisioningPackage)) {
            }
            z = true;
        }
        z = false;
        return z;
    }

    private synchronized boolean isOwnerProtectedPackage(int i, java.lang.String str) {
        boolean isPackageProtectedForUser;
        try {
            if (hasProtectedPackages(i)) {
                isPackageProtectedForUser = isPackageProtectedForUser(i, str);
            } else {
                isPackageProtectedForUser = isPackageProtectedForUser(-1, str);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return isPackageProtectedForUser;
    }

    private synchronized boolean isPackageProtectedForUser(int i, java.lang.String str) {
        boolean z;
        int indexOfKey = this.mOwnerProtectedPackages.indexOfKey(i);
        if (indexOfKey >= 0) {
            z = this.mOwnerProtectedPackages.valueAt(indexOfKey).contains(str);
        }
        return z;
    }

    private synchronized boolean hasProtectedPackages(int i) {
        return this.mOwnerProtectedPackages.indexOfKey(i) >= 0;
    }

    public boolean isPackageStateProtected(int i, java.lang.String str) {
        return hasDeviceOwnerOrProfileOwner(i, str) || isProtectedPackage(i, str);
    }

    public boolean isPackageDataProtected(int i, java.lang.String str) {
        return hasDeviceOwnerOrProfileOwner(i, str) || isProtectedPackage(i, str);
    }
}
