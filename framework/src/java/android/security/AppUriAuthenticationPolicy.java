package android.security;

/* loaded from: classes3.dex */
public final class AppUriAuthenticationPolicy implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.AppUriAuthenticationPolicy> CREATOR = new android.os.Parcelable.Creator<android.security.AppUriAuthenticationPolicy>() { // from class: android.security.AppUriAuthenticationPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.AppUriAuthenticationPolicy createFromParcel(android.os.Parcel parcel) {
            java.util.HashMap hashMap = new java.util.HashMap();
            parcel.readMap(hashMap, android.security.UrisToAliases.class.getClassLoader());
            return new android.security.AppUriAuthenticationPolicy(hashMap);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.AppUriAuthenticationPolicy[] newArray(int i) {
            return new android.security.AppUriAuthenticationPolicy[i];
        }
    };
    private static final java.lang.String KEY_AUTHENTICATION_POLICY_APP = "policy_app";
    private static final java.lang.String KEY_AUTHENTICATION_POLICY_APP_TO_URIS = "authentication_policy_app_to_uris";
    private final java.util.Map<java.lang.String, android.security.UrisToAliases> mAppToUris;

    private AppUriAuthenticationPolicy(java.util.Map<java.lang.String, android.security.UrisToAliases> map) {
        java.util.Objects.requireNonNull(map);
        this.mAppToUris = map;
    }

    public static final class Builder {
        private java.util.Map<java.lang.String, android.security.UrisToAliases> mPackageNameToUris = new java.util.HashMap();

        public android.security.AppUriAuthenticationPolicy.Builder addAppAndUriMapping(java.lang.String str, android.net.Uri uri, java.lang.String str2) {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(uri);
            java.util.Objects.requireNonNull(str2);
            android.security.UrisToAliases orDefault = this.mPackageNameToUris.getOrDefault(str, new android.security.UrisToAliases());
            orDefault.addUriToAlias(uri, str2);
            this.mPackageNameToUris.put(str, orDefault);
            return this;
        }

        public android.security.AppUriAuthenticationPolicy.Builder addAppAndUriMapping(java.lang.String str, android.security.UrisToAliases urisToAliases) {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(urisToAliases);
            this.mPackageNameToUris.put(str, urisToAliases);
            return this;
        }

        public android.security.AppUriAuthenticationPolicy build() {
            return new android.security.AppUriAuthenticationPolicy(this.mPackageNameToUris);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeMap(this.mAppToUris);
    }

    public java.lang.String toString() {
        return "AppUriAuthenticationPolicy{mPackageNameToUris=" + this.mAppToUris + '}';
    }

    public java.util.Map<java.lang.String, java.util.Map<android.net.Uri, java.lang.String>> getAppAndUriMappings() {
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.util.Map.Entry<java.lang.String, android.security.UrisToAliases> entry : this.mAppToUris.entrySet()) {
            hashMap.put(entry.getKey(), entry.getValue().getUrisToAliases());
        }
        return hashMap;
    }

    public static android.security.AppUriAuthenticationPolicy readFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.security.AppUriAuthenticationPolicy.Builder builder = new android.security.AppUriAuthenticationPolicy.Builder();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlPullParser.getName().equals(KEY_AUTHENTICATION_POLICY_APP_TO_URIS)) {
                builder.addAppAndUriMapping(xmlPullParser.getAttributeValue(null, KEY_AUTHENTICATION_POLICY_APP), android.security.UrisToAliases.readFromXml(xmlPullParser));
            }
        }
        return builder.build();
    }

    public void writeToXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        for (java.util.Map.Entry<java.lang.String, android.security.UrisToAliases> entry : this.mAppToUris.entrySet()) {
            xmlSerializer.startTag(null, KEY_AUTHENTICATION_POLICY_APP_TO_URIS);
            xmlSerializer.attribute(null, KEY_AUTHENTICATION_POLICY_APP, entry.getKey());
            entry.getValue().writeToXml(xmlSerializer);
            xmlSerializer.endTag(null, KEY_AUTHENTICATION_POLICY_APP_TO_URIS);
        }
    }

    public java.util.Set<java.lang.String> getAliases() {
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator<android.security.UrisToAliases> it = this.mAppToUris.values().iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().getUrisToAliases().values());
        }
        return hashSet;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.security.AppUriAuthenticationPolicy)) {
            return false;
        }
        return java.util.Objects.equals(this.mAppToUris, ((android.security.AppUriAuthenticationPolicy) obj).mAppToUris);
    }

    public int hashCode() {
        return this.mAppToUris.hashCode();
    }
}
