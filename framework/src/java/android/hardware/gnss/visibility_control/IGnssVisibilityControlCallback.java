package android.hardware.gnss.visibility_control;

/* loaded from: classes2.dex */
public interface IGnssVisibilityControlCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$gnss$visibility_control$IGnssVisibilityControlCallback".replace('$', '.');
    public static final java.lang.String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    public @interface NfwProtocolStack {
        public static final int CTRL_PLANE = 0;
        public static final int IMS = 10;
        public static final int OTHER_PROTOCOL_STACK = 100;
        public static final int SIM = 11;
        public static final int SUPL = 1;
    }

    public @interface NfwRequestor {
        public static final int AUTOMOBILE_CLIENT = 20;
        public static final int CARRIER = 0;
        public static final int GNSS_CHIPSET_VENDOR = 12;
        public static final int MODEM_CHIPSET_VENDOR = 11;
        public static final int OEM = 10;
        public static final int OTHER_CHIPSET_VENDOR = 13;
        public static final int OTHER_REQUESTOR = 100;
    }

    public @interface NfwResponseType {
        public static final int ACCEPTED_LOCATION_PROVIDED = 2;
        public static final int ACCEPTED_NO_LOCATION_PROVIDED = 1;
        public static final int REJECTED = 0;
    }

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    boolean isInEmergencySession() throws android.os.RemoteException;

    void nfwNotifyCb(android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification nfwNotification) throws android.os.RemoteException;

    public static class Default implements android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback {
        @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
        public void nfwNotifyCb(android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification nfwNotification) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
        public boolean isInEmergencySession() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_isInEmergencySession = 2;
        static final int TRANSACTION_nfwNotifyCb = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback)) {
                return (android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback) queryLocalInterface;
            }
            return new android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "nfwNotifyCb";
                case 2:
                    return "isInEmergencySession";
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
                    android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification nfwNotification = (android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification) parcel.readTypedObject(android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification.CREATOR);
                    parcel.enforceNoDataAvail();
                    nfwNotifyCb(nfwNotification);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean isInEmergencySession = isInEmergencySession();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInEmergencySession);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback {
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

            @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
            public void nfwNotifyCb(android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification nfwNotification) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(nfwNotification, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method nfwNotifyCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
            public boolean isInEmergencySession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isInEmergencySession is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
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

            @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
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

    public static class NfwNotification implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification>() { // from class: android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification nfwNotification = new android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification();
                nfwNotification.readFromParcel(parcel);
                return nfwNotification;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification[] newArray(int i) {
                return new android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification[i];
            }
        };
        public boolean inEmergencyMode = false;
        public boolean isCachedLocation = false;
        public java.lang.String otherProtocolStackName;
        public int protocolStack;
        public java.lang.String proxyAppPackageName;
        public int requestor;
        public java.lang.String requestorId;
        public int responseType;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.proxyAppPackageName);
            parcel.writeInt(this.protocolStack);
            parcel.writeString(this.otherProtocolStackName);
            parcel.writeInt(this.requestor);
            parcel.writeString(this.requestorId);
            parcel.writeInt(this.responseType);
            parcel.writeBoolean(this.inEmergencyMode);
            parcel.writeBoolean(this.isCachedLocation);
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
                this.proxyAppPackageName = parcel.readString();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.protocolStack = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.otherProtocolStackName = parcel.readString();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.requestor = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.requestorId = parcel.readString();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.responseType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.inEmergencyMode = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.isCachedLocation = parcel.readBoolean();
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
