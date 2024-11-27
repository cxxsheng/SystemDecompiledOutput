package com.android.internal.content.om;

/* loaded from: classes4.dex */
public class OverlayManagerImpl {
    private static final boolean DEBUG = false;
    private static final java.lang.String FRRO_EXTENSION = ".frro";
    private static final java.lang.String IDMAP_EXTENSION = ".idmap";
    public static final java.lang.String SELF_TARGET = ".self_target";
    private static final java.lang.String TAG = "OverlayManagerImpl";
    private java.nio.file.Path mBasePath;
    private final android.content.Context mContext;

    private static native void createFrroFile(java.lang.String str, android.os.FabricatedOverlayInternal fabricatedOverlayInternal) throws java.io.IOException;

    private static native void createIdmapFile(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) throws java.io.IOException;

    private static native android.os.FabricatedOverlayInfo getFabricatedOverlayInfo(java.lang.String str) throws java.io.IOException;

    public OverlayManagerImpl(android.content.Context context) {
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
        if (!android.os.Process.myUserHandle().equals(context.getUser())) {
            throw new java.lang.SecurityException("Self-Targeting doesn't support multiple user now!");
        }
    }

    private static void cleanExpiredOverlays(java.nio.file.Path path, java.nio.file.Path path2) {
        try {
            final java.lang.String path3 = path2.toString();
            final java.lang.String path4 = path.getFileName().toString();
            java.nio.file.Files.walkFileTree(path, new java.nio.file.SimpleFileVisitor<java.nio.file.Path>() { // from class: com.android.internal.content.om.OverlayManagerImpl.1
                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public java.nio.file.FileVisitResult preVisitDirectory(java.nio.file.Path path5, java.nio.file.attribute.BasicFileAttributes basicFileAttributes) throws java.io.IOException {
                    if (path5.getFileName().toString().equals(path3)) {
                        return java.nio.file.FileVisitResult.SKIP_SUBTREE;
                    }
                    return super.preVisitDirectory((com.android.internal.content.om.OverlayManagerImpl.AnonymousClass1) path5, basicFileAttributes);
                }

                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public java.nio.file.FileVisitResult visitFile(java.nio.file.Path path5, java.nio.file.attribute.BasicFileAttributes basicFileAttributes) throws java.io.IOException {
                    if (!path5.toFile().delete()) {
                        android.util.Log.w(com.android.internal.content.om.OverlayManagerImpl.TAG, "Failed to delete file " + path5);
                    }
                    return super.visitFile((com.android.internal.content.om.OverlayManagerImpl.AnonymousClass1) path5, basicFileAttributes);
                }

                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public java.nio.file.FileVisitResult postVisitDirectory(java.nio.file.Path path5, java.io.IOException iOException) throws java.io.IOException {
                    java.lang.String path6 = path5.getFileName().toString();
                    if (!path6.equals(path3) && !path6.equals(path4) && !path5.toFile().delete()) {
                        android.util.Log.w(com.android.internal.content.om.OverlayManagerImpl.TAG, "Failed to delete dir " + path5);
                    }
                    return super.postVisitDirectory((com.android.internal.content.om.OverlayManagerImpl.AnonymousClass1) path5, iOException);
                }
            });
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, "Unknown fail " + e);
        }
    }

    public void ensureBaseDir() {
        boolean z = false;
        java.nio.file.Path fileName = java.nio.file.Path.of(this.mContext.getApplicationInfo().getBaseCodePath(), new java.lang.String[0]).getParent().getFileName();
        java.io.File dir = this.mContext.getDir(SELF_TARGET, 0);
        com.android.internal.util.Preconditions.checkArgument(dir.isDirectory() && dir.exists() && dir.canWrite() && dir.canRead() && dir.canExecute(), "Can't work for this context");
        cleanExpiredOverlays(dir.toPath(), fileName);
        java.io.File file = new java.io.File(dir, fileName.toString());
        if (!file.exists()) {
            if (!file.mkdirs()) {
                android.util.Log.w(TAG, "Failed to create directory " + file);
            }
            android.os.FileUtils.setPermissions(file, 448, -1, -1);
        }
        if (file.isDirectory() && file.exists() && file.canWrite() && file.canRead() && file.canExecute()) {
            z = true;
        }
        com.android.internal.util.Preconditions.checkArgument(z, "Can't create a workspace for this context");
        this.mBasePath = file.toPath();
    }

    private boolean isSameWithTargetSignature(java.lang.String str) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        java.lang.String packageName = this.mContext.getPackageName();
        return android.text.TextUtils.equals(packageName, str) || packageManager.checkSignatures(packageName, str) == 0;
    }

    public static java.lang.String checkOverlayNameValid(java.lang.String str) {
        java.lang.String str2 = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "overlayName should be neither empty nor null string");
        java.lang.String validateName = android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(str2, false, true);
        com.android.internal.util.Preconditions.checkArgument(validateName == null, android.text.TextUtils.formatSimple("Invalid overlayName \"%s\". The check result is %s.", str2, validateName));
        return str2;
    }

    private void checkPackageName(java.lang.String str) {
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        com.android.internal.util.Preconditions.checkArgument(android.text.TextUtils.equals(this.mContext.getPackageName(), str), android.text.TextUtils.formatSimple("UID %d doesn't own the package %s", java.lang.Integer.valueOf(android.os.Process.myUid()), str));
    }

    public void registerFabricatedOverlay(android.os.FabricatedOverlayInternal fabricatedOverlayInternal) throws java.io.IOException, android.content.pm.PackageManager.NameNotFoundException {
        ensureBaseDir();
        java.util.Objects.requireNonNull(fabricatedOverlayInternal);
        boolean z = true;
        com.android.internal.util.Preconditions.checkArgument(!((java.util.List) java.util.Objects.requireNonNull(fabricatedOverlayInternal.entries)).isEmpty(), "overlay entries shouldn't be empty");
        java.lang.String checkOverlayNameValid = checkOverlayNameValid(fabricatedOverlayInternal.overlayName);
        checkPackageName(fabricatedOverlayInternal.packageName);
        checkPackageName(fabricatedOverlayInternal.targetPackageName);
        com.android.internal.util.Preconditions.checkStringNotEmpty(fabricatedOverlayInternal.targetOverlayable, "Target overlayable should be neither null nor empty string.");
        android.content.pm.ApplicationInfo applicationInfo = this.mContext.getApplicationInfo();
        java.lang.String str = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(applicationInfo.getBaseCodePath());
        java.nio.file.Path resolve = this.mBasePath.resolve(checkOverlayNameValid + FRRO_EXTENSION);
        java.nio.file.Path resolve2 = this.mBasePath.resolve(checkOverlayNameValid + IDMAP_EXTENSION);
        createFrroFile(resolve.toString(), fabricatedOverlayInternal);
        try {
            java.lang.String path = resolve.toString();
            java.lang.String path2 = resolve2.toString();
            if (!applicationInfo.isSystemApp() && !applicationInfo.isSystemExt()) {
                z = false;
            }
            createIdmapFile(str, path, path2, checkOverlayNameValid, z, applicationInfo.isVendor(), applicationInfo.isProduct(), isSameWithTargetSignature(fabricatedOverlayInternal.targetPackageName), applicationInfo.isOdm(), applicationInfo.isOem());
        } catch (java.io.IOException e) {
            if (!resolve.toFile().delete()) {
                android.util.Log.w(TAG, "Failed to delete file " + resolve);
            }
            throw e;
        }
    }

    public void unregisterFabricatedOverlay(java.lang.String str) {
        ensureBaseDir();
        checkOverlayNameValid(str);
        java.nio.file.Path resolve = this.mBasePath.resolve(str + FRRO_EXTENSION);
        java.nio.file.Path resolve2 = this.mBasePath.resolve(str + IDMAP_EXTENSION);
        if (!resolve.toFile().delete()) {
            android.util.Log.w(TAG, "Failed to delete file " + resolve);
        }
        if (!resolve2.toFile().delete()) {
            android.util.Log.w(TAG, "Failed to delete file " + resolve2);
        }
    }

    public void commit(android.content.om.OverlayManagerTransaction overlayManagerTransaction) throws android.content.pm.PackageManager.NameNotFoundException, java.io.IOException {
        java.util.Objects.requireNonNull(overlayManagerTransaction);
        java.util.Iterator<android.content.om.OverlayManagerTransaction.Request> requests = overlayManagerTransaction.getRequests();
        while (requests.hasNext()) {
            android.content.om.OverlayManagerTransaction.Request next = requests.next();
            if (next.type == 2) {
                android.os.FabricatedOverlayInternal fabricatedOverlayInternal = (android.os.FabricatedOverlayInternal) java.util.Objects.requireNonNull((android.os.FabricatedOverlayInternal) next.extras.getParcelable(android.content.om.OverlayManagerTransaction.Request.BUNDLE_FABRICATED_OVERLAY, android.os.FabricatedOverlayInternal.class));
                if (android.text.TextUtils.isEmpty(fabricatedOverlayInternal.packageName)) {
                    fabricatedOverlayInternal.packageName = this.mContext.getPackageName();
                } else if (!android.text.TextUtils.equals(fabricatedOverlayInternal.packageName, this.mContext.getPackageName())) {
                    throw new java.lang.IllegalArgumentException("Unknown package name in transaction");
                }
                registerFabricatedOverlay(fabricatedOverlayInternal);
            } else if (next.type == 3) {
                unregisterFabricatedOverlay(((android.content.om.OverlayIdentifier) java.util.Objects.requireNonNull(next.overlay)).getOverlayName());
            } else {
                throw new java.lang.IllegalArgumentException("Unknown request in transaction " + next);
            }
        }
    }

    public java.util.List<android.content.om.OverlayInfo> getOverlayInfosForTarget(java.lang.String str) {
        ensureBaseDir();
        java.io.File[] listFiles = this.mBasePath.toFile().listFiles(new java.io.FilenameFilter() { // from class: com.android.internal.content.om.OverlayManagerImpl$$ExternalSyntheticLambda0
            @Override // java.io.FilenameFilter
            public final boolean accept(java.io.File file, java.lang.String str2) {
                return com.android.internal.content.om.OverlayManagerImpl.lambda$getOverlayInfosForTarget$0(file, str2);
            }
        });
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.io.File file : listFiles) {
            try {
                android.os.FabricatedOverlayInfo fabricatedOverlayInfo = getFabricatedOverlayInfo(file.getAbsolutePath());
                if (android.text.TextUtils.equals(str, fabricatedOverlayInfo.targetPackageName)) {
                    arrayList.add(new android.content.om.OverlayInfo(fabricatedOverlayInfo.packageName, fabricatedOverlayInfo.overlayName, fabricatedOverlayInfo.targetPackageName, fabricatedOverlayInfo.targetOverlayable, null, file.getAbsolutePath(), 3, android.os.UserHandle.myUserId(), Integer.MAX_VALUE, true, true));
                }
            } catch (java.io.IOException e) {
                android.util.Log.w(TAG, "can't load " + file);
            }
        }
        return arrayList;
    }

    static /* synthetic */ boolean lambda$getOverlayInfosForTarget$0(java.io.File file, java.lang.String str) {
        if (str.endsWith(FRRO_EXTENSION)) {
            return new java.io.File(file, str.substring(0, str.length() - FRRO_EXTENSION.length()) + IDMAP_EXTENSION).exists();
        }
        return false;
    }
}
