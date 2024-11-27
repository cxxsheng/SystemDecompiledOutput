package android.hardware.gnss;

/* loaded from: classes2.dex */
public interface IGnssAntennaInfoCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$gnss$IGnssAntennaInfoCallback".replace('$', '.');
    public static final java.lang.String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void gnssAntennaInfoCb(android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo[] gnssAntennaInfoArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.gnss.IGnssAntennaInfoCallback {
        @Override // android.hardware.gnss.IGnssAntennaInfoCallback
        public void gnssAntennaInfoCb(android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo[] gnssAntennaInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssAntennaInfoCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssAntennaInfoCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.gnss.IGnssAntennaInfoCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_gnssAntennaInfoCb = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.gnss.IGnssAntennaInfoCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.IGnssAntennaInfoCallback)) {
                return (android.hardware.gnss.IGnssAntennaInfoCallback) queryLocalInterface;
            }
            return new android.hardware.gnss.IGnssAntennaInfoCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "gnssAntennaInfoCb";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo[] gnssAntennaInfoArr = (android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo[]) parcel.createTypedArray(android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    gnssAntennaInfoCb(gnssAntennaInfoArr);
                    parcel2.writeNoException();
                    break;
            }
            return true;
        }

        private static class Proxy implements android.hardware.gnss.IGnssAntennaInfoCallback {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.gnss.IGnssAntennaInfoCallback
            public void gnssAntennaInfoCb(android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo[] gnssAntennaInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(gnssAntennaInfoArr, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssAntennaInfoCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssAntennaInfoCallback
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.gnss.IGnssAntennaInfoCallback
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (java.lang.Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }

    public static class Row implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.IGnssAntennaInfoCallback.Row> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.IGnssAntennaInfoCallback.Row>() { // from class: android.hardware.gnss.IGnssAntennaInfoCallback.Row.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssAntennaInfoCallback.Row createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.IGnssAntennaInfoCallback.Row row = new android.hardware.gnss.IGnssAntennaInfoCallback.Row();
                row.readFromParcel(parcel);
                return row;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssAntennaInfoCallback.Row[] newArray(int i) {
                return new android.hardware.gnss.IGnssAntennaInfoCallback.Row[i];
            }
        };
        public double[] row;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeDoubleArray(this.row);
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
                } else {
                    this.row = parcel.createDoubleArray();
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

    public static class Coord implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.IGnssAntennaInfoCallback.Coord> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.IGnssAntennaInfoCallback.Coord>() { // from class: android.hardware.gnss.IGnssAntennaInfoCallback.Coord.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssAntennaInfoCallback.Coord createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.IGnssAntennaInfoCallback.Coord coord = new android.hardware.gnss.IGnssAntennaInfoCallback.Coord();
                coord.readFromParcel(parcel);
                return coord;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssAntennaInfoCallback.Coord[] newArray(int i) {
                return new android.hardware.gnss.IGnssAntennaInfoCallback.Coord[i];
            }
        };
        public double x = 0.0d;
        public double xUncertainty = 0.0d;
        public double y = 0.0d;
        public double yUncertainty = 0.0d;
        public double z = 0.0d;
        public double zUncertainty = 0.0d;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeDouble(this.x);
            parcel.writeDouble(this.xUncertainty);
            parcel.writeDouble(this.y);
            parcel.writeDouble(this.yUncertainty);
            parcel.writeDouble(this.z);
            parcel.writeDouble(this.zUncertainty);
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
                this.x = parcel.readDouble();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.xUncertainty = parcel.readDouble();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.y = parcel.readDouble();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.yUncertainty = parcel.readDouble();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.z = parcel.readDouble();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.zUncertainty = parcel.readDouble();
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

    public static class GnssAntennaInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo>() { // from class: android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo gnssAntennaInfo = new android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo();
                gnssAntennaInfo.readFromParcel(parcel);
                return gnssAntennaInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo[] newArray(int i) {
                return new android.hardware.gnss.IGnssAntennaInfoCallback.GnssAntennaInfo[i];
            }
        };
        public long carrierFrequencyHz = 0;
        public android.hardware.gnss.IGnssAntennaInfoCallback.Coord phaseCenterOffsetCoordinateMillimeters;
        public android.hardware.gnss.IGnssAntennaInfoCallback.Row[] phaseCenterVariationCorrectionMillimeters;
        public android.hardware.gnss.IGnssAntennaInfoCallback.Row[] phaseCenterVariationCorrectionUncertaintyMillimeters;
        public android.hardware.gnss.IGnssAntennaInfoCallback.Row[] signalGainCorrectionDbi;
        public android.hardware.gnss.IGnssAntennaInfoCallback.Row[] signalGainCorrectionUncertaintyDbi;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeLong(this.carrierFrequencyHz);
            parcel.writeTypedObject(this.phaseCenterOffsetCoordinateMillimeters, i);
            parcel.writeTypedArray(this.phaseCenterVariationCorrectionMillimeters, i);
            parcel.writeTypedArray(this.phaseCenterVariationCorrectionUncertaintyMillimeters, i);
            parcel.writeTypedArray(this.signalGainCorrectionDbi, i);
            parcel.writeTypedArray(this.signalGainCorrectionUncertaintyDbi, i);
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
                this.carrierFrequencyHz = parcel.readLong();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.phaseCenterOffsetCoordinateMillimeters = (android.hardware.gnss.IGnssAntennaInfoCallback.Coord) parcel.readTypedObject(android.hardware.gnss.IGnssAntennaInfoCallback.Coord.CREATOR);
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.phaseCenterVariationCorrectionMillimeters = (android.hardware.gnss.IGnssAntennaInfoCallback.Row[]) parcel.createTypedArray(android.hardware.gnss.IGnssAntennaInfoCallback.Row.CREATOR);
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.phaseCenterVariationCorrectionUncertaintyMillimeters = (android.hardware.gnss.IGnssAntennaInfoCallback.Row[]) parcel.createTypedArray(android.hardware.gnss.IGnssAntennaInfoCallback.Row.CREATOR);
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.signalGainCorrectionDbi = (android.hardware.gnss.IGnssAntennaInfoCallback.Row[]) parcel.createTypedArray(android.hardware.gnss.IGnssAntennaInfoCallback.Row.CREATOR);
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.signalGainCorrectionUncertaintyDbi = (android.hardware.gnss.IGnssAntennaInfoCallback.Row[]) parcel.createTypedArray(android.hardware.gnss.IGnssAntennaInfoCallback.Row.CREATOR);
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
            return describeContents(this.phaseCenterOffsetCoordinateMillimeters) | 0 | describeContents(this.phaseCenterVariationCorrectionMillimeters) | describeContents(this.phaseCenterVariationCorrectionUncertaintyMillimeters) | describeContents(this.signalGainCorrectionDbi) | describeContents(this.signalGainCorrectionUncertaintyDbi);
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
}
