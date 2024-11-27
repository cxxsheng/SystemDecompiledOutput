package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class ProcessedData {
    private java.lang.String mAccountId;
    private final byte[] mData;
    private java.lang.String mSubscriptionId;

    ProcessedData(byte[] bArr, java.lang.String str) {
        this.mAccountId = "_NO_USER";
        this.mSubscriptionId = "";
        this.mData = bArr;
        this.mAccountId = str;
    }

    ProcessedData(byte[] bArr, java.lang.String str, java.lang.String str2) {
        this.mAccountId = "_NO_USER";
        this.mSubscriptionId = "";
        this.mData = bArr;
        this.mAccountId = str;
        this.mSubscriptionId = str2;
    }

    public byte[] getData() {
        return this.mData;
    }

    public java.lang.String getAccountId() {
        return this.mAccountId;
    }

    public java.lang.String getSubscriptionId() {
        return this.mSubscriptionId;
    }
}
