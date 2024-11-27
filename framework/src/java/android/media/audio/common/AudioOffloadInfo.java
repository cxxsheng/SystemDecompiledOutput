package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioOffloadInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioOffloadInfo> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioOffloadInfo>() { // from class: android.media.audio.common.AudioOffloadInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioOffloadInfo createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioOffloadInfo audioOffloadInfo = new android.media.audio.common.AudioOffloadInfo();
            audioOffloadInfo.readFromParcel(parcel);
            return audioOffloadInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioOffloadInfo[] newArray(int i) {
            return new android.media.audio.common.AudioOffloadInfo[i];
        }
    };
    public android.media.audio.common.AudioConfigBase base;
    public int streamType = -2;
    public int bitRatePerSecond = 0;
    public long durationUs = 0;
    public boolean hasVideo = false;
    public boolean isStreaming = false;
    public int bitWidth = 16;
    public int offloadBufferSize = 0;
    public int usage = -1;
    public byte encapsulationMode = -1;
    public int contentId = 0;
    public int syncId = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.base, i);
        parcel.writeInt(this.streamType);
        parcel.writeInt(this.bitRatePerSecond);
        parcel.writeLong(this.durationUs);
        parcel.writeBoolean(this.hasVideo);
        parcel.writeBoolean(this.isStreaming);
        parcel.writeInt(this.bitWidth);
        parcel.writeInt(this.offloadBufferSize);
        parcel.writeInt(this.usage);
        parcel.writeByte(this.encapsulationMode);
        parcel.writeInt(this.contentId);
        parcel.writeInt(this.syncId);
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
            this.streamType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.bitRatePerSecond = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.durationUs = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.hasVideo = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.isStreaming = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.bitWidth = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.offloadBufferSize = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.usage = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.encapsulationMode = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.contentId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.syncId = parcel.readInt();
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
        stringJoiner.add("streamType: " + this.streamType);
        stringJoiner.add("bitRatePerSecond: " + this.bitRatePerSecond);
        stringJoiner.add("durationUs: " + this.durationUs);
        stringJoiner.add("hasVideo: " + this.hasVideo);
        stringJoiner.add("isStreaming: " + this.isStreaming);
        stringJoiner.add("bitWidth: " + this.bitWidth);
        stringJoiner.add("offloadBufferSize: " + this.offloadBufferSize);
        stringJoiner.add("usage: " + this.usage);
        stringJoiner.add("encapsulationMode: " + ((int) this.encapsulationMode));
        stringJoiner.add("contentId: " + this.contentId);
        stringJoiner.add("syncId: " + this.syncId);
        return "AudioOffloadInfo" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioOffloadInfo)) {
            return false;
        }
        android.media.audio.common.AudioOffloadInfo audioOffloadInfo = (android.media.audio.common.AudioOffloadInfo) obj;
        if (java.util.Objects.deepEquals(this.base, audioOffloadInfo.base) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.streamType), java.lang.Integer.valueOf(audioOffloadInfo.streamType)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.bitRatePerSecond), java.lang.Integer.valueOf(audioOffloadInfo.bitRatePerSecond)) && java.util.Objects.deepEquals(java.lang.Long.valueOf(this.durationUs), java.lang.Long.valueOf(audioOffloadInfo.durationUs)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.hasVideo), java.lang.Boolean.valueOf(audioOffloadInfo.hasVideo)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.isStreaming), java.lang.Boolean.valueOf(audioOffloadInfo.isStreaming)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.bitWidth), java.lang.Integer.valueOf(audioOffloadInfo.bitWidth)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.offloadBufferSize), java.lang.Integer.valueOf(audioOffloadInfo.offloadBufferSize)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.usage), java.lang.Integer.valueOf(audioOffloadInfo.usage)) && java.util.Objects.deepEquals(java.lang.Byte.valueOf(this.encapsulationMode), java.lang.Byte.valueOf(audioOffloadInfo.encapsulationMode)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.contentId), java.lang.Integer.valueOf(audioOffloadInfo.contentId)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.syncId), java.lang.Integer.valueOf(audioOffloadInfo.syncId))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.base, java.lang.Integer.valueOf(this.streamType), java.lang.Integer.valueOf(this.bitRatePerSecond), java.lang.Long.valueOf(this.durationUs), java.lang.Boolean.valueOf(this.hasVideo), java.lang.Boolean.valueOf(this.isStreaming), java.lang.Integer.valueOf(this.bitWidth), java.lang.Integer.valueOf(this.offloadBufferSize), java.lang.Integer.valueOf(this.usage), java.lang.Byte.valueOf(this.encapsulationMode), java.lang.Integer.valueOf(this.contentId), java.lang.Integer.valueOf(this.syncId)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.base) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
