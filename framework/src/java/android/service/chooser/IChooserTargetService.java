package android.service.chooser;

/* loaded from: classes3.dex */
public interface IChooserTargetService extends android.os.IInterface {
    void getChooserTargets(android.content.ComponentName componentName, android.content.IntentFilter intentFilter, android.service.chooser.IChooserTargetResult iChooserTargetResult) throws android.os.RemoteException;

    public static class Default implements android.service.chooser.IChooserTargetService {
        @Override // android.service.chooser.IChooserTargetService
        public void getChooserTargets(android.content.ComponentName componentName, android.content.IntentFilter intentFilter, android.service.chooser.IChooserTargetResult iChooserTargetResult) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.chooser.IChooserTargetService {
        public static final java.lang.String DESCRIPTOR = "android.service.chooser.IChooserTargetService";
        static final int TRANSACTION_getChooserTargets = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.chooser.IChooserTargetService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.chooser.IChooserTargetService)) {
                return (android.service.chooser.IChooserTargetService) queryLocalInterface;
            }
            return new android.service.chooser.IChooserTargetService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getChooserTargets";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.content.IntentFilter intentFilter = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    android.service.chooser.IChooserTargetResult asInterface = android.service.chooser.IChooserTargetResult.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getChooserTargets(componentName, intentFilter, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.chooser.IChooserTargetService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.chooser.IChooserTargetService.Stub.DESCRIPTOR;
            }

            @Override // android.service.chooser.IChooserTargetService
            public void getChooserTargets(android.content.ComponentName componentName, android.content.IntentFilter intentFilter, android.service.chooser.IChooserTargetResult iChooserTargetResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.chooser.IChooserTargetService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeStrongInterface(iChooserTargetResult);
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
