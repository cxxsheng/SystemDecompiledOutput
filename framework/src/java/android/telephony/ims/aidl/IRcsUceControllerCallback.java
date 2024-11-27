package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IRcsUceControllerCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IRcsUceControllerCallback";

    void onCapabilitiesReceived(java.util.List<android.telephony.ims.RcsContactUceCapability> list) throws android.os.RemoteException;

    void onComplete(android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException;

    void onError(int i, long j, android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IRcsUceControllerCallback {
        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onCapabilitiesReceived(java.util.List<android.telephony.ims.RcsContactUceCapability> list) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onComplete(android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onError(int i, long j, android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IRcsUceControllerCallback {
        static final int TRANSACTION_onCapabilitiesReceived = 1;
        static final int TRANSACTION_onComplete = 2;
        static final int TRANSACTION_onError = 3;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IRcsUceControllerCallback.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IRcsUceControllerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IRcsUceControllerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IRcsUceControllerCallback)) {
                return (android.telephony.ims.aidl.IRcsUceControllerCallback) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IRcsUceControllerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCapabilitiesReceived";
                case 2:
                    return "onComplete";
                case 3:
                    return "onError";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IRcsUceControllerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IRcsUceControllerCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.ims.RcsContactUceCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCapabilitiesReceived(createTypedArrayList);
                    return true;
                case 2:
                    android.telephony.ims.SipDetails sipDetails = (android.telephony.ims.SipDetails) parcel.readTypedObject(android.telephony.ims.SipDetails.CREATOR);
                    parcel.enforceNoDataAvail();
                    onComplete(sipDetails);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    android.telephony.ims.SipDetails sipDetails2 = (android.telephony.ims.SipDetails) parcel.readTypedObject(android.telephony.ims.SipDetails.CREATOR);
                    parcel.enforceNoDataAvail();
                    onError(readInt, readLong, sipDetails2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IRcsUceControllerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IRcsUceControllerCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
            public void onCapabilitiesReceived(java.util.List<android.telephony.ims.RcsContactUceCapability> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IRcsUceControllerCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
            public void onComplete(android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IRcsUceControllerCallback.DESCRIPTOR);
                    obtain.writeTypedObject(sipDetails, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
            public void onError(int i, long j, android.telephony.ims.SipDetails sipDetails) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IRcsUceControllerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(sipDetails, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
