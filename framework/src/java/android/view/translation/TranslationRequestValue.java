package android.view.translation;

/* loaded from: classes4.dex */
public final class TranslationRequestValue implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.translation.TranslationRequestValue> CREATOR = new android.os.Parcelable.Creator<android.view.translation.TranslationRequestValue>() { // from class: android.view.translation.TranslationRequestValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationRequestValue[] newArray(int i) {
            return new android.view.translation.TranslationRequestValue[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationRequestValue createFromParcel(android.os.Parcel parcel) {
            return new android.view.translation.TranslationRequestValue(parcel);
        }
    };
    private final java.lang.CharSequence mText;

    public static android.view.translation.TranslationRequestValue forText(java.lang.CharSequence charSequence) {
        java.util.Objects.requireNonNull(charSequence, "text should not be null");
        return new android.view.translation.TranslationRequestValue(charSequence);
    }

    public java.lang.CharSequence getText() {
        return this.mText;
    }

    public TranslationRequestValue(java.lang.CharSequence charSequence) {
        this.mText = charSequence;
    }

    public java.lang.String toString() {
        return "TranslationRequestValue { text = " + ((java.lang.Object) this.mText) + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mText, ((android.view.translation.TranslationRequestValue) obj).mText);
    }

    public int hashCode() {
        return 31 + java.util.Objects.hashCode(this.mText);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mText != null ? (byte) 1 : (byte) 0);
        if (this.mText != null) {
            parcel.writeCharSequence(this.mText);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TranslationRequestValue(android.os.Parcel parcel) {
        this.mText = (parcel.readByte() & 1) == 0 ? null : parcel.readCharSequence();
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
