package android.view.translation;

/* loaded from: classes4.dex */
public final class TranslationSpec implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.translation.TranslationSpec> CREATOR = new android.os.Parcelable.Creator<android.view.translation.TranslationSpec>() { // from class: android.view.translation.TranslationSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationSpec[] newArray(int i) {
            return new android.view.translation.TranslationSpec[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationSpec createFromParcel(android.os.Parcel parcel) {
            return new android.view.translation.TranslationSpec(parcel);
        }
    };
    public static final int DATA_FORMAT_TEXT = 1;
    private final int mDataFormat;

    @java.lang.Deprecated
    private final java.lang.String mLanguage;
    private final android.icu.util.ULocale mLocale;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataFormat {
    }

    void parcelLocale(android.os.Parcel parcel, int i) {
        parcel.writeSerializable(this.mLocale);
    }

    static android.icu.util.ULocale unparcelLocale(android.os.Parcel parcel) {
        return (android.icu.util.ULocale) parcel.readSerializable(android.icu.util.ULocale.class.getClassLoader(), android.icu.util.ULocale.class);
    }

    @java.lang.Deprecated
    public TranslationSpec(java.lang.String str, int i) {
        this.mLanguage = str;
        this.mDataFormat = i;
        this.mLocale = new android.icu.util.ULocale.Builder().setLanguage(str).build();
    }

    public TranslationSpec(android.icu.util.ULocale uLocale, int i) {
        java.util.Objects.requireNonNull(uLocale);
        this.mLanguage = uLocale.getLanguage();
        this.mLocale = uLocale;
        this.mDataFormat = i;
    }

    @java.lang.Deprecated
    public java.lang.String getLanguage() {
        return this.mLanguage;
    }

    public android.icu.util.ULocale getLocale() {
        return this.mLocale;
    }

    public int getDataFormat() {
        return this.mDataFormat;
    }

    public java.lang.String toString() {
        return "TranslationSpec { language = " + this.mLanguage + ", locale = " + this.mLocale + ", dataFormat = " + this.mDataFormat + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.translation.TranslationSpec translationSpec = (android.view.translation.TranslationSpec) obj;
        if (java.util.Objects.equals(this.mLanguage, translationSpec.mLanguage) && java.util.Objects.equals(this.mLocale, translationSpec.mLocale) && this.mDataFormat == translationSpec.mDataFormat) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Objects.hashCode(this.mLanguage) + 31) * 31) + java.util.Objects.hashCode(this.mLocale)) * 31) + this.mDataFormat;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mLanguage);
        parcelLocale(parcel, i);
        parcel.writeInt(this.mDataFormat);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TranslationSpec(android.os.Parcel parcel) {
        java.lang.String readString = parcel.readString();
        android.icu.util.ULocale unparcelLocale = unparcelLocale(parcel);
        int readInt = parcel.readInt();
        this.mLanguage = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) java.lang.Deprecated.class, (java.lang.annotation.Annotation) null, this.mLanguage);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mLanguage);
        this.mLocale = unparcelLocale;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mLocale);
        this.mDataFormat = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.view.translation.TranslationSpec.DataFormat.class, (java.lang.annotation.Annotation) null, this.mDataFormat);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
