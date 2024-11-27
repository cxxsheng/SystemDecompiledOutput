package com.android.server.wallpaper;

/* loaded from: classes.dex */
public class WallpaperDataParser {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = com.android.server.wallpaper.WallpaperDataParser.class.getSimpleName();
    private final android.content.Context mContext;
    private final android.content.ComponentName mImageWallpaper;
    private final com.android.server.wallpaper.WallpaperCropper mWallpaperCropper;
    private final com.android.server.wallpaper.WallpaperDisplayHelper mWallpaperDisplayHelper;

    WallpaperDataParser(android.content.Context context, com.android.server.wallpaper.WallpaperDisplayHelper wallpaperDisplayHelper, com.android.server.wallpaper.WallpaperCropper wallpaperCropper) {
        this.mContext = context;
        this.mWallpaperDisplayHelper = wallpaperDisplayHelper;
        this.mWallpaperCropper = wallpaperCropper;
        this.mImageWallpaper = android.content.ComponentName.unflattenFromString(context.getResources().getString(android.R.string.httpErrorFile));
    }

    private com.android.internal.util.JournaledFile makeJournaledFile(int i) {
        java.lang.String absolutePath = new java.io.File(com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(i), "wallpaper_info.xml").getAbsolutePath();
        return new com.android.internal.util.JournaledFile(new java.io.File(absolutePath), new java.io.File(absolutePath + ".tmp"));
    }

    static class WallpaperLoadingResult {

        @android.annotation.Nullable
        private final com.android.server.wallpaper.WallpaperData mLockWallpaperData;
        private final boolean mSuccess;
        private final com.android.server.wallpaper.WallpaperData mSystemWallpaperData;

        private WallpaperLoadingResult(com.android.server.wallpaper.WallpaperData wallpaperData, com.android.server.wallpaper.WallpaperData wallpaperData2, boolean z) {
            this.mSystemWallpaperData = wallpaperData;
            this.mLockWallpaperData = wallpaperData2;
            this.mSuccess = z;
        }

        public com.android.server.wallpaper.WallpaperData getSystemWallpaperData() {
            return this.mSystemWallpaperData;
        }

        public com.android.server.wallpaper.WallpaperData getLockWallpaperData() {
            return this.mLockWallpaperData;
        }

        public boolean success() {
            return this.mSuccess;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0249  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult loadSettingsLocked(int i, boolean z, boolean z2, int i2) {
        com.android.server.wallpaper.WallpaperData wallpaperData;
        int i3;
        java.io.FileInputStream fileInputStream;
        com.android.server.wallpaper.WallpaperData wallpaperData2;
        boolean z3;
        com.android.server.wallpaper.WallpaperData wallpaperData3;
        java.io.FileInputStream fileInputStream2;
        java.io.File chooseForRead = makeJournaledFile(i).chooseForRead();
        boolean z4 = (i2 & 1) != 0;
        int i4 = 2;
        boolean z5 = (i2 & 2) != 0;
        if (z4) {
            if (z2) {
                migrateFromOld();
            }
            wallpaperData = new com.android.server.wallpaper.WallpaperData(i, 1);
            wallpaperData.allowBackup = true;
            if (!wallpaperData.cropExists()) {
                if (wallpaperData.sourceExists()) {
                    this.mWallpaperCropper.generateCrop(wallpaperData);
                } else {
                    android.util.Slog.i(TAG, "No static wallpaper imagery; defaults will be shown");
                }
            }
        } else {
            wallpaperData = null;
        }
        com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(0);
        try {
            fileInputStream = new java.io.FileInputStream(chooseForRead);
        } catch (java.io.FileNotFoundException e) {
            i3 = 1;
            fileInputStream = null;
        } catch (java.io.IOException e2) {
            e = e2;
            i3 = 1;
            fileInputStream = null;
        } catch (java.lang.IndexOutOfBoundsException e3) {
            e = e3;
            i3 = 1;
            fileInputStream = null;
        } catch (java.lang.NullPointerException e4) {
            e = e4;
            i3 = 1;
            fileInputStream = null;
        } catch (java.lang.NumberFormatException e5) {
            e = e5;
            i3 = 1;
            fileInputStream = null;
        } catch (org.xmlpull.v1.XmlPullParserException e6) {
            e = e6;
            i3 = 1;
            fileInputStream = null;
        }
        try {
            com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
            wallpaperData2 = null;
            while (true) {
                try {
                    int next = resolvePullParser.next();
                    if (next == i4) {
                        java.lang.String name = resolvePullParser.getName();
                        if (("wp".equals(name) && z4) || ("kwp".equals(name) && z5)) {
                            if ("kwp".equals(name)) {
                                fileInputStream2 = fileInputStream;
                                try {
                                    wallpaperData2 = new com.android.server.wallpaper.WallpaperData(i, 2);
                                } catch (java.io.FileNotFoundException e7) {
                                    fileInputStream = fileInputStream2;
                                    i3 = 1;
                                    android.util.Slog.w(TAG, "no current wallpaper -- first boot?");
                                    z3 = false;
                                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
                                    if (z4) {
                                    }
                                    if (z5) {
                                    }
                                    return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
                                } catch (java.io.IOException e8) {
                                    e = e8;
                                    fileInputStream = fileInputStream2;
                                    i3 = 1;
                                    android.util.Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
                                    z3 = false;
                                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
                                    if (z4) {
                                    }
                                    if (z5) {
                                    }
                                    return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
                                } catch (java.lang.IndexOutOfBoundsException e9) {
                                    e = e9;
                                    fileInputStream = fileInputStream2;
                                    i3 = 1;
                                    android.util.Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
                                    z3 = false;
                                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
                                    if (z4) {
                                    }
                                    if (z5) {
                                    }
                                    return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
                                } catch (java.lang.NullPointerException e10) {
                                    e = e10;
                                    fileInputStream = fileInputStream2;
                                    i3 = 1;
                                    android.util.Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
                                    z3 = false;
                                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
                                    if (z4) {
                                    }
                                    if (z5) {
                                    }
                                    return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
                                } catch (java.lang.NumberFormatException e11) {
                                    e = e11;
                                    fileInputStream = fileInputStream2;
                                    i3 = 1;
                                    android.util.Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
                                    z3 = false;
                                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
                                    if (z4) {
                                    }
                                    if (z5) {
                                    }
                                    return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
                                } catch (org.xmlpull.v1.XmlPullParserException e12) {
                                    e = e12;
                                    fileInputStream = fileInputStream2;
                                    i3 = 1;
                                    android.util.Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
                                    z3 = false;
                                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
                                    if (z4) {
                                    }
                                    if (z5) {
                                    }
                                    return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
                                }
                            } else {
                                fileInputStream2 = fileInputStream;
                            }
                            com.android.server.wallpaper.WallpaperData wallpaperData4 = "wp".equals(name) ? wallpaperData : wallpaperData2;
                            if (!com.android.window.flags.Flags.multiCrop()) {
                                parseWallpaperAttributes(resolvePullParser, wallpaperData4, z);
                            }
                            java.lang.String attributeValue = resolvePullParser.getAttributeValue((java.lang.String) null, "component");
                            wallpaperData4.nextWallpaperComponent = attributeValue != null ? android.content.ComponentName.unflattenFromString(attributeValue) : null;
                            if (wallpaperData4.nextWallpaperComponent == null || com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(wallpaperData4.nextWallpaperComponent.getPackageName())) {
                                wallpaperData4.nextWallpaperComponent = this.mImageWallpaper;
                            }
                            if (com.android.window.flags.Flags.multiCrop()) {
                                parseWallpaperAttributes(resolvePullParser, wallpaperData4, z);
                            }
                        } else {
                            fileInputStream2 = fileInputStream;
                        }
                    } else {
                        fileInputStream2 = fileInputStream;
                    }
                    i3 = 1;
                    if (next == 1) {
                        break;
                    }
                    fileInputStream = fileInputStream2;
                    i4 = 2;
                } catch (java.io.FileNotFoundException e13) {
                } catch (java.io.IOException e14) {
                    e = e14;
                } catch (java.lang.IndexOutOfBoundsException e15) {
                    e = e15;
                } catch (java.lang.NullPointerException e16) {
                    e = e16;
                } catch (java.lang.NumberFormatException e17) {
                    e = e17;
                } catch (org.xmlpull.v1.XmlPullParserException e18) {
                    e = e18;
                }
            }
            fileInputStream = fileInputStream2;
            z3 = true;
        } catch (java.io.FileNotFoundException e19) {
            i3 = 1;
            wallpaperData2 = null;
            android.util.Slog.w(TAG, "no current wallpaper -- first boot?");
            z3 = false;
            libcore.io.IoUtils.closeQuietly(fileInputStream);
            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
            if (z4) {
            }
            if (z5) {
            }
            return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
        } catch (java.io.IOException e20) {
            e = e20;
            i3 = 1;
            wallpaperData2 = null;
            android.util.Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
            z3 = false;
            libcore.io.IoUtils.closeQuietly(fileInputStream);
            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
            if (z4) {
            }
            if (z5) {
            }
            return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
        } catch (java.lang.IndexOutOfBoundsException e21) {
            e = e21;
            i3 = 1;
            wallpaperData2 = null;
            android.util.Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
            z3 = false;
            libcore.io.IoUtils.closeQuietly(fileInputStream);
            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
            if (z4) {
            }
            if (z5) {
            }
            return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
        } catch (java.lang.NullPointerException e22) {
            e = e22;
            i3 = 1;
            wallpaperData2 = null;
            android.util.Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
            z3 = false;
            libcore.io.IoUtils.closeQuietly(fileInputStream);
            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
            if (z4) {
            }
            if (z5) {
            }
            return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
        } catch (java.lang.NumberFormatException e23) {
            e = e23;
            i3 = 1;
            wallpaperData2 = null;
            android.util.Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
            z3 = false;
            libcore.io.IoUtils.closeQuietly(fileInputStream);
            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
            if (z4) {
            }
            if (z5) {
            }
            return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
        } catch (org.xmlpull.v1.XmlPullParserException e24) {
            e = e24;
            i3 = 1;
            wallpaperData2 = null;
            android.util.Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
            z3 = false;
            libcore.io.IoUtils.closeQuietly(fileInputStream);
            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
            if (z4) {
            }
            if (z5) {
            }
            return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
        }
        libcore.io.IoUtils.closeQuietly(fileInputStream);
        this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayDataOrCreate, 0);
        if (z4) {
            if (!z3) {
                wallpaperData.cropHint.set(0, 0, 0, 0);
                displayDataOrCreate.mPadding.set(0, 0, 0, 0);
                wallpaperData.name = "";
            } else if (wallpaperData.wallpaperId <= 0) {
                wallpaperData.wallpaperId = com.android.server.wallpaper.WallpaperUtils.makeWallpaperIdLocked();
            }
            ensureSaneWallpaperData(wallpaperData);
            wallpaperData.mWhich = wallpaperData2 != null ? i3 : 3;
        }
        if (z5) {
            wallpaperData3 = !z3 ? null : wallpaperData2;
            if (wallpaperData3 != null) {
                ensureSaneWallpaperData(wallpaperData3);
                wallpaperData3.mWhich = 2;
            }
        } else {
            wallpaperData3 = wallpaperData2;
        }
        return new com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult(wallpaperData, wallpaperData3, z3);
    }

    private void ensureSaneWallpaperData(com.android.server.wallpaper.WallpaperData wallpaperData) {
        if (wallpaperData.cropHint.width() < 0 || wallpaperData.cropHint.height() < 0) {
            wallpaperData.cropHint.set(0, 0, 0, 0);
        }
    }

    private void migrateFromOld() {
        java.io.File file = new java.io.File(com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(0), "wallpaper");
        java.io.File file2 = new java.io.File("/data/data/com.android.settings/files/wallpaper");
        java.io.File file3 = new java.io.File(com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(0), "wallpaper_orig");
        if (file.exists()) {
            if (!file3.exists()) {
                android.os.FileUtils.copyFile(file, file3);
            }
        } else if (file2.exists()) {
            java.io.File file4 = new java.io.File("/data/system/wallpaper_info.xml");
            if (file4.exists()) {
                file4.renameTo(new java.io.File(com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(0), "wallpaper_info.xml"));
            }
            android.os.FileUtils.copyFile(file2, file);
            file2.renameTo(file3);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void parseWallpaperAttributes(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.wallpaper.WallpaperData wallpaperData, boolean z) throws org.xmlpull.v1.XmlPullParserException {
        com.android.server.wallpaper.WallpaperData.BindSource bindSource;
        java.lang.String str = null;
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "id", -1);
        if (attributeInt != -1) {
            wallpaperData.wallpaperId = attributeInt;
            if (attributeInt > com.android.server.wallpaper.WallpaperUtils.getCurrentWallpaperId()) {
                com.android.server.wallpaper.WallpaperUtils.setCurrentWallpaperId(attributeInt);
            }
        } else {
            wallpaperData.wallpaperId = com.android.server.wallpaper.WallpaperUtils.makeWallpaperIdLocked();
        }
        java.lang.String str2 = "cropLeft";
        int i = 0;
        java.lang.String str3 = "cropTop";
        android.graphics.Rect rect = new android.graphics.Rect(getAttributeInt(typedXmlPullParser, "cropLeft", 0), getAttributeInt(typedXmlPullParser, "cropTop", 0), getAttributeInt(typedXmlPullParser, "cropRight", 0), getAttributeInt(typedXmlPullParser, "cropBottom", 0));
        android.graphics.Rect rect2 = new android.graphics.Rect(getAttributeInt(typedXmlPullParser, "totalCropLeft", 0), getAttributeInt(typedXmlPullParser, "totalCropTop", 0), getAttributeInt(typedXmlPullParser, "totalCropRight", 0), getAttributeInt(typedXmlPullParser, "totalCropBottom", 0));
        wallpaperData.mSupportsMultiCrop = com.android.window.flags.Flags.multiCrop() && (typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "supportsMultiCrop", false) || this.mImageWallpaper.equals(wallpaperData.wallpaperComponent));
        if (wallpaperData.mSupportsMultiCrop) {
            wallpaperData.mCropHints = new android.util.SparseArray<>();
            for (android.util.Pair<java.lang.Integer, java.lang.String> pair : screenDimensionPairs()) {
                java.lang.String str4 = str2;
                java.lang.String str5 = str3;
                android.graphics.Rect rect3 = new android.graphics.Rect(typedXmlPullParser.getAttributeInt(str, str2 + ((java.lang.String) pair.second), i), typedXmlPullParser.getAttributeInt((java.lang.String) null, str3 + ((java.lang.String) pair.second), i), typedXmlPullParser.getAttributeInt((java.lang.String) null, "cropRight" + ((java.lang.String) pair.second), 0), typedXmlPullParser.getAttributeInt((java.lang.String) null, "cropBottom" + ((java.lang.String) pair.second), 0));
                if (!rect3.isEmpty()) {
                    wallpaperData.mCropHints.put(((java.lang.Integer) pair.first).intValue(), rect3);
                }
                str2 = str4;
                str3 = str5;
                str = null;
                i = 0;
            }
            if (wallpaperData.mCropHints.size() == 0 && rect2.isEmpty()) {
                int i2 = rect.width() < rect.height() ? 0 : 1;
                if (!rect.isEmpty()) {
                    wallpaperData.mCropHints.put(i2, rect);
                }
            } else {
                wallpaperData.cropHint.set(rect2);
            }
        } else {
            wallpaperData.cropHint.set(rect);
        }
        com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(0);
        if (!z && !com.android.window.flags.Flags.multiCrop()) {
            displayDataOrCreate.mWidth = typedXmlPullParser.getAttributeInt((java.lang.String) null, "width", 0);
            displayDataOrCreate.mHeight = typedXmlPullParser.getAttributeInt((java.lang.String) null, "height", 0);
        }
        if (!com.android.window.flags.Flags.multiCrop()) {
            displayDataOrCreate.mPadding.left = getAttributeInt(typedXmlPullParser, "paddingLeft", 0);
            displayDataOrCreate.mPadding.top = getAttributeInt(typedXmlPullParser, "paddingTop", 0);
            displayDataOrCreate.mPadding.right = getAttributeInt(typedXmlPullParser, "paddingRight", 0);
            displayDataOrCreate.mPadding.bottom = getAttributeInt(typedXmlPullParser, "paddingBottom", 0);
        }
        wallpaperData.mWallpaperDimAmount = getAttributeFloat(typedXmlPullParser, "dimAmount", com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        try {
            bindSource = (com.android.server.wallpaper.WallpaperData.BindSource) java.lang.Enum.valueOf(com.android.server.wallpaper.WallpaperData.BindSource.class, getAttributeString(typedXmlPullParser, "bindSource", com.android.server.wallpaper.WallpaperData.BindSource.UNKNOWN.name()));
        } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
            bindSource = com.android.server.wallpaper.WallpaperData.BindSource.UNKNOWN;
        }
        wallpaperData.mBindSource = bindSource;
        int attributeInt2 = getAttributeInt(typedXmlPullParser, "dimAmountsCount", 0);
        if (attributeInt2 > 0) {
            android.util.SparseArray<java.lang.Float> sparseArray = new android.util.SparseArray<>(attributeInt2);
            for (int i3 = 0; i3 < attributeInt2; i3++) {
                sparseArray.put(getAttributeInt(typedXmlPullParser, "dimUID" + i3, 0), java.lang.Float.valueOf(getAttributeFloat(typedXmlPullParser, "dimValue" + i3, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE)));
            }
            wallpaperData.mUidToDimAmount = sparseArray;
        }
        int attributeInt3 = getAttributeInt(typedXmlPullParser, "colorsCount", 0);
        int attributeInt4 = getAttributeInt(typedXmlPullParser, "allColorsCount", 0);
        if (attributeInt4 > 0) {
            java.util.HashMap hashMap = new java.util.HashMap(attributeInt4);
            for (int i4 = 0; i4 < attributeInt4; i4++) {
                hashMap.put(java.lang.Integer.valueOf(getAttributeInt(typedXmlPullParser, "allColorsValue" + i4, 0)), java.lang.Integer.valueOf(getAttributeInt(typedXmlPullParser, "allColorsPopulation" + i4, 0)));
            }
            wallpaperData.primaryColors = new android.app.WallpaperColors(hashMap, getAttributeInt(typedXmlPullParser, "colorHints", 0));
        } else if (attributeInt3 > 0) {
            android.graphics.Color color = null;
            android.graphics.Color color2 = null;
            android.graphics.Color color3 = null;
            for (int i5 = 0; i5 < attributeInt3; i5++) {
                android.graphics.Color valueOf = android.graphics.Color.valueOf(getAttributeInt(typedXmlPullParser, "colorValue" + i5, 0));
                if (i5 == 0) {
                    color3 = valueOf;
                } else if (i5 == 1) {
                    color = valueOf;
                } else if (i5 != 2) {
                    break;
                } else {
                    color2 = valueOf;
                }
            }
            wallpaperData.primaryColors = new android.app.WallpaperColors(color3, color, color2, getAttributeInt(typedXmlPullParser, "colorHints", 0));
        }
        wallpaperData.name = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        wallpaperData.allowBackup = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, com.android.server.am.HostingRecord.HOSTING_TYPE_BACKUP, false);
    }

    private static int getAttributeInt(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, int i) {
        return typedXmlPullParser.getAttributeInt((java.lang.String) null, str, i);
    }

    private static float getAttributeFloat(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, float f) {
        return typedXmlPullParser.getAttributeFloat((java.lang.String) null, str, f);
    }

    private java.lang.String getAttributeString(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.lang.String str2) {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue != null ? attributeValue : str2;
    }

    void saveSettingsLocked(int i, com.android.server.wallpaper.WallpaperData wallpaperData, com.android.server.wallpaper.WallpaperData wallpaperData2) {
        java.io.FileOutputStream fileOutputStream;
        com.android.internal.util.JournaledFile makeJournaledFile = makeJournaledFile(i);
        java.io.FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new java.io.FileOutputStream(makeJournaledFile.chooseForWrite(), false);
        } catch (java.io.IOException e) {
        }
        try {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(fileOutputStream);
            resolveSerializer.startDocument((java.lang.String) null, true);
            if (wallpaperData != null) {
                writeWallpaperAttributes(resolveSerializer, "wp", wallpaperData);
            }
            if (wallpaperData2 != null) {
                writeWallpaperAttributes(resolveSerializer, "kwp", wallpaperData2);
            }
            resolveSerializer.endDocument();
            fileOutputStream.flush();
            android.os.FileUtils.sync(fileOutputStream);
            fileOutputStream.close();
            makeJournaledFile.commit();
        } catch (java.io.IOException e2) {
            fileOutputStream2 = fileOutputStream;
            libcore.io.IoUtils.closeQuietly(fileOutputStream2);
            makeJournaledFile.rollback();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void writeWallpaperAttributes(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, com.android.server.wallpaper.WallpaperData wallpaperData) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        int unfoldedOrientation;
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.attributeInt((java.lang.String) null, "id", wallpaperData.wallpaperId);
        typedXmlSerializer.attributeBoolean((java.lang.String) null, "supportsMultiCrop", wallpaperData.mSupportsMultiCrop);
        int i = 0;
        if (com.android.window.flags.Flags.multiCrop() && wallpaperData.mSupportsMultiCrop) {
            if (wallpaperData.mCropHints == null) {
                android.util.Slog.e(TAG, "cropHints should not be null when saved");
                wallpaperData.mCropHints = new android.util.SparseArray<>();
            }
            for (android.util.Pair<java.lang.Integer, java.lang.String> pair : screenDimensionPairs()) {
                android.graphics.Rect rect = wallpaperData.mCropHints.get(((java.lang.Integer) pair.first).intValue());
                if (rect != null) {
                    typedXmlSerializer.attributeInt((java.lang.String) null, "cropLeft" + ((java.lang.String) pair.second), rect.left);
                    typedXmlSerializer.attributeInt((java.lang.String) null, "cropTop" + ((java.lang.String) pair.second), rect.top);
                    typedXmlSerializer.attributeInt((java.lang.String) null, "cropRight" + ((java.lang.String) pair.second), rect.right);
                    typedXmlSerializer.attributeInt((java.lang.String) null, "cropBottom" + ((java.lang.String) pair.second), rect.bottom);
                    int i2 = wallpaperData.mOrientationWhenSet;
                    if (this.mWallpaperDisplayHelper.isFoldable() && (unfoldedOrientation = this.mWallpaperDisplayHelper.getUnfoldedOrientation(i2)) != -1) {
                        i2 = unfoldedOrientation;
                    }
                    if (((java.lang.Integer) pair.first).intValue() == i2) {
                        typedXmlSerializer.attributeInt((java.lang.String) null, "cropLeft", rect.left);
                        typedXmlSerializer.attributeInt((java.lang.String) null, "cropTop", rect.top);
                        typedXmlSerializer.attributeInt((java.lang.String) null, "cropRight", rect.right);
                        typedXmlSerializer.attributeInt((java.lang.String) null, "cropBottom", rect.bottom);
                    }
                }
            }
            typedXmlSerializer.attributeInt((java.lang.String) null, "totalCropLeft", wallpaperData.cropHint.left);
            typedXmlSerializer.attributeInt((java.lang.String) null, "totalCropTop", wallpaperData.cropHint.top);
            typedXmlSerializer.attributeInt((java.lang.String) null, "totalCropRight", wallpaperData.cropHint.right);
            typedXmlSerializer.attributeInt((java.lang.String) null, "totalCropBottom", wallpaperData.cropHint.bottom);
        } else if (!com.android.window.flags.Flags.multiCrop()) {
            com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(0);
            typedXmlSerializer.attributeInt((java.lang.String) null, "width", displayDataOrCreate.mWidth);
            typedXmlSerializer.attributeInt((java.lang.String) null, "height", displayDataOrCreate.mHeight);
            typedXmlSerializer.attributeInt((java.lang.String) null, "cropLeft", wallpaperData.cropHint.left);
            typedXmlSerializer.attributeInt((java.lang.String) null, "cropTop", wallpaperData.cropHint.top);
            typedXmlSerializer.attributeInt((java.lang.String) null, "cropRight", wallpaperData.cropHint.right);
            typedXmlSerializer.attributeInt((java.lang.String) null, "cropBottom", wallpaperData.cropHint.bottom);
            if (displayDataOrCreate.mPadding.left != 0) {
                typedXmlSerializer.attributeInt((java.lang.String) null, "paddingLeft", displayDataOrCreate.mPadding.left);
            }
            if (displayDataOrCreate.mPadding.top != 0) {
                typedXmlSerializer.attributeInt((java.lang.String) null, "paddingTop", displayDataOrCreate.mPadding.top);
            }
            if (displayDataOrCreate.mPadding.right != 0) {
                typedXmlSerializer.attributeInt((java.lang.String) null, "paddingRight", displayDataOrCreate.mPadding.right);
            }
            if (displayDataOrCreate.mPadding.bottom != 0) {
                typedXmlSerializer.attributeInt((java.lang.String) null, "paddingBottom", displayDataOrCreate.mPadding.bottom);
            }
        }
        typedXmlSerializer.attributeFloat((java.lang.String) null, "dimAmount", wallpaperData.mWallpaperDimAmount);
        typedXmlSerializer.attribute((java.lang.String) null, "bindSource", wallpaperData.mBindSource.name());
        int size = wallpaperData.mUidToDimAmount.size();
        typedXmlSerializer.attributeInt((java.lang.String) null, "dimAmountsCount", size);
        if (size > 0) {
            int i3 = 0;
            for (int i4 = 0; i4 < wallpaperData.mUidToDimAmount.size(); i4++) {
                typedXmlSerializer.attributeInt((java.lang.String) null, "dimUID" + i3, wallpaperData.mUidToDimAmount.keyAt(i4));
                typedXmlSerializer.attributeFloat((java.lang.String) null, "dimValue" + i3, wallpaperData.mUidToDimAmount.valueAt(i4).floatValue());
                i3++;
            }
        }
        if (wallpaperData.primaryColors != null) {
            int size2 = wallpaperData.primaryColors.getMainColors().size();
            typedXmlSerializer.attributeInt((java.lang.String) null, "colorsCount", size2);
            if (size2 > 0) {
                for (int i5 = 0; i5 < size2; i5++) {
                    typedXmlSerializer.attributeInt((java.lang.String) null, "colorValue" + i5, ((android.graphics.Color) wallpaperData.primaryColors.getMainColors().get(i5)).toArgb());
                }
            }
            int size3 = wallpaperData.primaryColors.getAllColors().size();
            typedXmlSerializer.attributeInt((java.lang.String) null, "allColorsCount", size3);
            if (size3 > 0) {
                for (java.util.Map.Entry entry : wallpaperData.primaryColors.getAllColors().entrySet()) {
                    typedXmlSerializer.attributeInt((java.lang.String) null, "allColorsValue" + i, ((java.lang.Integer) entry.getKey()).intValue());
                    typedXmlSerializer.attributeInt((java.lang.String) null, "allColorsPopulation" + i, ((java.lang.Integer) entry.getValue()).intValue());
                    i++;
                }
            }
            typedXmlSerializer.attributeInt((java.lang.String) null, "colorHints", wallpaperData.primaryColors.getColorHints());
        }
        typedXmlSerializer.attribute((java.lang.String) null, "name", wallpaperData.name);
        if (wallpaperData.wallpaperComponent != null && !wallpaperData.wallpaperComponent.equals(this.mImageWallpaper)) {
            typedXmlSerializer.attribute((java.lang.String) null, "component", wallpaperData.wallpaperComponent.flattenToShortString());
        }
        if (wallpaperData.allowBackup) {
            typedXmlSerializer.attributeBoolean((java.lang.String) null, com.android.server.am.HostingRecord.HOSTING_TYPE_BACKUP, true);
        }
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x01ad, code lost:
    
        if (r7 != 0) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x015e, code lost:
    
        libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) r3);
        libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x015b, code lost:
    
        android.os.FileUtils.sync(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0186, code lost:
    
        if (r7 != 0) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0159, code lost:
    
        if (r7 != 0) goto L86;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.io.FileOutputStream, java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r3v11, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v12, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r3v15, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v31 */
    /* JADX WARN: Type inference failed for: r3v34 */
    /* JADX WARN: Type inference failed for: r3v37 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v40 */
    /* JADX WARN: Type inference failed for: r3v41 */
    /* JADX WARN: Type inference failed for: r3v42 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v10, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r7v11, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v29 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v31 */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r7v35 */
    /* JADX WARN: Type inference failed for: r7v36 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7, types: [java.io.FileOutputStream, java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean restoreNamedResourceLocked(com.android.server.wallpaper.WallpaperData wallpaperData) {
        java.io.FileOutputStream fileOutputStream;
        java.io.FileOutputStream fileOutputStream2;
        java.io.FileOutputStream fileOutputStream3;
        java.io.FileOutputStream fileOutputStream4;
        java.io.FileOutputStream fileOutputStream5;
        java.io.FileOutputStream fileOutputStream6;
        if (wallpaperData.name.length() > 4 && "res:".equals(wallpaperData.name.substring(0, 4))) {
            java.lang.String substring = wallpaperData.name.substring(4);
            int indexOf = substring.indexOf(58);
            java.io.InputStream inputStream = null;
            java.lang.String substring2 = indexOf > 0 ? substring.substring(0, indexOf) : null;
            int lastIndexOf = substring.lastIndexOf(47);
            ?? substring3 = lastIndexOf > 0 ? substring.substring(lastIndexOf + 1) : 0;
            ?? substring4 = (indexOf <= 0 || lastIndexOf <= 0 || lastIndexOf - indexOf <= 1) ? 0 : substring.substring(indexOf + 1, lastIndexOf);
            if (substring2 != null && substring3 != 0 && substring4 != 0) {
                int i = -1;
                try {
                    try {
                        android.content.res.Resources resources = this.mContext.createPackageContext(substring2, 4).getResources();
                        i = resources.getIdentifier(substring, null, null);
                        if (i == 0) {
                            android.util.Slog.e(TAG, "couldn't resolve identifier pkg=" + substring2 + " type=" + substring4 + " ident=" + substring3);
                            libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
                            libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
                            libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
                            return false;
                        }
                        java.io.InputStream openRawResource = resources.openRawResource(i);
                        try {
                            if (wallpaperData.getWallpaperFile().exists()) {
                                wallpaperData.getWallpaperFile().delete();
                                wallpaperData.getCropFile().delete();
                            }
                            java.io.FileOutputStream fileOutputStream7 = new java.io.FileOutputStream(wallpaperData.getWallpaperFile());
                            try {
                                java.io.FileOutputStream fileOutputStream8 = new java.io.FileOutputStream(wallpaperData.getCropFile());
                                try {
                                    byte[] bArr = new byte[32768];
                                    while (true) {
                                        int read = openRawResource.read(bArr);
                                        if (read <= 0) {
                                            android.util.Slog.v(TAG, "Restored wallpaper: " + substring);
                                            libcore.io.IoUtils.closeQuietly(openRawResource);
                                            android.os.FileUtils.sync(fileOutputStream7);
                                            android.os.FileUtils.sync(fileOutputStream8);
                                            libcore.io.IoUtils.closeQuietly(fileOutputStream7);
                                            libcore.io.IoUtils.closeQuietly(fileOutputStream8);
                                            return true;
                                        }
                                        fileOutputStream7.write(bArr, 0, read);
                                        fileOutputStream8.write(bArr, 0, read);
                                    }
                                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                    fileOutputStream5 = fileOutputStream7;
                                    fileOutputStream6 = fileOutputStream8;
                                    inputStream = openRawResource;
                                    substring4 = fileOutputStream5;
                                    substring3 = fileOutputStream6;
                                    android.util.Slog.e(TAG, "Package name " + substring2 + " not found");
                                    libcore.io.IoUtils.closeQuietly(inputStream);
                                    if (substring4 != 0) {
                                        android.os.FileUtils.sync(substring4);
                                    }
                                } catch (android.content.res.Resources.NotFoundException e2) {
                                    fileOutputStream3 = fileOutputStream7;
                                    fileOutputStream4 = fileOutputStream8;
                                    inputStream = openRawResource;
                                    substring4 = fileOutputStream3;
                                    substring3 = fileOutputStream4;
                                    android.util.Slog.e(TAG, "Resource not found: " + i);
                                    libcore.io.IoUtils.closeQuietly(inputStream);
                                    if (substring4 != 0) {
                                        android.os.FileUtils.sync(substring4);
                                    }
                                } catch (java.io.IOException e3) {
                                    e = e3;
                                    fileOutputStream = fileOutputStream7;
                                    fileOutputStream2 = fileOutputStream8;
                                    inputStream = openRawResource;
                                    substring4 = fileOutputStream;
                                    substring3 = fileOutputStream2;
                                    android.util.Slog.e(TAG, "IOException while restoring wallpaper ", e);
                                    libcore.io.IoUtils.closeQuietly(inputStream);
                                    if (substring4 != 0) {
                                        android.os.FileUtils.sync(substring4);
                                    }
                                } catch (java.lang.Throwable th) {
                                    th = th;
                                    substring4 = fileOutputStream7;
                                    substring3 = fileOutputStream8;
                                    inputStream = openRawResource;
                                    libcore.io.IoUtils.closeQuietly(inputStream);
                                    if (substring4 != 0) {
                                        android.os.FileUtils.sync(substring4);
                                    }
                                    if (substring3 != 0) {
                                        android.os.FileUtils.sync(substring3);
                                    }
                                    libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) substring4);
                                    libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) substring3);
                                    throw th;
                                }
                            } catch (android.content.pm.PackageManager.NameNotFoundException e4) {
                                fileOutputStream6 = null;
                                fileOutputStream5 = fileOutputStream7;
                            } catch (android.content.res.Resources.NotFoundException e5) {
                                fileOutputStream4 = null;
                                fileOutputStream3 = fileOutputStream7;
                            } catch (java.io.IOException e6) {
                                e = e6;
                                fileOutputStream2 = null;
                                fileOutputStream = fileOutputStream7;
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                                substring3 = 0;
                                substring4 = fileOutputStream7;
                            }
                        } catch (android.content.pm.PackageManager.NameNotFoundException e7) {
                            fileOutputStream5 = null;
                            fileOutputStream6 = null;
                        } catch (android.content.res.Resources.NotFoundException e8) {
                            fileOutputStream3 = null;
                            fileOutputStream4 = null;
                        } catch (java.io.IOException e9) {
                            e = e9;
                            fileOutputStream = null;
                            fileOutputStream2 = null;
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                            substring4 = 0;
                            substring3 = 0;
                        }
                    } catch (android.content.pm.PackageManager.NameNotFoundException e10) {
                        substring4 = 0;
                        substring3 = 0;
                    } catch (android.content.res.Resources.NotFoundException e11) {
                        substring4 = 0;
                        substring3 = 0;
                    } catch (java.io.IOException e12) {
                        e = e12;
                        substring4 = 0;
                        substring3 = 0;
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                        substring4 = 0;
                        substring3 = 0;
                    }
                } catch (java.lang.Throwable th5) {
                    th = th5;
                }
            }
        }
        return false;
    }

    private static java.util.List<android.util.Pair<java.lang.Integer, java.lang.String>> screenDimensionPairs() {
        return java.util.List.of(new android.util.Pair(0, "Portrait"), new android.util.Pair(1, "Landscape"), new android.util.Pair(2, "SquarePortrait"), new android.util.Pair(3, "SquareLandscape"));
    }
}
