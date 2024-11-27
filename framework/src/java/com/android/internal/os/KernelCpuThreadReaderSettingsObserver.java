package com.android.internal.os;

/* loaded from: classes4.dex */
public class KernelCpuThreadReaderSettingsObserver extends android.database.ContentObserver {
    private static final java.lang.String COLLECTED_UIDS_DEFAULT = "0-0;1000-1000";
    private static final java.lang.String COLLECTED_UIDS_SETTINGS_KEY = "collected_uids";
    private static final int MINIMUM_TOTAL_CPU_USAGE_MILLIS_DEFAULT = 10000;
    private static final java.lang.String MINIMUM_TOTAL_CPU_USAGE_MILLIS_SETTINGS_KEY = "minimum_total_cpu_usage_millis";
    private static final int NUM_BUCKETS_DEFAULT = 8;
    private static final java.lang.String NUM_BUCKETS_SETTINGS_KEY = "num_buckets";
    private static final java.lang.String TAG = "KernelCpuThreadReaderSettingsObserver";
    private final android.content.Context mContext;
    private final com.android.internal.os.KernelCpuThreadReader mKernelCpuThreadReader;
    private final com.android.internal.os.KernelCpuThreadReaderDiff mKernelCpuThreadReaderDiff;

    public static com.android.internal.os.KernelCpuThreadReaderDiff getSettingsModifiedReader(android.content.Context context) {
        com.android.internal.os.KernelCpuThreadReaderSettingsObserver kernelCpuThreadReaderSettingsObserver = new com.android.internal.os.KernelCpuThreadReaderSettingsObserver(context);
        context.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor(android.provider.Settings.Global.KERNEL_CPU_THREAD_READER), false, kernelCpuThreadReaderSettingsObserver, 0);
        return kernelCpuThreadReaderSettingsObserver.mKernelCpuThreadReaderDiff;
    }

    private KernelCpuThreadReaderSettingsObserver(android.content.Context context) {
        super(com.android.internal.os.BackgroundThread.getHandler());
        com.android.internal.os.KernelCpuThreadReaderDiff kernelCpuThreadReaderDiff;
        this.mContext = context;
        this.mKernelCpuThreadReader = com.android.internal.os.KernelCpuThreadReader.create(8, com.android.internal.os.KernelCpuThreadReaderSettingsObserver.UidPredicate.fromString(COLLECTED_UIDS_DEFAULT));
        if (this.mKernelCpuThreadReader == null) {
            kernelCpuThreadReaderDiff = null;
        } else {
            kernelCpuThreadReaderDiff = new com.android.internal.os.KernelCpuThreadReaderDiff(this.mKernelCpuThreadReader, 10000);
        }
        this.mKernelCpuThreadReaderDiff = kernelCpuThreadReaderDiff;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, java.util.Collection<android.net.Uri> collection, int i, int i2) {
        updateReader();
    }

    private void updateReader() {
        if (this.mKernelCpuThreadReader == null) {
            return;
        }
        android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
        try {
            keyValueListParser.setString(android.provider.Settings.Global.getString(this.mContext.getContentResolver(), android.provider.Settings.Global.KERNEL_CPU_THREAD_READER));
            try {
                com.android.internal.os.KernelCpuThreadReaderSettingsObserver.UidPredicate fromString = com.android.internal.os.KernelCpuThreadReaderSettingsObserver.UidPredicate.fromString(keyValueListParser.getString(COLLECTED_UIDS_SETTINGS_KEY, COLLECTED_UIDS_DEFAULT));
                this.mKernelCpuThreadReader.setNumBuckets(keyValueListParser.getInt(NUM_BUCKETS_SETTINGS_KEY, 8));
                this.mKernelCpuThreadReader.setUidPredicate(fromString);
                this.mKernelCpuThreadReaderDiff.setMinimumTotalCpuUsageMillis(keyValueListParser.getInt(MINIMUM_TOTAL_CPU_USAGE_MILLIS_SETTINGS_KEY, 10000));
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.w(TAG, "Failed to get UID predicate", e);
            }
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Slog.e(TAG, "Bad settings", e2);
        }
    }

    public static class UidPredicate implements java.util.function.Predicate<java.lang.Integer> {
        private static final java.util.regex.Pattern UID_RANGE_PATTERN = java.util.regex.Pattern.compile("([0-9]+)-([0-9]+)");
        private static final java.lang.String UID_SPECIFIER_DELIMITER = ";";
        private final java.util.List<android.util.Range<java.lang.Integer>> mAcceptedUidRanges;

        public static com.android.internal.os.KernelCpuThreadReaderSettingsObserver.UidPredicate fromString(java.lang.String str) throws java.lang.NumberFormatException {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.String str2 : str.split(";")) {
                java.util.regex.Matcher matcher = UID_RANGE_PATTERN.matcher(str2);
                if (!matcher.matches()) {
                    throw new java.lang.NumberFormatException("Failed to recognize as number range: " + str2);
                }
                arrayList.add(android.util.Range.create(java.lang.Integer.valueOf(java.lang.Integer.parseInt(matcher.group(1))), java.lang.Integer.valueOf(java.lang.Integer.parseInt(matcher.group(2)))));
            }
            return new com.android.internal.os.KernelCpuThreadReaderSettingsObserver.UidPredicate(arrayList);
        }

        private UidPredicate(java.util.List<android.util.Range<java.lang.Integer>> list) {
            this.mAcceptedUidRanges = list;
        }

        @Override // java.util.function.Predicate
        public boolean test(java.lang.Integer num) {
            for (int i = 0; i < this.mAcceptedUidRanges.size(); i++) {
                if (this.mAcceptedUidRanges.get(i).contains((android.util.Range<java.lang.Integer>) num)) {
                    return true;
                }
            }
            return false;
        }
    }
}
