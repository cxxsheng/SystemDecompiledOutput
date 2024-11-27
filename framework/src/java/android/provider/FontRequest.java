package android.provider;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class FontRequest {
    private final java.util.List<java.util.List<byte[]>> mCertificates;
    private final java.lang.String mIdentifier;
    private final java.lang.String mProviderAuthority;
    private final java.lang.String mProviderPackage;
    private final java.lang.String mQuery;

    public FontRequest(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mProviderAuthority = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str);
        this.mQuery = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str3);
        this.mProviderPackage = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str2);
        this.mCertificates = java.util.Collections.emptyList();
        this.mIdentifier = this.mProviderAuthority + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.mProviderPackage + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.mQuery;
    }

    public FontRequest(java.lang.String str, java.lang.String str2, java.lang.String str3, java.util.List<java.util.List<byte[]>> list) {
        this.mProviderAuthority = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str);
        this.mProviderPackage = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str2);
        this.mQuery = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str3);
        this.mCertificates = (java.util.List) com.android.internal.util.Preconditions.checkNotNull(list);
        this.mIdentifier = this.mProviderAuthority + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.mProviderPackage + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.mQuery;
    }

    public java.lang.String getProviderAuthority() {
        return this.mProviderAuthority;
    }

    public java.lang.String getProviderPackage() {
        return this.mProviderPackage;
    }

    public java.lang.String getQuery() {
        return this.mQuery;
    }

    public java.util.List<java.util.List<byte[]>> getCertificates() {
        return this.mCertificates;
    }

    public java.lang.String getIdentifier() {
        return this.mIdentifier;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("FontRequest {mProviderAuthority: " + this.mProviderAuthority + ", mProviderPackage: " + this.mProviderPackage + ", mQuery: " + this.mQuery + ", mCertificates:");
        for (int i = 0; i < this.mCertificates.size(); i++) {
            sb.append(" [");
            java.util.List<byte[]> list = this.mCertificates.get(i);
            for (int i2 = 0; i2 < list.size(); i2++) {
                sb.append(" \"");
                sb.append(android.util.Base64.encodeToString(list.get(i2), 0));
                sb.append("\"");
            }
            sb.append(" ]");
        }
        sb.append("}");
        return sb.toString();
    }
}
