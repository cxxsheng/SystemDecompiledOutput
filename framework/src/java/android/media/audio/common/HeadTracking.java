package android.media.audio.common;

/* loaded from: classes2.dex */
public class HeadTracking implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.HeadTracking> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.HeadTracking>() { // from class: android.media.audio.common.HeadTracking.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.HeadTracking createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.HeadTracking headTracking = new android.media.audio.common.HeadTracking();
            headTracking.readFromParcel(parcel);
            return headTracking;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.HeadTracking[] newArray(int i) {
            return new android.media.audio.common.HeadTracking[i];
        }
    };

    public @interface ConnectionMode {
        public static final byte DIRECT_TO_SENSOR_SW = 1;
        public static final byte DIRECT_TO_SENSOR_TUNNEL = 2;
        public static final byte FRAMEWORK_PROCESSED = 0;
    }

    public @interface Mode {
        public static final byte DISABLED = 1;
        public static final byte OTHER = 0;
        public static final byte RELATIVE_SCREEN = 3;
        public static final byte RELATIVE_WORLD = 2;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        if (readInt < 4) {
            try {
                throw new android.os.BadParcelableException("Parcelable too small");
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
        if (dataPosition > Integer.MAX_VALUE - readInt) {
            throw new android.os.BadParcelableException("Overflow in the size of parcelable");
        }
        parcel.setDataPosition(dataPosition + readInt);
    }

    public java.lang.String toString() {
        return "HeadTracking" + new java.util.StringJoiner(", ", "{", "}").toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.HeadTracking)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(new java.lang.Object[0]).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class SensorData implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.audio.common.HeadTracking.SensorData> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.HeadTracking.SensorData>() { // from class: android.media.audio.common.HeadTracking.SensorData.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.HeadTracking.SensorData createFromParcel(android.os.Parcel parcel) {
                return new android.media.audio.common.HeadTracking.SensorData(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.HeadTracking.SensorData[] newArray(int i) {
                return new android.media.audio.common.HeadTracking.SensorData[i];
            }
        };
        public static final int headToStage = 0;
        private int _tag;
        private java.lang.Object _value;

        public @interface Tag {
            public static final int headToStage = 0;
        }

        public SensorData() {
            this._tag = 0;
            this._value = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        }

        private SensorData(android.os.Parcel parcel) {
            readFromParcel(parcel);
        }

        private SensorData(int i, java.lang.Object obj) {
            this._tag = i;
            this._value = obj;
        }

        public int getTag() {
            return this._tag;
        }

        public static android.media.audio.common.HeadTracking.SensorData headToStage(float[] fArr) {
            return new android.media.audio.common.HeadTracking.SensorData(0, fArr);
        }

        public float[] getHeadToStage() {
            _assertTag(0);
            return (float[]) this._value;
        }

        public void setHeadToStage(float[] fArr) {
            _set(0, fArr);
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this._tag);
            switch (this._tag) {
                case 0:
                    parcel.writeFixedArray(getHeadToStage(), i, 6);
                    break;
            }
        }

        public void readFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            switch (readInt) {
                case 0:
                    _set(readInt, (float[]) parcel.createFixedArray(float[].class, 6));
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            getTag();
            return 0;
        }

        private void _assertTag(int i) {
            if (getTag() != i) {
                throw new java.lang.IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
            }
        }

        private java.lang.String _tagString(int i) {
            switch (i) {
                case 0:
                    return "headToStage";
                default:
                    throw new java.lang.IllegalStateException("unknown field: " + i);
            }
        }

        private void _set(int i, java.lang.Object obj) {
            this._tag = i;
            this._value = obj;
        }
    }
}
