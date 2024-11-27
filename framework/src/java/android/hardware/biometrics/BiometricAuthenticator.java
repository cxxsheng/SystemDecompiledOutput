package android.hardware.biometrics;

/* loaded from: classes.dex */
public interface BiometricAuthenticator {
    public static final int TYPE_ANY_BIOMETRIC = 14;
    public static final int TYPE_CREDENTIAL = 1;
    public static final int TYPE_FACE = 8;
    public static final int TYPE_FINGERPRINT = 2;
    public static final int TYPE_IRIS = 4;
    public static final int TYPE_NONE = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Modality {
    }

    public static abstract class Identifier implements android.os.Parcelable {
        private int mBiometricId;
        private long mDeviceId;
        private java.lang.CharSequence mName;

        public Identifier() {
        }

        public Identifier(java.lang.CharSequence charSequence, int i, long j) {
            this.mName = charSequence;
            this.mBiometricId = i;
            this.mDeviceId = j;
        }

        public java.lang.CharSequence getName() {
            return this.mName;
        }

        public int getBiometricId() {
            return this.mBiometricId;
        }

        public long getDeviceId() {
            return this.mDeviceId;
        }

        public void setName(java.lang.CharSequence charSequence) {
            this.mName = charSequence;
        }

        public void setDeviceId(long j) {
            this.mDeviceId = j;
        }
    }

    public static class AuthenticationResult {
        private int mAuthenticationType;
        private android.hardware.biometrics.CryptoObject mCryptoObject;
        private android.hardware.biometrics.BiometricAuthenticator.Identifier mIdentifier;
        private int mUserId;

        public AuthenticationResult() {
        }

        public AuthenticationResult(android.hardware.biometrics.CryptoObject cryptoObject, int i, android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, int i2) {
            this.mCryptoObject = cryptoObject;
            this.mAuthenticationType = i;
            this.mIdentifier = identifier;
            this.mUserId = i2;
        }

        public android.hardware.biometrics.CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public int getAuthenticationType() {
            return this.mAuthenticationType;
        }

        public android.hardware.biometrics.BiometricAuthenticator.Identifier getId() {
            return this.mIdentifier;
        }

        public int getUserId() {
            return this.mUserId;
        }
    }

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int i, java.lang.CharSequence charSequence) {
        }

        public void onAuthenticationHelp(int i, java.lang.CharSequence charSequence) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationAcquired(int i) {
        }
    }
}
