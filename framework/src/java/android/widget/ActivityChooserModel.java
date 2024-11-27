package android.widget;

/* loaded from: classes4.dex */
public class ActivityChooserModel extends android.database.DataSetObservable {
    private static final java.lang.String ATTRIBUTE_ACTIVITY = "activity";
    private static final java.lang.String ATTRIBUTE_TIME = "time";
    private static final java.lang.String ATTRIBUTE_WEIGHT = "weight";
    private static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final java.lang.String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final java.lang.String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    private static final java.lang.String TAG_HISTORICAL_RECORD = "historical-record";
    private static final java.lang.String TAG_HISTORICAL_RECORDS = "historical-records";
    private android.widget.ActivityChooserModel.OnChooseActivityListener mActivityChoserModelPolicy;
    private final android.content.Context mContext;
    private final java.lang.String mHistoryFileName;
    private android.content.Intent mIntent;
    private static final java.lang.String LOG_TAG = android.widget.ActivityChooserModel.class.getSimpleName();
    private static final java.lang.Object sRegistryLock = new java.lang.Object();
    private static final java.util.Map<java.lang.String, android.widget.ActivityChooserModel> sDataModelRegistry = new java.util.HashMap();
    private final java.lang.Object mInstanceLock = new java.lang.Object();
    private final java.util.List<android.widget.ActivityChooserModel.ActivityResolveInfo> mActivities = new java.util.ArrayList();
    private final java.util.List<android.widget.ActivityChooserModel.HistoricalRecord> mHistoricalRecords = new java.util.ArrayList();
    private final com.android.internal.content.PackageMonitor mPackageMonitor = new android.widget.ActivityChooserModel.DataModelPackageMonitor();
    private android.widget.ActivityChooserModel.ActivitySorter mActivitySorter = new android.widget.ActivityChooserModel.DefaultSorter();
    private int mHistoryMaxSize = 50;
    private boolean mCanReadHistoricalData = true;
    private boolean mReadShareHistoryCalled = false;
    private boolean mHistoricalRecordsChanged = true;
    private boolean mReloadActivities = false;

    public interface ActivityChooserModelClient {
        void setActivityChooserModel(android.widget.ActivityChooserModel activityChooserModel);
    }

    public interface ActivitySorter {
        void sort(android.content.Intent intent, java.util.List<android.widget.ActivityChooserModel.ActivityResolveInfo> list, java.util.List<android.widget.ActivityChooserModel.HistoricalRecord> list2);
    }

    public interface OnChooseActivityListener {
        boolean onChooseActivity(android.widget.ActivityChooserModel activityChooserModel, android.content.Intent intent);
    }

    public static android.widget.ActivityChooserModel get(android.content.Context context, java.lang.String str) {
        android.widget.ActivityChooserModel activityChooserModel;
        synchronized (sRegistryLock) {
            activityChooserModel = sDataModelRegistry.get(str);
            if (activityChooserModel == null) {
                activityChooserModel = new android.widget.ActivityChooserModel(context, str);
                sDataModelRegistry.put(str, activityChooserModel);
            }
        }
        return activityChooserModel;
    }

    private ActivityChooserModel(android.content.Context context, java.lang.String str) {
        this.mContext = context.getApplicationContext();
        if (!android.text.TextUtils.isEmpty(str) && !str.endsWith(HISTORY_FILE_EXTENSION)) {
            this.mHistoryFileName = str + HISTORY_FILE_EXTENSION;
        } else {
            this.mHistoryFileName = str;
        }
        this.mPackageMonitor.register(this.mContext, (android.os.Looper) null, true);
    }

    public void setIntent(android.content.Intent intent) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == intent) {
                return;
            }
            this.mIntent = intent;
            this.mReloadActivities = true;
            ensureConsistentState();
        }
    }

    public android.content.Intent getIntent() {
        android.content.Intent intent;
        synchronized (this.mInstanceLock) {
            intent = this.mIntent;
        }
        return intent;
    }

    public int getActivityCount() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mActivities.size();
        }
        return size;
    }

    public android.content.pm.ResolveInfo getActivity(int i) {
        android.content.pm.ResolveInfo resolveInfo;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            resolveInfo = this.mActivities.get(i).resolveInfo;
        }
        return resolveInfo;
    }

    public int getActivityIndex(android.content.pm.ResolveInfo resolveInfo) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            java.util.List<android.widget.ActivityChooserModel.ActivityResolveInfo> list = this.mActivities;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i).resolveInfo == resolveInfo) {
                    return i;
                }
            }
            return -1;
        }
    }

    public android.content.Intent chooseActivity(int i) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == null) {
                return null;
            }
            ensureConsistentState();
            android.widget.ActivityChooserModel.ActivityResolveInfo activityResolveInfo = this.mActivities.get(i);
            android.content.ComponentName componentName = new android.content.ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name);
            android.content.Intent intent = new android.content.Intent(this.mIntent);
            intent.setComponent(componentName);
            if (this.mActivityChoserModelPolicy != null) {
                if (this.mActivityChoserModelPolicy.onChooseActivity(this, new android.content.Intent(intent))) {
                    return null;
                }
            }
            addHisoricalRecord(new android.widget.ActivityChooserModel.HistoricalRecord(componentName, java.lang.System.currentTimeMillis(), 1.0f));
            return intent;
        }
    }

    public void setOnChooseActivityListener(android.widget.ActivityChooserModel.OnChooseActivityListener onChooseActivityListener) {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = onChooseActivityListener;
        }
    }

    public android.content.pm.ResolveInfo getDefaultActivity() {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            if (!this.mActivities.isEmpty()) {
                return this.mActivities.get(0).resolveInfo;
            }
            return null;
        }
    }

    public void setDefaultActivity(int i) {
        float f;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            android.widget.ActivityChooserModel.ActivityResolveInfo activityResolveInfo = this.mActivities.get(i);
            android.widget.ActivityChooserModel.ActivityResolveInfo activityResolveInfo2 = this.mActivities.get(0);
            if (activityResolveInfo2 != null) {
                f = (activityResolveInfo2.weight - activityResolveInfo.weight) + 5.0f;
            } else {
                f = 1.0f;
            }
            addHisoricalRecord(new android.widget.ActivityChooserModel.HistoricalRecord(new android.content.ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), java.lang.System.currentTimeMillis(), f));
        }
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw new java.lang.IllegalStateException("No preceding call to #readHistoricalData");
        }
        if (!this.mHistoricalRecordsChanged) {
            return;
        }
        this.mHistoricalRecordsChanged = false;
        if (!android.text.TextUtils.isEmpty(this.mHistoryFileName)) {
            new android.widget.ActivityChooserModel.PersistHistoryAsyncTask().executeOnExecutor(android.os.AsyncTask.SERIAL_EXECUTOR, new java.util.ArrayList(this.mHistoricalRecords), this.mHistoryFileName);
        }
    }

    public void setActivitySorter(android.widget.ActivityChooserModel.ActivitySorter activitySorter) {
        synchronized (this.mInstanceLock) {
            if (this.mActivitySorter == activitySorter) {
                return;
            }
            this.mActivitySorter = activitySorter;
            if (sortActivitiesIfNeeded()) {
                notifyChanged();
            }
        }
    }

    public void setHistoryMaxSize(int i) {
        synchronized (this.mInstanceLock) {
            if (this.mHistoryMaxSize == i) {
                return;
            }
            this.mHistoryMaxSize = i;
            pruneExcessiveHistoricalRecordsIfNeeded();
            if (sortActivitiesIfNeeded()) {
                notifyChanged();
            }
        }
    }

    public int getHistoryMaxSize() {
        int i;
        synchronized (this.mInstanceLock) {
            i = this.mHistoryMaxSize;
        }
        return i;
    }

    public int getHistorySize() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mHistoricalRecords.size();
        }
        return size;
    }

    protected void finalize() throws java.lang.Throwable {
        super.finalize();
        this.mPackageMonitor.unregister();
    }

    private void ensureConsistentState() {
        boolean loadActivitiesIfNeeded = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (loadActivitiesIfNeeded) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter != null && this.mIntent != null && !this.mActivities.isEmpty() && !this.mHistoricalRecords.isEmpty()) {
            this.mActivitySorter.sort(this.mIntent, this.mActivities, java.util.Collections.unmodifiableList(this.mHistoricalRecords));
            return true;
        }
        return false;
    }

    private boolean loadActivitiesIfNeeded() {
        if (!this.mReloadActivities || this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int size = queryIntentActivities.size();
        for (int i = 0; i < size; i++) {
            android.content.pm.ResolveInfo resolveInfo = queryIntentActivities.get(i);
            android.content.pm.ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (android.app.ActivityManager.checkComponentPermission(activityInfo.permission, android.os.Process.myUid(), activityInfo.applicationInfo.uid, activityInfo.exported) == 0) {
                this.mActivities.add(new android.widget.ActivityChooserModel.ActivityResolveInfo(resolveInfo));
            }
        }
        return true;
    }

    private boolean readHistoricalDataIfNeeded() {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || android.text.TextUtils.isEmpty(this.mHistoryFileName)) {
            return false;
        }
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    private boolean addHisoricalRecord(android.widget.ActivityChooserModel.HistoricalRecord historicalRecord) {
        boolean add = this.mHistoricalRecords.add(historicalRecord);
        if (add) {
            this.mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
        return add;
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int size = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (size <= 0) {
            return;
        }
        this.mHistoricalRecordsChanged = true;
        for (int i = 0; i < size; i++) {
            this.mHistoricalRecords.remove(0);
        }
    }

    public static final class HistoricalRecord {
        public final android.content.ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(java.lang.String str, long j, float f) {
            this(android.content.ComponentName.unflattenFromString(str), j, f);
        }

        public HistoricalRecord(android.content.ComponentName componentName, long j, float f) {
            this.activity = componentName;
            this.time = j;
            this.weight = f;
        }

        public int hashCode() {
            return (((((this.activity == null ? 0 : this.activity.hashCode()) + 31) * 31) + ((int) (this.time ^ (this.time >>> 32)))) * 31) + java.lang.Float.floatToIntBits(this.weight);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.widget.ActivityChooserModel.HistoricalRecord historicalRecord = (android.widget.ActivityChooserModel.HistoricalRecord) obj;
            if (this.activity == null) {
                if (historicalRecord.activity != null) {
                    return false;
                }
            } else if (!this.activity.equals(historicalRecord.activity)) {
                return false;
            }
            if (this.time == historicalRecord.time && java.lang.Float.floatToIntBits(this.weight) == java.lang.Float.floatToIntBits(historicalRecord.weight)) {
                return true;
            }
            return false;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
            sb.append("; activity:").append(this.activity);
            sb.append("; time:").append(this.time);
            sb.append("; weight:").append(new java.math.BigDecimal(this.weight));
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            return sb.toString();
        }
    }

    public final class ActivityResolveInfo implements java.lang.Comparable<android.widget.ActivityChooserModel.ActivityResolveInfo> {
        public final android.content.pm.ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(android.content.pm.ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
        }

        public int hashCode() {
            return java.lang.Float.floatToIntBits(this.weight) + 31;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass() && java.lang.Float.floatToIntBits(this.weight) == java.lang.Float.floatToIntBits(((android.widget.ActivityChooserModel.ActivityResolveInfo) obj).weight)) {
                return true;
            }
            return false;
        }

        @Override // java.lang.Comparable
        public int compareTo(android.widget.ActivityChooserModel.ActivityResolveInfo activityResolveInfo) {
            return java.lang.Float.floatToIntBits(activityResolveInfo.weight) - java.lang.Float.floatToIntBits(this.weight);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
            sb.append("resolveInfo:").append(this.resolveInfo.toString());
            sb.append("; weight:").append(new java.math.BigDecimal(this.weight));
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            return sb.toString();
        }
    }

    private final class DefaultSorter implements android.widget.ActivityChooserModel.ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final java.util.Map<android.content.ComponentName, android.widget.ActivityChooserModel.ActivityResolveInfo> mPackageNameToActivityMap;

        private DefaultSorter() {
            this.mPackageNameToActivityMap = new java.util.HashMap();
        }

        @Override // android.widget.ActivityChooserModel.ActivitySorter
        public void sort(android.content.Intent intent, java.util.List<android.widget.ActivityChooserModel.ActivityResolveInfo> list, java.util.List<android.widget.ActivityChooserModel.HistoricalRecord> list2) {
            java.util.Map<android.content.ComponentName, android.widget.ActivityChooserModel.ActivityResolveInfo> map = this.mPackageNameToActivityMap;
            map.clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                android.widget.ActivityChooserModel.ActivityResolveInfo activityResolveInfo = list.get(i);
                activityResolveInfo.weight = 0.0f;
                map.put(new android.content.ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), activityResolveInfo);
            }
            float f = 1.0f;
            for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                android.widget.ActivityChooserModel.HistoricalRecord historicalRecord = list2.get(size2);
                android.widget.ActivityChooserModel.ActivityResolveInfo activityResolveInfo2 = map.get(historicalRecord.activity);
                if (activityResolveInfo2 != null) {
                    activityResolveInfo2.weight += historicalRecord.weight * f;
                    f *= WEIGHT_DECAY_COEFFICIENT;
                }
            }
            java.util.Collections.sort(list);
        }
    }

    private void readHistoricalDataImpl() {
        java.io.FileInputStream openFileInput;
        org.xmlpull.v1.XmlPullParser newPullParser;
        try {
            try {
                try {
                    openFileInput = this.mContext.openFileInput(this.mHistoryFileName);
                    try {
                        newPullParser = android.util.Xml.newPullParser();
                        newPullParser.setInput(openFileInput, java.nio.charset.StandardCharsets.UTF_8.name());
                        for (int i = 0; i != 1 && i != 2; i = newPullParser.next()) {
                        }
                    } catch (java.io.IOException e) {
                        android.util.Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, e);
                        if (openFileInput == null) {
                            return;
                        } else {
                            openFileInput.close();
                        }
                    } catch (org.xmlpull.v1.XmlPullParserException e2) {
                        android.util.Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, e2);
                        if (openFileInput == null) {
                            return;
                        } else {
                            openFileInput.close();
                        }
                    }
                    if (!TAG_HISTORICAL_RECORDS.equals(newPullParser.getName())) {
                        throw new org.xmlpull.v1.XmlPullParserException("Share records file does not start with historical-records tag.");
                    }
                    java.util.List<android.widget.ActivityChooserModel.HistoricalRecord> list = this.mHistoricalRecords;
                    list.clear();
                    while (true) {
                        int next = newPullParser.next();
                        if (next == 1) {
                            if (openFileInput == null) {
                                return;
                            } else {
                                openFileInput.close();
                            }
                        } else if (next != 3 && next != 4) {
                            if (!TAG_HISTORICAL_RECORD.equals(newPullParser.getName())) {
                                throw new org.xmlpull.v1.XmlPullParserException("Share records file not well-formed.");
                            }
                            list.add(new android.widget.ActivityChooserModel.HistoricalRecord(newPullParser.getAttributeValue(null, "activity"), java.lang.Long.parseLong(newPullParser.getAttributeValue(null, "time")), java.lang.Float.parseFloat(newPullParser.getAttributeValue(null, "weight"))));
                        }
                    }
                } catch (java.io.IOException e3) {
                }
            } catch (java.lang.Throwable th) {
                if (openFileInput != null) {
                    try {
                        openFileInput.close();
                    } catch (java.io.IOException e4) {
                    }
                }
                throw th;
            }
        } catch (java.io.FileNotFoundException e5) {
        }
    }

    private final class PersistHistoryAsyncTask extends android.os.AsyncTask<java.lang.Object, java.lang.Void, java.lang.Void> {
        private PersistHistoryAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public java.lang.Void doInBackground(java.lang.Object... objArr) {
            java.util.List list = (java.util.List) objArr[0];
            java.lang.String str = (java.lang.String) objArr[1];
            try {
                java.io.FileOutputStream openFileOutput = android.widget.ActivityChooserModel.this.mContext.openFileOutput(str, 0);
                org.xmlpull.v1.XmlSerializer newSerializer = android.util.Xml.newSerializer();
                try {
                    try {
                        try {
                            try {
                                newSerializer.setOutput(openFileOutput, null);
                                newSerializer.startDocument(java.nio.charset.StandardCharsets.UTF_8.name(), true);
                                newSerializer.startTag(null, android.widget.ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                                int size = list.size();
                                for (int i = 0; i < size; i++) {
                                    android.widget.ActivityChooserModel.HistoricalRecord historicalRecord = (android.widget.ActivityChooserModel.HistoricalRecord) list.remove(0);
                                    newSerializer.startTag(null, android.widget.ActivityChooserModel.TAG_HISTORICAL_RECORD);
                                    newSerializer.attribute(null, "activity", historicalRecord.activity.flattenToString());
                                    newSerializer.attribute(null, "time", java.lang.String.valueOf(historicalRecord.time));
                                    newSerializer.attribute(null, "weight", java.lang.String.valueOf(historicalRecord.weight));
                                    newSerializer.endTag(null, android.widget.ActivityChooserModel.TAG_HISTORICAL_RECORD);
                                }
                                newSerializer.endTag(null, android.widget.ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                                newSerializer.endDocument();
                                android.widget.ActivityChooserModel.this.mCanReadHistoricalData = true;
                                if (openFileOutput != null) {
                                    openFileOutput.close();
                                }
                            } catch (java.io.IOException e) {
                                android.util.Log.e(android.widget.ActivityChooserModel.LOG_TAG, "Error writing historical recrod file: " + android.widget.ActivityChooserModel.this.mHistoryFileName, e);
                                android.widget.ActivityChooserModel.this.mCanReadHistoricalData = true;
                                if (openFileOutput != null) {
                                    openFileOutput.close();
                                }
                            } catch (java.lang.IllegalArgumentException e2) {
                                android.util.Log.e(android.widget.ActivityChooserModel.LOG_TAG, "Error writing historical recrod file: " + android.widget.ActivityChooserModel.this.mHistoryFileName, e2);
                                android.widget.ActivityChooserModel.this.mCanReadHistoricalData = true;
                                if (openFileOutput != null) {
                                    openFileOutput.close();
                                }
                            }
                        } catch (java.lang.IllegalStateException e3) {
                            android.util.Log.e(android.widget.ActivityChooserModel.LOG_TAG, "Error writing historical recrod file: " + android.widget.ActivityChooserModel.this.mHistoryFileName, e3);
                            android.widget.ActivityChooserModel.this.mCanReadHistoricalData = true;
                            if (openFileOutput != null) {
                                openFileOutput.close();
                            }
                        }
                    } catch (java.io.IOException e4) {
                    }
                    return null;
                } catch (java.lang.Throwable th) {
                    android.widget.ActivityChooserModel.this.mCanReadHistoricalData = true;
                    if (openFileOutput != null) {
                        try {
                            openFileOutput.close();
                        } catch (java.io.IOException e5) {
                        }
                    }
                    throw th;
                }
            } catch (java.io.FileNotFoundException e6) {
                android.util.Log.e(android.widget.ActivityChooserModel.LOG_TAG, "Error writing historical recrod file: " + str, e6);
                return null;
            }
        }
    }

    private final class DataModelPackageMonitor extends com.android.internal.content.PackageMonitor {
        private DataModelPackageMonitor() {
        }

        @Override // com.android.internal.content.PackageMonitor
        public void onSomePackagesChanged() {
            android.widget.ActivityChooserModel.this.mReloadActivities = true;
        }
    }
}
