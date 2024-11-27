package android.text;

/* loaded from: classes3.dex */
public class Html {
    public static final int FROM_HTML_MODE_COMPACT = 63;
    public static final int FROM_HTML_MODE_LEGACY = 0;
    public static final int FROM_HTML_OPTION_USE_CSS_COLORS = 256;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE = 32;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_DIV = 16;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_HEADING = 2;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST = 8;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM = 4;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH = 1;
    private static final int TO_HTML_PARAGRAPH_FLAG = 1;
    public static final int TO_HTML_PARAGRAPH_LINES_CONSECUTIVE = 0;
    public static final int TO_HTML_PARAGRAPH_LINES_INDIVIDUAL = 1;

    public interface ImageGetter {
        android.graphics.drawable.Drawable getDrawable(java.lang.String str);
    }

    public interface TagHandler {
        void handleTag(boolean z, java.lang.String str, android.text.Editable editable, org.xml.sax.XMLReader xMLReader);
    }

    private Html() {
    }

    @java.lang.Deprecated
    public static android.text.Spanned fromHtml(java.lang.String str) {
        return fromHtml(str, 0, null, null);
    }

    public static android.text.Spanned fromHtml(java.lang.String str, int i) {
        return fromHtml(str, i, null, null);
    }

    private static class HtmlParser {
        private static final org.ccil.cowan.tagsoup.HTMLSchema schema = new org.ccil.cowan.tagsoup.HTMLSchema();

        private HtmlParser() {
        }
    }

    @java.lang.Deprecated
    public static android.text.Spanned fromHtml(java.lang.String str, android.text.Html.ImageGetter imageGetter, android.text.Html.TagHandler tagHandler) {
        return fromHtml(str, 0, imageGetter, tagHandler);
    }

    public static android.text.Spanned fromHtml(java.lang.String str, int i, android.text.Html.ImageGetter imageGetter, android.text.Html.TagHandler tagHandler) {
        org.ccil.cowan.tagsoup.Parser parser = new org.ccil.cowan.tagsoup.Parser();
        try {
            parser.setProperty("http://www.ccil.org/~cowan/tagsoup/properties/schema", android.text.Html.HtmlParser.schema);
            return new android.text.HtmlToSpannedConverter(str, imageGetter, tagHandler, parser, i).convert();
        } catch (org.xml.sax.SAXNotRecognizedException e) {
            throw new java.lang.RuntimeException(e);
        } catch (org.xml.sax.SAXNotSupportedException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    @java.lang.Deprecated
    public static java.lang.String toHtml(android.text.Spanned spanned) {
        return toHtml(spanned, 0);
    }

    public static java.lang.String toHtml(android.text.Spanned spanned, int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        withinHtml(sb, spanned, i);
        return sb.toString();
    }

    public static java.lang.String escapeHtml(java.lang.CharSequence charSequence) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        withinStyle(sb, charSequence, 0, charSequence.length());
        return sb.toString();
    }

    private static void withinHtml(java.lang.StringBuilder sb, android.text.Spanned spanned, int i) {
        if ((i & 1) == 0) {
            encodeTextAlignmentByDiv(sb, spanned, i);
        } else {
            withinDiv(sb, spanned, 0, spanned.length(), i);
        }
    }

    private static void encodeTextAlignmentByDiv(java.lang.StringBuilder sb, android.text.Spanned spanned, int i) {
        int length = spanned.length();
        int i2 = 0;
        while (i2 < length) {
            int nextSpanTransition = spanned.nextSpanTransition(i2, length, android.text.style.ParagraphStyle.class);
            android.text.style.ParagraphStyle[] paragraphStyleArr = (android.text.style.ParagraphStyle[]) spanned.getSpans(i2, nextSpanTransition, android.text.style.ParagraphStyle.class);
            java.lang.String str = " ";
            boolean z = false;
            for (int i3 = 0; i3 < paragraphStyleArr.length; i3++) {
                if (paragraphStyleArr[i3] instanceof android.text.style.AlignmentSpan) {
                    android.text.Layout.Alignment alignment = ((android.text.style.AlignmentSpan) paragraphStyleArr[i3]).getAlignment();
                    if (alignment == android.text.Layout.Alignment.ALIGN_CENTER) {
                        str = "align=\"center\" " + str;
                        z = true;
                    } else if (alignment == android.text.Layout.Alignment.ALIGN_OPPOSITE) {
                        str = "align=\"right\" " + str;
                        z = true;
                    } else {
                        str = "align=\"left\" " + str;
                        z = true;
                    }
                }
            }
            if (z) {
                sb.append("<div ").append(str).append(">");
            }
            withinDiv(sb, spanned, i2, nextSpanTransition, i);
            if (z) {
                sb.append("</div>");
            }
            i2 = nextSpanTransition;
        }
    }

    private static void withinDiv(java.lang.StringBuilder sb, android.text.Spanned spanned, int i, int i2, int i3) {
        while (i < i2) {
            int nextSpanTransition = spanned.nextSpanTransition(i, i2, android.text.style.QuoteSpan.class);
            android.text.style.QuoteSpan[] quoteSpanArr = (android.text.style.QuoteSpan[]) spanned.getSpans(i, nextSpanTransition, android.text.style.QuoteSpan.class);
            for (android.text.style.QuoteSpan quoteSpan : quoteSpanArr) {
                sb.append("<blockquote>");
            }
            withinBlockquote(sb, spanned, i, nextSpanTransition, i3);
            for (android.text.style.QuoteSpan quoteSpan2 : quoteSpanArr) {
                sb.append("</blockquote>\n");
            }
            i = nextSpanTransition;
        }
    }

    private static java.lang.String getTextDirection(android.text.Spanned spanned, int i, int i2) {
        if (android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR.isRtl(spanned, i, i2 - i)) {
            return " dir=\"rtl\"";
        }
        return " dir=\"ltr\"";
    }

    private static java.lang.String getTextStyles(android.text.Spanned spanned, int i, int i2, boolean z, boolean z2) {
        java.lang.String str;
        java.lang.String str2 = null;
        if (!z) {
            str = null;
        } else {
            str = "margin-top:0; margin-bottom:0;";
        }
        if (z2) {
            android.text.style.AlignmentSpan[] alignmentSpanArr = (android.text.style.AlignmentSpan[]) spanned.getSpans(i, i2, android.text.style.AlignmentSpan.class);
            int length = alignmentSpanArr.length - 1;
            while (true) {
                if (length < 0) {
                    break;
                }
                android.text.style.AlignmentSpan alignmentSpan = alignmentSpanArr[length];
                if ((spanned.getSpanFlags(alignmentSpan) & 51) != 51) {
                    length--;
                } else {
                    android.text.Layout.Alignment alignment = alignmentSpan.getAlignment();
                    if (alignment == android.text.Layout.Alignment.ALIGN_NORMAL) {
                        str2 = "text-align:start;";
                    } else if (alignment == android.text.Layout.Alignment.ALIGN_CENTER) {
                        str2 = "text-align:center;";
                    } else if (alignment == android.text.Layout.Alignment.ALIGN_OPPOSITE) {
                        str2 = "text-align:end;";
                    }
                }
            }
        }
        if (str == null && str2 == null) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(" style=\"");
        if (str != null && str2 != null) {
            sb.append(str).append(" ").append(str2);
        } else if (str != null) {
            sb.append(str);
        } else if (str2 != null) {
            sb.append(str2);
        }
        return sb.append("\"").toString();
    }

    private static void withinBlockquote(java.lang.StringBuilder sb, android.text.Spanned spanned, int i, int i2, int i3) {
        if ((i3 & 1) == 0) {
            withinBlockquoteConsecutive(sb, spanned, i, i2);
        } else {
            withinBlockquoteIndividual(sb, spanned, i, i2);
        }
    }

    private static void withinBlockquoteIndividual(java.lang.StringBuilder sb, android.text.Spanned spanned, int i, int i2) {
        boolean z;
        boolean z2 = false;
        while (i <= i2) {
            int indexOf = android.text.TextUtils.indexOf((java.lang.CharSequence) spanned, '\n', i, i2);
            if (indexOf < 0) {
                indexOf = i2;
            }
            if (indexOf == i) {
                if (z2) {
                    sb.append("</ul>\n");
                    z2 = false;
                }
                sb.append("<br>\n");
            } else {
                android.text.style.ParagraphStyle[] paragraphStyleArr = (android.text.style.ParagraphStyle[]) spanned.getSpans(i, indexOf, android.text.style.ParagraphStyle.class);
                int length = paragraphStyleArr.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        z = false;
                        break;
                    }
                    android.text.style.ParagraphStyle paragraphStyle = paragraphStyleArr[i3];
                    if ((spanned.getSpanFlags(paragraphStyle) & 51) != 51 || !(paragraphStyle instanceof android.text.style.BulletSpan)) {
                        i3++;
                    } else {
                        z = true;
                        break;
                    }
                }
                if (z && !z2) {
                    sb.append("<ul").append(getTextStyles(spanned, i, indexOf, true, false)).append(">\n");
                    z2 = true;
                }
                if (z2 && !z) {
                    sb.append("</ul>\n");
                    z2 = false;
                }
                java.lang.String str = z ? "li" : "p";
                sb.append("<").append(str).append(getTextDirection(spanned, i, indexOf)).append(getTextStyles(spanned, i, indexOf, !z, true)).append(">");
                withinParagraph(sb, spanned, i, indexOf);
                sb.append("</");
                sb.append(str);
                sb.append(">\n");
                if (indexOf == i2 && z2) {
                    sb.append("</ul>\n");
                    z2 = false;
                }
            }
            i = indexOf + 1;
        }
    }

    private static void withinBlockquoteConsecutive(java.lang.StringBuilder sb, android.text.Spanned spanned, int i, int i2) {
        sb.append("<p").append(getTextDirection(spanned, i, i2)).append(">");
        int i3 = i;
        while (i3 < i2) {
            int indexOf = android.text.TextUtils.indexOf((java.lang.CharSequence) spanned, '\n', i3, i2);
            if (indexOf < 0) {
                indexOf = i2;
            }
            int i4 = 0;
            while (indexOf < i2 && spanned.charAt(indexOf) == '\n') {
                i4++;
                indexOf++;
            }
            withinParagraph(sb, spanned, i3, indexOf - i4);
            if (i4 == 1) {
                sb.append("<br>\n");
            } else {
                for (int i5 = 2; i5 < i4; i5++) {
                    sb.append("<br>");
                }
                if (indexOf != i2) {
                    sb.append("</p>\n");
                    sb.append("<p").append(getTextDirection(spanned, i, i2)).append(">");
                }
            }
            i3 = indexOf;
        }
        sb.append("</p>\n");
    }

    private static void withinParagraph(java.lang.StringBuilder sb, android.text.Spanned spanned, int i, int i2) {
        while (i < i2) {
            int nextSpanTransition = spanned.nextSpanTransition(i, i2, android.text.style.CharacterStyle.class);
            android.text.style.CharacterStyle[] characterStyleArr = (android.text.style.CharacterStyle[]) spanned.getSpans(i, nextSpanTransition, android.text.style.CharacterStyle.class);
            for (int i3 = 0; i3 < characterStyleArr.length; i3++) {
                if (characterStyleArr[i3] instanceof android.text.style.StyleSpan) {
                    int style = ((android.text.style.StyleSpan) characterStyleArr[i3]).getStyle();
                    if ((style & 1) != 0) {
                        sb.append("<b>");
                    }
                    if ((style & 2) != 0) {
                        sb.append("<i>");
                    }
                }
                if ((characterStyleArr[i3] instanceof android.text.style.TypefaceSpan) && "monospace".equals(((android.text.style.TypefaceSpan) characterStyleArr[i3]).getFamily())) {
                    sb.append("<tt>");
                }
                if (characterStyleArr[i3] instanceof android.text.style.SuperscriptSpan) {
                    sb.append("<sup>");
                }
                if (characterStyleArr[i3] instanceof android.text.style.SubscriptSpan) {
                    sb.append("<sub>");
                }
                if (characterStyleArr[i3] instanceof android.text.style.UnderlineSpan) {
                    sb.append("<u>");
                }
                if (characterStyleArr[i3] instanceof android.text.style.StrikethroughSpan) {
                    sb.append("<span style=\"text-decoration:line-through;\">");
                }
                if (characterStyleArr[i3] instanceof android.text.style.URLSpan) {
                    sb.append("<a href=\"");
                    sb.append(((android.text.style.URLSpan) characterStyleArr[i3]).getURL());
                    sb.append("\">");
                }
                if (characterStyleArr[i3] instanceof android.text.style.ImageSpan) {
                    sb.append("<img src=\"");
                    sb.append(((android.text.style.ImageSpan) characterStyleArr[i3]).getSource());
                    sb.append("\">");
                    i = nextSpanTransition;
                }
                if (characterStyleArr[i3] instanceof android.text.style.AbsoluteSizeSpan) {
                    android.text.style.AbsoluteSizeSpan absoluteSizeSpan = (android.text.style.AbsoluteSizeSpan) characterStyleArr[i3];
                    float size = absoluteSizeSpan.getSize();
                    if (!absoluteSizeSpan.getDip()) {
                        size /= android.app.ActivityThread.currentApplication().getResources().getDisplayMetrics().density;
                    }
                    sb.append(java.lang.String.format("<span style=\"font-size:%.0fpx\";>", java.lang.Float.valueOf(size)));
                }
                if (characterStyleArr[i3] instanceof android.text.style.RelativeSizeSpan) {
                    sb.append(java.lang.String.format("<span style=\"font-size:%.2fem;\">", java.lang.Float.valueOf(((android.text.style.RelativeSizeSpan) characterStyleArr[i3]).getSizeChange())));
                }
                if (characterStyleArr[i3] instanceof android.text.style.ForegroundColorSpan) {
                    sb.append(java.lang.String.format("<span style=\"color:#%06X;\">", java.lang.Integer.valueOf(((android.text.style.ForegroundColorSpan) characterStyleArr[i3]).getForegroundColor() & 16777215)));
                }
                if (characterStyleArr[i3] instanceof android.text.style.BackgroundColorSpan) {
                    sb.append(java.lang.String.format("<span style=\"background-color:#%06X;\">", java.lang.Integer.valueOf(((android.text.style.BackgroundColorSpan) characterStyleArr[i3]).getBackgroundColor() & 16777215)));
                }
            }
            withinStyle(sb, spanned, i, nextSpanTransition);
            for (int length = characterStyleArr.length - 1; length >= 0; length--) {
                if (characterStyleArr[length] instanceof android.text.style.BackgroundColorSpan) {
                    sb.append("</span>");
                }
                if (characterStyleArr[length] instanceof android.text.style.ForegroundColorSpan) {
                    sb.append("</span>");
                }
                if (characterStyleArr[length] instanceof android.text.style.RelativeSizeSpan) {
                    sb.append("</span>");
                }
                if (characterStyleArr[length] instanceof android.text.style.AbsoluteSizeSpan) {
                    sb.append("</span>");
                }
                if (characterStyleArr[length] instanceof android.text.style.URLSpan) {
                    sb.append("</a>");
                }
                if (characterStyleArr[length] instanceof android.text.style.StrikethroughSpan) {
                    sb.append("</span>");
                }
                if (characterStyleArr[length] instanceof android.text.style.UnderlineSpan) {
                    sb.append("</u>");
                }
                if (characterStyleArr[length] instanceof android.text.style.SubscriptSpan) {
                    sb.append("</sub>");
                }
                if (characterStyleArr[length] instanceof android.text.style.SuperscriptSpan) {
                    sb.append("</sup>");
                }
                if ((characterStyleArr[length] instanceof android.text.style.TypefaceSpan) && ((android.text.style.TypefaceSpan) characterStyleArr[length]).getFamily().equals("monospace")) {
                    sb.append("</tt>");
                }
                if (characterStyleArr[length] instanceof android.text.style.StyleSpan) {
                    int style2 = ((android.text.style.StyleSpan) characterStyleArr[length]).getStyle();
                    if ((style2 & 1) != 0) {
                        sb.append("</b>");
                    }
                    if ((style2 & 2) != 0) {
                        sb.append("</i>");
                    }
                }
            }
            i = nextSpanTransition;
        }
    }

    private static void withinStyle(java.lang.StringBuilder sb, java.lang.CharSequence charSequence, int i, int i2) {
        int i3;
        char charAt;
        while (i < i2) {
            char charAt2 = charSequence.charAt(i);
            if (charAt2 == '<') {
                sb.append("&lt;");
            } else if (charAt2 == '>') {
                sb.append("&gt;");
            } else if (charAt2 == '&') {
                sb.append("&amp;");
            } else if (charAt2 >= 55296 && charAt2 <= 57343) {
                if (charAt2 < 56320 && (i3 = i + 1) < i2 && (charAt = charSequence.charAt(i3)) >= 56320 && charAt <= 57343) {
                    sb.append("&#").append(((charAt2 - 55296) << 10) | 65536 | (charAt - 56320)).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
                    i = i3;
                }
            } else if (charAt2 > '~' || charAt2 < ' ') {
                sb.append("&#").append((int) charAt2).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
            } else if (charAt2 == ' ') {
                while (true) {
                    int i4 = i + 1;
                    if (i4 >= i2 || charSequence.charAt(i4) != ' ') {
                        break;
                    }
                    sb.append("&nbsp;");
                    i = i4;
                }
                sb.append(' ');
            } else {
                sb.append(charAt2);
            }
            i++;
        }
    }
}
