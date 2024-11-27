package android.media.soundtrigger;

/* loaded from: classes2.dex */
public class SoundModel implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.soundtrigger.SoundModel> CREATOR = new android.os.Parcelable.Creator<android.media.soundtrigger.SoundModel>() { // from class: android.media.soundtrigger.SoundModel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.SoundModel createFromParcel(android.os.Parcel parcel) {
            android.media.soundtrigger.SoundModel soundModel = new android.media.soundtrigger.SoundModel();
            soundModel.readFromParcel(parcel);
            return soundModel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.soundtrigger.SoundModel[] newArray(int i) {
            return new android.media.soundtrigger.SoundModel[i];
        }
    };
    public android.os.ParcelFileDescriptor data;
    public java.lang.String uuid;
    public java.lang.String vendorUuid;
    public int type = -1;
    public int dataSize = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeString(this.uuid);
        parcel.writeString(this.vendorUuid);
        parcel.writeTypedObject(this.data, i);
        parcel.writeInt(this.dataSize);
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
            this.type = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.uuid = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.vendorUuid = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.data = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.dataSize = parcel.readInt();
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
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("uuid: " + java.util.Objects.toString(this.uuid));
        stringJoiner.add("vendorUuid: " + java.util.Objects.toString(this.vendorUuid));
        stringJoiner.add("data: " + java.util.Objects.toString(this.data));
        stringJoiner.add("dataSize: " + this.dataSize);
        return "SoundModel" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.soundtrigger.SoundModel)) {
            return false;
        }
        android.media.soundtrigger.SoundModel soundModel = (android.media.soundtrigger.SoundModel) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.type), java.lang.Integer.valueOf(soundModel.type)) && java.util.Objects.deepEquals(this.uuid, soundModel.uuid) && java.util.Objects.deepEquals(this.vendorUuid, soundModel.vendorUuid) && java.util.Objects.deepEquals(this.data, soundModel.data) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.dataSize), java.lang.Integer.valueOf(soundModel.dataSize))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.type), this.uuid, this.vendorUuid, this.data, java.lang.Integer.valueOf(this.dataSize)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.data) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
