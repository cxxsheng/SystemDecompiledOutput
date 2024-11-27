package android.telephony.satellite;

/* loaded from: classes3.dex */
public interface ISatelliteCapabilitiesCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.satellite.ISatelliteCapabilitiesCallback";

    void onSatelliteCapabilitiesChanged(android.telephony.satellite.SatelliteCapabilities satelliteCapabilities) throws android.os.RemoteException;

    public static class Default implements android.telephony.satellite.ISatelliteCapabilitiesCallback {
        @Override // android.telephony.satellite.ISatelliteCapabilitiesCallback
        public void onSatelliteCapabilitiesChanged(android.telephony.satellite.SatelliteCapabilities satelliteCapabilities) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.satellite.ISatelliteCapabilitiesCallback {
        static final int TRANSACTION_onSatelliteCapabilitiesChanged = 1;

        public Stub() {
            attachInterface(this, android.telephony.satellite.ISatelliteCapabilitiesCallback.DESCRIPTOR);
        }

        public static android.telephony.satellite.ISatelliteCapabilitiesCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.satellite.ISatelliteCapabilitiesCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.satellite.ISatelliteCapabilitiesCallback)) {
                return (android.telephony.satellite.ISatelliteCapabilitiesCallback) queryLocalInterface;
            }
            return new android.telephony.satellite.ISatelliteCapabilitiesCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
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
                parcel.enforceInterface(android.telephony.satellite.ISatelliteCapabilitiesCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.satellite.ISatelliteCapabilitiesCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.satellite.SatelliteCapabilities satelliteCapabilities = (android.telephony.satellite.SatelliteCapabilities) parcel.readTypedObject(android.telephony.satellite.SatelliteCapabilities.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSatelliteCapabilitiesChanged(satelliteCapabilities);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.satellite.ISatelliteCapabilitiesCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.satellite.ISatelliteCapabilitiesCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteCapabilitiesCallback
            public void onSatelliteCapabilitiesChanged(android.telephony.satellite.SatelliteCapabilities satelliteCapabilities) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.satellite.ISatelliteCapabilitiesCallback.DESCRIPTOR);
                    obtain.writeTypedObject(satelliteCapabilities, 0);
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
