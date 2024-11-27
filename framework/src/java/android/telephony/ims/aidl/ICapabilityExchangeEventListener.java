package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface ICapabilityExchangeEventListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.ICapabilityExchangeEventListener";

    void onPublishUpdated(android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException;

    void onRemoteCapabilityRequest(android.net.Uri uri, java.util.List<java.lang.String> list, android.telephony.ims.aidl.IOptionsRequestCallback iOptionsRequestCallback) throws android.os.RemoteException;

    void onRequestPublishCapabilities(int i) throws android.os.RemoteException;

    void onUnpublish() throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.ICapabilityExchangeEventListener {
        @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
        public void onRequestPublishCapabilities(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
        public void onUnpublish() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
        public void onPublishUpdated(android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
        public void onRemoteCapabilityRequest(android.net.Uri uri, java.util.List<java.lang.String> list, android.telephony.ims.aidl.IOptionsRequestCallback iOptionsRequestCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.ICapabilityExchangeEventListener {
        static final int TRANSACTION_onPublishUpdated = 3;
        static final int TRANSACTION_onRemoteCapabilityRequest = 4;
        static final int TRANSACTION_onRequestPublishCapabilities = 1;
        static final int TRANSACTION_onUnpublish = 2;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.ICapabilityExchangeEventListener.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.ICapabilityExchangeEventListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.ICapabilityExchangeEventListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.ICapabilityExchangeEventListener)) {
                return (android.telephony.ims.aidl.ICapabilityExchangeEventListener) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.ICapabilityExchangeEventListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onRequestPublishCapabilities";
                case 2:
                    return "onUnpublish";
                case 3:
                    return "onPublishUpdated";
                case 4:
                    return "onRemoteCapabilityRequest";
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
                parcel.enforceInterface(android.telephony.ims.aidl.ICapabilityExchangeEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.ICapabilityExchangeEventListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestPublishCapabilities(readInt);
                    return true;
                case 2:
                    onUnpublish();
                    return true;
                case 3:
                    android.telephony.ims.SipDetails sipDetails = (android.telephony.ims.SipDetails) parcel.readTypedObject(android.telephony.ims.SipDetails.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPublishUpdated(sipDetails);
                    return true;
                case 4:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    android.telephony.ims.aidl.IOptionsRequestCallback asInterface = android.telephony.ims.aidl.IOptionsRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onRemoteCapabilityRequest(uri, createStringArrayList, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.ICapabilityExchangeEventListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.ICapabilityExchangeEventListener.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
            public void onRequestPublishCapabilities(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ICapabilityExchangeEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
            public void onUnpublish() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ICapabilityExchangeEventListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
            public void onPublishUpdated(android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ICapabilityExchangeEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(sipDetails, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
            public void onRemoteCapabilityRequest(android.net.Uri uri, java.util.List<java.lang.String> list, android.telephony.ims.aidl.IOptionsRequestCallback iOptionsRequestCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ICapabilityExchangeEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iOptionsRequestCallback);
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
