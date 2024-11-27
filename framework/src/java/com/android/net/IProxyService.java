package com.android.net;

/* loaded from: classes5.dex */
public interface IProxyService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.net.IProxyService";

    java.lang.String resolvePacFile(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setPacFile(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.net.IProxyService {
        @Override // com.android.net.IProxyService
        public java.lang.String resolvePacFile(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.net.IProxyService
        public void setPacFile(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.net.IProxyService {
        static final int TRANSACTION_resolvePacFile = 1;
        static final int TRANSACTION_setPacFile = 2;

        public Stub() {
            attachInterface(this, "com.android.net.IProxyService");
        }

        public static com.android.net.IProxyService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.net.IProxyService");
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.net.IProxyService)) {
                return (com.android.net.IProxyService) queryLocalInterface;
            }
            return new com.android.net.IProxyService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "resolvePacFile";
                case 2:
                    return "setPacFile";
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
                parcel.enforceInterface("com.android.net.IProxyService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.net.IProxyService");
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String resolvePacFile = resolvePacFile(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeString(resolvePacFile);
                    return true;
                case 2:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPacFile(readString3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.net.IProxyService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return "com.android.net.IProxyService";
            }

            @Override // com.android.net.IProxyService
            public java.lang.String resolvePacFile(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void setPacFile(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
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
