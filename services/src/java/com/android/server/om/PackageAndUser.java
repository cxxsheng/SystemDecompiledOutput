package com.android.server.om;

/* loaded from: classes2.dex */
final class PackageAndUser {

    @android.annotation.NonNull
    public final java.lang.String packageName;
    public final int userId;

    PackageAndUser(@android.annotation.NonNull java.lang.String str, int i) {
        this.packageName = str;
        this.userId = i;
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.om.PackageAndUser)) {
            return false;
        }
        com.android.server.om.PackageAndUser packageAndUser = (com.android.server.om.PackageAndUser) obj;
        return this.packageName.equals(packageAndUser.packageName) && this.userId == packageAndUser.userId;
    }

    public int hashCode() {
        return ((this.packageName.hashCode() + 31) * 31) + this.userId;
    }

    public java.lang.String toString() {
        return java.lang.String.format("PackageAndUser{packageName=%s, userId=%d}", this.packageName, java.lang.Integer.valueOf(this.userId));
    }
}
