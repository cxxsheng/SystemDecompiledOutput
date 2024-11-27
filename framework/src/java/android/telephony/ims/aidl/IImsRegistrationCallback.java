package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IImsRegistrationCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IImsRegistrationCallback";

    void onDeregistered(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2) throws android.os.RemoteException;

    void onDeregisteredWithDetails(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2, android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException;

    void onRegistered(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) throws android.os.RemoteException;

    void onRegistering(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) throws android.os.RemoteException;

    void onSubscriberAssociatedUriChanged(android.net.Uri[] uriArr) throws android.os.RemoteException;

    void onTechnologyChangeFailed(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IImsRegistrationCallback {
        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onRegistered(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onRegistering(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onDeregistered(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onDeregisteredWithDetails(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2, android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onTechnologyChangeFailed(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onSubscriberAssociatedUriChanged(android.net.Uri[] uriArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IImsRegistrationCallback {
        static final int TRANSACTION_onDeregistered = 3;
        static final int TRANSACTION_onDeregisteredWithDetails = 4;
        static final int TRANSACTION_onRegistered = 1;
        static final int TRANSACTION_onRegistering = 2;
        static final int TRANSACTION_onSubscriberAssociatedUriChanged = 6;
        static final int TRANSACTION_onTechnologyChangeFailed = 5;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IImsRegistrationCallback.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IImsRegistrationCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IImsRegistrationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IImsRegistrationCallback)) {
                return (android.telephony.ims.aidl.IImsRegistrationCallback) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IImsRegistrationCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onRegistered";
                case 2:
                    return "onRegistering";
                case 3:
                    return "onDeregistered";
                case 4:
                    return "onDeregisteredWithDetails";
                case 5:
                    return "onTechnologyChangeFailed";
                case 6:
                    return "onSubscriberAssociatedUriChanged";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IImsRegistrationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IImsRegistrationCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes = (android.telephony.ims.ImsRegistrationAttributes) parcel.readTypedObject(android.telephony.ims.ImsRegistrationAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRegistered(imsRegistrationAttributes);
                    return true;
                case 2:
                    android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes2 = (android.telephony.ims.ImsRegistrationAttributes) parcel.readTypedObject(android.telephony.ims.ImsRegistrationAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRegistering(imsRegistrationAttributes2);
                    return true;
                case 3:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDeregistered(imsReasonInfo, readInt, readInt2);
                    return true;
                case 4:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo2 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    android.telephony.ims.SipDetails sipDetails = (android.telephony.ims.SipDetails) parcel.readTypedObject(android.telephony.ims.SipDetails.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeregisteredWithDetails(imsReasonInfo2, readInt3, readInt4, sipDetails);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    android.telephony.ims.ImsReasonInfo imsReasonInfo3 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTechnologyChangeFailed(readInt5, imsReasonInfo3);
                    return true;
                case 6:
                    android.net.Uri[] uriArr = (android.net.Uri[]) parcel.createTypedArray(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSubscriberAssociatedUriChanged(uriArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IImsRegistrationCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IImsRegistrationCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onRegistered(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeTypedObject(imsRegistrationAttributes, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onRegistering(android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeTypedObject(imsRegistrationAttributes, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onDeregistered(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onDeregisteredWithDetails(android.telephony.ims.ImsReasonInfo imsReasonInfo, int i, int i2, android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(sipDetails, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onTechnologyChangeFailed(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onSubscriberAssociatedUriChanged(android.net.Uri[] uriArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeTypedArray(uriArr, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
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
