package com.android.server.wm;

/* loaded from: classes3.dex */
public class SensitiveContentPackages {
    private final android.util.ArraySet<com.android.server.wm.SensitiveContentPackages.PackageInfo> mProtectedPackages = new android.util.ArraySet<>();

    public boolean shouldBlockScreenCaptureForApp(java.lang.String str, int i, android.os.IBinder iBinder) {
        if (!android.view.flags.Flags.sensitiveContentAppProtection() && !android.permission.flags.Flags.sensitiveNotificationAppProtection()) {
            return false;
        }
        for (int i2 = 0; i2 < this.mProtectedPackages.size(); i2++) {
            com.android.server.wm.SensitiveContentPackages.PackageInfo valueAt = this.mProtectedPackages.valueAt(i2);
            if (valueAt != null && valueAt.mPkg.equals(str) && valueAt.mUid == i) {
                if (!android.view.flags.Flags.sensitiveContentAppProtection() || iBinder != valueAt.getWindowToken()) {
                    if (android.permission.flags.Flags.sensitiveNotificationAppProtection() && valueAt.getWindowToken() == null) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addBlockScreenCaptureForApps(@android.annotation.NonNull android.util.ArraySet<com.android.server.wm.SensitiveContentPackages.PackageInfo> arraySet) {
        if (this.mProtectedPackages.equals(arraySet)) {
            return false;
        }
        this.mProtectedPackages.addAll((android.util.ArraySet<? extends com.android.server.wm.SensitiveContentPackages.PackageInfo>) arraySet);
        return true;
    }

    public boolean removeBlockScreenCaptureForApps(@android.annotation.NonNull android.util.ArraySet<com.android.server.wm.SensitiveContentPackages.PackageInfo> arraySet) {
        return this.mProtectedPackages.removeAll((android.util.ArraySet<? extends com.android.server.wm.SensitiveContentPackages.PackageInfo>) arraySet);
    }

    public boolean clearBlockedApps() {
        if (this.mProtectedPackages.isEmpty()) {
            return false;
        }
        this.mProtectedPackages.clear();
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    public int size() {
        return this.mProtectedPackages.size();
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("SensitiveContentPackages:");
        printWriter.println("  Packages that should block screen capture (" + this.mProtectedPackages.size() + "):");
        java.util.Iterator<com.android.server.wm.SensitiveContentPackages.PackageInfo> it = this.mProtectedPackages.iterator();
        while (it.hasNext()) {
            com.android.server.wm.SensitiveContentPackages.PackageInfo next = it.next();
            printWriter.println("    package=" + next.mPkg + "  uid=" + next.mUid + " windowToken=" + next.mWindowToken);
        }
    }

    public static class PackageInfo {
        private final java.lang.String mPkg;
        private final int mUid;

        @android.annotation.Nullable
        private final android.os.IBinder mWindowToken;

        public PackageInfo(java.lang.String str, int i) {
            this(str, i, null);
        }

        public PackageInfo(java.lang.String str, int i, android.os.IBinder iBinder) {
            this.mPkg = str;
            this.mUid = i;
            this.mWindowToken = iBinder;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.wm.SensitiveContentPackages.PackageInfo)) {
                return false;
            }
            com.android.server.wm.SensitiveContentPackages.PackageInfo packageInfo = (com.android.server.wm.SensitiveContentPackages.PackageInfo) obj;
            return this.mUid == packageInfo.mUid && java.util.Objects.equals(this.mPkg, packageInfo.mPkg) && java.util.Objects.equals(this.mWindowToken, packageInfo.mWindowToken);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mPkg, java.lang.Integer.valueOf(this.mUid), this.mWindowToken);
        }

        public android.os.IBinder getWindowToken() {
            return this.mWindowToken;
        }

        public int getUid() {
            return this.mUid;
        }

        public java.lang.String getPkg() {
            return this.mPkg;
        }

        public java.lang.String toString() {
            return "package=" + this.mPkg + "  uid=" + this.mUid + " windowToken=" + this.mWindowToken;
        }
    }
}
