package android.hardware;

/* loaded from: classes.dex */
public class SerialManager {
    private static final java.lang.String TAG = "SerialManager";
    private final android.content.Context mContext;
    private final android.hardware.ISerialManager mService;

    public SerialManager(android.content.Context context, android.hardware.ISerialManager iSerialManager) {
        this.mContext = context;
        this.mService = iSerialManager;
    }

    public java.lang.String[] getSerialPorts() {
        try {
            return this.mService.getSerialPorts();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.SerialPort openSerialPort(java.lang.String str, int i) throws java.io.IOException {
        try {
            android.os.ParcelFileDescriptor openSerialPort = this.mService.openSerialPort(str);
            if (openSerialPort != null) {
                android.hardware.SerialPort serialPort = new android.hardware.SerialPort(str);
                serialPort.open(openSerialPort, i);
                return serialPort;
            }
            throw new java.io.IOException("Could not open serial port " + str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
