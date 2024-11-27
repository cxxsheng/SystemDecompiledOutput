package android.hardware.usb;

/* loaded from: classes.dex */
public interface IUsb extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$usb$IUsb".replace('$', '.');
    public static final java.lang.String HASH = "7fe46e9531884739d925b8caeee9dba5c411e228";
    public static final int VERSION = 3;

    void enableContaminantPresenceDetection(java.lang.String str, boolean z, long j) throws android.os.RemoteException;

    void enableUsbData(java.lang.String str, boolean z, long j) throws android.os.RemoteException;

    void enableUsbDataWhileDocked(java.lang.String str, long j) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void limitPowerTransfer(java.lang.String str, boolean z, long j) throws android.os.RemoteException;

    void queryPortStatus(long j) throws android.os.RemoteException;

    void resetUsbPort(java.lang.String str, long j) throws android.os.RemoteException;

    void setCallback(android.hardware.usb.IUsbCallback iUsbCallback) throws android.os.RemoteException;

    void switchRole(java.lang.String str, android.hardware.usb.PortRole portRole, long j) throws android.os.RemoteException;

    public static class Default implements android.hardware.usb.IUsb {
        @Override // android.hardware.usb.IUsb
        public void enableContaminantPresenceDetection(java.lang.String str, boolean z, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsb
        public void enableUsbData(java.lang.String str, boolean z, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsb
        public void enableUsbDataWhileDocked(java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsb
        public void queryPortStatus(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsb
        public void setCallback(android.hardware.usb.IUsbCallback iUsbCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsb
        public void switchRole(java.lang.String str, android.hardware.usb.PortRole portRole, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsb
        public void limitPowerTransfer(java.lang.String str, boolean z, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsb
        public void resetUsbPort(java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsb
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.usb.IUsb
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.usb.IUsb {
        static final int TRANSACTION_enableContaminantPresenceDetection = 1;
        static final int TRANSACTION_enableUsbData = 2;
        static final int TRANSACTION_enableUsbDataWhileDocked = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_limitPowerTransfer = 7;
        static final int TRANSACTION_queryPortStatus = 4;
        static final int TRANSACTION_resetUsbPort = 8;
        static final int TRANSACTION_setCallback = 5;
        static final int TRANSACTION_switchRole = 6;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.usb.IUsb.DESCRIPTOR);
        }

        public static android.hardware.usb.IUsb asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.usb.IUsb.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.usb.IUsb)) {
                return (android.hardware.usb.IUsb) queryLocalInterface;
            }
            return new android.hardware.usb.IUsb.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.usb.IUsb.DESCRIPTOR;
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
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    enableContaminantPresenceDetection(readString, readBoolean, readLong);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    enableUsbData(readString2, readBoolean2, readLong2);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    enableUsbDataWhileDocked(readString3, readLong3);
                    return true;
                case 4:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    queryPortStatus(readLong4);
                    return true;
                case 5:
                    android.hardware.usb.IUsbCallback asInterface = android.hardware.usb.IUsbCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    return true;
                case 6:
                    java.lang.String readString4 = parcel.readString();
                    android.hardware.usb.PortRole portRole = (android.hardware.usb.PortRole) parcel.readTypedObject(android.hardware.usb.PortRole.CREATOR);
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    switchRole(readString4, portRole, readLong5);
                    return true;
                case 7:
                    java.lang.String readString5 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    limitPowerTransfer(readString5, readBoolean3, readLong6);
                    return true;
                case 8:
                    java.lang.String readString6 = parcel.readString();
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    resetUsbPort(readString6, readLong7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.usb.IUsb {
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
                return android.hardware.usb.IUsb.DESCRIPTOR;
            }

            @Override // android.hardware.usb.IUsb
            public void enableContaminantPresenceDetection(java.lang.String str, boolean z, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method enableContaminantPresenceDetection is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void enableUsbData(java.lang.String str, boolean z, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method enableUsbData is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void enableUsbDataWhileDocked(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method enableUsbDataWhileDocked is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void queryPortStatus(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsb.DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method queryPortStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void setCallback(android.hardware.usb.IUsbCallback iUsbCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsb.DESCRIPTOR);
                    obtain.writeStrongInterface(iUsbCallback);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCallback is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void switchRole(java.lang.String str, android.hardware.usb.PortRole portRole, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(portRole, 0);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method switchRole is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void limitPowerTransfer(java.lang.String str, boolean z, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method limitPowerTransfer is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public void resetUsbPort(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsb.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method resetUsbPort is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsb
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.usb.IUsb.DESCRIPTOR);
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

            @Override // android.hardware.usb.IUsb
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.usb.IUsb.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.usb.IUsb.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}
