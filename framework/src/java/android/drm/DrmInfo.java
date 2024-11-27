package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DrmInfo {
    private final java.util.HashMap<java.lang.String, java.lang.Object> mAttributes = new java.util.HashMap<>();
    private byte[] mData;
    private final int mInfoType;
    private final java.lang.String mMimeType;

    public DrmInfo(int i, byte[] bArr, java.lang.String str) {
        this.mInfoType = i;
        this.mMimeType = str;
        this.mData = bArr;
        if (!isValid()) {
            throw new java.lang.IllegalArgumentException("infoType: " + i + ",mimeType: " + str + ",data: " + java.util.Arrays.toString(bArr));
        }
    }

    public DrmInfo(int i, java.lang.String str, java.lang.String str2) {
        this.mInfoType = i;
        this.mMimeType = str2;
        try {
            this.mData = android.drm.DrmUtils.readBytes(str);
        } catch (java.io.IOException e) {
            this.mData = null;
        }
        if (!isValid()) {
            java.lang.String str3 = "infoType: " + i + ",mimeType: " + str2 + ",data: " + java.util.Arrays.toString(this.mData);
            throw new java.lang.IllegalArgumentException();
        }
    }

    public void put(java.lang.String str, java.lang.Object obj) {
        this.mAttributes.put(str, obj);
    }

    public java.lang.Object get(java.lang.String str) {
        return this.mAttributes.get(str);
    }

    public java.util.Iterator<java.lang.String> keyIterator() {
        return this.mAttributes.keySet().iterator();
    }

    public java.util.Iterator<java.lang.Object> iterator() {
        return this.mAttributes.values().iterator();
    }

    public byte[] getData() {
        return this.mData;
    }

    public java.lang.String getMimeType() {
        return this.mMimeType;
    }

    public int getInfoType() {
        return this.mInfoType;
    }

    boolean isValid() {
        return (this.mMimeType == null || this.mMimeType.equals("") || this.mData == null || this.mData.length <= 0 || !android.drm.DrmInfoRequest.isValidType(this.mInfoType)) ? false : true;
    }
}
