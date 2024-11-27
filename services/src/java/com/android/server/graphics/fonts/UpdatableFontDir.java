package com.android.server.graphics.fonts;

/* loaded from: classes.dex */
final class UpdatableFontDir {
    private static final java.lang.String FONT_SIGNATURE_FILE = "font.fsv_sig";
    private static final java.lang.String RANDOM_DIR_PREFIX = "~~";
    private static final java.lang.String TAG = "UpdatableFontDir";
    private final android.util.AtomicFile mConfigFile;
    private final java.util.function.Function<java.util.Map<java.lang.String, java.io.File>, android.text.FontConfig> mConfigSupplier;
    private int mConfigVersion;
    private final java.util.function.Supplier<java.lang.Long> mCurrentTimeSupplier;
    private final java.io.File mFilesDir;
    private final android.util.ArrayMap<java.lang.String, com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo> mFontFileInfoMap;
    private final com.android.server.graphics.fonts.UpdatableFontDir.FsverityUtil mFsverityUtil;
    private long mLastModifiedMillis;
    private final com.android.server.graphics.fonts.UpdatableFontDir.FontFileParser mParser;

    interface FontFileParser {
        java.lang.String buildFontFileName(java.io.File file) throws java.io.IOException;

        java.lang.String getPostScriptName(java.io.File file) throws java.io.IOException;

        long getRevision(java.io.File file) throws java.io.IOException;

        void tryToCreateTypeface(java.io.File file) throws java.lang.Throwable;
    }

    interface FsverityUtil {
        boolean isFromTrustedProvider(java.lang.String str, byte[] bArr);

        boolean rename(java.io.File file, java.io.File file2);

        void setUpFsverity(java.lang.String str) throws java.io.IOException;
    }

    private static final class FontFileInfo {
        private final java.io.File mFile;
        private final java.lang.String mPsName;
        private final long mRevision;

        FontFileInfo(java.io.File file, java.lang.String str, long j) {
            this.mFile = file;
            this.mPsName = str;
            this.mRevision = j;
        }

        public java.io.File getFile() {
            return this.mFile;
        }

        public java.lang.String getPostScriptName() {
            return this.mPsName;
        }

        public java.io.File getRandomizedFontDir() {
            return this.mFile.getParentFile();
        }

        public long getRevision() {
            return this.mRevision;
        }

        public java.lang.String toString() {
            return "FontFileInfo{mFile=" + this.mFile + ", psName=" + this.mPsName + ", mRevision=" + this.mRevision + '}';
        }
    }

    UpdatableFontDir(java.io.File file, com.android.server.graphics.fonts.UpdatableFontDir.FontFileParser fontFileParser, com.android.server.graphics.fonts.UpdatableFontDir.FsverityUtil fsverityUtil, java.io.File file2) {
        this(file, fontFileParser, fsverityUtil, file2, new java.util.function.Supplier() { // from class: com.android.server.graphics.fonts.UpdatableFontDir$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Long.valueOf(java.lang.System.currentTimeMillis());
            }
        }, new java.util.function.Function() { // from class: com.android.server.graphics.fonts.UpdatableFontDir$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.text.FontConfig lambda$new$0;
                lambda$new$0 = com.android.server.graphics.fonts.UpdatableFontDir.lambda$new$0((java.util.Map) obj);
                return lambda$new$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.text.FontConfig lambda$new$0(java.util.Map map) {
        return android.graphics.fonts.SystemFonts.getSystemFontConfig(map, 0L, 0);
    }

    UpdatableFontDir(java.io.File file, com.android.server.graphics.fonts.UpdatableFontDir.FontFileParser fontFileParser, com.android.server.graphics.fonts.UpdatableFontDir.FsverityUtil fsverityUtil, java.io.File file2, java.util.function.Supplier<java.lang.Long> supplier, java.util.function.Function<java.util.Map<java.lang.String, java.io.File>, android.text.FontConfig> function) {
        this.mFontFileInfoMap = new android.util.ArrayMap<>();
        this.mFilesDir = file;
        this.mParser = fontFileParser;
        this.mFsverityUtil = fsverityUtil;
        this.mConfigFile = new android.util.AtomicFile(file2);
        this.mCurrentTimeSupplier = supplier;
        this.mConfigSupplier = function;
    }

    void loadFontFileMap() {
        this.mFontFileInfoMap.clear();
        long j = 0;
        this.mLastModifiedMillis = 0L;
        this.mConfigVersion = 1;
        try {
            com.android.server.graphics.fonts.PersistentSystemFontConfig.Config readPersistentConfig = readPersistentConfig();
            this.mLastModifiedMillis = readPersistentConfig.lastModifiedMillis;
            java.io.File[] listFiles = this.mFilesDir.listFiles();
            if (listFiles == null) {
                android.util.Slog.e(TAG, "Could not read: " + this.mFilesDir);
                this.mFontFileInfoMap.clear();
                this.mLastModifiedMillis = 0L;
                android.os.FileUtils.deleteContents(this.mFilesDir);
                this.mConfigFile.delete();
                return;
            }
            int length = listFiles.length;
            android.text.FontConfig fontConfig = null;
            int i = 0;
            while (i < length) {
                java.io.File file = listFiles[i];
                if (!file.getName().startsWith(RANDOM_DIR_PREFIX)) {
                    android.util.Slog.e(TAG, "Unexpected dir found: " + file);
                    this.mFontFileInfoMap.clear();
                    this.mLastModifiedMillis = j;
                    android.os.FileUtils.deleteContents(this.mFilesDir);
                    this.mConfigFile.delete();
                    return;
                }
                if (readPersistentConfig.updatedFontDirs.contains(file.getName())) {
                    java.io.File file2 = new java.io.File(file, FONT_SIGNATURE_FILE);
                    if (!file2.exists()) {
                        android.util.Slog.i(TAG, "The signature file is missing.");
                        this.mFontFileInfoMap.clear();
                        this.mLastModifiedMillis = j;
                        android.os.FileUtils.deleteContents(this.mFilesDir);
                        this.mConfigFile.delete();
                        return;
                    }
                    try {
                        byte[] readAllBytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(file2.getAbsolutePath(), new java.lang.String[0]));
                        java.io.File[] listFiles2 = file.listFiles();
                        if (listFiles2 == null || listFiles2.length != 2) {
                            android.util.Slog.e(TAG, "Unexpected files in dir: " + file);
                            return;
                        }
                        com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo validateFontFile = validateFontFile(listFiles2[0].equals(file2) ? listFiles2[1] : listFiles2[0], readAllBytes);
                        if (fontConfig == null) {
                            fontConfig = this.mConfigSupplier.apply(java.util.Collections.emptyMap());
                        }
                        addFileToMapIfSameOrNewer(validateFontFile, fontConfig, true);
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Failed to read signature file.");
                        return;
                    }
                } else {
                    android.util.Slog.i(TAG, "Deleting obsolete dir: " + file);
                    android.os.FileUtils.deleteContentsAndDir(file);
                }
                i++;
                j = 0;
            }
            for (int i2 = 0; i2 < readPersistentConfig.fontFamilies.size(); i2++) {
                android.graphics.fonts.FontUpdateRequest.Family family = readPersistentConfig.fontFamilies.get(i2);
                for (int i3 = 0; i3 < family.getFonts().size(); i3++) {
                    android.graphics.fonts.FontUpdateRequest.Font font = (android.graphics.fonts.FontUpdateRequest.Font) family.getFonts().get(i3);
                    if (!this.mFontFileInfoMap.containsKey(font.getPostScriptName())) {
                        if (fontConfig == null) {
                            fontConfig = this.mConfigSupplier.apply(java.util.Collections.emptyMap());
                        }
                        if (getFontByPostScriptName(font.getPostScriptName(), fontConfig) == null) {
                            android.util.Slog.e(TAG, "Unknown font that has PostScript name " + font.getPostScriptName() + " is requested in FontFamily " + family.getName());
                            return;
                        }
                    }
                }
            }
        } catch (java.lang.Throwable th) {
            try {
                android.util.Slog.e(TAG, "Failed to load font mappings.", th);
            } finally {
                this.mFontFileInfoMap.clear();
                this.mLastModifiedMillis = 0L;
                android.os.FileUtils.deleteContents(this.mFilesDir);
                this.mConfigFile.delete();
            }
        }
    }

    public void update(java.util.List<android.graphics.fonts.FontUpdateRequest> list) throws com.android.server.graphics.fonts.FontManagerService.SystemFontException {
        for (android.graphics.fonts.FontUpdateRequest fontUpdateRequest : list) {
            switch (fontUpdateRequest.getType()) {
                case 0:
                    java.util.Objects.requireNonNull(fontUpdateRequest.getFd());
                    java.util.Objects.requireNonNull(fontUpdateRequest.getSignature());
                    break;
                case 1:
                    java.util.Objects.requireNonNull(fontUpdateRequest.getFontFamily());
                    java.util.Objects.requireNonNull(fontUpdateRequest.getFontFamily().getName());
                    break;
            }
        }
        android.util.ArrayMap<? extends java.lang.String, ? extends com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo> arrayMap = new android.util.ArrayMap<>(this.mFontFileInfoMap);
        com.android.server.graphics.fonts.PersistentSystemFontConfig.Config readPersistentConfig = readPersistentConfig();
        java.util.HashMap hashMap = new java.util.HashMap();
        for (int i = 0; i < readPersistentConfig.fontFamilies.size(); i++) {
            android.graphics.fonts.FontUpdateRequest.Family family = readPersistentConfig.fontFamilies.get(i);
            hashMap.put(family.getName(), family);
        }
        long j = this.mLastModifiedMillis;
        try {
            for (android.graphics.fonts.FontUpdateRequest fontUpdateRequest2 : list) {
                switch (fontUpdateRequest2.getType()) {
                    case 0:
                        installFontFile(fontUpdateRequest2.getFd().getFileDescriptor(), fontUpdateRequest2.getSignature());
                        break;
                    case 1:
                        android.graphics.fonts.FontUpdateRequest.Family fontFamily = fontUpdateRequest2.getFontFamily();
                        hashMap.put(fontFamily.getName(), fontFamily);
                        break;
                }
            }
            java.util.Iterator it = hashMap.values().iterator();
            while (it.hasNext()) {
                if (resolveFontFilesForNamedFamily((android.graphics.fonts.FontUpdateRequest.Family) it.next()) == null) {
                    throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-9, "Required fonts are not available");
                }
            }
            this.mLastModifiedMillis = this.mCurrentTimeSupplier.get().longValue();
            com.android.server.graphics.fonts.PersistentSystemFontConfig.Config config = new com.android.server.graphics.fonts.PersistentSystemFontConfig.Config();
            config.lastModifiedMillis = this.mLastModifiedMillis;
            java.util.Iterator<com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo> it2 = this.mFontFileInfoMap.values().iterator();
            while (it2.hasNext()) {
                config.updatedFontDirs.add(it2.next().getRandomizedFontDir().getName());
            }
            config.fontFamilies.addAll(hashMap.values());
            writePersistentConfig(config);
            this.mConfigVersion++;
        } catch (java.lang.Throwable th) {
            this.mFontFileInfoMap.clear();
            this.mFontFileInfoMap.putAll(arrayMap);
            this.mLastModifiedMillis = j;
            throw th;
        }
    }

    private void installFontFile(java.io.FileDescriptor fileDescriptor, byte[] bArr) throws com.android.server.graphics.fonts.FontManagerService.SystemFontException {
        java.io.File randomDir = getRandomDir(this.mFilesDir);
        if (!randomDir.mkdir()) {
            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-1, "Failed to create font directory.");
        }
        try {
            android.system.Os.chmod(randomDir.getAbsolutePath(), com.android.internal.art.ArtStatsLog.ISOLATED_COMPILATION_SCHEDULED);
            try {
                java.io.File file = new java.io.File(randomDir, "font.ttf");
                try {
                    java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
                    try {
                        android.os.FileUtils.copy(fileDescriptor, fileOutputStream.getFD());
                        fileOutputStream.close();
                        try {
                            this.mFsverityUtil.setUpFsverity(file.getAbsolutePath());
                            try {
                                java.lang.String buildFontFileName = this.mParser.buildFontFileName(file);
                                if (buildFontFileName == null) {
                                    throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-4, "Failed to read PostScript name from font file");
                                }
                                java.io.File file2 = new java.io.File(randomDir, buildFontFileName);
                                if (!this.mFsverityUtil.rename(file, file2)) {
                                    throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-1, "Failed to move verified font file.");
                                }
                                try {
                                    android.system.Os.chmod(file2.getAbsolutePath(), com.android.internal.util.FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                                    java.io.File file3 = new java.io.File(randomDir, FONT_SIGNATURE_FILE);
                                    try {
                                        java.io.FileOutputStream fileOutputStream2 = new java.io.FileOutputStream(file3);
                                        try {
                                            fileOutputStream2.write(bArr);
                                            fileOutputStream2.close();
                                            try {
                                                android.system.Os.chmod(file3.getAbsolutePath(), com.android.internal.util.FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
                                                com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo validateFontFile = validateFontFile(file2, bArr);
                                                try {
                                                    this.mParser.tryToCreateTypeface(validateFontFile.getFile());
                                                    if (!addFileToMapIfSameOrNewer(validateFontFile, getSystemFontConfig(), false)) {
                                                        throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-5, "Downgrading font file is forbidden.");
                                                    }
                                                } catch (java.lang.Throwable th) {
                                                    throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-3, "Failed to create Typeface from file", th);
                                                }
                                            } catch (android.system.ErrnoException e) {
                                                throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-1, "Failed to change the signature file mode to 600", e);
                                            }
                                        } catch (java.lang.Throwable th2) {
                                            try {
                                                fileOutputStream2.close();
                                            } catch (java.lang.Throwable th3) {
                                                th2.addSuppressed(th3);
                                            }
                                            throw th2;
                                        }
                                    } catch (java.io.IOException e2) {
                                        throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-1, "Failed to write font signature file to storage.", e2);
                                    }
                                } catch (android.system.ErrnoException e3) {
                                    throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-1, "Failed to change font file mode to 644", e3);
                                }
                            } catch (java.io.IOException e4) {
                                throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-3, "Failed to read PostScript name from font file", e4);
                            }
                        } catch (java.io.IOException e5) {
                            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-2, "Failed to setup fs-verity.", e5);
                        }
                    } catch (java.lang.Throwable th4) {
                        try {
                            fileOutputStream.close();
                        } catch (java.lang.Throwable th5) {
                            th4.addSuppressed(th5);
                        }
                        throw th4;
                    }
                } catch (java.io.IOException e6) {
                    throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-1, "Failed to write font file to storage.", e6);
                }
            } catch (java.lang.Throwable th6) {
                android.os.FileUtils.deleteContentsAndDir(randomDir);
                throw th6;
            }
        } catch (android.system.ErrnoException e7) {
            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-1, "Failed to change mode to 711", e7);
        }
    }

    private static java.io.File getRandomDir(java.io.File file) {
        java.io.File file2;
        java.security.SecureRandom secureRandom = new java.security.SecureRandom();
        byte[] bArr = new byte[16];
        do {
            secureRandom.nextBytes(bArr);
            file2 = new java.io.File(file, RANDOM_DIR_PREFIX + android.util.Base64.encodeToString(bArr, 10));
        } while (file2.exists());
        return file2;
    }

    private com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo lookupFontFileInfo(java.lang.String str) {
        return this.mFontFileInfoMap.get(str);
    }

    private void putFontFileInfo(com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo fontFileInfo) {
        this.mFontFileInfoMap.put(fontFileInfo.getPostScriptName(), fontFileInfo);
    }

    private boolean addFileToMapIfSameOrNewer(com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo fontFileInfo, android.text.FontConfig fontConfig, boolean z) {
        com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo lookupFontFileInfo = lookupFontFileInfo(fontFileInfo.getPostScriptName());
        boolean z2 = false;
        if (lookupFontFileInfo == null) {
            if (getPreinstalledFontRevision(fontFileInfo, fontConfig) <= fontFileInfo.getRevision()) {
                z2 = true;
            }
        } else if (lookupFontFileInfo.getRevision() <= fontFileInfo.getRevision()) {
            z2 = true;
        }
        if (z2) {
            if (z && lookupFontFileInfo != null) {
                android.os.FileUtils.deleteContentsAndDir(lookupFontFileInfo.getRandomizedFontDir());
            }
            putFontFileInfo(fontFileInfo);
        } else if (z) {
            android.os.FileUtils.deleteContentsAndDir(fontFileInfo.getRandomizedFontDir());
        }
        return z2;
    }

    private android.text.FontConfig.Font getFontByPostScriptName(java.lang.String str, android.text.FontConfig fontConfig) {
        android.text.FontConfig.Font font = null;
        for (int i = 0; i < fontConfig.getFontFamilies().size(); i++) {
            android.text.FontConfig.FontFamily fontFamily = (android.text.FontConfig.FontFamily) fontConfig.getFontFamilies().get(i);
            int i2 = 0;
            while (true) {
                if (i2 < fontFamily.getFontList().size()) {
                    android.text.FontConfig.Font font2 = (android.text.FontConfig.Font) fontFamily.getFontList().get(i2);
                    if (!font2.getPostScriptName().equals(str)) {
                        i2++;
                    } else {
                        font = font2;
                        break;
                    }
                }
            }
        }
        for (int i3 = 0; i3 < fontConfig.getNamedFamilyLists().size(); i3++) {
            android.text.FontConfig.NamedFamilyList namedFamilyList = (android.text.FontConfig.NamedFamilyList) fontConfig.getNamedFamilyLists().get(i3);
            for (int i4 = 0; i4 < namedFamilyList.getFamilies().size(); i4++) {
                android.text.FontConfig.FontFamily fontFamily2 = (android.text.FontConfig.FontFamily) namedFamilyList.getFamilies().get(i4);
                int i5 = 0;
                while (true) {
                    if (i5 < fontFamily2.getFontList().size()) {
                        android.text.FontConfig.Font font3 = (android.text.FontConfig.Font) fontFamily2.getFontList().get(i5);
                        if (!font3.getPostScriptName().equals(str)) {
                            i5++;
                        } else {
                            font = font3;
                            break;
                        }
                    }
                }
            }
        }
        return font;
    }

    private long getPreinstalledFontRevision(com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo fontFileInfo, android.text.FontConfig fontConfig) {
        android.text.FontConfig.Font fontByPostScriptName = getFontByPostScriptName(fontFileInfo.getPostScriptName(), fontConfig);
        if (fontByPostScriptName == null) {
            return -1L;
        }
        java.io.File originalFile = fontByPostScriptName.getOriginalFile() != null ? fontByPostScriptName.getOriginalFile() : fontByPostScriptName.getFile();
        if (!originalFile.exists()) {
            return -1L;
        }
        long fontRevision = getFontRevision(originalFile);
        if (fontRevision == -1) {
            android.util.Slog.w(TAG, "Invalid preinstalled font file");
        }
        return fontRevision;
    }

    @android.annotation.NonNull
    private com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo validateFontFile(java.io.File file, byte[] bArr) throws com.android.server.graphics.fonts.FontManagerService.SystemFontException {
        if (!this.mFsverityUtil.isFromTrustedProvider(file.getAbsolutePath(), bArr)) {
            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-2, "Font validation failed. Fs-verity is not enabled: " + file);
        }
        try {
            java.lang.String postScriptName = this.mParser.getPostScriptName(file);
            long fontRevision = getFontRevision(file);
            if (fontRevision == -1) {
                throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-3, "Font validation failed. Could not read font revision: " + file);
            }
            return new com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo(file, postScriptName, fontRevision);
        } catch (java.io.IOException e) {
            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-4, "Font validation failed. Could not read PostScript name name: " + file);
        }
    }

    private long getFontRevision(java.io.File file) {
        try {
            return this.mParser.getRevision(file);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to read font file", e);
            return -1L;
        }
    }

    private android.text.FontConfig.NamedFamilyList resolveFontFilesForNamedFamily(android.graphics.fonts.FontUpdateRequest.Family family) {
        java.util.List fonts = family.getFonts();
        java.util.ArrayList arrayList = new java.util.ArrayList(fonts.size());
        for (int i = 0; i < fonts.size(); i++) {
            android.graphics.fonts.FontUpdateRequest.Font font = (android.graphics.fonts.FontUpdateRequest.Font) fonts.get(i);
            com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo fontFileInfo = this.mFontFileInfoMap.get(font.getPostScriptName());
            if (fontFileInfo == null) {
                android.util.Slog.e(TAG, "Failed to lookup font file that has " + font.getPostScriptName());
                return null;
            }
            arrayList.add(new android.text.FontConfig.Font(fontFileInfo.mFile, (java.io.File) null, fontFileInfo.getPostScriptName(), font.getFontStyle(), font.getIndex(), font.getFontVariationSettings(), (java.lang.String) null, 0));
        }
        return new android.text.FontConfig.NamedFamilyList(java.util.Collections.singletonList(new android.text.FontConfig.FontFamily(arrayList, android.os.LocaleList.getEmptyLocaleList(), 0)), family.getName());
    }

    java.util.Map<java.lang.String, java.io.File> getPostScriptMap() {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (int i = 0; i < this.mFontFileInfoMap.size(); i++) {
            com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo valueAt = this.mFontFileInfoMap.valueAt(i);
            arrayMap.put(valueAt.getPostScriptName(), valueAt.getFile());
        }
        return arrayMap;
    }

    android.text.FontConfig getSystemFontConfig() {
        android.text.FontConfig apply = this.mConfigSupplier.apply(getPostScriptMap());
        java.util.List<android.graphics.fonts.FontUpdateRequest.Family> list = readPersistentConfig().fontFamilies;
        java.util.ArrayList arrayList = new java.util.ArrayList(apply.getNamedFamilyLists().size() + list.size());
        arrayList.addAll(apply.getNamedFamilyLists());
        for (int i = 0; i < list.size(); i++) {
            android.text.FontConfig.NamedFamilyList resolveFontFilesForNamedFamily = resolveFontFilesForNamedFamily(list.get(i));
            if (resolveFontFilesForNamedFamily != null) {
                arrayList.add(resolveFontFilesForNamedFamily);
            }
        }
        return new android.text.FontConfig(apply.getFontFamilies(), apply.getAliases(), arrayList, apply.getLocaleFallbackCustomizations(), this.mLastModifiedMillis, this.mConfigVersion);
    }

    private com.android.server.graphics.fonts.PersistentSystemFontConfig.Config readPersistentConfig() {
        com.android.server.graphics.fonts.PersistentSystemFontConfig.Config config = new com.android.server.graphics.fonts.PersistentSystemFontConfig.Config();
        try {
            java.io.FileInputStream openRead = this.mConfigFile.openRead();
            try {
                com.android.server.graphics.fonts.PersistentSystemFontConfig.loadFromXml(openRead, config);
                if (openRead != null) {
                    openRead.close();
                }
            } catch (java.lang.Throwable th) {
                if (openRead != null) {
                    try {
                        openRead.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
        }
        return config;
    }

    private void writePersistentConfig(com.android.server.graphics.fonts.PersistentSystemFontConfig.Config config) throws com.android.server.graphics.fonts.FontManagerService.SystemFontException {
        java.io.FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mConfigFile.startWrite();
            com.android.server.graphics.fonts.PersistentSystemFontConfig.writeToXml(fileOutputStream, config);
            this.mConfigFile.finishWrite(fileOutputStream);
        } catch (java.io.IOException e) {
            if (fileOutputStream != null) {
                this.mConfigFile.failWrite(fileOutputStream);
            }
            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-6, "Failed to write config XML.", e);
        }
    }

    int getConfigVersion() {
        return this.mConfigVersion;
    }

    public java.util.Map<java.lang.String, android.text.FontConfig.NamedFamilyList> getFontFamilyMap() {
        com.android.server.graphics.fonts.PersistentSystemFontConfig.Config readPersistentConfig = readPersistentConfig();
        java.util.HashMap hashMap = new java.util.HashMap();
        for (int i = 0; i < readPersistentConfig.fontFamilies.size(); i++) {
            android.graphics.fonts.FontUpdateRequest.Family family = readPersistentConfig.fontFamilies.get(i);
            android.text.FontConfig.NamedFamilyList resolveFontFilesForNamedFamily = resolveFontFilesForNamedFamily(family);
            if (resolveFontFilesForNamedFamily != null) {
                hashMap.put(family.getName(), resolveFontFilesForNamedFamily);
            }
        }
        return hashMap;
    }

    static void deleteAllFiles(java.io.File file, java.io.File file2) {
        try {
            new android.util.AtomicFile(file2).delete();
        } catch (java.lang.Throwable th) {
            android.util.Slog.w(TAG, "Failed to delete " + file2);
        }
        try {
            android.os.FileUtils.deleteContents(file);
        } catch (java.lang.Throwable th2) {
            android.util.Slog.w(TAG, "Failed to delete " + file);
        }
    }
}
