package android.security.identity;

/* loaded from: classes3.dex */
class CredstoreResultData extends android.security.identity.ResultData {
    int mFeatureVersion = 0;
    byte[] mStaticAuthenticationData = null;
    byte[] mAuthenticatedData = null;
    byte[] mMessageAuthenticationCode = null;
    byte[] mSignature = null;
    private java.util.Map<java.lang.String, java.util.Map<java.lang.String, android.security.identity.CredstoreResultData.EntryData>> mData = new java.util.LinkedHashMap();

    private static class EntryData {
        int mStatus;
        byte[] mValue;

        EntryData(byte[] bArr, int i) {
            this.mValue = bArr;
            this.mStatus = i;
        }
    }

    CredstoreResultData() {
    }

    @Override // android.security.identity.ResultData
    public byte[] getAuthenticatedData() {
        return this.mAuthenticatedData;
    }

    @Override // android.security.identity.ResultData
    public byte[] getMessageAuthenticationCode() {
        return this.mMessageAuthenticationCode;
    }

    @Override // android.security.identity.ResultData
    byte[] getSignature() {
        if (this.mFeatureVersion < 202301) {
            throw new java.lang.UnsupportedOperationException();
        }
        return this.mSignature;
    }

    @Override // android.security.identity.ResultData
    public byte[] getStaticAuthenticationData() {
        return this.mStaticAuthenticationData;
    }

    @Override // android.security.identity.ResultData
    public java.util.Collection<java.lang.String> getNamespaces() {
        return java.util.Collections.unmodifiableCollection(this.mData.keySet());
    }

    @Override // android.security.identity.ResultData
    public java.util.Collection<java.lang.String> getEntryNames(java.lang.String str) {
        java.util.Map<java.lang.String, android.security.identity.CredstoreResultData.EntryData> map = this.mData.get(str);
        if (map == null) {
            return null;
        }
        return java.util.Collections.unmodifiableCollection(map.keySet());
    }

    @Override // android.security.identity.ResultData
    public java.util.Collection<java.lang.String> getRetrievedEntryNames(java.lang.String str) {
        java.util.Map<java.lang.String, android.security.identity.CredstoreResultData.EntryData> map = this.mData.get(str);
        if (map == null) {
            return null;
        }
        java.util.LinkedList linkedList = new java.util.LinkedList();
        for (java.util.Map.Entry<java.lang.String, android.security.identity.CredstoreResultData.EntryData> entry : map.entrySet()) {
            if (entry.getValue().mStatus == 0) {
                linkedList.add(entry.getKey());
            }
        }
        return linkedList;
    }

    private android.security.identity.CredstoreResultData.EntryData getEntryData(java.lang.String str, java.lang.String str2) {
        java.util.Map<java.lang.String, android.security.identity.CredstoreResultData.EntryData> map = this.mData.get(str);
        if (map == null) {
            return null;
        }
        return map.get(str2);
    }

    @Override // android.security.identity.ResultData
    public int getStatus(java.lang.String str, java.lang.String str2) {
        android.security.identity.CredstoreResultData.EntryData entryData = getEntryData(str, str2);
        if (entryData == null) {
            return 2;
        }
        return entryData.mStatus;
    }

    @Override // android.security.identity.ResultData
    public byte[] getEntry(java.lang.String str, java.lang.String str2) {
        android.security.identity.CredstoreResultData.EntryData entryData = getEntryData(str, str2);
        if (entryData == null) {
            return null;
        }
        return entryData.mValue;
    }

    static class Builder {
        private android.security.identity.CredstoreResultData mResultData = new android.security.identity.CredstoreResultData();

        Builder(int i, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
            this.mResultData.mFeatureVersion = i;
            this.mResultData.mStaticAuthenticationData = bArr;
            this.mResultData.mAuthenticatedData = bArr2;
            this.mResultData.mMessageAuthenticationCode = bArr3;
            this.mResultData.mSignature = bArr4;
        }

        private java.util.Map<java.lang.String, android.security.identity.CredstoreResultData.EntryData> getOrCreateInnerMap(java.lang.String str) {
            java.util.Map<java.lang.String, android.security.identity.CredstoreResultData.EntryData> map = (java.util.Map) this.mResultData.mData.get(str);
            if (map == null) {
                java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
                this.mResultData.mData.put(str, linkedHashMap);
                return linkedHashMap;
            }
            return map;
        }

        android.security.identity.CredstoreResultData.Builder addEntry(java.lang.String str, java.lang.String str2, byte[] bArr) {
            getOrCreateInnerMap(str).put(str2, new android.security.identity.CredstoreResultData.EntryData(bArr, 0));
            return this;
        }

        android.security.identity.CredstoreResultData.Builder addErrorStatus(java.lang.String str, java.lang.String str2, int i) {
            getOrCreateInnerMap(str).put(str2, new android.security.identity.CredstoreResultData.EntryData(null, i));
            return this;
        }

        android.security.identity.CredstoreResultData build() {
            return this.mResultData;
        }
    }
}
