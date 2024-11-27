package android.window;

/* loaded from: classes4.dex */
public interface IBackAnimationFinishedCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IBackAnimationFinishedCallback";

    void onAnimationFinished(boolean z) throws android.os.RemoteException;

    public static class Default implements android.window.IBackAnimationFinishedCallback {
        @Override // android.window.IBackAnimationFinishedCallback
        public void onAnimationFinished(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IBackAnimationFinishedCallback {
        static final int TRANSACTION_onAnimationFinished = 1;

        public Stub() {
            attachInterface(this, android.window.IBackAnimationFinishedCallback.DESCRIPTOR);
        }

        public static android.window.IBackAnimationFinishedCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IBackAnimationFinishedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IBackAnimationFinishedCallback)) {
                return (android.window.IBackAnimationFinishedCallback) queryLocalInterface;
            }
            return new android.window.IBackAnimationFinishedCallback.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(android.window.IBackAnimationFinishedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IBackAnimationFinishedCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAnimationFinished(readBoolean);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IBackAnimationFinishedCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IBackAnimationFinishedCallback.DESCRIPTOR;
            }

            @Override // android.window.IBackAnimationFinishedCallback
            public void onAnimationFinished(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IBackAnimationFinishedCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
