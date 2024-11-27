package android.content.pm;

/* loaded from: classes.dex */
public final class CapabilityParams implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.CapabilityParams> CREATOR = new android.os.Parcelable.Creator<android.content.pm.CapabilityParams>() { // from class: android.content.pm.CapabilityParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.CapabilityParams[] newArray(int i) {
            return new android.content.pm.CapabilityParams[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.CapabilityParams createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.CapabilityParams(parcel);
        }
    };
    private final java.util.List<java.lang.String> mAliases;
    private final java.lang.String mName;
    private final java.lang.String mPrimaryValue;

    private CapabilityParams(java.lang.String str, java.lang.String str2, java.util.Collection<java.lang.String> collection) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        this.mName = str;
        this.mPrimaryValue = str2;
        this.mAliases = collection == null ? java.util.Collections.emptyList() : java.util.Collections.unmodifiableList(new java.util.ArrayList(collection));
    }

    CapabilityParams(android.content.pm.CapabilityParams capabilityParams) {
        this(capabilityParams.mName, capabilityParams.mPrimaryValue, capabilityParams.mAliases);
    }

    private CapabilityParams(android.content.pm.CapabilityParams.Builder builder) {
        this(builder.mKey, builder.mPrimaryValue, builder.mAliases);
    }

    private CapabilityParams(android.os.Parcel parcel) {
        this.mName = parcel.readString();
        this.mPrimaryValue = parcel.readString();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        this.mAliases = java.util.Collections.unmodifiableList(arrayList);
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String getValue() {
        return this.mPrimaryValue;
    }

    public java.util.List<java.lang.String> getAliases() {
        return new java.util.ArrayList(this.mAliases);
    }

    java.util.List<java.lang.String> getValues() {
        if (this.mAliases == null) {
            return new java.util.ArrayList(java.util.Collections.singletonList(this.mPrimaryValue));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mAliases.size() + 1);
        arrayList.add(this.mPrimaryValue);
        arrayList.addAll(this.mAliases);
        return arrayList;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.content.pm.CapabilityParams)) {
            return false;
        }
        android.content.pm.CapabilityParams capabilityParams = (android.content.pm.CapabilityParams) obj;
        return this.mName.equals(capabilityParams.mName) && this.mPrimaryValue.equals(capabilityParams.mPrimaryValue) && this.mAliases.equals(capabilityParams.mAliases);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mName, this.mPrimaryValue, this.mAliases);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mPrimaryValue);
        parcel.writeStringList(this.mAliases);
    }

    public static final class Builder {
        private java.util.Set<java.lang.String> mAliases;
        private final java.lang.String mKey;
        private final java.lang.String mPrimaryValue;

        public Builder(java.lang.String str, java.lang.String str2) {
            java.util.Objects.requireNonNull(str);
            if (android.text.TextUtils.isEmpty(str2)) {
                throw new java.lang.IllegalArgumentException("Primary value cannot be empty or null");
            }
            this.mPrimaryValue = str2;
            this.mKey = str;
        }

        public android.content.pm.CapabilityParams.Builder addAlias(java.lang.String str) {
            if (this.mAliases == null) {
                this.mAliases = new android.util.ArraySet(1);
            }
            this.mAliases.add(str);
            return this;
        }

        public android.content.pm.CapabilityParams build() {
            return new android.content.pm.CapabilityParams(this);
        }
    }
}
