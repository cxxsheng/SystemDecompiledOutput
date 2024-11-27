package android.hardware.usb.gadget;

/* loaded from: classes2.dex */
public interface IUsbGadget extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$usb$gadget$IUsbGadget".replace('$', '.');
    public static final java.lang.String HASH = "cb628c69682659911bca5c1d04042adba7f0de4b";
    public static final int VERSION = 1;

    void getCurrentUsbFunctions(android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getUsbSpeed(android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j) throws android.os.RemoteException;

    void reset(android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j) throws android.os.RemoteException;

    void setCurrentUsbFunctions(long j, android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j2, long j3) throws android.os.RemoteException;

    public static class Default implements android.hardware.usb.gadget.IUsbGadget {
        @Override // android.hardware.usb.gadget.IUsbGadget
        public void setCurrentUsbFunctions(long j, android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j2, long j3) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadget
        public void getCurrentUsbFunctions(android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadget
        public void getUsbSpeed(android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadget
        public void reset(android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.usb.gadget.IUsbGadget
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.usb.gadget.IUsbGadget
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.usb.gadget.IUsbGadget {
        static final int TRANSACTION_getCurrentUsbFunctions = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getUsbSpeed = 3;
        static final int TRANSACTION_reset = 4;
        static final int TRANSACTION_setCurrentUsbFunctions = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.usb.gadget.IUsbGadget asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.usb.gadget.IUsbGadget)) {
                return (android.hardware.usb.gadget.IUsbGadget) queryLocalInterface;
            }
            return new android.hardware.usb.gadget.IUsbGadget.Stub.Proxy(iBinder);
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
                    long readLong = parcel.readLong();
                    android.hardware.usb.gadget.IUsbGadgetCallback asInterface = android.hardware.usb.gadget.IUsbGadgetCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong2 = parcel.readLong();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setCurrentUsbFunctions(readLong, asInterface, readLong2, readLong3);
                    return true;
                case 2:
                    android.hardware.usb.gadget.IUsbGadgetCallback asInterface2 = android.hardware.usb.gadget.IUsbGadgetCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    getCurrentUsbFunctions(asInterface2, readLong4);
                    return true;
                case 3:
                    android.hardware.usb.gadget.IUsbGadgetCallback asInterface3 = android.hardware.usb.gadget.IUsbGadgetCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    getUsbSpeed(asInterface3, readLong5);
                    return true;
                case 4:
                    android.hardware.usb.gadget.IUsbGadgetCallback asInterface4 = android.hardware.usb.gadget.IUsbGadgetCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    reset(asInterface4, readLong6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.usb.gadget.IUsbGadget {
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

            @Override // android.hardware.usb.gadget.IUsbGadget
            public void setCurrentUsbFunctions(long j, android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j2, long j3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iUsbGadgetCallback);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCurrentUsbFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadget
            public void getCurrentUsbFunctions(android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iUsbGadgetCallback);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCurrentUsbFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadget
            public void getUsbSpeed(android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iUsbGadgetCallback);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getUsbSpeed is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadget
            public void reset(android.hardware.usb.gadget.IUsbGadgetCallback iUsbGadgetCallback, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iUsbGadgetCallback);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method reset is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.usb.gadget.IUsbGadget
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

            @Override // android.hardware.usb.gadget.IUsbGadget
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
