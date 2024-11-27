package android.content.pm.verify.domain;

/* loaded from: classes.dex */
public class DomainSet implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.verify.domain.DomainSet> CREATOR = new android.os.Parcelable.Creator<android.content.pm.verify.domain.DomainSet>() { // from class: android.content.pm.verify.domain.DomainSet.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.verify.domain.DomainSet[] newArray(int i) {
            return new android.content.pm.verify.domain.DomainSet[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.verify.domain.DomainSet createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.verify.domain.DomainSet(parcel);
        }
    };
    private final java.util.Set<java.lang.String> mDomains;

    private void parcelDomains(android.os.Parcel parcel, int i) {
        android.content.pm.verify.domain.DomainVerificationUtils.writeHostSet(parcel, this.mDomains);
    }

    private java.util.Set<java.lang.String> unparcelDomains(android.os.Parcel parcel) {
        return android.content.pm.verify.domain.DomainVerificationUtils.readHostSet(parcel);
    }

    public DomainSet(java.util.Set<java.lang.String> set) {
        this.mDomains = set;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mDomains);
    }

    public java.util.Set<java.lang.String> getDomains() {
        return this.mDomains;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mDomains, ((android.content.pm.verify.domain.DomainSet) obj).mDomains);
    }

    public int hashCode() {
        return 31 + java.util.Objects.hashCode(this.mDomains);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcelDomains(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected DomainSet(android.os.Parcel parcel) {
        this.mDomains = unparcelDomains(parcel);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mDomains);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
