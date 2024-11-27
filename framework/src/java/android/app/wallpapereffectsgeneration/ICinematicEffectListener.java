package android.app.wallpapereffectsgeneration;

/* loaded from: classes.dex */
public interface ICinematicEffectListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.wallpapereffectsgeneration.ICinematicEffectListener";

    void onCinematicEffectGenerated(android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) throws android.os.RemoteException;

    public static class Default implements android.app.wallpapereffectsgeneration.ICinematicEffectListener {
        @Override // android.app.wallpapereffectsgeneration.ICinematicEffectListener
        public void onCinematicEffectGenerated(android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.wallpapereffectsgeneration.ICinematicEffectListener {
        static final int TRANSACTION_onCinematicEffectGenerated = 1;

        public Stub() {
            attachInterface(this, android.app.wallpapereffectsgeneration.ICinematicEffectListener.DESCRIPTOR);
        }

        public static android.app.wallpapereffectsgeneration.ICinematicEffectListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.wallpapereffectsgeneration.ICinematicEffectListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.wallpapereffectsgeneration.ICinematicEffectListener)) {
                return (android.app.wallpapereffectsgeneration.ICinematicEffectListener) queryLocalInterface;
            }
            return new android.app.wallpapereffectsgeneration.ICinematicEffectListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCinematicEffectGenerated";
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
                parcel.enforceInterface(android.app.wallpapereffectsgeneration.ICinematicEffectListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.wallpapereffectsgeneration.ICinematicEffectListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse = (android.app.wallpapereffectsgeneration.CinematicEffectResponse) parcel.readTypedObject(android.app.wallpapereffectsgeneration.CinematicEffectResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCinematicEffectGenerated(cinematicEffectResponse);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.wallpapereffectsgeneration.ICinematicEffectListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.wallpapereffectsgeneration.ICinematicEffectListener.DESCRIPTOR;
            }

            @Override // android.app.wallpapereffectsgeneration.ICinematicEffectListener
            public void onCinematicEffectGenerated(android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.wallpapereffectsgeneration.ICinematicEffectListener.DESCRIPTOR);
                    obtain.writeTypedObject(cinematicEffectResponse, 0);
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
