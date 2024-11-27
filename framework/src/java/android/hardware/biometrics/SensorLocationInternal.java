package android.hardware.biometrics;

/* loaded from: classes.dex */
public class SensorLocationInternal implements android.os.Parcelable {
    public final java.lang.String displayId;
    public final int sensorLocationX;
    public final int sensorLocationY;
    public final int sensorRadius;
    public static final android.hardware.biometrics.SensorLocationInternal DEFAULT = new android.hardware.biometrics.SensorLocationInternal("", 0, 0, 0);
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.SensorLocationInternal> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.SensorLocationInternal>() { // from class: android.hardware.biometrics.SensorLocationInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.SensorLocationInternal createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.biometrics.SensorLocationInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.SensorLocationInternal[] newArray(int i) {
            return new android.hardware.biometrics.SensorLocationInternal[i];
        }
    };

    public SensorLocationInternal(java.lang.String str, int i, int i2, int i3) {
        this.displayId = str == null ? "" : str;
        this.sensorLocationX = i;
        this.sensorLocationY = i2;
        this.sensorRadius = i3;
    }

    protected SensorLocationInternal(android.os.Parcel parcel) {
        this.displayId = parcel.readString16NoHelper();
        this.sensorLocationX = parcel.readInt();
        this.sensorLocationY = parcel.readInt();
        this.sensorRadius = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.displayId);
        parcel.writeInt(this.sensorLocationX);
        parcel.writeInt(this.sensorLocationY);
        parcel.writeInt(this.sensorRadius);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "[id: " + this.displayId + ", x: " + this.sensorLocationX + ", y: " + this.sensorLocationY + ", r: " + this.sensorRadius + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public android.graphics.Rect getRect() {
        return new android.graphics.Rect(this.sensorLocationX - this.sensorRadius, this.sensorLocationY - this.sensorRadius, this.sensorLocationX + this.sensorRadius, this.sensorLocationY + this.sensorRadius);
    }
}
