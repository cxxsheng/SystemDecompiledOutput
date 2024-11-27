package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DrmErrorEvent extends android.drm.DrmEvent {
    public static final int TYPE_ACQUIRE_DRM_INFO_FAILED = 2008;
    public static final int TYPE_NOT_SUPPORTED = 2003;
    public static final int TYPE_NO_INTERNET_CONNECTION = 2005;
    public static final int TYPE_OUT_OF_MEMORY = 2004;
    public static final int TYPE_PROCESS_DRM_INFO_FAILED = 2006;
    public static final int TYPE_REMOVE_ALL_RIGHTS_FAILED = 2007;
    public static final int TYPE_RIGHTS_NOT_INSTALLED = 2001;
    public static final int TYPE_RIGHTS_RENEWAL_NOT_ALLOWED = 2002;

    public DrmErrorEvent(int i, int i2, java.lang.String str) {
        super(i, i2, str);
        checkTypeValidity(i2);
    }

    public DrmErrorEvent(int i, int i2, java.lang.String str, java.util.HashMap<java.lang.String, java.lang.Object> hashMap) {
        super(i, i2, str, hashMap);
        checkTypeValidity(i2);
    }

    private void checkTypeValidity(int i) {
        if (i < 2001 || i > 2008) {
            throw new java.lang.IllegalArgumentException("Unsupported type: " + i);
        }
    }
}
