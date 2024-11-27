package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioConfigBase implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioConfigBase> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioConfigBase>() { // from class: android.media.audio.common.AudioConfigBase.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioConfigBase createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioConfigBase audioConfigBase = new android.media.audio.common.AudioConfigBase();
            audioConfigBase.readFromParcel(parcel);
            return audioConfigBase;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioConfigBase[] newArray(int i) {
            return new android.media.audio.common.AudioConfigBase[i];
        }
    };
    public android.media.audio.common.AudioChannelLayout channelMask;
    public android.media.audio.common.AudioFormatDescription format;
    public int sampleRate = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sampleRate);
        parcel.writeTypedObject(this.channelMask, i);
        parcel.writeTypedObject(this.format, i);
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
            this.sampleRate = parcel.readInt();
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
            } else {
                this.format = (android.media.audio.common.AudioFormatDescription) parcel.readTypedObject(android.media.audio.common.AudioFormatDescription.CREATOR);
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
        stringJoiner.add("sampleRate: " + this.sampleRate);
        stringJoiner.add("channelMask: " + java.util.Objects.toString(this.channelMask));
        stringJoiner.add("format: " + java.util.Objects.toString(this.format));
        return "AudioConfigBase" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioConfigBase)) {
            return false;
        }
        android.media.audio.common.AudioConfigBase audioConfigBase = (android.media.audio.common.AudioConfigBase) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.sampleRate), java.lang.Integer.valueOf(audioConfigBase.sampleRate)) && java.util.Objects.deepEquals(this.channelMask, audioConfigBase.channelMask) && java.util.Objects.deepEquals(this.format, audioConfigBase.format)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.sampleRate), this.channelMask, this.format).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.channelMask) | 0 | describeContents(this.format);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
