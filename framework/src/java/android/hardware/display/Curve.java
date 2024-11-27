package android.hardware.display;

/* loaded from: classes2.dex */
public final class Curve implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.display.Curve> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.Curve>() { // from class: android.hardware.display.Curve.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.Curve createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.display.Curve(parcel.createFloatArray(), parcel.createFloatArray());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.Curve[] newArray(int i) {
            return new android.hardware.display.Curve[i];
        }
    };
    private final float[] mX;
    private final float[] mY;

    public Curve(float[] fArr, float[] fArr2) {
        this.mX = fArr;
        this.mY = fArr2;
    }

    public float[] getX() {
        return this.mX;
    }

    public float[] getY() {
        return this.mY;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloatArray(this.mX);
        parcel.writeFloatArray(this.mY);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        int length = this.mX.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START).append(this.mX[i]).append(", ").append(this.mY[i]).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
