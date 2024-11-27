package android.content.pm.verify.domain;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class DomainOwner implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.verify.domain.DomainOwner> CREATOR = new android.os.Parcelable.Creator<android.content.pm.verify.domain.DomainOwner>() { // from class: android.content.pm.verify.domain.DomainOwner.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.verify.domain.DomainOwner[] newArray(int i) {
            return new android.content.pm.verify.domain.DomainOwner[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.verify.domain.DomainOwner createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.verify.domain.DomainOwner(parcel);
        }
    };
    private final boolean mOverrideable;
    private final java.lang.String mPackageName;

    public DomainOwner(java.lang.String str, boolean z) {
        this.mPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mOverrideable = z;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public boolean isOverrideable() {
        return this.mOverrideable;
    }

    public java.lang.String toString() {
        return "DomainOwner { packageName = " + this.mPackageName + ", overrideable = " + this.mOverrideable + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.pm.verify.domain.DomainOwner domainOwner = (android.content.pm.verify.domain.DomainOwner) obj;
        if (java.util.Objects.equals(this.mPackageName, domainOwner.mPackageName) && this.mOverrideable == domainOwner.mOverrideable) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((java.util.Objects.hashCode(this.mPackageName) + 31) * 31) + java.lang.Boolean.hashCode(this.mOverrideable);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mOverrideable ? (byte) 2 : (byte) 0);
        parcel.writeString(this.mPackageName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    DomainOwner(android.os.Parcel parcel) {
        boolean z = (parcel.readByte() & 2) != 0;
        this.mPackageName = parcel.readString();
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mOverrideable = z;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
