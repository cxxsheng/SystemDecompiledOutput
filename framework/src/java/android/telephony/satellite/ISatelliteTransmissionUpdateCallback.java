package android.telephony.satellite;

/* loaded from: classes3.dex */
public interface ISatelliteTransmissionUpdateCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.satellite.ISatelliteTransmissionUpdateCallback";

    void onReceiveDatagramStateChanged(int i, int i2, int i3) throws android.os.RemoteException;

    void onSatellitePositionChanged(android.telephony.satellite.PointingInfo pointingInfo) throws android.os.RemoteException;

    void onSendDatagramStateChanged(int i, int i2, int i3) throws android.os.RemoteException;

    public static class Default implements android.telephony.satellite.ISatelliteTransmissionUpdateCallback {
        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSendDatagramStateChanged(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onReceiveDatagramStateChanged(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSatellitePositionChanged(android.telephony.satellite.PointingInfo pointingInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.satellite.ISatelliteTransmissionUpdateCallback {
        static final int TRANSACTION_onReceiveDatagramStateChanged = 2;
        static final int TRANSACTION_onSatellitePositionChanged = 3;
        static final int TRANSACTION_onSendDatagramStateChanged = 1;

        public Stub() {
            attachInterface(this, android.telephony.satellite.ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
        }

        public static android.telephony.satellite.ISatelliteTransmissionUpdateCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.satellite.ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.satellite.ISatelliteTransmissionUpdateCallback)) {
                return (android.telephony.satellite.ISatelliteTransmissionUpdateCallback) queryLocalInterface;
            }
            return new android.telephony.satellite.ISatelliteTransmissionUpdateCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSendDatagramStateChanged";
                case 2:
                    return "onReceiveDatagramStateChanged";
                case 3:
                    return "onSatellitePositionChanged";
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
                parcel.enforceInterface(android.telephony.satellite.ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.satellite.ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSendDatagramStateChanged(readInt, readInt2, readInt3);
                    return true;
                case 2:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onReceiveDatagramStateChanged(readInt4, readInt5, readInt6);
                    return true;
                case 3:
                    android.telephony.satellite.PointingInfo pointingInfo = (android.telephony.satellite.PointingInfo) parcel.readTypedObject(android.telephony.satellite.PointingInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSatellitePositionChanged(pointingInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.satellite.ISatelliteTransmissionUpdateCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.satellite.ISatelliteTransmissionUpdateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
            public void onSendDatagramStateChanged(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
            public void onReceiveDatagramStateChanged(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
            public void onSatellitePositionChanged(android.telephony.satellite.PointingInfo pointingInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.ISatelliteTransmissionUpdateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(pointingInfo, 0);
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
