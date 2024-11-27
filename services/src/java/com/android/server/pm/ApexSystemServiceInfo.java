package com.android.server.pm;

/* loaded from: classes2.dex */
public final class ApexSystemServiceInfo implements java.lang.Comparable<com.android.server.pm.ApexSystemServiceInfo> {
    final int mInitOrder;

    @android.annotation.Nullable
    final java.lang.String mJarPath;
    final java.lang.String mName;

    public ApexSystemServiceInfo(java.lang.String str, java.lang.String str2, int i) {
        this.mName = str;
        this.mJarPath = str2;
        this.mInitOrder = i;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String getJarPath() {
        return this.mJarPath;
    }

    public int getInitOrder() {
        return this.mInitOrder;
    }

    @Override // java.lang.Comparable
    public int compareTo(com.android.server.pm.ApexSystemServiceInfo apexSystemServiceInfo) {
        if (this.mInitOrder == apexSystemServiceInfo.mInitOrder) {
            return this.mName.compareTo(apexSystemServiceInfo.mName);
        }
        return -java.lang.Integer.compare(this.mInitOrder, apexSystemServiceInfo.mInitOrder);
    }
}
