package android.util;

/* loaded from: classes3.dex */
public class BackupUtils {
    public static final int NOT_NULL = 1;
    public static final int NULL = 0;

    public static class BadVersionException extends java.lang.Exception {
        public BadVersionException(java.lang.String str) {
            super(str);
        }

        public BadVersionException(java.lang.String str, java.lang.Throwable th) {
            super(str, th);
        }
    }

    public static java.lang.String readString(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        if (dataInputStream.readByte() == 1) {
            return dataInputStream.readUTF();
        }
        return null;
    }

    public static void writeString(java.io.DataOutputStream dataOutputStream, java.lang.String str) throws java.io.IOException {
        if (str != null) {
            dataOutputStream.writeByte(1);
            dataOutputStream.writeUTF(str);
        } else {
            dataOutputStream.writeByte(0);
        }
    }
}
