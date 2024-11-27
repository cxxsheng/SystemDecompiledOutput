package android.hardware.devicestate;

/* loaded from: classes2.dex */
public final class DeviceStateInfo implements android.os.Parcelable {
    public static final int CHANGED_BASE_STATE = 2;
    public static final int CHANGED_CURRENT_STATE = 4;
    public static final int CHANGED_SUPPORTED_STATES = 1;
    public static final android.os.Parcelable.Creator<android.hardware.devicestate.DeviceStateInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.devicestate.DeviceStateInfo>() { // from class: android.hardware.devicestate.DeviceStateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.devicestate.DeviceStateInfo createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int[] iArr = new int[readInt];
            for (int i = 0; i < readInt; i++) {
                iArr[i] = parcel.readInt();
            }
            return new android.hardware.devicestate.DeviceStateInfo(iArr, parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.devicestate.DeviceStateInfo[] newArray(int i) {
            return new android.hardware.devicestate.DeviceStateInfo[i];
        }
    };
    public final int baseState;
    public final int currentState;
    public final int[] supportedStates;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ChangeFlags {
    }

    public DeviceStateInfo(int[] iArr, int i, int i2) {
        this.supportedStates = iArr;
        this.baseState = i;
        this.currentState = i2;
    }

    public DeviceStateInfo(android.hardware.devicestate.DeviceStateInfo deviceStateInfo) {
        this(java.util.Arrays.copyOf(deviceStateInfo.supportedStates, deviceStateInfo.supportedStates.length), deviceStateInfo.baseState, deviceStateInfo.currentState);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.hardware.devicestate.DeviceStateInfo deviceStateInfo = (android.hardware.devicestate.DeviceStateInfo) obj;
        if (this.baseState == deviceStateInfo.baseState && this.currentState == deviceStateInfo.currentState && java.util.Arrays.equals(this.supportedStates, deviceStateInfo.supportedStates)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (java.util.Objects.hash(java.lang.Integer.valueOf(this.baseState), java.lang.Integer.valueOf(this.currentState)) * 31) + java.util.Arrays.hashCode(this.supportedStates);
    }

    public int diff(android.hardware.devicestate.DeviceStateInfo deviceStateInfo) {
        int i;
        if (java.util.Arrays.equals(this.supportedStates, deviceStateInfo.supportedStates)) {
            i = 0;
        } else {
            i = 1;
        }
        if (this.baseState != deviceStateInfo.baseState) {
            i |= 2;
        }
        if (this.currentState != deviceStateInfo.currentState) {
            return i | 4;
        }
        return i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.supportedStates.length);
        for (int i2 = 0; i2 < this.supportedStates.length; i2++) {
            parcel.writeInt(this.supportedStates[i2]);
        }
        parcel.writeInt(this.baseState);
        parcel.writeInt(this.currentState);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
