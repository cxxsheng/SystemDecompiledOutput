package android.credentials;

/* loaded from: classes.dex */
public class ClearCredentialStateException extends java.lang.Exception {
    public static final java.lang.String TYPE_UNKNOWN = "android.credentials.ClearCredentialStateException.TYPE_UNKNOWN";
    private final java.lang.String mType;

    public java.lang.String getType() {
        return this.mType;
    }

    public ClearCredentialStateException(java.lang.String str, java.lang.String str2) {
        this(str, str2, null);
    }

    public ClearCredentialStateException(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        super(str2, th);
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "type must not be empty");
    }

    public ClearCredentialStateException(java.lang.String str, java.lang.Throwable th) {
        this(str, null, th);
    }

    public ClearCredentialStateException(java.lang.String str) {
        this(str, null, null);
    }
}
