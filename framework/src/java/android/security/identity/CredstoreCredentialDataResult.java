package android.security.identity;

/* loaded from: classes3.dex */
class CredstoreCredentialDataResult extends android.security.identity.CredentialDataResult {
    android.security.identity.CredstoreCredentialDataResult.CredstoreEntries mDeviceSignedEntries;
    android.security.identity.ResultData mDeviceSignedResult;
    android.security.identity.CredstoreCredentialDataResult.CredstoreEntries mIssuerSignedEntries;
    android.security.identity.ResultData mIssuerSignedResult;

    CredstoreCredentialDataResult(android.security.identity.ResultData resultData, android.security.identity.ResultData resultData2) {
        this.mDeviceSignedResult = resultData;
        this.mIssuerSignedResult = resultData2;
        this.mDeviceSignedEntries = new android.security.identity.CredstoreCredentialDataResult.CredstoreEntries(resultData);
        this.mIssuerSignedEntries = new android.security.identity.CredstoreCredentialDataResult.CredstoreEntries(resultData2);
    }

    @Override // android.security.identity.CredentialDataResult
    public byte[] getDeviceNameSpaces() {
        return this.mDeviceSignedResult.getAuthenticatedData();
    }

    @Override // android.security.identity.CredentialDataResult
    public byte[] getDeviceMac() {
        return this.mDeviceSignedResult.getMessageAuthenticationCode();
    }

    @Override // android.security.identity.CredentialDataResult
    public byte[] getDeviceSignature() {
        return this.mDeviceSignedResult.getSignature();
    }

    @Override // android.security.identity.CredentialDataResult
    public byte[] getStaticAuthenticationData() {
        return this.mDeviceSignedResult.getStaticAuthenticationData();
    }

    @Override // android.security.identity.CredentialDataResult
    public android.security.identity.CredentialDataResult.Entries getDeviceSignedEntries() {
        return this.mDeviceSignedEntries;
    }

    @Override // android.security.identity.CredentialDataResult
    public android.security.identity.CredentialDataResult.Entries getIssuerSignedEntries() {
        return this.mIssuerSignedEntries;
    }

    static class CredstoreEntries implements android.security.identity.CredentialDataResult.Entries {
        android.security.identity.ResultData mResultData;

        CredstoreEntries(android.security.identity.ResultData resultData) {
            this.mResultData = resultData;
        }

        @Override // android.security.identity.CredentialDataResult.Entries
        public java.util.Collection<java.lang.String> getNamespaces() {
            return this.mResultData.getNamespaces();
        }

        @Override // android.security.identity.CredentialDataResult.Entries
        public java.util.Collection<java.lang.String> getEntryNames(java.lang.String str) {
            java.util.Collection<java.lang.String> entryNames = this.mResultData.getEntryNames(str);
            if (entryNames == null) {
                return new java.util.LinkedList();
            }
            return entryNames;
        }

        @Override // android.security.identity.CredentialDataResult.Entries
        public java.util.Collection<java.lang.String> getRetrievedEntryNames(java.lang.String str) {
            java.util.Collection<java.lang.String> retrievedEntryNames = this.mResultData.getRetrievedEntryNames(str);
            if (retrievedEntryNames == null) {
                return new java.util.LinkedList();
            }
            return retrievedEntryNames;
        }

        @Override // android.security.identity.CredentialDataResult.Entries
        public int getStatus(java.lang.String str, java.lang.String str2) {
            return this.mResultData.getStatus(str, str2);
        }

        @Override // android.security.identity.CredentialDataResult.Entries
        public byte[] getEntry(java.lang.String str, java.lang.String str2) {
            return this.mResultData.getEntry(str, str2);
        }
    }
}
