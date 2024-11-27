package android.security;

/* loaded from: classes3.dex */
public final class UrisToAliases implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.UrisToAliases> CREATOR = new android.os.Parcelable.Creator<android.security.UrisToAliases>() { // from class: android.security.UrisToAliases.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.UrisToAliases createFromParcel(android.os.Parcel parcel) {
            java.util.HashMap hashMap = new java.util.HashMap();
            parcel.readMap(hashMap, java.lang.String.class.getClassLoader());
            return new android.security.UrisToAliases(hashMap);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.UrisToAliases[] newArray(int i) {
            return new android.security.UrisToAliases[i];
        }
    };
    private static final java.lang.String KEY_AUTHENTICATION_POLICY_ALIAS = "policy_alias";
    private static final java.lang.String KEY_AUTHENTICATION_POLICY_URI = "policy_uri";
    private static final java.lang.String KEY_AUTHENTICATION_POLICY_URI_TO_ALIAS = "authentication_policy_uri_to_alias";
    private final java.util.Map<android.net.Uri, java.lang.String> mUrisToAliases;

    public UrisToAliases() {
        this.mUrisToAliases = new java.util.HashMap();
    }

    private UrisToAliases(java.util.Map<android.net.Uri, java.lang.String> map) {
        this.mUrisToAliases = map;
    }

    public java.util.Map<android.net.Uri, java.lang.String> getUrisToAliases() {
        return java.util.Collections.unmodifiableMap(this.mUrisToAliases);
    }

    public void addUriToAlias(android.net.Uri uri, java.lang.String str) {
        this.mUrisToAliases.put(uri, str);
    }

    public static android.security.UrisToAliases readFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.util.HashMap hashMap = new java.util.HashMap();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlPullParser.getName().equals(KEY_AUTHENTICATION_POLICY_URI_TO_ALIAS)) {
                hashMap.put(android.net.Uri.parse(xmlPullParser.getAttributeValue(null, KEY_AUTHENTICATION_POLICY_URI)), xmlPullParser.getAttributeValue(null, KEY_AUTHENTICATION_POLICY_ALIAS));
            }
        }
        return new android.security.UrisToAliases(hashMap);
    }

    public void writeToXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        for (java.util.Map.Entry<android.net.Uri, java.lang.String> entry : this.mUrisToAliases.entrySet()) {
            xmlSerializer.startTag(null, KEY_AUTHENTICATION_POLICY_URI_TO_ALIAS);
            xmlSerializer.attribute(null, KEY_AUTHENTICATION_POLICY_URI, entry.getKey().toString());
            xmlSerializer.attribute(null, KEY_AUTHENTICATION_POLICY_ALIAS, entry.getValue());
            xmlSerializer.endTag(null, KEY_AUTHENTICATION_POLICY_URI_TO_ALIAS);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeMap(this.mUrisToAliases);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.security.UrisToAliases)) {
            return false;
        }
        return java.util.Objects.equals(this.mUrisToAliases, ((android.security.UrisToAliases) obj).mUrisToAliases);
    }

    public int hashCode() {
        return this.mUrisToAliases.hashCode();
    }
}
