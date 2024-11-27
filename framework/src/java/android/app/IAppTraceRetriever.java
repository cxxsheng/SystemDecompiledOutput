package android.app;

/* loaded from: classes.dex */
public interface IAppTraceRetriever extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IAppTraceRetriever";

    android.os.ParcelFileDescriptor getTraceFileDescriptor(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.app.IAppTraceRetriever {
        @Override // android.app.IAppTraceRetriever
        public android.os.ParcelFileDescriptor getTraceFileDescriptor(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IAppTraceRetriever {
        static final int TRANSACTION_getTraceFileDescriptor = 1;

        public Stub() {
            attachInterface(this, android.app.IAppTraceRetriever.DESCRIPTOR);
        }

        public static android.app.IAppTraceRetriever asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IAppTraceRetriever.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IAppTraceRetriever)) {
                return (android.app.IAppTraceRetriever) queryLocalInterface;
            }
            return new android.app.IAppTraceRetriever.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTraceFileDescriptor";
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
                parcel.enforceInterface(android.app.IAppTraceRetriever.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IAppTraceRetriever.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor traceFileDescriptor = getTraceFileDescriptor(readString, readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(traceFileDescriptor, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IAppTraceRetriever {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IAppTraceRetriever.DESCRIPTOR;
            }

            @Override // android.app.IAppTraceRetriever
            public android.os.ParcelFileDescriptor getTraceFileDescriptor(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IAppTraceRetriever.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
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
