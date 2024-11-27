package android.security.identity;

/* loaded from: classes3.dex */
public class CredentialDataRequest {
    java.util.Map<java.lang.String, java.util.Collection<java.lang.String>> mDeviceSignedEntriesToRequest = new java.util.LinkedHashMap();
    java.util.Map<java.lang.String, java.util.Collection<java.lang.String>> mIssuerSignedEntriesToRequest = new java.util.LinkedHashMap();
    boolean mAllowUsingExhaustedKeys = true;
    boolean mAllowUsingExpiredKeys = false;
    boolean mIncrementUseCount = true;
    byte[] mRequestMessage = null;
    byte[] mReaderSignature = null;

    CredentialDataRequest() {
    }

    public java.util.Map<java.lang.String, java.util.Collection<java.lang.String>> getDeviceSignedEntriesToRequest() {
        return this.mDeviceSignedEntriesToRequest;
    }

    public java.util.Map<java.lang.String, java.util.Collection<java.lang.String>> getIssuerSignedEntriesToRequest() {
        return this.mIssuerSignedEntriesToRequest;
    }

    public boolean isAllowUsingExhaustedKeys() {
        return this.mAllowUsingExhaustedKeys;
    }

    public boolean isAllowUsingExpiredKeys() {
        return this.mAllowUsingExpiredKeys;
    }

    public boolean isIncrementUseCount() {
        return this.mIncrementUseCount;
    }

    public byte[] getRequestMessage() {
        return this.mRequestMessage;
    }

    public byte[] getReaderSignature() {
        return this.mReaderSignature;
    }

    public static final class Builder {
        private android.security.identity.CredentialDataRequest mData = new android.security.identity.CredentialDataRequest();

        public android.security.identity.CredentialDataRequest.Builder setDeviceSignedEntriesToRequest(java.util.Map<java.lang.String, java.util.Collection<java.lang.String>> map) {
            this.mData.mDeviceSignedEntriesToRequest = map;
            return this;
        }

        public android.security.identity.CredentialDataRequest.Builder setIssuerSignedEntriesToRequest(java.util.Map<java.lang.String, java.util.Collection<java.lang.String>> map) {
            this.mData.mIssuerSignedEntriesToRequest = map;
            return this;
        }

        public android.security.identity.CredentialDataRequest.Builder setAllowUsingExhaustedKeys(boolean z) {
            this.mData.mAllowUsingExhaustedKeys = z;
            return this;
        }

        public android.security.identity.CredentialDataRequest.Builder setAllowUsingExpiredKeys(boolean z) {
            this.mData.mAllowUsingExpiredKeys = z;
            return this;
        }

        public android.security.identity.CredentialDataRequest.Builder setIncrementUseCount(boolean z) {
            this.mData.mIncrementUseCount = z;
            return this;
        }

        public android.security.identity.CredentialDataRequest.Builder setRequestMessage(byte[] bArr) {
            this.mData.mRequestMessage = bArr;
            return this;
        }

        public android.security.identity.CredentialDataRequest.Builder setReaderSignature(byte[] bArr) {
            this.mData.mReaderSignature = bArr;
            return this;
        }

        public android.security.identity.CredentialDataRequest build() {
            return this.mData;
        }
    }
}
