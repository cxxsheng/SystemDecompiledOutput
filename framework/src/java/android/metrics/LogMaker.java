package android.metrics;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class LogMaker {
    public static final int MAX_SERIALIZED_SIZE = 4000;
    private static final java.lang.String TAG = "LogBuilder";
    private android.util.SparseArray<java.lang.Object> entries = new android.util.SparseArray<>();

    public LogMaker(int i) {
        setCategory(i);
    }

    public LogMaker(java.lang.Object[] objArr) {
        if (objArr != null) {
            deserialize(objArr);
        } else {
            setCategory(0);
        }
    }

    public android.metrics.LogMaker setCategory(int i) {
        this.entries.put(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_CATEGORY, java.lang.Integer.valueOf(i));
        return this;
    }

    public android.metrics.LogMaker clearCategory() {
        this.entries.remove(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_CATEGORY);
        return this;
    }

    public android.metrics.LogMaker setType(int i) {
        this.entries.put(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_TYPE, java.lang.Integer.valueOf(i));
        return this;
    }

    public android.metrics.LogMaker clearType() {
        this.entries.remove(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_TYPE);
        return this;
    }

    public android.metrics.LogMaker setSubtype(int i) {
        this.entries.put(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_SUBTYPE, java.lang.Integer.valueOf(i));
        return this;
    }

    public android.metrics.LogMaker clearSubtype() {
        this.entries.remove(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_SUBTYPE);
        return this;
    }

    public android.metrics.LogMaker setLatency(long j) {
        this.entries.put(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_LATENCY_MILLIS, java.lang.Long.valueOf(j));
        return this;
    }

    public android.metrics.LogMaker setTimestamp(long j) {
        this.entries.put(805, java.lang.Long.valueOf(j));
        return this;
    }

    public android.metrics.LogMaker clearTimestamp() {
        this.entries.remove(805);
        return this;
    }

    public android.metrics.LogMaker setPackageName(java.lang.String str) {
        this.entries.put(806, str);
        return this;
    }

    public android.metrics.LogMaker setComponentName(android.content.ComponentName componentName) {
        this.entries.put(806, componentName.getPackageName());
        this.entries.put(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_CLASS_NAME, componentName.getClassName());
        return this;
    }

    public android.metrics.LogMaker clearPackageName() {
        this.entries.remove(806);
        return this;
    }

    public android.metrics.LogMaker setProcessId(int i) {
        this.entries.put(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_PID, java.lang.Integer.valueOf(i));
        return this;
    }

    public android.metrics.LogMaker clearProcessId() {
        this.entries.remove(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_PID);
        return this;
    }

    public android.metrics.LogMaker setUid(int i) {
        this.entries.put(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_UID, java.lang.Integer.valueOf(i));
        return this;
    }

    public android.metrics.LogMaker clearUid() {
        this.entries.remove(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_UID);
        return this;
    }

    public android.metrics.LogMaker setCounterName(java.lang.String str) {
        this.entries.put(799, str);
        return this;
    }

    public android.metrics.LogMaker setCounterBucket(int i) {
        this.entries.put(801, java.lang.Integer.valueOf(i));
        return this;
    }

    public android.metrics.LogMaker setCounterBucket(long j) {
        this.entries.put(801, java.lang.Long.valueOf(j));
        return this;
    }

    public android.metrics.LogMaker setCounterValue(int i) {
        this.entries.put(802, java.lang.Integer.valueOf(i));
        return this;
    }

    public android.metrics.LogMaker addTaggedData(int i, java.lang.Object obj) {
        if (obj == null) {
            return clearTaggedData(i);
        }
        if (!isValidValue(obj)) {
            throw new java.lang.IllegalArgumentException("Value must be loggable type - int, long, float, String");
        }
        if (obj.toString().getBytes().length > 4000) {
            android.util.Log.i(TAG, "Log value too long, omitted: " + obj.toString());
        } else {
            this.entries.put(i, obj);
        }
        return this;
    }

    public android.metrics.LogMaker clearTaggedData(int i) {
        this.entries.delete(i);
        return this;
    }

    public boolean isValidValue(java.lang.Object obj) {
        return (obj instanceof java.lang.Integer) || (obj instanceof java.lang.String) || (obj instanceof java.lang.Long) || (obj instanceof java.lang.Float);
    }

    public java.lang.Object getTaggedData(int i) {
        return this.entries.get(i);
    }

    public int getCategory() {
        java.lang.Object obj = this.entries.get(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_CATEGORY);
        if (obj instanceof java.lang.Integer) {
            return ((java.lang.Integer) obj).intValue();
        }
        return 0;
    }

    public int getType() {
        java.lang.Object obj = this.entries.get(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_TYPE);
        if (obj instanceof java.lang.Integer) {
            return ((java.lang.Integer) obj).intValue();
        }
        return 0;
    }

    public int getSubtype() {
        java.lang.Object obj = this.entries.get(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_SUBTYPE);
        if (obj instanceof java.lang.Integer) {
            return ((java.lang.Integer) obj).intValue();
        }
        return 0;
    }

    public long getTimestamp() {
        java.lang.Object obj = this.entries.get(805);
        if (obj instanceof java.lang.Long) {
            return ((java.lang.Long) obj).longValue();
        }
        return 0L;
    }

    public java.lang.String getPackageName() {
        java.lang.Object obj = this.entries.get(806);
        if (obj instanceof java.lang.String) {
            return (java.lang.String) obj;
        }
        return null;
    }

    public int getProcessId() {
        java.lang.Object obj = this.entries.get(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_PID);
        if (obj instanceof java.lang.Integer) {
            return ((java.lang.Integer) obj).intValue();
        }
        return -1;
    }

    public int getUid() {
        java.lang.Object obj = this.entries.get(com.android.internal.logging.nano.MetricsProto.MetricsEvent.RESERVED_FOR_LOGBUILDER_UID);
        if (obj instanceof java.lang.Integer) {
            return ((java.lang.Integer) obj).intValue();
        }
        return -1;
    }

    public java.lang.String getCounterName() {
        java.lang.Object obj = this.entries.get(799);
        if (obj instanceof java.lang.String) {
            return (java.lang.String) obj;
        }
        return null;
    }

    public long getCounterBucket() {
        java.lang.Object obj = this.entries.get(801);
        if (obj instanceof java.lang.Number) {
            return ((java.lang.Number) obj).longValue();
        }
        return 0L;
    }

    public boolean isLongCounterBucket() {
        return this.entries.get(801) instanceof java.lang.Long;
    }

    public int getCounterValue() {
        java.lang.Object obj = this.entries.get(802);
        if (obj instanceof java.lang.Integer) {
            return ((java.lang.Integer) obj).intValue();
        }
        return 0;
    }

    public java.lang.Object[] serialize() {
        java.lang.Object[] objArr = new java.lang.Object[this.entries.size() * 2];
        for (int i = 0; i < this.entries.size(); i++) {
            int i2 = i * 2;
            objArr[i2] = java.lang.Integer.valueOf(this.entries.keyAt(i));
            objArr[i2 + 1] = this.entries.valueAt(i);
        }
        int length = java.util.Arrays.toString(objArr).getBytes().length;
        if (length > 4000) {
            android.util.Log.i(TAG, "Log line too long, did not emit: " + length + " bytes.");
            throw new java.lang.RuntimeException();
        }
        return objArr;
    }

    public void deserialize(java.lang.Object[] objArr) {
        int i;
        java.lang.Object obj;
        for (int i2 = 0; objArr != null && i2 < objArr.length; i2 = i) {
            i = i2 + 1;
            java.lang.Object obj2 = objArr[i2];
            if (i < objArr.length) {
                obj = objArr[i];
                i++;
            } else {
                obj = null;
            }
            if (obj2 instanceof java.lang.Integer) {
                this.entries.put(((java.lang.Integer) obj2).intValue(), obj);
            } else {
                android.util.Log.i(TAG, "Invalid key " + (obj2 == null ? "null" : obj2.toString()));
            }
        }
    }

    public boolean isSubsetOf(android.metrics.LogMaker logMaker) {
        if (logMaker == null) {
            return false;
        }
        for (int i = 0; i < this.entries.size(); i++) {
            int keyAt = this.entries.keyAt(i);
            java.lang.Object valueAt = this.entries.valueAt(i);
            java.lang.Object obj = logMaker.entries.get(keyAt);
            if ((valueAt == null && obj != null) || !valueAt.equals(obj)) {
                return false;
            }
        }
        return true;
    }

    public android.util.SparseArray<java.lang.Object> getEntries() {
        return this.entries;
    }
}
