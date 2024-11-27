package android.media;

/* loaded from: classes2.dex */
public class SurroundSoundConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.SurroundSoundConfig> CREATOR = new android.os.Parcelable.Creator<android.media.SurroundSoundConfig>() { // from class: android.media.SurroundSoundConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.SurroundSoundConfig createFromParcel(android.os.Parcel parcel) {
            android.media.SurroundSoundConfig surroundSoundConfig = new android.media.SurroundSoundConfig();
            surroundSoundConfig.readFromParcel(parcel);
            return surroundSoundConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.SurroundSoundConfig[] newArray(int i) {
            return new android.media.SurroundSoundConfig[i];
        }
    };
    public android.media.SurroundSoundConfig.SurroundFormatFamily[] formatFamilies;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.formatFamilies, i);
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
            } else {
                this.formatFamilies = (android.media.SurroundSoundConfig.SurroundFormatFamily[]) parcel.createTypedArray(android.media.SurroundSoundConfig.SurroundFormatFamily.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.formatFamilies) | 0;
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

    public static class SurroundFormatFamily implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.SurroundSoundConfig.SurroundFormatFamily> CREATOR = new android.os.Parcelable.Creator<android.media.SurroundSoundConfig.SurroundFormatFamily>() { // from class: android.media.SurroundSoundConfig.SurroundFormatFamily.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.SurroundSoundConfig.SurroundFormatFamily createFromParcel(android.os.Parcel parcel) {
                android.media.SurroundSoundConfig.SurroundFormatFamily surroundFormatFamily = new android.media.SurroundSoundConfig.SurroundFormatFamily();
                surroundFormatFamily.readFromParcel(parcel);
                return surroundFormatFamily;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.SurroundSoundConfig.SurroundFormatFamily[] newArray(int i) {
                return new android.media.SurroundSoundConfig.SurroundFormatFamily[i];
            }
        };
        public android.media.audio.common.AudioFormatDescription primaryFormat;
        public android.media.audio.common.AudioFormatDescription[] subFormats;

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.primaryFormat, i);
            parcel.writeTypedArray(this.subFormats, i);
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
                this.primaryFormat = (android.media.audio.common.AudioFormatDescription) parcel.readTypedObject(android.media.audio.common.AudioFormatDescription.CREATOR);
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.subFormats = (android.media.audio.common.AudioFormatDescription[]) parcel.createTypedArray(android.media.audio.common.AudioFormatDescription.CREATOR);
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.primaryFormat) | 0 | describeContents(this.subFormats);
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
}
