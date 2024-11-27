package android.os;

/* loaded from: classes3.dex */
public interface IIncidentAuthListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IIncidentAuthListener";

    void onReportApproved() throws android.os.RemoteException;

    void onReportDenied() throws android.os.RemoteException;

    public static class Default implements android.os.IIncidentAuthListener {
        @Override // android.os.IIncidentAuthListener
        public void onReportApproved() throws android.os.RemoteException {
        }

        @Override // android.os.IIncidentAuthListener
        public void onReportDenied() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IIncidentAuthListener {
        static final int TRANSACTION_onReportApproved = 1;
        static final int TRANSACTION_onReportDenied = 2;

        public Stub() {
            attachInterface(this, android.os.IIncidentAuthListener.DESCRIPTOR);
        }

        public static android.os.IIncidentAuthListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IIncidentAuthListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IIncidentAuthListener)) {
                return (android.os.IIncidentAuthListener) queryLocalInterface;
            }
            return new android.os.IIncidentAuthListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onReportApproved";
                case 2:
                    return "onReportDenied";
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
                parcel.enforceInterface(android.os.IIncidentAuthListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IIncidentAuthListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onReportApproved();
                    return true;
                case 2:
                    onReportDenied();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IIncidentAuthListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IIncidentAuthListener.DESCRIPTOR;
            }

            @Override // android.os.IIncidentAuthListener
            public void onReportApproved() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentAuthListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentAuthListener
            public void onReportDenied() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IIncidentAuthListener.DESCRIPTOR);
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
