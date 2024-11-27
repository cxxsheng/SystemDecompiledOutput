package android.view;

/* loaded from: classes4.dex */
public interface IScrollCaptureConnection extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.IScrollCaptureConnection";

    void close() throws android.os.RemoteException;

    android.os.ICancellationSignal endCapture() throws android.os.RemoteException;

    android.os.ICancellationSignal requestImage(android.graphics.Rect rect) throws android.os.RemoteException;

    android.os.ICancellationSignal startCapture(android.view.Surface surface, android.view.IScrollCaptureCallbacks iScrollCaptureCallbacks) throws android.os.RemoteException;

    public static class Default implements android.view.IScrollCaptureConnection {
        @Override // android.view.IScrollCaptureConnection
        public android.os.ICancellationSignal startCapture(android.view.Surface surface, android.view.IScrollCaptureCallbacks iScrollCaptureCallbacks) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IScrollCaptureConnection
        public android.os.ICancellationSignal requestImage(android.graphics.Rect rect) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IScrollCaptureConnection
        public android.os.ICancellationSignal endCapture() throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IScrollCaptureConnection
        public void close() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IScrollCaptureConnection {
        static final int TRANSACTION_close = 4;
        static final int TRANSACTION_endCapture = 3;
        static final int TRANSACTION_requestImage = 2;
        static final int TRANSACTION_startCapture = 1;

        public Stub() {
            attachInterface(this, android.view.IScrollCaptureConnection.DESCRIPTOR);
        }

        public static android.view.IScrollCaptureConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.IScrollCaptureConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IScrollCaptureConnection)) {
                return (android.view.IScrollCaptureConnection) queryLocalInterface;
            }
            return new android.view.IScrollCaptureConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startCapture";
                case 2:
                    return "requestImage";
                case 3:
                    return "endCapture";
                case 4:
                    return "close";
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
                parcel.enforceInterface(android.view.IScrollCaptureConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.IScrollCaptureConnection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    android.view.IScrollCaptureCallbacks asInterface = android.view.IScrollCaptureCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.os.ICancellationSignal startCapture = startCapture(surface, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startCapture);
                    return true;
                case 2:
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.ICancellationSignal requestImage = requestImage(rect);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(requestImage);
                    return true;
                case 3:
                    android.os.ICancellationSignal endCapture = endCapture();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(endCapture);
                    return true;
                case 4:
                    close();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IScrollCaptureConnection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IScrollCaptureConnection.DESCRIPTOR;
            }

            @Override // android.view.IScrollCaptureConnection
            public android.os.ICancellationSignal startCapture(android.view.Surface surface, android.view.IScrollCaptureCallbacks iScrollCaptureCallbacks) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IScrollCaptureConnection.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeStrongInterface(iScrollCaptureCallbacks);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IScrollCaptureConnection
            public android.os.ICancellationSignal requestImage(android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IScrollCaptureConnection.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IScrollCaptureConnection
            public android.os.ICancellationSignal endCapture() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IScrollCaptureConnection.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IScrollCaptureConnection
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IScrollCaptureConnection.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
