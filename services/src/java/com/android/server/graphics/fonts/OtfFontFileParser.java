package com.android.server.graphics.fonts;

/* loaded from: classes.dex */
class OtfFontFileParser implements com.android.server.graphics.fonts.UpdatableFontDir.FontFileParser {
    OtfFontFileParser() {
    }

    @Override // com.android.server.graphics.fonts.UpdatableFontDir.FontFileParser
    public java.lang.String getPostScriptName(java.io.File file) throws java.io.IOException {
        java.nio.ByteBuffer mmap = mmap(file);
        try {
            return android.graphics.fonts.FontFileUtil.getPostScriptName(mmap, 0);
        } finally {
            unmap(mmap);
        }
    }

    /* JADX WARN: Finally extract failed */
    @Override // com.android.server.graphics.fonts.UpdatableFontDir.FontFileParser
    public java.lang.String buildFontFileName(java.io.File file) throws java.io.IOException {
        java.nio.ByteBuffer mmap = mmap(file);
        try {
            java.lang.String postScriptName = android.graphics.fonts.FontFileUtil.getPostScriptName(mmap, 0);
            int isPostScriptType1Font = android.graphics.fonts.FontFileUtil.isPostScriptType1Font(mmap, 0);
            int isCollectionFont = android.graphics.fonts.FontFileUtil.isCollectionFont(mmap);
            if (android.text.TextUtils.isEmpty(postScriptName) || isPostScriptType1Font == -1 || isCollectionFont == -1) {
                unmap(mmap);
                return null;
            }
            java.lang.String str = postScriptName + (isCollectionFont == 1 ? isPostScriptType1Font == 1 ? ".otc" : ".ttc" : isPostScriptType1Font == 1 ? ".otf" : ".ttf");
            unmap(mmap);
            return str;
        } catch (java.lang.Throwable th) {
            unmap(mmap);
            throw th;
        }
    }

    @Override // com.android.server.graphics.fonts.UpdatableFontDir.FontFileParser
    public long getRevision(java.io.File file) throws java.io.IOException {
        java.nio.ByteBuffer mmap = mmap(file);
        try {
            return android.graphics.fonts.FontFileUtil.getRevision(mmap, 0);
        } finally {
            unmap(mmap);
        }
    }

    @Override // com.android.server.graphics.fonts.UpdatableFontDir.FontFileParser
    public void tryToCreateTypeface(java.io.File file) throws java.lang.Throwable {
        java.nio.ByteBuffer mmap = mmap(file);
        try {
            android.graphics.Typeface build = new android.graphics.Typeface.CustomFallbackBuilder(new android.graphics.fonts.FontFamily.Builder(new android.graphics.fonts.Font.Builder(mmap).build()).build()).build();
            android.text.TextPaint textPaint = new android.text.TextPaint();
            textPaint.setTextSize(24.0f);
            textPaint.setTypeface(build);
            android.text.StaticLayout build2 = android.text.StaticLayout.Builder.obtain("abcXYZ@- 🫖🇺🇸💏🏻👨🏼\u200d❤️\u200d💋\u200d👨🏿", 0, "abcXYZ@- 🫖🇺🇸💏🏻👨🏼\u200d❤️\u200d💋\u200d👨🏿".length(), textPaint, (int) java.lang.Math.ceil(android.text.Layout.getDesiredWidth("abcXYZ@- 🫖🇺🇸💏🏻👨🏼\u200d❤️\u200d💋\u200d👨🏿", textPaint))).build();
            build2.draw(new android.graphics.Canvas(android.graphics.Bitmap.createBitmap(build2.getWidth(), build2.getHeight(), android.graphics.Bitmap.Config.ALPHA_8)));
        } finally {
            unmap(mmap);
        }
    }

    private static java.nio.ByteBuffer mmap(java.io.File file) throws java.io.IOException {
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
        try {
            java.nio.channels.FileChannel channel = fileInputStream.getChannel();
            java.nio.MappedByteBuffer map = channel.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, 0L, channel.size());
            fileInputStream.close();
            return map;
        } catch (java.lang.Throwable th) {
            try {
                fileInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static void unmap(java.nio.ByteBuffer byteBuffer) {
        if (byteBuffer instanceof java.nio.DirectByteBuffer) {
            java.nio.NioUtils.freeDirectBuffer(byteBuffer);
        }
    }
}
