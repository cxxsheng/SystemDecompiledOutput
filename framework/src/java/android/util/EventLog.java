package android.util;

/* loaded from: classes3.dex */
public class EventLog {
    private static final java.lang.String COMMENT_PATTERN = "^\\s*(#.*)?$";
    private static final java.lang.String TAG = "EventLog";
    private static final java.lang.String TAGS_FILE = "/system/etc/event-log-tags";
    private static final java.lang.String TAG_PATTERN = "^\\s*(\\d+)\\s+(\\w+)\\s*(\\(.*\\))?\\s*$";
    private static java.util.HashMap<java.lang.String, java.lang.Integer> sTagCodes = null;
    private static java.util.HashMap<java.lang.Integer, java.lang.String> sTagNames = null;

    public static native void readEvents(int[] iArr, java.util.Collection<android.util.EventLog.Event> collection) throws java.io.IOException;

    @android.annotation.SystemApi
    public static native void readEventsOnWrapping(int[] iArr, long j, java.util.Collection<android.util.EventLog.Event> collection) throws java.io.IOException;

    public static native int writeEvent(int i, float f);

    public static native int writeEvent(int i, int i2);

    public static native int writeEvent(int i, long j);

    public static native int writeEvent(int i, java.lang.String str);

    public static native int writeEvent(int i, java.lang.Object... objArr);

    public static final class Event {
        private static final byte FLOAT_TYPE = 4;
        private static final int HEADER_SIZE_OFFSET = 2;
        private static final byte INT_TYPE = 0;
        private static final int LENGTH_OFFSET = 0;
        private static final byte LIST_TYPE = 3;
        private static final byte LONG_TYPE = 1;
        private static final int NANOSECONDS_OFFSET = 16;
        private static final int PROCESS_OFFSET = 4;
        private static final int SECONDS_OFFSET = 12;
        private static final byte STRING_TYPE = 2;
        private static final int TAG_LENGTH = 4;
        private static final int THREAD_OFFSET = 8;
        private static final int UID_OFFSET = 24;
        private static final int V1_PAYLOAD_START = 20;
        private final java.nio.ByteBuffer mBuffer;
        private java.lang.Exception mLastWtf;

        Event(byte[] bArr) {
            this.mBuffer = java.nio.ByteBuffer.wrap(bArr);
            this.mBuffer.order(java.nio.ByteOrder.nativeOrder());
        }

        public int getProcessId() {
            return this.mBuffer.getInt(4);
        }

        @android.annotation.SystemApi
        public int getUid() {
            try {
                return this.mBuffer.getInt(24);
            } catch (java.lang.IndexOutOfBoundsException e) {
                return -1;
            }
        }

        public int getThreadId() {
            return this.mBuffer.getInt(8);
        }

        public long getTimeNanos() {
            return (this.mBuffer.getInt(12) * 1000000000) + this.mBuffer.getInt(16);
        }

        public int getTag() {
            return this.mBuffer.getInt(getHeaderSize());
        }

        private int getHeaderSize() {
            short s = this.mBuffer.getShort(2);
            if (s != 0) {
                return s;
            }
            return 20;
        }

        public synchronized java.lang.Object getData() {
            try {
                int headerSize = getHeaderSize();
                this.mBuffer.limit(this.mBuffer.getShort(0) + headerSize);
                int i = headerSize + 4;
                if (i >= this.mBuffer.limit()) {
                    return null;
                }
                this.mBuffer.position(i);
                return decodeObject();
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.wtf(android.util.EventLog.TAG, "Illegal entry payload: tag=" + getTag(), e);
                this.mLastWtf = e;
                return null;
            } catch (java.nio.BufferUnderflowException e2) {
                android.util.Log.wtf(android.util.EventLog.TAG, "Truncated entry payload: tag=" + getTag(), e2);
                this.mLastWtf = e2;
                return null;
            }
        }

        public android.util.EventLog.Event withNewData(java.lang.Object obj) {
            byte[] encodeObject = encodeObject(obj);
            if (encodeObject.length > 65531) {
                throw new java.lang.IllegalArgumentException("Payload too long");
            }
            int headerSize = getHeaderSize() + 4;
            byte[] bArr = new byte[encodeObject.length + headerSize];
            java.lang.System.arraycopy(this.mBuffer.array(), 0, bArr, 0, headerSize);
            java.lang.System.arraycopy(encodeObject, 0, bArr, headerSize, encodeObject.length);
            android.util.EventLog.Event event = new android.util.EventLog.Event(bArr);
            event.mBuffer.putShort(0, (short) (encodeObject.length + 4));
            return event;
        }

        private java.lang.Object decodeObject() {
            byte b = this.mBuffer.get();
            switch (b) {
                case 0:
                    return java.lang.Integer.valueOf(this.mBuffer.getInt());
                case 1:
                    return java.lang.Long.valueOf(this.mBuffer.getLong());
                case 2:
                    try {
                        int i = this.mBuffer.getInt();
                        int position = this.mBuffer.position();
                        this.mBuffer.position(position + i);
                        return new java.lang.String(this.mBuffer.array(), position, i, android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
                    } catch (java.io.UnsupportedEncodingException e) {
                        android.util.Log.wtf(android.util.EventLog.TAG, "UTF-8 is not supported", e);
                        this.mLastWtf = e;
                        return null;
                    }
                case 3:
                    int i2 = this.mBuffer.get();
                    if (i2 < 0) {
                        i2 += 256;
                    }
                    java.lang.Object[] objArr = new java.lang.Object[i2];
                    for (int i3 = 0; i3 < i2; i3++) {
                        objArr[i3] = decodeObject();
                    }
                    return objArr;
                case 4:
                    return java.lang.Float.valueOf(this.mBuffer.getFloat());
                default:
                    throw new java.lang.IllegalArgumentException("Unknown entry type: " + ((int) b));
            }
        }

        private static byte[] encodeObject(java.lang.Object obj) {
            byte[] bArr;
            if (obj == null) {
                return new byte[0];
            }
            if (obj instanceof java.lang.Integer) {
                return java.nio.ByteBuffer.allocate(5).order(java.nio.ByteOrder.nativeOrder()).put((byte) 0).putInt(((java.lang.Integer) obj).intValue()).array();
            }
            if (obj instanceof java.lang.Long) {
                return java.nio.ByteBuffer.allocate(9).order(java.nio.ByteOrder.nativeOrder()).put((byte) 1).putLong(((java.lang.Long) obj).longValue()).array();
            }
            if (obj instanceof java.lang.Float) {
                return java.nio.ByteBuffer.allocate(5).order(java.nio.ByteOrder.nativeOrder()).put((byte) 4).putFloat(((java.lang.Float) obj).floatValue()).array();
            }
            if (obj instanceof java.lang.String) {
                try {
                    bArr = ((java.lang.String) obj).getBytes(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
                } catch (java.io.UnsupportedEncodingException e) {
                    bArr = new byte[0];
                }
                return java.nio.ByteBuffer.allocate(bArr.length + 5).order(java.nio.ByteOrder.nativeOrder()).put((byte) 2).putInt(bArr.length).put(bArr).array();
            }
            if (obj instanceof java.lang.Object[]) {
                java.lang.Object[] objArr = (java.lang.Object[]) obj;
                if (objArr.length > 255) {
                    throw new java.lang.IllegalArgumentException("Object array too long");
                }
                byte[][] bArr2 = new byte[objArr.length][];
                int i = 0;
                for (int i2 = 0; i2 < objArr.length; i2++) {
                    bArr2[i2] = encodeObject(objArr[i2]);
                    i += bArr2[i2].length;
                }
                java.nio.ByteBuffer put = java.nio.ByteBuffer.allocate(i + 2).order(java.nio.ByteOrder.nativeOrder()).put((byte) 3).put((byte) objArr.length);
                for (int i3 = 0; i3 < objArr.length; i3++) {
                    put.put(bArr2[i3]);
                }
                return put.array();
            }
            throw new java.lang.IllegalArgumentException("Unknown object type " + obj);
        }

        public static android.util.EventLog.Event fromBytes(byte[] bArr) {
            return new android.util.EventLog.Event(bArr);
        }

        public byte[] getBytes() {
            byte[] array = this.mBuffer.array();
            return java.util.Arrays.copyOf(array, array.length);
        }

        public java.lang.Exception getLastError() {
            return this.mLastWtf;
        }

        public void clearError() {
            this.mLastWtf = null;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return java.util.Arrays.equals(this.mBuffer.array(), ((android.util.EventLog.Event) obj).mBuffer.array());
        }

        public int hashCode() {
            return java.util.Arrays.hashCode(this.mBuffer.array());
        }
    }

    public static java.lang.String getTagName(int i) {
        readTagsFile();
        return sTagNames.get(java.lang.Integer.valueOf(i));
    }

    public static int getTagCode(java.lang.String str) {
        readTagsFile();
        java.lang.Integer num = sTagCodes.get(str);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    private static synchronized void readTagsFile() {
        java.lang.String readLine;
        synchronized (android.util.EventLog.class) {
            if (sTagCodes != null && sTagNames != null) {
                return;
            }
            sTagCodes = new java.util.HashMap<>();
            sTagNames = new java.util.HashMap<>();
            java.util.regex.Pattern compile = java.util.regex.Pattern.compile(COMMENT_PATTERN);
            java.util.regex.Pattern compile2 = java.util.regex.Pattern.compile(TAG_PATTERN);
            java.io.BufferedReader bufferedReader = null;
            bufferedReader = null;
            bufferedReader = null;
            try {
                try {
                    java.io.BufferedReader bufferedReader2 = new java.io.BufferedReader(new java.io.FileReader(TAGS_FILE), 256);
                    while (true) {
                        try {
                            readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            }
                            if (!compile.matcher(readLine).matches()) {
                                java.util.regex.Matcher matcher = compile2.matcher(readLine);
                                if (matcher.matches()) {
                                    try {
                                        registerTagLocked(java.lang.Integer.parseInt(matcher.group(1)), matcher.group(2));
                                    } catch (java.lang.NumberFormatException e) {
                                        android.util.Log.wtf(TAG, "Error in /system/etc/event-log-tags: " + readLine, e);
                                    }
                                } else {
                                    android.util.Log.wtf(TAG, "Bad entry in /system/etc/event-log-tags: " + readLine);
                                }
                            }
                        } catch (java.io.IOException e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            android.util.Log.wtf(TAG, "Error reading /system/etc/event-log-tags", e);
                            if (bufferedReader != null) {
                                bufferedReader.close();
                                bufferedReader = bufferedReader;
                            }
                        } catch (java.lang.Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (java.io.IOException e3) {
                                }
                            }
                            throw th;
                        }
                    }
                    bufferedReader2.close();
                    bufferedReader = readLine;
                } catch (java.io.IOException e4) {
                    e = e4;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        }
    }

    private static void registerTagLocked(int i, java.lang.String str) {
        sTagCodes.put(str, java.lang.Integer.valueOf(i));
        sTagNames.put(java.lang.Integer.valueOf(i), str);
    }

    private static synchronized void readTagsFile$ravenwood() {
        synchronized (android.util.EventLog.class) {
            sTagCodes = new java.util.HashMap<>();
            sTagNames = new java.util.HashMap<>();
            registerTagLocked(524288, "sysui_action");
            registerTagLocked(com.android.internal.logging.EventLogTags.SYSUI_COUNT, "sysui_count");
            registerTagLocked(com.android.internal.logging.EventLogTags.SYSUI_HISTOGRAM, "sysui_histogram");
        }
    }
}
