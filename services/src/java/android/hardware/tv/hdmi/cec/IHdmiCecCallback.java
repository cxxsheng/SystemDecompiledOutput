package android.hardware.tv.hdmi.cec;

/* loaded from: classes.dex */
public interface IHdmiCecCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$tv$hdmi$cec$IHdmiCecCallback".replace('$', '.');
    public static final java.lang.String HASH = "cd956e3a0c2e6ade71693c85e9f0aeffa221ea26";
    public static final int VERSION = 1;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void onCecMessage(android.hardware.tv.hdmi.cec.CecMessage cecMessage) throws android.os.RemoteException;

    public static class Default implements android.hardware.tv.hdmi.cec.IHdmiCecCallback {
        @Override // android.hardware.tv.hdmi.cec.IHdmiCecCallback
        public void onCecMessage(android.hardware.tv.hdmi.cec.CecMessage cecMessage) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCecCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCecCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.tv.hdmi.cec.IHdmiCecCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onCecMessage = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.tv.hdmi.cec.IHdmiCecCallback.DESCRIPTOR);
        }

        public static android.hardware.tv.hdmi.cec.IHdmiCecCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.tv.hdmi.cec.IHdmiCecCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.tv.hdmi.cec.IHdmiCecCallback)) {
                return (android.hardware.tv.hdmi.cec.IHdmiCecCallback) queryLocalInterface;
            }
            return new android.hardware.tv.hdmi.cec.IHdmiCecCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.tv.hdmi.cec.IHdmiCecCallback.DESCRIPTOR;
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
                    android.hardware.tv.hdmi.cec.CecMessage cecMessage = (android.hardware.tv.hdmi.cec.CecMessage) parcel.readTypedObject(android.hardware.tv.hdmi.cec.CecMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCecMessage(cecMessage);
                    break;
            }
            return true;
        }

        private static class Proxy implements android.hardware.tv.hdmi.cec.IHdmiCecCallback {
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
                return android.hardware.tv.hdmi.cec.IHdmiCecCallback.DESCRIPTOR;
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCecCallback
            public void onCecMessage(android.hardware.tv.hdmi.cec.CecMessage cecMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCecCallback.DESCRIPTOR);
                    obtain.writeTypedObject(cecMessage, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onCecMessage is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.cec.IHdmiCecCallback
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCecCallback.DESCRIPTOR);
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

            @Override // android.hardware.tv.hdmi.cec.IHdmiCecCallback
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.tv.hdmi.cec.IHdmiCecCallback.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.tv.hdmi.cec.IHdmiCecCallback.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
