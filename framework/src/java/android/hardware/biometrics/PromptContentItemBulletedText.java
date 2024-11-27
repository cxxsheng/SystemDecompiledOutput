package android.hardware.biometrics;

/* loaded from: classes.dex */
public final class PromptContentItemBulletedText implements android.hardware.biometrics.PromptContentItemParcelable {
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.PromptContentItemBulletedText> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.PromptContentItemBulletedText>() { // from class: android.hardware.biometrics.PromptContentItemBulletedText.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.PromptContentItemBulletedText createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.biometrics.PromptContentItemBulletedText(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.PromptContentItemBulletedText[] newArray(int i) {
            return new android.hardware.biometrics.PromptContentItemBulletedText[i];
        }
    };
    private final java.lang.String mText;

    public PromptContentItemBulletedText(java.lang.String str) {
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
