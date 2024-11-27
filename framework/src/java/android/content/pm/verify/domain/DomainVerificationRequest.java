package android.content.pm.verify.domain;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class DomainVerificationRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.verify.domain.DomainVerificationRequest> CREATOR;
    static com.android.internal.util.Parcelling<java.util.Set<java.lang.String>> sParcellingForPackageNames;
    private final java.util.Set<java.lang.String> mPackageNames;

    private void parcelPackageNames(android.os.Parcel parcel, int i) {
        android.content.pm.verify.domain.DomainVerificationUtils.writeHostSet(parcel, this.mPackageNames);
    }

    private java.util.Set<java.lang.String> unparcelPackageNames(android.os.Parcel parcel) {
        return android.content.pm.verify.domain.DomainVerificationUtils.readHostSet(parcel);
    }

    public DomainVerificationRequest(java.util.Set<java.lang.String> set) {
        this.mPackageNames = set;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageNames);
    }

    public java.util.Set<java.lang.String> getPackageNames() {
        return this.mPackageNames;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mPackageNames, ((android.content.pm.verify.domain.DomainVerificationRequest) obj).mPackageNames);
    }

    public int hashCode() {
        return 31 + java.util.Objects.hashCode(this.mPackageNames);
    }

    static {
        sParcellingForPackageNames = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForStringSet.class);
        if (sParcellingForPackageNames == null) {
            sParcellingForPackageNames = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForStringSet());
        }
        CREATOR = new android.os.Parcelable.Creator<android.content.pm.verify.domain.DomainVerificationRequest>() { // from class: android.content.pm.verify.domain.DomainVerificationRequest.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.verify.domain.DomainVerificationRequest[] newArray(int i) {
                return new android.content.pm.verify.domain.DomainVerificationRequest[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.verify.domain.DomainVerificationRequest createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.verify.domain.DomainVerificationRequest(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcelPackageNames(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    DomainVerificationRequest(android.os.Parcel parcel) {
        this.mPackageNames = unparcelPackageNames(parcel);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageNames);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
