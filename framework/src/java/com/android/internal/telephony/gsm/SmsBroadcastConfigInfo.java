package com.android.internal.telephony.gsm;

/* loaded from: classes5.dex */
public final class SmsBroadcastConfigInfo {
    private int mFromCodeScheme;
    private int mFromServiceId;
    private boolean mSelected;
    private int mToCodeScheme;
    private int mToServiceId;

    public SmsBroadcastConfigInfo(int i, int i2, int i3, int i4, boolean z) {
        this.mFromServiceId = i;
        this.mToServiceId = i2;
        this.mFromCodeScheme = i3;
        this.mToCodeScheme = i4;
        this.mSelected = z;
    }

    public void setFromServiceId(int i) {
        this.mFromServiceId = i;
    }

    public int getFromServiceId() {
        return this.mFromServiceId;
    }

    public void setToServiceId(int i) {
        this.mToServiceId = i;
    }

    public int getToServiceId() {
        return this.mToServiceId;
    }

    public void setFromCodeScheme(int i) {
        this.mFromCodeScheme = i;
    }

    public int getFromCodeScheme() {
        return this.mFromCodeScheme;
    }

    public void setToCodeScheme(int i) {
        this.mToCodeScheme = i;
    }

    public int getToCodeScheme() {
        return this.mToCodeScheme;
    }

    public void setSelected(boolean z) {
        this.mSelected = z;
    }

    public boolean isSelected() {
        return this.mSelected;
    }

    public java.lang.String toString() {
        return "SmsBroadcastConfigInfo: Id [" + this.mFromServiceId + ',' + this.mToServiceId + "] Code [" + this.mFromCodeScheme + ',' + this.mToCodeScheme + "] " + (this.mSelected ? "ENABLED" : "DISABLED");
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mFromServiceId), java.lang.Integer.valueOf(this.mToServiceId), java.lang.Integer.valueOf(this.mFromCodeScheme), java.lang.Integer.valueOf(this.mToCodeScheme), java.lang.Boolean.valueOf(this.mSelected));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.telephony.gsm.SmsBroadcastConfigInfo)) {
            return false;
        }
        com.android.internal.telephony.gsm.SmsBroadcastConfigInfo smsBroadcastConfigInfo = (com.android.internal.telephony.gsm.SmsBroadcastConfigInfo) obj;
        return this.mFromServiceId == smsBroadcastConfigInfo.mFromServiceId && this.mToServiceId == smsBroadcastConfigInfo.mToServiceId && this.mFromCodeScheme == smsBroadcastConfigInfo.mFromCodeScheme && this.mToCodeScheme == smsBroadcastConfigInfo.mToCodeScheme && this.mSelected == smsBroadcastConfigInfo.mSelected;
    }
}
