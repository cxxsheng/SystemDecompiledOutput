package android.content.pm;

/* loaded from: classes.dex */
public interface IShortcutChangeCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.content.pm.IShortcutChangeCallback";

    void onShortcutsAddedOrUpdated(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void onShortcutsRemoved(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IShortcutChangeCallback {
        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsAddedOrUpdated(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsRemoved(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IShortcutChangeCallback {
        static final int TRANSACTION_onShortcutsAddedOrUpdated = 1;
        static final int TRANSACTION_onShortcutsRemoved = 2;

        public Stub() {
            attachInterface(this, android.content.pm.IShortcutChangeCallback.DESCRIPTOR);
        }

        public static android.content.pm.IShortcutChangeCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.pm.IShortcutChangeCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IShortcutChangeCallback)) {
                return (android.content.pm.IShortcutChangeCallback) queryLocalInterface;
            }
            return new android.content.pm.IShortcutChangeCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onShortcutsAddedOrUpdated";
                case 2:
                    return "onShortcutsRemoved";
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
                parcel.enforceInterface(android.content.pm.IShortcutChangeCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.pm.IShortcutChangeCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.content.pm.ShortcutInfo.CREATOR);
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onShortcutsAddedOrUpdated(readString, createTypedArrayList, userHandle);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.content.pm.ShortcutInfo.CREATOR);
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onShortcutsRemoved(readString2, createTypedArrayList2, userHandle2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IShortcutChangeCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IShortcutChangeCallback.DESCRIPTOR;
            }

            @Override // android.content.pm.IShortcutChangeCallback
            public void onShortcutsAddedOrUpdated(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutChangeCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutChangeCallback
            public void onShortcutsRemoved(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IShortcutChangeCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
