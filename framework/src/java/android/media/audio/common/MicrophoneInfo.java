package android.media.audio.common;

/* loaded from: classes2.dex */
public class MicrophoneInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.MicrophoneInfo> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.MicrophoneInfo>() { // from class: android.media.audio.common.MicrophoneInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.MicrophoneInfo createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.MicrophoneInfo microphoneInfo = new android.media.audio.common.MicrophoneInfo();
            microphoneInfo.readFromParcel(parcel);
            return microphoneInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.MicrophoneInfo[] newArray(int i) {
            return new android.media.audio.common.MicrophoneInfo[i];
        }
    };
    public static final int GROUP_UNKNOWN = -1;
    public static final int INDEX_IN_THE_GROUP_UNKNOWN = -1;
    public android.media.audio.common.AudioDevice device;
    public android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint[] frequencyResponse;
    public java.lang.String id;
    public android.media.audio.common.MicrophoneInfo.Coordinate orientation;
    public android.media.audio.common.MicrophoneInfo.Coordinate position;
    public android.media.audio.common.MicrophoneInfo.Sensitivity sensitivity;
    public int location = 0;
    public int group = -1;
    public int indexInTheGroup = -1;
    public int directionality = 0;

    public @interface Directionality {
        public static final int BI_DIRECTIONAL = 2;
        public static final int CARDIOID = 3;
        public static final int HYPER_CARDIOID = 4;
        public static final int OMNI = 1;
        public static final int SUPER_CARDIOID = 5;
        public static final int UNKNOWN = 0;
    }

    public @interface Location {
        public static final int MAINBODY = 1;
        public static final int MAINBODY_MOVABLE = 2;
        public static final int PERIPHERAL = 3;
        public static final int UNKNOWN = 0;
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
        parcel.writeTypedObject(this.device, i);
        parcel.writeInt(this.location);
        parcel.writeInt(this.group);
        parcel.writeInt(this.indexInTheGroup);
        parcel.writeTypedObject(this.sensitivity, i);
        parcel.writeInt(this.directionality);
        parcel.writeTypedArray(this.frequencyResponse, i);
        parcel.writeTypedObject(this.position, i);
        parcel.writeTypedObject(this.orientation, i);
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
                return;
            }
            this.device = (android.media.audio.common.AudioDevice) parcel.readTypedObject(android.media.audio.common.AudioDevice.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.location = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.group = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.indexInTheGroup = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sensitivity = (android.media.audio.common.MicrophoneInfo.Sensitivity) parcel.readTypedObject(android.media.audio.common.MicrophoneInfo.Sensitivity.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.directionality = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.frequencyResponse = (android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint[]) parcel.createTypedArray(android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.position = (android.media.audio.common.MicrophoneInfo.Coordinate) parcel.readTypedObject(android.media.audio.common.MicrophoneInfo.Coordinate.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.orientation = (android.media.audio.common.MicrophoneInfo.Coordinate) parcel.readTypedObject(android.media.audio.common.MicrophoneInfo.Coordinate.CREATOR);
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
        stringJoiner.add("device: " + java.util.Objects.toString(this.device));
        stringJoiner.add("location: " + this.location);
        stringJoiner.add("group: " + this.group);
        stringJoiner.add("indexInTheGroup: " + this.indexInTheGroup);
        stringJoiner.add("sensitivity: " + java.util.Objects.toString(this.sensitivity));
        stringJoiner.add("directionality: " + this.directionality);
        stringJoiner.add("frequencyResponse: " + java.util.Arrays.toString(this.frequencyResponse));
        stringJoiner.add("position: " + java.util.Objects.toString(this.position));
        stringJoiner.add("orientation: " + java.util.Objects.toString(this.orientation));
        return "MicrophoneInfo" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.MicrophoneInfo)) {
            return false;
        }
        android.media.audio.common.MicrophoneInfo microphoneInfo = (android.media.audio.common.MicrophoneInfo) obj;
        if (java.util.Objects.deepEquals(this.id, microphoneInfo.id) && java.util.Objects.deepEquals(this.device, microphoneInfo.device) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.location), java.lang.Integer.valueOf(microphoneInfo.location)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.group), java.lang.Integer.valueOf(microphoneInfo.group)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.indexInTheGroup), java.lang.Integer.valueOf(microphoneInfo.indexInTheGroup)) && java.util.Objects.deepEquals(this.sensitivity, microphoneInfo.sensitivity) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.directionality), java.lang.Integer.valueOf(microphoneInfo.directionality)) && java.util.Objects.deepEquals(this.frequencyResponse, microphoneInfo.frequencyResponse) && java.util.Objects.deepEquals(this.position, microphoneInfo.position) && java.util.Objects.deepEquals(this.orientation, microphoneInfo.orientation)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.id, this.device, java.lang.Integer.valueOf(this.location), java.lang.Integer.valueOf(this.group), java.lang.Integer.valueOf(this.indexInTheGroup), this.sensitivity, java.lang.Integer.valueOf(this.directionality), this.frequencyResponse, this.position, this.orientation).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.device) | 0 | describeContents(this.sensitivity) | describeContents(this.frequencyResponse) | describeContents(this.position) | describeContents(this.orientation);
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

    public static class Sensitivity implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.audio.common.MicrophoneInfo.Sensitivity> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.MicrophoneInfo.Sensitivity>() { // from class: android.media.audio.common.MicrophoneInfo.Sensitivity.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.MicrophoneInfo.Sensitivity createFromParcel(android.os.Parcel parcel) {
                android.media.audio.common.MicrophoneInfo.Sensitivity sensitivity = new android.media.audio.common.MicrophoneInfo.Sensitivity();
                sensitivity.readFromParcel(parcel);
                return sensitivity;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.MicrophoneInfo.Sensitivity[] newArray(int i) {
                return new android.media.audio.common.MicrophoneInfo.Sensitivity[i];
            }
        };
        public float leveldBFS = 0.0f;
        public float maxSpldB = 0.0f;
        public float minSpldB = 0.0f;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeFloat(this.leveldBFS);
            parcel.writeFloat(this.maxSpldB);
            parcel.writeFloat(this.minSpldB);
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
                this.leveldBFS = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.maxSpldB = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.minSpldB = parcel.readFloat();
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

    public static class FrequencyResponsePoint implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint>() { // from class: android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint createFromParcel(android.os.Parcel parcel) {
                android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint frequencyResponsePoint = new android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint();
                frequencyResponsePoint.readFromParcel(parcel);
                return frequencyResponsePoint;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint[] newArray(int i) {
                return new android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint[i];
            }
        };
        public float frequencyHz = 0.0f;
        public float leveldB = 0.0f;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeFloat(this.frequencyHz);
            parcel.writeFloat(this.leveldB);
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
                this.frequencyHz = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.leveldB = parcel.readFloat();
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

    public static class Coordinate implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.audio.common.MicrophoneInfo.Coordinate> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.MicrophoneInfo.Coordinate>() { // from class: android.media.audio.common.MicrophoneInfo.Coordinate.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.MicrophoneInfo.Coordinate createFromParcel(android.os.Parcel parcel) {
                android.media.audio.common.MicrophoneInfo.Coordinate coordinate = new android.media.audio.common.MicrophoneInfo.Coordinate();
                coordinate.readFromParcel(parcel);
                return coordinate;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.MicrophoneInfo.Coordinate[] newArray(int i) {
                return new android.media.audio.common.MicrophoneInfo.Coordinate[i];
            }
        };
        public float x = 0.0f;
        public float y = 0.0f;
        public float z = 0.0f;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeFloat(this.x);
            parcel.writeFloat(this.y);
            parcel.writeFloat(this.z);
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
                this.x = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.y = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.z = parcel.readFloat();
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
