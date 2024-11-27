package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioDevice implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioDevice> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioDevice>() { // from class: android.media.audio.common.AudioDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioDevice createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioDevice audioDevice = new android.media.audio.common.AudioDevice();
            audioDevice.readFromParcel(parcel);
            return audioDevice;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioDevice[] newArray(int i) {
            return new android.media.audio.common.AudioDevice[i];
        }
    };
    public android.media.audio.common.AudioDeviceAddress address;
    public android.media.audio.common.AudioDeviceDescription type;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.type, i);
        parcel.writeTypedObject(this.address, i);
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
            this.type = (android.media.audio.common.AudioDeviceDescription) parcel.readTypedObject(android.media.audio.common.AudioDeviceDescription.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.address = (android.media.audio.common.AudioDeviceAddress) parcel.readTypedObject(android.media.audio.common.AudioDeviceAddress.CREATOR);
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
        stringJoiner.add("type: " + java.util.Objects.toString(this.type));
        stringJoiner.add("address: " + java.util.Objects.toString(this.address));
        return "AudioDevice" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioDevice)) {
            return false;
        }
        android.media.audio.common.AudioDevice audioDevice = (android.media.audio.common.AudioDevice) obj;
        if (java.util.Objects.deepEquals(this.type, audioDevice.type) && java.util.Objects.deepEquals(this.address, audioDevice.address)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.type, this.address).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.type) | 0 | describeContents(this.address);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
