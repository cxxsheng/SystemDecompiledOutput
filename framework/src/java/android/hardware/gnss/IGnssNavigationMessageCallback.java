package android.hardware.gnss;

/* loaded from: classes2.dex */
public interface IGnssNavigationMessageCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$gnss$IGnssNavigationMessageCallback".replace('$', '.');
    public static final java.lang.String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void gnssNavigationMessageCb(android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage gnssNavigationMessage) throws android.os.RemoteException;

    public static class Default implements android.hardware.gnss.IGnssNavigationMessageCallback {
        @Override // android.hardware.gnss.IGnssNavigationMessageCallback
        public void gnssNavigationMessageCb(android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage gnssNavigationMessage) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssNavigationMessageCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssNavigationMessageCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.gnss.IGnssNavigationMessageCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_gnssNavigationMessageCb = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.gnss.IGnssNavigationMessageCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.IGnssNavigationMessageCallback)) {
                return (android.hardware.gnss.IGnssNavigationMessageCallback) queryLocalInterface;
            }
            return new android.hardware.gnss.IGnssNavigationMessageCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "gnssNavigationMessageCb";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            java.lang.String str = DESCRIPTOR;
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
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage gnssNavigationMessage = (android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage) parcel.readTypedObject(android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    gnssNavigationMessageCb(gnssNavigationMessage);
                    parcel2.writeNoException();
                    break;
            }
            return true;
        }

        private static class Proxy implements android.hardware.gnss.IGnssNavigationMessageCallback {
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
                return DESCRIPTOR;
            }

            @Override // android.hardware.gnss.IGnssNavigationMessageCallback
            public void gnssNavigationMessageCb(android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage gnssNavigationMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(gnssNavigationMessage, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssNavigationMessageCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssNavigationMessageCallback
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
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

            @Override // android.hardware.gnss.IGnssNavigationMessageCallback
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
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
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }

    public static class GnssNavigationMessage implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage>() { // from class: android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage gnssNavigationMessage = new android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage();
                gnssNavigationMessage.readFromParcel(parcel);
                return gnssNavigationMessage;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage[] newArray(int i) {
                return new android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage[i];
            }
        };
        public static final int STATUS_PARITY_PASSED = 1;
        public static final int STATUS_PARITY_REBUILT = 2;
        public static final int STATUS_UNKNOWN = 0;
        public byte[] data;
        public int type;
        public int svid = 0;
        public int status = 0;
        public int messageId = 0;
        public int submessageId = 0;

        public @interface GnssNavigationMessageType {
            public static final int BDS_CNAV1 = 1283;
            public static final int BDS_CNAV2 = 1284;
            public static final int BDS_D1 = 1281;
            public static final int BDS_D2 = 1282;
            public static final int GAL_F = 1538;
            public static final int GAL_I = 1537;
            public static final int GLO_L1CA = 769;
            public static final int GPS_CNAV2 = 260;
            public static final int GPS_L1CA = 257;
            public static final int GPS_L2CNAV = 258;
            public static final int GPS_L5CNAV = 259;
            public static final int IRN_L5CA = 1793;
            public static final int QZS_L1CA = 1025;
            public static final int SBS = 513;
            public static final int UNKNOWN = 0;
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.svid);
            parcel.writeInt(this.type);
            parcel.writeInt(this.status);
            parcel.writeInt(this.messageId);
            parcel.writeInt(this.submessageId);
            parcel.writeByteArray(this.data);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.svid = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.status = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.messageId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.submessageId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.data = parcel.createByteArray();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
