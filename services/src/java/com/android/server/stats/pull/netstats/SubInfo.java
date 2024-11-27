package com.android.server.stats.pull.netstats;

/* loaded from: classes2.dex */
public final class SubInfo {
    public final int carrierId;
    public final boolean isOpportunistic;

    @android.annotation.NonNull
    public final java.lang.String mcc;

    @android.annotation.NonNull
    public final java.lang.String mnc;
    public final int subId;

    @android.annotation.NonNull
    public final java.lang.String subscriberId;

    public SubInfo(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, boolean z) {
        this.subId = i;
        this.carrierId = i2;
        this.mcc = str;
        this.mnc = str2;
        this.subscriberId = str3;
        this.isOpportunistic = z;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || com.android.server.stats.pull.netstats.SubInfo.class != obj.getClass()) {
            return false;
        }
        com.android.server.stats.pull.netstats.SubInfo subInfo = (com.android.server.stats.pull.netstats.SubInfo) obj;
        if (this.subId == subInfo.subId && this.carrierId == subInfo.carrierId && this.isOpportunistic == subInfo.isOpportunistic && this.mcc.equals(subInfo.mcc) && this.mnc.equals(subInfo.mnc) && this.subscriberId.equals(subInfo.subscriberId)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.subId), this.mcc, this.mnc, java.lang.Integer.valueOf(this.carrierId), this.subscriberId, java.lang.Boolean.valueOf(this.isOpportunistic));
    }
}
