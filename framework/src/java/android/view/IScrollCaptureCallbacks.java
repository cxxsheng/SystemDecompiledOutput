package android.view;

/* loaded from: classes4.dex */
public interface IScrollCaptureCallbacks extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.IScrollCaptureCallbacks";

    void onCaptureEnded() throws android.os.RemoteException;

    void onCaptureStarted() throws android.os.RemoteException;

    void onImageRequestCompleted(int i, android.graphics.Rect rect) throws android.os.RemoteException;

    public static class Default implements android.view.IScrollCaptureCallbacks {
        @Override // android.view.IScrollCaptureCallbacks
        public void onCaptureStarted() throws android.os.RemoteException {
        }

        @Override // android.view.IScrollCaptureCallbacks
        public void onImageRequestCompleted(int i, android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.view.IScrollCaptureCallbacks
        public void onCaptureEnded() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IScrollCaptureCallbacks {
        static final int TRANSACTION_onCaptureEnded = 3;
        static final int TRANSACTION_onCaptureStarted = 1;
        static final int TRANSACTION_onImageRequestCompleted = 2;

        public Stub() {
            attachInterface(this, android.view.IScrollCaptureCallbacks.DESCRIPTOR);
        }

        public static android.view.IScrollCaptureCallbacks asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.IScrollCaptureCallbacks.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IScrollCaptureCallbacks)) {
                return (android.view.IScrollCaptureCallbacks) queryLocalInterface;
            }
            return new android.view.IScrollCaptureCallbacks.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCaptureStarted";
                case 2:
                    return "onImageRequestCompleted";
                case 3:
                    return "onCaptureEnded";
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
                parcel.enforceInterface(android.view.IScrollCaptureCallbacks.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.IScrollCaptureCallbacks.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onCaptureStarted();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onImageRequestCompleted(readInt, rect);
                    return true;
                case 3:
                    onCaptureEnded();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IScrollCaptureCallbacks {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IScrollCaptureCallbacks.DESCRIPTOR;
            }

            @Override // android.view.IScrollCaptureCallbacks
            public void onCaptureStarted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IScrollCaptureCallbacks.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IScrollCaptureCallbacks
            public void onImageRequestCompleted(int i, android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IScrollCaptureCallbacks.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IScrollCaptureCallbacks
            public void onCaptureEnded() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IScrollCaptureCallbacks.DESCRIPTOR);
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
