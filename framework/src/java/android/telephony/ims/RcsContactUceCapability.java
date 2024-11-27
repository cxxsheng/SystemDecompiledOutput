package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class RcsContactUceCapability implements android.os.Parcelable {
    public static final int CAPABILITY_MECHANISM_OPTIONS = 2;
    public static final int CAPABILITY_MECHANISM_PRESENCE = 1;
    public static final android.os.Parcelable.Creator<android.telephony.ims.RcsContactUceCapability> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.RcsContactUceCapability>() { // from class: android.telephony.ims.RcsContactUceCapability.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RcsContactUceCapability createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.RcsContactUceCapability(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RcsContactUceCapability[] newArray(int i) {
            return new android.telephony.ims.RcsContactUceCapability[i];
        }
    };
    public static final int REQUEST_RESULT_FOUND = 3;
    public static final int REQUEST_RESULT_NOT_FOUND = 2;
    public static final int REQUEST_RESULT_NOT_ONLINE = 1;
    public static final int REQUEST_RESULT_UNKNOWN = 0;
    public static final int SOURCE_TYPE_CACHED = 1;
    public static final int SOURCE_TYPE_NETWORK = 0;
    private int mCapabilityMechanism;
    private final android.net.Uri mContactUri;
    private android.net.Uri mEntityUri;
    private final java.util.Set<java.lang.String> mFeatureTags;
    private final java.util.List<android.telephony.ims.RcsContactPresenceTuple> mPresenceTuples;
    private int mRequestResult;
    private int mSourceType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CapabilityMechanism {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SourceType {
    }

    public static final class OptionsBuilder {
        private final android.telephony.ims.RcsContactUceCapability mCapabilities;

        public OptionsBuilder(android.net.Uri uri) {
            this.mCapabilities = new android.telephony.ims.RcsContactUceCapability(uri, 2, 0);
        }

        public OptionsBuilder(android.net.Uri uri, int i) {
            this.mCapabilities = new android.telephony.ims.RcsContactUceCapability(uri, 2, i);
        }

        public android.telephony.ims.RcsContactUceCapability.OptionsBuilder setRequestResult(int i) {
            this.mCapabilities.mRequestResult = i;
            return this;
        }

        public android.telephony.ims.RcsContactUceCapability.OptionsBuilder addFeatureTag(java.lang.String str) {
            this.mCapabilities.mFeatureTags.add(str);
            return this;
        }

        public android.telephony.ims.RcsContactUceCapability.OptionsBuilder addFeatureTags(java.util.Set<java.lang.String> set) {
            this.mCapabilities.mFeatureTags.addAll(set);
            return this;
        }

        public android.telephony.ims.RcsContactUceCapability build() {
            return this.mCapabilities;
        }
    }

    public static final class PresenceBuilder {
        private final android.telephony.ims.RcsContactUceCapability mCapabilities;

        public PresenceBuilder(android.net.Uri uri, int i, int i2) {
            this.mCapabilities = new android.telephony.ims.RcsContactUceCapability(uri, 1, i);
            this.mCapabilities.mRequestResult = i2;
        }

        public android.telephony.ims.RcsContactUceCapability.PresenceBuilder addCapabilityTuple(android.telephony.ims.RcsContactPresenceTuple rcsContactPresenceTuple) {
            this.mCapabilities.mPresenceTuples.add(rcsContactPresenceTuple);
            return this;
        }

        public android.telephony.ims.RcsContactUceCapability.PresenceBuilder addCapabilityTuples(java.util.List<android.telephony.ims.RcsContactPresenceTuple> list) {
            this.mCapabilities.mPresenceTuples.addAll(list);
            return this;
        }

        public android.telephony.ims.RcsContactUceCapability.PresenceBuilder setEntityUri(android.net.Uri uri) {
            this.mCapabilities.mEntityUri = uri;
            return this;
        }

        public android.telephony.ims.RcsContactUceCapability build() {
            return this.mCapabilities;
        }
    }

    private RcsContactUceCapability(android.net.Uri uri, int i, int i2) {
        this.mFeatureTags = new java.util.HashSet();
        this.mPresenceTuples = new java.util.ArrayList();
        this.mContactUri = uri;
        this.mCapabilityMechanism = i;
        this.mSourceType = i2;
    }

    private RcsContactUceCapability(android.os.Parcel parcel) {
        this.mFeatureTags = new java.util.HashSet();
        this.mPresenceTuples = new java.util.ArrayList();
        this.mContactUri = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class);
        this.mCapabilityMechanism = parcel.readInt();
        this.mSourceType = parcel.readInt();
        this.mRequestResult = parcel.readInt();
        this.mEntityUri = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        this.mFeatureTags.addAll(arrayList);
        parcel.readParcelableList(this.mPresenceTuples, android.telephony.ims.RcsContactPresenceTuple.class.getClassLoader(), android.telephony.ims.RcsContactPresenceTuple.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mContactUri, i);
        parcel.writeInt(this.mCapabilityMechanism);
        parcel.writeInt(this.mSourceType);
        parcel.writeInt(this.mRequestResult);
        parcel.writeParcelable(this.mEntityUri, i);
        parcel.writeStringList(new java.util.ArrayList(this.mFeatureTags));
        parcel.writeParcelableList(this.mPresenceTuples, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCapabilityMechanism() {
        return this.mCapabilityMechanism;
    }

    public java.util.Set<java.lang.String> getFeatureTags() {
        if (this.mCapabilityMechanism != 2) {
            return java.util.Collections.emptySet();
        }
        return java.util.Collections.unmodifiableSet(this.mFeatureTags);
    }

    public java.util.List<android.telephony.ims.RcsContactPresenceTuple> getCapabilityTuples() {
        if (this.mCapabilityMechanism != 1) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.unmodifiableList(this.mPresenceTuples);
    }

    public android.telephony.ims.RcsContactPresenceTuple getCapabilityTuple(java.lang.String str) {
        if (this.mCapabilityMechanism != 1) {
            return null;
        }
        for (android.telephony.ims.RcsContactPresenceTuple rcsContactPresenceTuple : this.mPresenceTuples) {
            if (rcsContactPresenceTuple.getServiceId() != null && rcsContactPresenceTuple.getServiceId().equals(str)) {
                return rcsContactPresenceTuple;
            }
        }
        return null;
    }

    public int getSourceType() {
        return this.mSourceType;
    }

    public int getRequestResult() {
        return this.mRequestResult;
    }

    public android.net.Uri getContactUri() {
        return this.mContactUri;
    }

    public android.net.Uri getEntityUri() {
        return this.mEntityUri;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("RcsContactUceCapability");
        if (this.mCapabilityMechanism == 1) {
            sb.append("(presence) {");
        } else if (this.mCapabilityMechanism == 2) {
            sb.append("(options) {");
        } else {
            sb.append("(?) {");
        }
        if (android.os.Build.IS_ENG) {
            sb.append("uri=");
            sb.append(this.mContactUri);
        } else {
            sb.append("uri (isNull)=");
            sb.append(this.mContactUri != null ? "XXX" : "null");
        }
        sb.append(", sourceType=");
        sb.append(this.mSourceType);
        sb.append(", requestResult=");
        sb.append(this.mRequestResult);
        if (android.os.Build.IS_ENG) {
            sb.append("entity uri=");
            sb.append(this.mEntityUri != null ? this.mEntityUri : "null");
        } else {
            sb.append("entity uri (isNull)=");
            sb.append(this.mEntityUri == null ? "null" : "XXX");
        }
        if (this.mCapabilityMechanism == 1) {
            sb.append(", presenceTuples={");
            sb.append(this.mPresenceTuples);
            sb.append("}");
        } else if (this.mCapabilityMechanism == 2) {
            sb.append(", featureTags={");
            sb.append(this.mFeatureTags);
            sb.append("}");
        }
        return sb.toString();
    }
}
