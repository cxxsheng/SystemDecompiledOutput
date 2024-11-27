package com.android.server.devicepolicy;

/* loaded from: classes.dex */
public class DevicePolicyCacheImpl extends android.app.admin.DevicePolicyCache {
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mScreenCaptureDisallowedUser = com.android.server.am.ProcessList.INVALID_ADJ;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Set<java.lang.Integer> mScreenCaptureDisallowedUsers = new java.util.HashSet();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mPasswordQuality = new android.util.SparseIntArray();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mPermissionPolicy = new android.util.SparseIntArray();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArrayMap<java.lang.String, java.lang.String> mLauncherShortcutOverrides = new android.util.ArrayMap<>();
    private final java.util.concurrent.atomic.AtomicBoolean mCanGrantSensorsPermissions = new java.util.concurrent.atomic.AtomicBoolean(false);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mContentProtectionPolicy = new android.util.SparseIntArray();

    public void onUserRemoved(int i) {
        synchronized (this.mLock) {
            this.mPasswordQuality.delete(i);
            this.mPermissionPolicy.delete(i);
            this.mContentProtectionPolicy.delete(i);
        }
    }

    public boolean isScreenCaptureAllowed(int i) {
        return isScreenCaptureAllowedInPolicyEngine(i);
    }

    private boolean isScreenCaptureAllowedInPolicyEngine(int i) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = (this.mScreenCaptureDisallowedUsers.contains(java.lang.Integer.valueOf(i)) || this.mScreenCaptureDisallowedUsers.contains(-1)) ? false : true;
            } finally {
            }
        }
        return z;
    }

    public int getScreenCaptureDisallowedUser() {
        int i;
        synchronized (this.mLock) {
            i = this.mScreenCaptureDisallowedUser;
        }
        return i;
    }

    public void setScreenCaptureDisallowedUser(int i) {
        synchronized (this.mLock) {
            this.mScreenCaptureDisallowedUser = i;
        }
    }

    public void setScreenCaptureDisallowedUser(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    this.mScreenCaptureDisallowedUsers.add(java.lang.Integer.valueOf(i));
                } else {
                    this.mScreenCaptureDisallowedUsers.remove(java.lang.Integer.valueOf(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getPasswordQuality(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mPasswordQuality.get(i, 0);
        }
        return i2;
    }

    public void setPasswordQuality(int i, int i2) {
        synchronized (this.mLock) {
            this.mPasswordQuality.put(i, i2);
        }
    }

    public int getPermissionPolicy(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mPermissionPolicy.get(i, 0);
        }
        return i2;
    }

    public void setPermissionPolicy(int i, int i2) {
        synchronized (this.mLock) {
            this.mPermissionPolicy.put(i, i2);
        }
    }

    public int getContentProtectionPolicy(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mContentProtectionPolicy.get(i, 1);
        }
        return i2;
    }

    public void setContentProtectionPolicy(int i, @android.annotation.Nullable java.lang.Integer num) {
        synchronized (this.mLock) {
            try {
                if (num == null) {
                    this.mContentProtectionPolicy.delete(i);
                } else {
                    this.mContentProtectionPolicy.put(i, num.intValue());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean canAdminGrantSensorsPermissions() {
        return this.mCanGrantSensorsPermissions.get();
    }

    public void setAdminCanGrantSensorsPermissions(boolean z) {
        this.mCanGrantSensorsPermissions.set(z);
    }

    public java.util.Map<java.lang.String, java.lang.String> getLauncherShortcutOverrides() {
        android.util.ArrayMap arrayMap;
        synchronized (this.mLock) {
            arrayMap = new android.util.ArrayMap(this.mLauncherShortcutOverrides);
        }
        return arrayMap;
    }

    public void setLauncherShortcutOverrides(android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap) {
        synchronized (this.mLock) {
            this.mLauncherShortcutOverrides = new android.util.ArrayMap<>(arrayMap);
        }
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.println("Device policy cache:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Screen capture disallowed users: " + this.mScreenCaptureDisallowedUsers);
            indentingPrintWriter.println("Password quality: " + this.mPasswordQuality);
            indentingPrintWriter.println("Permission policy: " + this.mPermissionPolicy);
            indentingPrintWriter.println("Content protection policy: " + this.mContentProtectionPolicy);
            indentingPrintWriter.println("Admin can grant sensors permission: " + this.mCanGrantSensorsPermissions.get());
            indentingPrintWriter.print("Shortcuts overrides: ");
            indentingPrintWriter.println(this.mLauncherShortcutOverrides);
            indentingPrintWriter.decreaseIndent();
        }
    }
}
