package android.window;

/* loaded from: classes4.dex */
public interface ITaskFragmentOrganizerController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.ITaskFragmentOrganizerController";

    void applyTransaction(android.window.WindowContainerTransaction windowContainerTransaction, int i, boolean z, android.window.RemoteTransition remoteTransition) throws android.os.RemoteException;

    boolean isActivityEmbedded(android.os.IBinder iBinder) throws android.os.RemoteException;

    void onTransactionHandled(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction, int i, boolean z) throws android.os.RemoteException;

    void registerOrganizer(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, boolean z) throws android.os.RemoteException;

    void registerRemoteAnimations(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, android.view.RemoteAnimationDefinition remoteAnimationDefinition) throws android.os.RemoteException;

    void unregisterOrganizer(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) throws android.os.RemoteException;

    void unregisterRemoteAnimations(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) throws android.os.RemoteException;

    public static class Default implements android.window.ITaskFragmentOrganizerController {
        @Override // android.window.ITaskFragmentOrganizerController
        public void registerOrganizer(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, boolean z) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public void unregisterOrganizer(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public void registerRemoteAnimations(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, android.view.RemoteAnimationDefinition remoteAnimationDefinition) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public void unregisterRemoteAnimations(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public boolean isActivityEmbedded(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public void onTransactionHandled(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public void applyTransaction(android.window.WindowContainerTransaction windowContainerTransaction, int i, boolean z, android.window.RemoteTransition remoteTransition) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.ITaskFragmentOrganizerController {
        static final int TRANSACTION_applyTransaction = 7;
        static final int TRANSACTION_isActivityEmbedded = 5;
        static final int TRANSACTION_onTransactionHandled = 6;
        static final int TRANSACTION_registerOrganizer = 1;
        static final int TRANSACTION_registerRemoteAnimations = 3;
        static final int TRANSACTION_unregisterOrganizer = 2;
        static final int TRANSACTION_unregisterRemoteAnimations = 4;

        public Stub() {
            attachInterface(this, android.window.ITaskFragmentOrganizerController.DESCRIPTOR);
        }

        public static android.window.ITaskFragmentOrganizerController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.ITaskFragmentOrganizerController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.ITaskFragmentOrganizerController)) {
                return (android.window.ITaskFragmentOrganizerController) queryLocalInterface;
            }
            return new android.window.ITaskFragmentOrganizerController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerOrganizer";
                case 2:
                    return "unregisterOrganizer";
                case 3:
                    return "registerRemoteAnimations";
                case 4:
                    return "unregisterRemoteAnimations";
                case 5:
                    return "isActivityEmbedded";
                case 6:
                    return "onTransactionHandled";
                case 7:
                    return "applyTransaction";
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
                parcel.enforceInterface(android.window.ITaskFragmentOrganizerController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.ITaskFragmentOrganizerController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.window.ITaskFragmentOrganizer asInterface = android.window.ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerOrganizer(asInterface, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.window.ITaskFragmentOrganizer asInterface2 = android.window.ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterOrganizer(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.window.ITaskFragmentOrganizer asInterface3 = android.window.ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    android.view.RemoteAnimationDefinition remoteAnimationDefinition = (android.view.RemoteAnimationDefinition) parcel.readTypedObject(android.view.RemoteAnimationDefinition.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimations(asInterface3, remoteAnimationDefinition);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.window.ITaskFragmentOrganizer asInterface4 = android.window.ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRemoteAnimations(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isActivityEmbedded = isActivityEmbedded(readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivityEmbedded);
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.window.WindowContainerTransaction windowContainerTransaction = (android.window.WindowContainerTransaction) parcel.readTypedObject(android.window.WindowContainerTransaction.CREATOR);
                    int readInt = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTransactionHandled(readStrongBinder2, windowContainerTransaction, readInt, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.window.WindowContainerTransaction windowContainerTransaction2 = (android.window.WindowContainerTransaction) parcel.readTypedObject(android.window.WindowContainerTransaction.CREATOR);
                    int readInt2 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    android.window.RemoteTransition remoteTransition = (android.window.RemoteTransition) parcel.readTypedObject(android.window.RemoteTransition.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyTransaction(windowContainerTransaction2, readInt2, readBoolean3, remoteTransition);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.ITaskFragmentOrganizerController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.ITaskFragmentOrganizerController.DESCRIPTOR;
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void registerOrganizer(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFragmentOrganizer);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void unregisterOrganizer(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFragmentOrganizer);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void registerRemoteAnimations(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, android.view.RemoteAnimationDefinition remoteAnimationDefinition) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFragmentOrganizer);
                    obtain.writeTypedObject(remoteAnimationDefinition, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void unregisterRemoteAnimations(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFragmentOrganizer);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public boolean isActivityEmbedded(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void onTransactionHandled(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void applyTransaction(android.window.WindowContainerTransaction windowContainerTransaction, int i, boolean z, android.window.RemoteTransition remoteTransition) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(remoteTransition, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
