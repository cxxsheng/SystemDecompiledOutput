package android.media.soundtrigger;

/* loaded from: classes2.dex */
public class RecognitionConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger.RecognitionConfig> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger.RecognitionConfig>() { // from class: android.media.soundtrigger.RecognitionConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.RecognitionConfig createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger.RecognitionConfig recognitionConfig = new android.media.soundtrigger.RecognitionConfig();
            recognitionConfig.readFromParcel(parcel);
            return recognitionConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.RecognitionConfig[] newArray(int i) {
            return new android.media.soundtrigger.RecognitionConfig[i];
        }
    };
    public byte[] data;
    public android.media.soundtrigger.PhraseRecognitionExtra[] phraseRecognitionExtras;
    public boolean captureRequested = false;
    public int audioCapabilities = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.captureRequested);
        parcel.writeTypedArray(this.phraseRecognitionExtras, i);
        parcel.writeInt(this.audioCapabilities);
        parcel.writeByteArray(this.data);
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
            this.captureRequested = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.phraseRecognitionExtras = (android.media.soundtrigger.PhraseRecognitionExtra[]) parcel.createTypedArray(android.media.soundtrigger.PhraseRecognitionExtra.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.audioCapabilities = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.data = parcel.createByteArray();
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
        stringJoiner.add("captureRequested: " + this.captureRequested);
        stringJoiner.add("phraseRecognitionExtras: " + java.util.Arrays.toString(this.phraseRecognitionExtras));
        stringJoiner.add("audioCapabilities: " + this.audioCapabilities);
        stringJoiner.add("data: " + java.util.Arrays.toString(this.data));
        return "RecognitionConfig" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger.RecognitionConfig)) {
            return false;
        }
        android.media.soundtrigger.RecognitionConfig recognitionConfig = (android.media.soundtrigger.RecognitionConfig) obj;
        if (java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.captureRequested), java.lang.Boolean.valueOf(recognitionConfig.captureRequested)) && java.util.Objects.deepEquals(this.phraseRecognitionExtras, recognitionConfig.phraseRecognitionExtras) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.audioCapabilities), java.lang.Integer.valueOf(recognitionConfig.audioCapabilities)) && java.util.Objects.deepEquals(this.data, recognitionConfig.data)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Boolean.valueOf(this.captureRequested), this.phraseRecognitionExtras, java.lang.Integer.valueOf(this.audioCapabilities), this.data).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.phraseRecognitionExtras) | 0;
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
