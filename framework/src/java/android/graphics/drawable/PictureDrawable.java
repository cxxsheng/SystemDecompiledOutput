package android.graphics.drawable;

/* loaded from: classes.dex */
public class PictureDrawable extends android.graphics.drawable.Drawable {
    private android.graphics.Picture mPicture;

    public PictureDrawable(android.graphics.Picture picture) {
        this.mPicture = picture;
    }

    public android.graphics.Picture getPicture() {
        return this.mPicture;
    }

    public void setPicture(android.graphics.Picture picture) {
        this.mPicture = picture;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        if (this.mPicture != null) {
            android.graphics.Rect bounds = getBounds();
            canvas.save();
            canvas.clipRect(bounds);
            canvas.translate(bounds.left, bounds.top);
            canvas.drawPicture(this.mPicture);
            canvas.restore();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        if (this.mPicture != null) {
            return this.mPicture.getWidth();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        if (this.mPicture != null) {
            return this.mPicture.getHeight();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }
}
