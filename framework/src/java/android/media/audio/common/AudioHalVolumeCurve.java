package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioHalVolumeCurve implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioHalVolumeCurve> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioHalVolumeCurve>() { // from class: android.media.audio.common.AudioHalVolumeCurve.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalVolumeCurve createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioHalVolumeCurve audioHalVolumeCurve = new android.media.audio.common.AudioHalVolumeCurve();
            audioHalVolumeCurve.readFromParcel(parcel);
            return audioHalVolumeCurve;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalVolumeCurve[] newArray(int i) {
            return new android.media.audio.common.AudioHalVolumeCurve[i];
        }
    };
    public android.media.audio.common.AudioHalVolumeCurve.CurvePoint[] curvePoints;
    public byte deviceCategory = 1;

    public @interface DeviceCategory {
        public static final byte EARPIECE = 2;
        public static final byte EXT_MEDIA = 3;
        public static final byte HEADSET = 0;
        public static final byte HEARING_AID = 4;
        public static final byte SPEAKER = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.deviceCategory);
        parcel.writeTypedArray(this.curvePoints, i);
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
            this.deviceCategory = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.curvePoints = (android.media.audio.common.AudioHalVolumeCurve.CurvePoint[]) parcel.createTypedArray(android.media.audio.common.AudioHalVolumeCurve.CurvePoint.CREATOR);
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
        stringJoiner.add("deviceCategory: " + ((int) this.deviceCategory));
        stringJoiner.add("curvePoints: " + java.util.Arrays.toString(this.curvePoints));
        return "AudioHalVolumeCurve" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioHalVolumeCurve)) {
            return false;
        }
        android.media.audio.common.AudioHalVolumeCurve audioHalVolumeCurve = (android.media.audio.common.AudioHalVolumeCurve) obj;
        if (java.util.Objects.deepEquals(java.lang.Byte.valueOf(this.deviceCategory), java.lang.Byte.valueOf(audioHalVolumeCurve.deviceCategory)) && java.util.Objects.deepEquals(this.curvePoints, audioHalVolumeCurve.curvePoints)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Byte.valueOf(this.deviceCategory), this.curvePoints).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.curvePoints) | 0;
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

    public static class CurvePoint implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.audio.common.AudioHalVolumeCurve.CurvePoint> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioHalVolumeCurve.CurvePoint>() { // from class: android.media.audio.common.AudioHalVolumeCurve.CurvePoint.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.AudioHalVolumeCurve.CurvePoint createFromParcel(android.os.Parcel parcel) {
                android.media.audio.common.AudioHalVolumeCurve.CurvePoint curvePoint = new android.media.audio.common.AudioHalVolumeCurve.CurvePoint();
                curvePoint.readFromParcel(parcel);
                return curvePoint;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.AudioHalVolumeCurve.CurvePoint[] newArray(int i) {
                return new android.media.audio.common.AudioHalVolumeCurve.CurvePoint[i];
            }
        };
        public static final byte MAX_INDEX = 100;
        public static final byte MIN_INDEX = 0;
        public byte index = 0;
        public int attenuationMb = 0;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeByte(this.index);
            parcel.writeInt(this.attenuationMb);
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
                this.index = parcel.readByte();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.attenuationMb = parcel.readInt();
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
            return 0;
        }
    }
}
