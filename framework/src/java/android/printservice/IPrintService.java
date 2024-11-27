package android.printservice;

/* loaded from: classes3.dex */
public interface IPrintService extends android.os.IInterface {
    void createPrinterDiscoverySession() throws android.os.RemoteException;

    void destroyPrinterDiscoverySession() throws android.os.RemoteException;

    void onPrintJobQueued(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException;

    void requestCancelPrintJob(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException;

    void requestCustomPrinterIcon(android.print.PrinterId printerId) throws android.os.RemoteException;

    void setClient(android.printservice.IPrintServiceClient iPrintServiceClient) throws android.os.RemoteException;

    void startPrinterDiscovery(java.util.List<android.print.PrinterId> list) throws android.os.RemoteException;

    void startPrinterStateTracking(android.print.PrinterId printerId) throws android.os.RemoteException;

    void stopPrinterDiscovery() throws android.os.RemoteException;

    void stopPrinterStateTracking(android.print.PrinterId printerId) throws android.os.RemoteException;

    void validatePrinters(java.util.List<android.print.PrinterId> list) throws android.os.RemoteException;

    public static class Default implements android.printservice.IPrintService {
        @Override // android.printservice.IPrintService
        public void setClient(android.printservice.IPrintServiceClient iPrintServiceClient) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void requestCancelPrintJob(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void onPrintJobQueued(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void createPrinterDiscoverySession() throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void startPrinterDiscovery(java.util.List<android.print.PrinterId> list) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void stopPrinterDiscovery() throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void validatePrinters(java.util.List<android.print.PrinterId> list) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void startPrinterStateTracking(android.print.PrinterId printerId) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void requestCustomPrinterIcon(android.print.PrinterId printerId) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void stopPrinterStateTracking(android.print.PrinterId printerId) throws android.os.RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void destroyPrinterDiscoverySession() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.printservice.IPrintService {
        public static final java.lang.String DESCRIPTOR = "android.printservice.IPrintService";
        static final int TRANSACTION_createPrinterDiscoverySession = 4;
        static final int TRANSACTION_destroyPrinterDiscoverySession = 11;
        static final int TRANSACTION_onPrintJobQueued = 3;
        static final int TRANSACTION_requestCancelPrintJob = 2;
        static final int TRANSACTION_requestCustomPrinterIcon = 9;
        static final int TRANSACTION_setClient = 1;
        static final int TRANSACTION_startPrinterDiscovery = 5;
        static final int TRANSACTION_startPrinterStateTracking = 8;
        static final int TRANSACTION_stopPrinterDiscovery = 6;
        static final int TRANSACTION_stopPrinterStateTracking = 10;
        static final int TRANSACTION_validatePrinters = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.printservice.IPrintService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.printservice.IPrintService)) {
                return (android.printservice.IPrintService) queryLocalInterface;
            }
            return new android.printservice.IPrintService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setClient";
                case 2:
                    return "requestCancelPrintJob";
                case 3:
                    return "onPrintJobQueued";
                case 4:
                    return "createPrinterDiscoverySession";
                case 5:
                    return "startPrinterDiscovery";
                case 6:
                    return "stopPrinterDiscovery";
                case 7:
                    return "validatePrinters";
                case 8:
                    return "startPrinterStateTracking";
                case 9:
                    return "requestCustomPrinterIcon";
                case 10:
                    return "stopPrinterStateTracking";
                case 11:
                    return "destroyPrinterDiscoverySession";
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
                    android.printservice.IPrintServiceClient asInterface = android.printservice.IPrintServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setClient(asInterface);
                    return true;
                case 2:
                    android.print.PrintJobInfo printJobInfo = (android.print.PrintJobInfo) parcel.readTypedObject(android.print.PrintJobInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCancelPrintJob(printJobInfo);
                    return true;
                case 3:
                    android.print.PrintJobInfo printJobInfo2 = (android.print.PrintJobInfo) parcel.readTypedObject(android.print.PrintJobInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrintJobQueued(printJobInfo2);
                    return true;
                case 4:
                    createPrinterDiscoverySession();
                    return true;
                case 5:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.print.PrinterId.CREATOR);
                    parcel.enforceNoDataAvail();
                    startPrinterDiscovery(createTypedArrayList);
                    return true;
                case 6:
                    stopPrinterDiscovery();
                    return true;
                case 7:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.print.PrinterId.CREATOR);
                    parcel.enforceNoDataAvail();
                    validatePrinters(createTypedArrayList2);
                    return true;
                case 8:
                    android.print.PrinterId printerId = (android.print.PrinterId) parcel.readTypedObject(android.print.PrinterId.CREATOR);
                    parcel.enforceNoDataAvail();
                    startPrinterStateTracking(printerId);
                    return true;
                case 9:
                    android.print.PrinterId printerId2 = (android.print.PrinterId) parcel.readTypedObject(android.print.PrinterId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCustomPrinterIcon(printerId2);
                    return true;
                case 10:
                    android.print.PrinterId printerId3 = (android.print.PrinterId) parcel.readTypedObject(android.print.PrinterId.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopPrinterStateTracking(printerId3);
                    return true;
                case 11:
                    destroyPrinterDiscoverySession();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.printservice.IPrintService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.printservice.IPrintService.Stub.DESCRIPTOR;
            }

            @Override // android.printservice.IPrintService
            public void setClient(android.printservice.IPrintServiceClient iPrintServiceClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintServiceClient);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void requestCancelPrintJob(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void onPrintJobQueued(android.print.PrintJobInfo printJobInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobInfo, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void createPrinterDiscoverySession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintService.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void startPrinterDiscovery(java.util.List<android.print.PrinterId> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintService.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void stopPrinterDiscovery() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintService.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void validatePrinters(java.util.List<android.print.PrinterId> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintService.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void startPrinterStateTracking(android.print.PrinterId printerId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void requestCustomPrinterIcon(android.print.PrinterId printerId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void stopPrinterStateTracking(android.print.PrinterId printerId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void destroyPrinterDiscoverySession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.printservice.IPrintService.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
