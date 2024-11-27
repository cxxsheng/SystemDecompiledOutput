package android.security.identity;

/* loaded from: classes3.dex */
public class PersonalizationData {
    private java.util.LinkedHashMap<java.lang.String, android.security.identity.PersonalizationData.NamespaceData> mNamespaces;
    private java.util.ArrayList<android.security.identity.AccessControlProfile> mProfiles;

    private PersonalizationData() {
        this.mProfiles = new java.util.ArrayList<>();
        this.mNamespaces = new java.util.LinkedHashMap<>();
    }

    java.util.Collection<android.security.identity.AccessControlProfile> getAccessControlProfiles() {
        return java.util.Collections.unmodifiableCollection(this.mProfiles);
    }

    java.util.Collection<java.lang.String> getNamespaces() {
        return java.util.Collections.unmodifiableCollection(this.mNamespaces.keySet());
    }

    android.security.identity.PersonalizationData.NamespaceData getNamespaceData(java.lang.String str) {
        return this.mNamespaces.get(str);
    }

    static class NamespaceData {
        private java.util.LinkedHashMap<java.lang.String, android.security.identity.PersonalizationData.EntryData> mEntries;
        private java.lang.String mNamespace;

        private NamespaceData(java.lang.String str) {
            this.mEntries = new java.util.LinkedHashMap<>();
            this.mNamespace = str;
        }

        java.lang.String getNamespaceName() {
            return this.mNamespace;
        }

        java.util.Collection<java.lang.String> getEntryNames() {
            return java.util.Collections.unmodifiableCollection(this.mEntries.keySet());
        }

        java.util.Collection<android.security.identity.AccessControlProfileId> getAccessControlProfileIds(java.lang.String str) {
            android.security.identity.PersonalizationData.EntryData entryData = this.mEntries.get(str);
            if (entryData != null) {
                return entryData.mAccessControlProfileIds;
            }
            return null;
        }

        byte[] getEntryValue(java.lang.String str) {
            android.security.identity.PersonalizationData.EntryData entryData = this.mEntries.get(str);
            if (entryData != null) {
                return entryData.mValue;
            }
            return null;
        }
    }

    private static class EntryData {
        java.util.Collection<android.security.identity.AccessControlProfileId> mAccessControlProfileIds;
        byte[] mValue;

        EntryData(byte[] bArr, java.util.Collection<android.security.identity.AccessControlProfileId> collection) {
            this.mValue = bArr;
            this.mAccessControlProfileIds = collection;
        }
    }

    public static final class Builder {
        private android.security.identity.PersonalizationData mData = new android.security.identity.PersonalizationData();

        public android.security.identity.PersonalizationData.Builder putEntry(java.lang.String str, java.lang.String str2, java.util.Collection<android.security.identity.AccessControlProfileId> collection, byte[] bArr) {
            android.security.identity.PersonalizationData.NamespaceData namespaceData = (android.security.identity.PersonalizationData.NamespaceData) this.mData.mNamespaces.get(str);
            if (namespaceData == null) {
                namespaceData = new android.security.identity.PersonalizationData.NamespaceData(str);
                this.mData.mNamespaces.put(str, namespaceData);
            }
            namespaceData.mEntries.put(str2, new android.security.identity.PersonalizationData.EntryData(bArr, collection));
            return this;
        }

        public android.security.identity.PersonalizationData.Builder addAccessControlProfile(android.security.identity.AccessControlProfile accessControlProfile) {
            this.mData.mProfiles.add(accessControlProfile);
            return this;
        }

        public android.security.identity.PersonalizationData build() {
            return this.mData;
        }
    }
}
