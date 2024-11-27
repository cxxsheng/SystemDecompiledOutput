package android.media.soundtrigger;

/* loaded from: classes2.dex */
public class Phrase implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger.Phrase> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger.Phrase>() { // from class: android.media.soundtrigger.Phrase.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.Phrase createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger.Phrase phrase = new android.media.soundtrigger.Phrase();
            phrase.readFromParcel(parcel);
            return phrase;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.Phrase[] newArray(int i) {
            return new android.media.soundtrigger.Phrase[i];
        }
    };
    public java.lang.String locale;
    public java.lang.String text;
    public int[] users;
    public int id = 0;
    public int recognitionModes = 0;

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
        parcel.writeIntArray(this.users);
        parcel.writeString(this.locale);
        parcel.writeString(this.text);
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
            this.users = parcel.createIntArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.locale = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.text = parcel.readString();
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
        stringJoiner.add("users: " + java.util.Arrays.toString(this.users));
        stringJoiner.add("locale: " + java.util.Objects.toString(this.locale));
        stringJoiner.add("text: " + java.util.Objects.toString(this.text));
        return "Phrase" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger.Phrase)) {
            return false;
        }
        android.media.soundtrigger.Phrase phrase = (android.media.soundtrigger.Phrase) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.id), java.lang.Integer.valueOf(phrase.id)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.recognitionModes), java.lang.Integer.valueOf(phrase.recognitionModes)) && java.util.Objects.deepEquals(this.users, phrase.users) && java.util.Objects.deepEquals(this.locale, phrase.locale) && java.util.Objects.deepEquals(this.text, phrase.text)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.id), java.lang.Integer.valueOf(this.recognitionModes), this.users, this.locale, this.text).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
