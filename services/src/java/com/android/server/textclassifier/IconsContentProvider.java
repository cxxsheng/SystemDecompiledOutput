package com.android.server.textclassifier;

/* loaded from: classes2.dex */
public final class IconsContentProvider extends android.content.ContentProvider {
    private static final java.lang.String MIME_TYPE = "image/png";
    private static final java.lang.String TAG = "IconsContentProvider";
    private final android.content.ContentProvider.PipeDataWriter<android.util.Pair<com.android.server.textclassifier.IconsUriHelper.ResourceInfo, java.lang.Integer>> mWriter = new android.content.ContentProvider.PipeDataWriter() { // from class: com.android.server.textclassifier.IconsContentProvider$$ExternalSyntheticLambda0
        @Override // android.content.ContentProvider.PipeDataWriter
        public final void writeDataToPipe(android.os.ParcelFileDescriptor parcelFileDescriptor, android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, java.lang.Object obj) {
            com.android.server.textclassifier.IconsContentProvider.this.lambda$new$0(parcelFileDescriptor, uri, str, bundle, (android.util.Pair) obj);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.os.ParcelFileDescriptor parcelFileDescriptor, android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.util.Pair pair) {
        try {
            android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
            try {
                com.android.server.textclassifier.IconsUriHelper.ResourceInfo resourceInfo = (com.android.server.textclassifier.IconsUriHelper.ResourceInfo) pair.first;
                getBitmap(android.graphics.drawable.Icon.createWithResource(resourceInfo.packageName, resourceInfo.id).loadDrawableAsUser(getContext(), ((java.lang.Integer) pair.second).intValue())).compress(android.graphics.Bitmap.CompressFormat.PNG, 100, autoCloseOutputStream);
                autoCloseOutputStream.close();
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error retrieving icon for uri: " + uri, e);
        }
    }

    @Override // android.content.ContentProvider
    public android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str) {
        com.android.server.textclassifier.IconsUriHelper.ResourceInfo resourceInfo = com.android.server.textclassifier.IconsUriHelper.getInstance().getResourceInfo(uri);
        if (resourceInfo == null) {
            android.util.Log.e(TAG, "No icon found for uri: " + uri);
            return null;
        }
        try {
            return openPipeHelper(uri, MIME_TYPE, null, new android.util.Pair(resourceInfo, java.lang.Integer.valueOf(android.os.UserHandle.getCallingUserId())), this.mWriter);
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Error opening pipe helper for icon at uri: " + uri, e);
            return null;
        }
    }

    private static android.graphics.Bitmap getBitmap(android.graphics.drawable.Drawable drawable) {
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            throw new java.lang.IllegalStateException("The icon is zero-sized");
        }
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static boolean sameIcon(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        getBitmap(drawable).compress(android.graphics.Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        java.io.ByteArrayOutputStream byteArrayOutputStream2 = new java.io.ByteArrayOutputStream();
        getBitmap(drawable2).compress(android.graphics.Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream2);
        return java.util.Arrays.equals(byteArrayOutputStream.toByteArray(), byteArrayOutputStream2.toByteArray());
    }

    @Override // android.content.ContentProvider
    public java.lang.String getType(android.net.Uri uri) {
        return MIME_TYPE;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int delete(android.net.Uri uri, java.lang.String str, java.lang.String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr) {
        return 0;
    }
}
