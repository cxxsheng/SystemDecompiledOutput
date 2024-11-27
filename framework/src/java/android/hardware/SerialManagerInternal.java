package android.hardware;

/* loaded from: classes.dex */
public abstract class SerialManagerInternal {
    public abstract void addVirtualSerialPortForTest(java.lang.String str, java.util.function.Supplier<android.os.ParcelFileDescriptor> supplier);

    public abstract void removeVirtualSerialPortForTest(java.lang.String str);
}
