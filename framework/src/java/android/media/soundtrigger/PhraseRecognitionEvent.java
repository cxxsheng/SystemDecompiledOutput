package android.media.soundtrigger;

/* loaded from: classes2.dex */
public class PhraseRecognitionEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger.PhraseRecognitionEvent> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger.PhraseRecognitionEvent>() { // from class: android.media.soundtrigger.PhraseRecognitionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.PhraseRecognitionEvent createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent = new android.media.soundtrigger.PhraseRecognitionEvent();
            phraseRecognitionEvent.readFromParcel(parcel);
            return phraseRecognitionEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.PhraseRecognitionEvent[] newArray(int i) {
            return new android.media.soundtrigger.PhraseRecognitionEvent[i];
        }
    };
    public android.media.soundtrigger.RecognitionEvent common;
    public android.media.soundtrigger.PhraseRecognitionExtra[] phraseExtras;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.common, i);
        parcel.writeTypedArray(this.phraseExtras, i);
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
            this.common = (android.media.soundtrigger.RecognitionEvent) parcel.readTypedObject(android.media.soundtrigger.RecognitionEvent.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.phraseExtras = (android.media.soundtrigger.PhraseRecognitionExtra[]) parcel.createTypedArray(android.media.soundtrigger.PhraseRecognitionExtra.CREATOR);
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
        stringJoiner.add("common: " + java.util.Objects.toString(this.common));
        stringJoiner.add("phraseExtras: " + java.util.Arrays.toString(this.phraseExtras));
        return "PhraseRecognitionEvent" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger.PhraseRecognitionEvent)) {
            return false;
        }
        android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent = (android.media.soundtrigger.PhraseRecognitionEvent) obj;
        if (java.util.Objects.deepEquals(this.common, phraseRecognitionEvent.common) && java.util.Objects.deepEquals(this.phraseExtras, phraseRecognitionEvent.phraseExtras)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.common, this.phraseExtras).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.common) | 0 | describeContents(this.phraseExtras);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
