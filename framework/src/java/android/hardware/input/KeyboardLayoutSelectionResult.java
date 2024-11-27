package android.hardware.input;

/* loaded from: classes2.dex */
public final class KeyboardLayoutSelectionResult implements android.os.Parcelable {
    public static final int LAYOUT_SELECTION_CRITERIA_DEFAULT = 4;
    public static final int LAYOUT_SELECTION_CRITERIA_DEVICE = 2;
    public static final int LAYOUT_SELECTION_CRITERIA_UNSPECIFIED = 0;
    public static final int LAYOUT_SELECTION_CRITERIA_USER = 1;
    public static final int LAYOUT_SELECTION_CRITERIA_VIRTUAL_KEYBOARD = 3;
    private final java.lang.String mLayoutDescriptor;
    private final int mSelectionCriteria;
    public static final android.hardware.input.KeyboardLayoutSelectionResult FAILED = new android.hardware.input.KeyboardLayoutSelectionResult(null, 0);
    public static final android.os.Parcelable.Creator<android.hardware.input.KeyboardLayoutSelectionResult> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.KeyboardLayoutSelectionResult>() { // from class: android.hardware.input.KeyboardLayoutSelectionResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.KeyboardLayoutSelectionResult[] newArray(int i) {
            return new android.hardware.input.KeyboardLayoutSelectionResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.KeyboardLayoutSelectionResult createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.KeyboardLayoutSelectionResult(parcel);
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LayoutSelectionCriteria {
    }

    public static java.lang.String layoutSelectionCriteriaToString(int i) {
        switch (i) {
            case 0:
                return "LAYOUT_SELECTION_CRITERIA_UNSPECIFIED";
            case 1:
                return "LAYOUT_SELECTION_CRITERIA_USER";
            case 2:
                return "LAYOUT_SELECTION_CRITERIA_DEVICE";
            case 3:
                return "LAYOUT_SELECTION_CRITERIA_VIRTUAL_KEYBOARD";
            case 4:
                return "LAYOUT_SELECTION_CRITERIA_DEFAULT";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    public KeyboardLayoutSelectionResult(java.lang.String str, int i) {
        this.mLayoutDescriptor = str;
        this.mSelectionCriteria = i;
        if (this.mSelectionCriteria != 0 && this.mSelectionCriteria != 1 && this.mSelectionCriteria != 2 && this.mSelectionCriteria != 3 && this.mSelectionCriteria != 4) {
            throw new java.lang.IllegalArgumentException("selectionCriteria was " + this.mSelectionCriteria + " but must be one of: LAYOUT_SELECTION_CRITERIA_UNSPECIFIED(0), LAYOUT_SELECTION_CRITERIA_USER(1), LAYOUT_SELECTION_CRITERIA_DEVICE(2), LAYOUT_SELECTION_CRITERIA_VIRTUAL_KEYBOARD(3), LAYOUT_SELECTION_CRITERIA_DEFAULT(4" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public java.lang.String getLayoutDescriptor() {
        return this.mLayoutDescriptor;
    }

    public int getSelectionCriteria() {
        return this.mSelectionCriteria;
    }

    public java.lang.String toString() {
        return "KeyboardLayoutSelectionResult { layoutDescriptor = " + this.mLayoutDescriptor + ", selectionCriteria = " + layoutSelectionCriteriaToString(this.mSelectionCriteria) + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.hardware.input.KeyboardLayoutSelectionResult keyboardLayoutSelectionResult = (android.hardware.input.KeyboardLayoutSelectionResult) obj;
        if (java.util.Objects.equals(this.mLayoutDescriptor, keyboardLayoutSelectionResult.mLayoutDescriptor) && this.mSelectionCriteria == keyboardLayoutSelectionResult.mSelectionCriteria) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((java.util.Objects.hashCode(this.mLayoutDescriptor) + 31) * 31) + this.mSelectionCriteria;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mLayoutDescriptor != null ? (byte) 1 : (byte) 0);
        if (this.mLayoutDescriptor != null) {
            parcel.writeString(this.mLayoutDescriptor);
        }
        parcel.writeInt(this.mSelectionCriteria);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    KeyboardLayoutSelectionResult(android.os.Parcel parcel) {
        java.lang.String readString = (parcel.readByte() & 1) == 0 ? null : parcel.readString();
        int readInt = parcel.readInt();
        this.mLayoutDescriptor = readString;
        this.mSelectionCriteria = readInt;
        if (this.mSelectionCriteria != 0 && this.mSelectionCriteria != 1 && this.mSelectionCriteria != 2 && this.mSelectionCriteria != 3 && this.mSelectionCriteria != 4) {
            throw new java.lang.IllegalArgumentException("selectionCriteria was " + this.mSelectionCriteria + " but must be one of: LAYOUT_SELECTION_CRITERIA_UNSPECIFIED(0), LAYOUT_SELECTION_CRITERIA_USER(1), LAYOUT_SELECTION_CRITERIA_DEVICE(2), LAYOUT_SELECTION_CRITERIA_VIRTUAL_KEYBOARD(3), LAYOUT_SELECTION_CRITERIA_DEFAULT(4" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
