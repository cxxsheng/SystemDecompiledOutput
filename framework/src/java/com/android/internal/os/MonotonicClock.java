package com.android.internal.os;

/* loaded from: classes4.dex */
public class MonotonicClock {
    private static final java.lang.String TAG = "MonotonicClock";
    public static final long UNDEFINED = -1;
    private static final java.lang.String XML_ATTR_TIMESHIFT = "timeshift";
    private static final java.lang.String XML_TAG_MONOTONIC_TIME = "monotonic_time";
    private final com.android.internal.os.Clock mClock;
    private final android.util.AtomicFile mFile;
    private final long mTimeshift;

    public MonotonicClock(java.io.File file) {
        this(file, com.android.internal.os.Clock.SYSTEM_CLOCK.elapsedRealtime(), com.android.internal.os.Clock.SYSTEM_CLOCK);
    }

    public MonotonicClock(long j, com.android.internal.os.Clock clock) {
        this(null, j, clock);
    }

    public MonotonicClock(java.io.File file, long j, com.android.internal.os.Clock clock) {
        this.mClock = clock;
        if (file != null) {
            this.mFile = new android.util.AtomicFile(file);
            this.mTimeshift = read(j - this.mClock.elapsedRealtime());
        } else {
            this.mFile = null;
            this.mTimeshift = j - this.mClock.elapsedRealtime();
        }
    }

    public long monotonicTime() {
        return monotonicTime(this.mClock.elapsedRealtime());
    }

    public long monotonicTime(long j) {
        return this.mTimeshift + j;
    }

    private long read(long j) {
        if (!this.mFile.exists()) {
            return j;
        }
        try {
            return readXml(new java.io.ByteArrayInputStream(this.mFile.readFully()), android.util.Xml.newBinaryPullParser());
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Cannot load monotonic clock from " + this.mFile.getBaseFile(), e);
            return j;
        }
    }

    public void write() {
        if (this.mFile == null) {
            return;
        }
        java.io.FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mFile.startWrite();
            writeXml(fileOutputStream, android.util.Xml.newBinarySerializer());
            this.mFile.finishWrite(fileOutputStream);
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Cannot write monotonic clock to " + this.mFile.getBaseFile(), e);
            this.mFile.failWrite(fileOutputStream);
        }
    }

    private long readXml(java.io.InputStream inputStream, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException {
        try {
            typedXmlPullParser.setInput(inputStream, java.nio.charset.StandardCharsets.UTF_8.name());
            int eventType = typedXmlPullParser.getEventType();
            long j = 0;
            while (eventType != 1) {
                if (eventType == 2) {
                    if (typedXmlPullParser.getName().equals(XML_TAG_MONOTONIC_TIME)) {
                        j = typedXmlPullParser.getAttributeLong(null, XML_ATTR_TIMESHIFT);
                    }
                }
                eventType = typedXmlPullParser.next();
            }
            return j - this.mClock.elapsedRealtime();
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw new java.io.IOException(e);
        }
    }

    private void writeXml(java.io.OutputStream outputStream, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.setOutput(outputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        typedXmlSerializer.startDocument(null, true);
        typedXmlSerializer.startTag(null, XML_TAG_MONOTONIC_TIME);
        typedXmlSerializer.attributeLong(null, XML_ATTR_TIMESHIFT, monotonicTime());
        typedXmlSerializer.endTag(null, XML_TAG_MONOTONIC_TIME);
        typedXmlSerializer.endDocument();
    }
}
