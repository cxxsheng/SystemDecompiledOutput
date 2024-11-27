package android.hardware;

/* loaded from: classes.dex */
public class SerialPort {
    private static final java.lang.String TAG = "SerialPort";
    private android.os.ParcelFileDescriptor mFileDescriptor;
    private final java.lang.String mName;
    private int mNativeContext;

    private native void native_close();

    private native void native_open(java.io.FileDescriptor fileDescriptor, int i) throws java.io.IOException;

    private native int native_read_array(byte[] bArr, int i) throws java.io.IOException;

    private native int native_read_direct(java.nio.ByteBuffer byteBuffer, int i) throws java.io.IOException;

    private native void native_send_break();

    private native void native_write_array(byte[] bArr, int i) throws java.io.IOException;

    private native void native_write_direct(java.nio.ByteBuffer byteBuffer, int i) throws java.io.IOException;

    public SerialPort(java.lang.String str) {
        this.mName = str;
    }

    public void open(android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws java.io.IOException {
        native_open(parcelFileDescriptor.getFileDescriptor(), i);
        this.mFileDescriptor = parcelFileDescriptor;
    }

    public void close() throws java.io.IOException {
        if (this.mFileDescriptor != null) {
            this.mFileDescriptor.close();
            this.mFileDescriptor = null;
        }
        native_close();
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int read(java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
        if (byteBuffer.isDirect()) {
            return native_read_direct(byteBuffer, byteBuffer.remaining());
        }
        if (byteBuffer.hasArray()) {
            return native_read_array(byteBuffer.array(), byteBuffer.remaining());
        }
        throw new java.lang.IllegalArgumentException("buffer is not direct and has no array");
    }

    public void write(java.nio.ByteBuffer byteBuffer, int i) throws java.io.IOException {
        if (byteBuffer.isDirect()) {
            native_write_direct(byteBuffer, i);
        } else {
            if (byteBuffer.hasArray()) {
                native_write_array(byteBuffer.array(), i);
                return;
            }
            throw new java.lang.IllegalArgumentException("buffer is not direct and has no array");
        }
    }

    public void sendBreak() {
        native_send_break();
    }
}
