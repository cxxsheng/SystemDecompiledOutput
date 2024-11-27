package android.media.tv.ad;

/* loaded from: classes2.dex */
public interface ITvAdServiceCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.ad.ITvAdServiceCallback";

    public static class Default implements android.media.tv.ad.ITvAdServiceCallback {
        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ad.ITvAdServiceCallback {
        public Stub() {
            attachInterface(this, android.media.tv.ad.ITvAdServiceCallback.DESCRIPTOR);
        }

        public static android.media.tv.ad.ITvAdServiceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.ad.ITvAdServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ad.ITvAdServiceCallback)) {
                return (android.media.tv.ad.ITvAdServiceCallback) queryLocalInterface;
            }
            return new android.media.tv.ad.ITvAdServiceCallback.Stub.Proxy(iBinder);
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
                parcel2.writeString(android.media.tv.ad.ITvAdServiceCallback.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements android.media.tv.ad.ITvAdServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ad.ITvAdServiceCallback.DESCRIPTOR;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
