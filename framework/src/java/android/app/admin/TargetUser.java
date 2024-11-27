package android.app.admin;

/* loaded from: classes.dex */
public final class TargetUser {
    public static final int GLOBAL_USER_ID = -3;
    public static final int LOCAL_USER_ID = -1;
    public static final int PARENT_USER_ID = -2;
    public static final int UNKNOWN_USER_ID = -3;
    private final int mUserId;
    public static final android.app.admin.TargetUser LOCAL_USER = new android.app.admin.TargetUser(-1);
    public static final android.app.admin.TargetUser PARENT_USER = new android.app.admin.TargetUser(-2);
    public static final android.app.admin.TargetUser GLOBAL = new android.app.admin.TargetUser(-3);
    public static final android.app.admin.TargetUser UNKNOWN_USER = new android.app.admin.TargetUser(-3);

    public TargetUser(int i) {
        this.mUserId = i;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mUserId == ((android.app.admin.TargetUser) obj).mUserId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mUserId));
    }
}
