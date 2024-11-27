package android.view;

/* loaded from: classes4.dex */
public interface IRemoteAnimationFinishedCallback extends android.os.IInterface {
    void onAnimationFinished() throws android.os.RemoteException;

    public static class Default implements android.view.IRemoteAnimationFinishedCallback {
        @Override // android.view.IRemoteAnimationFinishedCallback
        public void onAnimationFinished() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IRemoteAnimationFinishedCallback {
        public static final java.lang.String DESCRIPTOR = "android.view.IRemoteAnimationFinishedCallback";
        static final int TRANSACTION_onAnimationFinished = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.IRemoteAnimationFinishedCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IRemoteAnimationFinishedCallback)) {
                return (android.view.IRemoteAnimationFinishedCallback) queryLocalInterface;
            }
            return new android.view.IRemoteAnimationFinishedCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAnimationFinished";
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
                    onAnimationFinished();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IRemoteAnimationFinishedCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IRemoteAnimationFinishedCallback.Stub.DESCRIPTOR;
            }

            @Override // android.view.IRemoteAnimationFinishedCallback
            public void onAnimationFinished() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IRemoteAnimationFinishedCallback.Stub.DESCRIPTOR);
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
