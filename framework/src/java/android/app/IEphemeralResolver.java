package android.app;

/* loaded from: classes.dex */
public interface IEphemeralResolver extends android.os.IInterface {
    void getEphemeralIntentFilterList(android.os.IRemoteCallback iRemoteCallback, java.lang.String str, int i) throws android.os.RemoteException;

    void getEphemeralResolveInfoList(android.os.IRemoteCallback iRemoteCallback, int[] iArr, int i) throws android.os.RemoteException;

    public static class Default implements android.app.IEphemeralResolver {
        @Override // android.app.IEphemeralResolver
        public void getEphemeralResolveInfoList(android.os.IRemoteCallback iRemoteCallback, int[] iArr, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IEphemeralResolver
        public void getEphemeralIntentFilterList(android.os.IRemoteCallback iRemoteCallback, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IEphemeralResolver {
        public static final java.lang.String DESCRIPTOR = "android.app.IEphemeralResolver";
        static final int TRANSACTION_getEphemeralIntentFilterList = 2;
        static final int TRANSACTION_getEphemeralResolveInfoList = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IEphemeralResolver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IEphemeralResolver)) {
                return (android.app.IEphemeralResolver) queryLocalInterface;
            }
            return new android.app.IEphemeralResolver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getEphemeralResolveInfoList";
                case 2:
                    return "getEphemeralIntentFilterList";
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
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IRemoteCallback asInterface = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int[] createIntArray = parcel.createIntArray();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getEphemeralResolveInfoList(asInterface, createIntArray, readInt);
                    return true;
                case 2:
                    android.os.IRemoteCallback asInterface2 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getEphemeralIntentFilterList(asInterface2, readString, readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IEphemeralResolver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IEphemeralResolver.Stub.DESCRIPTOR;
            }

            @Override // android.app.IEphemeralResolver
            public void getEphemeralResolveInfoList(android.os.IRemoteCallback iRemoteCallback, int[] iArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IEphemeralResolver.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IEphemeralResolver
            public void getEphemeralIntentFilterList(android.os.IRemoteCallback iRemoteCallback, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IEphemeralResolver.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
