package android.frameworks.vibrator;

/* loaded from: classes.dex */
public class ScaleParam implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.frameworks.vibrator.ScaleParam> CREATOR = new android.os.Parcelable.Creator<android.frameworks.vibrator.ScaleParam>() { // from class: android.frameworks.vibrator.ScaleParam.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.frameworks.vibrator.ScaleParam createFromParcel(android.os.Parcel parcel) {
            android.frameworks.vibrator.ScaleParam scaleParam = new android.frameworks.vibrator.ScaleParam();
            scaleParam.readFromParcel(parcel);
            return scaleParam;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.frameworks.vibrator.ScaleParam[] newArray(int i) {
            return new android.frameworks.vibrator.ScaleParam[i];
        }
    };
    public static final int TYPE_ALARM = 1;
    public static final int TYPE_INTERACTIVE = 8;
    public static final int TYPE_MEDIA = 16;
    public static final int TYPE_NOTIFICATION = 2;
    public static final int TYPE_RINGTONE = 4;
    public int typesMask = 0;
    public float scale = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.typesMask);
        parcel.writeFloat(this.scale);
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
            this.typesMask = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.scale = parcel.readFloat();
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
