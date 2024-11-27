package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class ParcelableHandwritingGesture implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.ParcelableHandwritingGesture> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.ParcelableHandwritingGesture>() { // from class: android.view.inputmethod.ParcelableHandwritingGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.ParcelableHandwritingGesture createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.ParcelableHandwritingGesture(android.view.inputmethod.ParcelableHandwritingGesture.createFromParcelInternal(parcel.readInt(), parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.ParcelableHandwritingGesture[] newArray(int i) {
            return new android.view.inputmethod.ParcelableHandwritingGesture[i];
        }
    };
    private final android.view.inputmethod.HandwritingGesture mGesture;
    private final android.os.Parcelable mGestureAsParcelable;

    /* JADX WARN: Multi-variable type inference failed */
    private ParcelableHandwritingGesture(android.view.inputmethod.HandwritingGesture handwritingGesture) {
        this.mGesture = handwritingGesture;
        this.mGestureAsParcelable = (android.os.Parcelable) handwritingGesture;
    }

    public static android.view.inputmethod.ParcelableHandwritingGesture of(android.view.inputmethod.HandwritingGesture handwritingGesture) {
        return new android.view.inputmethod.ParcelableHandwritingGesture((android.view.inputmethod.HandwritingGesture) java.util.Objects.requireNonNull(handwritingGesture));
    }

    public android.view.inputmethod.HandwritingGesture get() {
        return this.mGesture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.view.inputmethod.HandwritingGesture createFromParcelInternal(int i, android.os.Parcel parcel) {
        switch (i) {
            case 0:
                throw new java.lang.UnsupportedOperationException("GESTURE_TYPE_NONE is not supported");
            case 1:
                return android.view.inputmethod.SelectGesture.CREATOR.createFromParcel(parcel);
            case 2:
                return android.view.inputmethod.InsertGesture.CREATOR.createFromParcel(parcel);
            case 4:
                return android.view.inputmethod.DeleteGesture.CREATOR.createFromParcel(parcel);
            case 8:
                return android.view.inputmethod.RemoveSpaceGesture.CREATOR.createFromParcel(parcel);
            case 16:
                return android.view.inputmethod.JoinOrSplitGesture.CREATOR.createFromParcel(parcel);
            case 32:
                return android.view.inputmethod.SelectRangeGesture.CREATOR.createFromParcel(parcel);
            case 64:
                return android.view.inputmethod.DeleteRangeGesture.CREATOR.createFromParcel(parcel);
            case 128:
                return android.view.inputmethod.InsertModeGesture.CREATOR.createFromParcel(parcel);
            default:
                throw new java.lang.UnsupportedOperationException("Unknown type=" + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mGestureAsParcelable.describeContents();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mGesture.getGestureType());
        this.mGestureAsParcelable.writeToParcel(parcel, i);
    }
}
