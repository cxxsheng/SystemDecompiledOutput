package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioPortDeviceExt implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioPortDeviceExt> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioPortDeviceExt>() { // from class: android.media.audio.common.AudioPortDeviceExt.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPortDeviceExt createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioPortDeviceExt audioPortDeviceExt = new android.media.audio.common.AudioPortDeviceExt();
            audioPortDeviceExt.readFromParcel(parcel);
            return audioPortDeviceExt;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPortDeviceExt[] newArray(int i) {
            return new android.media.audio.common.AudioPortDeviceExt[i];
        }
    };
    public static final int FLAG_INDEX_DEFAULT_DEVICE = 0;
    public android.media.audio.common.AudioDevice device;
    public android.media.audio.common.AudioFormatDescription[] encodedFormats;
    public int flags = 0;
    public int encapsulationModes = 0;
    public int encapsulationMetadataTypes = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.device, i);
        parcel.writeInt(this.flags);
        parcel.writeTypedArray(this.encodedFormats, i);
        parcel.writeInt(this.encapsulationModes);
        parcel.writeInt(this.encapsulationMetadataTypes);
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
            this.device = (android.media.audio.common.AudioDevice) parcel.readTypedObject(android.media.audio.common.AudioDevice.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.flags = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.encodedFormats = (android.media.audio.common.AudioFormatDescription[]) parcel.createTypedArray(android.media.audio.common.AudioFormatDescription.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.encapsulationModes = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.encapsulationMetadataTypes = parcel.readInt();
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
        stringJoiner.add("device: " + java.util.Objects.toString(this.device));
        stringJoiner.add("flags: " + this.flags);
        stringJoiner.add("encodedFormats: " + java.util.Arrays.toString(this.encodedFormats));
        stringJoiner.add("encapsulationModes: " + this.encapsulationModes);
        stringJoiner.add("encapsulationMetadataTypes: " + this.encapsulationMetadataTypes);
        return "AudioPortDeviceExt" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioPortDeviceExt)) {
            return false;
        }
        android.media.audio.common.AudioPortDeviceExt audioPortDeviceExt = (android.media.audio.common.AudioPortDeviceExt) obj;
        if (java.util.Objects.deepEquals(this.device, audioPortDeviceExt.device) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.flags), java.lang.Integer.valueOf(audioPortDeviceExt.flags)) && java.util.Objects.deepEquals(this.encodedFormats, audioPortDeviceExt.encodedFormats) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.encapsulationModes), java.lang.Integer.valueOf(audioPortDeviceExt.encapsulationModes)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.encapsulationMetadataTypes), java.lang.Integer.valueOf(audioPortDeviceExt.encapsulationMetadataTypes))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.device, java.lang.Integer.valueOf(this.flags), this.encodedFormats, java.lang.Integer.valueOf(this.encapsulationModes), java.lang.Integer.valueOf(this.encapsulationMetadataTypes)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.device) | 0 | describeContents(this.encodedFormats);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
