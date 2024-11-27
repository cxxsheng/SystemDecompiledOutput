package android.app;

/* loaded from: classes.dex */
public interface IWallpaperManager extends android.os.IInterface {
    void addOnLocalColorsChangedListener(android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, java.util.List<android.graphics.RectF> list, int i, int i2, int i3) throws android.os.RemoteException;

    void clearWallpaper(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    android.graphics.Rect getBitmapCrop(android.graphics.Point point, int[] iArr, java.util.List<android.graphics.Rect> list) throws android.os.RemoteException;

    java.util.List getBitmapCrops(java.util.List<android.graphics.Point> list, int i, boolean z, int i2) throws android.os.RemoteException;

    java.util.List getFutureBitmapCrops(android.graphics.Point point, java.util.List<android.graphics.Point> list, int[] iArr, java.util.List<android.graphics.Rect> list2) throws android.os.RemoteException;

    int getHeightHint(int i) throws android.os.RemoteException;

    java.lang.String getName() throws android.os.RemoteException;

    @java.lang.Deprecated
    android.os.ParcelFileDescriptor getWallpaper(java.lang.String str, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException;

    android.app.WallpaperColors getWallpaperColors(int i, int i2, int i3) throws android.os.RemoteException;

    float getWallpaperDimAmount() throws android.os.RemoteException;

    int getWallpaperIdForUser(int i, int i2) throws android.os.RemoteException;

    android.app.WallpaperInfo getWallpaperInfo(int i) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor getWallpaperInfoFile(int i) throws android.os.RemoteException;

    android.app.WallpaperInfo getWallpaperInfoWithFlags(int i, int i2) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor getWallpaperWithFeature(java.lang.String str, java.lang.String str2, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, android.os.Bundle bundle, int i2, boolean z) throws android.os.RemoteException;

    int getWidthHint(int i) throws android.os.RemoteException;

    boolean hasNamedWallpaper(java.lang.String str) throws android.os.RemoteException;

    boolean isSetWallpaperAllowed(java.lang.String str) throws android.os.RemoteException;

    boolean isStaticWallpaper(int i) throws android.os.RemoteException;

    boolean isWallpaperBackupEligible(int i, int i2) throws android.os.RemoteException;

    boolean isWallpaperSupported(java.lang.String str) throws android.os.RemoteException;

    boolean lockScreenWallpaperExists() throws android.os.RemoteException;

    void notifyGoingToSleep(int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException;

    void notifyWakingUp(int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException;

    void registerWallpaperColorsCallback(android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws android.os.RemoteException;

    void removeOnLocalColorsChangedListener(android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, java.util.List<android.graphics.RectF> list, int i, int i2, int i3) throws android.os.RemoteException;

    void setDimensionHints(int i, int i2, java.lang.String str, int i3) throws android.os.RemoteException;

    void setDisplayPadding(android.graphics.Rect rect, java.lang.String str, int i) throws android.os.RemoteException;

    void setInAmbientMode(boolean z, long j) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor setWallpaper(java.lang.String str, java.lang.String str2, int[] iArr, java.util.List<android.graphics.Rect> list, boolean z, android.os.Bundle bundle, int i, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i2) throws android.os.RemoteException;

    void setWallpaperComponent(android.content.ComponentName componentName) throws android.os.RemoteException;

    void setWallpaperComponentChecked(android.content.ComponentName componentName, java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void setWallpaperDimAmount(float f) throws android.os.RemoteException;

    void settingsRestored() throws android.os.RemoteException;

    void unregisterWallpaperColorsCallback(android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.app.IWallpaperManager {
        @Override // android.app.IWallpaperManager
        public android.os.ParcelFileDescriptor setWallpaper(java.lang.String str, java.lang.String str2, int[] iArr, java.util.List<android.graphics.Rect> list, boolean z, android.os.Bundle bundle, int i, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void setWallpaperComponentChecked(android.content.ComponentName componentName, java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void setWallpaperComponent(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public android.os.ParcelFileDescriptor getWallpaper(java.lang.String str, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public android.os.ParcelFileDescriptor getWallpaperWithFeature(java.lang.String str, java.lang.String str2, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, android.os.Bundle bundle, int i2, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public java.util.List getBitmapCrops(java.util.List<android.graphics.Point> list, int i, boolean z, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public java.util.List getFutureBitmapCrops(android.graphics.Point point, java.util.List<android.graphics.Point> list, int[] iArr, java.util.List<android.graphics.Rect> list2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public android.graphics.Rect getBitmapCrop(android.graphics.Point point, int[] iArr, java.util.List<android.graphics.Rect> list) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public int getWallpaperIdForUser(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public android.app.WallpaperInfo getWallpaperInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public android.app.WallpaperInfo getWallpaperInfoWithFlags(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public android.os.ParcelFileDescriptor getWallpaperInfoFile(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void clearWallpaper(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public boolean hasNamedWallpaper(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public void setDimensionHints(int i, int i2, java.lang.String str, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public int getWidthHint(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public int getHeightHint(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IWallpaperManager
        public void setDisplayPadding(android.graphics.Rect rect, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public java.lang.String getName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void settingsRestored() throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public boolean isWallpaperSupported(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isSetWallpaperAllowed(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isWallpaperBackupEligible(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public android.app.WallpaperColors getWallpaperColors(int i, int i2, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IWallpaperManager
        public void removeOnLocalColorsChangedListener(android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, java.util.List<android.graphics.RectF> list, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void addOnLocalColorsChangedListener(android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, java.util.List<android.graphics.RectF> list, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void registerWallpaperColorsCallback(android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void unregisterWallpaperColorsCallback(android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void setInAmbientMode(boolean z, long j) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void notifyWakingUp(int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void notifyGoingToSleep(int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public void setWallpaperDimAmount(float f) throws android.os.RemoteException {
        }

        @Override // android.app.IWallpaperManager
        public float getWallpaperDimAmount() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.app.IWallpaperManager
        public boolean lockScreenWallpaperExists() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IWallpaperManager
        public boolean isStaticWallpaper(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IWallpaperManager {
        public static final java.lang.String DESCRIPTOR = "android.app.IWallpaperManager";
        static final int TRANSACTION_addOnLocalColorsChangedListener = 26;
        static final int TRANSACTION_clearWallpaper = 13;
        static final int TRANSACTION_getBitmapCrop = 8;
        static final int TRANSACTION_getBitmapCrops = 6;
        static final int TRANSACTION_getFutureBitmapCrops = 7;
        static final int TRANSACTION_getHeightHint = 17;
        static final int TRANSACTION_getName = 19;
        static final int TRANSACTION_getWallpaper = 4;
        static final int TRANSACTION_getWallpaperColors = 24;
        static final int TRANSACTION_getWallpaperDimAmount = 33;
        static final int TRANSACTION_getWallpaperIdForUser = 9;
        static final int TRANSACTION_getWallpaperInfo = 10;
        static final int TRANSACTION_getWallpaperInfoFile = 12;
        static final int TRANSACTION_getWallpaperInfoWithFlags = 11;
        static final int TRANSACTION_getWallpaperWithFeature = 5;
        static final int TRANSACTION_getWidthHint = 16;
        static final int TRANSACTION_hasNamedWallpaper = 14;
        static final int TRANSACTION_isSetWallpaperAllowed = 22;
        static final int TRANSACTION_isStaticWallpaper = 35;
        static final int TRANSACTION_isWallpaperBackupEligible = 23;
        static final int TRANSACTION_isWallpaperSupported = 21;
        static final int TRANSACTION_lockScreenWallpaperExists = 34;
        static final int TRANSACTION_notifyGoingToSleep = 31;
        static final int TRANSACTION_notifyWakingUp = 30;
        static final int TRANSACTION_registerWallpaperColorsCallback = 27;
        static final int TRANSACTION_removeOnLocalColorsChangedListener = 25;
        static final int TRANSACTION_setDimensionHints = 15;
        static final int TRANSACTION_setDisplayPadding = 18;
        static final int TRANSACTION_setInAmbientMode = 29;
        static final int TRANSACTION_setWallpaper = 1;
        static final int TRANSACTION_setWallpaperComponent = 3;
        static final int TRANSACTION_setWallpaperComponentChecked = 2;
        static final int TRANSACTION_setWallpaperDimAmount = 32;
        static final int TRANSACTION_settingsRestored = 20;
        static final int TRANSACTION_unregisterWallpaperColorsCallback = 28;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IWallpaperManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IWallpaperManager)) {
                return (android.app.IWallpaperManager) queryLocalInterface;
            }
            return new android.app.IWallpaperManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setWallpaper";
                case 2:
                    return "setWallpaperComponentChecked";
                case 3:
                    return "setWallpaperComponent";
                case 4:
                    return "getWallpaper";
                case 5:
                    return "getWallpaperWithFeature";
                case 6:
                    return "getBitmapCrops";
                case 7:
                    return "getFutureBitmapCrops";
                case 8:
                    return "getBitmapCrop";
                case 9:
                    return "getWallpaperIdForUser";
                case 10:
                    return "getWallpaperInfo";
                case 11:
                    return "getWallpaperInfoWithFlags";
                case 12:
                    return "getWallpaperInfoFile";
                case 13:
                    return "clearWallpaper";
                case 14:
                    return "hasNamedWallpaper";
                case 15:
                    return "setDimensionHints";
                case 16:
                    return "getWidthHint";
                case 17:
                    return "getHeightHint";
                case 18:
                    return "setDisplayPadding";
                case 19:
                    return "getName";
                case 20:
                    return "settingsRestored";
                case 21:
                    return "isWallpaperSupported";
                case 22:
                    return "isSetWallpaperAllowed";
                case 23:
                    return "isWallpaperBackupEligible";
                case 24:
                    return "getWallpaperColors";
                case 25:
                    return "removeOnLocalColorsChangedListener";
                case 26:
                    return "addOnLocalColorsChangedListener";
                case 27:
                    return "registerWallpaperColorsCallback";
                case 28:
                    return "unregisterWallpaperColorsCallback";
                case 29:
                    return "setInAmbientMode";
                case 30:
                    return "notifyWakingUp";
                case 31:
                    return "notifyGoingToSleep";
                case 32:
                    return "setWallpaperDimAmount";
                case 33:
                    return "getWallpaperDimAmount";
                case 34:
                    return "lockScreenWallpaperExists";
                case 35:
                    return "isStaticWallpaper";
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
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.graphics.Rect.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    android.os.Bundle bundle = new android.os.Bundle();
                    int readInt = parcel.readInt();
                    android.app.IWallpaperManagerCallback asInterface = android.app.IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor wallpaper = setWallpaper(readString, readString2, createIntArray, createTypedArrayList, readBoolean, bundle, readInt, asInterface, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaper, 1);
                    parcel2.writeTypedObject(bundle, 1);
                    return true;
                case 2:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWallpaperComponentChecked(componentName, readString3, readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setWallpaperComponent(componentName2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    android.app.IWallpaperManagerCallback asInterface2 = android.app.IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    android.os.Bundle bundle2 = new android.os.Bundle();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor wallpaper2 = getWallpaper(readString4, asInterface2, readInt5, bundle2, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaper2, 1);
                    parcel2.writeTypedObject(bundle2, 1);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    android.app.IWallpaperManagerCallback asInterface3 = android.app.IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    android.os.Bundle bundle3 = new android.os.Bundle();
                    int readInt8 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor wallpaperWithFeature = getWallpaperWithFeature(readString5, readString6, asInterface3, readInt7, bundle3, readInt8, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperWithFeature, 1);
                    parcel2.writeTypedObject(bundle3, 1);
                    return true;
                case 6:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.graphics.Point.CREATOR);
                    int readInt9 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List bitmapCrops = getBitmapCrops(createTypedArrayList2, readInt9, readBoolean3, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeList(bitmapCrops);
                    return true;
                case 7:
                    android.graphics.Point point = (android.graphics.Point) parcel.readTypedObject(android.graphics.Point.CREATOR);
                    java.util.ArrayList createTypedArrayList3 = parcel.createTypedArrayList(android.graphics.Point.CREATOR);
                    int[] createIntArray2 = parcel.createIntArray();
                    java.util.ArrayList createTypedArrayList4 = parcel.createTypedArrayList(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List futureBitmapCrops = getFutureBitmapCrops(point, createTypedArrayList3, createIntArray2, createTypedArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeList(futureBitmapCrops);
                    return true;
                case 8:
                    android.graphics.Point point2 = (android.graphics.Point) parcel.readTypedObject(android.graphics.Point.CREATOR);
                    int[] createIntArray3 = parcel.createIntArray();
                    java.util.ArrayList createTypedArrayList5 = parcel.createTypedArrayList(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.graphics.Rect bitmapCrop = getBitmapCrop(point2, createIntArray3, createTypedArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bitmapCrop, 1);
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int wallpaperIdForUser = getWallpaperIdForUser(readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeInt(wallpaperIdForUser);
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.WallpaperInfo wallpaperInfo = getWallpaperInfo(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperInfo, 1);
                    return true;
                case 11:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.WallpaperInfo wallpaperInfoWithFlags = getWallpaperInfoWithFlags(readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperInfoWithFlags, 1);
                    return true;
                case 12:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor wallpaperInfoFile = getWallpaperInfoFile(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperInfoFile, 1);
                    return true;
                case 13:
                    java.lang.String readString7 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearWallpaper(readString7, readInt17, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasNamedWallpaper = hasNamedWallpaper(readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasNamedWallpaper);
                    return true;
                case 15:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDimensionHints(readInt19, readInt20, readString9, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int widthHint = getWidthHint(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeInt(widthHint);
                    return true;
                case 17:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int heightHint = getHeightHint(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(heightHint);
                    return true;
                case 18:
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    java.lang.String readString10 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayPadding(rect, readString10, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    java.lang.String name = getName();
                    parcel2.writeNoException();
                    parcel2.writeString(name);
                    return true;
                case 20:
                    settingsRestored();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isWallpaperSupported = isWallpaperSupported(readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWallpaperSupported);
                    return true;
                case 22:
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSetWallpaperAllowed = isSetWallpaperAllowed(readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSetWallpaperAllowed);
                    return true;
                case 23:
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWallpaperBackupEligible = isWallpaperBackupEligible(readInt25, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWallpaperBackupEligible);
                    return true;
                case 24:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.WallpaperColors wallpaperColors = getWallpaperColors(readInt27, readInt28, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaperColors, 1);
                    return true;
                case 25:
                    android.app.ILocalWallpaperColorConsumer asInterface4 = android.app.ILocalWallpaperColorConsumer.Stub.asInterface(parcel.readStrongBinder());
                    java.util.ArrayList createTypedArrayList6 = parcel.createTypedArrayList(android.graphics.RectF.CREATOR);
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOnLocalColorsChangedListener(asInterface4, createTypedArrayList6, readInt30, readInt31, readInt32);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    android.app.ILocalWallpaperColorConsumer asInterface5 = android.app.ILocalWallpaperColorConsumer.Stub.asInterface(parcel.readStrongBinder());
                    java.util.ArrayList createTypedArrayList7 = parcel.createTypedArrayList(android.graphics.RectF.CREATOR);
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOnLocalColorsChangedListener(asInterface5, createTypedArrayList7, readInt33, readInt34, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    android.app.IWallpaperManagerCallback asInterface6 = android.app.IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerWallpaperColorsCallback(asInterface6, readInt36, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    android.app.IWallpaperManagerCallback asInterface7 = android.app.IWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterWallpaperColorsCallback(asInterface7, readInt38, readInt39);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    boolean readBoolean4 = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInAmbientMode(readBoolean4, readLong);
                    return true;
                case 30:
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyWakingUp(readInt40, readInt41, bundle4);
                    return true;
                case 31:
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyGoingToSleep(readInt42, readInt43, bundle5);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setWallpaperDimAmount(readFloat);
                    return true;
                case 33:
                    float wallpaperDimAmount = getWallpaperDimAmount();
                    parcel2.writeNoException();
                    parcel2.writeFloat(wallpaperDimAmount);
                    return true;
                case 34:
                    boolean lockScreenWallpaperExists = lockScreenWallpaperExists();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockScreenWallpaperExists);
                    return true;
                case 35:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStaticWallpaper = isStaticWallpaper(readInt44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStaticWallpaper);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IWallpaperManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IWallpaperManager.Stub.DESCRIPTOR;
            }

            @Override // android.app.IWallpaperManager
            public android.os.ParcelFileDescriptor setWallpaper(java.lang.String str, java.lang.String str2, int[] iArr, java.util.List<android.graphics.Rect> list, boolean z, android.os.Bundle bundle, int i, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedList(list, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperComponentChecked(android.content.ComponentName componentName, java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperComponent(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public android.os.ParcelFileDescriptor getWallpaper(java.lang.String str, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public android.os.ParcelFileDescriptor getWallpaperWithFeature(java.lang.String str, java.lang.String str2, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, android.os.Bundle bundle, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    return parcelFileDescriptor;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public java.util.List getBitmapCrops(java.util.List<android.graphics.Point> list, int i, boolean z, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public java.util.List getFutureBitmapCrops(android.graphics.Point point, java.util.List<android.graphics.Point> list, int[] iArr, java.util.List<android.graphics.Rect> list2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(point, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedList(list2, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public android.graphics.Rect getBitmapCrop(android.graphics.Point point, int[] iArr, java.util.List<android.graphics.Rect> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(point, 0);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Rect) obtain2.readTypedObject(android.graphics.Rect.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getWallpaperIdForUser(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public android.app.WallpaperInfo getWallpaperInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.WallpaperInfo) obtain2.readTypedObject(android.app.WallpaperInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public android.app.WallpaperInfo getWallpaperInfoWithFlags(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.WallpaperInfo) obtain2.readTypedObject(android.app.WallpaperInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public android.os.ParcelFileDescriptor getWallpaperInfoFile(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void clearWallpaper(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean hasNamedWallpaper(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setDimensionHints(int i, int i2, java.lang.String str, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getWidthHint(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public int getHeightHint(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setDisplayPadding(android.graphics.Rect rect, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public java.lang.String getName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void settingsRestored() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperSupported(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isSetWallpaperAllowed(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isWallpaperBackupEligible(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public android.app.WallpaperColors getWallpaperColors(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.WallpaperColors) obtain2.readTypedObject(android.app.WallpaperColors.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void removeOnLocalColorsChangedListener(android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, java.util.List<android.graphics.RectF> list, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iLocalWallpaperColorConsumer);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void addOnLocalColorsChangedListener(android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, java.util.List<android.graphics.RectF> list, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iLocalWallpaperColorConsumer);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void registerWallpaperColorsCallback(android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void unregisterWallpaperColorsCallback(android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperManagerCallback);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setInAmbientMode(boolean z, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyWakingUp(int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void notifyGoingToSleep(int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public void setWallpaperDimAmount(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public float getWallpaperDimAmount() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean lockScreenWallpaperExists() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IWallpaperManager
            public boolean isStaticWallpaper(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IWallpaperManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 34;
        }
    }
}
