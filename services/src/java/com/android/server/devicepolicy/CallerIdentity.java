package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class CallerIdentity {

    @android.annotation.Nullable
    private final android.content.ComponentName mComponentName;

    @android.annotation.Nullable
    private final java.lang.String mPackageName;
    private final int mUid;

    CallerIdentity(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.content.ComponentName componentName) {
        this.mUid = i;
        this.mPackageName = str;
        this.mComponentName = componentName;
    }

    public int getUid() {
        return this.mUid;
    }

    public int getUserId() {
        return android.os.UserHandle.getUserId(this.mUid);
    }

    public android.os.UserHandle getUserHandle() {
        return android.os.UserHandle.getUserHandleForUid(this.mUid);
    }

    @android.annotation.Nullable
    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    @android.annotation.Nullable
    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public boolean hasAdminComponent() {
        return this.mComponentName != null;
    }

    public boolean hasPackage() {
        return this.mPackageName != null;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("CallerIdentity[uid=");
        sb.append(this.mUid);
        if (this.mPackageName != null) {
            sb.append(", pkg=");
            sb.append(this.mPackageName);
        }
        if (this.mComponentName != null) {
            sb.append(", cmp=");
            sb.append(this.mComponentName.flattenToShortString());
        }
        sb.append("]");
        return sb.toString();
    }
}
