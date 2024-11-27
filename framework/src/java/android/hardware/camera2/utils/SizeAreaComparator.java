package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class SizeAreaComparator implements java.util.Comparator<android.util.Size> {
    @Override // java.util.Comparator
    public int compare(android.util.Size size, android.util.Size size2) {
        com.android.internal.util.Preconditions.checkNotNull(size, "size must not be null");
        com.android.internal.util.Preconditions.checkNotNull(size2, "size2 must not be null");
        if (size.equals(size2)) {
            return 0;
        }
        long width = size.getWidth();
        long width2 = size2.getWidth();
        long height = size.getHeight() * width;
        long height2 = size2.getHeight() * width2;
        return height == height2 ? width > width2 ? 1 : -1 : height > height2 ? 1 : -1;
    }

    public static android.util.Size findLargestByArea(java.util.List<android.util.Size> list) {
        com.android.internal.util.Preconditions.checkNotNull(list, "sizes must not be null");
        return (android.util.Size) java.util.Collections.max(list, new android.hardware.camera2.utils.SizeAreaComparator());
    }
}
