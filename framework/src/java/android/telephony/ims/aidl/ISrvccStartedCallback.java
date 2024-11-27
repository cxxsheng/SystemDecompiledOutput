package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface ISrvccStartedCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.ISrvccStartedCallback";

    void onSrvccCallNotified(java.util.List<android.telephony.ims.SrvccCall> list) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.ISrvccStartedCallback {
        @Override // android.telephony.ims.aidl.ISrvccStartedCallback
        public void onSrvccCallNotified(java.util.List<android.telephony.ims.SrvccCall> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.ISrvccStartedCallback {
        static final int TRANSACTION_onSrvccCallNotified = 1;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.ISrvccStartedCallback.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.ISrvccStartedCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.ISrvccStartedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.ISrvccStartedCallback)) {
                return (android.telephony.ims.aidl.ISrvccStartedCallback) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.ISrvccStartedCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSrvccCallNotified";
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
                parcel.enforceInterface(android.telephony.ims.aidl.ISrvccStartedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.ISrvccStartedCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.ims.SrvccCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSrvccCallNotified(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.ISrvccStartedCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.ISrvccStartedCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISrvccStartedCallback
            public void onSrvccCallNotified(java.util.List<android.telephony.ims.SrvccCall> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISrvccStartedCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
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
