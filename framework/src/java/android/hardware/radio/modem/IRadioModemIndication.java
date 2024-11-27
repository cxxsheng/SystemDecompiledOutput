package android.hardware.radio.modem;

/* loaded from: classes2.dex */
public interface IRadioModemIndication extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$modem$IRadioModemIndication".replace('$', '.');
    public static final java.lang.String HASH = "8586a5528f0085c15cff4b6628f1b8153aca29ad";
    public static final int VERSION = 3;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void hardwareConfigChanged(int i, android.hardware.radio.modem.HardwareConfig[] hardwareConfigArr) throws android.os.RemoteException;

    void modemReset(int i, java.lang.String str) throws android.os.RemoteException;

    void onImeiMappingChanged(int i, android.hardware.radio.modem.ImeiInfo imeiInfo) throws android.os.RemoteException;

    void radioCapabilityIndication(int i, android.hardware.radio.modem.RadioCapability radioCapability) throws android.os.RemoteException;

    void radioStateChanged(int i, int i2) throws android.os.RemoteException;

    void rilConnected(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.modem.IRadioModemIndication {
        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void hardwareConfigChanged(int i, android.hardware.radio.modem.HardwareConfig[] hardwareConfigArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void modemReset(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void radioCapabilityIndication(int i, android.hardware.radio.modem.RadioCapability radioCapability) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void radioStateChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void rilConnected(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public void onImeiMappingChanged(int i, android.hardware.radio.modem.ImeiInfo imeiInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.modem.IRadioModemIndication
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.modem.IRadioModemIndication {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_hardwareConfigChanged = 1;
        static final int TRANSACTION_modemReset = 2;
        static final int TRANSACTION_onImeiMappingChanged = 6;
        static final int TRANSACTION_radioCapabilityIndication = 3;
        static final int TRANSACTION_radioStateChanged = 4;
        static final int TRANSACTION_rilConnected = 5;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.modem.IRadioModemIndication asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.modem.IRadioModemIndication)) {
                return (android.hardware.radio.modem.IRadioModemIndication) queryLocalInterface;
            }
            return new android.hardware.radio.modem.IRadioModemIndication.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
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
                    int readInt = parcel.readInt();
                    android.hardware.radio.modem.HardwareConfig[] hardwareConfigArr = (android.hardware.radio.modem.HardwareConfig[]) parcel.createTypedArray(android.hardware.radio.modem.HardwareConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    hardwareConfigChanged(readInt, hardwareConfigArr);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    modemReset(readInt2, readString);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    android.hardware.radio.modem.RadioCapability radioCapability = (android.hardware.radio.modem.RadioCapability) parcel.readTypedObject(android.hardware.radio.modem.RadioCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    radioCapabilityIndication(readInt3, radioCapability);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    radioStateChanged(readInt4, readInt5);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    rilConnected(readInt6);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    android.hardware.radio.modem.ImeiInfo imeiInfo = (android.hardware.radio.modem.ImeiInfo) parcel.readTypedObject(android.hardware.radio.modem.ImeiInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onImeiMappingChanged(readInt7, imeiInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.modem.IRadioModemIndication {
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

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void hardwareConfigChanged(int i, android.hardware.radio.modem.HardwareConfig[] hardwareConfigArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(hardwareConfigArr, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method hardwareConfigChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void modemReset(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method modemReset is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void radioCapabilityIndication(int i, android.hardware.radio.modem.RadioCapability radioCapability) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(radioCapability, 0);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method radioCapabilityIndication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void radioStateChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method radioStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void rilConnected(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method rilConnected is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
            public void onImeiMappingChanged(int i, android.hardware.radio.modem.ImeiInfo imeiInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imeiInfo, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onImeiMappingChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.modem.IRadioModemIndication
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

            @Override // android.hardware.radio.modem.IRadioModemIndication
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
    }
}
