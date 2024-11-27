package com.android.server.pm;

/* loaded from: classes2.dex */
public class ShortcutDumpFiles {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ShortcutService";
    private final com.android.server.pm.ShortcutService mService;

    public ShortcutDumpFiles(com.android.server.pm.ShortcutService shortcutService) {
        this.mService = shortcutService;
    }

    public boolean save(java.lang.String str, java.util.function.Consumer<java.io.PrintWriter> consumer) {
        try {
            java.io.File dumpPath = this.mService.getDumpPath();
            dumpPath.mkdirs();
            if (!dumpPath.exists()) {
                android.util.Slog.e(TAG, "Failed to create directory: " + dumpPath);
                return false;
            }
            java.io.PrintWriter printWriter = new java.io.PrintWriter(new java.io.BufferedOutputStream(new java.io.FileOutputStream(new java.io.File(dumpPath, str))));
            try {
                consumer.accept(printWriter);
                printWriter.close();
                return true;
            } catch (java.lang.Throwable th) {
                try {
                    printWriter.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException | java.lang.RuntimeException e) {
            android.util.Slog.w(TAG, "Failed to create dump file: " + str, e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$save$0(byte[] bArr, java.io.PrintWriter printWriter) {
        printWriter.println(java.nio.charset.StandardCharsets.UTF_8.decode(java.nio.ByteBuffer.wrap(bArr)).toString());
    }

    public boolean save(java.lang.String str, final byte[] bArr) {
        return save(str, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutDumpFiles$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutDumpFiles.lambda$save$0(bArr, (java.io.PrintWriter) obj);
            }
        });
    }

    public void dumpAll(java.io.PrintWriter printWriter) {
        try {
            java.io.File dumpPath = this.mService.getDumpPath();
            java.io.File[] listFiles = dumpPath.listFiles(new java.io.FileFilter() { // from class: com.android.server.pm.ShortcutDumpFiles$$ExternalSyntheticLambda1
                @Override // java.io.FileFilter
                public final boolean accept(java.io.File file) {
                    boolean isFile;
                    isFile = file.isFile();
                    return isFile;
                }
            });
            if (!dumpPath.exists() || com.android.internal.util.ArrayUtils.isEmpty(listFiles)) {
                printWriter.print("  No dump files found.");
                return;
            }
            java.util.Arrays.sort(listFiles, java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutDumpFiles$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String name;
                    name = ((java.io.File) obj).getName();
                    return name;
                }
            }));
            for (java.io.File file : listFiles) {
                printWriter.print("*** Dumping: ");
                printWriter.println(file.getName());
                printWriter.print("mtime: ");
                printWriter.println(com.android.server.pm.ShortcutService.formatTime(file.lastModified()));
                java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(file)));
                while (true) {
                    try {
                        java.lang.String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        } else {
                            printWriter.println(readLine);
                        }
                    } catch (java.lang.Throwable th) {
                        try {
                            bufferedReader.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                bufferedReader.close();
            }
        } catch (java.io.IOException | java.lang.RuntimeException e) {
            android.util.Slog.w(TAG, "Failed to print dump files", e);
        }
    }
}
