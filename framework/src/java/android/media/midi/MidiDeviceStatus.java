package android.media.midi;

/* loaded from: classes2.dex */
public final class MidiDeviceStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.midi.MidiDeviceStatus> CREATOR = new android.os.Parcelable.Creator<android.media.midi.MidiDeviceStatus>() { // from class: android.media.midi.MidiDeviceStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.midi.MidiDeviceStatus createFromParcel(android.os.Parcel parcel) {
            return new android.media.midi.MidiDeviceStatus((android.media.midi.MidiDeviceInfo) parcel.readParcelable(android.media.midi.MidiDeviceInfo.class.getClassLoader(), android.media.midi.MidiDeviceInfo.class), parcel.createBooleanArray(), parcel.createIntArray());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.midi.MidiDeviceStatus[] newArray(int i) {
            return new android.media.midi.MidiDeviceStatus[i];
        }
    };
    private static final java.lang.String TAG = "MidiDeviceStatus";
    private final android.media.midi.MidiDeviceInfo mDeviceInfo;
    private final boolean[] mInputPortOpen;
    private final int[] mOutputPortOpenCount;

    public MidiDeviceStatus(android.media.midi.MidiDeviceInfo midiDeviceInfo, boolean[] zArr, int[] iArr) {
        this.mDeviceInfo = midiDeviceInfo;
        this.mInputPortOpen = new boolean[zArr.length];
        java.lang.System.arraycopy(zArr, 0, this.mInputPortOpen, 0, zArr.length);
        this.mOutputPortOpenCount = new int[iArr.length];
        java.lang.System.arraycopy(iArr, 0, this.mOutputPortOpenCount, 0, iArr.length);
    }

    public MidiDeviceStatus(android.media.midi.MidiDeviceInfo midiDeviceInfo) {
        this.mDeviceInfo = midiDeviceInfo;
        this.mInputPortOpen = new boolean[midiDeviceInfo.getInputPortCount()];
        this.mOutputPortOpenCount = new int[midiDeviceInfo.getOutputPortCount()];
    }

    public android.media.midi.MidiDeviceInfo getDeviceInfo() {
        return this.mDeviceInfo;
    }

    public boolean isInputPortOpen(int i) {
        return this.mInputPortOpen[i];
    }

    public int getOutputPortOpenCount(int i) {
        return this.mOutputPortOpenCount[i];
    }

    public java.lang.String toString() {
        int inputPortCount = this.mDeviceInfo.getInputPortCount();
        int outputPortCount = this.mDeviceInfo.getOutputPortCount();
        java.lang.StringBuilder sb = new java.lang.StringBuilder("mInputPortOpen=[");
        for (int i = 0; i < inputPortCount; i++) {
            sb.append(this.mInputPortOpen[i]);
            if (i < inputPortCount - 1) {
                sb.append(",");
            }
        }
        sb.append("] mOutputPortOpenCount=[");
        for (int i2 = 0; i2 < outputPortCount; i2++) {
            sb.append(this.mOutputPortOpenCount[i2]);
            if (i2 < outputPortCount - 1) {
                sb.append(",");
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mDeviceInfo, i);
        parcel.writeBooleanArray(this.mInputPortOpen);
        parcel.writeIntArray(this.mOutputPortOpenCount);
    }
}
