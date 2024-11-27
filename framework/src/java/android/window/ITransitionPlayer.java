package android.window;

/* loaded from: classes4.dex */
public interface ITransitionPlayer extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.ITransitionPlayer";

    void onTransitionReady(android.os.IBinder iBinder, android.window.TransitionInfo transitionInfo, android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl.Transaction transaction2) throws android.os.RemoteException;

    void requestStartTransition(android.os.IBinder iBinder, android.window.TransitionRequestInfo transitionRequestInfo) throws android.os.RemoteException;

    public static class Default implements android.window.ITransitionPlayer {
        @Override // android.window.ITransitionPlayer
        public void onTransitionReady(android.os.IBinder iBinder, android.window.TransitionInfo transitionInfo, android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl.Transaction transaction2) throws android.os.RemoteException {
        }

        @Override // android.window.ITransitionPlayer
        public void requestStartTransition(android.os.IBinder iBinder, android.window.TransitionRequestInfo transitionRequestInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.ITransitionPlayer {
        static final int TRANSACTION_onTransitionReady = 1;
        static final int TRANSACTION_requestStartTransition = 2;

        public Stub() {
            attachInterface(this, android.window.ITransitionPlayer.DESCRIPTOR);
        }

        public static android.window.ITransitionPlayer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.ITransitionPlayer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.ITransitionPlayer)) {
                return (android.window.ITransitionPlayer) queryLocalInterface;
            }
            return new android.window.ITransitionPlayer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTransitionReady";
                case 2:
                    return "requestStartTransition";
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
                parcel.enforceInterface(android.window.ITransitionPlayer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.ITransitionPlayer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.window.TransitionInfo transitionInfo = (android.window.TransitionInfo) parcel.readTypedObject(android.window.TransitionInfo.CREATOR);
                    android.view.SurfaceControl.Transaction transaction = (android.view.SurfaceControl.Transaction) parcel.readTypedObject(android.view.SurfaceControl.Transaction.CREATOR);
                    android.view.SurfaceControl.Transaction transaction2 = (android.view.SurfaceControl.Transaction) parcel.readTypedObject(android.view.SurfaceControl.Transaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTransitionReady(readStrongBinder, transitionInfo, transaction, transaction2);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.window.TransitionRequestInfo transitionRequestInfo = (android.window.TransitionRequestInfo) parcel.readTypedObject(android.window.TransitionRequestInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestStartTransition(readStrongBinder2, transitionRequestInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.ITransitionPlayer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.ITransitionPlayer.DESCRIPTOR;
            }

            @Override // android.window.ITransitionPlayer
            public void onTransitionReady(android.os.IBinder iBinder, android.window.TransitionInfo transitionInfo, android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl.Transaction transaction2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITransitionPlayer.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(transitionInfo, 0);
                    obtain.writeTypedObject(transaction, 0);
                    obtain.writeTypedObject(transaction2, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.ITransitionPlayer
            public void requestStartTransition(android.os.IBinder iBinder, android.window.TransitionRequestInfo transitionRequestInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ITransitionPlayer.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(transitionRequestInfo, 0);
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
