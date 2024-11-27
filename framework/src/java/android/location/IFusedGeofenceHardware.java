package android.location;

/* loaded from: classes2.dex */
public interface IFusedGeofenceHardware extends android.os.IInterface {
    void addGeofences(android.hardware.location.GeofenceHardwareRequestParcelable[] geofenceHardwareRequestParcelableArr) throws android.os.RemoteException;

    boolean isSupported() throws android.os.RemoteException;

    void modifyGeofenceOptions(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException;

    void pauseMonitoringGeofence(int i) throws android.os.RemoteException;

    void removeGeofences(int[] iArr) throws android.os.RemoteException;

    void resumeMonitoringGeofence(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.location.IFusedGeofenceHardware {
        @Override // android.location.IFusedGeofenceHardware
        public boolean isSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.location.IFusedGeofenceHardware
        public void addGeofences(android.hardware.location.GeofenceHardwareRequestParcelable[] geofenceHardwareRequestParcelableArr) throws android.os.RemoteException {
        }

        @Override // android.location.IFusedGeofenceHardware
        public void removeGeofences(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.location.IFusedGeofenceHardware
        public void pauseMonitoringGeofence(int i) throws android.os.RemoteException {
        }

        @Override // android.location.IFusedGeofenceHardware
        public void resumeMonitoringGeofence(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.location.IFusedGeofenceHardware
        public void modifyGeofenceOptions(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.location.IFusedGeofenceHardware {
        public static final java.lang.String DESCRIPTOR = "android.location.IFusedGeofenceHardware";
        static final int TRANSACTION_addGeofences = 2;
        static final int TRANSACTION_isSupported = 1;
        static final int TRANSACTION_modifyGeofenceOptions = 6;
        static final int TRANSACTION_pauseMonitoringGeofence = 4;
        static final int TRANSACTION_removeGeofences = 3;
        static final int TRANSACTION_resumeMonitoringGeofence = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.location.IFusedGeofenceHardware asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.location.IFusedGeofenceHardware)) {
                return (android.location.IFusedGeofenceHardware) queryLocalInterface;
            }
            return new android.location.IFusedGeofenceHardware.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isSupported";
                case 2:
                    return "addGeofences";
                case 3:
                    return "removeGeofences";
                case 4:
                    return "pauseMonitoringGeofence";
                case 5:
                    return "resumeMonitoringGeofence";
                case 6:
                    return "modifyGeofenceOptions";
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
                    boolean isSupported = isSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupported);
                    return true;
                case 2:
                    android.hardware.location.GeofenceHardwareRequestParcelable[] geofenceHardwareRequestParcelableArr = (android.hardware.location.GeofenceHardwareRequestParcelable[]) parcel.createTypedArray(android.hardware.location.GeofenceHardwareRequestParcelable.CREATOR);
                    parcel.enforceNoDataAvail();
                    addGeofences(geofenceHardwareRequestParcelableArr);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeGeofences(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    pauseMonitoringGeofence(readInt);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resumeMonitoringGeofence(readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    modifyGeofenceOptions(readInt4, readInt5, readInt6, readInt7, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.location.IFusedGeofenceHardware {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.location.IFusedGeofenceHardware.Stub.DESCRIPTOR;
            }

            @Override // android.location.IFusedGeofenceHardware
            public boolean isSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.location.IFusedGeofenceHardware.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IFusedGeofenceHardware
            public void addGeofences(android.hardware.location.GeofenceHardwareRequestParcelable[] geofenceHardwareRequestParcelableArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.location.IFusedGeofenceHardware.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(geofenceHardwareRequestParcelableArr, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IFusedGeofenceHardware
            public void removeGeofences(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.location.IFusedGeofenceHardware.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IFusedGeofenceHardware
            public void pauseMonitoringGeofence(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.location.IFusedGeofenceHardware.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IFusedGeofenceHardware
            public void resumeMonitoringGeofence(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.location.IFusedGeofenceHardware.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.location.IFusedGeofenceHardware
            public void modifyGeofenceOptions(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.location.IFusedGeofenceHardware.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
