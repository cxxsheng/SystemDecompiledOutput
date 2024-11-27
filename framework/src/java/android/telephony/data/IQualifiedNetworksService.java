package android.telephony.data;

/* loaded from: classes3.dex */
public interface IQualifiedNetworksService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.data.IQualifiedNetworksService";

    void createNetworkAvailabilityProvider(int i, android.telephony.data.IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) throws android.os.RemoteException;

    void removeNetworkAvailabilityProvider(int i) throws android.os.RemoteException;

    void reportEmergencyDataNetworkPreferredTransportChanged(int i, int i2) throws android.os.RemoteException;

    void reportThrottleStatusChanged(int i, java.util.List<android.telephony.data.ThrottleStatus> list) throws android.os.RemoteException;

    public static class Default implements android.telephony.data.IQualifiedNetworksService {
        @Override // android.telephony.data.IQualifiedNetworksService
        public void createNetworkAvailabilityProvider(int i, android.telephony.data.IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void removeNetworkAvailabilityProvider(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void reportThrottleStatusChanged(int i, java.util.List<android.telephony.data.ThrottleStatus> list) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void reportEmergencyDataNetworkPreferredTransportChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.data.IQualifiedNetworksService {
        static final int TRANSACTION_createNetworkAvailabilityProvider = 1;
        static final int TRANSACTION_removeNetworkAvailabilityProvider = 2;
        static final int TRANSACTION_reportEmergencyDataNetworkPreferredTransportChanged = 4;
        static final int TRANSACTION_reportThrottleStatusChanged = 3;

        public Stub() {
            attachInterface(this, android.telephony.data.IQualifiedNetworksService.DESCRIPTOR);
        }

        public static android.telephony.data.IQualifiedNetworksService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.data.IQualifiedNetworksService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.data.IQualifiedNetworksService)) {
                return (android.telephony.data.IQualifiedNetworksService) queryLocalInterface;
            }
            return new android.telephony.data.IQualifiedNetworksService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createNetworkAvailabilityProvider";
                case 2:
                    return "removeNetworkAvailabilityProvider";
                case 3:
                    return "reportThrottleStatusChanged";
                case 4:
                    return "reportEmergencyDataNetworkPreferredTransportChanged";
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
                parcel.enforceInterface(android.telephony.data.IQualifiedNetworksService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.data.IQualifiedNetworksService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.telephony.data.IQualifiedNetworksServiceCallback asInterface = android.telephony.data.IQualifiedNetworksServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createNetworkAvailabilityProvider(readInt, asInterface);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeNetworkAvailabilityProvider(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.data.ThrottleStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportThrottleStatusChanged(readInt3, createTypedArrayList);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportEmergencyDataNetworkPreferredTransportChanged(readInt4, readInt5);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.data.IQualifiedNetworksService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.data.IQualifiedNetworksService.DESCRIPTOR;
            }

            @Override // android.telephony.data.IQualifiedNetworksService
            public void createNetworkAvailabilityProvider(int i, android.telephony.data.IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IQualifiedNetworksService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iQualifiedNetworksServiceCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksService
            public void removeNetworkAvailabilityProvider(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IQualifiedNetworksService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksService
            public void reportThrottleStatusChanged(int i, java.util.List<android.telephony.data.ThrottleStatus> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IQualifiedNetworksService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksService
            public void reportEmergencyDataNetworkPreferredTransportChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IQualifiedNetworksService.DESCRIPTOR);
                    obtain.writeInt(i);
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
