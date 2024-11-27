package android.print;

/* loaded from: classes3.dex */
public interface IPrintJobStateChangeListener extends android.os.IInterface {
    void onPrintJobStateChanged(android.print.PrintJobId printJobId) throws android.os.RemoteException;

    public static class Default implements android.print.IPrintJobStateChangeListener {
        @Override // android.print.IPrintJobStateChangeListener
        public void onPrintJobStateChanged(android.print.PrintJobId printJobId) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.print.IPrintJobStateChangeListener {
        public static final java.lang.String DESCRIPTOR = "android.print.IPrintJobStateChangeListener";
        static final int TRANSACTION_onPrintJobStateChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.print.IPrintJobStateChangeListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.print.IPrintJobStateChangeListener)) {
                return (android.print.IPrintJobStateChangeListener) queryLocalInterface;
            }
            return new android.print.IPrintJobStateChangeListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPrintJobStateChanged";
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
                    android.print.PrintJobId printJobId = (android.print.PrintJobId) parcel.readTypedObject(android.print.PrintJobId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrintJobStateChanged(printJobId);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.print.IPrintJobStateChangeListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.print.IPrintJobStateChangeListener.Stub.DESCRIPTOR;
            }

            @Override // android.print.IPrintJobStateChangeListener
            public void onPrintJobStateChanged(android.print.PrintJobId printJobId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintJobStateChangeListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
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
