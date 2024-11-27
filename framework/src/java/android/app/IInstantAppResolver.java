package android.app;

/* loaded from: classes.dex */
public interface IInstantAppResolver extends android.os.IInterface {
    void getInstantAppIntentFilterList(android.content.pm.InstantAppRequestInfo instantAppRequestInfo, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    void getInstantAppResolveInfoList(android.content.pm.InstantAppRequestInfo instantAppRequestInfo, int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    public static class Default implements android.app.IInstantAppResolver {
        @Override // android.app.IInstantAppResolver
        public void getInstantAppResolveInfoList(android.content.pm.InstantAppRequestInfo instantAppRequestInfo, int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IInstantAppResolver
        public void getInstantAppIntentFilterList(android.content.pm.InstantAppRequestInfo instantAppRequestInfo, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IInstantAppResolver {
        public static final java.lang.String DESCRIPTOR = "android.app.IInstantAppResolver";
        static final int TRANSACTION_getInstantAppIntentFilterList = 2;
        static final int TRANSACTION_getInstantAppResolveInfoList = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IInstantAppResolver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IInstantAppResolver)) {
                return (android.app.IInstantAppResolver) queryLocalInterface;
            }
            return new android.app.IInstantAppResolver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getInstantAppResolveInfoList";
                case 2:
                    return "getInstantAppIntentFilterList";
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
                    android.content.pm.InstantAppRequestInfo instantAppRequestInfo = (android.content.pm.InstantAppRequestInfo) parcel.readTypedObject(android.content.pm.InstantAppRequestInfo.CREATOR);
                    int readInt = parcel.readInt();
                    android.os.IRemoteCallback asInterface = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getInstantAppResolveInfoList(instantAppRequestInfo, readInt, asInterface);
                    return true;
                case 2:
                    android.content.pm.InstantAppRequestInfo instantAppRequestInfo2 = (android.content.pm.InstantAppRequestInfo) parcel.readTypedObject(android.content.pm.InstantAppRequestInfo.CREATOR);
                    android.os.IRemoteCallback asInterface2 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getInstantAppIntentFilterList(instantAppRequestInfo2, asInterface2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IInstantAppResolver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IInstantAppResolver.Stub.DESCRIPTOR;
            }

            @Override // android.app.IInstantAppResolver
            public void getInstantAppResolveInfoList(android.content.pm.InstantAppRequestInfo instantAppRequestInfo, int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IInstantAppResolver.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(instantAppRequestInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IInstantAppResolver
            public void getInstantAppIntentFilterList(android.content.pm.InstantAppRequestInfo instantAppRequestInfo, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IInstantAppResolver.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(instantAppRequestInfo, 0);
                    obtain.writeStrongInterface(iRemoteCallback);
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
