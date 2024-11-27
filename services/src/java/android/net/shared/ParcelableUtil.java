package android.net.shared;

/* loaded from: classes.dex */
public final class ParcelableUtil {
    public static <ParcelableType, BaseType> ParcelableType[] toParcelableArray(@android.annotation.NonNull java.util.Collection<BaseType> collection, @android.annotation.NonNull java.util.function.Function<BaseType, ParcelableType> function, @android.annotation.NonNull java.lang.Class<ParcelableType> cls) {
        ParcelableType[] parcelabletypeArr = (ParcelableType[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, collection.size()));
        java.util.Iterator<BaseType> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            parcelabletypeArr[i] = function.apply(it.next());
            i++;
        }
        return parcelabletypeArr;
    }

    public static <ParcelableType, BaseType> java.util.ArrayList<BaseType> fromParcelableArray(@android.annotation.NonNull ParcelableType[] parcelabletypeArr, @android.annotation.NonNull java.util.function.Function<ParcelableType, BaseType> function) {
        java.util.ArrayList<BaseType> arrayList = new java.util.ArrayList<>(parcelabletypeArr.length);
        for (ParcelableType parcelabletype : parcelabletypeArr) {
            arrayList.add(function.apply(parcelabletype));
        }
        return arrayList;
    }
}
