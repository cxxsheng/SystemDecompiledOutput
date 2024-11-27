package android.text.style;

/* loaded from: classes3.dex */
public class ImageSpan extends android.text.style.DynamicDrawableSpan {
    private android.net.Uri mContentUri;
    private android.content.Context mContext;
    private android.graphics.drawable.Drawable mDrawable;
    private int mResourceId;
    private java.lang.String mSource;

    @java.lang.Deprecated
    public ImageSpan(android.graphics.Bitmap bitmap) {
        this((android.content.Context) null, bitmap, 0);
    }

    @java.lang.Deprecated
    public ImageSpan(android.graphics.Bitmap bitmap, int i) {
        this((android.content.Context) null, bitmap, i);
    }

    public ImageSpan(android.content.Context context, android.graphics.Bitmap bitmap) {
        this(context, bitmap, 0);
    }

    public ImageSpan(android.content.Context context, android.graphics.Bitmap bitmap, int i) {
        super(i);
        android.graphics.drawable.BitmapDrawable bitmapDrawable;
        this.mContext = context;
        if (context != null) {
            bitmapDrawable = new android.graphics.drawable.BitmapDrawable(context.getResources(), bitmap);
        } else {
            bitmapDrawable = new android.graphics.drawable.BitmapDrawable(bitmap);
        }
        this.mDrawable = bitmapDrawable;
        int intrinsicWidth = this.mDrawable.getIntrinsicWidth();
        int intrinsicHeight = this.mDrawable.getIntrinsicHeight();
        this.mDrawable.setBounds(0, 0, intrinsicWidth <= 0 ? 0 : intrinsicWidth, intrinsicHeight <= 0 ? 0 : intrinsicHeight);
    }

    public ImageSpan(android.graphics.drawable.Drawable drawable) {
        this(drawable, 0);
    }

    public ImageSpan(android.graphics.drawable.Drawable drawable, int i) {
        super(i);
        this.mDrawable = drawable;
    }

    public ImageSpan(android.graphics.drawable.Drawable drawable, java.lang.String str) {
        this(drawable, str, 0);
    }

    public ImageSpan(android.graphics.drawable.Drawable drawable, java.lang.String str, int i) {
        super(i);
        this.mDrawable = drawable;
        this.mSource = str;
    }

    public ImageSpan(android.content.Context context, android.net.Uri uri) {
        this(context, uri, 0);
    }

    public ImageSpan(android.content.Context context, android.net.Uri uri, int i) {
        super(i);
        this.mContext = context;
        this.mContentUri = uri;
        this.mSource = uri.toString();
    }

    public ImageSpan(android.content.Context context, int i) {
        this(context, i, 0);
    }

    public ImageSpan(android.content.Context context, int i, int i2) {
        super(i2);
        this.mContext = context;
        this.mResourceId = i;
    }

    @Override // android.text.style.DynamicDrawableSpan
    public android.graphics.drawable.Drawable getDrawable() {
        android.graphics.drawable.Drawable drawable;
        if (this.mDrawable != null) {
            return this.mDrawable;
        }
        android.graphics.drawable.BitmapDrawable bitmapDrawable = null;
        if (this.mContentUri != null) {
            try {
                java.io.InputStream openInputStream = this.mContext.getContentResolver().openInputStream(this.mContentUri);
                android.graphics.drawable.BitmapDrawable bitmapDrawable2 = new android.graphics.drawable.BitmapDrawable(this.mContext.getResources(), android.graphics.BitmapFactory.decodeStream(openInputStream));
                try {
                    bitmapDrawable2.setBounds(0, 0, bitmapDrawable2.getIntrinsicWidth(), bitmapDrawable2.getIntrinsicHeight());
                    openInputStream.close();
                    return bitmapDrawable2;
                } catch (java.lang.Exception e) {
                    e = e;
                    bitmapDrawable = bitmapDrawable2;
                    android.util.Log.e("ImageSpan", "Failed to loaded content " + this.mContentUri, e);
                    return bitmapDrawable;
                }
            } catch (java.lang.Exception e2) {
                e = e2;
            }
        } else {
            try {
                drawable = this.mContext.getDrawable(this.mResourceId);
            } catch (java.lang.Exception e3) {
                drawable = null;
            }
            try {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            } catch (java.lang.Exception e4) {
                android.util.Log.e("ImageSpan", "Unable to find resource: " + this.mResourceId);
                return drawable;
            }
        }
    }

    public java.lang.String getSource() {
        return this.mSource;
    }

    @Override // android.text.style.DynamicDrawableSpan
    public java.lang.String toString() {
        return "ImageSpan{drawable=" + getDrawable() + ", source='" + getSource() + android.text.format.DateFormat.QUOTE + ", verticalAlignment=" + getVerticalAlignment() + '}';
    }
}
