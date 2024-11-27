package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioHalProductStrategy implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioHalProductStrategy> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioHalProductStrategy>() { // from class: android.media.audio.common.AudioHalProductStrategy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalProductStrategy createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioHalProductStrategy audioHalProductStrategy = new android.media.audio.common.AudioHalProductStrategy();
            audioHalProductStrategy.readFromParcel(parcel);
            return audioHalProductStrategy;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalProductStrategy[] newArray(int i) {
            return new android.media.audio.common.AudioHalProductStrategy[i];
        }
    };
    public static final int VENDOR_STRATEGY_ID_START = 1000;
    public android.media.audio.common.AudioHalAttributesGroup[] attributesGroups;
    public int id = -1;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeTypedArray(this.attributesGroups, i);
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
            } else {
                this.attributesGroups = (android.media.audio.common.AudioHalAttributesGroup[]) parcel.createTypedArray(android.media.audio.common.AudioHalAttributesGroup.CREATOR);
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
        stringJoiner.add("attributesGroups: " + java.util.Arrays.toString(this.attributesGroups));
        return "AudioHalProductStrategy" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioHalProductStrategy)) {
            return false;
        }
        android.media.audio.common.AudioHalProductStrategy audioHalProductStrategy = (android.media.audio.common.AudioHalProductStrategy) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.id), java.lang.Integer.valueOf(audioHalProductStrategy.id)) && java.util.Objects.deepEquals(this.attributesGroups, audioHalProductStrategy.attributesGroups)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.id), this.attributesGroups).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.attributesGroups) | 0;
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
