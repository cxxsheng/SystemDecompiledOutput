package android.telephony;

/* loaded from: classes3.dex */
public interface IIntegerConsumer extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.IIntegerConsumer";

    void accept(int i) throws android.os.RemoteException;

    public static class Default implements android.telephony.IIntegerConsumer {
        @Override // android.telephony.IIntegerConsumer
        public void accept(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.IIntegerConsumer {
        static final int TRANSACTION_accept = 1;

        public Stub() {
            attachInterface(this, android.telephony.IIntegerConsumer.DESCRIPTOR);
        }

        public static android.telephony.IIntegerConsumer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.IIntegerConsumer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.IIntegerConsumer)) {
                return (android.telephony.IIntegerConsumer) queryLocalInterface;
            }
            return new android.telephony.IIntegerConsumer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "accept";
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
                parcel.enforceInterface(android.telephony.IIntegerConsumer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.IIntegerConsumer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    accept(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.IIntegerConsumer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.IIntegerConsumer.DESCRIPTOR;
            }

            @Override // android.telephony.IIntegerConsumer
            public void accept(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.IIntegerConsumer.DESCRIPTOR);
                    obtain.writeInt(i);
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
