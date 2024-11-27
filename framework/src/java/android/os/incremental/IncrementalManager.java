package android.os.incremental;

/* loaded from: classes3.dex */
public final class IncrementalManager {
    private static final java.lang.String ALLOWED_PROPERTY = "incremental.allowed";
    public static final int CREATE_MODE_CREATE = 4;
    public static final int CREATE_MODE_OPEN_EXISTING = 8;
    public static final int CREATE_MODE_PERMANENT_BIND = 2;
    public static final int CREATE_MODE_TEMPORARY_BIND = 1;
    public static final int MIN_VERSION_TO_SUPPORT_FSVERITY = 2;
    private static final java.lang.String TAG = "IncrementalManager";
    private final android.os.incremental.IncrementalManager.LoadingProgressCallbacks mLoadingProgressCallbacks = new android.os.incremental.IncrementalManager.LoadingProgressCallbacks();
    private final android.os.incremental.IIncrementalService mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CreateMode {
    }

    private static native boolean nativeIsEnabled();

    private static native boolean nativeIsIncrementalFd(int i);

    private static native boolean nativeIsIncrementalPath(java.lang.String str);

    private static native boolean nativeIsV2Available();

    private static native byte[] nativeUnsafeGetFileSignature(java.lang.String str);

    public IncrementalManager(android.os.incremental.IIncrementalService iIncrementalService) {
        this.mService = iIncrementalService;
    }

    public android.os.incremental.IncrementalStorage createStorage(java.lang.String str, android.content.pm.DataLoaderParams dataLoaderParams, int i) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(dataLoaderParams);
        try {
            int createStorage = this.mService.createStorage(str, dataLoaderParams.getData(), i);
            if (createStorage < 0) {
                return null;
            }
            return new android.os.incremental.IncrementalStorage(this.mService, createStorage);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.incremental.IncrementalStorage openStorage(java.lang.String str) {
        try {
            int openStorage = this.mService.openStorage(str);
            if (openStorage < 0) {
                return null;
            }
            return new android.os.incremental.IncrementalStorage(this.mService, openStorage);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.incremental.IncrementalStorage createStorage(java.lang.String str, android.os.incremental.IncrementalStorage incrementalStorage, int i) {
        int i2 = -1;
        try {
            android.system.StructStat stat = android.system.Os.stat(str);
            i2 = this.mService.createLinkedStorage(str, incrementalStorage.getId(), i);
            if (i2 < 0) {
                return null;
            }
            android.system.Os.chmod(str, stat.st_mode & 4095);
            return new android.os.incremental.IncrementalStorage(this.mService, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.system.ErrnoException e2) {
            if (i2 >= 0) {
                try {
                    this.mService.deleteStorage(i2);
                } catch (android.os.RemoteException e3) {
                    throw e3.rethrowFromSystemServer();
                }
            }
            throw new java.lang.RuntimeException(e2);
        }
    }

    public void linkCodePath(java.io.File file, java.io.File file2) throws java.lang.IllegalArgumentException, java.io.IOException {
        java.io.File absoluteFile = file.getAbsoluteFile();
        android.os.incremental.IncrementalStorage openStorage = openStorage(absoluteFile.toString());
        if (openStorage == null) {
            throw new java.lang.IllegalArgumentException("Not an Incremental path: " + absoluteFile);
        }
        java.lang.String parent = file2.getAbsoluteFile().getParent();
        android.os.incremental.IncrementalStorage createStorage = createStorage(parent, openStorage, 6);
        if (createStorage == null) {
            throw new java.io.IOException("Failed to create linked storage at dir: " + parent);
        }
        try {
            linkFiles(openStorage, absoluteFile, "", createStorage, file2.getName());
        } catch (java.lang.Exception e) {
            createStorage.unBind(parent);
            throw e;
        }
    }

    private void linkFiles(final android.os.incremental.IncrementalStorage incrementalStorage, java.io.File file, java.lang.String str, final android.os.incremental.IncrementalStorage incrementalStorage2, java.lang.String str2) throws java.io.IOException {
        final java.nio.file.Path resolve = file.toPath().resolve(str);
        final java.nio.file.Path path = java.nio.file.Paths.get(str2, new java.lang.String[0]);
        java.nio.file.Files.walkFileTree(file.toPath(), new java.nio.file.SimpleFileVisitor<java.nio.file.Path>() { // from class: android.os.incremental.IncrementalManager.1
            @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
            public java.nio.file.FileVisitResult preVisitDirectory(java.nio.file.Path path2, java.nio.file.attribute.BasicFileAttributes basicFileAttributes) throws java.io.IOException {
                incrementalStorage2.makeDirectory(path.resolve(resolve.relativize(path2)).toString());
                return java.nio.file.FileVisitResult.CONTINUE;
            }

            @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
            public java.nio.file.FileVisitResult visitFile(java.nio.file.Path path2, java.nio.file.attribute.BasicFileAttributes basicFileAttributes) throws java.io.IOException {
                incrementalStorage.makeLink(path2.toAbsolutePath().toString(), incrementalStorage2, path.resolve(resolve.relativize(path2)).toString());
                return java.nio.file.FileVisitResult.CONTINUE;
            }
        });
    }

    public static boolean isFeatureEnabled() {
        return nativeIsEnabled();
    }

    public static int getVersion() {
        if (nativeIsEnabled()) {
            return nativeIsV2Available() ? 2 : 1;
        }
        return 0;
    }

    public static boolean isAllowed() {
        return isFeatureEnabled() && android.os.SystemProperties.getBoolean(ALLOWED_PROPERTY, true);
    }

    public static boolean isIncrementalPath(java.lang.String str) {
        return nativeIsIncrementalPath(str);
    }

    public static boolean isIncrementalFileFd(java.io.FileDescriptor fileDescriptor) {
        return nativeIsIncrementalFd(fileDescriptor.getInt$());
    }

    public static byte[] unsafeGetFileSignature(java.lang.String str) {
        return nativeUnsafeGetFileSignature(str);
    }

    public void rmPackageDir(java.io.File file) {
        try {
            java.lang.String absolutePath = file.getAbsolutePath();
            android.os.incremental.IncrementalStorage openStorage = openStorage(absolutePath);
            if (openStorage == null) {
                return;
            }
            this.mLoadingProgressCallbacks.cleanUpCallbacks(openStorage);
            openStorage.unBind(absolutePath);
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Failed to remove code path", e);
        }
    }

    public boolean registerLoadingProgressCallback(java.lang.String str, android.content.pm.IPackageLoadingProgressCallback iPackageLoadingProgressCallback) {
        android.os.incremental.IncrementalStorage openStorage = openStorage(str);
        if (openStorage == null) {
            return false;
        }
        return this.mLoadingProgressCallbacks.registerCallback(openStorage, iPackageLoadingProgressCallback);
    }

    public void unregisterLoadingProgressCallbacks(java.lang.String str) {
        android.os.incremental.IncrementalStorage openStorage = openStorage(str);
        if (openStorage == null) {
            return;
        }
        this.mLoadingProgressCallbacks.cleanUpCallbacks(openStorage);
    }

    private static class LoadingProgressCallbacks extends android.os.incremental.IStorageLoadingProgressListener.Stub {
        private final android.util.SparseArray<android.os.RemoteCallbackList<android.content.pm.IPackageLoadingProgressCallback>> mCallbacks;

        private LoadingProgressCallbacks() {
            this.mCallbacks = new android.util.SparseArray<>();
        }

        public void cleanUpCallbacks(android.os.incremental.IncrementalStorage incrementalStorage) {
            android.os.RemoteCallbackList<android.content.pm.IPackageLoadingProgressCallback> removeReturnOld;
            int id = incrementalStorage.getId();
            synchronized (this.mCallbacks) {
                removeReturnOld = this.mCallbacks.removeReturnOld(id);
            }
            if (removeReturnOld == null) {
                return;
            }
            removeReturnOld.kill();
            incrementalStorage.unregisterLoadingProgressListener();
        }

        public boolean registerCallback(android.os.incremental.IncrementalStorage incrementalStorage, android.content.pm.IPackageLoadingProgressCallback iPackageLoadingProgressCallback) {
            int id = incrementalStorage.getId();
            synchronized (this.mCallbacks) {
                android.os.RemoteCallbackList<android.content.pm.IPackageLoadingProgressCallback> remoteCallbackList = this.mCallbacks.get(id);
                if (remoteCallbackList == null) {
                    remoteCallbackList = new android.os.RemoteCallbackList<>();
                    this.mCallbacks.put(id, remoteCallbackList);
                }
                remoteCallbackList.register(iPackageLoadingProgressCallback);
                if (remoteCallbackList.getRegisteredCallbackCount() > 1) {
                    return true;
                }
                return incrementalStorage.registerLoadingProgressListener(this);
            }
        }

        @Override // android.os.incremental.IStorageLoadingProgressListener
        public void onStorageLoadingProgressChanged(int i, float f) {
            android.os.RemoteCallbackList<android.content.pm.IPackageLoadingProgressCallback> remoteCallbackList;
            synchronized (this.mCallbacks) {
                remoteCallbackList = this.mCallbacks.get(i);
            }
            if (remoteCallbackList == null) {
                return;
            }
            int beginBroadcast = remoteCallbackList.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    remoteCallbackList.getBroadcastItem(i2).onPackageLoadingProgressChanged(f);
                } catch (android.os.RemoteException e) {
                }
            }
            remoteCallbackList.finishBroadcast();
        }
    }

    public android.os.incremental.IncrementalMetrics getMetrics(java.lang.String str) {
        android.os.incremental.IncrementalStorage openStorage = openStorage(str);
        if (openStorage == null) {
            return null;
        }
        return new android.os.incremental.IncrementalMetrics(openStorage.getMetrics());
    }
}
