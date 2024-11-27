package android.content.pm;

/* loaded from: classes.dex */
public interface IPackageInstallObserver2 extends android.os.IInterface {
    void onPackageInstalled(java.lang.String str, int i, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void onUserActionRequired(android.content.Intent intent) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IPackageInstallObserver2 {
        @Override // android.content.pm.IPackageInstallObserver2
        public void onUserActionRequired(android.content.Intent intent) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IPackageInstallObserver2
        public void onPackageInstalled(java.lang.String str, int i, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IPackageInstallObserver2 {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.IPackageInstallObserver2";
        static final int TRANSACTION_onPackageInstalled = 2;
        static final int TRANSACTION_onUserActionRequired = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.pm.IPackageInstallObserver2 asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IPackageInstallObserver2)) {
                return (android.content.pm.IPackageInstallObserver2) queryLocalInterface;
            }
            return new android.content.pm.IPackageInstallObserver2.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUserActionRequired";
                case 2:
                    return "onPackageInstalled";
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
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUserActionRequired(intent);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPackageInstalled(readString, readInt, readString2, bundle);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IPackageInstallObserver2 {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IPackageInstallObserver2.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageInstallObserver2
            public void onUserActionRequired(android.content.Intent intent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallObserver2.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallObserver2
            public void onPackageInstalled(java.lang.String str, int i, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallObserver2.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
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
