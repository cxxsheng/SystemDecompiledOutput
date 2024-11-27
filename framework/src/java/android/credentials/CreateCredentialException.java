package android.credentials;

/* loaded from: classes.dex */
public class CreateCredentialException extends java.lang.Exception {
    public static final java.lang.String TYPE_INTERRUPTED = "android.credentials.CreateCredentialException.TYPE_INTERRUPTED";
    public static final java.lang.String TYPE_NO_CREATE_OPTIONS = "android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS";
    public static final java.lang.String TYPE_UNKNOWN = "android.credentials.CreateCredentialException.TYPE_UNKNOWN";
    public static final java.lang.String TYPE_USER_CANCELED = "android.credentials.CreateCredentialException.TYPE_USER_CANCELED";
    private final java.lang.String mType;

    public java.lang.String getType() {
        return this.mType;
    }

    public CreateCredentialException(java.lang.String str, java.lang.String str2) {
        this(str, str2, null);
    }

    public CreateCredentialException(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        super(str2, th);
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "type must not be empty");
    }

    public CreateCredentialException(java.lang.String str, java.lang.Throwable th) {
        this(str, null, th);
    }

    public CreateCredentialException(java.lang.String str) {
        this(str, null, null);
    }
}
