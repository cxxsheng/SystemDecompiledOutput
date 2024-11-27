package android.credentials;

/* loaded from: classes.dex */
public class GetCandidateCredentialsException extends java.lang.Exception {
    public static final java.lang.String TYPE_INTERRUPTED = "android.credentials.GetCredentialException.TYPE_INTERRUPTED";
    public static final java.lang.String TYPE_NO_CREDENTIAL = "android.credentials.GetCandidateCredentialsException.TYPE_NO_CREDENTIAL";
    public static final java.lang.String TYPE_UNKNOWN = "android.credentials.GetCandidateCredentialsException.TYPE_UNKNOWN";
    public static final java.lang.String TYPE_USER_CANCELED = "android.credentials.GetCredentialException.TYPE_USER_CANCELED";
    private final java.lang.String mType;

    public java.lang.String getType() {
        return this.mType;
    }

    public GetCandidateCredentialsException(java.lang.String str, java.lang.String str2) {
        this(str, str2, null);
    }

    public GetCandidateCredentialsException(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        super(str2, th);
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "type must not be empty");
    }

    public GetCandidateCredentialsException(java.lang.String str, java.lang.Throwable th) {
        this(str, null, th);
    }

    public GetCandidateCredentialsException(java.lang.String str) {
        this(str, null, null);
    }
}
