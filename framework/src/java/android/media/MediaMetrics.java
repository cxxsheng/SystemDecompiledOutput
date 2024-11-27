package android.media;

/* loaded from: classes2.dex */
public class MediaMetrics {
    private static final java.nio.charset.Charset MEDIAMETRICS_CHARSET = java.nio.charset.StandardCharsets.UTF_8;
    public static final java.lang.String SEPARATOR = ".";
    public static final java.lang.String TAG = "MediaMetrics";
    private static final int TYPE_CSTRING = 4;
    private static final int TYPE_DOUBLE = 3;
    private static final int TYPE_INT32 = 1;
    private static final int TYPE_INT64 = 2;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_RATE = 5;

    public interface Key<T> {
        java.lang.String getName();

        java.lang.Class<T> getValueClass();
    }

    public static class Name {
        public static final java.lang.String AUDIO = "audio";
        public static final java.lang.String AUDIO_BLUETOOTH = "audio.bluetooth";
        public static final java.lang.String AUDIO_DEVICE = "audio.device";
        public static final java.lang.String AUDIO_FOCUS = "audio.focus";
        public static final java.lang.String AUDIO_FORCE_USE = "audio.forceUse";
        public static final java.lang.String AUDIO_MIC = "audio.mic";
        public static final java.lang.String AUDIO_MIDI = "audio.midi";
        public static final java.lang.String AUDIO_MODE = "audio.mode";
        public static final java.lang.String AUDIO_SERVICE = "audio.service";
        public static final java.lang.String AUDIO_VOLUME = "audio.volume";
        public static final java.lang.String AUDIO_VOLUME_EVENT = "audio.volume.event";
        public static final java.lang.String METRICS_MANAGER = "metrics.manager";
    }

    public static class Property {
        public static final android.media.MediaMetrics.Key<java.lang.String> ADDRESS = android.media.MediaMetrics.createKey("address", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> ATTRIBUTES = android.media.MediaMetrics.createKey("attributes", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> CALLING_PACKAGE = android.media.MediaMetrics.createKey("callingPackage", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> CLIENT_NAME = android.media.MediaMetrics.createKey("clientName", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> CLOSED_COUNT = android.media.MediaMetrics.createKey("closedCount", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> DELAY_MS = android.media.MediaMetrics.createKey(com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_DELAY_MS, java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> DEVICE = android.media.MediaMetrics.createKey(android.hardware.usb.UsbManager.EXTRA_DEVICE, java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> DEVICE_DISCONNECTED = android.media.MediaMetrics.createKey("deviceDisconnected", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> DEVICE_ID = android.media.MediaMetrics.createKey("deviceId", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> DIRECTION = android.media.MediaMetrics.createKey("direction", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Long> DURATION_NS = android.media.MediaMetrics.createKey("durationNs", java.lang.Long.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> EARLY_RETURN = android.media.MediaMetrics.createKey("earlyReturn", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> ENCODING = android.media.MediaMetrics.createKey("encoding", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> EVENT = android.media.MediaMetrics.createKey("event#", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> ENABLED = android.media.MediaMetrics.createKey("enabled", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> EXTERNAL = android.media.MediaMetrics.createKey("external", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> FLAGS = android.media.MediaMetrics.createKey("flags", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> FOCUS_CHANGE_HINT = android.media.MediaMetrics.createKey("focusChangeHint", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> FORCE_USE_DUE_TO = android.media.MediaMetrics.createKey("forceUseDueTo", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> FORCE_USE_MODE = android.media.MediaMetrics.createKey("forceUseMode", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Double> GAIN_DB = android.media.MediaMetrics.createKey("gainDb", java.lang.Double.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> GROUP = android.media.MediaMetrics.createKey("group", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> HAS_HEAD_TRACKER = android.media.MediaMetrics.createKey("hasHeadTracker", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> HARDWARE_TYPE = android.media.MediaMetrics.createKey("hardwareType", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> HEAD_TRACKER_ENABLED = android.media.MediaMetrics.createKey("headTrackerEnabled", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> INDEX = android.media.MediaMetrics.createKey(android.graphics.FontListParser.ATTR_INDEX, java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> OLD_INDEX = android.media.MediaMetrics.createKey("oldIndex", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> INPUT_PORT_COUNT = android.media.MediaMetrics.createKey("inputPortCount", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> IS_SHARED = android.media.MediaMetrics.createKey("isShared", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> LOG_SESSION_ID = android.media.MediaMetrics.createKey("logSessionId", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> MAX_INDEX = android.media.MediaMetrics.createKey("maxIndex", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> MIN_INDEX = android.media.MediaMetrics.createKey("minIndex", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> MODE = android.media.MediaMetrics.createKey("mode", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> MUTE = android.media.MediaMetrics.createKey(android.media.MediaMetrics.Value.MUTE, java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> NAME = android.media.MediaMetrics.createKey("name", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> OBSERVERS = android.media.MediaMetrics.createKey("observers", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> OPENED_COUNT = android.media.MediaMetrics.createKey("openedCount", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> OUTPUT_PORT_COUNT = android.media.MediaMetrics.createKey("outputPortCount", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> REQUEST = android.media.MediaMetrics.createKey("request", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> REQUESTED_MODE = android.media.MediaMetrics.createKey("requestedMode", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> SCO_AUDIO_MODE = android.media.MediaMetrics.createKey("scoAudioMode", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> SDK = android.media.MediaMetrics.createKey("sdk", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> STATE = android.media.MediaMetrics.createKey("state", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> STATUS = android.media.MediaMetrics.createKey("status", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> STREAM_TYPE = android.media.MediaMetrics.createKey(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_STREAM, java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> SUPPORTS_MIDI_UMP = android.media.MediaMetrics.createKey("supportsMidiUmp", java.lang.String.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> TOTAL_INPUT_BYTES = android.media.MediaMetrics.createKey("totalInputBytes", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.Integer> TOTAL_OUTPUT_BYTES = android.media.MediaMetrics.createKey("totalOutputBytes", java.lang.Integer.class);
        public static final android.media.MediaMetrics.Key<java.lang.String> USING_ALSA = android.media.MediaMetrics.createKey("usingAlsa", java.lang.String.class);
    }

    public static class Value {
        public static final java.lang.String CONNECT = "connect";
        public static final java.lang.String CONNECTED = "connected";
        public static final java.lang.String DISCONNECT = "disconnect";
        public static final java.lang.String DISCONNECTED = "disconnected";
        public static final java.lang.String DOWN = "down";
        public static final java.lang.String MUTE = "mute";
        public static final java.lang.String NO = "no";
        public static final java.lang.String OFF = "off";
        public static final java.lang.String ON = "on";
        public static final java.lang.String UNMUTE = "unmute";
        public static final java.lang.String UP = "up";
        public static final java.lang.String YES = "yes";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native int native_submit_bytebuffer(java.nio.ByteBuffer byteBuffer, int i);

    public static <T> android.media.MediaMetrics.Key<T> createKey(final java.lang.String str, final java.lang.Class<T> cls) {
        return new android.media.MediaMetrics.Key<T>() { // from class: android.media.MediaMetrics.1
            private final java.lang.String mName;
            private final java.lang.Class<T> mType;

            {
                this.mName = str;
                this.mType = cls;
            }

            @Override // android.media.MediaMetrics.Key
            public java.lang.String getName() {
                return this.mName;
            }

            @Override // android.media.MediaMetrics.Key
            public java.lang.Class<T> getValueClass() {
                return this.mType;
            }

            public boolean equals(java.lang.Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof android.media.MediaMetrics.Key)) {
                    return false;
                }
                android.media.MediaMetrics.Key key = (android.media.MediaMetrics.Key) obj;
                return this.mName.equals(key.getName()) && this.mType.equals(key.getValueClass());
            }

            public int hashCode() {
                return java.util.Objects.hash(this.mName, this.mType);
            }
        };
    }

    public static class Item {
        public static final java.lang.String BUNDLE_HEADER_SIZE = "_headerSize";
        public static final java.lang.String BUNDLE_KEY = "_key";
        public static final java.lang.String BUNDLE_KEY_SIZE = "_keySize";
        public static final java.lang.String BUNDLE_PID = "_pid";
        public static final java.lang.String BUNDLE_PROPERTY_COUNT = "_propertyCount";
        public static final java.lang.String BUNDLE_TIMESTAMP = "_timestamp";
        public static final java.lang.String BUNDLE_TOTAL_SIZE = "_totalSize";
        public static final java.lang.String BUNDLE_UID = "_uid";
        public static final java.lang.String BUNDLE_VERSION = "_version";
        private static final int FORMAT_VERSION = 0;
        private static final int HEADER_SIZE_OFFSET = 4;
        private static final int MINIMUM_PAYLOAD_SIZE = 4;
        private static final int TOTAL_SIZE_OFFSET = 0;
        private java.nio.ByteBuffer mBuffer;
        private final int mHeaderSize;
        private final java.lang.String mKey;
        private final int mPidOffset;
        private int mPropertyCount;
        private final int mPropertyCountOffset;
        private final int mPropertyStartOffset;
        private final int mTimeNsOffset;
        private final int mUidOffset;

        public Item(java.lang.String str) {
            this(str, -1, -1, 0L, 2048);
        }

        public Item(java.lang.String str, int i, int i2, long j, int i3) {
            this.mPropertyCount = 0;
            byte[] bytes = str.getBytes(android.media.MediaMetrics.MEDIAMETRICS_CHARSET);
            int length = bytes.length;
            if (length > 65534) {
                throw new java.lang.IllegalArgumentException("Key length too large");
            }
            this.mHeaderSize = length + 12 + 1 + 4 + 4 + 8;
            this.mPidOffset = this.mHeaderSize - 16;
            this.mUidOffset = this.mHeaderSize - 12;
            this.mTimeNsOffset = this.mHeaderSize - 8;
            this.mPropertyCountOffset = this.mHeaderSize;
            this.mPropertyStartOffset = this.mHeaderSize + 4;
            this.mKey = str;
            this.mBuffer = java.nio.ByteBuffer.allocateDirect(java.lang.Math.max(i3, this.mHeaderSize + 4));
            this.mBuffer.order(java.nio.ByteOrder.nativeOrder()).putInt(0).putInt(this.mHeaderSize).putChar((char) 0).putChar((char) (length + 1)).put(bytes).put((byte) 0).putInt(i).putInt(i2).putLong(j);
            if (this.mHeaderSize != this.mBuffer.position()) {
                throw new java.lang.IllegalStateException("Mismatched sizing");
            }
            this.mBuffer.putInt(0);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public <T> android.media.MediaMetrics.Item set(android.media.MediaMetrics.Key<T> key, T t) {
            if (t instanceof java.lang.Integer) {
                putInt(key.getName(), ((java.lang.Integer) t).intValue());
            } else if (t instanceof java.lang.Long) {
                putLong(key.getName(), ((java.lang.Long) t).longValue());
            } else if (t instanceof java.lang.Double) {
                putDouble(key.getName(), ((java.lang.Double) t).doubleValue());
            } else if (t instanceof java.lang.String) {
                putString(key.getName(), (java.lang.String) t);
            }
            return this;
        }

        public android.media.MediaMetrics.Item putInt(java.lang.String str, int i) {
            byte[] bytes = str.getBytes(android.media.MediaMetrics.MEDIAMETRICS_CHARSET);
            char reserveProperty = (char) reserveProperty(bytes, 4);
            int position = this.mBuffer.position() + reserveProperty;
            this.mBuffer.putChar(reserveProperty).put((byte) 1).put(bytes).put((byte) 0).putInt(i);
            this.mPropertyCount++;
            if (this.mBuffer.position() != position) {
                throw new java.lang.IllegalStateException("Final position " + this.mBuffer.position() + " != estimatedFinalPosition " + position);
            }
            return this;
        }

        public android.media.MediaMetrics.Item putLong(java.lang.String str, long j) {
            byte[] bytes = str.getBytes(android.media.MediaMetrics.MEDIAMETRICS_CHARSET);
            char reserveProperty = (char) reserveProperty(bytes, 8);
            int position = this.mBuffer.position() + reserveProperty;
            this.mBuffer.putChar(reserveProperty).put((byte) 2).put(bytes).put((byte) 0).putLong(j);
            this.mPropertyCount++;
            if (this.mBuffer.position() != position) {
                throw new java.lang.IllegalStateException("Final position " + this.mBuffer.position() + " != estimatedFinalPosition " + position);
            }
            return this;
        }

        public android.media.MediaMetrics.Item putDouble(java.lang.String str, double d) {
            byte[] bytes = str.getBytes(android.media.MediaMetrics.MEDIAMETRICS_CHARSET);
            char reserveProperty = (char) reserveProperty(bytes, 8);
            int position = this.mBuffer.position() + reserveProperty;
            this.mBuffer.putChar(reserveProperty).put((byte) 3).put(bytes).put((byte) 0).putDouble(d);
            this.mPropertyCount++;
            if (this.mBuffer.position() != position) {
                throw new java.lang.IllegalStateException("Final position " + this.mBuffer.position() + " != estimatedFinalPosition " + position);
            }
            return this;
        }

        public android.media.MediaMetrics.Item putString(java.lang.String str, java.lang.String str2) {
            byte[] bytes = str.getBytes(android.media.MediaMetrics.MEDIAMETRICS_CHARSET);
            byte[] bytes2 = str2.getBytes(android.media.MediaMetrics.MEDIAMETRICS_CHARSET);
            char reserveProperty = (char) reserveProperty(bytes, bytes2.length + 1);
            int position = this.mBuffer.position() + reserveProperty;
            this.mBuffer.putChar(reserveProperty).put((byte) 4).put(bytes).put((byte) 0).put(bytes2).put((byte) 0);
            this.mPropertyCount++;
            if (this.mBuffer.position() != position) {
                throw new java.lang.IllegalStateException("Final position " + this.mBuffer.position() + " != estimatedFinalPosition " + position);
            }
            return this;
        }

        public android.media.MediaMetrics.Item setPid(int i) {
            this.mBuffer.putInt(this.mPidOffset, i);
            return this;
        }

        public android.media.MediaMetrics.Item setUid(int i) {
            this.mBuffer.putInt(this.mUidOffset, i);
            return this;
        }

        public android.media.MediaMetrics.Item setTimestamp(long j) {
            this.mBuffer.putLong(this.mTimeNsOffset, j);
            return this;
        }

        public android.media.MediaMetrics.Item clear() {
            this.mBuffer.position(this.mPropertyStartOffset);
            this.mBuffer.limit(this.mBuffer.capacity());
            this.mBuffer.putLong(this.mTimeNsOffset, 0L);
            this.mPropertyCount = 0;
            return this;
        }

        public boolean record() {
            updateHeader();
            return android.media.MediaMetrics.native_submit_bytebuffer(this.mBuffer, this.mBuffer.limit()) >= 0;
        }

        public android.os.Bundle toBundle() {
            updateHeader();
            java.nio.ByteBuffer duplicate = this.mBuffer.duplicate();
            duplicate.order(java.nio.ByteOrder.nativeOrder()).flip();
            return toBundle(duplicate);
        }

        public static android.os.Bundle toBundle(java.nio.ByteBuffer byteBuffer) {
            android.os.Bundle bundle = new android.os.Bundle();
            int i = byteBuffer.getInt();
            int i2 = byteBuffer.getInt();
            char c = byteBuffer.getChar();
            char c2 = byteBuffer.getChar();
            if (i < 0 || i2 < 0) {
                throw new java.lang.IllegalArgumentException("Item size cannot be > 2147483647");
            }
            if (c2 > 0) {
                java.lang.String stringFromBuffer = getStringFromBuffer(byteBuffer, c2);
                int i3 = byteBuffer.getInt();
                int i4 = byteBuffer.getInt();
                long j = byteBuffer.getLong();
                int position = byteBuffer.position();
                if (c == 0) {
                    if (position != i2) {
                        throw new java.lang.IllegalArgumentException("Item key:" + stringFromBuffer + " headerRead:" + position + " != headerSize:" + i2);
                    }
                } else {
                    if (position > i2) {
                        throw new java.lang.IllegalArgumentException("Item key:" + stringFromBuffer + " headerRead:" + position + " > headerSize:" + i2);
                    }
                    if (position < i2) {
                        byteBuffer.position(i2);
                    }
                }
                int i5 = byteBuffer.getInt();
                if (i5 < 0) {
                    throw new java.lang.IllegalArgumentException("Cannot have more than 2147483647 properties");
                }
                bundle.putInt(BUNDLE_TOTAL_SIZE, i);
                bundle.putInt(BUNDLE_HEADER_SIZE, i2);
                bundle.putChar(BUNDLE_VERSION, c);
                bundle.putChar(BUNDLE_KEY_SIZE, c2);
                bundle.putString(BUNDLE_KEY, stringFromBuffer);
                bundle.putInt(BUNDLE_PID, i3);
                bundle.putInt(BUNDLE_UID, i4);
                bundle.putLong(BUNDLE_TIMESTAMP, j);
                bundle.putInt(BUNDLE_PROPERTY_COUNT, i5);
                for (int i6 = 0; i6 < i5; i6++) {
                    int position2 = byteBuffer.position();
                    char c3 = byteBuffer.getChar();
                    byte b = byteBuffer.get();
                    java.lang.String stringFromBuffer2 = getStringFromBuffer(byteBuffer);
                    switch (b) {
                        case 0:
                            break;
                        case 1:
                            bundle.putInt(stringFromBuffer2, byteBuffer.getInt());
                            break;
                        case 2:
                            bundle.putLong(stringFromBuffer2, byteBuffer.getLong());
                            break;
                        case 3:
                            bundle.putDouble(stringFromBuffer2, byteBuffer.getDouble());
                            break;
                        case 4:
                            bundle.putString(stringFromBuffer2, getStringFromBuffer(byteBuffer));
                            break;
                        case 5:
                            byteBuffer.getLong();
                            byteBuffer.getLong();
                            break;
                        default:
                            if (c == 0) {
                                throw new java.lang.IllegalArgumentException("Property " + stringFromBuffer2 + " has unsupported type " + ((int) b));
                            }
                            byteBuffer.position(position2 + c3);
                            break;
                    }
                    int position3 = byteBuffer.position() - position2;
                    if (position3 != c3) {
                        throw new java.lang.IllegalArgumentException("propSize:" + c3 + " != deltaPosition:" + position3);
                    }
                }
                int position4 = byteBuffer.position();
                if (position4 != i) {
                    throw new java.lang.IllegalArgumentException("totalSize:" + i + " != finalPosition:" + position4);
                }
                return bundle;
            }
            throw new java.lang.IllegalArgumentException("Illegal null key");
        }

        private int reserveProperty(byte[] bArr, int i) {
            int length = bArr.length;
            if (length > 65535) {
                throw new java.lang.IllegalStateException("property key too long " + new java.lang.String(bArr, android.media.MediaMetrics.MEDIAMETRICS_CHARSET));
            }
            if (i > 65535) {
                throw new java.lang.IllegalStateException("payload too large " + i);
            }
            int i2 = length + 3 + 1 + i;
            if (i2 > 65535) {
                throw new java.lang.IllegalStateException("Item property " + new java.lang.String(bArr, android.media.MediaMetrics.MEDIAMETRICS_CHARSET) + " is too large to send");
            }
            if (this.mBuffer.remaining() < i2) {
                int position = this.mBuffer.position() + i2;
                if (position > 1073741823) {
                    throw new java.lang.IllegalStateException("Item memory requirements too large: " + position);
                }
                java.nio.ByteBuffer allocateDirect = java.nio.ByteBuffer.allocateDirect(position << 1);
                allocateDirect.order(java.nio.ByteOrder.nativeOrder());
                this.mBuffer.flip();
                allocateDirect.put(this.mBuffer);
                this.mBuffer = allocateDirect;
            }
            return i2;
        }

        private static java.lang.String getStringFromBuffer(java.nio.ByteBuffer byteBuffer) {
            return getStringFromBuffer(byteBuffer, Integer.MAX_VALUE);
        }

        private static java.lang.String getStringFromBuffer(java.nio.ByteBuffer byteBuffer, int i) {
            int i2;
            int position = byteBuffer.position();
            int limit = byteBuffer.limit();
            if (i < Integer.MAX_VALUE - position && (i2 = position + i) < limit) {
                limit = i2;
            }
            while (position < limit) {
                if (byteBuffer.get(position) != 0) {
                    position++;
                } else {
                    int i3 = position + 1;
                    if (i != Integer.MAX_VALUE && i3 - byteBuffer.position() != i) {
                        throw new java.lang.IllegalArgumentException("chars consumed at " + position + ": " + (i3 - byteBuffer.position()) + " != size: " + i);
                    }
                    if (byteBuffer.hasArray()) {
                        java.lang.String str = new java.lang.String(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), position - byteBuffer.position(), android.media.MediaMetrics.MEDIAMETRICS_CHARSET);
                        byteBuffer.position(i3);
                        return str;
                    }
                    byte[] bArr = new byte[position - byteBuffer.position()];
                    byteBuffer.get(bArr);
                    java.lang.String str2 = new java.lang.String(bArr, android.media.MediaMetrics.MEDIAMETRICS_CHARSET);
                    byteBuffer.get();
                    return str2;
                }
            }
            throw new java.lang.IllegalArgumentException("No zero termination found in string position: " + byteBuffer.position() + " end: " + position);
        }

        private void updateHeader() {
            this.mBuffer.putInt(0, this.mBuffer.position()).putInt(this.mPropertyCountOffset, (char) this.mPropertyCount);
        }
    }
}
