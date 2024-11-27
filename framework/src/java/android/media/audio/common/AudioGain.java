package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioGain implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioGain> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioGain>() { // from class: android.media.audio.common.AudioGain.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioGain createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioGain audioGain = new android.media.audio.common.AudioGain();
            audioGain.readFromParcel(parcel);
            return audioGain;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioGain[] newArray(int i) {
            return new android.media.audio.common.AudioGain[i];
        }
    };
    public android.media.audio.common.AudioChannelLayout channelMask;
    public int mode = 0;
    public int minValue = 0;
    public int maxValue = 0;
    public int defaultValue = 0;
    public int stepValue = 0;
    public int minRampMs = 0;
    public int maxRampMs = 0;
    public boolean useForVolume = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.mode);
        parcel.writeTypedObject(this.channelMask, i);
        parcel.writeInt(this.minValue);
        parcel.writeInt(this.maxValue);
        parcel.writeInt(this.defaultValue);
        parcel.writeInt(this.stepValue);
        parcel.writeInt(this.minRampMs);
        parcel.writeInt(this.maxRampMs);
        parcel.writeBoolean(this.useForVolume);
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
            this.mode = parcel.readInt();
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
            this.minValue = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxValue = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.defaultValue = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.stepValue = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.minRampMs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxRampMs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.useForVolume = parcel.readBoolean();
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
        stringJoiner.add("mode: " + this.mode);
        stringJoiner.add("channelMask: " + java.util.Objects.toString(this.channelMask));
        stringJoiner.add("minValue: " + this.minValue);
        stringJoiner.add("maxValue: " + this.maxValue);
        stringJoiner.add("defaultValue: " + this.defaultValue);
        stringJoiner.add("stepValue: " + this.stepValue);
        stringJoiner.add("minRampMs: " + this.minRampMs);
        stringJoiner.add("maxRampMs: " + this.maxRampMs);
        stringJoiner.add("useForVolume: " + this.useForVolume);
        return "AudioGain" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioGain)) {
            return false;
        }
        android.media.audio.common.AudioGain audioGain = (android.media.audio.common.AudioGain) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.mode), java.lang.Integer.valueOf(audioGain.mode)) && java.util.Objects.deepEquals(this.channelMask, audioGain.channelMask) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.minValue), java.lang.Integer.valueOf(audioGain.minValue)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.maxValue), java.lang.Integer.valueOf(audioGain.maxValue)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.defaultValue), java.lang.Integer.valueOf(audioGain.defaultValue)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.stepValue), java.lang.Integer.valueOf(audioGain.stepValue)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.minRampMs), java.lang.Integer.valueOf(audioGain.minRampMs)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.maxRampMs), java.lang.Integer.valueOf(audioGain.maxRampMs)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.useForVolume), java.lang.Boolean.valueOf(audioGain.useForVolume))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.mode), this.channelMask, java.lang.Integer.valueOf(this.minValue), java.lang.Integer.valueOf(this.maxValue), java.lang.Integer.valueOf(this.defaultValue), java.lang.Integer.valueOf(this.stepValue), java.lang.Integer.valueOf(this.minRampMs), java.lang.Integer.valueOf(this.maxRampMs), java.lang.Boolean.valueOf(this.useForVolume)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.channelMask) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
