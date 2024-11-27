package android.content.pm;

/* loaded from: classes.dex */
public final class Capability implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.Capability> CREATOR = new android.os.Parcelable.Creator<android.content.pm.Capability>() { // from class: android.content.pm.Capability.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.Capability[] newArray(int i) {
            return new android.content.pm.Capability[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.Capability createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.Capability(parcel);
        }
    };
    private final java.lang.String mName;

    Capability(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        if (str.contains("/")) {
            throw new java.lang.IllegalArgumentException("'/' is not permitted in the capability name");
        }
        this.mName = str;
    }

    Capability(android.content.pm.Capability capability) {
        this(capability.mName);
    }

    private Capability(android.content.pm.Capability.Builder builder) {
        this(builder.mName);
    }

    private Capability(android.os.Parcel parcel) {
        this.mName = parcel.readString();
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.content.pm.Capability)) {
            return false;
        }
        return this.mName.equals(((android.content.pm.Capability) obj).mName);
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private final java.lang.String mName;

        public Builder(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            if (str.contains("/")) {
                throw new java.lang.IllegalArgumentException("'/' is not permitted in the capability name");
            }
            this.mName = str;
        }

        public android.content.pm.Capability build() {
            return new android.content.pm.Capability(this);
        }
    }
}
