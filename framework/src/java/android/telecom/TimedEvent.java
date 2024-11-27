package android.telecom;

/* loaded from: classes3.dex */
public abstract class TimedEvent<T> {
    public abstract T getKey();

    public abstract long getTime();

    public static <T> java.util.Map<T, java.lang.Double> averageTimings(java.util.Collection<? extends android.telecom.TimedEvent<T>> collection) {
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.HashMap hashMap2 = new java.util.HashMap();
        for (android.telecom.TimedEvent<T> timedEvent : collection) {
            if (hashMap.containsKey(timedEvent.getKey())) {
                hashMap.put(timedEvent.getKey(), java.lang.Integer.valueOf(((java.lang.Integer) hashMap.get(timedEvent.getKey())).intValue() + 1));
                hashMap2.put(timedEvent.getKey(), java.lang.Double.valueOf(((java.lang.Double) hashMap2.get(timedEvent.getKey())).doubleValue() + timedEvent.getTime()));
            } else {
                hashMap.put(timedEvent.getKey(), 1);
                hashMap2.put(timedEvent.getKey(), java.lang.Double.valueOf(timedEvent.getTime()));
            }
        }
        for (java.util.Map.Entry entry : hashMap2.entrySet()) {
            hashMap2.put(entry.getKey(), java.lang.Double.valueOf(((java.lang.Double) entry.getValue()).doubleValue() / ((java.lang.Integer) hashMap.get(entry.getKey())).intValue()));
        }
        return hashMap2;
    }
}
