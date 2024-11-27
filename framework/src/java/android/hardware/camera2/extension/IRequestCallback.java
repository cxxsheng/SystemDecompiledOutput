package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public interface IRequestCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.extension.IRequestCallback";

    void onCaptureBufferLost(int i, long j, int i2) throws android.os.RemoteException;

    void onCaptureCompleted(int i, android.hardware.camera2.extension.ParcelTotalCaptureResult parcelTotalCaptureResult) throws android.os.RemoteException;

    void onCaptureFailed(int i, android.hardware.camera2.extension.CaptureFailure captureFailure) throws android.os.RemoteException;

    void onCaptureProgressed(int i, android.hardware.camera2.extension.ParcelCaptureResult parcelCaptureResult) throws android.os.RemoteException;

    void onCaptureSequenceAborted(int i) throws android.os.RemoteException;

    void onCaptureSequenceCompleted(int i, long j) throws android.os.RemoteException;

    void onCaptureStarted(int i, long j, long j2) throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.extension.IRequestCallback {
        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureStarted(int i, long j, long j2) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureProgressed(int i, android.hardware.camera2.extension.ParcelCaptureResult parcelCaptureResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureCompleted(int i, android.hardware.camera2.extension.ParcelTotalCaptureResult parcelTotalCaptureResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureFailed(int i, android.hardware.camera2.extension.CaptureFailure captureFailure) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureBufferLost(int i, long j, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureSequenceCompleted(int i, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureSequenceAborted(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.extension.IRequestCallback {
        static final int TRANSACTION_onCaptureBufferLost = 5;
        static final int TRANSACTION_onCaptureCompleted = 3;
        static final int TRANSACTION_onCaptureFailed = 4;
        static final int TRANSACTION_onCaptureProgressed = 2;
        static final int TRANSACTION_onCaptureSequenceAborted = 7;
        static final int TRANSACTION_onCaptureSequenceCompleted = 6;
        static final int TRANSACTION_onCaptureStarted = 1;

        public Stub() {
            attachInterface(this, android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR);
        }

        public static android.hardware.camera2.extension.IRequestCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.extension.IRequestCallback)) {
                return (android.hardware.camera2.extension.IRequestCallback) queryLocalInterface;
            }
            return new android.hardware.camera2.extension.IRequestCallback.Stub.Proxy(iBinder);
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
                    return "onCaptureProgressed";
                case 3:
                    return "onCaptureCompleted";
                case 4:
                    return "onCaptureFailed";
                case 5:
                    return "onCaptureBufferLost";
                case 6:
                    return "onCaptureSequenceCompleted";
                case 7:
                    return "onCaptureSequenceAborted";
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
                parcel.enforceInterface(android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onCaptureStarted(readInt, readLong, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.hardware.camera2.extension.ParcelCaptureResult parcelCaptureResult = (android.hardware.camera2.extension.ParcelCaptureResult) parcel.readTypedObject(android.hardware.camera2.extension.ParcelCaptureResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCaptureProgressed(readInt2, parcelCaptureResult);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    android.hardware.camera2.extension.ParcelTotalCaptureResult parcelTotalCaptureResult = (android.hardware.camera2.extension.ParcelTotalCaptureResult) parcel.readTypedObject(android.hardware.camera2.extension.ParcelTotalCaptureResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCaptureCompleted(readInt3, parcelTotalCaptureResult);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    android.hardware.camera2.extension.CaptureFailure captureFailure = (android.hardware.camera2.extension.CaptureFailure) parcel.readTypedObject(android.hardware.camera2.extension.CaptureFailure.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCaptureFailed(readInt4, captureFailure);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCaptureBufferLost(readInt5, readLong3, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onCaptureSequenceCompleted(readInt7, readLong4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCaptureSequenceAborted(readInt8);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.extension.IRequestCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureStarted(int i, long j, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureProgressed(int i, android.hardware.camera2.extension.ParcelCaptureResult parcelCaptureResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parcelCaptureResult, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureCompleted(int i, android.hardware.camera2.extension.ParcelTotalCaptureResult parcelTotalCaptureResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parcelTotalCaptureResult, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureFailed(int i, android.hardware.camera2.extension.CaptureFailure captureFailure) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(captureFailure, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureBufferLost(int i, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureSequenceCompleted(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureSequenceAborted(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
