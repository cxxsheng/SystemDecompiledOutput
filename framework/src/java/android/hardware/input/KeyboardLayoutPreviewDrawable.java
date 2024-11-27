package android.hardware.input;

/* loaded from: classes2.dex */
final class KeyboardLayoutPreviewDrawable extends android.graphics.drawable.Drawable {
    private static final int GRAVITY_BOTTOM = 8;
    private static final int GRAVITY_LEFT = 1;
    private static final int GRAVITY_RIGHT = 2;
    private static final int GRAVITY_TOP = 4;
    private static final int KEYBOARD_PADDING_IN_DP = 10;
    private static final int KEYBOARD_RADIUS_IN_DP = 10;
    private static final int KEY_PADDING_IN_DP = 3;
    private static final int KEY_RADIUS_IN_DP = 5;
    private static final int MAX_GLYPH_TEXT_SIZE_IN_SP = 20;
    private static final int MIN_GLYPH_TEXT_SIZE_IN_SP = 10;
    private static final java.lang.String TAG = "KeyboardLayoutPreview";
    private static final int TEXT_PADDING_IN_DP = 1;
    private final int mHeight;
    private final android.hardware.input.PhysicalKeyLayout mKeyLayout;
    private final android.hardware.input.KeyboardLayoutPreviewDrawable.ResourceProvider mResourceProvider;
    private final int mWidth;
    private final java.util.List<android.hardware.input.KeyboardLayoutPreviewDrawable.KeyDrawable> mKeyDrawables = new java.util.ArrayList();
    private final android.graphics.RectF mKeyboardBackground = new android.graphics.RectF();

    private interface KeyDrawable {
        void draw(android.graphics.Canvas canvas);
    }

    public KeyboardLayoutPreviewDrawable(android.content.Context context, android.hardware.input.PhysicalKeyLayout physicalKeyLayout, int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mResourceProvider = new android.hardware.input.KeyboardLayoutPreviewDrawable.ResourceProvider(context);
        this.mKeyLayout = physicalKeyLayout;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mHeight;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        int i;
        int i2;
        android.hardware.input.PhysicalKeyLayout.LayoutKey[] layoutKeyArr;
        int i3;
        int i4;
        float f;
        int i5;
        super.onBoundsChange(rect);
        this.mKeyDrawables.clear();
        android.hardware.input.PhysicalKeyLayout.LayoutKey[][] keys = this.mKeyLayout.getKeys();
        if (keys == null) {
            return;
        }
        android.hardware.input.PhysicalKeyLayout.EnterKey enterKey = this.mKeyLayout.getEnterKey();
        int width = rect.width();
        int height = rect.height();
        int keyboardPadding = this.mResourceProvider.getKeyboardPadding();
        int keyPadding = this.mResourceProvider.getKeyPadding();
        float keyRadius = this.mResourceProvider.getKeyRadius();
        this.mKeyboardBackground.set(0.0f, 0.0f, width, height);
        int i6 = keyboardPadding * 2;
        int i7 = width - i6;
        int i8 = height - i6;
        if (i7 <= 0 || i8 <= 0) {
            android.util.Slog.e(TAG, "Invalid width and height to draw layout preview, width = " + i7 + ", height = " + i8);
            return;
        }
        int length = keys.length;
        float f2 = (i8 - ((length * 2) * keyPadding)) / length;
        this.mResourceProvider.calculateBestTextSizeForKey(f2);
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        int i9 = 0;
        while (i9 < length) {
            android.hardware.input.PhysicalKeyLayout.LayoutKey[] layoutKeyArr2 = keys[i9];
            int length2 = layoutKeyArr2.length;
            android.hardware.input.PhysicalKeyLayout.LayoutKey[][] layoutKeyArr3 = keys;
            float f6 = 0.0f;
            for (android.hardware.input.PhysicalKeyLayout.LayoutKey layoutKey : layoutKeyArr2) {
                f6 += layoutKey.keyWeight();
            }
            float f7 = (i7 - ((length2 * 2) * keyPadding)) / f6;
            int i10 = length;
            float f8 = (((i9 * 2) + 1) * keyPadding) + keyboardPadding + (i9 * f2);
            float f9 = f3;
            float f10 = f4;
            float f11 = f5;
            int i11 = 0;
            float f12 = 0.0f;
            while (i11 < length2) {
                float f13 = (((i11 * 2) + 1) * keyPadding) + keyboardPadding + (f12 * f7);
                float keyWeight = f12 + layoutKeyArr2[i11].keyWeight();
                float f14 = f7;
                android.graphics.RectF rectF = new android.graphics.RectF(f13, f8, (layoutKeyArr2[i11].keyWeight() * f7) + f13, f8 + f2);
                if (enterKey != null && layoutKeyArr2[i11].keyCode() == 66) {
                    if (enterKey.row() != i9 || enterKey.column() != i11) {
                        i = keyboardPadding;
                        i2 = length2;
                        layoutKeyArr = layoutKeyArr2;
                        i3 = i9;
                        i4 = i11;
                        f = f8;
                        i5 = i10;
                    } else {
                        f10 = rectF.left;
                        i = keyboardPadding;
                        f11 = rectF.top;
                        i2 = length2;
                        layoutKeyArr = layoutKeyArr2;
                        i3 = i9;
                        i4 = i11;
                        f = f8;
                        i5 = i10;
                        f9 = f14;
                    }
                } else if (android.hardware.input.PhysicalKeyLayout.isSpecialKey(layoutKeyArr2[i11])) {
                    i = keyboardPadding;
                    i2 = length2;
                    layoutKeyArr = layoutKeyArr2;
                    i3 = i9;
                    i4 = i11;
                    i5 = i10;
                    f = f8;
                    this.mKeyDrawables.add(new android.hardware.input.KeyboardLayoutPreviewDrawable.TypingKey(null, rectF, keyRadius, this.mResourceProvider.getTextPadding(), this.mResourceProvider.getSpecialKeyPaint(), this.mResourceProvider.getSpecialKeyPaint(), this.mResourceProvider.getSpecialKeyPaint()));
                } else {
                    i = keyboardPadding;
                    i2 = length2;
                    layoutKeyArr = layoutKeyArr2;
                    i3 = i9;
                    i4 = i11;
                    f = f8;
                    i5 = i10;
                    if (android.hardware.input.PhysicalKeyLayout.isKeyPositionUnsure(layoutKeyArr[i4])) {
                        this.mKeyDrawables.add(new android.hardware.input.KeyboardLayoutPreviewDrawable.UnsureTypingKey(layoutKeyArr[i4].glyph(), rectF, keyRadius, this.mResourceProvider.getTextPadding(), this.mResourceProvider.getTypingKeyPaint(), this.mResourceProvider.getPrimaryGlyphPaint(), this.mResourceProvider.getSecondaryGlyphPaint()));
                    } else {
                        this.mKeyDrawables.add(new android.hardware.input.KeyboardLayoutPreviewDrawable.TypingKey(layoutKeyArr[i4].glyph(), rectF, keyRadius, this.mResourceProvider.getTextPadding(), this.mResourceProvider.getTypingKeyPaint(), this.mResourceProvider.getPrimaryGlyphPaint(), this.mResourceProvider.getSecondaryGlyphPaint()));
                    }
                }
                i11 = i4 + 1;
                f12 = keyWeight;
                f7 = f14;
                i9 = i3;
                i10 = i5;
                f8 = f;
                length2 = i2;
                keyboardPadding = i;
                layoutKeyArr2 = layoutKeyArr;
            }
            i9++;
            keys = layoutKeyArr3;
            f3 = f9;
            f4 = f10;
            f5 = f11;
            length = i10;
        }
        if (enterKey != null) {
            android.hardware.input.KeyboardLayoutPreviewDrawable.IsoEnterKey.Builder builder = new android.hardware.input.KeyboardLayoutPreviewDrawable.IsoEnterKey.Builder(keyRadius, this.mResourceProvider.getSpecialKeyPaint());
            builder.setTopWidth(enterKey.topKeyWeight() * f3).setStartPoint(f4, f5).setVerticalEdges(f2, (keyPadding + f2) * 2.0f).setBottomWidth(enterKey.bottomKeyWeight() * f3);
            this.mKeyDrawables.add(builder.build());
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        float backgroundRadius = this.mResourceProvider.getBackgroundRadius();
        canvas.drawRoundRect(this.mKeyboardBackground, backgroundRadius, backgroundRadius, this.mResourceProvider.getBackgroundPaint());
        java.util.Iterator<android.hardware.input.KeyboardLayoutPreviewDrawable.KeyDrawable> it = this.mKeyDrawables.iterator();
        while (it.hasNext()) {
            it.next().draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -1;
    }

    private static class TypingKey implements android.hardware.input.KeyboardLayoutPreviewDrawable.KeyDrawable {
        private final android.graphics.Paint mBaseTextPaint;
        private final java.util.List<android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable> mGlyphDrawables;
        private final android.graphics.Paint mKeyPaint;
        private final float mKeyRadius;
        private final android.graphics.RectF mKeyRect;
        private final android.graphics.Paint mModifierTextPaint;
        private final float mTextPadding;

        private TypingKey(android.hardware.input.PhysicalKeyLayout.KeyGlyph keyGlyph, android.graphics.RectF rectF, float f, float f2, android.graphics.Paint paint, android.graphics.Paint paint2, android.graphics.Paint paint3) {
            this.mGlyphDrawables = new java.util.ArrayList();
            this.mKeyRect = rectF;
            this.mKeyRadius = f;
            this.mTextPadding = f2;
            this.mKeyPaint = paint;
            this.mBaseTextPaint = paint2;
            this.mModifierTextPaint = paint3;
            initGlyphs(keyGlyph);
        }

        private void initGlyphs(android.hardware.input.PhysicalKeyLayout.KeyGlyph keyGlyph) {
            createGlyphs(keyGlyph);
            measureGlyphs();
        }

        private void createGlyphs(android.hardware.input.PhysicalKeyLayout.KeyGlyph keyGlyph) {
            if (keyGlyph == null || !keyGlyph.hasBaseText()) {
                return;
            }
            this.mGlyphDrawables.add(new android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable(keyGlyph.getBaseText(), new android.graphics.RectF(), 9, this.mBaseTextPaint));
            if (keyGlyph.hasValidShiftText()) {
                this.mGlyphDrawables.add(new android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable(keyGlyph.getShiftText(), new android.graphics.RectF(), 5, this.mModifierTextPaint));
            }
            if (keyGlyph.hasValidAltGrText()) {
                this.mGlyphDrawables.add(new android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable(keyGlyph.getAltGrText(), new android.graphics.RectF(), 10, this.mModifierTextPaint));
            }
            if (keyGlyph.hasValidAltGrShiftText()) {
                this.mGlyphDrawables.add(new android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable(keyGlyph.getAltGrShiftText(), new android.graphics.RectF(), 6, this.mModifierTextPaint));
            }
        }

        private void measureGlyphs() {
            float width = this.mKeyRect.width();
            float height = this.mKeyRect.height();
            for (android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable glyphDrawable : this.mGlyphDrawables) {
                float f = width / 2.0f;
                float f2 = height / 2.0f;
                if ((glyphDrawable.gravity & 1) != 0) {
                    f = (f - (width / 4.0f)) + (this.mTextPadding / 2.0f);
                }
                if ((glyphDrawable.gravity & 2) != 0) {
                    f = (f + (width / 4.0f)) - (this.mTextPadding / 2.0f);
                }
                if ((glyphDrawable.gravity & 4) != 0) {
                    f2 = (f2 - (height / 4.0f)) + (this.mTextPadding / 2.0f);
                }
                if ((glyphDrawable.gravity & 8) != 0) {
                    f2 = (f2 + (height / 4.0f)) - (this.mTextPadding / 2.0f);
                }
                android.graphics.Rect rect = new android.graphics.Rect();
                glyphDrawable.paint.getTextBounds(glyphDrawable.text, 0, glyphDrawable.text.length(), rect);
                float width2 = rect.width() / 2.0f;
                float height2 = rect.height() / 2.0f;
                glyphDrawable.rect.set(f - width2, (f2 - height2) - rect.top, f + width2, (f2 + height2) - rect.top);
            }
        }

        @Override // android.hardware.input.KeyboardLayoutPreviewDrawable.KeyDrawable
        public void draw(android.graphics.Canvas canvas) {
            canvas.drawRoundRect(this.mKeyRect, this.mKeyRadius, this.mKeyRadius, this.mKeyPaint);
            for (android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable glyphDrawable : this.mGlyphDrawables) {
                float width = glyphDrawable.rect.width();
                float height = glyphDrawable.rect.height();
                float width2 = this.mKeyRect.width();
                float height2 = this.mKeyRect.height();
                if (width == 0.0f || height == 0.0f || width2 == 0.0f || height2 == 0.0f) {
                    return;
                } else {
                    canvas.drawText(glyphDrawable.text, 0, glyphDrawable.text.length(), this.mKeyRect.left + glyphDrawable.rect.left, this.mKeyRect.top + glyphDrawable.rect.top, glyphDrawable.paint);
                }
            }
        }
    }

    private static class UnsureTypingKey extends android.hardware.input.KeyboardLayoutPreviewDrawable.TypingKey {
        private UnsureTypingKey(android.hardware.input.PhysicalKeyLayout.KeyGlyph keyGlyph, android.graphics.RectF rectF, float f, float f2, android.graphics.Paint paint, android.graphics.Paint paint2, android.graphics.Paint paint3) {
            super(keyGlyph, rectF, f, f2, android.hardware.input.KeyboardLayoutPreviewDrawable.createGreyedOutPaint(paint), android.hardware.input.KeyboardLayoutPreviewDrawable.createGreyedOutPaint(paint2), android.hardware.input.KeyboardLayoutPreviewDrawable.createGreyedOutPaint(paint3));
        }
    }

    private static class IsoEnterKey implements android.hardware.input.KeyboardLayoutPreviewDrawable.KeyDrawable {
        private final android.graphics.Paint mKeyPaint;
        private final android.graphics.Path mPath;

        private IsoEnterKey(android.graphics.Paint paint, android.graphics.Path path) {
            this.mKeyPaint = paint;
            this.mPath = path;
        }

        @Override // android.hardware.input.KeyboardLayoutPreviewDrawable.KeyDrawable
        public void draw(android.graphics.Canvas canvas) {
            canvas.drawPath(this.mPath, this.mKeyPaint);
        }

        private static class Builder {
            private float mBottomWidth;
            private final android.graphics.Paint mKeyPaint;
            private final float mKeyRadius;
            private float mLeft;
            private float mLeftHeight;
            private float mRightHeight;
            private float mTop;
            private float mTopWidth;

            private Builder(float f, android.graphics.Paint paint) {
                this.mKeyRadius = f;
                this.mKeyPaint = paint;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public android.hardware.input.KeyboardLayoutPreviewDrawable.IsoEnterKey.Builder setStartPoint(float f, float f2) {
                this.mLeft = f;
                this.mTop = f2;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public android.hardware.input.KeyboardLayoutPreviewDrawable.IsoEnterKey.Builder setTopWidth(float f) {
                this.mTopWidth = f;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public android.hardware.input.KeyboardLayoutPreviewDrawable.IsoEnterKey.Builder setBottomWidth(float f) {
                this.mBottomWidth = f;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public android.hardware.input.KeyboardLayoutPreviewDrawable.IsoEnterKey.Builder setVerticalEdges(float f, float f2) {
                this.mLeftHeight = f;
                this.mRightHeight = f2;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public android.hardware.input.KeyboardLayoutPreviewDrawable.IsoEnterKey build() {
                android.graphics.Path path = new android.graphics.Path();
                android.graphics.RectF rectF = new android.graphics.RectF(-this.mKeyRadius, -this.mKeyRadius, this.mKeyRadius, this.mKeyRadius);
                path.moveTo(this.mLeft + this.mKeyRadius, this.mTop);
                path.lineTo((this.mLeft + this.mTopWidth) - this.mKeyRadius, this.mTop);
                rectF.offsetTo((this.mLeft + this.mTopWidth) - (this.mKeyRadius * 2.0f), this.mTop);
                path.arcTo(rectF, 270.0f, 90.0f);
                path.lineTo(this.mLeft + this.mTopWidth, (this.mTop + this.mRightHeight) - this.mKeyRadius);
                rectF.offsetTo((this.mLeft + this.mTopWidth) - (this.mKeyRadius * 2.0f), (this.mTop + this.mRightHeight) - (this.mKeyRadius * 2.0f));
                path.arcTo(rectF, 0.0f, 90.0f);
                path.lineTo(((this.mLeft + this.mTopWidth) - this.mBottomWidth) + this.mKeyRadius, this.mTop + this.mRightHeight);
                rectF.offsetTo((this.mLeft + this.mTopWidth) - this.mBottomWidth, (this.mTop + this.mRightHeight) - (this.mKeyRadius * 2.0f));
                path.arcTo(rectF, 90.0f, 90.0f);
                path.lineTo((this.mLeft + this.mTopWidth) - this.mBottomWidth, (this.mTop + this.mLeftHeight) - this.mKeyRadius);
                rectF.offsetTo(((this.mLeft + this.mTopWidth) - this.mBottomWidth) - (this.mKeyRadius * 2.0f), this.mTop + this.mLeftHeight);
                path.arcTo(rectF, 0.0f, -90.0f);
                path.lineTo(this.mLeft + this.mKeyRadius, this.mTop + this.mLeftHeight);
                rectF.offsetTo(this.mLeft, (this.mTop + this.mLeftHeight) - (this.mKeyRadius * 2.0f));
                path.arcTo(rectF, 90.0f, 90.0f);
                path.lineTo(this.mLeft, this.mTop + this.mKeyRadius);
                rectF.offsetTo(this.mLeft, this.mTop);
                path.arcTo(rectF, 180.0f, 90.0f);
                path.close();
                return new android.hardware.input.KeyboardLayoutPreviewDrawable.IsoEnterKey(this.mKeyPaint, path);
            }
        }
    }

    private static final class GlyphDrawable extends java.lang.Record {
        private final int gravity;
        private final android.graphics.Paint paint;
        private final android.graphics.RectF rect;
        private final java.lang.String text;

        private GlyphDrawable(java.lang.String text, android.graphics.RectF rect, int gravity, android.graphics.Paint paint) {
            this.text = text;
            this.rect = rect;
            this.gravity = gravity;
            this.paint = paint;
        }

        @Override // java.lang.Record
        public final boolean equals(java.lang.Object obj) {
            return (boolean) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "equals", java.lang.invoke.MethodType.methodType(java.lang.Boolean.TYPE, android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable.class, java.lang.Object.class), android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable.class, "text;rect;gravity;paint", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->text:Ljava/lang/String;", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->rect:Landroid/graphics/RectF;", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->gravity:I", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->paint:Landroid/graphics/Paint;").dynamicInvoker().invoke(this, obj) /* invoke-custom */;
        }

        public int gravity() {
            return this.gravity;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "hashCode", java.lang.invoke.MethodType.methodType(java.lang.Integer.TYPE, android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable.class), android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable.class, "text;rect;gravity;paint", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->text:Ljava/lang/String;", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->rect:Landroid/graphics/RectF;", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->gravity:I", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->paint:Landroid/graphics/Paint;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public android.graphics.Paint paint() {
            return this.paint;
        }

        public android.graphics.RectF rect() {
            return this.rect;
        }

        public java.lang.String text() {
            return this.text;
        }

        @Override // java.lang.Record
        public final java.lang.String toString() {
            return (java.lang.String) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "toString", java.lang.invoke.MethodType.methodType(java.lang.String.class, android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable.class), android.hardware.input.KeyboardLayoutPreviewDrawable.GlyphDrawable.class, "text;rect;gravity;paint", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->text:Ljava/lang/String;", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->rect:Landroid/graphics/RectF;", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->gravity:I", "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->paint:Landroid/graphics/Paint;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }
    }

    private static class ResourceProvider {
        private final android.graphics.Paint mBackgroundPaint;
        private final float mBackgroundRadius;
        private final android.graphics.Paint.FontMetrics mFontMetrics;
        private final int mKeyPadding;
        private final float mKeyRadius;
        private final int mKeyboardPadding;
        private final android.graphics.Paint mPrimaryGlyphPaint;
        private final android.graphics.Paint mSecondaryGlyphPaint;
        private final float mSpToPxMultiplier;
        private final android.graphics.Paint mSpecialKeyPaint;
        private final float mTextPadding;
        private final android.graphics.Paint mTypingKeyPaint;

        private ResourceProvider(android.content.Context context) {
            this.mKeyPadding = (int) android.util.TypedValue.applyDimension(1, 3.0f, context.getResources().getDisplayMetrics());
            this.mKeyboardPadding = (int) android.util.TypedValue.applyDimension(1, 10.0f, context.getResources().getDisplayMetrics());
            this.mKeyRadius = (int) android.util.TypedValue.applyDimension(1, 5.0f, context.getResources().getDisplayMetrics());
            this.mBackgroundRadius = (int) android.util.TypedValue.applyDimension(1, 10.0f, context.getResources().getDisplayMetrics());
            this.mSpToPxMultiplier = android.util.TypedValue.applyDimension(2, 1.0f, context.getResources().getDisplayMetrics());
            this.mTextPadding = android.util.TypedValue.applyDimension(1, 1.0f, context.getResources().getDisplayMetrics());
            boolean z = (context.getResources().getConfiguration().uiMode & 48) == 32;
            int color = context.getColor(z ? 17170625 : 17170543);
            int color2 = context.getColor(z ? 17170471 : 17170530);
            int color3 = context.getColor(z ? 17170584 : 17170541);
            int color4 = context.getColor(z ? 17170594 : 17170551);
            int color5 = context.getColor(z ? 17170587 : 17170544);
            this.mPrimaryGlyphPaint = android.hardware.input.KeyboardLayoutPreviewDrawable.createTextPaint(color3, this.mSpToPxMultiplier * 10.0f, android.graphics.Typeface.create(android.graphics.Typeface.SANS_SERIF, 1));
            this.mSecondaryGlyphPaint = android.hardware.input.KeyboardLayoutPreviewDrawable.createTextPaint(color4, this.mSpToPxMultiplier * 10.0f, android.graphics.Typeface.create(android.graphics.Typeface.SANS_SERIF, 0));
            this.mFontMetrics = this.mPrimaryGlyphPaint.getFontMetrics();
            this.mTypingKeyPaint = android.hardware.input.KeyboardLayoutPreviewDrawable.createFillPaint(color);
            this.mSpecialKeyPaint = android.hardware.input.KeyboardLayoutPreviewDrawable.createFillPaint(color2);
            this.mBackgroundPaint = android.hardware.input.KeyboardLayoutPreviewDrawable.createFillPaint(color5);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void calculateBestTextSizeForKey(float f) {
            int i = (int) (this.mSpToPxMultiplier * 10.0f);
            while (true) {
                i++;
                float f2 = i;
                if (f2 >= this.mSpToPxMultiplier * 20.0f) {
                    break;
                }
                updateTextSize(f2);
                if ((this.mFontMetrics.bottom - this.mFontMetrics.top) + (this.mTextPadding * 3.0f) > f / 2.0f) {
                    i--;
                    break;
                }
            }
            updateTextSize(i);
        }

        private void updateTextSize(float f) {
            this.mPrimaryGlyphPaint.setTextSize(f);
            this.mSecondaryGlyphPaint.setTextSize(f);
            this.mPrimaryGlyphPaint.getFontMetrics(this.mFontMetrics);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.graphics.Paint getBackgroundPaint() {
            return this.mBackgroundPaint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.graphics.Paint getTypingKeyPaint() {
            return this.mTypingKeyPaint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.graphics.Paint getSpecialKeyPaint() {
            return this.mSpecialKeyPaint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.graphics.Paint getPrimaryGlyphPaint() {
            return this.mPrimaryGlyphPaint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.graphics.Paint getSecondaryGlyphPaint() {
            return this.mSecondaryGlyphPaint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getKeyPadding() {
            return this.mKeyPadding;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getKeyboardPadding() {
            return this.mKeyboardPadding;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getTextPadding() {
            return this.mTextPadding;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getKeyRadius() {
            return this.mKeyRadius;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getBackgroundRadius() {
            return this.mBackgroundRadius;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.Paint createTextPaint(int i, float f, android.graphics.Typeface typeface) {
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setColor(i);
        paint.setStyle(android.graphics.Paint.Style.FILL);
        paint.setTextSize(f);
        paint.setTypeface(typeface);
        return paint;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.Paint createFillPaint(int i) {
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setColor(i);
        paint.setStyle(android.graphics.Paint.Style.FILL);
        return paint;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.Paint createGreyedOutPaint(android.graphics.Paint paint) {
        android.graphics.Paint paint2 = new android.graphics.Paint(paint);
        paint2.setAlpha(100);
        return paint2;
    }
}
