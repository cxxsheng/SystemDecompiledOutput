package com.android.internal.view;

/* loaded from: classes5.dex */
public class ScrollCaptureInternal {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_VERBOSE = false;
    private static final int DOWN = 1;
    private static final java.lang.String TAG = "ScrollCaptureInternal";
    public static final int TYPE_FIXED = 0;
    private static final int TYPE_OPAQUE = 3;
    public static final int TYPE_RECYCLING = 2;
    public static final int TYPE_SCROLLING = 1;
    private static final int UP = -1;

    private static int detectScrollingType(android.view.View view) {
        if (!view.canScrollVertically(1) && !view.canScrollVertically(-1)) {
            return 0;
        }
        if (!(view instanceof android.view.ViewGroup)) {
            return 3;
        }
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
        if (viewGroup.getChildCount() > 1) {
            return 2;
        }
        if (viewGroup.getChildCount() < 1) {
            return 3;
        }
        if (view.getScrollY() != 0) {
            return 1;
        }
        android.util.Log.v(TAG, "hint: scrollY == 0");
        if (view.canScrollVertically(-1)) {
            return 2;
        }
        view.scrollTo(view.getScrollX(), 1);
        if (view.getScrollY() != 1) {
            return 2;
        }
        view.scrollTo(view.getScrollX(), 0);
        return 1;
    }

    public android.view.ScrollCaptureCallback requestCallback(android.view.View view, android.graphics.Rect rect, android.graphics.Point point) {
        switch (detectScrollingType(view)) {
            case 1:
                return new com.android.internal.view.ScrollCaptureViewSupport((android.view.ViewGroup) view, new com.android.internal.view.ScrollViewCaptureHelper());
            case 2:
                if (view instanceof android.widget.ListView) {
                    return new com.android.internal.view.ScrollCaptureViewSupport((android.widget.ListView) view, new com.android.internal.view.ListViewCaptureHelper());
                }
                return new com.android.internal.view.ScrollCaptureViewSupport((android.view.ViewGroup) view, new com.android.internal.view.RecyclerViewCaptureHelper());
            case 3:
                if (view instanceof android.webkit.WebView) {
                    android.util.Log.d(TAG, "scroll capture: Using WebView support");
                    return new com.android.internal.view.ScrollCaptureViewSupport((android.webkit.WebView) view, new com.android.internal.view.WebViewCaptureHelper());
                }
                return null;
            default:
                return null;
        }
    }

    private static java.lang.String formatIntToHexString(int i) {
        return "0x" + java.lang.Integer.toHexString(i).toUpperCase();
    }

    static java.lang.String resolveId(android.content.Context context, int i) {
        android.content.res.Resources resources = context.getResources();
        if (i >= 0) {
            try {
                return resources.getResourceTypeName(i) + '/' + resources.getResourceEntryName(i);
            } catch (android.content.res.Resources.NotFoundException e) {
                return "id/" + formatIntToHexString(i);
            }
        }
        return "NO_ID";
    }
}
