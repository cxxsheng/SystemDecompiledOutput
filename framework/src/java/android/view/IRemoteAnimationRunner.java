package android.view;

/* loaded from: classes4.dex */
public interface IRemoteAnimationRunner extends android.os.IInterface {
    void onAnimationCancelled() throws android.os.RemoteException;

    void onAnimationStart(int i, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr3, android.view.IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws android.os.RemoteException;

    public static class Default implements android.view.IRemoteAnimationRunner {
        @Override // android.view.IRemoteAnimationRunner
        public void onAnimationStart(int i, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr3, android.view.IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws android.os.RemoteException {
        }

        @Override // android.view.IRemoteAnimationRunner
        public void onAnimationCancelled() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IRemoteAnimationRunner {
        public static final java.lang.String DESCRIPTOR = "android.view.IRemoteAnimationRunner";
        static final int TRANSACTION_onAnimationCancelled = 2;
        static final int TRANSACTION_onAnimationStart = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.IRemoteAnimationRunner asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IRemoteAnimationRunner)) {
                return (android.view.IRemoteAnimationRunner) queryLocalInterface;
            }
            return new android.view.IRemoteAnimationRunner.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAnimationStart";
                case 2:
                    return "onAnimationCancelled";
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
                    int readInt = parcel.readInt();
                    android.view.RemoteAnimationTarget[] remoteAnimationTargetArr = (android.view.RemoteAnimationTarget[]) parcel.createTypedArray(android.view.RemoteAnimationTarget.CREATOR);
                    android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2 = (android.view.RemoteAnimationTarget[]) parcel.createTypedArray(android.view.RemoteAnimationTarget.CREATOR);
                    android.view.RemoteAnimationTarget[] remoteAnimationTargetArr3 = (android.view.RemoteAnimationTarget[]) parcel.createTypedArray(android.view.RemoteAnimationTarget.CREATOR);
                    android.view.IRemoteAnimationFinishedCallback asInterface = android.view.IRemoteAnimationFinishedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onAnimationStart(readInt, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, asInterface);
                    return true;
                case 2:
                    onAnimationCancelled();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IRemoteAnimationRunner {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IRemoteAnimationRunner.Stub.DESCRIPTOR;
            }

            @Override // android.view.IRemoteAnimationRunner
            public void onAnimationStart(int i, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr3, android.view.IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IRemoteAnimationRunner.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(remoteAnimationTargetArr, 0);
                    obtain.writeTypedArray(remoteAnimationTargetArr2, 0);
                    obtain.writeTypedArray(remoteAnimationTargetArr3, 0);
                    obtain.writeStrongInterface(iRemoteAnimationFinishedCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IRemoteAnimationRunner
            public void onAnimationCancelled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IRemoteAnimationRunner.Stub.DESCRIPTOR);
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
