package com.android.server.people.data;

/* loaded from: classes2.dex */
public class EventIndex {
    private static final int RETENTION_DAYS = 63;
    private static final int TIME_SLOT_FOUR_HOURS = 1;
    private static final int TIME_SLOT_ONE_DAY = 0;
    private static final int TIME_SLOT_ONE_HOUR = 2;
    private static final int TIME_SLOT_TWO_MINUTES = 3;
    private static final int TIME_SLOT_TYPES_COUNT = 4;
    private final long[] mEventBitmaps;
    private final com.android.server.people.data.EventIndex.Injector mInjector;
    private long mLastUpdatedTime;
    private final java.lang.Object mLock;
    private static final java.lang.String TAG = com.android.server.people.data.EventIndex.class.getSimpleName();
    static final com.android.server.people.data.EventIndex EMPTY = new com.android.server.people.data.EventIndex();
    private static final java.util.List<java.util.function.Function<java.lang.Long, android.util.Range<java.lang.Long>>> TIME_SLOT_FACTORIES = java.util.Collections.unmodifiableList(java.util.Arrays.asList(new java.util.function.Function() { // from class: com.android.server.people.data.EventIndex$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final java.lang.Object apply(java.lang.Object obj) {
            android.util.Range createOneDayLongTimeSlot;
            createOneDayLongTimeSlot = com.android.server.people.data.EventIndex.createOneDayLongTimeSlot(((java.lang.Long) obj).longValue());
            return createOneDayLongTimeSlot;
        }
    }, new java.util.function.Function() { // from class: com.android.server.people.data.EventIndex$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final java.lang.Object apply(java.lang.Object obj) {
            android.util.Range createFourHoursLongTimeSlot;
            createFourHoursLongTimeSlot = com.android.server.people.data.EventIndex.createFourHoursLongTimeSlot(((java.lang.Long) obj).longValue());
            return createFourHoursLongTimeSlot;
        }
    }, new java.util.function.Function() { // from class: com.android.server.people.data.EventIndex$$ExternalSyntheticLambda2
        @Override // java.util.function.Function
        public final java.lang.Object apply(java.lang.Object obj) {
            android.util.Range createOneHourLongTimeSlot;
            createOneHourLongTimeSlot = com.android.server.people.data.EventIndex.createOneHourLongTimeSlot(((java.lang.Long) obj).longValue());
            return createOneHourLongTimeSlot;
        }
    }, new java.util.function.Function() { // from class: com.android.server.people.data.EventIndex$$ExternalSyntheticLambda3
        @Override // java.util.function.Function
        public final java.lang.Object apply(java.lang.Object obj) {
            android.util.Range createTwoMinutesLongTimeSlot;
            createTwoMinutesLongTimeSlot = com.android.server.people.data.EventIndex.createTwoMinutesLongTimeSlot(((java.lang.Long) obj).longValue());
            return createTwoMinutesLongTimeSlot;
        }
    }));

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface TimeSlotType {
    }

    static com.android.server.people.data.EventIndex combine(com.android.server.people.data.EventIndex eventIndex, com.android.server.people.data.EventIndex eventIndex2) {
        com.android.server.people.data.EventIndex eventIndex3 = eventIndex.mLastUpdatedTime < eventIndex2.mLastUpdatedTime ? eventIndex : eventIndex2;
        if (eventIndex.mLastUpdatedTime < eventIndex2.mLastUpdatedTime) {
            eventIndex = eventIndex2;
        }
        com.android.server.people.data.EventIndex eventIndex4 = new com.android.server.people.data.EventIndex(eventIndex3);
        eventIndex4.updateEventBitmaps(eventIndex.mLastUpdatedTime);
        for (int i = 0; i < 4; i++) {
            long[] jArr = eventIndex4.mEventBitmaps;
            jArr[i] = jArr[i] | eventIndex.mEventBitmaps[i];
        }
        return eventIndex4;
    }

    EventIndex() {
        this(new com.android.server.people.data.EventIndex.Injector());
    }

    EventIndex(@android.annotation.NonNull com.android.server.people.data.EventIndex eventIndex) {
        this(eventIndex.mInjector, eventIndex.mEventBitmaps, eventIndex.mLastUpdatedTime);
    }

    @com.android.internal.annotations.VisibleForTesting
    EventIndex(@android.annotation.NonNull com.android.server.people.data.EventIndex.Injector injector) {
        this(injector, new long[]{0, 0, 0, 0}, injector.currentTimeMillis());
    }

    private EventIndex(@android.annotation.NonNull com.android.server.people.data.EventIndex.Injector injector, long[] jArr, long j) {
        this.mLock = new java.lang.Object();
        this.mInjector = injector;
        this.mEventBitmaps = java.util.Arrays.copyOf(jArr, 4);
        this.mLastUpdatedTime = j;
    }

    @android.annotation.Nullable
    public android.util.Range<java.lang.Long> getMostRecentActiveTimeSlot() {
        synchronized (this.mLock) {
            for (int i = 3; i >= 0; i--) {
                try {
                    if (this.mEventBitmaps[i] != 0) {
                        android.util.Range<java.lang.Long> apply = TIME_SLOT_FACTORIES.get(i).apply(java.lang.Long.valueOf(this.mLastUpdatedTime));
                        long duration = getDuration(apply) * java.lang.Long.numberOfTrailingZeros(this.mEventBitmaps[i]);
                        return android.util.Range.create(java.lang.Long.valueOf(apply.getLower().longValue() - duration), java.lang.Long.valueOf(apply.getUpper().longValue() - duration));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return null;
        }
    }

    @android.annotation.NonNull
    public java.util.List<android.util.Range<java.lang.Long>> getActiveTimeSlots() {
        java.util.List<android.util.Range<java.lang.Long>> arrayList = new java.util.ArrayList<>();
        synchronized (this.mLock) {
            for (int i = 0; i < 4; i++) {
                try {
                    arrayList = combineTimeSlotLists(arrayList, getActiveTimeSlotsForType(i));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        java.util.Collections.reverse(arrayList);
        return arrayList;
    }

    public boolean isEmpty() {
        synchronized (this.mLock) {
            for (int i = 0; i < 4; i++) {
                try {
                    if (this.mEventBitmaps[i] != 0) {
                        return false;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return true;
        }
    }

    void addEvent(long j) {
        if (EMPTY == this) {
            throw new java.lang.IllegalStateException("EMPTY instance is immutable");
        }
        synchronized (this.mLock) {
            try {
                long currentTimeMillis = this.mInjector.currentTimeMillis();
                updateEventBitmaps(currentTimeMillis);
                for (int i = 0; i < 4; i++) {
                    int diffTimeSlots = diffTimeSlots(i, j, currentTimeMillis);
                    if (diffTimeSlots < 64) {
                        long[] jArr = this.mEventBitmaps;
                        jArr[i] = jArr[i] | (1 << diffTimeSlots);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void update() {
        updateEventBitmaps(this.mInjector.currentTimeMillis());
    }

    public java.lang.String toString() {
        return "EventIndex {perDayEventBitmap=0b" + java.lang.Long.toBinaryString(this.mEventBitmaps[0]) + ", perFourHoursEventBitmap=0b" + java.lang.Long.toBinaryString(this.mEventBitmaps[1]) + ", perHourEventBitmap=0b" + java.lang.Long.toBinaryString(this.mEventBitmaps[2]) + ", perTwoMinutesEventBitmap=0b" + java.lang.Long.toBinaryString(this.mEventBitmaps[3]) + ", lastUpdatedTime=" + android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", this.mLastUpdatedTime) + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.people.data.EventIndex)) {
            return false;
        }
        com.android.server.people.data.EventIndex eventIndex = (com.android.server.people.data.EventIndex) obj;
        return this.mLastUpdatedTime == eventIndex.mLastUpdatedTime && java.util.Arrays.equals(this.mEventBitmaps, eventIndex.mEventBitmaps);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mLastUpdatedTime), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mEventBitmaps)));
    }

    synchronized void writeToProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream) {
        try {
            for (long j : this.mEventBitmaps) {
                protoOutputStream.write(2211908157441L, j);
            }
            protoOutputStream.write(1112396529666L, this.mLastUpdatedTime);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private void updateEventBitmaps(long j) {
        for (int i = 0; i < 4; i++) {
            int diffTimeSlots = diffTimeSlots(i, this.mLastUpdatedTime, j);
            if (diffTimeSlots < 64) {
                long[] jArr = this.mEventBitmaps;
                jArr[i] = jArr[i] << diffTimeSlots;
            } else {
                this.mEventBitmaps[i] = 0;
            }
        }
        long[] jArr2 = this.mEventBitmaps;
        jArr2[0] = jArr2[0] << 1;
        long[] jArr3 = this.mEventBitmaps;
        jArr3[0] = jArr3[0] >>> 1;
        this.mLastUpdatedTime = j;
    }

    static com.android.server.people.data.EventIndex readFromProto(@android.annotation.NonNull android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        long[] jArr = new long[4];
        int i = 0;
        long j = 0;
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    jArr[i] = protoInputStream.readLong(2211908157441L);
                    i++;
                    break;
                case 2:
                    j = protoInputStream.readLong(1112396529666L);
                    break;
                default:
                    android.util.Slog.e(TAG, "Could not read undefined field: " + protoInputStream.getFieldNumber());
                    break;
            }
        }
        return new com.android.server.people.data.EventIndex(new com.android.server.people.data.EventIndex.Injector(), jArr, j);
    }

    private static java.time.LocalDateTime toLocalDateTime(long j) {
        return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(j), java.util.TimeZone.getDefault().toZoneId());
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [java.time.ZonedDateTime] */
    private static long toEpochMilli(java.time.LocalDateTime localDateTime) {
        return localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private static long getDuration(android.util.Range<java.lang.Long> range) {
        return range.getUpper().longValue() - range.getLower().longValue();
    }

    private static int diffTimeSlots(int i, long j, long j2) {
        java.util.function.Function<java.lang.Long, android.util.Range<java.lang.Long>> function = TIME_SLOT_FACTORIES.get(i);
        android.util.Range<java.lang.Long> apply = function.apply(java.lang.Long.valueOf(j));
        return (int) ((function.apply(java.lang.Long.valueOf(j2)).getLower().longValue() - apply.getLower().longValue()) / getDuration(apply));
    }

    private java.util.List<android.util.Range<java.lang.Long>> getActiveTimeSlotsForType(int i) {
        long j = this.mEventBitmaps[i];
        android.util.Range<java.lang.Long> apply = TIME_SLOT_FACTORIES.get(i).apply(java.lang.Long.valueOf(this.mLastUpdatedTime));
        long longValue = apply.getLower().longValue();
        long duration = getDuration(apply);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (j != 0) {
            int numberOfTrailingZeros = java.lang.Long.numberOfTrailingZeros(j);
            if (numberOfTrailingZeros > 0) {
                longValue -= numberOfTrailingZeros * duration;
                j >>>= numberOfTrailingZeros;
            }
            if (j != 0) {
                arrayList.add(android.util.Range.create(java.lang.Long.valueOf(longValue), java.lang.Long.valueOf(longValue + duration)));
                longValue -= duration;
                j >>>= 1;
            }
        }
        return arrayList;
    }

    private static java.util.List<android.util.Range<java.lang.Long>> combineTimeSlotLists(java.util.List<android.util.Range<java.lang.Long>> list, java.util.List<android.util.Range<java.lang.Long>> list2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i = 0;
        int i2 = 0;
        while (i < list.size() && i2 < list2.size()) {
            android.util.Range<java.lang.Long> range = list.get(i);
            android.util.Range<java.lang.Long> range2 = list2.get(i2);
            if (range.contains(range2)) {
                arrayList.add(range2);
                i++;
                i2++;
            } else if (range.getLower().longValue() < range2.getLower().longValue()) {
                arrayList.add(range2);
                i2++;
            } else {
                arrayList.add(range);
                i++;
            }
        }
        if (i < list.size()) {
            arrayList.addAll(list.subList(i, list.size()));
        } else if (i2 < list2.size()) {
            arrayList.addAll(list2.subList(i2, list2.size()));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public static android.util.Range<java.lang.Long> createOneDayLongTimeSlot(long j) {
        java.time.LocalDateTime truncatedTo = toLocalDateTime(j).truncatedTo(java.time.temporal.ChronoUnit.DAYS);
        return android.util.Range.create(java.lang.Long.valueOf(toEpochMilli(truncatedTo)), java.lang.Long.valueOf(toEpochMilli(truncatedTo.plusDays(1L))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public static android.util.Range<java.lang.Long> createFourHoursLongTimeSlot(long j) {
        java.time.LocalDateTime minusHours = toLocalDateTime(j).truncatedTo(java.time.temporal.ChronoUnit.HOURS).minusHours(toLocalDateTime(j).getHour() % 4);
        return android.util.Range.create(java.lang.Long.valueOf(toEpochMilli(minusHours)), java.lang.Long.valueOf(toEpochMilli(minusHours.plusHours(4L))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public static android.util.Range<java.lang.Long> createOneHourLongTimeSlot(long j) {
        java.time.LocalDateTime truncatedTo = toLocalDateTime(j).truncatedTo(java.time.temporal.ChronoUnit.HOURS);
        return android.util.Range.create(java.lang.Long.valueOf(toEpochMilli(truncatedTo)), java.lang.Long.valueOf(toEpochMilli(truncatedTo.plusHours(1L))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public static android.util.Range<java.lang.Long> createTwoMinutesLongTimeSlot(long j) {
        java.time.LocalDateTime minusMinutes = toLocalDateTime(j).truncatedTo(java.time.temporal.ChronoUnit.MINUTES).minusMinutes(toLocalDateTime(j).getMinute() % 2);
        return android.util.Range.create(java.lang.Long.valueOf(toEpochMilli(minusMinutes)), java.lang.Long.valueOf(toEpochMilli(minusMinutes.plusMinutes(2L))));
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        long currentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }
    }
}
