package android.hardware.soundtrigger;

/* loaded from: classes2.dex */
public final class KeyphraseMetadata implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.soundtrigger.KeyphraseMetadata> CREATOR = new android.os.Parcelable.Creator<android.hardware.soundtrigger.KeyphraseMetadata>() { // from class: android.hardware.soundtrigger.KeyphraseMetadata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.soundtrigger.KeyphraseMetadata[] newArray(int i) {
            return new android.hardware.soundtrigger.KeyphraseMetadata[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.soundtrigger.KeyphraseMetadata createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.soundtrigger.KeyphraseMetadata(parcel);
        }
    };
    private final int mId;
    private final java.lang.String mKeyphrase;
    private final int mRecognitionModeFlags;
    private final android.util.ArraySet<java.util.Locale> mSupportedLocales;

    public KeyphraseMetadata(int i, java.lang.String str, java.util.Set<java.util.Locale> set, int i2) {
        this.mId = i;
        this.mKeyphrase = str;
        this.mSupportedLocales = new android.util.ArraySet<>(set);
        this.mRecognitionModeFlags = i2;
    }

    public int getId() {
        return this.mId;
    }

    public java.lang.String getKeyphrase() {
        return this.mKeyphrase;
    }

    public java.util.Set<java.util.Locale> getSupportedLocales() {
        return this.mSupportedLocales;
    }

    public int getRecognitionModeFlags() {
        return this.mRecognitionModeFlags;
    }

    public boolean supportsPhrase(java.lang.String str) {
        return getKeyphrase().isEmpty() || getKeyphrase().equalsIgnoreCase(str);
    }

    public boolean supportsLocale(java.util.Locale locale) {
        return getSupportedLocales().isEmpty() || getSupportedLocales().contains(locale);
    }

    public java.lang.String toString() {
        return "KeyphraseMetadata { id = " + this.mId + ", keyphrase = " + this.mKeyphrase + ", supportedLocales = " + this.mSupportedLocales + ", recognitionModeFlags = " + this.mRecognitionModeFlags + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.hardware.soundtrigger.KeyphraseMetadata keyphraseMetadata = (android.hardware.soundtrigger.KeyphraseMetadata) obj;
        if (this.mId == keyphraseMetadata.mId && java.util.Objects.equals(this.mKeyphrase, keyphraseMetadata.mKeyphrase) && java.util.Objects.equals(this.mSupportedLocales, keyphraseMetadata.mSupportedLocales) && this.mRecognitionModeFlags == keyphraseMetadata.mRecognitionModeFlags) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.mId + 31) * 31) + java.util.Objects.hashCode(this.mKeyphrase)) * 31) + java.util.Objects.hashCode(this.mSupportedLocales)) * 31) + this.mRecognitionModeFlags;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mKeyphrase);
        parcel.writeArraySet(this.mSupportedLocales);
        parcel.writeInt(this.mRecognitionModeFlags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    KeyphraseMetadata(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.lang.String readString = parcel.readString();
        android.util.ArraySet readArraySet = parcel.readArraySet(null);
        int readInt2 = parcel.readInt();
        this.mId = readInt;
        this.mKeyphrase = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mKeyphrase);
        this.mSupportedLocales = readArraySet;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSupportedLocales);
        this.mRecognitionModeFlags = readInt2;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
