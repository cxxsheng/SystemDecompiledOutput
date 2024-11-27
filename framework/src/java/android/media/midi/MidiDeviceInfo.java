package android.media.midi;

/* loaded from: classes2.dex */
public final class MidiDeviceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.midi.MidiDeviceInfo> CREATOR = new android.os.Parcelable.Creator<android.media.midi.MidiDeviceInfo>() { // from class: android.media.midi.MidiDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.midi.MidiDeviceInfo createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            java.lang.String[] createStringArray = parcel.createStringArray();
            java.lang.String[] createStringArray2 = parcel.createStringArray();
            boolean z = parcel.readInt() == 1;
            int readInt5 = parcel.readInt();
            parcel.readBundle();
            return new android.media.midi.MidiDeviceInfo(readInt, readInt2, readInt3, readInt4, createStringArray, createStringArray2, parcel.readBundle(), z, readInt5);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.midi.MidiDeviceInfo[] newArray(int i) {
            return new android.media.midi.MidiDeviceInfo[i];
        }
    };
    public static final java.lang.String PROPERTY_ALSA_CARD = "alsa_card";
    public static final java.lang.String PROPERTY_ALSA_DEVICE = "alsa_device";
    public static final java.lang.String PROPERTY_BLUETOOTH_DEVICE = "bluetooth_device";
    public static final java.lang.String PROPERTY_MANUFACTURER = "manufacturer";
    public static final java.lang.String PROPERTY_NAME = "name";
    public static final java.lang.String PROPERTY_PRODUCT = "product";
    public static final java.lang.String PROPERTY_SERIAL_NUMBER = "serial_number";
    public static final java.lang.String PROPERTY_SERVICE_INFO = "service_info";
    public static final java.lang.String PROPERTY_USB_DEVICE = "usb_device";
    public static final java.lang.String PROPERTY_VERSION = "version";
    public static final int PROTOCOL_UMP_MIDI_1_0_UP_TO_128_BITS = 3;
    public static final int PROTOCOL_UMP_MIDI_1_0_UP_TO_128_BITS_AND_JRTS = 4;
    public static final int PROTOCOL_UMP_MIDI_1_0_UP_TO_64_BITS = 1;
    public static final int PROTOCOL_UMP_MIDI_1_0_UP_TO_64_BITS_AND_JRTS = 2;
    public static final int PROTOCOL_UMP_MIDI_2_0 = 17;
    public static final int PROTOCOL_UMP_MIDI_2_0_AND_JRTS = 18;
    public static final int PROTOCOL_UMP_USE_MIDI_CI = 0;
    public static final int PROTOCOL_UNKNOWN = -1;
    private static final java.lang.String TAG = "MidiDeviceInfo";
    public static final int TYPE_BLUETOOTH = 3;
    public static final int TYPE_USB = 1;
    public static final int TYPE_VIRTUAL = 2;
    private final int mDefaultProtocol;
    private final int mId;
    private final int mInputPortCount;
    private final java.lang.String[] mInputPortNames;
    private final boolean mIsPrivate;
    private final int mOutputPortCount;
    private final java.lang.String[] mOutputPortNames;
    private final android.os.Bundle mProperties;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Protocol {
    }

    public static final class PortInfo {
        public static final int TYPE_INPUT = 1;
        public static final int TYPE_OUTPUT = 2;
        private final java.lang.String mName;
        private final int mPortNumber;
        private final int mPortType;

        PortInfo(int i, int i2, java.lang.String str) {
            this.mPortType = i;
            this.mPortNumber = i2;
            this.mName = str == null ? "" : str;
        }

        public int getType() {
            return this.mPortType;
        }

        public int getPortNumber() {
            return this.mPortNumber;
        }

        public java.lang.String getName() {
            return this.mName;
        }
    }

    public MidiDeviceInfo(int i, int i2, int i3, int i4, java.lang.String[] strArr, java.lang.String[] strArr2, android.os.Bundle bundle, boolean z, int i5) {
        if (i3 < 0 || i3 > 256) {
            throw new java.lang.IllegalArgumentException("numInputPorts out of range = " + i3);
        }
        if (i4 < 0 || i4 > 256) {
            throw new java.lang.IllegalArgumentException("numOutputPorts out of range = " + i4);
        }
        this.mType = i;
        this.mId = i2;
        this.mInputPortCount = i3;
        this.mOutputPortCount = i4;
        if (strArr == null) {
            this.mInputPortNames = new java.lang.String[i3];
        } else {
            this.mInputPortNames = strArr;
        }
        if (strArr2 == null) {
            this.mOutputPortNames = new java.lang.String[i4];
        } else {
            this.mOutputPortNames = strArr2;
        }
        this.mProperties = bundle;
        this.mIsPrivate = z;
        this.mDefaultProtocol = i5;
    }

    public int getType() {
        return this.mType;
    }

    public int getId() {
        return this.mId;
    }

    public int getInputPortCount() {
        return this.mInputPortCount;
    }

    public int getOutputPortCount() {
        return this.mOutputPortCount;
    }

    public android.media.midi.MidiDeviceInfo.PortInfo[] getPorts() {
        android.media.midi.MidiDeviceInfo.PortInfo[] portInfoArr = new android.media.midi.MidiDeviceInfo.PortInfo[this.mInputPortCount + this.mOutputPortCount];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.mInputPortCount) {
            portInfoArr[i3] = new android.media.midi.MidiDeviceInfo.PortInfo(1, i2, this.mInputPortNames[i2]);
            i2++;
            i3++;
        }
        while (i < this.mOutputPortCount) {
            portInfoArr[i3] = new android.media.midi.MidiDeviceInfo.PortInfo(2, i, this.mOutputPortNames[i]);
            i++;
            i3++;
        }
        return portInfoArr;
    }

    public android.os.Bundle getProperties() {
        return this.mProperties;
    }

    public boolean isPrivate() {
        return this.mIsPrivate;
    }

    public int getDefaultProtocol() {
        return this.mDefaultProtocol;
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.media.midi.MidiDeviceInfo) && ((android.media.midi.MidiDeviceInfo) obj).mId == this.mId;
    }

    public int hashCode() {
        return this.mId;
    }

    public java.lang.String toString() {
        this.mProperties.getString("name");
        return "MidiDeviceInfo[mType=" + this.mType + ",mInputPortCount=" + this.mInputPortCount + ",mOutputPortCount=" + this.mOutputPortCount + ",mProperties=" + this.mProperties + ",mIsPrivate=" + this.mIsPrivate + ",mDefaultProtocol=" + this.mDefaultProtocol;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private android.os.Bundle getBasicProperties(java.lang.String[] strArr) {
        android.os.Bundle bundle = new android.os.Bundle();
        for (java.lang.String str : strArr) {
            java.lang.Object obj = this.mProperties.get(str);
            if (obj != null) {
                if (obj instanceof java.lang.String) {
                    bundle.putString(str, (java.lang.String) obj);
                } else if (obj instanceof java.lang.Integer) {
                    bundle.putInt(str, ((java.lang.Integer) obj).intValue());
                } else {
                    android.util.Log.w(TAG, "Unsupported property type: " + obj.getClass().getName());
                }
            }
        }
        return bundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mInputPortCount);
        parcel.writeInt(this.mOutputPortCount);
        parcel.writeStringArray(this.mInputPortNames);
        parcel.writeStringArray(this.mOutputPortNames);
        parcel.writeInt(this.mIsPrivate ? 1 : 0);
        parcel.writeInt(this.mDefaultProtocol);
        parcel.writeBundle(getBasicProperties(new java.lang.String[]{"name", PROPERTY_MANUFACTURER, "product", "version", "serial_number", PROPERTY_ALSA_CARD, PROPERTY_ALSA_DEVICE}));
        parcel.writeBundle(this.mProperties);
    }
}
