package android.se.omapi;

/* loaded from: classes3.dex */
public interface ISecureElementService extends android.os.IInterface {
    public static final java.lang.String HASH = "894069bcfe4f35ceb2088278ddf87c83adee8014";
    public static final int VERSION = 1;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.se.omapi.ISecureElementReader getReader(java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] getReaders() throws android.os.RemoteException;

    boolean[] isNfcEventAllowed(java.lang.String str, byte[] bArr, java.lang.String[] strArr, int i) throws android.os.RemoteException;

    public static class Default implements android.se.omapi.ISecureElementService {
        @Override // android.se.omapi.ISecureElementService
        public java.lang.String[] getReaders() throws android.os.RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementService
        public android.se.omapi.ISecureElementReader getReader(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementService
        public boolean[] isNfcEventAllowed(java.lang.String str, byte[] bArr, java.lang.String[] strArr, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.se.omapi.ISecureElementService
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.se.omapi.ISecureElementService {
        public static final java.lang.String DESCRIPTOR = "android$se$omapi$ISecureElementService".replace('$', '.');
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getReader = 2;
        static final int TRANSACTION_getReaders = 1;
        static final int TRANSACTION_isNfcEventAllowed = 3;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.se.omapi.ISecureElementService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.se.omapi.ISecureElementService)) {
                return (android.se.omapi.ISecureElementService) queryLocalInterface;
            }
            return new android.se.omapi.ISecureElementService.Stub.Proxy(iBinder);
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
                    java.lang.String[] readers = getReaders();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(readers);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.se.omapi.ISecureElementReader reader = getReader(readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(reader);
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean[] isNfcEventAllowed = isNfcEventAllowed(readString2, createByteArray, createStringArray, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBooleanArray(isNfcEventAllowed);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.se.omapi.ISecureElementService {
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
                return android.se.omapi.ISecureElementService.Stub.DESCRIPTOR;
            }

            @Override // android.se.omapi.ISecureElementService
            public java.lang.String[] getReaders() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.se.omapi.ISecureElementService.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getReaders is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementService
            public android.se.omapi.ISecureElementReader getReader(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.se.omapi.ISecureElementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getReader is unimplemented.");
                    }
                    obtain2.readException();
                    return android.se.omapi.ISecureElementReader.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementService
            public boolean[] isNfcEventAllowed(java.lang.String str, byte[] bArr, java.lang.String[] strArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.se.omapi.ISecureElementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isNfcEventAllowed is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createBooleanArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementService
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.se.omapi.ISecureElementService.Stub.DESCRIPTOR);
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

            @Override // android.se.omapi.ISecureElementService
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.se.omapi.ISecureElementService.Stub.DESCRIPTOR);
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
