package android.service.controls.templates;

/* loaded from: classes3.dex */
public final class ControlButton implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.controls.templates.ControlButton> CREATOR = new android.os.Parcelable.Creator<android.service.controls.templates.ControlButton>() { // from class: android.service.controls.templates.ControlButton.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.controls.templates.ControlButton createFromParcel(android.os.Parcel parcel) {
            return new android.service.controls.templates.ControlButton(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.controls.templates.ControlButton[] newArray(int i) {
            return new android.service.controls.templates.ControlButton[i];
        }
    };
    private final java.lang.CharSequence mActionDescription;
    private final boolean mChecked;

    public ControlButton(boolean z, java.lang.CharSequence charSequence) {
        com.android.internal.util.Preconditions.checkNotNull(charSequence);
        this.mChecked = z;
        this.mActionDescription = charSequence;
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    public java.lang.CharSequence getActionDescription() {
        return this.mActionDescription;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mChecked ? (byte) 1 : (byte) 0);
        parcel.writeCharSequence(this.mActionDescription);
    }

    ControlButton(android.os.Parcel parcel) {
        this.mChecked = parcel.readByte() != 0;
        this.mActionDescription = parcel.readCharSequence();
    }
}
