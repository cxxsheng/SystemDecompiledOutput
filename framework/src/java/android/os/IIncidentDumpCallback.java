package android.os;

/* loaded from: classes3.dex */
public interface IIncidentDumpCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IIncidentDumpCallback";

    void onDumpSection(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    public static class Default implements android.os.IIncidentDumpCallback {
        @Override // android.os.IIncidentDumpCallback
        public void onDumpSection(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IIncidentDumpCallback {
        static final int TRANSACTION_onDumpSection = 1;

        public Stub() {
            attachInterface(this, android.os.IIncidentDumpCallback.DESCRIPTOR);
        }

        public static android.os.IIncidentDumpCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IIncidentDumpCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IIncidentDumpCallback)) {
                return (android.os.IIncidentDumpCallback) queryLocalInterface;
            }
            return new android.os.IIncidentDumpCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDumpSection";
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
                parcel.enforceInterface(android.os.IIncidentDumpCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IIncidentDumpCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDumpSection(parcelFileDescriptor);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IIncidentDumpCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IIncidentDumpCallback.DESCRIPTOR;
            }

            @Override // android.os.IIncidentDumpCallback
            public void onDumpSection(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentDumpCallback.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
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
