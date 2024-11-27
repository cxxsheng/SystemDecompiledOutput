package com.android.internal.util.jobs;

@android.ravenwood.annotation.RavenwoodKeepWholeClass
/* loaded from: classes.dex */
public class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> boolean contains(@android.annotation.Nullable java.util.Collection<T> collection, T t) {
        return collection != null && collection.contains(t);
    }

    @android.annotation.NonNull
    public static <T> java.util.List<T> filter(@android.annotation.Nullable java.util.List<T> list, java.util.function.Predicate<? super T> predicate) {
        java.util.ArrayList arrayList = null;
        for (int i = 0; i < size(list); i++) {
            T t = list.get(i);
            if (predicate.test(t)) {
                arrayList = com.android.internal.util.jobs.ArrayUtils.add(arrayList, t);
            }
        }
        return emptyIfNull(arrayList);
    }

    @android.annotation.NonNull
    public static <T> java.util.Set<T> filter(@android.annotation.Nullable java.util.Set<T> set, java.util.function.Predicate<? super T> predicate) {
        if (set == null || set.size() == 0) {
            return java.util.Collections.emptySet();
        }
        android.util.ArraySet arraySet = null;
        if (set instanceof android.util.ArraySet) {
            android.util.ArraySet arraySet2 = (android.util.ArraySet) set;
            int size = arraySet2.size();
            for (int i = 0; i < size; i++) {
                android.Manifest manifest = (java.lang.Object) arraySet2.valueAt(i);
                if (predicate.test(manifest)) {
                    arraySet = com.android.internal.util.jobs.ArrayUtils.add((android.util.ArraySet<android.Manifest>) arraySet, manifest);
                }
            }
        } else {
            for (java.lang.Object obj : set) {
                if (predicate.test(obj)) {
                    arraySet = com.android.internal.util.jobs.ArrayUtils.add((android.util.ArraySet<java.lang.Object>) arraySet, obj);
                }
            }
        }
        return emptyIfNull(arraySet);
    }

    public static <T> void addIf(@android.annotation.Nullable java.util.List<T> list, @android.annotation.NonNull java.util.Collection<? super T> collection, @android.annotation.Nullable java.util.function.Predicate<? super T> predicate) {
        for (int i = 0; i < size(list); i++) {
            T t = list.get(i);
            if (predicate.test(t)) {
                collection.add(t);
            }
        }
    }

    @android.annotation.NonNull
    public static <I, O> java.util.List<O> map(@android.annotation.Nullable java.util.List<I> list, java.util.function.Function<? super I, ? extends O> function) {
        if (isEmpty(list)) {
            return java.util.Collections.emptyList();
        }
        int size = list.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(function.apply(list.get(i)));
        }
        return arrayList;
    }

    @android.annotation.NonNull
    public static <I, O> java.util.Set<O> map(@android.annotation.Nullable java.util.Set<I> set, java.util.function.Function<? super I, ? extends O> function) {
        if (isEmpty(set)) {
            return java.util.Collections.emptySet();
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(set.size());
        if (set instanceof android.util.ArraySet) {
            android.util.ArraySet arraySet2 = (android.util.ArraySet) set;
            int size = arraySet2.size();
            for (int i = 0; i < size; i++) {
                arraySet.add(function.apply((java.lang.Object) arraySet2.valueAt(i)));
            }
        } else {
            java.util.Iterator<I> it = set.iterator();
            while (it.hasNext()) {
                arraySet.add(function.apply(it.next()));
            }
        }
        return arraySet;
    }

    @android.annotation.NonNull
    public static <I, O> java.util.List<O> mapNotNull(@android.annotation.Nullable java.util.List<I> list, java.util.function.Function<? super I, ? extends O> function) {
        if (isEmpty(list)) {
            return java.util.Collections.emptyList();
        }
        int size = list.size();
        java.util.List list2 = null;
        for (int i = 0; i < size; i++) {
            O apply = function.apply(list.get(i));
            if (apply != null) {
                list2 = add((java.util.List<O>) list2, apply);
            }
        }
        return emptyIfNull(list2);
    }

    @android.annotation.NonNull
    public static <T> java.util.List<T> emptyIfNull(@android.annotation.Nullable java.util.List<T> list) {
        return list == null ? java.util.Collections.emptyList() : list;
    }

    @android.annotation.NonNull
    public static <T> java.util.Set<T> emptyIfNull(@android.annotation.Nullable java.util.Set<T> set) {
        return set == null ? java.util.Collections.emptySet() : set;
    }

    @android.annotation.NonNull
    public static <K, V> java.util.Map<K, V> emptyIfNull(@android.annotation.Nullable java.util.Map<K, V> map) {
        return map == null ? java.util.Collections.emptyMap() : map;
    }

    public static int size(@android.annotation.Nullable java.util.Collection<?> collection) {
        if (collection != null) {
            return collection.size();
        }
        return 0;
    }

    public static int size(@android.annotation.Nullable java.util.Map<?, ?> map) {
        if (map != null) {
            return map.size();
        }
        return 0;
    }

    public static boolean isEmpty(@android.annotation.Nullable java.util.Collection<?> collection) {
        return size(collection) == 0;
    }

    public static boolean isEmpty(@android.annotation.Nullable java.util.Map<?, ?> map) {
        return size(map) == 0;
    }

    @android.annotation.NonNull
    public static <T> java.util.List<T> filter(@android.annotation.Nullable java.util.List<?> list, java.lang.Class<T> cls) {
        if (isEmpty(list)) {
            return java.util.Collections.emptyList();
        }
        int size = list.size();
        java.util.ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            java.lang.Object obj = list.get(i);
            if (cls.isInstance(obj)) {
                arrayList = com.android.internal.util.jobs.ArrayUtils.add((java.util.ArrayList<java.lang.Object>) arrayList, obj);
            }
        }
        return emptyIfNull(arrayList);
    }

    public static <T> boolean any(@android.annotation.Nullable java.util.List<T> list, java.util.function.Predicate<T> predicate) {
        return find(list, predicate) != null;
    }

    public static <T> boolean any(@android.annotation.Nullable java.util.Set<T> set, java.util.function.Predicate<T> predicate) {
        return find(set, predicate) != null;
    }

    @android.annotation.Nullable
    public static <T> T find(@android.annotation.Nullable java.util.List<T> list, java.util.function.Predicate<T> predicate) {
        if (isEmpty(list)) {
            return null;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            T t = list.get(i);
            if (predicate.test(t)) {
                return t;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    public static <T> T find(@android.annotation.Nullable java.util.Set<T> set, java.util.function.Predicate<T> predicate) {
        int size;
        if (set == null || predicate == null || (size = set.size()) == 0) {
            return null;
        }
        try {
            if (set instanceof android.util.ArraySet) {
                android.util.ArraySet arraySet = (android.util.ArraySet) set;
                for (int i = 0; i < size; i++) {
                    T t = (T) arraySet.valueAt(i);
                    if (predicate.test(t)) {
                        return t;
                    }
                }
            } else {
                for (T t2 : set) {
                    if (predicate.test(t2)) {
                        return t2;
                    }
                }
            }
            return null;
        } catch (java.lang.Exception e) {
            throw android.util.ExceptionUtils.propagate(e);
        }
    }

    @android.annotation.NonNull
    public static <T> java.util.List<T> add(@android.annotation.Nullable java.util.List<T> list, T t) {
        if (list == null || list == java.util.Collections.emptyList()) {
            list = new java.util.ArrayList<>();
        }
        list.add(t);
        return list;
    }

    @android.annotation.NonNull
    public static <T> java.util.List<T> add(@android.annotation.Nullable java.util.List<T> list, int i, T t) {
        if (list == null || list == java.util.Collections.emptyList()) {
            list = new java.util.ArrayList<>();
        }
        list.add(i, t);
        return list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.NonNull
    public static <T> java.util.Set<T> addAll(@android.annotation.Nullable java.util.Set<T> set, @android.annotation.Nullable java.util.Collection<T> collection) {
        if (isEmpty((java.util.Collection<?>) collection)) {
            return set != null ? set : java.util.Collections.emptySet();
        }
        if (set == null || set == java.util.Collections.emptySet()) {
            set = new android.util.ArraySet<>();
        }
        set.addAll(collection);
        return set;
    }

    @android.annotation.NonNull
    public static <T> java.util.Set<T> add(@android.annotation.Nullable java.util.Set<T> set, T t) {
        if (set == null || set == java.util.Collections.emptySet()) {
            set = new android.util.ArraySet<>();
        }
        set.add(t);
        return set;
    }

    @android.annotation.NonNull
    public static <K, V> java.util.Map<K, V> add(@android.annotation.Nullable java.util.Map<K, V> map, K k, V v) {
        if (map == null || map == java.util.Collections.emptyMap()) {
            map = new android.util.ArrayMap<>();
        }
        map.put(k, v);
        return map;
    }

    @android.annotation.NonNull
    public static <T> java.util.List<T> remove(@android.annotation.Nullable java.util.List<T> list, T t) {
        if (isEmpty(list)) {
            return emptyIfNull(list);
        }
        list.remove(t);
        return list;
    }

    @android.annotation.NonNull
    public static <T> java.util.Set<T> remove(@android.annotation.Nullable java.util.Set<T> set, T t) {
        if (isEmpty(set)) {
            return emptyIfNull(set);
        }
        set.remove(t);
        return set;
    }

    @android.annotation.NonNull
    public static <T> java.util.List<T> copyOf(@android.annotation.Nullable java.util.List<T> list) {
        return isEmpty(list) ? java.util.Collections.emptyList() : new java.util.ArrayList(list);
    }

    @android.annotation.NonNull
    public static <T> java.util.Set<T> copyOf(@android.annotation.Nullable java.util.Set<T> set) {
        return isEmpty(set) ? java.util.Collections.emptySet() : new android.util.ArraySet(set);
    }

    @android.annotation.NonNull
    public static <T> java.util.Set<T> toSet(@android.annotation.Nullable java.util.Collection<T> collection) {
        return isEmpty((java.util.Collection<?>) collection) ? java.util.Collections.emptySet() : new android.util.ArraySet(collection);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> void forEach(@android.annotation.Nullable java.util.Set<T> set, @android.annotation.Nullable com.android.internal.util.jobs.FunctionalUtils.ThrowingConsumer<T> throwingConsumer) {
        int size;
        if (set == null || throwingConsumer == 0 || (size = set.size()) == 0) {
            return;
        }
        try {
            if (set instanceof android.util.ArraySet) {
                android.util.ArraySet arraySet = (android.util.ArraySet) set;
                for (int i = 0; i < size; i++) {
                    throwingConsumer.acceptOrThrow(arraySet.valueAt(i));
                }
                return;
            }
            java.util.Iterator<T> it = set.iterator();
            while (it.hasNext()) {
                throwingConsumer.acceptOrThrow(it.next());
            }
        } catch (java.lang.Exception e) {
            throw android.util.ExceptionUtils.propagate(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> void forEach(@android.annotation.Nullable java.util.Map<K, V> map, @android.annotation.Nullable java.util.function.BiConsumer<K, V> biConsumer) {
        int size;
        if (map == null || biConsumer == 0 || (size = map.size()) == 0) {
            return;
        }
        if (map instanceof android.util.ArrayMap) {
            android.util.ArrayMap arrayMap = (android.util.ArrayMap) map;
            for (int i = 0; i < size; i++) {
                biConsumer.accept(arrayMap.keyAt(i), arrayMap.valueAt(i));
            }
            return;
        }
        for (K k : map.keySet()) {
            biConsumer.accept(k, map.get(k));
        }
    }

    @android.annotation.Nullable
    public static <T> T firstOrNull(@android.annotation.Nullable java.util.List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @android.annotation.Nullable
    public static <T> T firstOrNull(@android.annotation.Nullable java.util.Collection<T> collection) {
        if (isEmpty((java.util.Collection<?>) collection)) {
            return null;
        }
        return collection.iterator().next();
    }

    @android.annotation.NonNull
    public static <T> java.util.List<T> singletonOrEmpty(@android.annotation.Nullable T t) {
        return t == null ? java.util.Collections.emptyList() : java.util.Collections.singletonList(t);
    }
}
