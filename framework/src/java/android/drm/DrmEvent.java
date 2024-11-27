package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DrmEvent {
    public static final java.lang.String DRM_INFO_OBJECT = "drm_info_object";
    public static final java.lang.String DRM_INFO_STATUS_OBJECT = "drm_info_status_object";
    public static final int TYPE_ALL_RIGHTS_REMOVED = 1001;
    public static final int TYPE_DRM_INFO_PROCESSED = 1002;
    private java.util.HashMap<java.lang.String, java.lang.Object> mAttributes;
    private java.lang.String mMessage;
    private final int mType;
    private final int mUniqueId;

    protected DrmEvent(int i, int i2, java.lang.String str, java.util.HashMap<java.lang.String, java.lang.Object> hashMap) {
        this.mMessage = "";
        this.mAttributes = new java.util.HashMap<>();
        this.mUniqueId = i;
        this.mType = i2;
        if (str != null) {
            this.mMessage = str;
        }
        if (hashMap != null) {
            this.mAttributes = hashMap;
        }
    }

    protected DrmEvent(int i, int i2, java.lang.String str) {
        this.mMessage = "";
        this.mAttributes = new java.util.HashMap<>();
        this.mUniqueId = i;
        this.mType = i2;
        if (str != null) {
            this.mMessage = str;
        }
    }

    public int getUniqueId() {
        return this.mUniqueId;
    }

    public int getType() {
        return this.mType;
    }

    public java.lang.String getMessage() {
        return this.mMessage;
    }

    public java.lang.Object getAttribute(java.lang.String str) {
        return this.mAttributes.get(str);
    }
}
