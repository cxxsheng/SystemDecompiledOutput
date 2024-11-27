package android.media.audio.common;

/* loaded from: classes2.dex */
public class MicrophoneDynamicInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.MicrophoneDynamicInfo> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.MicrophoneDynamicInfo>() { // from class: android.media.audio.common.MicrophoneDynamicInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.MicrophoneDynamicInfo createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.MicrophoneDynamicInfo microphoneDynamicInfo = new android.media.audio.common.MicrophoneDynamicInfo();
            microphoneDynamicInfo.readFromParcel(parcel);
            return microphoneDynamicInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.MicrophoneDynamicInfo[] newArray(int i) {
            return new android.media.audio.common.MicrophoneDynamicInfo[i];
        }
    };
    public int[] channelMapping;
    public java.lang.String id;

    public @interface ChannelMapping {
        public static final int DIRECT = 1;
        public static final int PROCESSED = 2;
        public static final int UNUSED = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.id);
        parcel.writeIntArray(this.channelMapping);
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
            this.id = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.channelMapping = parcel.createIntArray();
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
        stringJoiner.add("id: " + java.util.Objects.toString(this.id));
        stringJoiner.add("channelMapping: " + java.util.Arrays.toString(this.channelMapping));
        return "MicrophoneDynamicInfo" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.MicrophoneDynamicInfo)) {
            return false;
        }
        android.media.audio.common.MicrophoneDynamicInfo microphoneDynamicInfo = (android.media.audio.common.MicrophoneDynamicInfo) obj;
        if (java.util.Objects.deepEquals(this.id, microphoneDynamicInfo.id) && java.util.Objects.deepEquals(this.channelMapping, microphoneDynamicInfo.channelMapping)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.id, this.channelMapping).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
