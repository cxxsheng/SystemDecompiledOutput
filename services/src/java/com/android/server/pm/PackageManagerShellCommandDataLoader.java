package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageManagerShellCommandDataLoader extends android.service.dataloader.DataLoaderService {
    private static final char ARGS_DELIM = '&';
    private static final int INVALID_SHELL_COMMAND_ID = -1;
    private static final java.lang.String PACKAGE = "android";
    private static final java.lang.String SHELL_COMMAND_ID_PREFIX = "shellCommandId=";
    private static final java.lang.String STDIN_PATH = "-";
    public static final java.lang.String TAG = "PackageManagerShellCommandDataLoader";
    private static final int TOO_MANY_PENDING_SHELL_COMMANDS = 10;
    private static final java.lang.String CLASS = com.android.server.pm.PackageManagerShellCommandDataLoader.class.getName();
    static final java.security.SecureRandom sRandom = new java.security.SecureRandom();
    static final android.util.SparseArray<java.lang.ref.WeakReference<android.os.ShellCommand>> sShellCommands = new android.util.SparseArray<>();

    private static native void nativeInitialize();

    private static java.lang.String getDataLoaderParamsArgs(android.os.ShellCommand shellCommand) {
        int nextInt;
        nativeInitialize();
        synchronized (sShellCommands) {
            try {
                for (int size = sShellCommands.size() - 1; size >= 0; size--) {
                    if (sShellCommands.valueAt(size).get() == null) {
                        sShellCommands.removeAt(size);
                    }
                }
                if (sShellCommands.size() > 10) {
                    android.util.Slog.e(TAG, "Too many pending shell commands: " + sShellCommands.size());
                }
                do {
                    nextInt = sRandom.nextInt(2147483646) + 1;
                } while (sShellCommands.contains(nextInt));
                sShellCommands.put(nextInt, new java.lang.ref.WeakReference<>(shellCommand));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return SHELL_COMMAND_ID_PREFIX + nextInt;
    }

    static android.content.pm.DataLoaderParams getStreamingDataLoaderParams(android.os.ShellCommand shellCommand) {
        return android.content.pm.DataLoaderParams.forStreaming(new android.content.ComponentName("android", CLASS), getDataLoaderParamsArgs(shellCommand));
    }

    static android.content.pm.DataLoaderParams getIncrementalDataLoaderParams(android.os.ShellCommand shellCommand) {
        return android.content.pm.DataLoaderParams.forIncremental(new android.content.ComponentName("android", CLASS), getDataLoaderParamsArgs(shellCommand));
    }

    private static int extractShellCommandId(java.lang.String str) {
        int indexOf = str.indexOf(SHELL_COMMAND_ID_PREFIX);
        if (indexOf < 0) {
            android.util.Slog.e(TAG, "Missing shell command id param.");
            return -1;
        }
        int length = indexOf + SHELL_COMMAND_ID_PREFIX.length();
        int indexOf2 = str.indexOf(38, length);
        try {
            if (indexOf2 < 0) {
                return java.lang.Integer.parseInt(str.substring(length));
            }
            return java.lang.Integer.parseInt(str.substring(length, indexOf2));
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.e(TAG, "Incorrect shell command id format.", e);
            return -1;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class Metadata {
        static final byte ARCHIVED = 4;
        static final byte DATA_ONLY_STREAMING = 2;
        static final byte LOCAL_FILE = 1;
        static final byte STDIN = 0;
        static final byte STREAMING = 3;
        private static final java.util.concurrent.atomic.AtomicLong sGlobalSalt = new java.util.concurrent.atomic.AtomicLong(new java.security.SecureRandom().nextLong());
        private final byte[] mData;
        private final byte mMode;
        private final java.lang.String mSalt;

        private static java.lang.Long nextGlobalSalt() {
            return java.lang.Long.valueOf(sGlobalSalt.incrementAndGet());
        }

        static com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata forStdIn(java.lang.String str) {
            return new com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata((byte) 0, str);
        }

        @com.android.internal.annotations.VisibleForTesting
        public static com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata forLocalFile(java.lang.String str) {
            return new com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata((byte) 1, str, nextGlobalSalt().toString());
        }

        @com.android.internal.annotations.VisibleForTesting
        public static com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata forArchived(android.content.pm.ArchivedPackageParcel archivedPackageParcel) {
            return new com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata((byte) 4, writeArchivedPackageParcel(archivedPackageParcel), (java.lang.String) null);
        }

        static com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata forDataOnlyStreaming(java.lang.String str) {
            return new com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata((byte) 2, str);
        }

        static com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata forStreaming(java.lang.String str) {
            return new com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata((byte) 3, str);
        }

        private Metadata(byte b, java.lang.String str) {
            this(b, str, (java.lang.String) null);
        }

        private Metadata(byte b, java.lang.String str, java.lang.String str2) {
            this(b, (str == null ? "" : str).getBytes(java.nio.charset.StandardCharsets.UTF_8), str2);
        }

        private Metadata(byte b, byte[] bArr, java.lang.String str) {
            this.mMode = b;
            this.mData = bArr;
            this.mSalt = str;
        }

        static com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata fromByteArray(byte[] bArr) throws java.io.IOException {
            byte[] bArr2;
            java.lang.String str = null;
            if (bArr == null || bArr.length < 5) {
                return null;
            }
            byte b = bArr[0];
            switch (b) {
                case 1:
                    int i = java.nio.ByteBuffer.wrap(bArr, 1, 4).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt() + 5;
                    byte[] copyOfRange = java.util.Arrays.copyOfRange(bArr, 5, i);
                    java.lang.String str2 = new java.lang.String(bArr, i, bArr.length - i, java.nio.charset.StandardCharsets.UTF_8);
                    bArr2 = copyOfRange;
                    str = str2;
                    break;
                default:
                    bArr2 = java.util.Arrays.copyOfRange(bArr, 1, bArr.length);
                    break;
            }
            return new com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata(b, bArr2, str);
        }

        @com.android.internal.annotations.VisibleForTesting
        public byte[] toByteArray() {
            byte[] bArr = this.mData;
            switch (this.mMode) {
                case 1:
                    int length = bArr.length;
                    byte[] bytes = this.mSalt.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                    byte[] bArr2 = new byte[length + 5 + bytes.length];
                    bArr2[0] = this.mMode;
                    java.nio.ByteBuffer.wrap(bArr2, 1, 4).order(java.nio.ByteOrder.LITTLE_ENDIAN).putInt(length);
                    java.lang.System.arraycopy(bArr, 0, bArr2, 5, length);
                    java.lang.System.arraycopy(bytes, 0, bArr2, 5 + length, bytes.length);
                    return bArr2;
                default:
                    byte[] bArr3 = new byte[bArr.length + 1];
                    bArr3[0] = this.mMode;
                    java.lang.System.arraycopy(bArr, 0, bArr3, 1, bArr.length);
                    return bArr3;
            }
        }

        byte getMode() {
            return this.mMode;
        }

        byte[] getData() {
            return this.mData;
        }

        android.content.pm.ArchivedPackageParcel getArchivedPackage() {
            if (getMode() != 4) {
                throw new java.lang.IllegalStateException("Not an archived package metadata.");
            }
            return readArchivedPackageParcel(this.mData);
        }

        static android.content.pm.ArchivedPackageParcel readArchivedPackageParcel(byte[] bArr) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            try {
                obtain.unmarshall(bArr, 0, bArr.length);
                obtain.setDataPosition(0);
                return obtain.readParcelable(android.content.pm.ArchivedPackageParcel.class.getClassLoader());
            } finally {
                obtain.recycle();
            }
        }

        static byte[] writeArchivedPackageParcel(android.content.pm.ArchivedPackageParcel archivedPackageParcel) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            try {
                obtain.writeParcelable(archivedPackageParcel, 0);
                return obtain.marshall();
            } finally {
                obtain.recycle();
            }
        }
    }

    private static class DataLoader implements android.service.dataloader.DataLoaderService.DataLoader {
        private android.service.dataloader.DataLoaderService.FileSystemConnector mConnector;
        private android.content.pm.DataLoaderParams mParams;

        private DataLoader() {
            this.mParams = null;
            this.mConnector = null;
        }

        public boolean onCreate(@android.annotation.NonNull android.content.pm.DataLoaderParams dataLoaderParams, @android.annotation.NonNull android.service.dataloader.DataLoaderService.FileSystemConnector fileSystemConnector) {
            this.mParams = dataLoaderParams;
            this.mConnector = fileSystemConnector;
            return true;
        }

        public boolean onPrepareImage(@android.annotation.NonNull java.util.Collection<android.content.pm.InstallationFile> collection, @android.annotation.NonNull java.util.Collection<java.lang.String> collection2) {
            android.os.ShellCommand lookupShellCommand = com.android.server.pm.PackageManagerShellCommandDataLoader.lookupShellCommand(this.mParams.getArguments());
            try {
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.pm.PackageManagerShellCommandDataLoader.TAG, "Exception while streaming files", e);
                return false;
            }
            for (android.content.pm.InstallationFile installationFile : collection) {
                com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata fromByteArray = com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata.fromByteArray(installationFile.getMetadata());
                if (fromByteArray == null) {
                    android.util.Slog.e(com.android.server.pm.PackageManagerShellCommandDataLoader.TAG, "Invalid metadata for file: " + installationFile.getName());
                    return false;
                }
                switch (fromByteArray.getMode()) {
                    case 0:
                        if (lookupShellCommand == null) {
                            android.util.Slog.e(com.android.server.pm.PackageManagerShellCommandDataLoader.TAG, "Missing shell command for Metadata.STDIN.");
                            return false;
                        }
                        this.mConnector.writeData(installationFile.getName(), 0L, installationFile.getLengthBytes(), com.android.server.pm.PackageManagerShellCommandDataLoader.getStdInPFD(lookupShellCommand));
                        continue;
                    case 1:
                        if (lookupShellCommand == null) {
                            android.util.Slog.e(com.android.server.pm.PackageManagerShellCommandDataLoader.TAG, "Missing shell command for Metadata.LOCAL_FILE.");
                            return false;
                        }
                        android.os.ParcelFileDescriptor parcelFileDescriptor = null;
                        try {
                            parcelFileDescriptor = com.android.server.pm.PackageManagerShellCommandDataLoader.getLocalFilePFD(lookupShellCommand, new java.lang.String(fromByteArray.getData(), java.nio.charset.StandardCharsets.UTF_8));
                            this.mConnector.writeData(installationFile.getName(), 0L, parcelFileDescriptor.getStatSize(), parcelFileDescriptor);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                        } catch (java.lang.Throwable th) {
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                            throw th;
                        }
                    case 2:
                    case 3:
                    default:
                        android.util.Slog.e(com.android.server.pm.PackageManagerShellCommandDataLoader.TAG, "Unsupported metadata mode: " + ((int) fromByteArray.getMode()));
                        return false;
                    case 4:
                        continue;
                }
                android.util.Slog.e(com.android.server.pm.PackageManagerShellCommandDataLoader.TAG, "Exception while streaming files", e);
                return false;
            }
            return true;
        }
    }

    static android.os.ShellCommand lookupShellCommand(java.lang.String str) {
        java.lang.ref.WeakReference<android.os.ShellCommand> weakReference;
        int extractShellCommandId = extractShellCommandId(str);
        if (extractShellCommandId == -1) {
            return null;
        }
        synchronized (sShellCommands) {
            weakReference = sShellCommands.get(extractShellCommandId, null);
        }
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    static android.os.ParcelFileDescriptor getStdInPFD(android.os.ShellCommand shellCommand) {
        try {
            return android.os.ParcelFileDescriptor.dup(shellCommand.getInFileDescriptor());
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Exception while obtaining STDIN fd", e);
            return null;
        }
    }

    static android.os.ParcelFileDescriptor getLocalFilePFD(android.os.ShellCommand shellCommand, java.lang.String str) {
        return shellCommand.openFileForSystem(str, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
    }

    static int getStdIn(android.os.ShellCommand shellCommand) {
        android.os.ParcelFileDescriptor stdInPFD = getStdInPFD(shellCommand);
        if (stdInPFD == null) {
            return -1;
        }
        return stdInPFD.detachFd();
    }

    static int getLocalFile(android.os.ShellCommand shellCommand, java.lang.String str) {
        android.os.ParcelFileDescriptor localFilePFD = getLocalFilePFD(shellCommand, str);
        if (localFilePFD == null) {
            return -1;
        }
        return localFilePFD.detachFd();
    }

    public android.service.dataloader.DataLoaderService.DataLoader onCreateDataLoader(@android.annotation.NonNull android.content.pm.DataLoaderParams dataLoaderParams) {
        if (dataLoaderParams.getType() == 1) {
            return new com.android.server.pm.PackageManagerShellCommandDataLoader.DataLoader();
        }
        return null;
    }
}
