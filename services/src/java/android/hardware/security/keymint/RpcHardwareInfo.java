package android.hardware.security.keymint;

/* loaded from: classes.dex */
public class RpcHardwareInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.security.keymint.RpcHardwareInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.security.keymint.RpcHardwareInfo>() { // from class: android.hardware.security.keymint.RpcHardwareInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.keymint.RpcHardwareInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.security.keymint.RpcHardwareInfo rpcHardwareInfo = new android.hardware.security.keymint.RpcHardwareInfo();
            rpcHardwareInfo.readFromParcel(parcel);
            return rpcHardwareInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.keymint.RpcHardwareInfo[] newArray(int i) {
            return new android.hardware.security.keymint.RpcHardwareInfo[i];
        }
    };
    public static final int CURVE_25519 = 2;
    public static final int CURVE_NONE = 0;
    public static final int CURVE_P256 = 1;
    public static final int MIN_SUPPORTED_NUM_KEYS_IN_CSR = 20;
    public java.lang.String rpcAuthorName;
    public java.lang.String uniqueId;
    public int versionNumber = 0;
    public int supportedEekCurve = 0;
    public int supportedNumKeysInCsr = 4;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.versionNumber);
        parcel.writeString(this.rpcAuthorName);
        parcel.writeInt(this.supportedEekCurve);
        parcel.writeString(this.uniqueId);
        parcel.writeInt(this.supportedNumKeysInCsr);
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
            this.versionNumber = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.rpcAuthorName = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.supportedEekCurve = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.uniqueId = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.supportedNumKeysInCsr = parcel.readInt();
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
