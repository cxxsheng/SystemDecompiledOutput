package android.content.pm.verify.domain;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class DomainVerificationInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.verify.domain.DomainVerificationInfo> CREATOR;
    public static final int STATE_FIRST_VERIFIER_DEFINED = 1024;
    public static final int STATE_MODIFIABLE_UNVERIFIED = 3;
    public static final int STATE_MODIFIABLE_VERIFIED = 4;
    public static final int STATE_NO_RESPONSE = 0;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_UNMODIFIABLE = 2;
    static com.android.internal.util.Parcelling<java.util.UUID> sParcellingForIdentifier;
    private final java.util.Map<java.lang.String, java.lang.Integer> mHostToStateMap;
    private final java.util.UUID mIdentifier;
    private final java.lang.String mPackageName;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    private void parcelHostToStateMap(android.os.Parcel parcel, int i) {
        android.content.pm.verify.domain.DomainVerificationUtils.writeHostMap(parcel, this.mHostToStateMap);
    }

    private java.util.Map<java.lang.String, java.lang.Integer> unparcelHostToStateMap(android.os.Parcel parcel) {
        return android.content.pm.verify.domain.DomainVerificationUtils.readHostMap(parcel, new android.util.ArrayMap(), android.content.pm.verify.domain.DomainVerificationUserState.class.getClassLoader());
    }

    public static java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "STATE_NO_RESPONSE";
            case 1:
                return "STATE_SUCCESS";
            case 2:
                return "STATE_UNMODIFIABLE";
            case 3:
                return "STATE_MODIFIABLE_UNVERIFIED";
            case 4:
                return "STATE_MODIFIABLE_VERIFIED";
            case 1024:
                return "STATE_FIRST_VERIFIER_DEFINED";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    public DomainVerificationInfo(java.util.UUID uuid, java.lang.String str, java.util.Map<java.lang.String, java.lang.Integer> map) {
        this.mIdentifier = uuid;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mIdentifier);
        this.mPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mHostToStateMap = map;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHostToStateMap);
    }

    public java.util.UUID getIdentifier() {
        return this.mIdentifier;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.util.Map<java.lang.String, java.lang.Integer> getHostToStateMap() {
        return this.mHostToStateMap;
    }

    public java.lang.String toString() {
        return "DomainVerificationInfo { identifier = " + this.mIdentifier + ", packageName = " + this.mPackageName + ", hostToStateMap = " + this.mHostToStateMap + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.pm.verify.domain.DomainVerificationInfo domainVerificationInfo = (android.content.pm.verify.domain.DomainVerificationInfo) obj;
        if (java.util.Objects.equals(this.mIdentifier, domainVerificationInfo.mIdentifier) && java.util.Objects.equals(this.mPackageName, domainVerificationInfo.mPackageName) && java.util.Objects.equals(this.mHostToStateMap, domainVerificationInfo.mHostToStateMap)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Objects.hashCode(this.mIdentifier) + 31) * 31) + java.util.Objects.hashCode(this.mPackageName)) * 31) + java.util.Objects.hashCode(this.mHostToStateMap);
    }

    static {
        sParcellingForIdentifier = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForUUID.class);
        if (sParcellingForIdentifier == null) {
            sParcellingForIdentifier = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForUUID());
        }
        CREATOR = new android.os.Parcelable.Creator<android.content.pm.verify.domain.DomainVerificationInfo>() { // from class: android.content.pm.verify.domain.DomainVerificationInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.verify.domain.DomainVerificationInfo[] newArray(int i) {
                return new android.content.pm.verify.domain.DomainVerificationInfo[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.verify.domain.DomainVerificationInfo createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.verify.domain.DomainVerificationInfo(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        sParcellingForIdentifier.parcel(this.mIdentifier, parcel, i);
        parcel.writeString(this.mPackageName);
        parcelHostToStateMap(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    DomainVerificationInfo(android.os.Parcel parcel) {
        java.util.UUID unparcel = sParcellingForIdentifier.unparcel(parcel);
        java.lang.String readString = parcel.readString();
        java.util.Map<java.lang.String, java.lang.Integer> unparcelHostToStateMap = unparcelHostToStateMap(parcel);
        this.mIdentifier = unparcel;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mIdentifier);
        this.mPackageName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mHostToStateMap = unparcelHostToStateMap;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHostToStateMap);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
