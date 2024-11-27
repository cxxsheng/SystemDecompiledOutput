package android.credentials;

/* loaded from: classes.dex */
public final class Credential implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.Credential> CREATOR = new android.os.Parcelable.Creator<android.credentials.Credential>() { // from class: android.credentials.Credential.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.Credential[] newArray(int i) {
            return new android.credentials.Credential[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.Credential createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.Credential(parcel);
        }
    };
    public static final java.lang.String TYPE_PASSWORD_CREDENTIAL = "android.credentials.TYPE_PASSWORD_CREDENTIAL";
    private final android.os.Bundle mData;
    private final java.lang.String mType;

    public java.lang.String getType() {
        return this.mType;
    }

    public android.os.Bundle getData() {
        return this.mData;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeBundle(this.mData);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "Credential {type=" + this.mType + ", data=" + this.mData + "}";
    }

    public Credential(java.lang.String str, android.os.Bundle bundle) {
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "type must not be empty");
        this.mData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "data must not be null");
    }

    private Credential(android.os.Parcel parcel) {
        java.lang.String readString8 = parcel.readString8();
        android.os.Bundle readBundle = parcel.readBundle();
        this.mType = readString8;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mType);
        this.mData = readBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mData);
    }
}
