package android.debug;

/* loaded from: classes.dex */
public abstract class AdbManagerInternal {
    public abstract java.io.File getAdbKeysFile();

    public abstract java.io.File getAdbTempKeysFile();

    public abstract boolean isAdbEnabled(byte b);

    public abstract void notifyKeyFilesUpdated();

    public abstract void registerTransport(android.debug.IAdbTransport iAdbTransport);

    public abstract void startAdbdForTransport(byte b);

    public abstract void stopAdbdForTransport(byte b);

    public abstract void unregisterTransport(android.debug.IAdbTransport iAdbTransport);
}
