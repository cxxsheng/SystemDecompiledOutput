package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioAttributes implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioAttributes> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioAttributes>() { // from class: android.media.audio.common.AudioAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioAttributes createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioAttributes audioAttributes = new android.media.audio.common.AudioAttributes();
            audioAttributes.readFromParcel(parcel);
            return audioAttributes;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioAttributes[] newArray(int i) {
            return new android.media.audio.common.AudioAttributes[i];
        }
    };
    public java.lang.String[] tags;
    public int contentType = 0;
    public int usage = 0;
    public int source = 0;
    public int flags = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.contentType);
        parcel.writeInt(this.usage);
        parcel.writeInt(this.source);
        parcel.writeInt(this.flags);
        parcel.writeStringArray(this.tags);
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
            this.contentType = parcel.readInt();
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
            this.source = parcel.readInt();
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
            } else {
                this.tags = parcel.createStringArray();
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
        stringJoiner.add("contentType: " + this.contentType);
        stringJoiner.add("usage: " + this.usage);
        stringJoiner.add("source: " + this.source);
        stringJoiner.add("flags: " + this.flags);
        stringJoiner.add("tags: " + java.util.Arrays.toString(this.tags));
        return "AudioAttributes" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioAttributes)) {
            return false;
        }
        android.media.audio.common.AudioAttributes audioAttributes = (android.media.audio.common.AudioAttributes) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.contentType), java.lang.Integer.valueOf(audioAttributes.contentType)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.usage), java.lang.Integer.valueOf(audioAttributes.usage)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.source), java.lang.Integer.valueOf(audioAttributes.source)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.flags), java.lang.Integer.valueOf(audioAttributes.flags)) && java.util.Objects.deepEquals(this.tags, audioAttributes.tags)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.contentType), java.lang.Integer.valueOf(this.usage), java.lang.Integer.valueOf(this.source), java.lang.Integer.valueOf(this.flags), this.tags).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
