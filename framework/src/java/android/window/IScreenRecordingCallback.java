package android.window;

/* loaded from: classes4.dex */
public interface IScreenRecordingCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IScreenRecordingCallback";

    void onScreenRecordingStateChanged(boolean z) throws android.os.RemoteException;

    public static class Default implements android.window.IScreenRecordingCallback {
        @Override // android.window.IScreenRecordingCallback
        public void onScreenRecordingStateChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IScreenRecordingCallback {
        static final int TRANSACTION_onScreenRecordingStateChanged = 1;

        public Stub() {
            attachInterface(this, android.window.IScreenRecordingCallback.DESCRIPTOR);
        }

        public static android.window.IScreenRecordingCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IScreenRecordingCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IScreenRecordingCallback)) {
                return (android.window.IScreenRecordingCallback) queryLocalInterface;
            }
            return new android.window.IScreenRecordingCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onScreenRecordingStateChanged";
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
                parcel.enforceInterface(android.window.IScreenRecordingCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IScreenRecordingCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onScreenRecordingStateChanged(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IScreenRecordingCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IScreenRecordingCallback.DESCRIPTOR;
            }

            @Override // android.window.IScreenRecordingCallback
            public void onScreenRecordingStateChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IScreenRecordingCallback.DESCRIPTOR);
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
