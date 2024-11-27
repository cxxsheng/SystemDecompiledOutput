package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioPort implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioPort> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioPort>() { // from class: android.media.audio.common.AudioPort.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPort createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioPort audioPort = new android.media.audio.common.AudioPort();
            audioPort.readFromParcel(parcel);
            return audioPort;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPort[] newArray(int i) {
            return new android.media.audio.common.AudioPort[i];
        }
    };
    public android.media.audio.common.AudioPortExt ext;
    public android.media.audio.common.ExtraAudioDescriptor[] extraAudioDescriptors;
    public android.media.audio.common.AudioIoFlags flags;
    public android.media.audio.common.AudioGain[] gains;
    public int id = 0;
    public java.lang.String name;
    public android.media.audio.common.AudioProfile[] profiles;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeTypedArray(this.profiles, i);
        parcel.writeTypedObject(this.flags, i);
        parcel.writeTypedArray(this.extraAudioDescriptors, i);
        parcel.writeTypedArray(this.gains, i);
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
            this.name = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.profiles = (android.media.audio.common.AudioProfile[]) parcel.createTypedArray(android.media.audio.common.AudioProfile.CREATOR);
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
                return;
            }
            this.extraAudioDescriptors = (android.media.audio.common.ExtraAudioDescriptor[]) parcel.createTypedArray(android.media.audio.common.ExtraAudioDescriptor.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.gains = (android.media.audio.common.AudioGain[]) parcel.createTypedArray(android.media.audio.common.AudioGain.CREATOR);
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
        stringJoiner.add("name: " + java.util.Objects.toString(this.name));
        stringJoiner.add("profiles: " + java.util.Arrays.toString(this.profiles));
        stringJoiner.add("flags: " + java.util.Objects.toString(this.flags));
        stringJoiner.add("extraAudioDescriptors: " + java.util.Arrays.toString(this.extraAudioDescriptors));
        stringJoiner.add("gains: " + java.util.Arrays.toString(this.gains));
        stringJoiner.add("ext: " + java.util.Objects.toString(this.ext));
        return "AudioPort" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioPort)) {
            return false;
        }
        android.media.audio.common.AudioPort audioPort = (android.media.audio.common.AudioPort) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.id), java.lang.Integer.valueOf(audioPort.id)) && java.util.Objects.deepEquals(this.name, audioPort.name) && java.util.Objects.deepEquals(this.profiles, audioPort.profiles) && java.util.Objects.deepEquals(this.flags, audioPort.flags) && java.util.Objects.deepEquals(this.extraAudioDescriptors, audioPort.extraAudioDescriptors) && java.util.Objects.deepEquals(this.gains, audioPort.gains) && java.util.Objects.deepEquals(this.ext, audioPort.ext)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.id), this.name, this.profiles, this.flags, this.extraAudioDescriptors, this.gains, this.ext).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.profiles) | 0 | describeContents(this.flags) | describeContents(this.extraAudioDescriptors) | describeContents(this.gains) | describeContents(this.ext);
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
