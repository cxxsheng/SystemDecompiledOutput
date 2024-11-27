package android.service.carrier;

/* loaded from: classes3.dex */
public interface ICarrierMessagingClientService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.carrier.ICarrierMessagingClientService";

    public static class Default implements android.service.carrier.ICarrierMessagingClientService {
        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.carrier.ICarrierMessagingClientService {
        public Stub() {
            attachInterface(this, android.service.carrier.ICarrierMessagingClientService.DESCRIPTOR);
        }

        public static android.service.carrier.ICarrierMessagingClientService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.carrier.ICarrierMessagingClientService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.carrier.ICarrierMessagingClientService)) {
                return (android.service.carrier.ICarrierMessagingClientService) queryLocalInterface;
            }
            return new android.service.carrier.ICarrierMessagingClientService.Stub.Proxy(iBinder);
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
                parcel2.writeString(android.service.carrier.ICarrierMessagingClientService.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements android.service.carrier.ICarrierMessagingClientService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.carrier.ICarrierMessagingClientService.DESCRIPTOR;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
