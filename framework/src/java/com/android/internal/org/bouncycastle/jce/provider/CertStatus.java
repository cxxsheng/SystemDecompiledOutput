package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
class CertStatus {
    public static final int UNDETERMINED = 12;
    public static final int UNREVOKED = 11;
    int certStatus = 11;
    java.util.Date revocationDate = null;

    CertStatus() {
    }

    public java.util.Date getRevocationDate() {
        return this.revocationDate;
    }

    public void setRevocationDate(java.util.Date date) {
        this.revocationDate = date;
    }

    public int getCertStatus() {
        return this.certStatus;
    }

    public void setCertStatus(int i) {
        this.certStatus = i;
    }
}
