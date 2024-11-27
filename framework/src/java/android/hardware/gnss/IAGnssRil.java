package android.hardware.gnss;

/* loaded from: classes2.dex */
public interface IAGnssRil extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$gnss$IAGnssRil".replace('$', '.');
    public static final java.lang.String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int NETWORK_CAPABILITY_NOT_METERED = 1;
    public static final int NETWORK_CAPABILITY_NOT_ROAMING = 2;
    public static final int VERSION = 2;

    public @interface AGnssRefLocationType {
        public static final int GSM_CELLID = 1;
        public static final int LTE_CELLID = 4;
        public static final int NR_CELLID = 8;
        public static final int UMTS_CELLID = 2;
    }

    public @interface SetIdType {
        public static final int IMSI = 1;
        public static final int MSISDM = 2;
        public static final int NONE = 0;
    }

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void setCallback(android.hardware.gnss.IAGnssRilCallback iAGnssRilCallback) throws android.os.RemoteException;

    void setRefLocation(android.hardware.gnss.IAGnssRil.AGnssRefLocation aGnssRefLocation) throws android.os.RemoteException;

    void setSetId(int i, java.lang.String str) throws android.os.RemoteException;

    void updateNetworkState(android.hardware.gnss.IAGnssRil.NetworkAttributes networkAttributes) throws android.os.RemoteException;

    public static class Default implements android.hardware.gnss.IAGnssRil {
        @Override // android.hardware.gnss.IAGnssRil
        public void setCallback(android.hardware.gnss.IAGnssRilCallback iAGnssRilCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IAGnssRil
        public void setRefLocation(android.hardware.gnss.IAGnssRil.AGnssRefLocation aGnssRefLocation) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IAGnssRil
        public void setSetId(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IAGnssRil
        public void updateNetworkState(android.hardware.gnss.IAGnssRil.NetworkAttributes networkAttributes) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IAGnssRil
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IAGnssRil
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.gnss.IAGnssRil {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setRefLocation = 2;
        static final int TRANSACTION_setSetId = 3;
        static final int TRANSACTION_updateNetworkState = 4;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.gnss.IAGnssRil asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.IAGnssRil)) {
                return (android.hardware.gnss.IAGnssRil) queryLocalInterface;
            }
            return new android.hardware.gnss.IAGnssRil.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setCallback";
                case 2:
                    return "setRefLocation";
                case 3:
                    return "setSetId";
                case 4:
                    return "updateNetworkState";
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
                    android.hardware.gnss.IAGnssRilCallback asInterface = android.hardware.gnss.IAGnssRilCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.hardware.gnss.IAGnssRil.AGnssRefLocation aGnssRefLocation = (android.hardware.gnss.IAGnssRil.AGnssRefLocation) parcel.readTypedObject(android.hardware.gnss.IAGnssRil.AGnssRefLocation.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRefLocation(aGnssRefLocation);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSetId(readInt, readString);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.hardware.gnss.IAGnssRil.NetworkAttributes networkAttributes = (android.hardware.gnss.IAGnssRil.NetworkAttributes) parcel.readTypedObject(android.hardware.gnss.IAGnssRil.NetworkAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNetworkState(networkAttributes);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.gnss.IAGnssRil {
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

            @Override // android.hardware.gnss.IAGnssRil
            public void setCallback(android.hardware.gnss.IAGnssRilCallback iAGnssRilCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iAGnssRilCallback);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IAGnssRil
            public void setRefLocation(android.hardware.gnss.IAGnssRil.AGnssRefLocation aGnssRefLocation) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(aGnssRefLocation, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setRefLocation is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IAGnssRil
            public void setSetId(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setSetId is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IAGnssRil
            public void updateNetworkState(android.hardware.gnss.IAGnssRil.NetworkAttributes networkAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(networkAttributes, 0);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method updateNetworkState is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IAGnssRil
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

            @Override // android.hardware.gnss.IAGnssRil
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

    public static class AGnssRefLocationCellID implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID>() { // from class: android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID aGnssRefLocationCellID = new android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID();
                aGnssRefLocationCellID.readFromParcel(parcel);
                return aGnssRefLocationCellID;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID[] newArray(int i) {
                return new android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID[i];
            }
        };
        public int type;
        public int mcc = 0;
        public int mnc = 0;
        public int lac = 0;
        public long cid = 0;
        public int tac = 0;
        public int pcid = 0;
        public int arfcn = 0;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.type);
            parcel.writeInt(this.mcc);
            parcel.writeInt(this.mnc);
            parcel.writeInt(this.lac);
            parcel.writeLong(this.cid);
            parcel.writeInt(this.tac);
            parcel.writeInt(this.pcid);
            parcel.writeInt(this.arfcn);
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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.mcc = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.mnc = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.lac = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.cid = parcel.readLong();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.tac = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.pcid = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.arfcn = parcel.readInt();
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

    public static class AGnssRefLocation implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.IAGnssRil.AGnssRefLocation> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.IAGnssRil.AGnssRefLocation>() { // from class: android.hardware.gnss.IAGnssRil.AGnssRefLocation.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IAGnssRil.AGnssRefLocation createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.IAGnssRil.AGnssRefLocation aGnssRefLocation = new android.hardware.gnss.IAGnssRil.AGnssRefLocation();
                aGnssRefLocation.readFromParcel(parcel);
                return aGnssRefLocation;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IAGnssRil.AGnssRefLocation[] newArray(int i) {
                return new android.hardware.gnss.IAGnssRil.AGnssRefLocation[i];
            }
        };
        public android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID cellID;
        public int type;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.type);
            parcel.writeTypedObject(this.cellID, i);
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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.cellID = (android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID) parcel.readTypedObject(android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID.CREATOR);
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
            return describeContents(this.cellID) | 0;
        }

        private int describeContents(java.lang.Object obj) {
            if (obj == null || !(obj instanceof android.os.Parcelable)) {
                return 0;
            }
            return ((android.os.Parcelable) obj).describeContents();
        }
    }

    public static class NetworkAttributes implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.IAGnssRil.NetworkAttributes> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.IAGnssRil.NetworkAttributes>() { // from class: android.hardware.gnss.IAGnssRil.NetworkAttributes.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IAGnssRil.NetworkAttributes createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.IAGnssRil.NetworkAttributes networkAttributes = new android.hardware.gnss.IAGnssRil.NetworkAttributes();
                networkAttributes.readFromParcel(parcel);
                return networkAttributes;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IAGnssRil.NetworkAttributes[] newArray(int i) {
                return new android.hardware.gnss.IAGnssRil.NetworkAttributes[i];
            }
        };
        public java.lang.String apn;
        public long networkHandle = 0;
        public boolean isConnected = false;
        public int capabilities = 0;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeLong(this.networkHandle);
            parcel.writeBoolean(this.isConnected);
            parcel.writeInt(this.capabilities);
            parcel.writeString(this.apn);
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
                this.networkHandle = parcel.readLong();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.isConnected = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.capabilities = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.apn = parcel.readString();
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
