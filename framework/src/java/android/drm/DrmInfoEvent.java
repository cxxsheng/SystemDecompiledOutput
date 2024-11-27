package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DrmInfoEvent extends android.drm.DrmEvent {
    public static final int TYPE_ACCOUNT_ALREADY_REGISTERED = 5;
    public static final int TYPE_ALREADY_REGISTERED_BY_ANOTHER_ACCOUNT = 1;
    public static final int TYPE_REMOVE_RIGHTS = 2;
    public static final int TYPE_RIGHTS_INSTALLED = 3;
    public static final int TYPE_RIGHTS_REMOVED = 6;
    public static final int TYPE_WAIT_FOR_RIGHTS = 4;

    public DrmInfoEvent(int i, int i2, java.lang.String str) {
        super(i, i2, str);
        checkTypeValidity(i2);
    }

    public DrmInfoEvent(int i, int i2, java.lang.String str, java.util.HashMap<java.lang.String, java.lang.Object> hashMap) {
        super(i, i2, str, hashMap);
        checkTypeValidity(i2);
    }

    private void checkTypeValidity(int i) {
        if ((i < 1 || i > 6) && i != 1001 && i != 1002) {
            throw new java.lang.IllegalArgumentException("Unsupported type: " + i);
        }
    }
}
