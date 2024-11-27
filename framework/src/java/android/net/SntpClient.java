package android.net;

/* loaded from: classes2.dex */
public class SntpClient {
    private static final boolean DBG = true;
    private static final int NTP_LEAP_NOSYNC = 3;
    private static final int NTP_MODE_BROADCAST = 5;
    private static final int NTP_MODE_CLIENT = 3;
    private static final int NTP_MODE_SERVER = 4;
    private static final int NTP_PACKET_SIZE = 48;
    private static final int NTP_STRATUM_DEATH = 0;
    private static final int NTP_STRATUM_MAX = 15;
    private static final int NTP_VERSION = 3;
    private static final int ORIGINATE_TIME_OFFSET = 24;
    private static final int RECEIVE_TIME_OFFSET = 32;
    private static final int REFERENCE_TIME_OFFSET = 16;
    public static final int STANDARD_NTP_PORT = 123;
    private static final java.lang.String TAG = "SntpClient";
    private static final int TRANSMIT_TIME_OFFSET = 40;
    private long mClockOffset;
    private long mNtpTime;
    private long mNtpTimeReference;
    private final java.util.Random mRandom;
    private long mRoundTripTime;
    private java.net.InetSocketAddress mServerSocketAddress;
    private final java.util.function.Supplier<java.time.Instant> mSystemTimeSupplier;

    private static class InvalidServerReplyException extends java.lang.Exception {
        public InvalidServerReplyException(java.lang.String str) {
            super(str);
        }
    }

    public SntpClient() {
        this(new java.util.function.Supplier() { // from class: android.net.SntpClient$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.time.Instant.now();
            }
        }, defaultRandom());
    }

    public SntpClient(java.util.function.Supplier<java.time.Instant> supplier, java.util.Random random) {
        this.mSystemTimeSupplier = (java.util.function.Supplier) java.util.Objects.requireNonNull(supplier);
        this.mRandom = (java.util.Random) java.util.Objects.requireNonNull(random);
    }

    public boolean requestTime(java.lang.String str, int i, int i2, android.net.Network network) {
        android.net.Network privateDnsBypassingCopy = network.getPrivateDnsBypassingCopy();
        try {
            for (java.net.InetAddress inetAddress : privateDnsBypassingCopy.getAllByName(str)) {
                if (requestTime(inetAddress, i, i2, privateDnsBypassingCopy)) {
                    return true;
                }
            }
        } catch (java.net.UnknownHostException e) {
            android.util.Log.w(TAG, "Unknown host: " + str);
            android.net.EventLogTags.writeNtpFailure(str, e.toString());
        }
        android.util.Log.d(TAG, "request time failed");
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x014e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean requestTime(java.net.InetAddress inetAddress, int i, int i2, android.net.Network network) {
        int i3;
        java.net.DatagramSocket datagramSocket;
        android.net.sntp.Timestamp64 fromInstant;
        android.net.sntp.Timestamp64 randomizeSubMillis;
        long elapsedRealtime;
        long j;
        java.time.Instant plusMillis;
        android.net.sntp.Timestamp64 fromInstant2;
        byte b;
        byte b2;
        int i4;
        android.net.sntp.Timestamp64 readTimeStamp;
        android.net.sntp.Timestamp64 readTimeStamp2;
        android.net.sntp.Timestamp64 readTimeStamp3;
        android.net.sntp.Timestamp64 readTimeStamp4;
        int andSetThreadStatsTag = android.net.TrafficStats.getAndSetThreadStatsTag(com.android.internal.util.TrafficStatsConstants.TAG_SYSTEM_NTP);
        java.net.DatagramSocket datagramSocket2 = null;
        try {
            datagramSocket = new java.net.DatagramSocket();
            try {
                network.bindSocket(datagramSocket);
                datagramSocket.setSoTimeout(i2);
                byte[] bArr = new byte[48];
                java.net.DatagramPacket datagramPacket = new java.net.DatagramPacket(bArr, 48, inetAddress, i);
                bArr[0] = com.android.internal.telephony.GsmAlphabet.GSM_EXTENDED_ESCAPE;
                java.time.Instant instant = this.mSystemTimeSupplier.get();
                fromInstant = android.net.sntp.Timestamp64.fromInstant(instant);
                randomizeSubMillis = fromInstant.randomizeSubMillis(this.mRandom);
                long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                writeTimeStamp(bArr, 40, randomizeSubMillis);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(new java.net.DatagramPacket(bArr, 48));
                elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                j = elapsedRealtime - elapsedRealtime2;
                plusMillis = instant.plusMillis(j);
                fromInstant2 = android.net.sntp.Timestamp64.fromInstant(plusMillis);
                b = (byte) ((bArr[0] >> 6) & 3);
                b2 = (byte) (bArr[0] & 7);
                i4 = bArr[1] & 255;
                readTimeStamp = readTimeStamp(bArr, 16);
                readTimeStamp2 = readTimeStamp(bArr, 24);
                readTimeStamp3 = readTimeStamp(bArr, 32);
                readTimeStamp4 = readTimeStamp(bArr, 40);
                i3 = andSetThreadStatsTag;
            } catch (java.lang.Exception e) {
                e = e;
                i3 = andSetThreadStatsTag;
            } catch (java.lang.Throwable th) {
                th = th;
                i3 = andSetThreadStatsTag;
            }
        } catch (java.lang.Exception e2) {
            e = e2;
            i3 = andSetThreadStatsTag;
        } catch (java.lang.Throwable th2) {
            th = th2;
            i3 = andSetThreadStatsTag;
        }
        try {
            checkValidServerReply(b, b2, i4, readTimeStamp4, readTimeStamp, randomizeSubMillis, readTimeStamp2);
            long millis = j - android.net.sntp.Duration64.between(readTimeStamp3, readTimeStamp4).toDuration().toMillis();
            java.time.Duration calculateClockOffset = calculateClockOffset(fromInstant, readTimeStamp3, readTimeStamp4, fromInstant2);
            long millis2 = calculateClockOffset.toMillis();
            android.net.EventLogTags.writeNtpSuccess(inetAddress.toString(), millis, millis2);
            android.util.Log.d(TAG, "round trip: " + millis + "ms, clock offset: " + millis2 + "ms");
            this.mClockOffset = millis2;
            this.mNtpTime = plusMillis.plus((java.time.temporal.TemporalAmount) calculateClockOffset).toEpochMilli();
            this.mNtpTimeReference = elapsedRealtime;
            this.mRoundTripTime = millis;
            this.mServerSocketAddress = new java.net.InetSocketAddress(inetAddress, i);
            datagramSocket.close();
            android.net.TrafficStats.setThreadStatsTag(i3);
            return true;
        } catch (java.lang.Exception e3) {
            e = e3;
            datagramSocket2 = datagramSocket;
            try {
                android.net.EventLogTags.writeNtpFailure(inetAddress.toString(), e.toString());
                android.util.Log.d(TAG, "request time failed: " + e);
                if (datagramSocket2 != null) {
                    datagramSocket2.close();
                }
                android.net.TrafficStats.setThreadStatsTag(i3);
                return false;
            } catch (java.lang.Throwable th3) {
                th = th3;
                if (datagramSocket2 != null) {
                    datagramSocket2.close();
                }
                android.net.TrafficStats.setThreadStatsTag(i3);
                throw th;
            }
        } catch (java.lang.Throwable th4) {
            th = th4;
            datagramSocket2 = datagramSocket;
            if (datagramSocket2 != null) {
            }
            android.net.TrafficStats.setThreadStatsTag(i3);
            throw th;
        }
    }

    public static java.time.Duration calculateClockOffset(android.net.sntp.Timestamp64 timestamp64, android.net.sntp.Timestamp64 timestamp642, android.net.sntp.Timestamp64 timestamp643, android.net.sntp.Timestamp64 timestamp644) {
        return android.net.sntp.Duration64.between(timestamp64, timestamp642).plus(android.net.sntp.Duration64.between(timestamp644, timestamp643)).dividedBy(2L);
    }

    @java.lang.Deprecated
    public boolean requestTime(java.lang.String str, int i) {
        android.util.Log.w(TAG, "Shame on you for calling the hidden API requestTime()!");
        return false;
    }

    public long getClockOffset() {
        return this.mClockOffset;
    }

    public long getNtpTime() {
        return this.mNtpTime;
    }

    public long getNtpTimeReference() {
        return this.mNtpTimeReference;
    }

    public long getRoundTripTime() {
        return this.mRoundTripTime;
    }

    public java.net.InetSocketAddress getServerSocketAddress() {
        return this.mServerSocketAddress;
    }

    private static void checkValidServerReply(byte b, byte b2, int i, android.net.sntp.Timestamp64 timestamp64, android.net.sntp.Timestamp64 timestamp642, android.net.sntp.Timestamp64 timestamp643, android.net.sntp.Timestamp64 timestamp644) throws android.net.SntpClient.InvalidServerReplyException {
        if (b == 3) {
            throw new android.net.SntpClient.InvalidServerReplyException("unsynchronized server");
        }
        if (b2 != 4 && b2 != 5) {
            throw new android.net.SntpClient.InvalidServerReplyException("untrusted mode: " + ((int) b2));
        }
        if (i == 0 || i > 15) {
            throw new android.net.SntpClient.InvalidServerReplyException("untrusted stratum: " + i);
        }
        if (!timestamp643.equals(timestamp644)) {
            throw new android.net.SntpClient.InvalidServerReplyException("originateTimestamp != randomizedRequestTimestamp");
        }
        if (timestamp64.equals(android.net.sntp.Timestamp64.ZERO)) {
            throw new android.net.SntpClient.InvalidServerReplyException("zero transmitTimestamp");
        }
        if (timestamp642.equals(android.net.sntp.Timestamp64.ZERO)) {
            throw new android.net.SntpClient.InvalidServerReplyException("zero referenceTimestamp");
        }
    }

    private long readUnsigned32(byte[] bArr, int i) {
        int i2 = i + 1 + 1;
        return ((bArr[i2 + 1] & 255) | ((bArr[i] & 255) << 24) | ((bArr[r0] & 255) << 16) | ((bArr[i2] & 255) << 8)) & 4294967295L;
    }

    private android.net.sntp.Timestamp64 readTimeStamp(byte[] bArr, int i) {
        return android.net.sntp.Timestamp64.fromComponents(readUnsigned32(bArr, i), (int) readUnsigned32(bArr, i + 4));
    }

    private void writeTimeStamp(byte[] bArr, int i, android.net.sntp.Timestamp64 timestamp64) {
        long eraSeconds = timestamp64.getEraSeconds();
        int i2 = i + 1;
        bArr[i] = (byte) (eraSeconds >>> 24);
        int i3 = i2 + 1;
        bArr[i2] = (byte) (eraSeconds >>> 16);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (eraSeconds >>> 8);
        int i5 = i4 + 1;
        bArr[i4] = (byte) eraSeconds;
        int fractionBits = timestamp64.getFractionBits();
        int i6 = i5 + 1;
        bArr[i5] = (byte) (fractionBits >>> 24);
        int i7 = i6 + 1;
        bArr[i6] = (byte) (fractionBits >>> 16);
        bArr[i7] = (byte) (fractionBits >>> 8);
        bArr[i7 + 1] = (byte) fractionBits;
    }

    private static java.util.Random defaultRandom() {
        try {
            return java.security.SecureRandom.getInstanceStrong();
        } catch (java.security.NoSuchAlgorithmException e) {
            android.util.Slog.wtf(TAG, "Unable to access SecureRandom", e);
            return new java.util.Random(java.lang.System.currentTimeMillis());
        }
    }
}
