package android.view.accessibility;

/* loaded from: classes4.dex */
public interface IRemoteMagnificationAnimationCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.accessibility.IRemoteMagnificationAnimationCallback";

    void onResult(boolean z) throws android.os.RemoteException;

    public static class Default implements android.view.accessibility.IRemoteMagnificationAnimationCallback {
        @Override // android.view.accessibility.IRemoteMagnificationAnimationCallback
        public void onResult(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.accessibility.IRemoteMagnificationAnimationCallback {
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, android.view.accessibility.IRemoteMagnificationAnimationCallback.DESCRIPTOR);
        }

        public static android.view.accessibility.IRemoteMagnificationAnimationCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.accessibility.IRemoteMagnificationAnimationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.accessibility.IRemoteMagnificationAnimationCallback)) {
                return (android.view.accessibility.IRemoteMagnificationAnimationCallback) queryLocalInterface;
            }
            return new android.view.accessibility.IRemoteMagnificationAnimationCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onResult";
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
                parcel.enforceInterface(android.view.accessibility.IRemoteMagnificationAnimationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.accessibility.IRemoteMagnificationAnimationCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onResult(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.accessibility.IRemoteMagnificationAnimationCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.accessibility.IRemoteMagnificationAnimationCallback.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IRemoteMagnificationAnimationCallback
            public void onResult(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.accessibility.IRemoteMagnificationAnimationCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
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
