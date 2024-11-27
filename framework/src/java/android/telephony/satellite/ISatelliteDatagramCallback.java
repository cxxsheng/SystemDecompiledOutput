package android.telephony.satellite;

/* loaded from: classes3.dex */
public interface ISatelliteDatagramCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.satellite.ISatelliteDatagramCallback";

    void onSatelliteDatagramReceived(long j, android.telephony.satellite.SatelliteDatagram satelliteDatagram, int i, com.android.internal.telephony.IVoidConsumer iVoidConsumer) throws android.os.RemoteException;

    public static class Default implements android.telephony.satellite.ISatelliteDatagramCallback {
        @Override // android.telephony.satellite.ISatelliteDatagramCallback
        public void onSatelliteDatagramReceived(long j, android.telephony.satellite.SatelliteDatagram satelliteDatagram, int i, com.android.internal.telephony.IVoidConsumer iVoidConsumer) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.satellite.ISatelliteDatagramCallback {
        static final int TRANSACTION_onSatelliteDatagramReceived = 1;

        public Stub() {
            attachInterface(this, android.telephony.satellite.ISatelliteDatagramCallback.DESCRIPTOR);
        }

        public static android.telephony.satellite.ISatelliteDatagramCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.satellite.ISatelliteDatagramCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.satellite.ISatelliteDatagramCallback)) {
                return (android.telephony.satellite.ISatelliteDatagramCallback) queryLocalInterface;
            }
            return new android.telephony.satellite.ISatelliteDatagramCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSatelliteDatagramReceived";
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
                parcel.enforceInterface(android.telephony.satellite.ISatelliteDatagramCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.satellite.ISatelliteDatagramCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    android.telephony.satellite.SatelliteDatagram satelliteDatagram = (android.telephony.satellite.SatelliteDatagram) parcel.readTypedObject(android.telephony.satellite.SatelliteDatagram.CREATOR);
                    int readInt = parcel.readInt();
                    com.android.internal.telephony.IVoidConsumer asInterface = com.android.internal.telephony.IVoidConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSatelliteDatagramReceived(readLong, satelliteDatagram, readInt, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.satellite.ISatelliteDatagramCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.satellite.ISatelliteDatagramCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteDatagramCallback
            public void onSatelliteDatagramReceived(long j, android.telephony.satellite.SatelliteDatagram satelliteDatagram, int i, com.android.internal.telephony.IVoidConsumer iVoidConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.ISatelliteDatagramCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(satelliteDatagram, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVoidConsumer);
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
