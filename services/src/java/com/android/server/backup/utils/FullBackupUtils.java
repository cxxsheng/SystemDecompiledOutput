package com.android.server.backup.utils;

/* loaded from: classes.dex */
public class FullBackupUtils {
    public static void routeSocketDataToOutput(android.os.ParcelFileDescriptor parcelFileDescriptor, java.io.OutputStream outputStream) throws java.io.IOException {
        int i;
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor()));
        if (!com.android.server.backup.Flags.enableMaxSizeWritesToPipes()) {
            i = 32768;
        } else {
            i = 65536;
        }
        byte[] bArr = new byte[i];
        while (true) {
            int readInt = dataInputStream.readInt();
            if (readInt > 0) {
                while (readInt > 0) {
                    int read = dataInputStream.read(bArr, 0, readInt > i ? i : readInt);
                    if (read < 0) {
                        android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unexpectedly reached end of file while reading data");
                        throw new java.io.EOFException();
                    }
                    outputStream.write(bArr, 0, read);
                    readInt -= read;
                }
            } else {
                return;
            }
        }
    }
}
