package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioUuid implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioUuid> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioUuid>() { // from class: android.media.audio.common.AudioUuid.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioUuid createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioUuid audioUuid = new android.media.audio.common.AudioUuid();
            audioUuid.readFromParcel(parcel);
            return audioUuid;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioUuid[] newArray(int i) {
            return new android.media.audio.common.AudioUuid[i];
        }
    };
    public byte[] node;
    public int timeLow = 0;
    public int timeMid = 0;
    public int timeHiAndVersion = 0;
    public int clockSeq = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.timeLow);
        parcel.writeInt(this.timeMid);
        parcel.writeInt(this.timeHiAndVersion);
        parcel.writeInt(this.clockSeq);
        parcel.writeByteArray(this.node);
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
            this.timeLow = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timeMid = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timeHiAndVersion = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.clockSeq = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.node = parcel.createByteArray();
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
        stringJoiner.add("timeLow: " + this.timeLow);
        stringJoiner.add("timeMid: " + this.timeMid);
        stringJoiner.add("timeHiAndVersion: " + this.timeHiAndVersion);
        stringJoiner.add("clockSeq: " + this.clockSeq);
        stringJoiner.add("node: " + java.util.Arrays.toString(this.node));
        return "AudioUuid" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioUuid)) {
            return false;
        }
        android.media.audio.common.AudioUuid audioUuid = (android.media.audio.common.AudioUuid) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.timeLow), java.lang.Integer.valueOf(audioUuid.timeLow)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.timeMid), java.lang.Integer.valueOf(audioUuid.timeMid)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.timeHiAndVersion), java.lang.Integer.valueOf(audioUuid.timeHiAndVersion)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.clockSeq), java.lang.Integer.valueOf(audioUuid.clockSeq)) && java.util.Objects.deepEquals(this.node, audioUuid.node)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.timeLow), java.lang.Integer.valueOf(this.timeMid), java.lang.Integer.valueOf(this.timeHiAndVersion), java.lang.Integer.valueOf(this.clockSeq), this.node).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
