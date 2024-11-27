package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioProfile implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioProfile> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioProfile>() { // from class: android.media.audio.common.AudioProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioProfile createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioProfile audioProfile = new android.media.audio.common.AudioProfile();
            audioProfile.readFromParcel(parcel);
            return audioProfile;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioProfile[] newArray(int i) {
            return new android.media.audio.common.AudioProfile[i];
        }
    };
    public android.media.audio.common.AudioChannelLayout[] channelMasks;
    public int encapsulationType = 0;
    public android.media.audio.common.AudioFormatDescription format;
    public java.lang.String name;
    public int[] sampleRates;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeTypedObject(this.format, i);
        parcel.writeTypedArray(this.channelMasks, i);
        parcel.writeIntArray(this.sampleRates);
        parcel.writeInt(this.encapsulationType);
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
            this.name = parcel.readString();
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
            this.channelMasks = (android.media.audio.common.AudioChannelLayout[]) parcel.createTypedArray(android.media.audio.common.AudioChannelLayout.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sampleRates = parcel.createIntArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.encapsulationType = parcel.readInt();
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
        stringJoiner.add("name: " + java.util.Objects.toString(this.name));
        stringJoiner.add("format: " + java.util.Objects.toString(this.format));
        stringJoiner.add("channelMasks: " + java.util.Arrays.toString(this.channelMasks));
        stringJoiner.add("sampleRates: " + java.util.Arrays.toString(this.sampleRates));
        stringJoiner.add("encapsulationType: " + this.encapsulationType);
        return "AudioProfile" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioProfile)) {
            return false;
        }
        android.media.audio.common.AudioProfile audioProfile = (android.media.audio.common.AudioProfile) obj;
        if (java.util.Objects.deepEquals(this.name, audioProfile.name) && java.util.Objects.deepEquals(this.format, audioProfile.format) && java.util.Objects.deepEquals(this.channelMasks, audioProfile.channelMasks) && java.util.Objects.deepEquals(this.sampleRates, audioProfile.sampleRates) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.encapsulationType), java.lang.Integer.valueOf(audioProfile.encapsulationType))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.name, this.format, this.channelMasks, this.sampleRates, java.lang.Integer.valueOf(this.encapsulationType)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.format) | 0 | describeContents(this.channelMasks);
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
