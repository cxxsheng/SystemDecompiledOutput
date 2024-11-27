package android.media;

/* loaded from: classes2.dex */
public interface IDevicesForAttributesCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IDevicesForAttributesCallback";

    void onDevicesForAttributesChanged(android.media.AudioAttributes audioAttributes, boolean z, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException;

    public static class Default implements android.media.IDevicesForAttributesCallback {
        @Override // android.media.IDevicesForAttributesCallback
        public void onDevicesForAttributesChanged(android.media.AudioAttributes audioAttributes, boolean z, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IDevicesForAttributesCallback {
        static final int TRANSACTION_onDevicesForAttributesChanged = 1;

        public Stub() {
            attachInterface(this, android.media.IDevicesForAttributesCallback.DESCRIPTOR);
        }

        public static android.media.IDevicesForAttributesCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IDevicesForAttributesCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IDevicesForAttributesCallback)) {
                return (android.media.IDevicesForAttributesCallback) queryLocalInterface;
            }
            return new android.media.IDevicesForAttributesCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDevicesForAttributesChanged";
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
                parcel.enforceInterface(android.media.IDevicesForAttributesCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IDevicesForAttributesCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.AudioAttributes audioAttributes = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDevicesForAttributesChanged(audioAttributes, readBoolean, createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IDevicesForAttributesCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IDevicesForAttributesCallback.DESCRIPTOR;
            }

            @Override // android.media.IDevicesForAttributesCallback
            public void onDevicesForAttributesChanged(android.media.AudioAttributes audioAttributes, boolean z, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IDevicesForAttributesCallback.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeBoolean(z);
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
