package android.companion;

/* loaded from: classes.dex */
public class DeviceNotAssociatedException extends java.lang.RuntimeException {
    public DeviceNotAssociatedException(java.lang.String str) {
        super("Device not associated with the current app: " + str);
    }
}
