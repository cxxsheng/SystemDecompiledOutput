package android.telephony.satellite.stub;

/* loaded from: classes3.dex */
public interface ISatelliteListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.satellite.stub.ISatelliteListener";

    void onNtnSignalStrengthChanged(android.telephony.satellite.stub.NtnSignalStrength ntnSignalStrength) throws android.os.RemoteException;

    void onPendingDatagrams() throws android.os.RemoteException;

    void onSatelliteCapabilitiesChanged(android.telephony.satellite.stub.SatelliteCapabilities satelliteCapabilities) throws android.os.RemoteException;

    void onSatelliteDatagramReceived(android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram, int i) throws android.os.RemoteException;

    void onSatelliteModemStateChanged(int i) throws android.os.RemoteException;

    void onSatellitePositionChanged(android.telephony.satellite.stub.PointingInfo pointingInfo) throws android.os.RemoteException;

    void onSatelliteProvisionStateChanged(boolean z) throws android.os.RemoteException;

    public static class Default implements android.telephony.satellite.stub.ISatelliteListener {
        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onSatelliteProvisionStateChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onSatelliteDatagramReceived(android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram, int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onPendingDatagrams() throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onSatellitePositionChanged(android.telephony.satellite.stub.PointingInfo pointingInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onSatelliteModemStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onNtnSignalStrengthChanged(android.telephony.satellite.stub.NtnSignalStrength ntnSignalStrength) throws android.os.RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onSatelliteCapabilitiesChanged(android.telephony.satellite.stub.SatelliteCapabilities satelliteCapabilities) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.satellite.stub.ISatelliteListener {
        static final int TRANSACTION_onNtnSignalStrengthChanged = 6;
        static final int TRANSACTION_onPendingDatagrams = 3;
        static final int TRANSACTION_onSatelliteCapabilitiesChanged = 7;
        static final int TRANSACTION_onSatelliteDatagramReceived = 2;
        static final int TRANSACTION_onSatelliteModemStateChanged = 5;
        static final int TRANSACTION_onSatellitePositionChanged = 4;
        static final int TRANSACTION_onSatelliteProvisionStateChanged = 1;

        public Stub() {
            attachInterface(this, android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR);
        }

        public static android.telephony.satellite.stub.ISatelliteListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.satellite.stub.ISatelliteListener)) {
                return (android.telephony.satellite.stub.ISatelliteListener) queryLocalInterface;
            }
            return new android.telephony.satellite.stub.ISatelliteListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSatelliteProvisionStateChanged";
                case 2:
                    return "onSatelliteDatagramReceived";
                case 3:
                    return "onPendingDatagrams";
                case 4:
                    return "onSatellitePositionChanged";
                case 5:
                    return "onSatelliteModemStateChanged";
                case 6:
                    return "onNtnSignalStrengthChanged";
                case 7:
                    return "onSatelliteCapabilitiesChanged";
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
                parcel.enforceInterface(android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onSatelliteProvisionStateChanged(readBoolean);
                    return true;
                case 2:
                    android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram = (android.telephony.satellite.stub.SatelliteDatagram) parcel.readTypedObject(android.telephony.satellite.stub.SatelliteDatagram.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSatelliteDatagramReceived(satelliteDatagram, readInt);
                    return true;
                case 3:
                    onPendingDatagrams();
                    return true;
                case 4:
                    android.telephony.satellite.stub.PointingInfo pointingInfo = (android.telephony.satellite.stub.PointingInfo) parcel.readTypedObject(android.telephony.satellite.stub.PointingInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSatellitePositionChanged(pointingInfo);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSatelliteModemStateChanged(readInt2);
                    return true;
                case 6:
                    android.telephony.satellite.stub.NtnSignalStrength ntnSignalStrength = (android.telephony.satellite.stub.NtnSignalStrength) parcel.readTypedObject(android.telephony.satellite.stub.NtnSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNtnSignalStrengthChanged(ntnSignalStrength);
                    return true;
                case 7:
                    android.telephony.satellite.stub.SatelliteCapabilities satelliteCapabilities = (android.telephony.satellite.stub.SatelliteCapabilities) parcel.readTypedObject(android.telephony.satellite.stub.SatelliteCapabilities.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSatelliteCapabilitiesChanged(satelliteCapabilities);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.satellite.stub.ISatelliteListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onSatelliteProvisionStateChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onSatelliteDatagramReceived(android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR);
                    obtain.writeTypedObject(satelliteDatagram, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onPendingDatagrams() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onSatellitePositionChanged(android.telephony.satellite.stub.PointingInfo pointingInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR);
                    obtain.writeTypedObject(pointingInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onSatelliteModemStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onNtnSignalStrengthChanged(android.telephony.satellite.stub.NtnSignalStrength ntnSignalStrength) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR);
                    obtain.writeTypedObject(ntnSignalStrength, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onSatelliteCapabilitiesChanged(android.telephony.satellite.stub.SatelliteCapabilities satelliteCapabilities) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.stub.ISatelliteListener.DESCRIPTOR);
                    obtain.writeTypedObject(satelliteCapabilities, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
