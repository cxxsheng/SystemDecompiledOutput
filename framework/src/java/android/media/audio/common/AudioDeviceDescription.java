package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioDeviceDescription implements android.os.Parcelable {
    public static final java.lang.String CONNECTION_ANALOG = "analog";
    public static final java.lang.String CONNECTION_BT_A2DP = "bt-a2dp";
    public static final java.lang.String CONNECTION_BT_LE = "bt-le";
    public static final java.lang.String CONNECTION_BT_SCO = "bt-sco";

    @java.lang.Deprecated
    public static final java.lang.String CONNECTION_BUS = "bus";
    public static final java.lang.String CONNECTION_HDMI = "hdmi";
    public static final java.lang.String CONNECTION_HDMI_ARC = "hdmi-arc";
    public static final java.lang.String CONNECTION_HDMI_EARC = "hdmi-earc";
    public static final java.lang.String CONNECTION_IP_V4 = "ip-v4";
    public static final java.lang.String CONNECTION_SPDIF = "spdif";
    public static final java.lang.String CONNECTION_USB = "usb";
    public static final java.lang.String CONNECTION_VIRTUAL = "virtual";
    public static final java.lang.String CONNECTION_WIRELESS = "wireless";
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioDeviceDescription> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioDeviceDescription>() { // from class: android.media.audio.common.AudioDeviceDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioDeviceDescription createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioDeviceDescription audioDeviceDescription = new android.media.audio.common.AudioDeviceDescription();
            audioDeviceDescription.readFromParcel(parcel);
            return audioDeviceDescription;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioDeviceDescription[] newArray(int i) {
            return new android.media.audio.common.AudioDeviceDescription[i];
        }
    };
    public java.lang.String connection;
    public int type = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeString(this.connection);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.type = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.connection = parcel.readString();
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("connection: " + java.util.Objects.toString(this.connection));
        return "AudioDeviceDescription" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioDeviceDescription)) {
            return false;
        }
        android.media.audio.common.AudioDeviceDescription audioDeviceDescription = (android.media.audio.common.AudioDeviceDescription) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.type), java.lang.Integer.valueOf(audioDeviceDescription.type)) && java.util.Objects.deepEquals(this.connection, audioDeviceDescription.connection)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.type), this.connection).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
