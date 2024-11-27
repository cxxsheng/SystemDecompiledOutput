package android.media.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class PhraseRecognitionEventSys implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger_middleware.PhraseRecognitionEventSys> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger_middleware.PhraseRecognitionEventSys>() { // from class: android.media.soundtrigger_middleware.PhraseRecognitionEventSys.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger_middleware.PhraseRecognitionEventSys createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys = new android.media.soundtrigger_middleware.PhraseRecognitionEventSys();
            phraseRecognitionEventSys.readFromParcel(parcel);
            return phraseRecognitionEventSys;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger_middleware.PhraseRecognitionEventSys[] newArray(int i) {
            return new android.media.soundtrigger_middleware.PhraseRecognitionEventSys[i];
        }
    };
    public long halEventReceivedMillis = -1;
    public android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent;
    public android.os.IBinder token;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.phraseRecognitionEvent, i);
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
            this.phraseRecognitionEvent = (android.media.soundtrigger.PhraseRecognitionEvent) parcel.readTypedObject(android.media.soundtrigger.PhraseRecognitionEvent.CREATOR);
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
        stringJoiner.add("phraseRecognitionEvent: " + java.util.Objects.toString(this.phraseRecognitionEvent));
        stringJoiner.add("halEventReceivedMillis: " + this.halEventReceivedMillis);
        stringJoiner.add("token: " + java.util.Objects.toString(this.token));
        return "PhraseRecognitionEventSys" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger_middleware.PhraseRecognitionEventSys)) {
            return false;
        }
        android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys = (android.media.soundtrigger_middleware.PhraseRecognitionEventSys) obj;
        if (java.util.Objects.deepEquals(this.phraseRecognitionEvent, phraseRecognitionEventSys.phraseRecognitionEvent) && java.util.Objects.deepEquals(java.lang.Long.valueOf(this.halEventReceivedMillis), java.lang.Long.valueOf(phraseRecognitionEventSys.halEventReceivedMillis)) && java.util.Objects.deepEquals(this.token, phraseRecognitionEventSys.token)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.phraseRecognitionEvent, java.lang.Long.valueOf(this.halEventReceivedMillis), this.token).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.phraseRecognitionEvent) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
