package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public interface IImageProcessorImpl extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.extension.IImageProcessorImpl";

    void onNextImageAvailable(android.hardware.camera2.extension.OutputConfigId outputConfigId, android.hardware.camera2.extension.ParcelImage parcelImage, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.extension.IImageProcessorImpl {
        @Override // android.hardware.camera2.extension.IImageProcessorImpl
        public void onNextImageAvailable(android.hardware.camera2.extension.OutputConfigId outputConfigId, android.hardware.camera2.extension.ParcelImage parcelImage, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.extension.IImageProcessorImpl {
        static final int TRANSACTION_onNextImageAvailable = 1;

        public Stub() {
            attachInterface(this, android.hardware.camera2.extension.IImageProcessorImpl.DESCRIPTOR);
        }

        public static android.hardware.camera2.extension.IImageProcessorImpl asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.extension.IImageProcessorImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.extension.IImageProcessorImpl)) {
                return (android.hardware.camera2.extension.IImageProcessorImpl) queryLocalInterface;
            }
            return new android.hardware.camera2.extension.IImageProcessorImpl.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onNextImageAvailable";
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
                parcel.enforceInterface(android.hardware.camera2.extension.IImageProcessorImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.extension.IImageProcessorImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.camera2.extension.OutputConfigId outputConfigId = (android.hardware.camera2.extension.OutputConfigId) parcel.readTypedObject(android.hardware.camera2.extension.OutputConfigId.CREATOR);
                    android.hardware.camera2.extension.ParcelImage parcelImage = (android.hardware.camera2.extension.ParcelImage) parcel.readTypedObject(android.hardware.camera2.extension.ParcelImage.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNextImageAvailable(outputConfigId, parcelImage, readString);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.extension.IImageProcessorImpl {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.extension.IImageProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IImageProcessorImpl
            public void onNextImageAvailable(android.hardware.camera2.extension.OutputConfigId outputConfigId, android.hardware.camera2.extension.ParcelImage parcelImage, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(outputConfigId, 0);
                    obtain.writeTypedObject(parcelImage, 0);
                    obtain.writeString(str);
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
