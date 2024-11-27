package android.content;

/* loaded from: classes.dex */
public class ContentUris {
    public static long parseId(android.net.Uri uri) {
        java.lang.String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment == null) {
            return -1L;
        }
        return java.lang.Long.parseLong(lastPathSegment);
    }

    public static android.net.Uri.Builder appendId(android.net.Uri.Builder builder, long j) {
        return builder.appendEncodedPath(java.lang.String.valueOf(j));
    }

    public static android.net.Uri withAppendedId(android.net.Uri uri, long j) {
        return appendId(uri.buildUpon(), j).build();
    }

    public static android.net.Uri removeId(android.net.Uri uri) {
        java.lang.String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment == null) {
            throw new java.lang.IllegalArgumentException("No path segments to remove");
        }
        java.lang.Long.parseLong(lastPathSegment);
        java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
        android.net.Uri.Builder buildUpon = uri.buildUpon();
        buildUpon.path((java.lang.String) null);
        for (int i = 0; i < pathSegments.size() - 1; i++) {
            buildUpon.appendPath(pathSegments.get(i));
        }
        return buildUpon.build();
    }
}
