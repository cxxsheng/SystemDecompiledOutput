package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IImsServiceControllerListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IImsServiceControllerListener";

    void onUpdateSupportedImsFeatures(android.telephony.ims.stub.ImsFeatureConfiguration imsFeatureConfiguration) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IImsServiceControllerListener {
        @Override // android.telephony.ims.aidl.IImsServiceControllerListener
        public void onUpdateSupportedImsFeatures(android.telephony.ims.stub.ImsFeatureConfiguration imsFeatureConfiguration) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IImsServiceControllerListener {
        static final int TRANSACTION_onUpdateSupportedImsFeatures = 1;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IImsServiceControllerListener.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IImsServiceControllerListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IImsServiceControllerListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IImsServiceControllerListener)) {
                return (android.telephony.ims.aidl.IImsServiceControllerListener) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IImsServiceControllerListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUpdateSupportedImsFeatures";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IImsServiceControllerListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IImsServiceControllerListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.ims.stub.ImsFeatureConfiguration imsFeatureConfiguration = (android.telephony.ims.stub.ImsFeatureConfiguration) parcel.readTypedObject(android.telephony.ims.stub.ImsFeatureConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUpdateSupportedImsFeatures(imsFeatureConfiguration);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IImsServiceControllerListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IImsServiceControllerListener.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsServiceControllerListener
            public void onUpdateSupportedImsFeatures(android.telephony.ims.stub.ImsFeatureConfiguration imsFeatureConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceControllerListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsFeatureConfiguration, 0);
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
