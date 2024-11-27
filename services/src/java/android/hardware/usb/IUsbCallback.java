package android.hardware.usb;

/* loaded from: classes.dex */
public interface IUsbCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$usb$IUsbCallback".replace('$', '.');
    public static final java.lang.String HASH = "7fe46e9531884739d925b8caeee9dba5c411e228";
    public static final int VERSION = 3;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void notifyContaminantEnabledStatus(java.lang.String str, boolean z, int i, long j) throws android.os.RemoteException;

    void notifyEnableUsbDataStatus(java.lang.String str, boolean z, int i, long j) throws android.os.RemoteException;

    void notifyEnableUsbDataWhileDockedStatus(java.lang.String str, int i, long j) throws android.os.RemoteException;

    void notifyLimitPowerTransferStatus(java.lang.String str, boolean z, int i, long j) throws android.os.RemoteException;

    void notifyPortStatusChange(android.hardware.usb.PortStatus[] portStatusArr, int i) throws android.os.RemoteException;

    void notifyQueryPortStatus(java.lang.String str, int i, long j) throws android.os.RemoteException;

    void notifyResetUsbPortStatus(java.lang.String str, int i, long j) throws android.os.RemoteException;

    void notifyRoleSwitchStatus(java.lang.String str, android.hardware.usb.PortRole portRole, int i, long j) throws android.os.RemoteException;

    public static class Default implements android.hardware.usb.IUsbCallback {
        @Override // android.hardware.usb.IUsbCallback
        public void notifyPortStatusChange(android.hardware.usb.PortStatus[] portStatusArr, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyRoleSwitchStatus(java.lang.String str, android.hardware.usb.PortRole portRole, int i, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyEnableUsbDataStatus(java.lang.String str, boolean z, int i, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyEnableUsbDataWhileDockedStatus(java.lang.String str, int i, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyContaminantEnabledStatus(java.lang.String str, boolean z, int i, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyQueryPortStatus(java.lang.String str, int i, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyLimitPowerTransferStatus(java.lang.String str, boolean z, int i, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbCallback
        public void notifyResetUsbPortStatus(java.lang.String str, int i, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.IUsbCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.usb.IUsbCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.usb.IUsbCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_notifyContaminantEnabledStatus = 5;
        static final int TRANSACTION_notifyEnableUsbDataStatus = 3;
        static final int TRANSACTION_notifyEnableUsbDataWhileDockedStatus = 4;
        static final int TRANSACTION_notifyLimitPowerTransferStatus = 7;
        static final int TRANSACTION_notifyPortStatusChange = 1;
        static final int TRANSACTION_notifyQueryPortStatus = 6;
        static final int TRANSACTION_notifyResetUsbPortStatus = 8;
        static final int TRANSACTION_notifyRoleSwitchStatus = 2;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.usb.IUsbCallback.DESCRIPTOR);
        }

        public static android.hardware.usb.IUsbCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.usb.IUsbCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.usb.IUsbCallback)) {
                return (android.hardware.usb.IUsbCallback) queryLocalInterface;
            }
            return new android.hardware.usb.IUsbCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.usb.IUsbCallback.DESCRIPTOR;
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
                    android.hardware.usb.PortStatus[] portStatusArr = (android.hardware.usb.PortStatus[]) parcel.createTypedArray(android.hardware.usb.PortStatus.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPortStatusChange(portStatusArr, readInt);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    android.hardware.usb.PortRole portRole = (android.hardware.usb.PortRole) parcel.readTypedObject(android.hardware.usb.PortRole.CREATOR);
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyRoleSwitchStatus(readString, portRole, readInt2, readLong);
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt3 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyEnableUsbDataStatus(readString2, readBoolean, readInt3, readLong2);
                    return true;
                case 4:
                    java.lang.String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyEnableUsbDataWhileDockedStatus(readString3, readInt4, readLong3);
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyContaminantEnabledStatus(readString4, readBoolean2, readInt5, readLong4);
                    return true;
                case 6:
                    java.lang.String readString5 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyQueryPortStatus(readString5, readInt6, readLong5);
                    return true;
                case 7:
                    java.lang.String readString6 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt7 = parcel.readInt();
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyLimitPowerTransferStatus(readString6, readBoolean3, readInt7, readLong6);
                    return true;
                case 8:
                    java.lang.String readString7 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyResetUsbPortStatus(readString7, readInt8, readLong7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.usb.IUsbCallback {
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
                return android.hardware.usb.IUsbCallback.DESCRIPTOR;
            }

            @Override // android.hardware.usb.IUsbCallback
            public void notifyPortStatusChange(android.hardware.usb.PortStatus[] portStatusArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbCallback.DESCRIPTOR);
                    obtain.writeTypedArray(portStatusArr, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyPortStatusChange is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbCallback
            public void notifyRoleSwitchStatus(java.lang.String str, android.hardware.usb.PortRole portRole, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(portRole, 0);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyRoleSwitchStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbCallback
            public void notifyEnableUsbDataStatus(java.lang.String str, boolean z, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyEnableUsbDataStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbCallback
            public void notifyEnableUsbDataWhileDockedStatus(java.lang.String str, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyEnableUsbDataWhileDockedStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbCallback
            public void notifyContaminantEnabledStatus(java.lang.String str, boolean z, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyContaminantEnabledStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbCallback
            public void notifyQueryPortStatus(java.lang.String str, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyQueryPortStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbCallback
            public void notifyLimitPowerTransferStatus(java.lang.String str, boolean z, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyLimitPowerTransferStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbCallback
            public void notifyResetUsbPortStatus(java.lang.String str, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.usb.IUsbCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method notifyResetUsbPortStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.IUsbCallback
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.usb.IUsbCallback.DESCRIPTOR);
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

            @Override // android.hardware.usb.IUsbCallback
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.usb.IUsbCallback.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.usb.IUsbCallback.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
