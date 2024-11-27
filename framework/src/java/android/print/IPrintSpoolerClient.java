package android.print;

/* loaded from: classes3.dex */
public interface IPrintSpoolerClient extends android.os.IInterface {
    void onAllPrintJobsForServiceHandled(android.content.ComponentName componentName) throws android.os.RemoteException;

    void onAllPrintJobsHandled() throws android.os.RemoteException;

    void onPrintJobQueued(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException;

    void onPrintJobStateChanged(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException;

    public static class Default implements android.print.IPrintSpoolerClient {
        @Override // android.print.IPrintSpoolerClient
        public void onPrintJobQueued(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpoolerClient
        public void onAllPrintJobsForServiceHandled(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpoolerClient
        public void onAllPrintJobsHandled() throws android.os.RemoteException {
        }

        @Override // android.print.IPrintSpoolerClient
        public void onPrintJobStateChanged(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.print.IPrintSpoolerClient {
        public static final java.lang.String DESCRIPTOR = "android.print.IPrintSpoolerClient";
        static final int TRANSACTION_onAllPrintJobsForServiceHandled = 2;
        static final int TRANSACTION_onAllPrintJobsHandled = 3;
        static final int TRANSACTION_onPrintJobQueued = 1;
        static final int TRANSACTION_onPrintJobStateChanged = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.print.IPrintSpoolerClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.print.IPrintSpoolerClient)) {
                return (android.print.IPrintSpoolerClient) queryLocalInterface;
            }
            return new android.print.IPrintSpoolerClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPrintJobQueued";
                case 2:
                    return "onAllPrintJobsForServiceHandled";
                case 3:
                    return "onAllPrintJobsHandled";
                case 4:
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
                    android.print.PrintJobInfo printJobInfo = (android.print.PrintJobInfo) parcel.readTypedObject(android.print.PrintJobInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrintJobQueued(printJobInfo);
                    return true;
                case 2:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAllPrintJobsForServiceHandled(componentName);
                    return true;
                case 3:
                    onAllPrintJobsHandled();
                    return true;
                case 4:
                    android.print.PrintJobInfo printJobInfo2 = (android.print.PrintJobInfo) parcel.readTypedObject(android.print.PrintJobInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrintJobStateChanged(printJobInfo2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.print.IPrintSpoolerClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.print.IPrintSpoolerClient.Stub.DESCRIPTOR;
            }

            @Override // android.print.IPrintSpoolerClient
            public void onPrintJobQueued(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpoolerClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerClient
            public void onAllPrintJobsForServiceHandled(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpoolerClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerClient
            public void onAllPrintJobsHandled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpoolerClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerClient
            public void onPrintJobStateChanged(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.print.IPrintSpoolerClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
