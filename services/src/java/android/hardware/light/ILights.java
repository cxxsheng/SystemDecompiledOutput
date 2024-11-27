package android.hardware.light;

/* loaded from: classes.dex */
public interface ILights extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$light$ILights".replace('$', '.');
    public static final java.lang.String HASH = "33fec8401b6e66bddaeff251e1a2a0f4fa0d3bee";
    public static final int VERSION = 1;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.light.HwLight[] getLights() throws android.os.RemoteException;

    void setLightState(int i, android.hardware.light.HwLightState hwLightState) throws android.os.RemoteException;

    public static class Default implements android.hardware.light.ILights {
        @Override // android.hardware.light.ILights
        public void setLightState(int i, android.hardware.light.HwLightState hwLightState) throws android.os.RemoteException {
        }

        @Override // android.hardware.light.ILights
        public android.hardware.light.HwLight[] getLights() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.light.ILights
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.light.ILights
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.light.ILights {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getLights = 2;
        static final int TRANSACTION_setLightState = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.light.ILights.DESCRIPTOR);
        }

        public static android.hardware.light.ILights asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.light.ILights.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.light.ILights)) {
                return (android.hardware.light.ILights) queryLocalInterface;
            }
            return new android.hardware.light.ILights.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.light.ILights.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.hardware.light.HwLightState hwLightState = (android.hardware.light.HwLightState) parcel.readTypedObject(android.hardware.light.HwLightState.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLightState(readInt, hwLightState);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.hardware.light.HwLight[] lights = getLights();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(lights, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.light.ILights {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.light.ILights.DESCRIPTOR;
            }

            @Override // android.hardware.light.ILights
            public void setLightState(int i, android.hardware.light.HwLightState hwLightState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.light.ILights.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(hwLightState, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setLightState is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.light.ILights
            public android.hardware.light.HwLight[] getLights() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.light.ILights.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getLights is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.light.HwLight[]) obtain2.createTypedArray(android.hardware.light.HwLight.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.light.ILights
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.light.ILights.DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.light.ILights
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.light.ILights.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.light.ILights.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                            obtain2.readException();
                            this.mCachedHash = obtain2.readString();
                            obtain2.recycle();
                            obtain.recycle();
                        } catch (java.lang.Throwable th) {
                            obtain2.recycle();
                            obtain.recycle();
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}
