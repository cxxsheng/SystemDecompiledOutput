package android.window;

/* loaded from: classes4.dex */
public interface IWindowOrganizerController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IWindowOrganizerController";

    int applySyncTransaction(android.window.WindowContainerTransaction windowContainerTransaction, android.window.IWindowContainerTransactionCallback iWindowContainerTransactionCallback) throws android.os.RemoteException;

    void applyTransaction(android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException;

    void finishTransition(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException;

    android.os.IBinder getApplyToken() throws android.os.RemoteException;

    android.window.IDisplayAreaOrganizerController getDisplayAreaOrganizerController() throws android.os.RemoteException;

    android.window.ITaskFragmentOrganizerController getTaskFragmentOrganizerController() throws android.os.RemoteException;

    android.window.ITaskOrganizerController getTaskOrganizerController() throws android.os.RemoteException;

    android.window.ITransitionMetricsReporter getTransitionMetricsReporter() throws android.os.RemoteException;

    void registerTransitionPlayer(android.window.ITransitionPlayer iTransitionPlayer) throws android.os.RemoteException;

    int startLegacyTransition(int i, android.view.RemoteAnimationAdapter remoteAnimationAdapter, android.window.IWindowContainerTransactionCallback iWindowContainerTransactionCallback, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException;

    android.os.IBinder startNewTransition(int i, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException;

    void startTransition(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException;

    public static class Default implements android.window.IWindowOrganizerController {
        @Override // android.window.IWindowOrganizerController
        public void applyTransaction(android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public int applySyncTransaction(android.window.WindowContainerTransaction windowContainerTransaction, android.window.IWindowContainerTransactionCallback iWindowContainerTransactionCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.window.IWindowOrganizerController
        public android.os.IBinder startNewTransition(int i, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public void startTransition(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public int startLegacyTransition(int i, android.view.RemoteAnimationAdapter remoteAnimationAdapter, android.window.IWindowContainerTransactionCallback iWindowContainerTransactionCallback, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.window.IWindowOrganizerController
        public void finishTransition(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public android.window.ITaskOrganizerController getTaskOrganizerController() throws android.os.RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public android.window.IDisplayAreaOrganizerController getDisplayAreaOrganizerController() throws android.os.RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public android.window.ITaskFragmentOrganizerController getTaskFragmentOrganizerController() throws android.os.RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public void registerTransitionPlayer(android.window.ITransitionPlayer iTransitionPlayer) throws android.os.RemoteException {
        }

        @Override // android.window.IWindowOrganizerController
        public android.window.ITransitionMetricsReporter getTransitionMetricsReporter() throws android.os.RemoteException {
            return null;
        }

        @Override // android.window.IWindowOrganizerController
        public android.os.IBinder getApplyToken() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IWindowOrganizerController {
        static final int TRANSACTION_applySyncTransaction = 2;
        static final int TRANSACTION_applyTransaction = 1;
        static final int TRANSACTION_finishTransition = 6;
        static final int TRANSACTION_getApplyToken = 12;
        static final int TRANSACTION_getDisplayAreaOrganizerController = 8;
        static final int TRANSACTION_getTaskFragmentOrganizerController = 9;
        static final int TRANSACTION_getTaskOrganizerController = 7;
        static final int TRANSACTION_getTransitionMetricsReporter = 11;
        static final int TRANSACTION_registerTransitionPlayer = 10;
        static final int TRANSACTION_startLegacyTransition = 5;
        static final int TRANSACTION_startNewTransition = 3;
        static final int TRANSACTION_startTransition = 4;

        public Stub() {
            attachInterface(this, android.window.IWindowOrganizerController.DESCRIPTOR);
        }

        public static android.window.IWindowOrganizerController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IWindowOrganizerController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IWindowOrganizerController)) {
                return (android.window.IWindowOrganizerController) queryLocalInterface;
            }
            return new android.window.IWindowOrganizerController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "applyTransaction";
                case 2:
                    return "applySyncTransaction";
                case 3:
                    return "startNewTransition";
                case 4:
                    return "startTransition";
                case 5:
                    return "startLegacyTransition";
                case 6:
                    return "finishTransition";
                case 7:
                    return "getTaskOrganizerController";
                case 8:
                    return "getDisplayAreaOrganizerController";
                case 9:
                    return "getTaskFragmentOrganizerController";
                case 10:
                    return "registerTransitionPlayer";
                case 11:
                    return "getTransitionMetricsReporter";
                case 12:
                    return "getApplyToken";
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
                parcel.enforceInterface(android.window.IWindowOrganizerController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IWindowOrganizerController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.window.WindowContainerTransaction windowContainerTransaction = (android.window.WindowContainerTransaction) parcel.readTypedObject(android.window.WindowContainerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyTransaction(windowContainerTransaction);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.window.WindowContainerTransaction windowContainerTransaction2 = (android.window.WindowContainerTransaction) parcel.readTypedObject(android.window.WindowContainerTransaction.CREATOR);
                    android.window.IWindowContainerTransactionCallback asInterface = android.window.IWindowContainerTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int applySyncTransaction = applySyncTransaction(windowContainerTransaction2, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(applySyncTransaction);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    android.window.WindowContainerTransaction windowContainerTransaction3 = (android.window.WindowContainerTransaction) parcel.readTypedObject(android.window.WindowContainerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.IBinder startNewTransition = startNewTransition(readInt, windowContainerTransaction3);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(startNewTransition);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.window.WindowContainerTransaction windowContainerTransaction4 = (android.window.WindowContainerTransaction) parcel.readTypedObject(android.window.WindowContainerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    startTransition(readStrongBinder, windowContainerTransaction4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    android.view.RemoteAnimationAdapter remoteAnimationAdapter = (android.view.RemoteAnimationAdapter) parcel.readTypedObject(android.view.RemoteAnimationAdapter.CREATOR);
                    android.window.IWindowContainerTransactionCallback asInterface2 = android.window.IWindowContainerTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.window.WindowContainerTransaction windowContainerTransaction5 = (android.window.WindowContainerTransaction) parcel.readTypedObject(android.window.WindowContainerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startLegacyTransition = startLegacyTransition(readInt2, remoteAnimationAdapter, asInterface2, windowContainerTransaction5);
                    parcel2.writeNoException();
                    parcel2.writeInt(startLegacyTransition);
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.window.WindowContainerTransaction windowContainerTransaction6 = (android.window.WindowContainerTransaction) parcel.readTypedObject(android.window.WindowContainerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    finishTransition(readStrongBinder2, windowContainerTransaction6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.window.ITaskOrganizerController taskOrganizerController = getTaskOrganizerController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(taskOrganizerController);
                    return true;
                case 8:
                    android.window.IDisplayAreaOrganizerController displayAreaOrganizerController = getDisplayAreaOrganizerController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(displayAreaOrganizerController);
                    return true;
                case 9:
                    android.window.ITaskFragmentOrganizerController taskFragmentOrganizerController = getTaskFragmentOrganizerController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(taskFragmentOrganizerController);
                    return true;
                case 10:
                    android.window.ITransitionPlayer asInterface3 = android.window.ITransitionPlayer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTransitionPlayer(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.window.ITransitionMetricsReporter transitionMetricsReporter = getTransitionMetricsReporter();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(transitionMetricsReporter);
                    return true;
                case 12:
                    android.os.IBinder applyToken = getApplyToken();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(applyToken);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IWindowOrganizerController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IWindowOrganizerController.DESCRIPTOR;
            }

            @Override // android.window.IWindowOrganizerController
            public void applyTransaction(android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public int applySyncTransaction(android.window.WindowContainerTransaction windowContainerTransaction, android.window.IWindowContainerTransactionCallback iWindowContainerTransactionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    obtain.writeStrongInterface(iWindowContainerTransactionCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public android.os.IBinder startNewTransition(int i, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void startTransition(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public int startLegacyTransition(int i, android.view.RemoteAnimationAdapter remoteAnimationAdapter, android.window.IWindowContainerTransactionCallback iWindowContainerTransactionCallback, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteAnimationAdapter, 0);
                    obtain.writeStrongInterface(iWindowContainerTransactionCallback);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void finishTransition(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public android.window.ITaskOrganizerController getTaskOrganizerController() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.window.ITaskOrganizerController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public android.window.IDisplayAreaOrganizerController getDisplayAreaOrganizerController() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.window.IDisplayAreaOrganizerController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public android.window.ITaskFragmentOrganizerController getTaskFragmentOrganizerController() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.window.ITaskFragmentOrganizerController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public void registerTransitionPlayer(android.window.ITransitionPlayer iTransitionPlayer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransitionPlayer);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public android.window.ITransitionMetricsReporter getTransitionMetricsReporter() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.window.ITransitionMetricsReporter.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IWindowOrganizerController
            public android.os.IBinder getApplyToken() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IWindowOrganizerController.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
