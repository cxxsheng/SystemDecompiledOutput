package com.android.internal.graphics.fonts;

/* loaded from: classes4.dex */
public interface IFontManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.graphics.fonts.IFontManager";

    android.text.FontConfig getFontConfig() throws android.os.RemoteException;

    int updateFontFamily(java.util.List<android.graphics.fonts.FontUpdateRequest> list, int i) throws android.os.RemoteException;

    public static class Default implements com.android.internal.graphics.fonts.IFontManager {
        @Override // com.android.internal.graphics.fonts.IFontManager
        public android.text.FontConfig getFontConfig() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.graphics.fonts.IFontManager
        public int updateFontFamily(java.util.List<android.graphics.fonts.FontUpdateRequest> list, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.graphics.fonts.IFontManager {
        static final int TRANSACTION_getFontConfig = 1;
        static final int TRANSACTION_updateFontFamily = 2;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, com.android.internal.graphics.fonts.IFontManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static com.android.internal.graphics.fonts.IFontManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.graphics.fonts.IFontManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.graphics.fonts.IFontManager)) {
                return (com.android.internal.graphics.fonts.IFontManager) queryLocalInterface;
            }
            return new com.android.internal.graphics.fonts.IFontManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getFontConfig";
                case 2:
                    return "updateFontFamily";
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
                parcel.enforceInterface(com.android.internal.graphics.fonts.IFontManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.graphics.fonts.IFontManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.text.FontConfig fontConfig = getFontConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fontConfig, 1);
                    return true;
                case 2:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.graphics.fonts.FontUpdateRequest.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateFontFamily = updateFontFamily(createTypedArrayList, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateFontFamily);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.graphics.fonts.IFontManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.graphics.fonts.IFontManager.DESCRIPTOR;
            }

            @Override // com.android.internal.graphics.fonts.IFontManager
            public android.text.FontConfig getFontConfig() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.graphics.fonts.IFontManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.text.FontConfig) obtain2.readTypedObject(android.text.FontConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.graphics.fonts.IFontManager
            public int updateFontFamily(java.util.List<android.graphics.fonts.FontUpdateRequest> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.graphics.fonts.IFontManager.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getFontConfig_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_FONTS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
