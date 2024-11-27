package android.companion;

/* loaded from: classes.dex */
public interface ICompanionDeviceService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.ICompanionDeviceService";

    void onDeviceAppeared(android.companion.AssociationInfo associationInfo) throws android.os.RemoteException;

    void onDeviceDisappeared(android.companion.AssociationInfo associationInfo) throws android.os.RemoteException;

    void onDevicePresenceEvent(android.companion.DevicePresenceEvent devicePresenceEvent) throws android.os.RemoteException;

    public static class Default implements android.companion.ICompanionDeviceService {
        @Override // android.companion.ICompanionDeviceService
        public void onDeviceAppeared(android.companion.AssociationInfo associationInfo) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDeviceDisappeared(android.companion.AssociationInfo associationInfo) throws android.os.RemoteException {
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDevicePresenceEvent(android.companion.DevicePresenceEvent devicePresenceEvent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.ICompanionDeviceService {
        static final int TRANSACTION_onDeviceAppeared = 1;
        static final int TRANSACTION_onDeviceDisappeared = 2;
        static final int TRANSACTION_onDevicePresenceEvent = 3;

        public Stub() {
            attachInterface(this, android.companion.ICompanionDeviceService.DESCRIPTOR);
        }

        public static android.companion.ICompanionDeviceService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.ICompanionDeviceService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.ICompanionDeviceService)) {
                return (android.companion.ICompanionDeviceService) queryLocalInterface;
            }
            return new android.companion.ICompanionDeviceService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDeviceAppeared";
                case 2:
                    return "onDeviceDisappeared";
                case 3:
                    return "onDevicePresenceEvent";
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
                parcel.enforceInterface(android.companion.ICompanionDeviceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.ICompanionDeviceService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.companion.AssociationInfo associationInfo = (android.companion.AssociationInfo) parcel.readTypedObject(android.companion.AssociationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeviceAppeared(associationInfo);
                    return true;
                case 2:
                    android.companion.AssociationInfo associationInfo2 = (android.companion.AssociationInfo) parcel.readTypedObject(android.companion.AssociationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeviceDisappeared(associationInfo2);
                    return true;
                case 3:
                    android.companion.DevicePresenceEvent devicePresenceEvent = (android.companion.DevicePresenceEvent) parcel.readTypedObject(android.companion.DevicePresenceEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDevicePresenceEvent(devicePresenceEvent);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.ICompanionDeviceService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.ICompanionDeviceService.DESCRIPTOR;
            }

            @Override // android.companion.ICompanionDeviceService
            public void onDeviceAppeared(android.companion.AssociationInfo associationInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceService.DESCRIPTOR);
                    obtain.writeTypedObject(associationInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceService
            public void onDeviceDisappeared(android.companion.AssociationInfo associationInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceService.DESCRIPTOR);
                    obtain.writeTypedObject(associationInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceService
            public void onDevicePresenceEvent(android.companion.DevicePresenceEvent devicePresenceEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.ICompanionDeviceService.DESCRIPTOR);
                    obtain.writeTypedObject(devicePresenceEvent, 0);
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
