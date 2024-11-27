package android.text;

/* compiled from: Html.java */
/* loaded from: classes3.dex */
class HtmlToSpannedConverter implements org.xml.sax.ContentHandler {
    private static java.util.regex.Pattern sBackgroundColorPattern;
    private static java.util.regex.Pattern sForegroundColorPattern;
    private static java.util.regex.Pattern sTextAlignPattern;
    private static java.util.regex.Pattern sTextDecorationPattern;
    private int mFlags;
    private android.text.Html.ImageGetter mImageGetter;
    private org.xml.sax.XMLReader mReader;
    private java.lang.String mSource;
    private android.text.SpannableStringBuilder mSpannableStringBuilder = new android.text.SpannableStringBuilder();
    private android.text.Html.TagHandler mTagHandler;
    private static final float[] HEADING_SIZES = {1.5f, 1.4f, 1.3f, 1.2f, 1.1f, 1.0f};
    private static final java.util.Map<java.lang.String, java.lang.Integer> sColorMap = new java.util.HashMap();

    static {
        sColorMap.put("darkgray", -5658199);
        sColorMap.put("gray", -8355712);
        sColorMap.put("lightgray", -2894893);
        sColorMap.put("darkgrey", -5658199);
        sColorMap.put("grey", -8355712);
        sColorMap.put("lightgrey", -2894893);
        sColorMap.put("green", -16744448);
    }

    private static java.util.regex.Pattern getTextAlignPattern() {
        if (sTextAlignPattern == null) {
            sTextAlignPattern = java.util.regex.Pattern.compile("(?:\\s+|\\A)text-align\\s*:\\s*(\\S*)\\b");
        }
        return sTextAlignPattern;
    }

    private static java.util.regex.Pattern getForegroundColorPattern() {
        if (sForegroundColorPattern == null) {
            sForegroundColorPattern = java.util.regex.Pattern.compile("(?:\\s+|\\A)color\\s*:\\s*(\\S*)\\b");
        }
        return sForegroundColorPattern;
    }

    private static java.util.regex.Pattern getBackgroundColorPattern() {
        if (sBackgroundColorPattern == null) {
            sBackgroundColorPattern = java.util.regex.Pattern.compile("(?:\\s+|\\A)background(?:-color)?\\s*:\\s*(\\S*)\\b");
        }
        return sBackgroundColorPattern;
    }

    private static java.util.regex.Pattern getTextDecorationPattern() {
        if (sTextDecorationPattern == null) {
            sTextDecorationPattern = java.util.regex.Pattern.compile("(?:\\s+|\\A)text-decoration\\s*:\\s*(\\S*)\\b");
        }
        return sTextDecorationPattern;
    }

    public HtmlToSpannedConverter(java.lang.String str, android.text.Html.ImageGetter imageGetter, android.text.Html.TagHandler tagHandler, org.ccil.cowan.tagsoup.Parser parser, int i) {
        this.mSource = str;
        this.mImageGetter = imageGetter;
        this.mTagHandler = tagHandler;
        this.mReader = parser;
        this.mFlags = i;
    }

    public android.text.Spanned convert() {
        this.mReader.setContentHandler(this);
        try {
            this.mReader.parse(new org.xml.sax.InputSource(new java.io.StringReader(this.mSource)));
            java.lang.Object[] spans = this.mSpannableStringBuilder.getSpans(0, this.mSpannableStringBuilder.length(), android.text.style.ParagraphStyle.class);
            for (int i = 0; i < spans.length; i++) {
                int spanStart = this.mSpannableStringBuilder.getSpanStart(spans[i]);
                int spanEnd = this.mSpannableStringBuilder.getSpanEnd(spans[i]);
                int i2 = spanEnd - 2;
                if (i2 >= 0 && this.mSpannableStringBuilder.charAt(spanEnd - 1) == '\n' && this.mSpannableStringBuilder.charAt(i2) == '\n') {
                    spanEnd--;
                }
                if (spanEnd == spanStart) {
                    this.mSpannableStringBuilder.removeSpan(spans[i]);
                } else {
                    this.mSpannableStringBuilder.setSpan(spans[i], spanStart, spanEnd, 51);
                }
            }
            return this.mSpannableStringBuilder;
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(e);
        } catch (org.xml.sax.SAXException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    private void handleStartTag(java.lang.String str, org.xml.sax.Attributes attributes) {
        if (!str.equalsIgnoreCase(android.media.TtmlUtils.TAG_BR)) {
            if (str.equalsIgnoreCase("p")) {
                startBlockElement(this.mSpannableStringBuilder, attributes, getMarginParagraph());
                startCssStyle(this.mSpannableStringBuilder, attributes);
                return;
            }
            if (str.equalsIgnoreCase("ul")) {
                startBlockElement(this.mSpannableStringBuilder, attributes, getMarginList());
                return;
            }
            if (str.equalsIgnoreCase("li")) {
                startLi(this.mSpannableStringBuilder, attributes);
                return;
            }
            if (str.equalsIgnoreCase(android.media.TtmlUtils.TAG_DIV)) {
                startBlockElement(this.mSpannableStringBuilder, attributes, getMarginDiv());
                return;
            }
            if (str.equalsIgnoreCase(android.media.TtmlUtils.TAG_SPAN)) {
                startCssStyle(this.mSpannableStringBuilder, attributes);
                return;
            }
            byte b = 0;
            byte b2 = 0;
            byte b3 = 0;
            byte b4 = 0;
            byte b5 = 0;
            byte b6 = 0;
            byte b7 = 0;
            byte b8 = 0;
            byte b9 = 0;
            byte b10 = 0;
            byte b11 = 0;
            byte b12 = 0;
            byte b13 = 0;
            byte b14 = 0;
            if (str.equalsIgnoreCase("strong")) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Bold());
                return;
            }
            if (str.equalsIgnoreCase(android.app.blob.XmlTags.TAG_BLOB)) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Bold());
                return;
            }
            if (str.equalsIgnoreCase("em")) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Italic());
                return;
            }
            if (str.equalsIgnoreCase("cite")) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Italic());
                return;
            }
            if (str.equalsIgnoreCase("dfn")) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Italic());
                return;
            }
            if (str.equalsIgnoreCase("i")) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Italic());
                return;
            }
            if (str.equalsIgnoreCase("big")) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Big());
                return;
            }
            if (str.equalsIgnoreCase("small")) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Small());
                return;
            }
            if (str.equalsIgnoreCase(android.content.Context.FONT_SERVICE)) {
                startFont(this.mSpannableStringBuilder, attributes);
                return;
            }
            if (str.equalsIgnoreCase("blockquote")) {
                startBlockquote(this.mSpannableStringBuilder, attributes);
                return;
            }
            if (str.equalsIgnoreCase(android.media.TtmlUtils.TAG_TT)) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Monospace());
                return;
            }
            if (str.equalsIgnoreCase(android.app.backup.FullBackup.APK_TREE_TOKEN)) {
                startA(this.mSpannableStringBuilder, attributes);
                return;
            }
            if (str.equalsIgnoreCase(android.app.blob.XmlTags.ATTR_UID)) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Underline());
                return;
            }
            if (str.equalsIgnoreCase("del")) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Strikethrough());
                return;
            }
            if (str.equalsIgnoreCase(android.app.blob.XmlTags.TAG_SESSION)) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Strikethrough());
                return;
            }
            if (str.equalsIgnoreCase("strike")) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Strikethrough());
                return;
            }
            if (str.equalsIgnoreCase("sup")) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Super());
                return;
            }
            if (str.equalsIgnoreCase(android.provider.Telephony.BaseMmsColumns.SUBJECT)) {
                start(this.mSpannableStringBuilder, new android.text.HtmlToSpannedConverter.Sub());
                return;
            }
            if (str.length() == 2 && java.lang.Character.toLowerCase(str.charAt(0)) == 'h' && str.charAt(1) >= '1' && str.charAt(1) <= '6') {
                startHeading(this.mSpannableStringBuilder, attributes, str.charAt(1) - '1');
            } else if (str.equalsIgnoreCase("img")) {
                startImg(this.mSpannableStringBuilder, attributes, this.mImageGetter);
            } else if (this.mTagHandler != null) {
                this.mTagHandler.handleTag(true, str, this.mSpannableStringBuilder, this.mReader);
            }
        }
    }

    private void handleEndTag(java.lang.String str) {
        if (str.equalsIgnoreCase(android.media.TtmlUtils.TAG_BR)) {
            handleBr(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase("p")) {
            endCssStyle(this.mSpannableStringBuilder);
            endBlockElement(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase("ul")) {
            endBlockElement(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase("li")) {
            endLi(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase(android.media.TtmlUtils.TAG_DIV)) {
            endBlockElement(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase(android.media.TtmlUtils.TAG_SPAN)) {
            endCssStyle(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase("strong")) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Bold.class, new android.text.style.StyleSpan(1, android.app.ActivityThread.currentApplication().getResources().getConfiguration().fontWeightAdjustment));
            return;
        }
        if (str.equalsIgnoreCase(android.app.blob.XmlTags.TAG_BLOB)) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Bold.class, new android.text.style.StyleSpan(1, android.app.ActivityThread.currentApplication().getResources().getConfiguration().fontWeightAdjustment));
            return;
        }
        if (str.equalsIgnoreCase("em")) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Italic.class, new android.text.style.StyleSpan(2));
            return;
        }
        if (str.equalsIgnoreCase("cite")) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Italic.class, new android.text.style.StyleSpan(2));
            return;
        }
        if (str.equalsIgnoreCase("dfn")) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Italic.class, new android.text.style.StyleSpan(2));
            return;
        }
        if (str.equalsIgnoreCase("i")) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Italic.class, new android.text.style.StyleSpan(2));
            return;
        }
        if (str.equalsIgnoreCase("big")) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Big.class, new android.text.style.RelativeSizeSpan(1.25f));
            return;
        }
        if (str.equalsIgnoreCase("small")) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Small.class, new android.text.style.RelativeSizeSpan(0.8f));
            return;
        }
        if (str.equalsIgnoreCase(android.content.Context.FONT_SERVICE)) {
            endFont(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase("blockquote")) {
            endBlockquote(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase(android.media.TtmlUtils.TAG_TT)) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Monospace.class, new android.text.style.TypefaceSpan("monospace"));
            return;
        }
        if (str.equalsIgnoreCase(android.app.backup.FullBackup.APK_TREE_TOKEN)) {
            endA(this.mSpannableStringBuilder);
            return;
        }
        if (str.equalsIgnoreCase(android.app.blob.XmlTags.ATTR_UID)) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Underline.class, new android.text.style.UnderlineSpan());
            return;
        }
        if (str.equalsIgnoreCase("del")) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Strikethrough.class, new android.text.style.StrikethroughSpan());
            return;
        }
        if (str.equalsIgnoreCase(android.app.blob.XmlTags.TAG_SESSION)) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Strikethrough.class, new android.text.style.StrikethroughSpan());
            return;
        }
        if (str.equalsIgnoreCase("strike")) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Strikethrough.class, new android.text.style.StrikethroughSpan());
            return;
        }
        if (str.equalsIgnoreCase("sup")) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Super.class, new android.text.style.SuperscriptSpan());
            return;
        }
        if (str.equalsIgnoreCase(android.provider.Telephony.BaseMmsColumns.SUBJECT)) {
            end(this.mSpannableStringBuilder, android.text.HtmlToSpannedConverter.Sub.class, new android.text.style.SubscriptSpan());
            return;
        }
        if (str.length() == 2 && java.lang.Character.toLowerCase(str.charAt(0)) == 'h' && str.charAt(1) >= '1' && str.charAt(1) <= '6') {
            endHeading(this.mSpannableStringBuilder);
        } else if (this.mTagHandler != null) {
            this.mTagHandler.handleTag(false, str, this.mSpannableStringBuilder, this.mReader);
        }
    }

    private int getMarginParagraph() {
        return getMargin(1);
    }

    private int getMarginHeading() {
        return getMargin(2);
    }

    private int getMarginListItem() {
        return getMargin(4);
    }

    private int getMarginList() {
        return getMargin(8);
    }

    private int getMarginDiv() {
        return getMargin(16);
    }

    private int getMarginBlockquote() {
        return getMargin(32);
    }

    private int getMargin(int i) {
        if ((i & this.mFlags) != 0) {
            return 1;
        }
        return 2;
    }

    private static void appendNewlines(android.text.Editable editable, int i) {
        int length = editable.length();
        if (length == 0) {
            return;
        }
        int i2 = 0;
        for (int i3 = length - 1; i3 >= 0 && editable.charAt(i3) == '\n'; i3--) {
            i2++;
        }
        while (i2 < i) {
            editable.append("\n");
            i2++;
        }
    }

    private static void startBlockElement(android.text.Editable editable, org.xml.sax.Attributes attributes, int i) {
        editable.length();
        if (i > 0) {
            appendNewlines(editable, i);
            start(editable, new android.text.HtmlToSpannedConverter.Newline(i));
        }
        java.lang.String value = attributes.getValue("", "style");
        if (value != null) {
            java.util.regex.Matcher matcher = getTextAlignPattern().matcher(value);
            if (matcher.find()) {
                java.lang.String group = matcher.group(1);
                if (group.equalsIgnoreCase("start")) {
                    start(editable, new android.text.HtmlToSpannedConverter.Alignment(android.text.Layout.Alignment.ALIGN_NORMAL));
                } else if (group.equalsIgnoreCase("center")) {
                    start(editable, new android.text.HtmlToSpannedConverter.Alignment(android.text.Layout.Alignment.ALIGN_CENTER));
                } else if (group.equalsIgnoreCase("end")) {
                    start(editable, new android.text.HtmlToSpannedConverter.Alignment(android.text.Layout.Alignment.ALIGN_OPPOSITE));
                }
            }
        }
    }

    private static void endBlockElement(android.text.Editable editable) {
        android.text.HtmlToSpannedConverter.Newline newline = (android.text.HtmlToSpannedConverter.Newline) getLast(editable, android.text.HtmlToSpannedConverter.Newline.class);
        if (newline != null) {
            appendNewlines(editable, newline.mNumNewlines);
            editable.removeSpan(newline);
        }
        android.text.HtmlToSpannedConverter.Alignment alignment = (android.text.HtmlToSpannedConverter.Alignment) getLast(editable, android.text.HtmlToSpannedConverter.Alignment.class);
        if (alignment != null) {
            setSpanFromMark(editable, alignment, new android.text.style.AlignmentSpan.Standard(alignment.mAlignment));
        }
    }

    private static void handleBr(android.text.Editable editable) {
        editable.append('\n');
    }

    private void startLi(android.text.Editable editable, org.xml.sax.Attributes attributes) {
        startBlockElement(editable, attributes, getMarginListItem());
        start(editable, new android.text.HtmlToSpannedConverter.Bullet());
        startCssStyle(editable, attributes);
    }

    private static void endLi(android.text.Editable editable) {
        endCssStyle(editable);
        endBlockElement(editable);
        end(editable, android.text.HtmlToSpannedConverter.Bullet.class, new android.text.style.BulletSpan());
    }

    private void startBlockquote(android.text.Editable editable, org.xml.sax.Attributes attributes) {
        startBlockElement(editable, attributes, getMarginBlockquote());
        start(editable, new android.text.HtmlToSpannedConverter.Blockquote());
    }

    private static void endBlockquote(android.text.Editable editable) {
        endBlockElement(editable);
        end(editable, android.text.HtmlToSpannedConverter.Blockquote.class, new android.text.style.QuoteSpan());
    }

    private void startHeading(android.text.Editable editable, org.xml.sax.Attributes attributes, int i) {
        startBlockElement(editable, attributes, getMarginHeading());
        start(editable, new android.text.HtmlToSpannedConverter.Heading(i));
    }

    private static void endHeading(android.text.Editable editable) {
        android.text.HtmlToSpannedConverter.Heading heading = (android.text.HtmlToSpannedConverter.Heading) getLast(editable, android.text.HtmlToSpannedConverter.Heading.class);
        if (heading != null) {
            setSpanFromMark(editable, heading, new android.text.style.RelativeSizeSpan(HEADING_SIZES[heading.mLevel]), new android.text.style.StyleSpan(1, android.app.ActivityThread.currentApplication().getResources().getConfiguration().fontWeightAdjustment));
        }
        endBlockElement(editable);
    }

    private static <T> T getLast(android.text.Spanned spanned, java.lang.Class<T> cls) {
        java.lang.Object[] spans = spanned.getSpans(0, spanned.length(), cls);
        if (spans.length == 0) {
            return null;
        }
        return (T) spans[spans.length - 1];
    }

    private static void setSpanFromMark(android.text.Spannable spannable, java.lang.Object obj, java.lang.Object... objArr) {
        int spanStart = spannable.getSpanStart(obj);
        spannable.removeSpan(obj);
        int length = spannable.length();
        if (spanStart != length) {
            for (java.lang.Object obj2 : objArr) {
                spannable.setSpan(obj2, spanStart, length, 33);
            }
        }
    }

    private static void start(android.text.Editable editable, java.lang.Object obj) {
        int length = editable.length();
        editable.setSpan(obj, length, length, 17);
    }

    private static void end(android.text.Editable editable, java.lang.Class cls, java.lang.Object obj) {
        editable.length();
        java.lang.Object last = getLast(editable, cls);
        if (last != null) {
            setSpanFromMark(editable, last, obj);
        }
    }

    private void startCssStyle(android.text.Editable editable, org.xml.sax.Attributes attributes) {
        int htmlColor;
        int htmlColor2;
        java.lang.String value = attributes.getValue("", "style");
        if (value != null) {
            java.util.regex.Matcher matcher = getForegroundColorPattern().matcher(value);
            if (matcher.find() && (htmlColor2 = getHtmlColor(matcher.group(1))) != -1) {
                start(editable, new android.text.HtmlToSpannedConverter.Foreground(htmlColor2 | (-16777216)));
            }
            java.util.regex.Matcher matcher2 = getBackgroundColorPattern().matcher(value);
            if (matcher2.find() && (htmlColor = getHtmlColor(matcher2.group(1))) != -1) {
                start(editable, new android.text.HtmlToSpannedConverter.Background(htmlColor | (-16777216)));
            }
            java.util.regex.Matcher matcher3 = getTextDecorationPattern().matcher(value);
            if (matcher3.find() && matcher3.group(1).equalsIgnoreCase("line-through")) {
                start(editable, new android.text.HtmlToSpannedConverter.Strikethrough());
            }
        }
    }

    private static void endCssStyle(android.text.Editable editable) {
        android.text.HtmlToSpannedConverter.Strikethrough strikethrough = (android.text.HtmlToSpannedConverter.Strikethrough) getLast(editable, android.text.HtmlToSpannedConverter.Strikethrough.class);
        if (strikethrough != null) {
            setSpanFromMark(editable, strikethrough, new android.text.style.StrikethroughSpan());
        }
        android.text.HtmlToSpannedConverter.Background background = (android.text.HtmlToSpannedConverter.Background) getLast(editable, android.text.HtmlToSpannedConverter.Background.class);
        if (background != null) {
            setSpanFromMark(editable, background, new android.text.style.BackgroundColorSpan(background.mBackgroundColor));
        }
        android.text.HtmlToSpannedConverter.Foreground foreground = (android.text.HtmlToSpannedConverter.Foreground) getLast(editable, android.text.HtmlToSpannedConverter.Foreground.class);
        if (foreground != null) {
            setSpanFromMark(editable, foreground, new android.text.style.ForegroundColorSpan(foreground.mForegroundColor));
        }
    }

    private static void startImg(android.text.Editable editable, org.xml.sax.Attributes attributes, android.text.Html.ImageGetter imageGetter) {
        android.graphics.drawable.Drawable drawable;
        java.lang.String value = attributes.getValue("", "src");
        if (imageGetter == null) {
            drawable = null;
        } else {
            drawable = imageGetter.getDrawable(value);
        }
        if (drawable == null) {
            drawable = android.content.res.Resources.getSystem().getDrawable(com.android.internal.R.drawable.unknown_image);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        int length = editable.length();
        editable.append("￼");
        editable.setSpan(new android.text.style.ImageSpan(drawable, value), length, editable.length(), 33);
    }

    private void startFont(android.text.Editable editable, org.xml.sax.Attributes attributes) {
        int htmlColor;
        java.lang.String value = attributes.getValue("", "color");
        java.lang.String value2 = attributes.getValue("", android.content.Context.FACE_SERVICE);
        if (!android.text.TextUtils.isEmpty(value) && (htmlColor = getHtmlColor(value)) != -1) {
            start(editable, new android.text.HtmlToSpannedConverter.Foreground(htmlColor | (-16777216)));
        }
        if (!android.text.TextUtils.isEmpty(value2)) {
            start(editable, new android.text.HtmlToSpannedConverter.Font(value2));
        }
    }

    private static void endFont(android.text.Editable editable) {
        android.text.HtmlToSpannedConverter.Font font = (android.text.HtmlToSpannedConverter.Font) getLast(editable, android.text.HtmlToSpannedConverter.Font.class);
        if (font != null) {
            setSpanFromMark(editable, font, new android.text.style.TypefaceSpan(font.mFace));
        }
        android.text.HtmlToSpannedConverter.Foreground foreground = (android.text.HtmlToSpannedConverter.Foreground) getLast(editable, android.text.HtmlToSpannedConverter.Foreground.class);
        if (foreground != null) {
            setSpanFromMark(editable, foreground, new android.text.style.ForegroundColorSpan(foreground.mForegroundColor));
        }
    }

    private static void startA(android.text.Editable editable, org.xml.sax.Attributes attributes) {
        start(editable, new android.text.HtmlToSpannedConverter.Href(attributes.getValue("", "href")));
    }

    private static void endA(android.text.Editable editable) {
        android.text.HtmlToSpannedConverter.Href href = (android.text.HtmlToSpannedConverter.Href) getLast(editable, android.text.HtmlToSpannedConverter.Href.class);
        if (href != null && href.mHref != null) {
            setSpanFromMark(editable, href, new android.text.style.URLSpan(href.mHref));
        }
    }

    private int getHtmlColor(java.lang.String str) {
        java.lang.Integer num;
        if ((this.mFlags & 256) == 256 && (num = sColorMap.get(str.toLowerCase(java.util.Locale.US))) != null) {
            return num.intValue();
        }
        if (java.lang.Character.isLetter(str.charAt(0))) {
            try {
                return android.graphics.Color.parseColor(str);
            } catch (java.lang.IllegalArgumentException e) {
                return -1;
            }
        }
        try {
            return com.android.internal.util.XmlUtils.convertValueToInt(str, -1);
        } catch (java.lang.NumberFormatException e2) {
            return -1;
        }
    }

    @Override // org.xml.sax.ContentHandler
    public void setDocumentLocator(org.xml.sax.Locator locator) {
    }

    @Override // org.xml.sax.ContentHandler
    public void startDocument() throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void endDocument() throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void startPrefixMapping(java.lang.String str, java.lang.String str2) throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void endPrefixMapping(java.lang.String str) throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void startElement(java.lang.String str, java.lang.String str2, java.lang.String str3, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException {
        handleStartTag(str2, attributes);
    }

    @Override // org.xml.sax.ContentHandler
    public void endElement(java.lang.String str, java.lang.String str2, java.lang.String str3) throws org.xml.sax.SAXException {
        handleEndTag(str2);
    }

    @Override // org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws org.xml.sax.SAXException {
        char charAt;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i3 + i];
            if (c == ' ' || c == '\n') {
                int length = sb.length();
                if (length == 0) {
                    int length2 = this.mSpannableStringBuilder.length();
                    if (length2 == 0) {
                        charAt = '\n';
                    } else {
                        charAt = this.mSpannableStringBuilder.charAt(length2 - 1);
                    }
                } else {
                    charAt = sb.charAt(length - 1);
                }
                if (charAt != ' ' && charAt != '\n') {
                    sb.append(' ');
                }
            } else {
                sb.append(c);
            }
        }
        this.mSpannableStringBuilder.append((java.lang.CharSequence) sb);
    }

    @Override // org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void processingInstruction(java.lang.String str, java.lang.String str2) throws org.xml.sax.SAXException {
    }

    @Override // org.xml.sax.ContentHandler
    public void skippedEntity(java.lang.String str) throws org.xml.sax.SAXException {
    }

    /* compiled from: Html.java */
    private static class Bold {
        private Bold() {
        }
    }

    /* compiled from: Html.java */
    private static class Italic {
        private Italic() {
        }
    }

    /* compiled from: Html.java */
    private static class Underline {
        private Underline() {
        }
    }

    /* compiled from: Html.java */
    private static class Strikethrough {
        private Strikethrough() {
        }
    }

    /* compiled from: Html.java */
    private static class Big {
        private Big() {
        }
    }

    /* compiled from: Html.java */
    private static class Small {
        private Small() {
        }
    }

    /* compiled from: Html.java */
    private static class Monospace {
        private Monospace() {
        }
    }

    /* compiled from: Html.java */
    private static class Blockquote {
        private Blockquote() {
        }
    }

    /* compiled from: Html.java */
    private static class Super {
        private Super() {
        }
    }

    /* compiled from: Html.java */
    private static class Sub {
        private Sub() {
        }
    }

    /* compiled from: Html.java */
    private static class Bullet {
        private Bullet() {
        }
    }

    /* compiled from: Html.java */
    private static class Font {
        public java.lang.String mFace;

        public Font(java.lang.String str) {
            this.mFace = str;
        }
    }

    /* compiled from: Html.java */
    private static class Href {
        public java.lang.String mHref;

        public Href(java.lang.String str) {
            this.mHref = str;
        }
    }

    /* compiled from: Html.java */
    private static class Foreground {
        private int mForegroundColor;

        public Foreground(int i) {
            this.mForegroundColor = i;
        }
    }

    /* compiled from: Html.java */
    private static class Background {
        private int mBackgroundColor;

        public Background(int i) {
            this.mBackgroundColor = i;
        }
    }

    /* compiled from: Html.java */
    private static class Heading {
        private int mLevel;

        public Heading(int i) {
            this.mLevel = i;
        }
    }

    /* compiled from: Html.java */
    private static class Newline {
        private int mNumNewlines;

        public Newline(int i) {
            this.mNumNewlines = i;
        }
    }

    /* compiled from: Html.java */
    private static class Alignment {
        private android.text.Layout.Alignment mAlignment;

        public Alignment(android.text.Layout.Alignment alignment) {
            this.mAlignment = alignment;
        }
    }
}
