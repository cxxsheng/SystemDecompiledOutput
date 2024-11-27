package android.window;

/* loaded from: classes4.dex */
public interface IBackAnimationRunner extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IBackAnimationRunner";

    void onAnimationCancelled() throws android.os.RemoteException;

    void onAnimationStart(android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr3, android.window.IBackAnimationFinishedCallback iBackAnimationFinishedCallback) throws android.os.RemoteException;

    public static class Default implements android.window.IBackAnimationRunner {
        @Override // android.window.IBackAnimationRunner
        public void onAnimationCancelled() throws android.os.RemoteException {
        }

        @Override // android.window.IBackAnimationRunner
        public void onAnimationStart(android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr3, android.window.IBackAnimationFinishedCallback iBackAnimationFinishedCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IBackAnimationRunner {
        static final int TRANSACTION_onAnimationCancelled = 2;
        static final int TRANSACTION_onAnimationStart = 3;

        public Stub() {
            attachInterface(this, android.window.IBackAnimationRunner.DESCRIPTOR);
        }

        public static android.window.IBackAnimationRunner asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IBackAnimationRunner.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IBackAnimationRunner)) {
                return (android.window.IBackAnimationRunner) queryLocalInterface;
            }
            return new android.window.IBackAnimationRunner.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 2:
                    return "onAnimationCancelled";
                case 3:
                    return "onAnimationStart";
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
                parcel.enforceInterface(android.window.IBackAnimationRunner.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IBackAnimationRunner.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 2:
                    onAnimationCancelled();
                    return true;
                case 3:
                    android.view.RemoteAnimationTarget[] remoteAnimationTargetArr = (android.view.RemoteAnimationTarget[]) parcel.createTypedArray(android.view.RemoteAnimationTarget.CREATOR);
                    android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2 = (android.view.RemoteAnimationTarget[]) parcel.createTypedArray(android.view.RemoteAnimationTarget.CREATOR);
                    android.view.RemoteAnimationTarget[] remoteAnimationTargetArr3 = (android.view.RemoteAnimationTarget[]) parcel.createTypedArray(android.view.RemoteAnimationTarget.CREATOR);
                    android.window.IBackAnimationFinishedCallback asInterface = android.window.IBackAnimationFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onAnimationStart(remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IBackAnimationRunner {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IBackAnimationRunner.DESCRIPTOR;
            }

            @Override // android.window.IBackAnimationRunner
            public void onAnimationCancelled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IBackAnimationRunner.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IBackAnimationRunner
            public void onAnimationStart(android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr3, android.window.IBackAnimationFinishedCallback iBackAnimationFinishedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IBackAnimationRunner.DESCRIPTOR);
                    obtain.writeTypedArray(remoteAnimationTargetArr, 0);
                    obtain.writeTypedArray(remoteAnimationTargetArr2, 0);
                    obtain.writeTypedArray(remoteAnimationTargetArr3, 0);
                    obtain.writeStrongInterface(iBackAnimationFinishedCallback);
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
