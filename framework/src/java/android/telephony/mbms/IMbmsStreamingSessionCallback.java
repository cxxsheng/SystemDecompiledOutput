package android.telephony.mbms;

/* loaded from: classes3.dex */
public interface IMbmsStreamingSessionCallback extends android.os.IInterface {
    void onError(int i, java.lang.String str) throws android.os.RemoteException;

    void onMiddlewareReady() throws android.os.RemoteException;

    void onStreamingServicesUpdated(java.util.List<android.telephony.mbms.StreamingServiceInfo> list) throws android.os.RemoteException;

    public static class Default implements android.telephony.mbms.IMbmsStreamingSessionCallback {
        @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
        public void onError(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
        public void onStreamingServicesUpdated(java.util.List<android.telephony.mbms.StreamingServiceInfo> list) throws android.os.RemoteException {
        }

        @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
        public void onMiddlewareReady() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.mbms.IMbmsStreamingSessionCallback {
        public static final java.lang.String DESCRIPTOR = "android.telephony.mbms.IMbmsStreamingSessionCallback";
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onMiddlewareReady = 3;
        static final int TRANSACTION_onStreamingServicesUpdated = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.telephony.mbms.IMbmsStreamingSessionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.mbms.IMbmsStreamingSessionCallback)) {
                return (android.telephony.mbms.IMbmsStreamingSessionCallback) queryLocalInterface;
            }
            return new android.telephony.mbms.IMbmsStreamingSessionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onError";
                case 2:
                    return "onStreamingServicesUpdated";
                case 3:
                    return "onMiddlewareReady";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onError(readInt, readString);
                    return true;
                case 2:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.mbms.StreamingServiceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onStreamingServicesUpdated(createTypedArrayList);
                    return true;
                case 3:
                    onMiddlewareReady();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.mbms.IMbmsStreamingSessionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.mbms.IMbmsStreamingSessionCallback.Stub.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
            public void onError(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.IMbmsStreamingSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
            public void onStreamingServicesUpdated(java.util.List<android.telephony.mbms.StreamingServiceInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.IMbmsStreamingSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
            public void onMiddlewareReady() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.IMbmsStreamingSessionCallback.Stub.DESCRIPTOR);
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
