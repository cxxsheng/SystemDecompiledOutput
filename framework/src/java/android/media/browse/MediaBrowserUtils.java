package android.media.browse;

/* loaded from: classes2.dex */
public class MediaBrowserUtils {
    public static boolean areSameOptions(android.os.Bundle bundle, android.os.Bundle bundle2) {
        if (bundle == bundle2) {
            return true;
        }
        if (bundle == null) {
            if (bundle2.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE, -1) == -1 && bundle2.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE_SIZE, -1) == -1) {
                return true;
            }
            return false;
        }
        if (bundle2 == null) {
            if (bundle.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE, -1) == -1 && bundle.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE_SIZE, -1) == -1) {
                return true;
            }
            return false;
        }
        if (bundle.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE, -1) == bundle2.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE, -1) && bundle.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE_SIZE, -1) == bundle2.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE_SIZE, -1)) {
            return true;
        }
        return false;
    }

    public static boolean hasDuplicatedItems(android.os.Bundle bundle, android.os.Bundle bundle2) {
        int i;
        int i2;
        int i3;
        int i4 = bundle == null ? -1 : bundle.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE, -1);
        int i5 = bundle2 == null ? -1 : bundle2.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE, -1);
        int i6 = bundle == null ? -1 : bundle.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE_SIZE, -1);
        int i7 = bundle2 == null ? -1 : bundle2.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE_SIZE, -1);
        int i8 = Integer.MAX_VALUE;
        if (i4 == -1 || i6 == -1) {
            i = Integer.MAX_VALUE;
            i2 = 0;
        } else {
            i2 = i4 * i6;
            i = (i6 + i2) - 1;
        }
        if (i5 == -1 || i7 == -1) {
            i3 = 0;
        } else {
            i3 = i5 * i7;
            i8 = (i7 + i3) - 1;
        }
        if (i2 > i3 || i3 > i) {
            return i2 <= i8 && i8 <= i;
        }
        return true;
    }

    public static java.util.List<android.media.browse.MediaBrowser.MediaItem> applyPagingOptions(java.util.List<android.media.browse.MediaBrowser.MediaItem> list, android.os.Bundle bundle) {
        if (list == null) {
            return null;
        }
        int i = bundle.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE, -1);
        int i2 = bundle.getInt(android.media.browse.MediaBrowser.EXTRA_PAGE_SIZE, -1);
        if (i == -1 && i2 == -1) {
            return list;
        }
        int i3 = i2 * i;
        int i4 = i3 + i2;
        if (i < 0 || i2 < 1 || i3 >= list.size()) {
            return java.util.Collections.EMPTY_LIST;
        }
        if (i4 > list.size()) {
            i4 = list.size();
        }
        return list.subList(i3, i4);
    }
}
