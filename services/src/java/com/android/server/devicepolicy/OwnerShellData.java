package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class OwnerShellData {
    public final android.content.ComponentName admin;
    public boolean isAffiliated;
    public final boolean isDeviceOwner;
    public final boolean isManagedProfileOwner;
    public final boolean isProfileOwner;
    public final int parentUserId;
    public final int userId;

    private OwnerShellData(int i, int i2, android.content.ComponentName componentName, boolean z, boolean z2, boolean z3) {
        com.android.internal.util.Preconditions.checkArgument(i != -10000, "userId cannot be USER_NULL");
        this.userId = i;
        this.parentUserId = i2;
        java.util.Objects.requireNonNull(componentName, "admin must not be null");
        this.admin = componentName;
        this.isDeviceOwner = z;
        this.isProfileOwner = z2;
        this.isManagedProfileOwner = z3;
        if (z3) {
            com.android.internal.util.Preconditions.checkArgument(i2 != -10000, "parentUserId cannot be USER_NULL for managed profile owner");
            com.android.internal.util.Preconditions.checkArgument(i2 != i, "cannot be parent of itself (%d)", new java.lang.Object[]{java.lang.Integer.valueOf(i)});
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(com.android.server.devicepolicy.OwnerShellData.class.getSimpleName());
        sb.append("[userId=");
        sb.append(this.userId);
        sb.append(",admin=");
        sb.append(this.admin.flattenToShortString());
        if (this.isDeviceOwner) {
            sb.append(",deviceOwner");
        }
        if (this.isProfileOwner) {
            sb.append(",isProfileOwner");
        }
        if (this.isManagedProfileOwner) {
            sb.append(",isManagedProfileOwner");
        }
        if (this.parentUserId != -10000) {
            sb.append(",parentUserId=");
            sb.append(this.parentUserId);
        }
        if (this.isAffiliated) {
            sb.append(",isAffiliated");
        }
        sb.append(']');
        return sb.toString();
    }

    static com.android.server.devicepolicy.OwnerShellData forDeviceOwner(int i, android.content.ComponentName componentName) {
        return new com.android.server.devicepolicy.OwnerShellData(i, com.android.server.am.ProcessList.INVALID_ADJ, componentName, true, false, false);
    }

    static com.android.server.devicepolicy.OwnerShellData forUserProfileOwner(int i, android.content.ComponentName componentName) {
        return new com.android.server.devicepolicy.OwnerShellData(i, com.android.server.am.ProcessList.INVALID_ADJ, componentName, false, true, false);
    }

    static com.android.server.devicepolicy.OwnerShellData forManagedProfileOwner(int i, int i2, android.content.ComponentName componentName) {
        return new com.android.server.devicepolicy.OwnerShellData(i, i2, componentName, false, false, true);
    }
}
