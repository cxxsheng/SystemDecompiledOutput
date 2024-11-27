package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface ISipDelegate extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.ISipDelegate";

    void cleanupSession(java.lang.String str) throws android.os.RemoteException;

    void notifyMessageReceiveError(java.lang.String str, int i) throws android.os.RemoteException;

    void notifyMessageReceived(java.lang.String str) throws android.os.RemoteException;

    void sendMessage(android.telephony.ims.SipMessage sipMessage, long j) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.ISipDelegate {
        @Override // android.telephony.ims.aidl.ISipDelegate
        public void sendMessage(android.telephony.ims.SipMessage sipMessage, long j) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void notifyMessageReceived(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void notifyMessageReceiveError(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void cleanupSession(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.ISipDelegate {
        static final int TRANSACTION_cleanupSession = 4;
        static final int TRANSACTION_notifyMessageReceiveError = 3;
        static final int TRANSACTION_notifyMessageReceived = 2;
        static final int TRANSACTION_sendMessage = 1;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.ISipDelegate.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.ISipDelegate asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.ISipDelegate.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.ISipDelegate)) {
                return (android.telephony.ims.aidl.ISipDelegate) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.ISipDelegate.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "sendMessage";
                case 2:
                    return "notifyMessageReceived";
                case 3:
                    return "notifyMessageReceiveError";
                case 4:
                    return "cleanupSession";
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
                parcel.enforceInterface(android.telephony.ims.aidl.ISipDelegate.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.ISipDelegate.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.ims.SipMessage sipMessage = (android.telephony.ims.SipMessage) parcel.readTypedObject(android.telephony.ims.SipMessage.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    sendMessage(sipMessage, readLong);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyMessageReceived(readString);
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyMessageReceiveError(readString2, readInt);
                    return true;
                case 4:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cleanupSession(readString3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.ISipDelegate {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.ISipDelegate.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void sendMessage(android.telephony.ims.SipMessage sipMessage, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISipDelegate.DESCRIPTOR);
                    obtain.writeTypedObject(sipMessage, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void notifyMessageReceived(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISipDelegate.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void notifyMessageReceiveError(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISipDelegate.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void cleanupSession(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.ISipDelegate.DESCRIPTOR);
                    obtain.writeString(str);
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
