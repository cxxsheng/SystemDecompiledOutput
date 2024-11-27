package android.window;

/* loaded from: classes4.dex */
public interface IWindowContainerToken extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IWindowContainerToken";

    public static class Default implements android.window.IWindowContainerToken {
        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IWindowContainerToken {
        public Stub() {
            attachInterface(this, android.window.IWindowContainerToken.DESCRIPTOR);
        }

        public static android.window.IWindowContainerToken asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IWindowContainerToken.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IWindowContainerToken)) {
                return (android.window.IWindowContainerToken) queryLocalInterface;
            }
            return new android.window.IWindowContainerToken.Stub.Proxy(iBinder);
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
                parcel2.writeString(android.window.IWindowContainerToken.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements android.window.IWindowContainerToken {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IWindowContainerToken.DESCRIPTOR;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
