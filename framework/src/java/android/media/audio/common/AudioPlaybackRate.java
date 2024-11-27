package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioPlaybackRate implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioPlaybackRate> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioPlaybackRate>() { // from class: android.media.audio.common.AudioPlaybackRate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPlaybackRate createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioPlaybackRate audioPlaybackRate = new android.media.audio.common.AudioPlaybackRate();
            audioPlaybackRate.readFromParcel(parcel);
            return audioPlaybackRate;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPlaybackRate[] newArray(int i) {
            return new android.media.audio.common.AudioPlaybackRate[i];
        }
    };
    public float speed = 0.0f;
    public float pitch = 0.0f;
    public int timestretchMode = 0;
    public int fallbackMode = 0;

    public @interface TimestretchFallbackMode {
        public static final int FAIL = 2;
        public static final int MUTE = 1;
        public static final int SYS_RESERVED_CUT_REPEAT = -1;
        public static final int SYS_RESERVED_DEFAULT = 0;
    }

    public @interface TimestretchMode {
        public static final int DEFAULT = 0;
        public static final int VOICE = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeFloat(this.speed);
        parcel.writeFloat(this.pitch);
        parcel.writeInt(this.timestretchMode);
        parcel.writeInt(this.fallbackMode);
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
            this.speed = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.pitch = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timestretchMode = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.fallbackMode = parcel.readInt();
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
        stringJoiner.add("speed: " + this.speed);
        stringJoiner.add("pitch: " + this.pitch);
        stringJoiner.add("timestretchMode: " + this.timestretchMode);
        stringJoiner.add("fallbackMode: " + this.fallbackMode);
        return "AudioPlaybackRate" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioPlaybackRate)) {
            return false;
        }
        android.media.audio.common.AudioPlaybackRate audioPlaybackRate = (android.media.audio.common.AudioPlaybackRate) obj;
        if (java.util.Objects.deepEquals(java.lang.Float.valueOf(this.speed), java.lang.Float.valueOf(audioPlaybackRate.speed)) && java.util.Objects.deepEquals(java.lang.Float.valueOf(this.pitch), java.lang.Float.valueOf(audioPlaybackRate.pitch)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.timestretchMode), java.lang.Integer.valueOf(audioPlaybackRate.timestretchMode)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.fallbackMode), java.lang.Integer.valueOf(audioPlaybackRate.fallbackMode))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Float.valueOf(this.speed), java.lang.Float.valueOf(this.pitch), java.lang.Integer.valueOf(this.timestretchMode), java.lang.Integer.valueOf(this.fallbackMode)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
