package android.media;

/* loaded from: classes2.dex */
public class LoudnessCodecInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.LoudnessCodecInfo> CREATOR = new android.os.Parcelable.Creator<android.media.LoudnessCodecInfo>() { // from class: android.media.LoudnessCodecInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.LoudnessCodecInfo createFromParcel(android.os.Parcel parcel) {
            android.media.LoudnessCodecInfo loudnessCodecInfo = new android.media.LoudnessCodecInfo();
            loudnessCodecInfo.readFromParcel(parcel);
            return loudnessCodecInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.LoudnessCodecInfo[] newArray(int i) {
            return new android.media.LoudnessCodecInfo[i];
        }
    };
    public boolean isDownmixing = false;
    public int metadataType;

    public @interface CodecMetadataType {
        public static final int CODEC_METADATA_TYPE_AC_3 = 3;
        public static final int CODEC_METADATA_TYPE_AC_4 = 4;
        public static final int CODEC_METADATA_TYPE_DTS_HD = 5;
        public static final int CODEC_METADATA_TYPE_DTS_UHD = 6;
        public static final int CODEC_METADATA_TYPE_INVALID = 0;
        public static final int CODEC_METADATA_TYPE_MPEG_4 = 1;
        public static final int CODEC_METADATA_TYPE_MPEG_D = 2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.metadataType);
        parcel.writeBoolean(this.isDownmixing);
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
            this.metadataType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.isDownmixing = parcel.readBoolean();
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
        stringJoiner.add("metadataType: " + this.metadataType);
        stringJoiner.add("isDownmixing: " + this.isDownmixing);
        return "LoudnessCodecInfo" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.LoudnessCodecInfo)) {
            return false;
        }
        android.media.LoudnessCodecInfo loudnessCodecInfo = (android.media.LoudnessCodecInfo) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.metadataType), java.lang.Integer.valueOf(loudnessCodecInfo.metadataType)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.isDownmixing), java.lang.Boolean.valueOf(loudnessCodecInfo.isDownmixing))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.metadataType), java.lang.Boolean.valueOf(this.isDownmixing)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
