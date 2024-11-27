package com.android.internal.logging;

/* loaded from: classes4.dex */
public class AndroidHandler extends java.util.logging.Handler {
    private static final java.util.logging.Formatter THE_FORMATTER = new java.util.logging.Formatter() { // from class: com.android.internal.logging.AndroidHandler.1
        @Override // java.util.logging.Formatter
        public java.lang.String format(java.util.logging.LogRecord logRecord) {
            java.lang.Throwable thrown = logRecord.getThrown();
            if (thrown != null) {
                java.io.StringWriter stringWriter = new java.io.StringWriter();
                com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter((java.io.Writer) stringWriter, false, 256);
                stringWriter.write(logRecord.getMessage());
                stringWriter.write("\n");
                thrown.printStackTrace(fastPrintWriter);
                fastPrintWriter.flush();
                return stringWriter.toString();
            }
            return logRecord.getMessage();
        }
    };

    public AndroidHandler() {
        setFormatter(THE_FORMATTER);
    }

    @Override // java.util.logging.Handler
    public void close() {
    }

    @Override // java.util.logging.Handler
    public void flush() {
    }

    private static java.lang.String loggerNameToTag(java.lang.String str) {
        if (str == null) {
            return "null";
        }
        int length = str.length();
        if (length <= 23) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(android.media.MediaMetrics.SEPARATOR) + 1;
        if (length - lastIndexOf > 23) {
            return str.substring(str.length() - 23);
        }
        return str.substring(lastIndexOf);
    }

    @Override // java.util.logging.Handler
    public void publish(java.util.logging.LogRecord logRecord) {
        int androidLevel = getAndroidLevel(logRecord.getLevel());
        java.lang.String loggerNameToTag = loggerNameToTag(logRecord.getLoggerName());
        if (!android.util.Log.isLoggable(loggerNameToTag, androidLevel)) {
            return;
        }
        try {
            android.util.Log.println(androidLevel, loggerNameToTag, getFormatter().format(logRecord));
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e("AndroidHandler", "Error logging message.", e);
        }
    }

    public void publish(java.util.logging.Logger logger, java.lang.String str, java.util.logging.Level level, java.lang.String str2) {
        int androidLevel = getAndroidLevel(level);
        if (!android.util.Log.isLoggable(str, androidLevel)) {
            return;
        }
        try {
            android.util.Log.println(androidLevel, str, str2);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e("AndroidHandler", "Error logging message.", e);
        }
    }

    static int getAndroidLevel(java.util.logging.Level level) {
        int intValue = level.intValue();
        if (intValue >= 1000) {
            return 6;
        }
        if (intValue >= 900) {
            return 5;
        }
        if (intValue >= 800) {
            return 4;
        }
        return 3;
    }
}
