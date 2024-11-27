package android.net.util;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class SocketUtils {
    public static void bindSocketToInterface(java.io.FileDescriptor fileDescriptor, java.lang.String str) throws android.system.ErrnoException {
        android.system.Os.setsockoptIfreq(fileDescriptor, android.system.OsConstants.SOL_SOCKET, android.system.OsConstants.SO_BINDTODEVICE, str);
        com.android.internal.net.NetworkUtilsInternal.protectFromVpn(fileDescriptor);
    }

    public static java.net.SocketAddress makeNetlinkSocketAddress(int i, int i2) {
        return new android.system.NetlinkSocketAddress(i, i2);
    }

    public static java.net.SocketAddress makePacketSocketAddress(int i, int i2) {
        return new android.system.PacketSocketAddress(i, i2, (byte[]) null);
    }

    @java.lang.Deprecated
    public static java.net.SocketAddress makePacketSocketAddress(int i, byte[] bArr) {
        return new android.system.PacketSocketAddress(0, i, bArr);
    }

    public static java.net.SocketAddress makePacketSocketAddress(int i, int i2, byte[] bArr) {
        return new android.system.PacketSocketAddress(i, i2, bArr);
    }

    public static void closeSocket(java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        libcore.io.IoBridge.closeAndSignalBlockedThreads(fileDescriptor);
    }

    private SocketUtils() {
    }
}
