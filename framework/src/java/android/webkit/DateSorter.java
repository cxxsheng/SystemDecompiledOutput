package android.webkit;

/* loaded from: classes4.dex */
public class DateSorter {
    public static final int DAY_COUNT = 5;
    private static final java.lang.String LOGTAG = "webkit";
    private static final int NUM_DAYS_AGO = 7;
    private long[] mBins = new long[4];
    private java.lang.String[] mLabels = new java.lang.String[5];

    public DateSorter(android.content.Context context) {
        android.content.res.Resources resources = context.getResources();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        beginningOfDay(calendar);
        this.mBins[0] = calendar.getTimeInMillis();
        calendar.add(6, -1);
        this.mBins[1] = calendar.getTimeInMillis();
        calendar.add(6, -6);
        this.mBins[2] = calendar.getTimeInMillis();
        calendar.add(6, 7);
        calendar.add(2, -1);
        this.mBins[3] = calendar.getTimeInMillis();
        java.util.Locale locale = resources.getConfiguration().locale;
        com.android.icu.text.DateSorterBridge createInstance = com.android.icu.text.DateSorterBridge.createInstance(locale == null ? java.util.Locale.getDefault() : locale);
        this.mLabels[0] = createInstance.getToday();
        this.mLabels[1] = createInstance.getYesterday();
        java.util.HashMap hashMap = new java.util.HashMap();
        hashMap.put("count", 7);
        this.mLabels[2] = android.util.PluralsMessageFormatter.format(resources, hashMap, com.android.internal.R.string.last_num_days);
        this.mLabels[3] = context.getString(com.android.internal.R.string.last_month);
        this.mLabels[4] = context.getString(com.android.internal.R.string.older);
    }

    public int getIndex(long j) {
        for (int i = 0; i < 4; i++) {
            if (j > this.mBins[i]) {
                return i;
            }
        }
        return 4;
    }

    public java.lang.String getLabel(int i) {
        if (i < 0 || i >= 5) {
            return "";
        }
        return this.mLabels[i];
    }

    public long getBoundary(int i) {
        if (i < 0 || i > 4) {
            i = 0;
        }
        if (i == 4) {
            return Long.MIN_VALUE;
        }
        return this.mBins[i];
    }

    private void beginningOfDay(java.util.Calendar calendar) {
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
    }
}
