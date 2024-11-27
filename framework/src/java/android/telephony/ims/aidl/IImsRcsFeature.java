package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IImsRcsFeature extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IImsRcsFeature";

    void addCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    void changeCapabilitiesConfiguration(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    int getFeatureState() throws android.os.RemoteException;

    void publishCapabilities(java.lang.String str, android.telephony.ims.aidl.IPublishResponseCallback iPublishResponseCallback) throws android.os.RemoteException;

    void queryCapabilityConfiguration(int i, int i2, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    int queryCapabilityStatus() throws android.os.RemoteException;

    void removeCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    void sendOptionsCapabilityRequest(android.net.Uri uri, java.util.List<java.lang.String> list, android.telephony.ims.aidl.IOptionsResponseCallback iOptionsResponseCallback) throws android.os.RemoteException;

    void setCapabilityExchangeEventListener(android.telephony.ims.aidl.ICapabilityExchangeEventListener iCapabilityExchangeEventListener) throws android.os.RemoteException;

    void subscribeForCapabilities(java.util.List<android.net.Uri> list, android.telephony.ims.aidl.ISubscribeResponseCallback iSubscribeResponseCallback) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IImsRcsFeature {
        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public int queryCapabilityStatus() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public int getFeatureState() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void addCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void removeCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void changeCapabilitiesConfiguration(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void queryCapabilityConfiguration(int i, int i2, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void setCapabilityExchangeEventListener(android.telephony.ims.aidl.ICapabilityExchangeEventListener iCapabilityExchangeEventListener) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void publishCapabilities(java.lang.String str, android.telephony.ims.aidl.IPublishResponseCallback iPublishResponseCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void subscribeForCapabilities(java.util.List<android.net.Uri> list, android.telephony.ims.aidl.ISubscribeResponseCallback iSubscribeResponseCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void sendOptionsCapabilityRequest(android.net.Uri uri, java.util.List<java.lang.String> list, android.telephony.ims.aidl.IOptionsResponseCallback iOptionsResponseCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IImsRcsFeature {
        static final int TRANSACTION_addCapabilityCallback = 3;
        static final int TRANSACTION_changeCapabilitiesConfiguration = 5;
        static final int TRANSACTION_getFeatureState = 2;
        static final int TRANSACTION_publishCapabilities = 8;
        static final int TRANSACTION_queryCapabilityConfiguration = 6;
        static final int TRANSACTION_queryCapabilityStatus = 1;
        static final int TRANSACTION_removeCapabilityCallback = 4;
        static final int TRANSACTION_sendOptionsCapabilityRequest = 10;
        static final int TRANSACTION_setCapabilityExchangeEventListener = 7;
        static final int TRANSACTION_subscribeForCapabilities = 9;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IImsRcsFeature asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IImsRcsFeature)) {
                return (android.telephony.ims.aidl.IImsRcsFeature) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IImsRcsFeature.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "queryCapabilityStatus";
                case 2:
                    return "getFeatureState";
                case 3:
                    return "addCapabilityCallback";
                case 4:
                    return "removeCapabilityCallback";
                case 5:
                    return "changeCapabilitiesConfiguration";
                case 6:
                    return "queryCapabilityConfiguration";
                case 7:
                    return "setCapabilityExchangeEventListener";
                case 8:
                    return "publishCapabilities";
                case 9:
                    return "subscribeForCapabilities";
                case 10:
                    return "sendOptionsCapabilityRequest";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int queryCapabilityStatus = queryCapabilityStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCapabilityStatus);
                    return true;
                case 2:
                    int featureState = getFeatureState();
                    parcel2.writeNoException();
                    parcel2.writeInt(featureState);
                    return true;
                case 3:
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCapabilityCallback(asInterface);
                    return true;
                case 4:
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface2 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCapabilityCallback(asInterface2);
                    return true;
                case 5:
                    android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest = (android.telephony.ims.feature.CapabilityChangeRequest) parcel.readTypedObject(android.telephony.ims.feature.CapabilityChangeRequest.CREATOR);
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface3 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    changeCapabilitiesConfiguration(capabilityChangeRequest, asInterface3);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface4 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    queryCapabilityConfiguration(readInt, readInt2, asInterface4);
                    return true;
                case 7:
                    android.telephony.ims.aidl.ICapabilityExchangeEventListener asInterface5 = android.telephony.ims.aidl.ICapabilityExchangeEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCapabilityExchangeEventListener(asInterface5);
                    return true;
                case 8:
                    java.lang.String readString = parcel.readString();
                    android.telephony.ims.aidl.IPublishResponseCallback asInterface6 = android.telephony.ims.aidl.IPublishResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    publishCapabilities(readString, asInterface6);
                    return true;
                case 9:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.net.Uri.CREATOR);
                    android.telephony.ims.aidl.ISubscribeResponseCallback asInterface7 = android.telephony.ims.aidl.ISubscribeResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribeForCapabilities(createTypedArrayList, asInterface7);
                    return true;
                case 10:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    android.telephony.ims.aidl.IOptionsResponseCallback asInterface8 = android.telephony.ims.aidl.IOptionsResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendOptionsCapabilityRequest(uri, createStringArrayList, asInterface8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IImsRcsFeature {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public int queryCapabilityStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public int getFeatureState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void addCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void removeCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void changeCapabilitiesConfiguration(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
                    obtain.writeTypedObject(capabilityChangeRequest, 0);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void queryCapabilityConfiguration(int i, int i2, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void setCapabilityExchangeEventListener(android.telephony.ims.aidl.ICapabilityExchangeEventListener iCapabilityExchangeEventListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iCapabilityExchangeEventListener);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void publishCapabilities(java.lang.String str, android.telephony.ims.aidl.IPublishResponseCallback iPublishResponseCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPublishResponseCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void subscribeForCapabilities(java.util.List<android.net.Uri> list, android.telephony.ims.aidl.ISubscribeResponseCallback iSubscribeResponseCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iSubscribeResponseCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void sendOptionsCapabilityRequest(android.net.Uri uri, java.util.List<java.lang.String> list, android.telephony.ims.aidl.IOptionsResponseCallback iOptionsResponseCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRcsFeature.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iOptionsResponseCallback);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
