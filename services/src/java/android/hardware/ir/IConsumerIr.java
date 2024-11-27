package android.hardware.ir;

/* loaded from: classes.dex */
public interface IConsumerIr extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$ir$IConsumerIr".replace('$', '.');
    public static final java.lang.String HASH = "3e04aed366e96850c6164287eaf78a8e4ab071b0";
    public static final int VERSION = 1;

    android.hardware.ir.ConsumerIrFreqRange[] getCarrierFreqs() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void transmit(int i, int[] iArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.ir.IConsumerIr {
        @Override // android.hardware.ir.IConsumerIr
        public android.hardware.ir.ConsumerIrFreqRange[] getCarrierFreqs() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ir.IConsumerIr
        public void transmit(int i, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.ir.IConsumerIr
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.ir.IConsumerIr
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.ir.IConsumerIr {
        static final int TRANSACTION_getCarrierFreqs = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_transmit = 2;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.ir.IConsumerIr.DESCRIPTOR);
        }

        public static android.hardware.ir.IConsumerIr asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.ir.IConsumerIr.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.ir.IConsumerIr)) {
                return (android.hardware.ir.IConsumerIr) queryLocalInterface;
            }
            return new android.hardware.ir.IConsumerIr.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.ir.IConsumerIr.DESCRIPTOR;
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
                    android.hardware.ir.ConsumerIrFreqRange[] carrierFreqs = getCarrierFreqs();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(carrierFreqs, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    transmit(readInt, createIntArray);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.ir.IConsumerIr {
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
                return android.hardware.ir.IConsumerIr.DESCRIPTOR;
            }

            @Override // android.hardware.ir.IConsumerIr
            public android.hardware.ir.ConsumerIrFreqRange[] getCarrierFreqs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ir.IConsumerIr.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCarrierFreqs is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.ir.ConsumerIrFreqRange[]) obtain2.createTypedArray(android.hardware.ir.ConsumerIrFreqRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ir.IConsumerIr
            public void transmit(int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ir.IConsumerIr.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method transmit is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ir.IConsumerIr
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.ir.IConsumerIr.DESCRIPTOR);
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

            @Override // android.hardware.ir.IConsumerIr
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.ir.IConsumerIr.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.ir.IConsumerIr.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
