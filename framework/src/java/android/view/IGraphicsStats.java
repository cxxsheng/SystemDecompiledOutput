package android.view;

/* loaded from: classes4.dex */
public interface IGraphicsStats extends android.os.IInterface {
    android.os.ParcelFileDescriptor requestBufferForProcess(java.lang.String str, android.view.IGraphicsStatsCallback iGraphicsStatsCallback) throws android.os.RemoteException;

    public static class Default implements android.view.IGraphicsStats {
        @Override // android.view.IGraphicsStats
        public android.os.ParcelFileDescriptor requestBufferForProcess(java.lang.String str, android.view.IGraphicsStatsCallback iGraphicsStatsCallback) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IGraphicsStats {
        public static final java.lang.String DESCRIPTOR = "android.view.IGraphicsStats";
        static final int TRANSACTION_requestBufferForProcess = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.IGraphicsStats asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IGraphicsStats)) {
                return (android.view.IGraphicsStats) queryLocalInterface;
            }
            return new android.view.IGraphicsStats.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "requestBufferForProcess";
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
                    java.lang.String readString = parcel.readString();
                    android.view.IGraphicsStatsCallback asInterface = android.view.IGraphicsStatsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor requestBufferForProcess = requestBufferForProcess(readString, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(requestBufferForProcess, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IGraphicsStats {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IGraphicsStats.Stub.DESCRIPTOR;
            }

            @Override // android.view.IGraphicsStats
            public android.os.ParcelFileDescriptor requestBufferForProcess(java.lang.String str, android.view.IGraphicsStatsCallback iGraphicsStatsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IGraphicsStats.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iGraphicsStatsCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
