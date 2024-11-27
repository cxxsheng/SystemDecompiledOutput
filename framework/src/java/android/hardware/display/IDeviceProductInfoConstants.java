package android.hardware.display;

/* loaded from: classes2.dex */
public interface IDeviceProductInfoConstants extends android.os.IInterface {
    public static final int CONNECTION_TO_SINK_BUILT_IN = 1;
    public static final int CONNECTION_TO_SINK_DIRECT = 2;
    public static final int CONNECTION_TO_SINK_TRANSITIVE = 3;
    public static final int CONNECTION_TO_SINK_UNKNOWN = 0;
    public static final java.lang.String DESCRIPTOR = "android.hardware.display.IDeviceProductInfoConstants";

    public static class Default implements android.hardware.display.IDeviceProductInfoConstants {
        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.display.IDeviceProductInfoConstants {
        public Stub() {
            attachInterface(this, android.hardware.display.IDeviceProductInfoConstants.DESCRIPTOR);
        }

        public static android.hardware.display.IDeviceProductInfoConstants asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.display.IDeviceProductInfoConstants.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.display.IDeviceProductInfoConstants)) {
                return (android.hardware.display.IDeviceProductInfoConstants) queryLocalInterface;
            }
            return new android.hardware.display.IDeviceProductInfoConstants.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            return null;
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.display.IDeviceProductInfoConstants.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements android.hardware.display.IDeviceProductInfoConstants {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.display.IDeviceProductInfoConstants.DESCRIPTOR;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
