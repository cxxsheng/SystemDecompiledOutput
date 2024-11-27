package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioConfig> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioConfig>() { // from class: android.media.audio.common.AudioConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioConfig createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioConfig audioConfig = new android.media.audio.common.AudioConfig();
            audioConfig.readFromParcel(parcel);
            return audioConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioConfig[] newArray(int i) {
            return new android.media.audio.common.AudioConfig[i];
        }
    };
    public android.media.audio.common.AudioConfigBase base;
    public long frameCount = 0;
    public android.media.audio.common.AudioOffloadInfo offloadInfo;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.base, i);
        parcel.writeTypedObject(this.offloadInfo, i);
        parcel.writeLong(this.frameCount);
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
            this.base = (android.media.audio.common.AudioConfigBase) parcel.readTypedObject(android.media.audio.common.AudioConfigBase.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.offloadInfo = (android.media.audio.common.AudioOffloadInfo) parcel.readTypedObject(android.media.audio.common.AudioOffloadInfo.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.frameCount = parcel.readLong();
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
        stringJoiner.add("base: " + java.util.Objects.toString(this.base));
        stringJoiner.add("offloadInfo: " + java.util.Objects.toString(this.offloadInfo));
        stringJoiner.add("frameCount: " + this.frameCount);
        return "AudioConfig" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioConfig)) {
            return false;
        }
        android.media.audio.common.AudioConfig audioConfig = (android.media.audio.common.AudioConfig) obj;
        if (java.util.Objects.deepEquals(this.base, audioConfig.base) && java.util.Objects.deepEquals(this.offloadInfo, audioConfig.offloadInfo) && java.util.Objects.deepEquals(java.lang.Long.valueOf(this.frameCount), java.lang.Long.valueOf(audioConfig.frameCount))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.base, this.offloadInfo, java.lang.Long.valueOf(this.frameCount)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.base) | 0 | describeContents(this.offloadInfo);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
