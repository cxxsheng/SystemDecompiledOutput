package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DrmRights {
    private java.lang.String mAccountId;
    private byte[] mData;
    private java.lang.String mMimeType;
    private java.lang.String mSubscriptionId;

    public DrmRights(java.lang.String str, java.lang.String str2) {
        instantiate(new java.io.File(str), str2);
    }

    public DrmRights(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this(str, str2);
        this.mAccountId = str3;
    }

    public DrmRights(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        this(str, str2);
        this.mAccountId = str3;
        this.mSubscriptionId = str4;
    }

    public DrmRights(java.io.File file, java.lang.String str) {
        instantiate(file, str);
    }

    private void instantiate(java.io.File file, java.lang.String str) {
        try {
            this.mData = android.drm.DrmUtils.readBytes(file);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        this.mMimeType = str;
        if (!isValid()) {
            throw new java.lang.IllegalArgumentException("mimeType: " + this.mMimeType + ",data: " + java.util.Arrays.toString(this.mData));
        }
    }

    public DrmRights(android.drm.ProcessedData processedData, java.lang.String str) {
        if (processedData == null) {
            throw new java.lang.IllegalArgumentException("data is null");
        }
        this.mData = processedData.getData();
        this.mAccountId = processedData.getAccountId();
        this.mSubscriptionId = processedData.getSubscriptionId();
        this.mMimeType = str;
        if (!isValid()) {
            throw new java.lang.IllegalArgumentException("mimeType: " + this.mMimeType + ",data: " + java.util.Arrays.toString(this.mData));
        }
    }

    public byte[] getData() {
        return this.mData;
    }

    public java.lang.String getMimeType() {
        return this.mMimeType;
    }

    public java.lang.String getAccountId() {
        return this.mAccountId;
    }

    public java.lang.String getSubscriptionId() {
        return this.mSubscriptionId;
    }

    boolean isValid() {
        return (this.mMimeType == null || this.mMimeType.equals("") || this.mData == null || this.mData.length <= 0) ? false : true;
    }
}
