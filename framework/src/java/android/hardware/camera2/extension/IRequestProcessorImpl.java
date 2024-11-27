package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public interface IRequestProcessorImpl extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.extension.IRequestProcessorImpl";

    void abortCaptures() throws android.os.RemoteException;

    void setImageProcessor(android.hardware.camera2.extension.OutputConfigId outputConfigId, android.hardware.camera2.extension.IImageProcessorImpl iImageProcessorImpl) throws android.os.RemoteException;

    int setRepeating(android.hardware.camera2.extension.Request request, android.hardware.camera2.extension.IRequestCallback iRequestCallback) throws android.os.RemoteException;

    void stopRepeating() throws android.os.RemoteException;

    int submit(android.hardware.camera2.extension.Request request, android.hardware.camera2.extension.IRequestCallback iRequestCallback) throws android.os.RemoteException;

    int submitBurst(java.util.List<android.hardware.camera2.extension.Request> list, android.hardware.camera2.extension.IRequestCallback iRequestCallback) throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.extension.IRequestProcessorImpl {
        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void setImageProcessor(android.hardware.camera2.extension.OutputConfigId outputConfigId, android.hardware.camera2.extension.IImageProcessorImpl iImageProcessorImpl) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submit(android.hardware.camera2.extension.Request request, android.hardware.camera2.extension.IRequestCallback iRequestCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submitBurst(java.util.List<android.hardware.camera2.extension.Request> list, android.hardware.camera2.extension.IRequestCallback iRequestCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int setRepeating(android.hardware.camera2.extension.Request request, android.hardware.camera2.extension.IRequestCallback iRequestCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void abortCaptures() throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void stopRepeating() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.extension.IRequestProcessorImpl {
        static final int TRANSACTION_abortCaptures = 5;
        static final int TRANSACTION_setImageProcessor = 1;
        static final int TRANSACTION_setRepeating = 4;
        static final int TRANSACTION_stopRepeating = 6;
        static final int TRANSACTION_submit = 2;
        static final int TRANSACTION_submitBurst = 3;

        public Stub() {
            attachInterface(this, android.hardware.camera2.extension.IRequestProcessorImpl.DESCRIPTOR);
        }

        public static android.hardware.camera2.extension.IRequestProcessorImpl asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.extension.IRequestProcessorImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.extension.IRequestProcessorImpl)) {
                return (android.hardware.camera2.extension.IRequestProcessorImpl) queryLocalInterface;
            }
            return new android.hardware.camera2.extension.IRequestProcessorImpl.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setImageProcessor";
                case 2:
                    return "submit";
                case 3:
                    return "submitBurst";
                case 4:
                    return "setRepeating";
                case 5:
                    return "abortCaptures";
                case 6:
                    return "stopRepeating";
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
                parcel.enforceInterface(android.hardware.camera2.extension.IRequestProcessorImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.extension.IRequestProcessorImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.camera2.extension.OutputConfigId outputConfigId = (android.hardware.camera2.extension.OutputConfigId) parcel.readTypedObject(android.hardware.camera2.extension.OutputConfigId.CREATOR);
                    android.hardware.camera2.extension.IImageProcessorImpl asInterface = android.hardware.camera2.extension.IImageProcessorImpl.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setImageProcessor(outputConfigId, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.hardware.camera2.extension.Request request = (android.hardware.camera2.extension.Request) parcel.readTypedObject(android.hardware.camera2.extension.Request.CREATOR);
                    android.hardware.camera2.extension.IRequestCallback asInterface2 = android.hardware.camera2.extension.IRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int submit = submit(request, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(submit);
                    return true;
                case 3:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.hardware.camera2.extension.Request.CREATOR);
                    android.hardware.camera2.extension.IRequestCallback asInterface3 = android.hardware.camera2.extension.IRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int submitBurst = submitBurst(createTypedArrayList, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(submitBurst);
                    return true;
                case 4:
                    android.hardware.camera2.extension.Request request2 = (android.hardware.camera2.extension.Request) parcel.readTypedObject(android.hardware.camera2.extension.Request.CREATOR);
                    android.hardware.camera2.extension.IRequestCallback asInterface4 = android.hardware.camera2.extension.IRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int repeating = setRepeating(request2, asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(repeating);
                    return true;
                case 5:
                    abortCaptures();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    stopRepeating();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.extension.IRequestProcessorImpl {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.extension.IRequestProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public void setImageProcessor(android.hardware.camera2.extension.OutputConfigId outputConfigId, android.hardware.camera2.extension.IImageProcessorImpl iImageProcessorImpl) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(outputConfigId, 0);
                    obtain.writeStrongInterface(iImageProcessorImpl);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public int submit(android.hardware.camera2.extension.Request request, android.hardware.camera2.extension.IRequestCallback iRequestCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(request, 0);
                    obtain.writeStrongInterface(iRequestCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public int submitBurst(java.util.List<android.hardware.camera2.extension.Request> list, android.hardware.camera2.extension.IRequestCallback iRequestCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iRequestCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public int setRepeating(android.hardware.camera2.extension.Request request, android.hardware.camera2.extension.IRequestCallback iRequestCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(request, 0);
                    obtain.writeStrongInterface(iRequestCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public void abortCaptures() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestProcessorImpl
            public void stopRepeating() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IRequestProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
