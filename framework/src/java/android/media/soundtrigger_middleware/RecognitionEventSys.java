package android.media.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class RecognitionEventSys implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger_middleware.RecognitionEventSys> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger_middleware.RecognitionEventSys>() { // from class: android.media.soundtrigger_middleware.RecognitionEventSys.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger_middleware.RecognitionEventSys createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys = new android.media.soundtrigger_middleware.RecognitionEventSys();
            recognitionEventSys.readFromParcel(parcel);
            return recognitionEventSys;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger_middleware.RecognitionEventSys[] newArray(int i) {
            return new android.media.soundtrigger_middleware.RecognitionEventSys[i];
        }
    };
    public long halEventReceivedMillis = -1;
    public android.media.soundtrigger.RecognitionEvent recognitionEvent;
    public android.os.IBinder token;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.recognitionEvent, i);
        parcel.writeLong(this.halEventReceivedMillis);
        parcel.writeStrongBinder(this.token);
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
            this.recognitionEvent = (android.media.soundtrigger.RecognitionEvent) parcel.readTypedObject(android.media.soundtrigger.RecognitionEvent.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.halEventReceivedMillis = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.token = parcel.readStrongBinder();
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
        stringJoiner.add("recognitionEvent: " + java.util.Objects.toString(this.recognitionEvent));
        stringJoiner.add("halEventReceivedMillis: " + this.halEventReceivedMillis);
        stringJoiner.add("token: " + java.util.Objects.toString(this.token));
        return "RecognitionEventSys" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger_middleware.RecognitionEventSys)) {
            return false;
        }
        android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys = (android.media.soundtrigger_middleware.RecognitionEventSys) obj;
        if (java.util.Objects.deepEquals(this.recognitionEvent, recognitionEventSys.recognitionEvent) && java.util.Objects.deepEquals(java.lang.Long.valueOf(this.halEventReceivedMillis), java.lang.Long.valueOf(recognitionEventSys.halEventReceivedMillis)) && java.util.Objects.deepEquals(this.token, recognitionEventSys.token)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.recognitionEvent, java.lang.Long.valueOf(this.halEventReceivedMillis), this.token).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.recognitionEvent) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
