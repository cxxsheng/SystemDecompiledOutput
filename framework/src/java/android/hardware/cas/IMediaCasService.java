package android.hardware.cas;

/* loaded from: classes.dex */
public interface IMediaCasService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$cas$IMediaCasService".replace('$', '.');
    public static final java.lang.String HASH = "bc51d8d70a55ec4723d3f73d0acf7003306bf69f";
    public static final int VERSION = 1;

    android.hardware.cas.IDescrambler createDescrambler(int i) throws android.os.RemoteException;

    android.hardware.cas.ICas createPlugin(int i, android.hardware.cas.ICasListener iCasListener) throws android.os.RemoteException;

    android.hardware.cas.AidlCasPluginDescriptor[] enumeratePlugins() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    boolean isDescramblerSupported(int i) throws android.os.RemoteException;

    boolean isSystemIdSupported(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.cas.IMediaCasService {
        @Override // android.hardware.cas.IMediaCasService
        public android.hardware.cas.IDescrambler createDescrambler(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.cas.IMediaCasService
        public android.hardware.cas.ICas createPlugin(int i, android.hardware.cas.ICasListener iCasListener) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.cas.IMediaCasService
        public android.hardware.cas.AidlCasPluginDescriptor[] enumeratePlugins() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.cas.IMediaCasService
        public boolean isDescramblerSupported(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.cas.IMediaCasService
        public boolean isSystemIdSupported(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.cas.IMediaCasService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.cas.IMediaCasService
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.cas.IMediaCasService {
        static final int TRANSACTION_createDescrambler = 1;
        static final int TRANSACTION_createPlugin = 2;
        static final int TRANSACTION_enumeratePlugins = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_isDescramblerSupported = 4;
        static final int TRANSACTION_isSystemIdSupported = 5;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.cas.IMediaCasService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.cas.IMediaCasService)) {
                return (android.hardware.cas.IMediaCasService) queryLocalInterface;
            }
            return new android.hardware.cas.IMediaCasService.Stub.Proxy(iBinder);
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
                    parcel.enforceNoDataAvail();
                    android.hardware.cas.IDescrambler createDescrambler = createDescrambler(readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createDescrambler);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.hardware.cas.ICasListener asInterface = android.hardware.cas.ICasListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.hardware.cas.ICas createPlugin = createPlugin(readInt2, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createPlugin);
                    return true;
                case 3:
                    android.hardware.cas.AidlCasPluginDescriptor[] enumeratePlugins = enumeratePlugins();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(enumeratePlugins, 1);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDescramblerSupported = isDescramblerSupported(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDescramblerSupported);
                    return true;
                case 5:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSystemIdSupported = isSystemIdSupported(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSystemIdSupported);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.cas.IMediaCasService {
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

            @Override // android.hardware.cas.IMediaCasService
            public android.hardware.cas.IDescrambler createDescrambler(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method createDescrambler is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.cas.IDescrambler.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public android.hardware.cas.ICas createPlugin(int i, android.hardware.cas.ICasListener iCasListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCasListener);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method createPlugin is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.cas.ICas.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public android.hardware.cas.AidlCasPluginDescriptor[] enumeratePlugins() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method enumeratePlugins is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.cas.AidlCasPluginDescriptor[]) obtain2.createTypedArray(android.hardware.cas.AidlCasPluginDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public boolean isDescramblerSupported(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isDescramblerSupported is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
            public boolean isSystemIdSupported(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isSystemIdSupported is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IMediaCasService
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

            @Override // android.hardware.cas.IMediaCasService
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
