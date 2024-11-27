package android.content.pm.verify.domain;

/* loaded from: classes.dex */
public final class DomainVerificationUserState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.verify.domain.DomainVerificationUserState> CREATOR;
    public static final int DOMAIN_STATE_NONE = 0;
    public static final int DOMAIN_STATE_SELECTED = 1;
    public static final int DOMAIN_STATE_VERIFIED = 2;
    static com.android.internal.util.Parcelling<java.util.UUID> sParcellingForIdentifier;
    private final java.util.Map<java.lang.String, java.lang.Integer> mHostToStateMap;
    private final java.util.UUID mIdentifier;
    private final boolean mLinkHandlingAllowed;
    private final java.lang.String mPackageName;
    private final android.os.UserHandle mUser;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DomainState {
    }

    private void parcelHostToStateMap(android.os.Parcel parcel, int i) {
        android.content.pm.verify.domain.DomainVerificationUtils.writeHostMap(parcel, this.mHostToStateMap);
    }

    private java.util.Map<java.lang.String, java.lang.Integer> unparcelHostToStateMap(android.os.Parcel parcel) {
        return android.content.pm.verify.domain.DomainVerificationUtils.readHostMap(parcel, new android.util.ArrayMap(), android.content.pm.verify.domain.DomainVerificationUserState.class.getClassLoader());
    }

    @android.annotation.SystemApi
    public java.util.UUID getIdentifier() {
        return this.mIdentifier;
    }

    public static java.lang.String domainStateToString(int i) {
        switch (i) {
            case 0:
                return "DOMAIN_STATE_NONE";
            case 1:
                return "DOMAIN_STATE_SELECTED";
            case 2:
                return "DOMAIN_STATE_VERIFIED";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    public DomainVerificationUserState(java.util.UUID uuid, java.lang.String str, android.os.UserHandle userHandle, boolean z, java.util.Map<java.lang.String, java.lang.Integer> map) {
        this.mIdentifier = uuid;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mIdentifier);
        this.mPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mUser = userHandle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mUser);
        this.mLinkHandlingAllowed = z;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) java.lang.Boolean.valueOf(this.mLinkHandlingAllowed));
        this.mHostToStateMap = map;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHostToStateMap);
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.os.UserHandle getUser() {
        return this.mUser;
    }

    public boolean isLinkHandlingAllowed() {
        return this.mLinkHandlingAllowed;
    }

    public java.util.Map<java.lang.String, java.lang.Integer> getHostToStateMap() {
        return this.mHostToStateMap;
    }

    public java.lang.String toString() {
        return "DomainVerificationUserState { identifier = " + this.mIdentifier + ", packageName = " + this.mPackageName + ", user = " + this.mUser + ", linkHandlingAllowed = " + this.mLinkHandlingAllowed + ", hostToStateMap = " + this.mHostToStateMap + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.pm.verify.domain.DomainVerificationUserState domainVerificationUserState = (android.content.pm.verify.domain.DomainVerificationUserState) obj;
        if (java.util.Objects.equals(this.mIdentifier, domainVerificationUserState.mIdentifier) && java.util.Objects.equals(this.mPackageName, domainVerificationUserState.mPackageName) && java.util.Objects.equals(this.mUser, domainVerificationUserState.mUser) && this.mLinkHandlingAllowed == domainVerificationUserState.mLinkHandlingAllowed && java.util.Objects.equals(this.mHostToStateMap, domainVerificationUserState.mHostToStateMap)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((java.util.Objects.hashCode(this.mIdentifier) + 31) * 31) + java.util.Objects.hashCode(this.mPackageName)) * 31) + java.util.Objects.hashCode(this.mUser)) * 31) + java.lang.Boolean.hashCode(this.mLinkHandlingAllowed)) * 31) + java.util.Objects.hashCode(this.mHostToStateMap);
    }

    static {
        sParcellingForIdentifier = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForUUID.class);
        if (sParcellingForIdentifier == null) {
            sParcellingForIdentifier = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForUUID());
        }
        CREATOR = new android.os.Parcelable.Creator<android.content.pm.verify.domain.DomainVerificationUserState>() { // from class: android.content.pm.verify.domain.DomainVerificationUserState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.verify.domain.DomainVerificationUserState[] newArray(int i) {
                return new android.content.pm.verify.domain.DomainVerificationUserState[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.verify.domain.DomainVerificationUserState createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.verify.domain.DomainVerificationUserState(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mLinkHandlingAllowed ? (byte) 8 : (byte) 0);
        sParcellingForIdentifier.parcel(this.mIdentifier, parcel, i);
        parcel.writeString(this.mPackageName);
        parcel.writeTypedObject(this.mUser, i);
        parcelHostToStateMap(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    DomainVerificationUserState(android.os.Parcel parcel) {
        boolean z = (parcel.readByte() & 8) != 0;
        java.util.UUID unparcel = sParcellingForIdentifier.unparcel(parcel);
        java.lang.String readString = parcel.readString();
        android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
        java.util.Map<java.lang.String, java.lang.Integer> unparcelHostToStateMap = unparcelHostToStateMap(parcel);
        this.mIdentifier = unparcel;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mIdentifier);
        this.mPackageName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mUser = userHandle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mUser);
        this.mLinkHandlingAllowed = z;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) java.lang.Boolean.valueOf(this.mLinkHandlingAllowed));
        this.mHostToStateMap = unparcelHostToStateMap;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHostToStateMap);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
