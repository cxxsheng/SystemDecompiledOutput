package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioPortConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioPortConfig> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioPortConfig>() { // from class: android.media.audio.common.AudioPortConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPortConfig createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioPortConfig audioPortConfig = new android.media.audio.common.AudioPortConfig();
            audioPortConfig.readFromParcel(parcel);
            return audioPortConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPortConfig[] newArray(int i) {
            return new android.media.audio.common.AudioPortConfig[i];
        }
    };
    public android.media.audio.common.AudioChannelLayout channelMask;
    public android.media.audio.common.AudioPortExt ext;
    public android.media.audio.common.AudioIoFlags flags;
    public android.media.audio.common.AudioFormatDescription format;
    public android.media.audio.common.AudioGainConfig gain;
    public int id = 0;
    public int portId = 0;
    public android.media.audio.common.Int sampleRate;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeInt(this.portId);
        parcel.writeTypedObject(this.sampleRate, i);
        parcel.writeTypedObject(this.channelMask, i);
        parcel.writeTypedObject(this.format, i);
        parcel.writeTypedObject(this.gain, i);
        parcel.writeTypedObject(this.flags, i);
        parcel.writeTypedObject(this.ext, i);
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
            this.id = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.portId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sampleRate = (android.media.audio.common.Int) parcel.readTypedObject(android.media.audio.common.Int.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.channelMask = (android.media.audio.common.AudioChannelLayout) parcel.readTypedObject(android.media.audio.common.AudioChannelLayout.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.format = (android.media.audio.common.AudioFormatDescription) parcel.readTypedObject(android.media.audio.common.AudioFormatDescription.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.gain = (android.media.audio.common.AudioGainConfig) parcel.readTypedObject(android.media.audio.common.AudioGainConfig.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.flags = (android.media.audio.common.AudioIoFlags) parcel.readTypedObject(android.media.audio.common.AudioIoFlags.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.ext = (android.media.audio.common.AudioPortExt) parcel.readTypedObject(android.media.audio.common.AudioPortExt.CREATOR);
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
        stringJoiner.add("id: " + this.id);
        stringJoiner.add("portId: " + this.portId);
        stringJoiner.add("sampleRate: " + java.util.Objects.toString(this.sampleRate));
        stringJoiner.add("channelMask: " + java.util.Objects.toString(this.channelMask));
        stringJoiner.add("format: " + java.util.Objects.toString(this.format));
        stringJoiner.add("gain: " + java.util.Objects.toString(this.gain));
        stringJoiner.add("flags: " + java.util.Objects.toString(this.flags));
        stringJoiner.add("ext: " + java.util.Objects.toString(this.ext));
        return "AudioPortConfig" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioPortConfig)) {
            return false;
        }
        android.media.audio.common.AudioPortConfig audioPortConfig = (android.media.audio.common.AudioPortConfig) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.id), java.lang.Integer.valueOf(audioPortConfig.id)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.portId), java.lang.Integer.valueOf(audioPortConfig.portId)) && java.util.Objects.deepEquals(this.sampleRate, audioPortConfig.sampleRate) && java.util.Objects.deepEquals(this.channelMask, audioPortConfig.channelMask) && java.util.Objects.deepEquals(this.format, audioPortConfig.format) && java.util.Objects.deepEquals(this.gain, audioPortConfig.gain) && java.util.Objects.deepEquals(this.flags, audioPortConfig.flags) && java.util.Objects.deepEquals(this.ext, audioPortConfig.ext)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.id), java.lang.Integer.valueOf(this.portId), this.sampleRate, this.channelMask, this.format, this.gain, this.flags, this.ext).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.sampleRate) | 0 | describeContents(this.channelMask) | describeContents(this.format) | describeContents(this.gain) | describeContents(this.flags) | describeContents(this.ext);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
