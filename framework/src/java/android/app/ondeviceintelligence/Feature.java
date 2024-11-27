package android.app.ondeviceintelligence;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class Feature implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ondeviceintelligence.Feature> CREATOR = new android.os.Parcelable.Creator<android.app.ondeviceintelligence.Feature>() { // from class: android.app.ondeviceintelligence.Feature.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ondeviceintelligence.Feature[] newArray(int i) {
            return new android.app.ondeviceintelligence.Feature[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ondeviceintelligence.Feature createFromParcel(android.os.Parcel parcel) {
            return new android.app.ondeviceintelligence.Feature(parcel);
        }
    };
    private final android.os.PersistableBundle mFeatureParams;
    private final int mId;
    private final java.lang.String mModelName;
    private final java.lang.String mName;
    private final int mType;
    private final int mVariant;

    Feature(int i, java.lang.String str, java.lang.String str2, int i2, int i3, android.os.PersistableBundle persistableBundle) {
        this.mId = i;
        this.mName = str;
        this.mModelName = str2;
        this.mType = i2;
        this.mVariant = i3;
        this.mFeatureParams = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mFeatureParams);
    }

    public int getId() {
        return this.mId;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String getModelName() {
        return this.mModelName;
    }

    public int getType() {
        return this.mType;
    }

    public int getVariant() {
        return this.mVariant;
    }

    public android.os.PersistableBundle getFeatureParams() {
        return this.mFeatureParams;
    }

    public java.lang.String toString() {
        return "Feature { id = " + this.mId + ", name = " + this.mName + ", modelName = " + this.mModelName + ", type = " + this.mType + ", variant = " + this.mVariant + ", featureParams = " + this.mFeatureParams + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.ondeviceintelligence.Feature feature = (android.app.ondeviceintelligence.Feature) obj;
        if (this.mId == feature.mId && java.util.Objects.equals(this.mName, feature.mName) && java.util.Objects.equals(this.mModelName, feature.mModelName) && this.mType == feature.mType && this.mVariant == feature.mVariant && java.util.Objects.equals(this.mFeatureParams, feature.mFeatureParams)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((this.mId + 31) * 31) + java.util.Objects.hashCode(this.mName)) * 31) + java.util.Objects.hashCode(this.mModelName)) * 31) + this.mType) * 31) + this.mVariant) * 31) + java.util.Objects.hashCode(this.mFeatureParams);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mName != null ? (byte) 2 : (byte) 0;
        if (this.mModelName != null) {
            b = (byte) (b | 4);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mId);
        if (this.mName != null) {
            parcel.writeString8(this.mName);
        }
        if (this.mModelName != null) {
            parcel.writeString8(this.mModelName);
        }
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mVariant);
        parcel.writeTypedObject(this.mFeatureParams, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Feature(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        java.lang.String readString = (readByte & 2) == 0 ? null : parcel.readString();
        java.lang.String readString2 = (readByte & 4) == 0 ? null : parcel.readString();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
        this.mId = readInt;
        this.mName = readString;
        this.mModelName = readString2;
        this.mType = readInt2;
        this.mVariant = readInt3;
        this.mFeatureParams = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mFeatureParams);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private android.os.PersistableBundle mFeatureParams;
        private int mId;
        private java.lang.String mModelName;
        private java.lang.String mName;
        private int mType;
        private int mVariant;

        public Builder(int i, int i2, int i3, android.os.PersistableBundle persistableBundle) {
            this.mId = i;
            this.mType = i2;
            this.mVariant = i3;
            this.mFeatureParams = persistableBundle;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mFeatureParams);
        }

        public android.app.ondeviceintelligence.Feature.Builder setId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mId = i;
            return this;
        }

        public android.app.ondeviceintelligence.Feature.Builder setName(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mName = str;
            return this;
        }

        public android.app.ondeviceintelligence.Feature.Builder setModelName(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mModelName = str;
            return this;
        }

        public android.app.ondeviceintelligence.Feature.Builder setType(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mType = i;
            return this;
        }

        public android.app.ondeviceintelligence.Feature.Builder setVariant(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mVariant = i;
            return this;
        }

        public android.app.ondeviceintelligence.Feature.Builder setFeatureParams(android.os.PersistableBundle persistableBundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mFeatureParams = persistableBundle;
            return this;
        }

        public android.app.ondeviceintelligence.Feature build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            return new android.app.ondeviceintelligence.Feature(this.mId, this.mName, this.mModelName, this.mType, this.mVariant, this.mFeatureParams);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 64) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
