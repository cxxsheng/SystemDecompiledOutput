package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class GetCredentialProviderInfo {
    private final java.util.List<android.credentials.selection.Entry> mActionChips;
    private final java.util.List<android.credentials.selection.AuthenticationEntry> mAuthenticationEntries;
    private final java.util.List<android.credentials.selection.Entry> mCredentialEntries;
    private final java.lang.String mProviderName;
    private final android.credentials.selection.Entry mRemoteEntry;

    GetCredentialProviderInfo(java.lang.String str, java.util.List<android.credentials.selection.Entry> list, java.util.List<android.credentials.selection.Entry> list2, java.util.List<android.credentials.selection.AuthenticationEntry> list3, android.credentials.selection.Entry entry) {
        this.mProviderName = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        this.mCredentialEntries = new java.util.ArrayList(list);
        this.mActionChips = new java.util.ArrayList(list2);
        this.mAuthenticationEntries = new java.util.ArrayList(list3);
        this.mRemoteEntry = entry;
    }

    public java.lang.String getProviderName() {
        return this.mProviderName;
    }

    public java.util.List<android.credentials.selection.Entry> getCredentialEntries() {
        return this.mCredentialEntries;
    }

    public java.util.List<android.credentials.selection.Entry> getActionChips() {
        return this.mActionChips;
    }

    public java.util.List<android.credentials.selection.AuthenticationEntry> getAuthenticationEntries() {
        return this.mAuthenticationEntries;
    }

    public android.credentials.selection.Entry getRemoteEntry() {
        return this.mRemoteEntry;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private java.lang.String mProviderName;
        private java.util.List<android.credentials.selection.Entry> mCredentialEntries = new java.util.ArrayList();
        private java.util.List<android.credentials.selection.Entry> mActionChips = new java.util.ArrayList();
        private java.util.List<android.credentials.selection.AuthenticationEntry> mAuthenticationEntries = new java.util.ArrayList();
        private android.credentials.selection.Entry mRemoteEntry = null;

        public Builder(java.lang.String str) {
            this.mProviderName = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        }

        public android.credentials.selection.GetCredentialProviderInfo.Builder setCredentialEntries(java.util.List<android.credentials.selection.Entry> list) {
            this.mCredentialEntries = list;
            return this;
        }

        public android.credentials.selection.GetCredentialProviderInfo.Builder setActionChips(java.util.List<android.credentials.selection.Entry> list) {
            this.mActionChips = list;
            return this;
        }

        public android.credentials.selection.GetCredentialProviderInfo.Builder setAuthenticationEntries(java.util.List<android.credentials.selection.AuthenticationEntry> list) {
            this.mAuthenticationEntries = list;
            return this;
        }

        public android.credentials.selection.GetCredentialProviderInfo.Builder setRemoteEntry(android.credentials.selection.Entry entry) {
            this.mRemoteEntry = entry;
            return this;
        }

        public android.credentials.selection.GetCredentialProviderInfo build() {
            return new android.credentials.selection.GetCredentialProviderInfo(this.mProviderName, this.mCredentialEntries, this.mActionChips, this.mAuthenticationEntries, this.mRemoteEntry);
        }
    }
}
