package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IRcsUcePublishStateCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IRcsUcePublishStateCallback";

    void onPublishUpdated(android.telephony.ims.PublishAttributes publishAttributes) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IRcsUcePublishStateCallback {
        @Override // android.telephony.ims.aidl.IRcsUcePublishStateCallback
        public void onPublishUpdated(android.telephony.ims.PublishAttributes publishAttributes) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IRcsUcePublishStateCallback {
        static final int TRANSACTION_onPublishUpdated = 1;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IRcsUcePublishStateCallback.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IRcsUcePublishStateCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IRcsUcePublishStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IRcsUcePublishStateCallback)) {
                return (android.telephony.ims.aidl.IRcsUcePublishStateCallback) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IRcsUcePublishStateCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPublishUpdated";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IRcsUcePublishStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IRcsUcePublishStateCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.ims.PublishAttributes publishAttributes = (android.telephony.ims.PublishAttributes) parcel.readTypedObject(android.telephony.ims.PublishAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPublishUpdated(publishAttributes);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IRcsUcePublishStateCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IRcsUcePublishStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IRcsUcePublishStateCallback
            public void onPublishUpdated(android.telephony.ims.PublishAttributes publishAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IRcsUcePublishStateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(publishAttributes, 0);
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
