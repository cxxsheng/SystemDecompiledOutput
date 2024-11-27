package android.media;

/* loaded from: classes2.dex */
public interface ICapturePresetDevicesRoleDispatcher extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.ICapturePresetDevicesRoleDispatcher";

    void dispatchDevicesRoleChanged(int i, int i2, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException;

    public static class Default implements android.media.ICapturePresetDevicesRoleDispatcher {
        @Override // android.media.ICapturePresetDevicesRoleDispatcher
        public void dispatchDevicesRoleChanged(int i, int i2, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.ICapturePresetDevicesRoleDispatcher {
        static final int TRANSACTION_dispatchDevicesRoleChanged = 1;

        public Stub() {
            attachInterface(this, android.media.ICapturePresetDevicesRoleDispatcher.DESCRIPTOR);
        }

        public static android.media.ICapturePresetDevicesRoleDispatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.ICapturePresetDevicesRoleDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.ICapturePresetDevicesRoleDispatcher)) {
                return (android.media.ICapturePresetDevicesRoleDispatcher) queryLocalInterface;
            }
            return new android.media.ICapturePresetDevicesRoleDispatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchDevicesRoleChanged";
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
                parcel.enforceInterface(android.media.ICapturePresetDevicesRoleDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.ICapturePresetDevicesRoleDispatcher.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchDevicesRoleChanged(readInt, readInt2, createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.ICapturePresetDevicesRoleDispatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.ICapturePresetDevicesRoleDispatcher.DESCRIPTOR;
            }

            @Override // android.media.ICapturePresetDevicesRoleDispatcher
            public void dispatchDevicesRoleChanged(int i, int i2, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ICapturePresetDevicesRoleDispatcher.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedList(list, 0);
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
