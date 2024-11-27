package com.nvidia.NvCPLSvc;

/* loaded from: classes5.dex */
public interface INvCPLRemoteService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.nvidia.NvCPLSvc.INvCPLRemoteService";

    int getActiveProfileType(java.lang.String str) throws android.os.RemoteException;

    byte[] getAppProfileSetting3DVStruct(java.lang.String str) throws android.os.RemoteException;

    int getAppProfileSettingBoolean(java.lang.String str, int i) throws android.os.RemoteException;

    int getAppProfileSettingInt(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String getAppProfileSettingString(java.lang.String str, int i) throws android.os.RemoteException;

    com.nvidia.NvCPLSvc.NvAppProfile[] getAppProfiles(java.lang.String[] strArr) throws android.os.RemoteException;

    java.lang.String getDeviceSerial() throws android.os.RemoteException;

    java.util.List<com.nvidia.NvCPLSvc.NvSaverAppInfo> getNvSaverAppInfo(int i) throws android.os.RemoteException;

    int[] getProfileTypes(java.lang.String str) throws android.os.RemoteException;

    android.os.IBinder getToolsApiInterface(java.lang.String str) throws android.os.RemoteException;

    void handleIntent(android.content.Intent intent) throws android.os.RemoteException;

    void powerHint(java.lang.String str) throws android.os.RemoteException;

    boolean setActiveProfileType(java.lang.String str, int i) throws android.os.RemoteException;

    boolean setAppProfileSetting(java.lang.String str, int i, int i2, java.lang.String str2) throws android.os.RemoteException;

    boolean setNvSaverAppInfo(java.lang.String str, int i) throws android.os.RemoteException;

    boolean setNvSaverAppInfoAll(java.util.List<com.nvidia.NvCPLSvc.NvSaverAppInfo> list) throws android.os.RemoteException;

    public static class Default implements com.nvidia.NvCPLSvc.INvCPLRemoteService {
        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public android.os.IBinder getToolsApiInterface(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public java.lang.String getAppProfileSettingString(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public int getAppProfileSettingInt(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public int getAppProfileSettingBoolean(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public byte[] getAppProfileSetting3DVStruct(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public void handleIntent(android.content.Intent intent) throws android.os.RemoteException {
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public boolean setNvSaverAppInfo(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public boolean setNvSaverAppInfoAll(java.util.List<com.nvidia.NvCPLSvc.NvSaverAppInfo> list) throws android.os.RemoteException {
            return false;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public java.util.List<com.nvidia.NvCPLSvc.NvSaverAppInfo> getNvSaverAppInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public boolean setAppProfileSetting(java.lang.String str, int i, int i2, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public int getActiveProfileType(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public int[] getProfileTypes(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public boolean setActiveProfileType(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public com.nvidia.NvCPLSvc.NvAppProfile[] getAppProfiles(java.lang.String[] strArr) throws android.os.RemoteException {
            return null;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public java.lang.String getDeviceSerial() throws android.os.RemoteException {
            return null;
        }

        @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
        public void powerHint(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.nvidia.NvCPLSvc.INvCPLRemoteService {
        static final int TRANSACTION_getActiveProfileType = 11;
        static final int TRANSACTION_getAppProfileSetting3DVStruct = 5;
        static final int TRANSACTION_getAppProfileSettingBoolean = 4;
        static final int TRANSACTION_getAppProfileSettingInt = 3;
        static final int TRANSACTION_getAppProfileSettingString = 2;
        static final int TRANSACTION_getAppProfiles = 14;
        static final int TRANSACTION_getDeviceSerial = 15;
        static final int TRANSACTION_getNvSaverAppInfo = 9;
        static final int TRANSACTION_getProfileTypes = 12;
        static final int TRANSACTION_getToolsApiInterface = 1;
        static final int TRANSACTION_handleIntent = 6;
        static final int TRANSACTION_powerHint = 16;
        static final int TRANSACTION_setActiveProfileType = 13;
        static final int TRANSACTION_setAppProfileSetting = 10;
        static final int TRANSACTION_setNvSaverAppInfo = 7;
        static final int TRANSACTION_setNvSaverAppInfoAll = 8;

        public Stub() {
            attachInterface(this, com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
        }

        public static com.nvidia.NvCPLSvc.INvCPLRemoteService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.nvidia.NvCPLSvc.INvCPLRemoteService)) {
                return (com.nvidia.NvCPLSvc.INvCPLRemoteService) queryLocalInterface;
            }
            return new com.nvidia.NvCPLSvc.INvCPLRemoteService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getToolsApiInterface";
                case 2:
                    return "getAppProfileSettingString";
                case 3:
                    return "getAppProfileSettingInt";
                case 4:
                    return "getAppProfileSettingBoolean";
                case 5:
                    return "getAppProfileSetting3DVStruct";
                case 6:
                    return "handleIntent";
                case 7:
                    return "setNvSaverAppInfo";
                case 8:
                    return "setNvSaverAppInfoAll";
                case 9:
                    return "getNvSaverAppInfo";
                case 10:
                    return "setAppProfileSetting";
                case 11:
                    return "getActiveProfileType";
                case 12:
                    return "getProfileTypes";
                case 13:
                    return "setActiveProfileType";
                case 14:
                    return "getAppProfiles";
                case 15:
                    return "getDeviceSerial";
                case 16:
                    return "powerHint";
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
                parcel.enforceInterface(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder toolsApiInterface = getToolsApiInterface(readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(toolsApiInterface);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String appProfileSettingString = getAppProfileSettingString(readString2, readInt);
                    parcel2.writeNoException();
                    parcel2.writeString(appProfileSettingString);
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appProfileSettingInt = getAppProfileSettingInt(readString3, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(appProfileSettingInt);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appProfileSettingBoolean = getAppProfileSettingBoolean(readString4, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(appProfileSettingBoolean);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] appProfileSetting3DVStruct = getAppProfileSetting3DVStruct(readString5);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(appProfileSetting3DVStruct);
                    return true;
                case 6:
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleIntent(intent);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString6 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nvSaverAppInfo = setNvSaverAppInfo(readString6, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nvSaverAppInfo);
                    return true;
                case 8:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(com.nvidia.NvCPLSvc.NvSaverAppInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean nvSaverAppInfoAll = setNvSaverAppInfoAll(createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nvSaverAppInfoAll);
                    return true;
                case 9:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<com.nvidia.NvCPLSvc.NvSaverAppInfo> nvSaverAppInfo2 = getNvSaverAppInfo(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(nvSaverAppInfo2, 1);
                    return true;
                case 10:
                    java.lang.String readString7 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean appProfileSetting = setAppProfileSetting(readString7, readInt6, readInt7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appProfileSetting);
                    return true;
                case 11:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int activeProfileType = getActiveProfileType(readString9);
                    parcel2.writeNoException();
                    parcel2.writeInt(activeProfileType);
                    return true;
                case 12:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] profileTypes = getProfileTypes(readString10);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(profileTypes);
                    return true;
                case 13:
                    java.lang.String readString11 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean activeProfileType2 = setActiveProfileType(readString11, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activeProfileType2);
                    return true;
                case 14:
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    com.nvidia.NvCPLSvc.NvAppProfile[] appProfiles = getAppProfiles(createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(appProfiles, 1);
                    return true;
                case 15:
                    java.lang.String deviceSerial = getDeviceSerial();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceSerial);
                    return true;
                case 16:
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    powerHint(readString12);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.nvidia.NvCPLSvc.INvCPLRemoteService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR;
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public android.os.IBinder getToolsApiInterface(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public java.lang.String getAppProfileSettingString(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public int getAppProfileSettingInt(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public int getAppProfileSettingBoolean(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public byte[] getAppProfileSetting3DVStruct(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public void handleIntent(android.content.Intent intent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public boolean setNvSaverAppInfo(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public boolean setNvSaverAppInfoAll(java.util.List<com.nvidia.NvCPLSvc.NvSaverAppInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public java.util.List<com.nvidia.NvCPLSvc.NvSaverAppInfo> getNvSaverAppInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(com.nvidia.NvCPLSvc.NvSaverAppInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public boolean setAppProfileSetting(java.lang.String str, int i, int i2, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public int getActiveProfileType(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public int[] getProfileTypes(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public boolean setActiveProfileType(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public com.nvidia.NvCPLSvc.NvAppProfile[] getAppProfiles(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.nvidia.NvCPLSvc.NvAppProfile[]) obtain2.createTypedArray(com.nvidia.NvCPLSvc.NvAppProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public java.lang.String getDeviceSerial() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nvidia.NvCPLSvc.INvCPLRemoteService
            public void powerHint(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.nvidia.NvCPLSvc.INvCPLRemoteService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }
    }
}
