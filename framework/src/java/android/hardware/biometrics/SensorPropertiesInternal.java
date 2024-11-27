package android.hardware.biometrics;

/* loaded from: classes.dex */
public class SensorPropertiesInternal implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.SensorPropertiesInternal> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.SensorPropertiesInternal>() { // from class: android.hardware.biometrics.SensorPropertiesInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.SensorPropertiesInternal createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.biometrics.SensorPropertiesInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.SensorPropertiesInternal[] newArray(int i) {
            return new android.hardware.biometrics.SensorPropertiesInternal[i];
        }
    };
    public final java.util.List<android.hardware.biometrics.ComponentInfoInternal> componentInfo;
    public final int maxEnrollmentsPerUser;
    public final boolean resetLockoutRequiresChallenge;
    public final boolean resetLockoutRequiresHardwareAuthToken;
    public final int sensorId;
    public final int sensorStrength;

    public static android.hardware.biometrics.SensorPropertiesInternal from(android.hardware.biometrics.SensorPropertiesInternal sensorPropertiesInternal) {
        return new android.hardware.biometrics.SensorPropertiesInternal(sensorPropertiesInternal.sensorId, sensorPropertiesInternal.sensorStrength, sensorPropertiesInternal.maxEnrollmentsPerUser, sensorPropertiesInternal.componentInfo, sensorPropertiesInternal.resetLockoutRequiresHardwareAuthToken, sensorPropertiesInternal.resetLockoutRequiresChallenge);
    }

    public SensorPropertiesInternal(int i, int i2, int i3, java.util.List<android.hardware.biometrics.ComponentInfoInternal> list, boolean z, boolean z2) {
        this.sensorId = i;
        this.sensorStrength = i2;
        this.maxEnrollmentsPerUser = i3;
        this.componentInfo = list;
        this.resetLockoutRequiresHardwareAuthToken = z;
        this.resetLockoutRequiresChallenge = z2;
    }

    protected SensorPropertiesInternal(android.os.Parcel parcel) {
        this.sensorId = parcel.readInt();
        this.sensorStrength = parcel.readInt();
        this.maxEnrollmentsPerUser = parcel.readInt();
        this.componentInfo = new java.util.ArrayList();
        parcel.readList(this.componentInfo, android.hardware.biometrics.ComponentInfoInternal.class.getClassLoader(), android.hardware.biometrics.ComponentInfoInternal.class);
        this.resetLockoutRequiresHardwareAuthToken = parcel.readBoolean();
        this.resetLockoutRequiresChallenge = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.sensorId);
        parcel.writeInt(this.sensorStrength);
        parcel.writeInt(this.maxEnrollmentsPerUser);
        parcel.writeList(this.componentInfo);
        parcel.writeBoolean(this.resetLockoutRequiresHardwareAuthToken);
        parcel.writeBoolean(this.resetLockoutRequiresChallenge);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("[ ");
        java.util.Iterator<android.hardware.biometrics.ComponentInfoInternal> it = this.componentInfo.iterator();
        while (it.hasNext()) {
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START).append(it.next().toString());
            sb.append("] ");
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return "ID: " + this.sensorId + ", Strength: " + this.sensorStrength + ", MaxEnrollmentsPerUser: " + this.maxEnrollmentsPerUser + ", ComponentInfo: " + sb.toString();
    }
}
