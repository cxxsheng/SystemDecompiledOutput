package android.security.identity;

/* loaded from: classes3.dex */
public abstract class CredentialDataResult {

    public interface Entries {
        public static final int STATUS_NOT_IN_REQUEST_MESSAGE = 3;
        public static final int STATUS_NOT_REQUESTED = 2;
        public static final int STATUS_NO_ACCESS_CONTROL_PROFILES = 6;
        public static final int STATUS_NO_SUCH_ENTRY = 1;
        public static final int STATUS_OK = 0;
        public static final int STATUS_READER_AUTHENTICATION_FAILED = 5;
        public static final int STATUS_USER_AUTHENTICATION_FAILED = 4;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Status {
        }

        byte[] getEntry(java.lang.String str, java.lang.String str2);

        java.util.Collection<java.lang.String> getEntryNames(java.lang.String str);

        java.util.Collection<java.lang.String> getNamespaces();

        java.util.Collection<java.lang.String> getRetrievedEntryNames(java.lang.String str);

        int getStatus(java.lang.String str, java.lang.String str2);
    }

    public abstract byte[] getDeviceMac();

    public abstract byte[] getDeviceNameSpaces();

    public abstract android.security.identity.CredentialDataResult.Entries getDeviceSignedEntries();

    public abstract android.security.identity.CredentialDataResult.Entries getIssuerSignedEntries();

    public abstract byte[] getStaticAuthenticationData();

    protected CredentialDataResult() {
    }

    public byte[] getDeviceSignature() {
        throw new java.lang.UnsupportedOperationException();
    }
}
