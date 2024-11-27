package android.hardware.biometrics.fingerprint;

/* loaded from: classes.dex */
public class PointerContext implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.fingerprint.PointerContext> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.fingerprint.PointerContext>() { // from class: android.hardware.biometrics.fingerprint.PointerContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.fingerprint.PointerContext createFromParcel(android.os.Parcel parcel) {
            android.hardware.biometrics.fingerprint.PointerContext pointerContext = new android.hardware.biometrics.fingerprint.PointerContext();
            pointerContext.readFromParcel(parcel);
            return pointerContext;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.fingerprint.PointerContext[] newArray(int i) {
            return new android.hardware.biometrics.fingerprint.PointerContext[i];
        }
    };
    public int pointerId = -1;
    public float x = 0.0f;
    public float y = 0.0f;
    public float minor = 0.0f;
    public float major = 0.0f;
    public float orientation = 0.0f;
    public boolean isAod = false;
    public long time = 0;
    public long gestureStart = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.pointerId);
        parcel.writeFloat(this.x);
        parcel.writeFloat(this.y);
        parcel.writeFloat(this.minor);
        parcel.writeFloat(this.major);
        parcel.writeFloat(this.orientation);
        parcel.writeBoolean(this.isAod);
        parcel.writeLong(this.time);
        parcel.writeLong(this.gestureStart);
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
            this.pointerId = parcel.readInt();
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
                return;
            }
            this.minor = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.major = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.orientation = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.isAod = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.time = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.gestureStart = parcel.readLong();
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

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.biometrics.fingerprint.PointerContext)) {
            return false;
        }
        android.hardware.biometrics.fingerprint.PointerContext pointerContext = (android.hardware.biometrics.fingerprint.PointerContext) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.pointerId), java.lang.Integer.valueOf(pointerContext.pointerId)) && java.util.Objects.deepEquals(java.lang.Float.valueOf(this.x), java.lang.Float.valueOf(pointerContext.x)) && java.util.Objects.deepEquals(java.lang.Float.valueOf(this.y), java.lang.Float.valueOf(pointerContext.y)) && java.util.Objects.deepEquals(java.lang.Float.valueOf(this.minor), java.lang.Float.valueOf(pointerContext.minor)) && java.util.Objects.deepEquals(java.lang.Float.valueOf(this.major), java.lang.Float.valueOf(pointerContext.major)) && java.util.Objects.deepEquals(java.lang.Float.valueOf(this.orientation), java.lang.Float.valueOf(pointerContext.orientation)) && java.util.Objects.deepEquals(java.lang.Boolean.valueOf(this.isAod), java.lang.Boolean.valueOf(pointerContext.isAod)) && java.util.Objects.deepEquals(java.lang.Long.valueOf(this.time), java.lang.Long.valueOf(pointerContext.time)) && java.util.Objects.deepEquals(java.lang.Long.valueOf(this.gestureStart), java.lang.Long.valueOf(pointerContext.gestureStart))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.pointerId), java.lang.Float.valueOf(this.x), java.lang.Float.valueOf(this.y), java.lang.Float.valueOf(this.minor), java.lang.Float.valueOf(this.major), java.lang.Float.valueOf(this.orientation), java.lang.Boolean.valueOf(this.isAod), java.lang.Long.valueOf(this.time), java.lang.Long.valueOf(this.gestureStart)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
