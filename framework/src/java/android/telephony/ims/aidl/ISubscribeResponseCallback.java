package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface ISubscribeResponseCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.ISubscribeResponseCallback";

    void onCommandError(int i) throws android.os.RemoteException;

    void onNetworkResponse(android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException;

    void onNotifyCapabilitiesUpdate(java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void onResourceTerminated(java.util.List<android.telephony.ims.RcsContactTerminatedReason> list) throws android.os.RemoteException;

    void onTerminated(java.lang.String str, long j) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.ISubscribeResponseCallback {
        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onCommandError(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onNetworkResponse(android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onNotifyCapabilitiesUpdate(java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onResourceTerminated(java.util.List<android.telephony.ims.RcsContactTerminatedReason> list) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onTerminated(java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.ISubscribeResponseCallback {
        static final int TRANSACTION_onCommandError = 1;
        static final int TRANSACTION_onNetworkResponse = 2;
        static final int TRANSACTION_onNotifyCapabilitiesUpdate = 3;
        static final int TRANSACTION_onResourceTerminated = 4;
        static final int TRANSACTION_onTerminated = 5;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.ISubscribeResponseCallback.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.ISubscribeResponseCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.ISubscribeResponseCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.ISubscribeResponseCallback)) {
                return (android.telephony.ims.aidl.ISubscribeResponseCallback) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.ISubscribeResponseCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCommandError";
                case 2:
                    return "onNetworkResponse";
                case 3:
                    return "onNotifyCapabilitiesUpdate";
                case 4:
                    return "onResourceTerminated";
                case 5:
                    return "onTerminated";
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
                parcel.enforceInterface(android.telephony.ims.aidl.ISubscribeResponseCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.ISubscribeResponseCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCommandError(readInt);
                    return true;
                case 2:
                    android.telephony.ims.SipDetails sipDetails = (android.telephony.ims.SipDetails) parcel.readTypedObject(android.telephony.ims.SipDetails.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNetworkResponse(sipDetails);
                    return true;
                case 3:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    onNotifyCapabilitiesUpdate(createStringArrayList);
                    return true;
                case 4:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.ims.RcsContactTerminatedReason.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResourceTerminated(createTypedArrayList);
                    return true;
                case 5:
                    java.lang.String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onTerminated(readString, readLong);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.ISubscribeResponseCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.ISubscribeResponseCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onCommandError(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISubscribeResponseCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onNetworkResponse(android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISubscribeResponseCallback.DESCRIPTOR);
                    obtain.writeTypedObject(sipDetails, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onNotifyCapabilitiesUpdate(java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISubscribeResponseCallback.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onResourceTerminated(java.util.List<android.telephony.ims.RcsContactTerminatedReason> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISubscribeResponseCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onTerminated(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISubscribeResponseCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
