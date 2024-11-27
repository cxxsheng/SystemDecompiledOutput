package android.media;

/* loaded from: classes2.dex */
public class AudioDevicePort extends android.media.AudioPort {
    private final java.lang.String mAddress;
    private final int[] mEncapsulationMetadataTypes;
    private final int[] mEncapsulationModes;
    private final int mType;

    public static android.media.AudioDevicePort createForTesting(int i, java.lang.String str, java.lang.String str2) {
        return new android.media.AudioDevicePort(new android.media.AudioHandle(0), str, null, null, null, null, null, i, str2, null, null);
    }

    AudioDevicePort(android.media.AudioHandle audioHandle, java.lang.String str, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, android.media.AudioGain[] audioGainArr, int i, java.lang.String str2, int[] iArr5, int[] iArr6) {
        super(audioHandle, android.media.AudioManager.isInputDevice(i) ? 1 : 2, str, iArr, iArr2, iArr3, iArr4, audioGainArr);
        this.mType = i;
        this.mAddress = str2;
        this.mEncapsulationModes = iArr5;
        this.mEncapsulationMetadataTypes = iArr6;
    }

    AudioDevicePort(android.media.AudioHandle audioHandle, java.lang.String str, java.util.List<android.media.AudioProfile> list, android.media.AudioGain[] audioGainArr, int i, java.lang.String str2, int[] iArr, int[] iArr2, java.util.List<android.media.AudioDescriptor> list2) {
        super(audioHandle, android.media.AudioManager.isInputDevice(i) ? 1 : 2, str, list, audioGainArr, list2);
        this.mType = i;
        this.mAddress = str2;
        this.mEncapsulationModes = iArr;
        this.mEncapsulationMetadataTypes = iArr2;
    }

    public int type() {
        return this.mType;
    }

    public java.lang.String address() {
        return this.mAddress;
    }

    public int[] encapsulationModes() {
        if (this.mEncapsulationModes == null) {
            return new int[0];
        }
        return java.util.Arrays.stream(this.mEncapsulationModes).boxed().filter(new java.util.function.Predicate() { // from class: android.media.AudioDevicePort$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.media.AudioDevicePort.lambda$encapsulationModes$0((java.lang.Integer) obj);
            }
        }).mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
    }

    static /* synthetic */ boolean lambda$encapsulationModes$0(java.lang.Integer num) {
        return num.intValue() != 2;
    }

    public int[] encapsulationMetadataTypes() {
        if (this.mEncapsulationMetadataTypes == null) {
            return new int[0];
        }
        int[] iArr = new int[this.mEncapsulationMetadataTypes.length];
        java.lang.System.arraycopy(this.mEncapsulationMetadataTypes, 0, iArr, 0, this.mEncapsulationMetadataTypes.length);
        return iArr;
    }

    @Override // android.media.AudioPort
    public android.media.AudioDevicePortConfig buildConfig(int i, int i2, int i3, android.media.AudioGainConfig audioGainConfig) {
        return new android.media.AudioDevicePortConfig(this, i, i2, i3, audioGainConfig);
    }

    @Override // android.media.AudioPort
    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.media.AudioDevicePort)) {
            return false;
        }
        android.media.AudioDevicePort audioDevicePort = (android.media.AudioDevicePort) obj;
        if (this.mType != audioDevicePort.type()) {
            return false;
        }
        if ((this.mAddress == null && audioDevicePort.address() != null) || !this.mAddress.equals(audioDevicePort.address())) {
            return false;
        }
        return super.equals(obj);
    }

    @Override // android.media.AudioPort
    public java.lang.String toString() {
        java.lang.String outputDeviceName;
        if (this.mRole == 1) {
            outputDeviceName = android.media.AudioSystem.getInputDeviceName(this.mType);
        } else {
            outputDeviceName = android.media.AudioSystem.getOutputDeviceName(this.mType);
        }
        return "{" + super.toString() + ", mType: " + outputDeviceName + ", mAddress: " + android.media.Utils.anonymizeBluetoothAddress(this.mType, this.mAddress) + "}";
    }
}
