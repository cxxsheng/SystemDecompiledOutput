package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class CreateCredentialProviderInfo {
    private final java.lang.String mProviderName;
    private final android.credentials.selection.Entry mRemoteEntry;
    private final java.util.List<android.credentials.selection.Entry> mSaveEntries;

    CreateCredentialProviderInfo(java.lang.String str, java.util.List<android.credentials.selection.Entry> list, android.credentials.selection.Entry entry) {
        this.mProviderName = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        this.mSaveEntries = new java.util.ArrayList(list);
        this.mRemoteEntry = entry;
    }

    public java.lang.String getProviderName() {
        return this.mProviderName;
    }

    public java.util.List<android.credentials.selection.Entry> getSaveEntries() {
        return this.mSaveEntries;
    }

    public android.credentials.selection.Entry getRemoteEntry() {
        return this.mRemoteEntry;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private java.lang.String mProviderName;
        private java.util.List<android.credentials.selection.Entry> mSaveEntries = new java.util.ArrayList();
        private android.credentials.selection.Entry mRemoteEntry = null;

        public Builder(java.lang.String str) {
            this.mProviderName = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        }

        public android.credentials.selection.CreateCredentialProviderInfo.Builder setSaveEntries(java.util.List<android.credentials.selection.Entry> list) {
            this.mSaveEntries = list;
            return this;
        }

        public android.credentials.selection.CreateCredentialProviderInfo.Builder setRemoteEntry(android.credentials.selection.Entry entry) {
            this.mRemoteEntry = entry;
            return this;
        }

        public android.credentials.selection.CreateCredentialProviderInfo build() {
            return new android.credentials.selection.CreateCredentialProviderInfo(this.mProviderName, this.mSaveEntries, this.mRemoteEntry);
        }
    }
}
