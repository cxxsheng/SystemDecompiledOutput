package com.android.internal.widget;

/* loaded from: classes5.dex */
public class LocalImageResolver {
    static final int DEFAULT_MAX_SAFE_ICON_SIZE_PX = 480;
    public static final int NO_MAX_SIZE = -1;
    private static final java.lang.String TAG = "LocalImageResolver";

    public static android.graphics.drawable.Drawable resolveImage(android.net.Uri uri, android.content.Context context) throws java.io.IOException {
        try {
            return android.graphics.ImageDecoder.decodeDrawable(android.graphics.ImageDecoder.createSource(context.getContentResolver(), uri), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.internal.widget.LocalImageResolver$$ExternalSyntheticLambda1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                    com.android.internal.widget.LocalImageResolver.onHeaderDecoded(imageDecoder, imageInfo, 480, 480);
                }
            });
        } catch (java.lang.Exception e) {
            throw new java.io.IOException(e);
        }
    }

    public static android.graphics.drawable.Drawable resolveImage(android.graphics.drawable.Icon icon, android.content.Context context) throws java.io.IOException {
        return resolveImage(icon, context, 480, 480);
    }

    public static android.graphics.drawable.Drawable resolveImage(android.graphics.drawable.Icon icon, android.content.Context context, int i, int i2) {
        android.graphics.drawable.Drawable resolveImage;
        if (icon == null) {
            return null;
        }
        switch (icon.getType()) {
            case 1:
            case 5:
                return resolveBitmapImage(icon, context, i, i2);
            case 2:
                android.content.res.Resources resolveResourcesForIcon = resolveResourcesForIcon(context, icon);
                if (resolveResourcesForIcon == null) {
                    return icon.loadDrawable(context);
                }
                android.graphics.drawable.Drawable resolveImage2 = resolveImage(resolveResourcesForIcon, icon.getResId(), i, i2);
                if (resolveImage2 != null) {
                    return tintDrawable(icon, resolveImage2);
                }
                break;
            case 4:
            case 6:
                android.net.Uri resolvableUri = getResolvableUri(icon);
                if (resolvableUri != null && (resolveImage = resolveImage(resolvableUri, context, i, i2)) != null) {
                    return tintDrawable(icon, resolveImage);
                }
                break;
        }
        try {
            return icon.loadDrawable(context);
        } catch (android.content.res.Resources.NotFoundException e) {
            return null;
        }
    }

    public static android.graphics.drawable.Drawable resolveImage(android.net.Uri uri, android.content.Context context, int i, int i2) {
        return resolveImage(android.graphics.ImageDecoder.createSource(context.getContentResolver(), uri), i, i2);
    }

    public static android.graphics.drawable.Drawable resolveImage(int i, android.content.Context context, int i2, int i3) {
        return resolveImage(android.graphics.ImageDecoder.createSource(context.getResources(), i), i2, i3);
    }

    private static android.graphics.drawable.Drawable resolveImage(android.content.res.Resources resources, int i, int i2, int i3) {
        return resolveImage(android.graphics.ImageDecoder.createSource(resources, i), i2, i3);
    }

    private static android.graphics.drawable.Drawable resolveBitmapImage(android.graphics.drawable.Icon icon, android.content.Context context, int i, int i2) {
        if (i > 0 && i2 > 0) {
            android.graphics.Bitmap bitmap = icon.getBitmap();
            if (bitmap == null) {
                return null;
            }
            if (bitmap.getWidth() > i || bitmap.getHeight() > i2) {
                android.graphics.drawable.Icon createWithAdaptiveBitmap = icon.getType() == 5 ? android.graphics.drawable.Icon.createWithAdaptiveBitmap(bitmap) : android.graphics.drawable.Icon.createWithBitmap(bitmap);
                createWithAdaptiveBitmap.setTintList(icon.getTintList()).setTintBlendMode(icon.getTintBlendMode()).scaleDownIfNecessary(i, i2);
                return createWithAdaptiveBitmap.loadDrawable(context);
            }
        }
        return icon.loadDrawable(context);
    }

    private static android.graphics.drawable.Drawable tintDrawable(android.graphics.drawable.Icon icon, android.graphics.drawable.Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (icon.hasTint()) {
            drawable.mutate();
            drawable.setTintList(icon.getTintList());
            drawable.setTintBlendMode(icon.getTintBlendMode());
        }
        return drawable;
    }

    private static android.graphics.drawable.Drawable resolveImage(android.graphics.ImageDecoder.Source source, final int i, final int i2) {
        try {
            return android.graphics.ImageDecoder.decodeDrawable(source, new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.internal.widget.LocalImageResolver$$ExternalSyntheticLambda0
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source2) {
                    com.android.internal.widget.LocalImageResolver.lambda$resolveImage$1(i, i2, imageDecoder, imageInfo, source2);
                }
            });
        } catch (android.content.res.Resources.NotFoundException | java.io.IOException e) {
            android.util.Log.d(TAG, "Couldn't use ImageDecoder for drawable, falling back to non-resized load.");
            return null;
        }
    }

    static /* synthetic */ void lambda$resolveImage$1(int i, int i2, android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        android.util.Size size = imageInfo.getSize();
        if (size.getWidth() <= i && size.getHeight() <= i2) {
            return;
        }
        if (size.getWidth() > size.getHeight()) {
            if (size.getWidth() > i) {
                imageDecoder.setTargetSize(i, (size.getHeight() * i) / size.getWidth());
            }
        } else if (size.getHeight() > i2) {
            imageDecoder.setTargetSize((size.getWidth() * i2) / size.getHeight(), i2);
        }
    }

    private static int getPowerOfTwoForSampleRatio(double d) {
        return java.lang.Math.max(1, java.lang.Integer.highestOneBit((int) java.lang.Math.floor(d)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, int i, int i2) {
        double d;
        android.util.Size size = imageInfo.getSize();
        int max = java.lang.Math.max(size.getHeight(), size.getWidth());
        int max2 = java.lang.Math.max(i, i2);
        if (max > max2) {
            d = (max * 1.0f) / max2;
        } else {
            d = 1.0d;
        }
        imageDecoder.setTargetSampleSize(getPowerOfTwoForSampleRatio(d));
    }

    private static android.net.Uri getResolvableUri(android.graphics.drawable.Icon icon) {
        if (icon != null) {
            if (icon.getType() != 4 && icon.getType() != 6) {
                return null;
            }
            return icon.getUri();
        }
        return null;
    }

    public static android.content.res.Resources resolveResourcesForIcon(android.content.Context context, android.graphics.drawable.Icon icon) {
        if (icon.getType() != 2) {
            return null;
        }
        android.content.res.Resources resources = icon.getResources();
        if (resources != null) {
            return resources;
        }
        java.lang.String resPackage = icon.getResPackage();
        if (android.text.TextUtils.isEmpty(resPackage) || context.getPackageName().equals(resPackage)) {
            return context.getResources();
        }
        if ("android".equals(resPackage)) {
            return android.content.res.Resources.getSystem();
        }
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        try {
            android.content.pm.ApplicationInfo applicationInfo = packageManager.getApplicationInfo(resPackage, 9216);
            if (applicationInfo != null) {
                return packageManager.getResourcesForApplication(applicationInfo);
            }
            return null;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, java.lang.String.format("Unable to resolve package %s for icon %s", resPackage, icon));
            return null;
        }
    }
}
