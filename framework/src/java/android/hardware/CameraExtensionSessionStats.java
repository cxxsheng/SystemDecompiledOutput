package android.hardware;

/* loaded from: classes.dex */
public class CameraExtensionSessionStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.CameraExtensionSessionStats> CREATOR = new android.os.Parcelable.Creator<android.hardware.CameraExtensionSessionStats>() { // from class: android.hardware.CameraExtensionSessionStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraExtensionSessionStats createFromParcel(android.os.Parcel parcel) {
            android.hardware.CameraExtensionSessionStats cameraExtensionSessionStats = new android.hardware.CameraExtensionSessionStats();
            cameraExtensionSessionStats.readFromParcel(parcel);
            return cameraExtensionSessionStats;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraExtensionSessionStats[] newArray(int i) {
            return new android.hardware.CameraExtensionSessionStats[i];
        }
    };
    public java.lang.String cameraId;
    public java.lang.String clientName;
    public java.lang.String key;
    public int type = -1;
    public boolean isAdvanced = false;

    public @interface Type {
        public static final int EXTENSION_AUTOMATIC = 0;
        public static final int EXTENSION_BOKEH = 2;
        public static final int EXTENSION_FACE_RETOUCH = 1;
        public static final int EXTENSION_HDR = 3;
        public static final int EXTENSION_NIGHT = 4;
        public static final int EXTENSION_NONE = -1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.key);
        parcel.writeString(this.cameraId);
        parcel.writeString(this.clientName);
        parcel.writeInt(this.type);
        parcel.writeBoolean(this.isAdvanced);
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
            this.key = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.cameraId = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.clientName = parcel.readString();
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
            } else {
                this.isAdvanced = parcel.readBoolean();
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
