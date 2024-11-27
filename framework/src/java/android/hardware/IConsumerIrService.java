package android.hardware;

/* loaded from: classes.dex */
public interface IConsumerIrService extends android.os.IInterface {
    int[] getCarrierFrequencies() throws android.os.RemoteException;

    boolean hasIrEmitter() throws android.os.RemoteException;

    void transmit(java.lang.String str, int i, int[] iArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.IConsumerIrService {
        @Override // android.hardware.IConsumerIrService
        public boolean hasIrEmitter() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.IConsumerIrService
        public void transmit(java.lang.String str, int i, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.IConsumerIrService
        public int[] getCarrierFrequencies() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.IConsumerIrService {
        public static final java.lang.String DESCRIPTOR = "android.hardware.IConsumerIrService";
        static final int TRANSACTION_getCarrierFrequencies = 3;
        static final int TRANSACTION_hasIrEmitter = 1;
        static final int TRANSACTION_transmit = 2;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.hardware.IConsumerIrService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.IConsumerIrService)) {
                return (android.hardware.IConsumerIrService) queryLocalInterface;
            }
            return new android.hardware.IConsumerIrService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "hasIrEmitter";
                case 2:
                    return "transmit";
                case 3:
                    return "getCarrierFrequencies";
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
                    boolean hasIrEmitter = hasIrEmitter();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasIrEmitter);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    transmit(readString, readInt, createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int[] carrierFrequencies = getCarrierFrequencies();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(carrierFrequencies);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.IConsumerIrService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.IConsumerIrService.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.IConsumerIrService
            public boolean hasIrEmitter() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.IConsumerIrService.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IConsumerIrService
            public void transmit(java.lang.String str, int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.IConsumerIrService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IConsumerIrService
            public int[] getCarrierFrequencies() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.IConsumerIrService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void transmit_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TRANSMIT_IR, getCallingPid(), getCallingUid());
        }

        protected void getCarrierFrequencies_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TRANSMIT_IR, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
