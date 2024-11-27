package android.window;

/* loaded from: classes4.dex */
public interface IRemoteTransition extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IRemoteTransition";

    void mergeAnimation(android.os.IBinder iBinder, android.window.TransitionInfo transitionInfo, android.view.SurfaceControl.Transaction transaction, android.os.IBinder iBinder2, android.window.IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws android.os.RemoteException;

    void onTransitionConsumed(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void startAnimation(android.os.IBinder iBinder, android.window.TransitionInfo transitionInfo, android.view.SurfaceControl.Transaction transaction, android.window.IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws android.os.RemoteException;

    public static class Default implements android.window.IRemoteTransition {
        @Override // android.window.IRemoteTransition
        public void startAnimation(android.os.IBinder iBinder, android.window.TransitionInfo transitionInfo, android.view.SurfaceControl.Transaction transaction, android.window.IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws android.os.RemoteException {
        }

        @Override // android.window.IRemoteTransition
        public void mergeAnimation(android.os.IBinder iBinder, android.window.TransitionInfo transitionInfo, android.view.SurfaceControl.Transaction transaction, android.os.IBinder iBinder2, android.window.IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws android.os.RemoteException {
        }

        @Override // android.window.IRemoteTransition
        public void onTransitionConsumed(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IRemoteTransition {
        static final int TRANSACTION_mergeAnimation = 2;
        static final int TRANSACTION_onTransitionConsumed = 3;
        static final int TRANSACTION_startAnimation = 1;

        public Stub() {
            attachInterface(this, android.window.IRemoteTransition.DESCRIPTOR);
        }

        public static android.window.IRemoteTransition asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IRemoteTransition.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IRemoteTransition)) {
                return (android.window.IRemoteTransition) queryLocalInterface;
            }
            return new android.window.IRemoteTransition.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startAnimation";
                case 2:
                    return "mergeAnimation";
                case 3:
                    return "onTransitionConsumed";
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
                parcel.enforceInterface(android.window.IRemoteTransition.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IRemoteTransition.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.window.TransitionInfo transitionInfo = (android.window.TransitionInfo) parcel.readTypedObject(android.window.TransitionInfo.CREATOR);
                    android.view.SurfaceControl.Transaction transaction = (android.view.SurfaceControl.Transaction) parcel.readTypedObject(android.view.SurfaceControl.Transaction.CREATOR);
                    android.window.IRemoteTransitionFinishedCallback asInterface = android.window.IRemoteTransitionFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startAnimation(readStrongBinder, transitionInfo, transaction, asInterface);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.window.TransitionInfo transitionInfo2 = (android.window.TransitionInfo) parcel.readTypedObject(android.window.TransitionInfo.CREATOR);
                    android.view.SurfaceControl.Transaction transaction2 = (android.view.SurfaceControl.Transaction) parcel.readTypedObject(android.view.SurfaceControl.Transaction.CREATOR);
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    android.window.IRemoteTransitionFinishedCallback asInterface2 = android.window.IRemoteTransitionFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    mergeAnimation(readStrongBinder2, transitionInfo2, transaction2, readStrongBinder3, asInterface2);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTransitionConsumed(readStrongBinder4, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IRemoteTransition {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IRemoteTransition.DESCRIPTOR;
            }

            @Override // android.window.IRemoteTransition
            public void startAnimation(android.os.IBinder iBinder, android.window.TransitionInfo transitionInfo, android.view.SurfaceControl.Transaction transaction, android.window.IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IRemoteTransition.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(transitionInfo, 0);
                    obtain.writeTypedObject(transaction, 0);
                    obtain.writeStrongInterface(iRemoteTransitionFinishedCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IRemoteTransition
            public void mergeAnimation(android.os.IBinder iBinder, android.window.TransitionInfo transitionInfo, android.view.SurfaceControl.Transaction transaction, android.os.IBinder iBinder2, android.window.IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IRemoteTransition.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(transitionInfo, 0);
                    obtain.writeTypedObject(transaction, 0);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeStrongInterface(iRemoteTransitionFinishedCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IRemoteTransition
            public void onTransitionConsumed(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IRemoteTransition.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
