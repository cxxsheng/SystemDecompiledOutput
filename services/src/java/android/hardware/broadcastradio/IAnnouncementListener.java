package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public interface IAnnouncementListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$broadcastradio$IAnnouncementListener".replace('$', '.');
    public static final java.lang.String HASH = "bff68a8bc8b7cc191ab62bee10f7df8e79494467";
    public static final int VERSION = 2;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void onListUpdated(android.hardware.broadcastradio.Announcement[] announcementArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.broadcastradio.IAnnouncementListener {
        @Override // android.hardware.broadcastradio.IAnnouncementListener
        public void onListUpdated(android.hardware.broadcastradio.Announcement[] announcementArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.IAnnouncementListener
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.broadcastradio.IAnnouncementListener
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.broadcastradio.IAnnouncementListener {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onListUpdated = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.broadcastradio.IAnnouncementListener.DESCRIPTOR);
        }

        public static android.hardware.broadcastradio.IAnnouncementListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.broadcastradio.IAnnouncementListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.broadcastradio.IAnnouncementListener)) {
                return (android.hardware.broadcastradio.IAnnouncementListener) queryLocalInterface;
            }
            return new android.hardware.broadcastradio.IAnnouncementListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.broadcastradio.IAnnouncementListener.DESCRIPTOR;
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
                    android.hardware.broadcastradio.Announcement[] announcementArr = (android.hardware.broadcastradio.Announcement[]) parcel.createTypedArray(android.hardware.broadcastradio.Announcement.CREATOR);
                    parcel.enforceNoDataAvail();
                    onListUpdated(announcementArr);
                    break;
            }
            return true;
        }

        private static class Proxy implements android.hardware.broadcastradio.IAnnouncementListener {
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
                return android.hardware.broadcastradio.IAnnouncementListener.DESCRIPTOR;
            }

            @Override // android.hardware.broadcastradio.IAnnouncementListener
            public void onListUpdated(android.hardware.broadcastradio.Announcement[] announcementArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IAnnouncementListener.DESCRIPTOR);
                    obtain.writeTypedArray(announcementArr, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onListUpdated is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IAnnouncementListener
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.broadcastradio.IAnnouncementListener.DESCRIPTOR);
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

            @Override // android.hardware.broadcastradio.IAnnouncementListener
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.broadcastradio.IAnnouncementListener.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.broadcastradio.IAnnouncementListener.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
