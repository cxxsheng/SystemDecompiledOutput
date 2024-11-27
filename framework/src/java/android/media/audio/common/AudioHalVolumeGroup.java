package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioHalVolumeGroup implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioHalVolumeGroup> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioHalVolumeGroup>() { // from class: android.media.audio.common.AudioHalVolumeGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalVolumeGroup createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioHalVolumeGroup audioHalVolumeGroup = new android.media.audio.common.AudioHalVolumeGroup();
            audioHalVolumeGroup.readFromParcel(parcel);
            return audioHalVolumeGroup;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalVolumeGroup[] newArray(int i) {
            return new android.media.audio.common.AudioHalVolumeGroup[i];
        }
    };
    public static final int INDEX_DEFERRED_TO_AUDIO_SERVICE = -1;
    public java.lang.String name;
    public android.media.audio.common.AudioHalVolumeCurve[] volumeCurves;
    public int minIndex = 0;
    public int maxIndex = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeInt(this.minIndex);
        parcel.writeInt(this.maxIndex);
        parcel.writeTypedArray(this.volumeCurves, i);
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
            this.name = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.minIndex = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxIndex = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.volumeCurves = (android.media.audio.common.AudioHalVolumeCurve[]) parcel.createTypedArray(android.media.audio.common.AudioHalVolumeCurve.CREATOR);
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
        stringJoiner.add("name: " + java.util.Objects.toString(this.name));
        stringJoiner.add("minIndex: " + this.minIndex);
        stringJoiner.add("maxIndex: " + this.maxIndex);
        stringJoiner.add("volumeCurves: " + java.util.Arrays.toString(this.volumeCurves));
        return "AudioHalVolumeGroup" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioHalVolumeGroup)) {
            return false;
        }
        android.media.audio.common.AudioHalVolumeGroup audioHalVolumeGroup = (android.media.audio.common.AudioHalVolumeGroup) obj;
        if (java.util.Objects.deepEquals(this.name, audioHalVolumeGroup.name) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.minIndex), java.lang.Integer.valueOf(audioHalVolumeGroup.minIndex)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.maxIndex), java.lang.Integer.valueOf(audioHalVolumeGroup.maxIndex)) && java.util.Objects.deepEquals(this.volumeCurves, audioHalVolumeGroup.volumeCurves)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.name, java.lang.Integer.valueOf(this.minIndex), java.lang.Integer.valueOf(this.maxIndex), this.volumeCurves).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.volumeCurves) | 0;
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
