package com.android.server.display;

/* loaded from: classes.dex */
public class AmbientBrightnessStatsTracker {

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_DAYS_TO_TRACK = 7;
    private final com.android.server.display.AmbientBrightnessStatsTracker.AmbientBrightnessStats mAmbientBrightnessStats;
    private float mCurrentAmbientBrightness;
    private int mCurrentUserId;
    private final com.android.server.display.AmbientBrightnessStatsTracker.Injector mInjector;
    private final com.android.server.display.AmbientBrightnessStatsTracker.Timer mTimer;
    private final android.os.UserManager mUserManager;
    private static final java.lang.String TAG = "AmbientBrightnessStatsTracker";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);

    @com.android.internal.annotations.VisibleForTesting
    static final float[] BUCKET_BOUNDARIES_FOR_NEW_STATS = {com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 0.1f, 0.3f, 1.0f, 3.0f, 10.0f, 30.0f, 100.0f, 300.0f, 1000.0f, 3000.0f, 10000.0f};

    @com.android.internal.annotations.VisibleForTesting
    interface Clock {
        long elapsedTimeMillis();
    }

    public AmbientBrightnessStatsTracker(android.os.UserManager userManager, @android.annotation.Nullable com.android.server.display.AmbientBrightnessStatsTracker.Injector injector) {
        this.mUserManager = userManager;
        if (injector != null) {
            this.mInjector = injector;
        } else {
            this.mInjector = new com.android.server.display.AmbientBrightnessStatsTracker.Injector();
        }
        this.mAmbientBrightnessStats = new com.android.server.display.AmbientBrightnessStatsTracker.AmbientBrightnessStats();
        this.mTimer = new com.android.server.display.AmbientBrightnessStatsTracker.Timer(new com.android.server.display.AmbientBrightnessStatsTracker.Clock() { // from class: com.android.server.display.AmbientBrightnessStatsTracker$$ExternalSyntheticLambda0
            @Override // com.android.server.display.AmbientBrightnessStatsTracker.Clock
            public final long elapsedTimeMillis() {
                long lambda$new$0;
                lambda$new$0 = com.android.server.display.AmbientBrightnessStatsTracker.this.lambda$new$0();
                return lambda$new$0;
            }
        });
        this.mCurrentAmbientBrightness = -1.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ long lambda$new$0() {
        return this.mInjector.elapsedRealtimeMillis();
    }

    public synchronized void start() {
        this.mTimer.reset();
        this.mTimer.start();
    }

    public synchronized void stop() {
        try {
            if (this.mTimer.isRunning()) {
                this.mAmbientBrightnessStats.log(this.mCurrentUserId, this.mInjector.getLocalDate(), this.mCurrentAmbientBrightness, this.mTimer.totalDurationSec());
            }
            this.mTimer.reset();
            this.mCurrentAmbientBrightness = -1.0f;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void add(int i, float f) {
        try {
            if (this.mTimer.isRunning()) {
                if (i == this.mCurrentUserId) {
                    this.mAmbientBrightnessStats.log(this.mCurrentUserId, this.mInjector.getLocalDate(), this.mCurrentAmbientBrightness, this.mTimer.totalDurationSec());
                } else {
                    if (DEBUG) {
                        android.util.Slog.v(TAG, "User switched since last sensor event.");
                    }
                    this.mCurrentUserId = i;
                }
                this.mTimer.reset();
                this.mTimer.start();
                this.mCurrentAmbientBrightness = f;
            } else if (DEBUG) {
                android.util.Slog.e(TAG, "Timer not running while trying to add brightness stats.");
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void writeStats(java.io.OutputStream outputStream) throws java.io.IOException {
        this.mAmbientBrightnessStats.writeToXML(outputStream);
    }

    public synchronized void readStats(java.io.InputStream inputStream) throws java.io.IOException {
        this.mAmbientBrightnessStats.readFromXML(inputStream);
    }

    public synchronized java.util.ArrayList<android.hardware.display.AmbientBrightnessDayStats> getUserStats(int i) {
        return this.mAmbientBrightnessStats.getUserStats(i);
    }

    public synchronized void dump(java.io.PrintWriter printWriter) {
        printWriter.println("AmbientBrightnessStats:");
        printWriter.print(this.mAmbientBrightnessStats);
    }

    class AmbientBrightnessStats {
        private static final java.lang.String ATTR_BUCKET_BOUNDARIES = "bucket-boundaries";
        private static final java.lang.String ATTR_BUCKET_STATS = "bucket-stats";
        private static final java.lang.String ATTR_LOCAL_DATE = "local-date";
        private static final java.lang.String ATTR_USER = "user";
        private static final java.lang.String TAG_AMBIENT_BRIGHTNESS_DAY_STATS = "ambient-brightness-day-stats";
        private static final java.lang.String TAG_AMBIENT_BRIGHTNESS_STATS = "ambient-brightness-stats";
        private java.util.Map<java.lang.Integer, java.util.Deque<android.hardware.display.AmbientBrightnessDayStats>> mStats = new java.util.HashMap();

        public AmbientBrightnessStats() {
        }

        public void log(int i, java.time.LocalDate localDate, float f, float f2) {
            getOrCreateDayStats(getOrCreateUserStats(this.mStats, i), localDate).log(f, f2);
        }

        public java.util.ArrayList<android.hardware.display.AmbientBrightnessDayStats> getUserStats(int i) {
            if (this.mStats.containsKey(java.lang.Integer.valueOf(i))) {
                return new java.util.ArrayList<>(this.mStats.get(java.lang.Integer.valueOf(i)));
            }
            return null;
        }

        public void writeToXML(java.io.OutputStream outputStream) throws java.io.IOException {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(outputStream);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            java.time.LocalDate minusDays = com.android.server.display.AmbientBrightnessStatsTracker.this.mInjector.getLocalDate().minusDays(7L);
            resolveSerializer.startTag((java.lang.String) null, TAG_AMBIENT_BRIGHTNESS_STATS);
            for (java.util.Map.Entry<java.lang.Integer, java.util.Deque<android.hardware.display.AmbientBrightnessDayStats>> entry : this.mStats.entrySet()) {
                for (android.hardware.display.AmbientBrightnessDayStats ambientBrightnessDayStats : entry.getValue()) {
                    int userSerialNumber = com.android.server.display.AmbientBrightnessStatsTracker.this.mInjector.getUserSerialNumber(com.android.server.display.AmbientBrightnessStatsTracker.this.mUserManager, entry.getKey().intValue());
                    if (userSerialNumber != -1 && ambientBrightnessDayStats.getLocalDate().isAfter(minusDays)) {
                        resolveSerializer.startTag((java.lang.String) null, TAG_AMBIENT_BRIGHTNESS_DAY_STATS);
                        resolveSerializer.attributeInt((java.lang.String) null, ATTR_USER, userSerialNumber);
                        resolveSerializer.attribute((java.lang.String) null, ATTR_LOCAL_DATE, ambientBrightnessDayStats.getLocalDate().toString());
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                        for (int i = 0; i < ambientBrightnessDayStats.getBucketBoundaries().length; i++) {
                            if (i > 0) {
                                sb.append(",");
                                sb2.append(",");
                            }
                            sb.append(ambientBrightnessDayStats.getBucketBoundaries()[i]);
                            sb2.append(ambientBrightnessDayStats.getStats()[i]);
                        }
                        resolveSerializer.attribute((java.lang.String) null, ATTR_BUCKET_BOUNDARIES, sb.toString());
                        resolveSerializer.attribute((java.lang.String) null, ATTR_BUCKET_STATS, sb2.toString());
                        resolveSerializer.endTag((java.lang.String) null, TAG_AMBIENT_BRIGHTNESS_DAY_STATS);
                    }
                }
            }
            resolveSerializer.endTag((java.lang.String) null, TAG_AMBIENT_BRIGHTNESS_STATS);
            resolveSerializer.endDocument();
            outputStream.flush();
        }

        /* JADX WARN: Code restructure failed: missing block: B:27:0x004f, code lost:
        
            if (r5 != 4) goto L56;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x005c, code lost:
        
            if (com.android.server.display.AmbientBrightnessStatsTracker.AmbientBrightnessStats.TAG_AMBIENT_BRIGHTNESS_DAY_STATS.equals(r14.getName()) == false) goto L63;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x005e, code lost:
        
            r5 = r14.getAttributeInt((java.lang.String) null, com.android.server.display.AmbientBrightnessStatsTracker.AmbientBrightnessStats.ATTR_USER);
            r7 = java.time.LocalDate.parse(r14.getAttributeValue((java.lang.String) null, com.android.server.display.AmbientBrightnessStatsTracker.AmbientBrightnessStats.ATTR_LOCAL_DATE));
            r8 = r14.getAttributeValue((java.lang.String) null, com.android.server.display.AmbientBrightnessStatsTracker.AmbientBrightnessStats.ATTR_BUCKET_BOUNDARIES).split(",");
            r6 = r14.getAttributeValue((java.lang.String) null, com.android.server.display.AmbientBrightnessStatsTracker.AmbientBrightnessStats.ATTR_BUCKET_STATS).split(",");
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0087, code lost:
        
            if (r8.length != r6.length) goto L59;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x008a, code lost:
        
            if (r8.length < 1) goto L60;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x008c, code lost:
        
            r9 = new float[r8.length];
            r10 = new float[r6.length];
            r11 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0094, code lost:
        
            if (r11 >= r8.length) goto L66;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0096, code lost:
        
            r9[r11] = java.lang.Float.parseFloat(r8[r11]);
            r10[r11] = java.lang.Float.parseFloat(r6[r11]);
            r11 = r11 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00a9, code lost:
        
            r5 = r13.this$0.mInjector.getUserId(r13.this$0.mUserManager, r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x00ba, code lost:
        
            if (r5 == (-1)) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x00c0, code lost:
        
            if (r7.isAfter(r2) == false) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x00c2, code lost:
        
            getOrCreateUserStats(r1, r5).offer(new android.hardware.display.AmbientBrightnessDayStats(r7, r9, r10));
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x00d7, code lost:
        
            throw new java.io.IOException("Invalid brightness stats string.");
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x0036, code lost:
        
            continue;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void readFromXML(java.io.InputStream inputStream) throws java.io.IOException {
            int next;
            try {
                java.util.HashMap hashMap = new java.util.HashMap();
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
                do {
                    next = resolvePullParser.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                java.lang.String name = resolvePullParser.getName();
                if (!TAG_AMBIENT_BRIGHTNESS_STATS.equals(name)) {
                    throw new org.xmlpull.v1.XmlPullParserException("Ambient brightness stats not found in tracker file " + name);
                }
                java.time.LocalDate minusDays = com.android.server.display.AmbientBrightnessStatsTracker.this.mInjector.getLocalDate().minusDays(7L);
                int depth = resolvePullParser.getDepth();
                while (true) {
                    int next2 = resolvePullParser.next();
                    if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                        break;
                    }
                }
                this.mStats = hashMap;
            } catch (java.io.IOException | java.lang.NullPointerException | java.lang.NumberFormatException | java.time.format.DateTimeParseException | org.xmlpull.v1.XmlPullParserException e) {
                throw new java.io.IOException("Failed to parse brightness stats file.", e);
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (java.util.Map.Entry<java.lang.Integer, java.util.Deque<android.hardware.display.AmbientBrightnessDayStats>> entry : this.mStats.entrySet()) {
                for (android.hardware.display.AmbientBrightnessDayStats ambientBrightnessDayStats : entry.getValue()) {
                    sb.append("  ");
                    sb.append(entry.getKey());
                    sb.append(" ");
                    sb.append(ambientBrightnessDayStats);
                    sb.append("\n");
                }
            }
            return sb.toString();
        }

        private java.util.Deque<android.hardware.display.AmbientBrightnessDayStats> getOrCreateUserStats(java.util.Map<java.lang.Integer, java.util.Deque<android.hardware.display.AmbientBrightnessDayStats>> map, int i) {
            if (!map.containsKey(java.lang.Integer.valueOf(i))) {
                map.put(java.lang.Integer.valueOf(i), new java.util.ArrayDeque());
            }
            return map.get(java.lang.Integer.valueOf(i));
        }

        private android.hardware.display.AmbientBrightnessDayStats getOrCreateDayStats(java.util.Deque<android.hardware.display.AmbientBrightnessDayStats> deque, java.time.LocalDate localDate) {
            android.hardware.display.AmbientBrightnessDayStats peekLast = deque.peekLast();
            if (peekLast != null && peekLast.getLocalDate().equals(localDate)) {
                return peekLast;
            }
            if (peekLast != null) {
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.AMBIENT_BRIGHTNESS_STATS_REPORTED, peekLast.getStats(), peekLast.getBucketBoundaries());
            }
            android.hardware.display.AmbientBrightnessDayStats ambientBrightnessDayStats = new android.hardware.display.AmbientBrightnessDayStats(localDate, com.android.server.display.AmbientBrightnessStatsTracker.BUCKET_BOUNDARIES_FOR_NEW_STATS);
            if (deque.size() == 7) {
                deque.poll();
            }
            deque.offer(ambientBrightnessDayStats);
            return ambientBrightnessDayStats;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Timer {
        private final com.android.server.display.AmbientBrightnessStatsTracker.Clock clock;
        private long startTimeMillis;
        private boolean started;

        public Timer(com.android.server.display.AmbientBrightnessStatsTracker.Clock clock) {
            this.clock = clock;
        }

        public void reset() {
            this.started = false;
        }

        public void start() {
            if (!this.started) {
                this.startTimeMillis = this.clock.elapsedTimeMillis();
                this.started = true;
            }
        }

        public boolean isRunning() {
            return this.started;
        }

        public float totalDurationSec() {
            if (this.started) {
                return (float) ((this.clock.elapsedTimeMillis() - this.startTimeMillis) / 1000.0d);
            }
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        public long elapsedRealtimeMillis() {
            return android.os.SystemClock.elapsedRealtime();
        }

        public int getUserSerialNumber(android.os.UserManager userManager, int i) {
            return userManager.getUserSerialNumber(i);
        }

        public int getUserId(android.os.UserManager userManager, int i) {
            return userManager.getUserHandle(i);
        }

        public java.time.LocalDate getLocalDate() {
            return java.time.LocalDate.now();
        }
    }
}
