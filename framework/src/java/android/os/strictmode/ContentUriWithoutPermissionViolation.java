package android.os.strictmode;

/* loaded from: classes3.dex */
public final class ContentUriWithoutPermissionViolation extends android.os.strictmode.Violation {
    public ContentUriWithoutPermissionViolation(android.net.Uri uri, java.lang.String str) {
        super(uri + " exposed beyond app through " + str + " without permission grant flags; did you forget FLAG_GRANT_READ_URI_PERMISSION?");
    }
}
