package android.os;

/* loaded from: classes3.dex */
public class BundleMerger implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.BundleMerger> CREATOR = new android.os.Parcelable.Creator<android.os.BundleMerger>() { // from class: android.os.BundleMerger.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BundleMerger createFromParcel(android.os.Parcel parcel) {
            return new android.os.BundleMerger(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BundleMerger[] newArray(int i) {
            return new android.os.BundleMerger[i];
        }
    };
    public static final int STRATEGY_ARRAY_APPEND = 50;
    public static final int STRATEGY_ARRAY_LIST_APPEND = 60;
    public static final int STRATEGY_BOOLEAN_AND = 30;
    public static final int STRATEGY_BOOLEAN_OR = 40;
    public static final int STRATEGY_COMPARABLE_MAX = 4;
    public static final int STRATEGY_COMPARABLE_MIN = 3;
    public static final int STRATEGY_FIRST = 1;
    public static final int STRATEGY_LAST = 2;
    public static final int STRATEGY_NUMBER_ADD = 10;
    public static final int STRATEGY_NUMBER_INCREMENT_FIRST = 20;
    public static final int STRATEGY_NUMBER_INCREMENT_FIRST_AND_ADD = 25;
    public static final int STRATEGY_REJECT = 0;
    private static final java.lang.String TAG = "BundleMerger";
    private int mDefaultStrategy;
    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mStrategies;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Strategy {
    }

    public BundleMerger() {
        this.mDefaultStrategy = 0;
        this.mStrategies = new android.util.ArrayMap<>();
    }

    private BundleMerger(android.os.Parcel parcel) {
        this.mDefaultStrategy = 0;
        this.mStrategies = new android.util.ArrayMap<>();
        this.mDefaultStrategy = parcel.readInt();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mStrategies.put(parcel.readString(), java.lang.Integer.valueOf(parcel.readInt()));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDefaultStrategy);
        int size = this.mStrategies.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeString(this.mStrategies.keyAt(i2));
            parcel.writeInt(this.mStrategies.valueAt(i2).intValue());
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setDefaultMergeStrategy(int i) {
        this.mDefaultStrategy = i;
    }

    public void setMergeStrategy(java.lang.String str, int i) {
        this.mStrategies.put(str, java.lang.Integer.valueOf(i));
    }

    public int getMergeStrategy(java.lang.String str) {
        return this.mStrategies.getOrDefault(str, java.lang.Integer.valueOf(this.mDefaultStrategy)).intValue();
    }

    public java.util.function.BinaryOperator<android.os.Bundle> asBinaryOperator() {
        return new java.util.function.BinaryOperator() { // from class: android.os.BundleMerger$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                return android.os.BundleMerger.this.merge((android.os.Bundle) obj, (android.os.Bundle) obj2);
            }
        };
    }

    public android.os.Bundle merge(android.os.Bundle bundle, android.os.Bundle bundle2) {
        if (bundle == null && bundle2 == null) {
            return null;
        }
        if (bundle == null) {
            bundle = android.os.Bundle.EMPTY;
        }
        if (bundle2 == null) {
            bundle2 = android.os.Bundle.EMPTY;
        }
        android.os.Bundle bundle3 = new android.os.Bundle();
        bundle3.putAll(bundle);
        bundle3.putAll(bundle2);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        arraySet.addAll(bundle.keySet());
        arraySet.retainAll(bundle2.keySet());
        for (int i = 0; i < arraySet.size(); i++) {
            java.lang.String str = (java.lang.String) arraySet.valueAt(i);
            int mergeStrategy = getMergeStrategy(str);
            java.lang.Object obj = bundle.get(str);
            java.lang.Object obj2 = bundle2.get(str);
            try {
                bundle3.putObject(str, merge(mergeStrategy, obj, obj2));
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "Failed to merge key " + str + " with " + obj + " and " + obj2 + " using strategy " + mergeStrategy, e);
            }
        }
        return bundle3;
    }

    public static java.lang.Object merge(int i, java.lang.Object obj, java.lang.Object obj2) {
        if (obj == null) {
            return obj2;
        }
        if (obj2 == null) {
            return obj;
        }
        if (obj.getClass() != obj2.getClass()) {
            throw new java.lang.IllegalArgumentException("Merging requires consistent classes; first " + obj.getClass() + " last " + obj2.getClass());
        }
        switch (i) {
            case 0:
                if (java.util.Objects.deepEquals(obj, obj2)) {
                    return obj;
                }
                return null;
            case 1:
                return obj;
            case 2:
                return obj2;
            case 3:
                return comparableMin(obj, obj2);
            case 4:
                return comparableMax(obj, obj2);
            case 10:
                return numberAdd(obj, obj2);
            case 20:
                return numberIncrementFirst(obj, obj2);
            case 25:
                return numberAdd(numberIncrementFirst(obj, obj2), obj2);
            case 30:
                return booleanAnd(obj, obj2);
            case 40:
                return booleanOr(obj, obj2);
            case 50:
                return arrayAppend(obj, obj2);
            case 60:
                return arrayListAppend(obj, obj2);
            default:
                throw new java.lang.UnsupportedOperationException();
        }
    }

    private static java.lang.Object comparableMin(java.lang.Object obj, java.lang.Object obj2) {
        return ((java.lang.Comparable) obj).compareTo(obj2) < 0 ? obj : obj2;
    }

    private static java.lang.Object comparableMax(java.lang.Object obj, java.lang.Object obj2) {
        return ((java.lang.Comparable) obj).compareTo(obj2) >= 0 ? obj : obj2;
    }

    private static java.lang.Object numberAdd(java.lang.Object obj, java.lang.Object obj2) {
        if (obj instanceof java.lang.Integer) {
            return java.lang.Integer.valueOf(((java.lang.Integer) obj).intValue() + ((java.lang.Integer) obj2).intValue());
        }
        if (obj instanceof java.lang.Long) {
            return java.lang.Long.valueOf(((java.lang.Long) obj).longValue() + ((java.lang.Long) obj2).longValue());
        }
        if (obj instanceof java.lang.Float) {
            return java.lang.Float.valueOf(((java.lang.Float) obj).floatValue() + ((java.lang.Float) obj2).floatValue());
        }
        if (obj instanceof java.lang.Double) {
            return java.lang.Double.valueOf(((java.lang.Double) obj).doubleValue() + ((java.lang.Double) obj2).doubleValue());
        }
        throw new java.lang.IllegalArgumentException("Unable to add " + obj.getClass());
    }

    private static java.lang.Number numberIncrementFirst(java.lang.Object obj, java.lang.Object obj2) {
        if (obj instanceof java.lang.Integer) {
            return java.lang.Integer.valueOf(((java.lang.Integer) obj).intValue() + 1);
        }
        if (obj instanceof java.lang.Long) {
            return java.lang.Long.valueOf(((java.lang.Long) obj).longValue() + 1);
        }
        throw new java.lang.IllegalArgumentException("Unable to add " + obj.getClass());
    }

    private static java.lang.Object booleanAnd(java.lang.Object obj, java.lang.Object obj2) {
        return java.lang.Boolean.valueOf(((java.lang.Boolean) obj).booleanValue() && ((java.lang.Boolean) obj2).booleanValue());
    }

    private static java.lang.Object booleanOr(java.lang.Object obj, java.lang.Object obj2) {
        return java.lang.Boolean.valueOf(((java.lang.Boolean) obj).booleanValue() || ((java.lang.Boolean) obj2).booleanValue());
    }

    private static java.lang.Object arrayAppend(java.lang.Object obj, java.lang.Object obj2) {
        if (!obj.getClass().isArray()) {
            throw new java.lang.IllegalArgumentException("Unable to append " + obj.getClass());
        }
        java.lang.Class<?> componentType = obj.getClass().getComponentType();
        int length = java.lang.reflect.Array.getLength(obj);
        int length2 = java.lang.reflect.Array.getLength(obj2);
        java.lang.Object newInstance = java.lang.reflect.Array.newInstance(componentType, length + length2);
        java.lang.System.arraycopy(obj, 0, newInstance, 0, length);
        java.lang.System.arraycopy(obj2, 0, newInstance, length, length2);
        return newInstance;
    }

    private static java.lang.Object arrayListAppend(java.lang.Object obj, java.lang.Object obj2) {
        if (!(obj instanceof java.util.ArrayList)) {
            throw new java.lang.IllegalArgumentException("Unable to append " + obj.getClass());
        }
        java.util.ArrayList arrayList = (java.util.ArrayList) obj;
        java.util.ArrayList arrayList2 = (java.util.ArrayList) obj2;
        java.util.ArrayList arrayList3 = new java.util.ArrayList(arrayList.size() + arrayList2.size());
        arrayList3.addAll(arrayList);
        arrayList3.addAll(arrayList2);
        return arrayList3;
    }
}
