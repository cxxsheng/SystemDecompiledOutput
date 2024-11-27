package android.credentials;

/* loaded from: classes.dex */
public final class CredentialDescription implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.CredentialDescription> CREATOR = new android.os.Parcelable.Creator<android.credentials.CredentialDescription>() { // from class: android.credentials.CredentialDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.CredentialDescription createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.CredentialDescription(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.CredentialDescription[] newArray(int i) {
            return new android.credentials.CredentialDescription[i];
        }
    };
    private static final int MAX_ALLOWED_ENTRIES_PER_DESCRIPTION = 16;
    private final java.util.List<android.service.credentials.CredentialEntry> mCredentialEntries;
    private final java.util.Set<java.lang.String> mSupportedElementKeys;
    private final java.lang.String mType;

    public CredentialDescription(java.lang.String str, java.util.Set<java.lang.String> set, java.util.List<android.service.credentials.CredentialEntry> list) {
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "type must not be empty");
        this.mSupportedElementKeys = (java.util.Set) java.util.Objects.requireNonNull(set);
        this.mCredentialEntries = (java.util.List) java.util.Objects.requireNonNull(list);
        com.android.internal.util.Preconditions.checkArgument(list.size() <= 16, "The number of Credential Entries exceed 16.");
        com.android.internal.util.Preconditions.checkArgument(compareEntryTypes(str, list) == 0, "Credential Entry type(s) do not match the request type.");
    }

    private CredentialDescription(android.os.Parcel parcel) {
        java.lang.String readString8 = parcel.readString8();
        java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.service.credentials.CredentialEntry.CREATOR);
        this.mType = readString8;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mType);
        this.mSupportedElementKeys = new java.util.HashSet(createStringArrayList);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSupportedElementKeys);
        this.mCredentialEntries = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCredentialEntries);
    }

    private static int compareEntryTypes(final java.lang.String str, java.util.List<android.service.credentials.CredentialEntry> list) {
        return list.stream().filter(new java.util.function.Predicate() { // from class: android.credentials.CredentialDescription$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.credentials.CredentialDescription.lambda$compareEntryTypes$0(str, (android.service.credentials.CredentialEntry) obj);
            }
        }).toList().size();
    }

    static /* synthetic */ boolean lambda$compareEntryTypes$0(java.lang.String str, android.service.credentials.CredentialEntry credentialEntry) {
        return !credentialEntry.getType().equals(str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeStringList(this.mSupportedElementKeys.stream().toList());
        parcel.writeTypedList(this.mCredentialEntries, i);
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public java.util.Set<java.lang.String> getSupportedElementKeys() {
        return new java.util.HashSet(this.mSupportedElementKeys);
    }

    public java.util.List<android.service.credentials.CredentialEntry> getCredentialEntries() {
        return this.mCredentialEntries;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mType, this.mSupportedElementKeys);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.credentials.CredentialDescription)) {
            return false;
        }
        android.credentials.CredentialDescription credentialDescription = (android.credentials.CredentialDescription) obj;
        return this.mType.equals(credentialDescription.mType) && this.mSupportedElementKeys.equals(credentialDescription.mSupportedElementKeys);
    }
}
