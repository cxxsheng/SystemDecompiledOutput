package android.hardware.biometrics;

/* loaded from: classes.dex */
public final class PromptContentItemPlainText implements android.hardware.biometrics.PromptContentItemParcelable {
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.PromptContentItemPlainText> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.PromptContentItemPlainText>() { // from class: android.hardware.biometrics.PromptContentItemPlainText.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.PromptContentItemPlainText createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.biometrics.PromptContentItemPlainText(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.PromptContentItemPlainText[] newArray(int i) {
            return new android.hardware.biometrics.PromptContentItemPlainText[i];
        }
    };
    private final java.lang.String mText;

    public PromptContentItemPlainText(java.lang.String str) {
        this.mText = str;
    }

    public java.lang.String getText() {
        return this.mText;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mText);
    }
}
