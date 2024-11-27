package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface ISipTransport extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.ISipTransport";

    void createSipDelegate(int i, android.telephony.ims.DelegateRequest delegateRequest, android.telephony.ims.aidl.ISipDelegateStateCallback iSipDelegateStateCallback, android.telephony.ims.aidl.ISipDelegateMessageCallback iSipDelegateMessageCallback) throws android.os.RemoteException;

    void destroySipDelegate(android.telephony.ims.aidl.ISipDelegate iSipDelegate, int i) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.ISipTransport {
        @Override // android.telephony.ims.aidl.ISipTransport
        public void createSipDelegate(int i, android.telephony.ims.DelegateRequest delegateRequest, android.telephony.ims.aidl.ISipDelegateStateCallback iSipDelegateStateCallback, android.telephony.ims.aidl.ISipDelegateMessageCallback iSipDelegateMessageCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipTransport
        public void destroySipDelegate(android.telephony.ims.aidl.ISipDelegate iSipDelegate, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.ISipTransport {
        static final int TRANSACTION_createSipDelegate = 1;
        static final int TRANSACTION_destroySipDelegate = 2;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.ISipTransport.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.ISipTransport asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.ISipTransport.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.ISipTransport)) {
                return (android.telephony.ims.aidl.ISipTransport) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.ISipTransport.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSipDelegate";
                case 2:
                    return "destroySipDelegate";
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
                parcel.enforceInterface(android.telephony.ims.aidl.ISipTransport.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.ISipTransport.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.telephony.ims.DelegateRequest delegateRequest = (android.telephony.ims.DelegateRequest) parcel.readTypedObject(android.telephony.ims.DelegateRequest.CREATOR);
                    android.telephony.ims.aidl.ISipDelegateStateCallback asInterface = android.telephony.ims.aidl.ISipDelegateStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.aidl.ISipDelegateMessageCallback asInterface2 = android.telephony.ims.aidl.ISipDelegateMessageCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createSipDelegate(readInt, delegateRequest, asInterface, asInterface2);
                    return true;
                case 2:
                    android.telephony.ims.aidl.ISipDelegate asInterface3 = android.telephony.ims.aidl.ISipDelegate.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroySipDelegate(asInterface3, readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.ISipTransport {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.ISipTransport.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISipTransport
            public void createSipDelegate(int i, android.telephony.ims.DelegateRequest delegateRequest, android.telephony.ims.aidl.ISipDelegateStateCallback iSipDelegateStateCallback, android.telephony.ims.aidl.ISipDelegateMessageCallback iSipDelegateMessageCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISipTransport.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(delegateRequest, 0);
                    obtain.writeStrongInterface(iSipDelegateStateCallback);
                    obtain.writeStrongInterface(iSipDelegateMessageCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipTransport
            public void destroySipDelegate(android.telephony.ims.aidl.ISipDelegate iSipDelegate, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISipTransport.DESCRIPTOR);
                    obtain.writeStrongInterface(iSipDelegate);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
