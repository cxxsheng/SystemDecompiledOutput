package android.media.soundtrigger;

/* loaded from: classes2.dex */
public class RecognitionEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger.RecognitionEvent> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger.RecognitionEvent>() { // from class: android.media.soundtrigger.RecognitionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.RecognitionEvent createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger.RecognitionEvent recognitionEvent = new android.media.soundtrigger.RecognitionEvent();
            recognitionEvent.readFromParcel(parcel);
            return recognitionEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.RecognitionEvent[] newArray(int i) {
            return new android.media.soundtrigger.RecognitionEvent[i];
        }
    };
    public android.media.audio.common.AudioConfig audioConfig;
    public byte[] data;
    public int status = -1;
    public int type = -1;
    public boolean captureAvailable = false;
    public int captureDelayMs = 0;
    public int capturePreambleMs = 0;
    public boolean triggerInData = false;
    public boolean recognitionStillActive = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.status);
        parcel.writeInt(this.type);
        parcel.writeBoolean(this.captureAvailable);
        parcel.writeInt(this.captureDelayMs);
        parcel.writeInt(this.capturePreambleMs);
        parcel.writeBoolean(this.triggerInData);
        parcel.writeTypedObject(this.audioConfig, i);
        parcel.writeByteArray(this.data);
        parcel.writeBoolean(this.recognitionStillActive);
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
            this.status = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.type = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.captureAvailable = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.captureDelayMs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.capturePreambleMs = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.triggerInData = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.audioConfig = (android.media.audio.common.AudioConfig) parcel.readTypedObject(android.media.audio.common.AudioConfig.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.data = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.recognitionStillActive = parcel.readBoolean();
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
        stringJoiner.add("status: " + this.status);
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("captureAvailable: " + this.captureAvailable);
        stringJoiner.add("captureDelayMs: " + this.captureDelayMs);
        stringJoiner.add("capturePreambleMs: " + this.capturePreambleMs);
        stringJoiner.add("triggerInData: " + this.triggerInData);
        stringJoiner.add("audioConfig: " + java.util.Objects.toString(this.audioConfig));
        stringJoiner.add("data: " + java.util.Arrays.toString(this.data));
        stringJoiner.add("recognitionStillActive: " + this.recognitionStillActive);
        return "RecognitionEvent" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger.RecognitionEvent)) {
            return false;
        }
        android.media.soundtrigger.RecognitionEvent recognitionEvent = (android.media.soundtrigger.RecognitionEvent) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.status), java.lang.Integer.valueOf(recognitionEvent.status)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.type), java.lang.Integer.valueOf(recognitionEvent.type)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.captureAvailable), java.lang.Boolean.valueOf(recognitionEvent.captureAvailable)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.captureDelayMs), java.lang.Integer.valueOf(recognitionEvent.captureDelayMs)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.capturePreambleMs), java.lang.Integer.valueOf(recognitionEvent.capturePreambleMs)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.triggerInData), java.lang.Boolean.valueOf(recognitionEvent.triggerInData)) && java.util.Objects.deepEquals(this.audioConfig, recognitionEvent.audioConfig) && java.util.Objects.deepEquals(this.data, recognitionEvent.data) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.recognitionStillActive), java.lang.Boolean.valueOf(recognitionEvent.recognitionStillActive))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.status), java.lang.Integer.valueOf(this.type), java.lang.Boolean.valueOf(this.captureAvailable), java.lang.Integer.valueOf(this.captureDelayMs), java.lang.Integer.valueOf(this.capturePreambleMs), java.lang.Boolean.valueOf(this.triggerInData), this.audioConfig, this.data, java.lang.Boolean.valueOf(this.recognitionStillActive)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.audioConfig) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
