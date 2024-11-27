package android.media.soundtrigger;

/* loaded from: classes2.dex */
public class PhraseRecognitionExtra implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger.PhraseRecognitionExtra> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger.PhraseRecognitionExtra>() { // from class: android.media.soundtrigger.PhraseRecognitionExtra.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.PhraseRecognitionExtra createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger.PhraseRecognitionExtra phraseRecognitionExtra = new android.media.soundtrigger.PhraseRecognitionExtra();
            phraseRecognitionExtra.readFromParcel(parcel);
            return phraseRecognitionExtra;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.PhraseRecognitionExtra[] newArray(int i) {
            return new android.media.soundtrigger.PhraseRecognitionExtra[i];
        }
    };
    public android.media.soundtrigger.ConfidenceLevel[] levels;
    public int id = 0;
    public int recognitionModes = 0;
    public int confidenceLevel = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeInt(this.recognitionModes);
        parcel.writeInt(this.confidenceLevel);
        parcel.writeTypedArray(this.levels, i);
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
            this.id = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.recognitionModes = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.confidenceLevel = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.levels = (android.media.soundtrigger.ConfidenceLevel[]) parcel.createTypedArray(android.media.soundtrigger.ConfidenceLevel.CREATOR);
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
        stringJoiner.add("id: " + this.id);
        stringJoiner.add("recognitionModes: " + this.recognitionModes);
        stringJoiner.add("confidenceLevel: " + this.confidenceLevel);
        stringJoiner.add("levels: " + java.util.Arrays.toString(this.levels));
        return "PhraseRecognitionExtra" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger.PhraseRecognitionExtra)) {
            return false;
        }
        android.media.soundtrigger.PhraseRecognitionExtra phraseRecognitionExtra = (android.media.soundtrigger.PhraseRecognitionExtra) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.id), java.lang.Integer.valueOf(phraseRecognitionExtra.id)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.recognitionModes), java.lang.Integer.valueOf(phraseRecognitionExtra.recognitionModes)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.confidenceLevel), java.lang.Integer.valueOf(phraseRecognitionExtra.confidenceLevel)) && java.util.Objects.deepEquals(this.levels, phraseRecognitionExtra.levels)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.id), java.lang.Integer.valueOf(this.recognitionModes), java.lang.Integer.valueOf(this.confidenceLevel), this.levels).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.levels) | 0;
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
