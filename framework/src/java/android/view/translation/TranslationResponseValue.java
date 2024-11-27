package android.view.translation;

/* loaded from: classes4.dex */
public final class TranslationResponseValue implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.translation.TranslationResponseValue> CREATOR = new android.os.Parcelable.Creator<android.view.translation.TranslationResponseValue>() { // from class: android.view.translation.TranslationResponseValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationResponseValue[] newArray(int i) {
            return new android.view.translation.TranslationResponseValue[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationResponseValue createFromParcel(android.os.Parcel parcel) {
            return new android.view.translation.TranslationResponseValue(parcel);
        }
    };
    public static final java.lang.String EXTRA_DEFINITIONS = "android.view.translation.extra.DEFINITIONS";
    public static final int STATUS_ERROR = 1;
    public static final int STATUS_SUCCESS = 0;
    private final android.os.Bundle mExtras;
    private final int mStatusCode;
    private final java.lang.CharSequence mText;
    private final java.lang.CharSequence mTransliteration;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    public static android.view.translation.TranslationResponseValue forError() {
        return new android.view.translation.TranslationResponseValue(1, null, android.os.Bundle.EMPTY, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.CharSequence defaultText() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.Bundle defaultExtras() {
        return android.os.Bundle.EMPTY;
    }

    private boolean extrasEquals(android.os.Bundle bundle) {
        return java.util.Objects.equals(this.mExtras, bundle) || (this.mExtras.isEmpty() && bundle.isEmpty());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.CharSequence defaultTransliteration() {
        return null;
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }
    }

    public static java.lang.String statusToString(int i) {
        switch (i) {
            case 0:
                return "STATUS_SUCCESS";
            case 1:
                return "STATUS_ERROR";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    TranslationResponseValue(int i, java.lang.CharSequence charSequence, android.os.Bundle bundle, java.lang.CharSequence charSequence2) {
        this.mStatusCode = i;
        if (this.mStatusCode != 0 && this.mStatusCode != 1) {
            throw new java.lang.IllegalArgumentException("statusCode was " + this.mStatusCode + " but must be one of: STATUS_SUCCESS(0), STATUS_ERROR(1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mText = charSequence;
        this.mExtras = bundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mExtras);
        this.mTransliteration = charSequence2;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public java.lang.CharSequence getText() {
        return this.mText;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public java.lang.CharSequence getTransliteration() {
        return this.mTransliteration;
    }

    public java.lang.String toString() {
        return "TranslationResponseValue { statusCode = " + statusToString(this.mStatusCode) + ", text = " + ((java.lang.Object) this.mText) + ", extras = " + this.mExtras + ", transliteration = " + ((java.lang.Object) this.mTransliteration) + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.translation.TranslationResponseValue translationResponseValue = (android.view.translation.TranslationResponseValue) obj;
        if (this.mStatusCode == translationResponseValue.mStatusCode && java.util.Objects.equals(this.mText, translationResponseValue.mText) && extrasEquals(translationResponseValue.mExtras) && java.util.Objects.equals(this.mTransliteration, translationResponseValue.mTransliteration)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.mStatusCode + 31) * 31) + java.util.Objects.hashCode(this.mText)) * 31) + java.util.Objects.hashCode(this.mExtras)) * 31) + java.util.Objects.hashCode(this.mTransliteration);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mText != null ? (byte) 2 : (byte) 0;
        if (this.mTransliteration != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mStatusCode);
        if (this.mText != null) {
            parcel.writeCharSequence(this.mText);
        }
        parcel.writeBundle(this.mExtras);
        if (this.mTransliteration != null) {
            parcel.writeCharSequence(this.mTransliteration);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TranslationResponseValue(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        java.lang.CharSequence readCharSequence = (readByte & 2) == 0 ? null : parcel.readCharSequence();
        android.os.Bundle readBundle = parcel.readBundle();
        java.lang.CharSequence readCharSequence2 = (readByte & 8) == 0 ? null : parcel.readCharSequence();
        this.mStatusCode = readInt;
        if (this.mStatusCode != 0 && this.mStatusCode != 1) {
            throw new java.lang.IllegalArgumentException("statusCode was " + this.mStatusCode + " but must be one of: STATUS_SUCCESS(0), STATUS_ERROR(1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mText = readCharSequence;
        this.mExtras = readBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mExtras);
        this.mTransliteration = readCharSequence2;
    }

    public static final class Builder extends android.view.translation.TranslationResponseValue.BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private android.os.Bundle mExtras;
        private int mStatusCode;
        private java.lang.CharSequence mText;
        private java.lang.CharSequence mTransliteration;

        public Builder(int i) {
            this.mStatusCode = i;
            if (this.mStatusCode != 0 && this.mStatusCode != 1) {
                throw new java.lang.IllegalArgumentException("statusCode was " + this.mStatusCode + " but must be one of: STATUS_SUCCESS(0), STATUS_ERROR(1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }

        public android.view.translation.TranslationResponseValue.Builder setText(java.lang.CharSequence charSequence) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mText = charSequence;
            return this;
        }

        public android.view.translation.TranslationResponseValue.Builder setExtras(android.os.Bundle bundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mExtras = bundle;
            return this;
        }

        public android.view.translation.TranslationResponseValue.Builder setTransliteration(java.lang.CharSequence charSequence) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mTransliteration = charSequence;
            return this;
        }

        public android.view.translation.TranslationResponseValue build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mText = android.view.translation.TranslationResponseValue.defaultText();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mExtras = android.view.translation.TranslationResponseValue.defaultExtras();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mTransliteration = android.view.translation.TranslationResponseValue.defaultTransliteration();
            }
            return new android.view.translation.TranslationResponseValue(this.mStatusCode, this.mText, this.mExtras, this.mTransliteration);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
