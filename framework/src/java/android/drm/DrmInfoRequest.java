package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DrmInfoRequest {
    public static final java.lang.String ACCOUNT_ID = "account_id";
    public static final java.lang.String SUBSCRIPTION_ID = "subscription_id";
    public static final int TYPE_REGISTRATION_INFO = 1;
    public static final int TYPE_RIGHTS_ACQUISITION_INFO = 3;
    public static final int TYPE_RIGHTS_ACQUISITION_PROGRESS_INFO = 4;
    public static final int TYPE_UNREGISTRATION_INFO = 2;
    private final int mInfoType;
    private final java.lang.String mMimeType;
    private final java.util.HashMap<java.lang.String, java.lang.Object> mRequestInformation = new java.util.HashMap<>();

    public DrmInfoRequest(int i, java.lang.String str) {
        this.mInfoType = i;
        this.mMimeType = str;
        if (!isValid()) {
            throw new java.lang.IllegalArgumentException("infoType: " + i + ",mimeType: " + str);
        }
    }

    public java.lang.String getMimeType() {
        return this.mMimeType;
    }

    public int getInfoType() {
        return this.mInfoType;
    }

    public void put(java.lang.String str, java.lang.Object obj) {
        this.mRequestInformation.put(str, obj);
    }

    public java.lang.Object get(java.lang.String str) {
        return this.mRequestInformation.get(str);
    }

    public java.util.Iterator<java.lang.String> keyIterator() {
        return this.mRequestInformation.keySet().iterator();
    }

    public java.util.Iterator<java.lang.Object> iterator() {
        return this.mRequestInformation.values().iterator();
    }

    boolean isValid() {
        return (this.mMimeType == null || this.mMimeType.equals("") || this.mRequestInformation == null || !isValidType(this.mInfoType)) ? false : true;
    }

    static boolean isValidType(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }
}
