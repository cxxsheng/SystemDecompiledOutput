package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class TextAttribute implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.TextAttribute> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.TextAttribute>() { // from class: android.view.inputmethod.TextAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.TextAttribute createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.TextAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.TextAttribute[] newArray(int i) {
            return new android.view.inputmethod.TextAttribute[i];
        }
    };
    private final android.os.PersistableBundle mExtras;
    private final java.util.List<java.lang.String> mTextConversionSuggestions;

    private TextAttribute(android.view.inputmethod.TextAttribute.Builder builder) {
        this.mTextConversionSuggestions = builder.mTextConversionSuggestions;
        this.mExtras = builder.mExtras;
    }

    private TextAttribute(android.os.Parcel parcel) {
        this.mTextConversionSuggestions = parcel.createStringArrayList();
        this.mExtras = parcel.readPersistableBundle();
    }

    public java.util.List<java.lang.String> getTextConversionSuggestions() {
        return this.mTextConversionSuggestions;
    }

    public android.os.PersistableBundle getExtras() {
        return this.mExtras;
    }

    public static final class Builder {
        private java.util.List<java.lang.String> mTextConversionSuggestions = new java.util.ArrayList();
        private android.os.PersistableBundle mExtras = new android.os.PersistableBundle();

        public android.view.inputmethod.TextAttribute.Builder setTextConversionSuggestions(java.util.List<java.lang.String> list) {
            this.mTextConversionSuggestions = java.util.Collections.unmodifiableList(list);
            return this;
        }

        public android.view.inputmethod.TextAttribute.Builder setExtras(android.os.PersistableBundle persistableBundle) {
            this.mExtras = persistableBundle;
            return this;
        }

        public android.view.inputmethod.TextAttribute build() {
            return new android.view.inputmethod.TextAttribute(this);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStringList(this.mTextConversionSuggestions);
        parcel.writePersistableBundle(this.mExtras);
    }
}
