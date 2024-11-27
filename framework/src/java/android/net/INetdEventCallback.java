package android.net;

/* loaded from: classes2.dex */
public interface INetdEventCallback extends android.os.IInterface {
    public static final int CALLBACK_CALLER_CONNECTIVITY_SERVICE = 0;
    public static final int CALLBACK_CALLER_DEVICE_POLICY = 1;
    public static final int CALLBACK_CALLER_NETWORK_WATCHLIST = 2;

    void onConnectEvent(java.lang.String str, int i, long j, int i2) throws android.os.RemoteException;

    void onDnsEvent(int i, int i2, int i3, java.lang.String str, java.lang.String[] strArr, int i4, long j, int i5) throws android.os.RemoteException;

    void onNat64PrefixEvent(int i, boolean z, java.lang.String str, int i2) throws android.os.RemoteException;

    void onPrivateDnsValidationEvent(int i, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException;

    public static class Default implements android.net.INetdEventCallback {
        @Override // android.net.INetdEventCallback
        public void onDnsEvent(int i, int i2, int i3, java.lang.String str, java.lang.String[] strArr, int i4, long j, int i5) throws android.os.RemoteException {
        }

        @Override // android.net.INetdEventCallback
        public void onNat64PrefixEvent(int i, boolean z, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetdEventCallback
        public void onPrivateDnsValidationEvent(int i, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetdEventCallback
        public void onConnectEvent(java.lang.String str, int i, long j, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.INetdEventCallback {
        public static final java.lang.String DESCRIPTOR = "android.net.INetdEventCallback";
        static final int TRANSACTION_onConnectEvent = 4;
        static final int TRANSACTION_onDnsEvent = 1;
        static final int TRANSACTION_onNat64PrefixEvent = 2;
        static final int TRANSACTION_onPrivateDnsValidationEvent = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.net.INetdEventCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.INetdEventCallback)) {
                return (android.net.INetdEventCallback) queryLocalInterface;
            }
            return new android.net.INetdEventCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDnsEvent";
                case 2:
                    return "onNat64PrefixEvent";
                case 3:
                    return "onPrivateDnsValidationEvent";
                case 4:
                    return "onConnectEvent";
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
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    int readInt4 = parcel.readInt();
                    long readLong = parcel.readLong();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDnsEvent(readInt, readInt2, readInt3, readString, createStringArray, readInt4, readLong, readInt5);
                    return true;
                case 2:
                    int readInt6 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    java.lang.String readString2 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNat64PrefixEvent(readInt6, readBoolean, readString2, readInt7);
                    return true;
                case 3:
                    int readInt8 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onPrivateDnsValidationEvent(readInt8, readString3, readString4, readBoolean2);
                    return true;
                case 4:
                    java.lang.String readString5 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onConnectEvent(readString5, readInt9, readLong2, readInt10);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.INetdEventCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.INetdEventCallback.Stub.DESCRIPTOR;
            }

            @Override // android.net.INetdEventCallback
            public void onDnsEvent(int i, int i2, int i3, java.lang.String str, java.lang.String[] strArr, int i4, long j, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetdEventCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i4);
                    obtain.writeLong(j);
                    obtain.writeInt(i5);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdEventCallback
            public void onNat64PrefixEvent(int i, boolean z, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetdEventCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdEventCallback
            public void onPrivateDnsValidationEvent(int i, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetdEventCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdEventCallback
            public void onConnectEvent(java.lang.String str, int i, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetdEventCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
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
