package com.google.android.util;

/* loaded from: classes5.dex */
public abstract class AbstractMessageParser {
    public static final java.lang.String musicNote = "♫ ";
    private java.util.HashMap<java.lang.Character, com.google.android.util.AbstractMessageParser.Format> formatStart;
    private int nextChar;
    private int nextClass;
    private boolean parseAcronyms;
    private boolean parseFormatting;
    private boolean parseMeText;
    private boolean parseMusic;
    private boolean parseSmilies;
    private boolean parseUrls;
    private java.util.ArrayList<com.google.android.util.AbstractMessageParser.Part> parts;
    private java.lang.String text;
    private java.util.ArrayList<com.google.android.util.AbstractMessageParser.Token> tokens;

    public interface Resources {
        com.google.android.util.AbstractMessageParser.TrieNode getAcronyms();

        com.google.android.util.AbstractMessageParser.TrieNode getDomainSuffixes();

        java.util.Set<java.lang.String> getSchemes();

        com.google.android.util.AbstractMessageParser.TrieNode getSmileys();
    }

    protected abstract com.google.android.util.AbstractMessageParser.Resources getResources();

    public AbstractMessageParser(java.lang.String str) {
        this(str, true, true, true, true, true, true);
    }

    public AbstractMessageParser(java.lang.String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.text = str;
        this.nextChar = 0;
        this.nextClass = 10;
        this.parts = new java.util.ArrayList<>();
        this.tokens = new java.util.ArrayList<>();
        this.formatStart = new java.util.HashMap<>();
        this.parseSmilies = z;
        this.parseAcronyms = z2;
        this.parseFormatting = z3;
        this.parseUrls = z4;
        this.parseMusic = z5;
        this.parseMeText = z6;
    }

    public final java.lang.String getRawText() {
        return this.text;
    }

    public final int getPartCount() {
        return this.parts.size();
    }

    public final com.google.android.util.AbstractMessageParser.Part getPart(int i) {
        return this.parts.get(i);
    }

    public final java.util.List<com.google.android.util.AbstractMessageParser.Part> getParts() {
        return this.parts;
    }

    public void parse() {
        java.lang.String str = null;
        if (parseMusicTrack()) {
            buildParts(null);
            return;
        }
        int i = 0;
        if (this.parseMeText && this.text.startsWith("/me") && this.text.length() > 3 && java.lang.Character.isWhitespace(this.text.charAt(3))) {
            java.lang.String substring = this.text.substring(0, 4);
            this.text = this.text.substring(4);
            str = substring;
        }
        boolean z = false;
        while (this.nextChar < this.text.length()) {
            if (!isWordBreak(this.nextChar) && (!z || !isSmileyBreak(this.nextChar))) {
                throw new java.lang.AssertionError("last chunk did not end at word break");
            }
            if (parseSmiley()) {
                z = true;
            } else {
                if (!parseAcronym() && !parseURL() && !parseFormatting()) {
                    parseText();
                }
                z = false;
            }
        }
        for (int i2 = 0; i2 < this.tokens.size(); i2++) {
            if (this.tokens.get(i2).isMedia()) {
                if (i2 > 0) {
                    int i3 = i2 - 1;
                    if (this.tokens.get(i3) instanceof com.google.android.util.AbstractMessageParser.Html) {
                        ((com.google.android.util.AbstractMessageParser.Html) this.tokens.get(i3)).trimLeadingWhitespace();
                    }
                }
                int i4 = i2 + 1;
                if (i4 < this.tokens.size() && (this.tokens.get(i4) instanceof com.google.android.util.AbstractMessageParser.Html)) {
                    ((com.google.android.util.AbstractMessageParser.Html) this.tokens.get(i4)).trimTrailingWhitespace();
                }
            }
        }
        while (i < this.tokens.size()) {
            if (this.tokens.get(i).isHtml() && this.tokens.get(i).toHtml(true).length() == 0) {
                this.tokens.remove(i);
                i--;
            }
            i++;
        }
        buildParts(str);
    }

    public static com.google.android.util.AbstractMessageParser.Token tokenForUrl(java.lang.String str, java.lang.String str2) {
        if (str == null) {
            return null;
        }
        com.google.android.util.AbstractMessageParser.Video matchURL = com.google.android.util.AbstractMessageParser.Video.matchURL(str, str2);
        if (matchURL != null) {
            return matchURL;
        }
        com.google.android.util.AbstractMessageParser.YouTubeVideo matchURL2 = com.google.android.util.AbstractMessageParser.YouTubeVideo.matchURL(str, str2);
        if (matchURL2 != null) {
            return matchURL2;
        }
        com.google.android.util.AbstractMessageParser.Photo matchURL3 = com.google.android.util.AbstractMessageParser.Photo.matchURL(str, str2);
        if (matchURL3 != null) {
            return matchURL3;
        }
        com.google.android.util.AbstractMessageParser.FlickrPhoto matchURL4 = com.google.android.util.AbstractMessageParser.FlickrPhoto.matchURL(str, str2);
        if (matchURL4 != null) {
            return matchURL4;
        }
        return new com.google.android.util.AbstractMessageParser.Link(str, str2);
    }

    private void buildParts(java.lang.String str) {
        for (int i = 0; i < this.tokens.size(); i++) {
            com.google.android.util.AbstractMessageParser.Token token = this.tokens.get(i);
            if (token.isMedia() || this.parts.size() == 0 || lastPart().isMedia()) {
                this.parts.add(new com.google.android.util.AbstractMessageParser.Part());
            }
            lastPart().add(token);
        }
        if (this.parts.size() > 0) {
            this.parts.get(0).setMeText(str);
        }
    }

    private com.google.android.util.AbstractMessageParser.Part lastPart() {
        return this.parts.get(this.parts.size() - 1);
    }

    private boolean parseMusicTrack() {
        if (this.parseMusic && this.text.startsWith(musicNote)) {
            addToken(new com.google.android.util.AbstractMessageParser.MusicTrack(this.text.substring(musicNote.length())));
            this.nextChar = this.text.length();
            return true;
        }
        return false;
    }

    private void parseText() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int i = this.nextChar;
        do {
            java.lang.String str = this.text;
            int i2 = this.nextChar;
            this.nextChar = i2 + 1;
            char charAt = str.charAt(i2);
            switch (charAt) {
                case '\n':
                    sb.append("<br>");
                    break;
                case '\"':
                    sb.append("&quot;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                default:
                    sb.append(charAt);
                    break;
            }
        } while (!isWordBreak(this.nextChar));
        addToken(new com.google.android.util.AbstractMessageParser.Html(this.text.substring(i, this.nextChar), sb.toString()));
    }

    private boolean parseSmiley() {
        com.google.android.util.AbstractMessageParser.TrieNode longestMatch;
        if (!this.parseSmilies || (longestMatch = longestMatch(getResources().getSmileys(), this, this.nextChar, true)) == null) {
            return false;
        }
        int charClass = getCharClass(this.nextChar - 1);
        int charClass2 = getCharClass(this.nextChar + longestMatch.getText().length());
        if ((charClass == 2 || charClass == 3) && (charClass2 == 2 || charClass2 == 3)) {
            return false;
        }
        addToken(new com.google.android.util.AbstractMessageParser.Smiley(longestMatch.getText()));
        this.nextChar += longestMatch.getText().length();
        return true;
    }

    private boolean parseAcronym() {
        com.google.android.util.AbstractMessageParser.TrieNode longestMatch;
        if (!this.parseAcronyms || (longestMatch = longestMatch(getResources().getAcronyms(), this, this.nextChar)) == null) {
            return false;
        }
        addToken(new com.google.android.util.AbstractMessageParser.Acronym(longestMatch.getText(), longestMatch.getValue()));
        this.nextChar += longestMatch.getText().length();
        return true;
    }

    private boolean isDomainChar(char c) {
        return c == '-' || java.lang.Character.isLetter(c) || java.lang.Character.isDigit(c);
    }

    private boolean isValidDomain(java.lang.String str) {
        if (matches(getResources().getDomainSuffixes(), reverse(str))) {
            return true;
        }
        return false;
    }

    private boolean parseURL() {
        java.lang.String str;
        char charAt;
        boolean z = false;
        if (!this.parseUrls || !isURLBreak(this.nextChar)) {
            return false;
        }
        int i = this.nextChar;
        int i2 = i;
        while (i2 < this.text.length() && isDomainChar(this.text.charAt(i2))) {
            i2++;
        }
        if (i2 == this.text.length()) {
            return false;
        }
        if (this.text.charAt(i2) == ':') {
            if (!getResources().getSchemes().contains(this.text.substring(this.nextChar, i2))) {
                return false;
            }
            str = "";
        } else {
            if (this.text.charAt(i2) != '.') {
                return false;
            }
            while (i2 < this.text.length() && ((charAt = this.text.charAt(i2)) == '.' || isDomainChar(charAt))) {
                i2++;
            }
            if (!isValidDomain(this.text.substring(this.nextChar, i2))) {
                return false;
            }
            int i3 = i2 + 1;
            if (i3 < this.text.length() && this.text.charAt(i2) == ':' && java.lang.Character.isDigit(this.text.charAt(i3))) {
                while (i3 < this.text.length() && java.lang.Character.isDigit(this.text.charAt(i3))) {
                    i3++;
                }
                i2 = i3;
            }
            if (i2 == this.text.length()) {
                z = true;
            } else {
                char charAt2 = this.text.charAt(i2);
                if (charAt2 == '?') {
                    int i4 = i2 + 1;
                    if (i4 == this.text.length()) {
                        z = true;
                    } else {
                        char charAt3 = this.text.charAt(i4);
                        if (java.lang.Character.isWhitespace(charAt3) || isPunctuation(charAt3)) {
                            z = true;
                        }
                    }
                } else if (isPunctuation(charAt2)) {
                    z = true;
                } else if (java.lang.Character.isWhitespace(charAt2)) {
                    z = true;
                } else if (charAt2 != '/' && charAt2 != '#') {
                    return false;
                }
            }
            str = "http://";
        }
        if (!z) {
            while (i2 < this.text.length() && !java.lang.Character.isWhitespace(this.text.charAt(i2))) {
                i2++;
            }
        }
        java.lang.String substring = this.text.substring(i, i2);
        addURLToken(str + substring, substring);
        this.nextChar = i2;
        return true;
    }

    private void addURLToken(java.lang.String str, java.lang.String str2) {
        addToken(tokenForUrl(str, str2));
    }

    private boolean parseFormatting() {
        if (!this.parseFormatting) {
            return false;
        }
        int i = this.nextChar;
        while (i < this.text.length() && isFormatChar(this.text.charAt(i))) {
            i++;
        }
        if (i == this.nextChar || !isWordBreak(i)) {
            return false;
        }
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        for (int i2 = this.nextChar; i2 < i; i2++) {
            char charAt = this.text.charAt(i2);
            java.lang.Character valueOf = java.lang.Character.valueOf(charAt);
            if (linkedHashMap.containsKey(valueOf)) {
                addToken(new com.google.android.util.AbstractMessageParser.Format(charAt, false));
            } else {
                com.google.android.util.AbstractMessageParser.Format format = this.formatStart.get(valueOf);
                if (format != null) {
                    format.setMatched(true);
                    this.formatStart.remove(valueOf);
                    linkedHashMap.put(valueOf, java.lang.Boolean.TRUE);
                } else {
                    com.google.android.util.AbstractMessageParser.Format format2 = new com.google.android.util.AbstractMessageParser.Format(charAt, true);
                    this.formatStart.put(valueOf, format2);
                    addToken(format2);
                    linkedHashMap.put(valueOf, java.lang.Boolean.FALSE);
                }
            }
        }
        for (java.lang.Character ch : linkedHashMap.keySet()) {
            if (linkedHashMap.get(ch) == java.lang.Boolean.TRUE) {
                com.google.android.util.AbstractMessageParser.Format format3 = new com.google.android.util.AbstractMessageParser.Format(ch.charValue(), false);
                format3.setMatched(true);
                addToken(format3);
            }
        }
        this.nextChar = i;
        return true;
    }

    private boolean isWordBreak(int i) {
        return getCharClass(i + (-1)) != getCharClass(i);
    }

    private boolean isSmileyBreak(int i) {
        if (i > 0 && i < this.text.length() && isSmileyBreak(this.text.charAt(i - 1), this.text.charAt(i))) {
            return true;
        }
        return false;
    }

    private boolean isURLBreak(int i) {
        switch (getCharClass(i - 1)) {
            case 2:
            case 3:
            case 4:
                return false;
            default:
                return true;
        }
    }

    private int getCharClass(int i) {
        if (i < 0 || this.text.length() <= i) {
            return 0;
        }
        char charAt = this.text.charAt(i);
        if (java.lang.Character.isWhitespace(charAt)) {
            return 1;
        }
        if (java.lang.Character.isLetter(charAt)) {
            return 2;
        }
        if (java.lang.Character.isDigit(charAt)) {
            return 3;
        }
        if (isPunctuation(charAt)) {
            int i2 = this.nextClass + 1;
            this.nextClass = i2;
            return i2;
        }
        return 4;
    }

    private static boolean isSmileyBreak(char c, char c2) {
        switch (c) {
            case '$':
            case '&':
            case '*':
            case '+':
            case '-':
            case '/':
            case '<':
            case '=':
            case '>':
            case '@':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '|':
            case '}':
            case '~':
                switch (c2) {
                    case '#':
                    case '$':
                    case '%':
                    case '*':
                    case '/':
                    case '<':
                    case '=':
                    case '>':
                    case '@':
                    case '[':
                    case '\\':
                    case '^':
                    case '~':
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    private static boolean isPunctuation(char c) {
        switch (c) {
            case '!':
            case '\"':
            case '(':
            case ')':
            case ',':
            case '.':
            case ':':
            case ';':
            case '?':
                return true;
            default:
                return false;
        }
    }

    private static boolean isFormatChar(char c) {
        switch (c) {
            case '*':
            case '^':
            case '_':
                return true;
            default:
                return false;
        }
    }

    public static abstract class Token {
        protected java.lang.String text;
        protected com.google.android.util.AbstractMessageParser.Token.Type type;

        public abstract boolean isHtml();

        public enum Type {
            HTML("html"),
            FORMAT(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT),
            LINK(android.app.blob.XmlTags.TAG_LEASEE),
            SMILEY("e"),
            ACRONYM(android.app.backup.FullBackup.APK_TREE_TOKEN),
            MUSIC("m"),
            GOOGLE_VIDEO("v"),
            YOUTUBE_VIDEO("yt"),
            PHOTO("p"),
            FLICKR(android.app.backup.FullBackup.FILES_TREE_TOKEN);

            private java.lang.String stringRep;

            Type(java.lang.String str) {
                this.stringRep = str;
            }

            @Override // java.lang.Enum
            public java.lang.String toString() {
                return this.stringRep;
            }
        }

        protected Token(com.google.android.util.AbstractMessageParser.Token.Type type, java.lang.String str) {
            this.type = type;
            this.text = str;
        }

        public com.google.android.util.AbstractMessageParser.Token.Type getType() {
            return this.type;
        }

        public java.util.List<java.lang.String> getInfo() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(getType().toString());
            return arrayList;
        }

        public java.lang.String getRawText() {
            return this.text;
        }

        public boolean isMedia() {
            return false;
        }

        public boolean isArray() {
            return !isHtml();
        }

        public java.lang.String toHtml(boolean z) {
            throw new java.lang.AssertionError("not html");
        }

        public boolean controlCaps() {
            return false;
        }

        public boolean setCaps() {
            return false;
        }
    }

    public static class Html extends com.google.android.util.AbstractMessageParser.Token {
        private java.lang.String html;

        public Html(java.lang.String str, java.lang.String str2) {
            super(com.google.android.util.AbstractMessageParser.Token.Type.HTML, str);
            this.html = str2;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isHtml() {
            return true;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.lang.String toHtml(boolean z) {
            return z ? this.html.toUpperCase() : this.html;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.util.List<java.lang.String> getInfo() {
            throw new java.lang.UnsupportedOperationException();
        }

        public void trimLeadingWhitespace() {
            this.text = trimLeadingWhitespace(this.text);
            this.html = trimLeadingWhitespace(this.html);
        }

        public void trimTrailingWhitespace() {
            this.text = trimTrailingWhitespace(this.text);
            this.html = trimTrailingWhitespace(this.html);
        }

        private static java.lang.String trimLeadingWhitespace(java.lang.String str) {
            int i = 0;
            while (i < str.length() && java.lang.Character.isWhitespace(str.charAt(i))) {
                i++;
            }
            return str.substring(i);
        }

        public static java.lang.String trimTrailingWhitespace(java.lang.String str) {
            int length = str.length();
            while (length > 0 && java.lang.Character.isWhitespace(str.charAt(length - 1))) {
                length--;
            }
            return str.substring(0, length);
        }
    }

    public static class MusicTrack extends com.google.android.util.AbstractMessageParser.Token {
        private java.lang.String track;

        public MusicTrack(java.lang.String str) {
            super(com.google.android.util.AbstractMessageParser.Token.Type.MUSIC, str);
            this.track = str;
        }

        public java.lang.String getTrack() {
            return this.track;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isHtml() {
            return false;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.util.List<java.lang.String> getInfo() {
            java.util.List<java.lang.String> info = super.getInfo();
            info.add(getTrack());
            return info;
        }
    }

    public static class Link extends com.google.android.util.AbstractMessageParser.Token {
        private java.lang.String url;

        public Link(java.lang.String str, java.lang.String str2) {
            super(com.google.android.util.AbstractMessageParser.Token.Type.LINK, str2);
            this.url = str;
        }

        public java.lang.String getURL() {
            return this.url;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isHtml() {
            return false;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.util.List<java.lang.String> getInfo() {
            java.util.List<java.lang.String> info = super.getInfo();
            info.add(getURL());
            info.add(getRawText());
            return info;
        }
    }

    public static class Video extends com.google.android.util.AbstractMessageParser.Token {
        private static final java.util.regex.Pattern URL_PATTERN = java.util.regex.Pattern.compile("(?i)http://video\\.google\\.[a-z0-9]+(?:\\.[a-z0-9]+)?/videoplay\\?.*?\\bdocid=(-?\\d+).*");
        private java.lang.String docid;

        public Video(java.lang.String str, java.lang.String str2) {
            super(com.google.android.util.AbstractMessageParser.Token.Type.GOOGLE_VIDEO, str2);
            this.docid = str;
        }

        public java.lang.String getDocID() {
            return this.docid;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isHtml() {
            return false;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isMedia() {
            return true;
        }

        public static com.google.android.util.AbstractMessageParser.Video matchURL(java.lang.String str, java.lang.String str2) {
            java.util.regex.Matcher matcher = URL_PATTERN.matcher(str);
            if (matcher.matches()) {
                return new com.google.android.util.AbstractMessageParser.Video(matcher.group(1), str2);
            }
            return null;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.util.List<java.lang.String> getInfo() {
            java.util.List<java.lang.String> info = super.getInfo();
            info.add(getRssUrl(this.docid));
            info.add(getURL(this.docid));
            return info;
        }

        public static java.lang.String getRssUrl(java.lang.String str) {
            return "http://video.google.com/videofeed?type=docid&output=rss&sourceid=gtalk&docid=" + str;
        }

        public static java.lang.String getURL(java.lang.String str) {
            return getURL(str, null);
        }

        public static java.lang.String getURL(java.lang.String str, java.lang.String str2) {
            if (str2 == null) {
                str2 = "";
            } else if (str2.length() > 0) {
                str2 = str2 + "&";
            }
            return "http://video.google.com/videoplay?" + str2 + "docid=" + str;
        }
    }

    public static class YouTubeVideo extends com.google.android.util.AbstractMessageParser.Token {
        private static final java.util.regex.Pattern URL_PATTERN = java.util.regex.Pattern.compile("(?i)http://(?:[a-z0-9]+\\.)?youtube\\.[a-z0-9]+(?:\\.[a-z0-9]+)?/watch\\?.*\\bv=([-_a-zA-Z0-9=]+).*");
        private java.lang.String docid;

        public YouTubeVideo(java.lang.String str, java.lang.String str2) {
            super(com.google.android.util.AbstractMessageParser.Token.Type.YOUTUBE_VIDEO, str2);
            this.docid = str;
        }

        public java.lang.String getDocID() {
            return this.docid;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isHtml() {
            return false;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isMedia() {
            return true;
        }

        public static com.google.android.util.AbstractMessageParser.YouTubeVideo matchURL(java.lang.String str, java.lang.String str2) {
            java.util.regex.Matcher matcher = URL_PATTERN.matcher(str);
            if (matcher.matches()) {
                return new com.google.android.util.AbstractMessageParser.YouTubeVideo(matcher.group(1), str2);
            }
            return null;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.util.List<java.lang.String> getInfo() {
            java.util.List<java.lang.String> info = super.getInfo();
            info.add(getRssUrl(this.docid));
            info.add(getURL(this.docid));
            return info;
        }

        public static java.lang.String getRssUrl(java.lang.String str) {
            return "http://youtube.com/watch?v=" + str;
        }

        public static java.lang.String getURL(java.lang.String str) {
            return getURL(str, null);
        }

        public static java.lang.String getURL(java.lang.String str, java.lang.String str2) {
            if (str2 == null) {
                str2 = "";
            } else if (str2.length() > 0) {
                str2 = str2 + "&";
            }
            return "http://youtube.com/watch?" + str2 + "v=" + str;
        }

        public static java.lang.String getPrefixedURL(boolean z, java.lang.String str, java.lang.String str2, java.lang.String str3) {
            java.lang.String str4;
            if (!z) {
                str4 = "";
            } else {
                str4 = "http://";
            }
            if (str == null) {
                str = "";
            }
            if (str3 == null) {
                str3 = "";
            } else if (str3.length() > 0) {
                str3 = str3 + "&";
            }
            return str4 + str + "youtube.com/watch?" + str3 + "v=" + str2;
        }
    }

    public static class Photo extends com.google.android.util.AbstractMessageParser.Token {
        private static final java.util.regex.Pattern URL_PATTERN = java.util.regex.Pattern.compile("http://picasaweb.google.com/([^/?#&]+)/+((?!searchbrowse)[^/?#&]+)(?:/|/photo)?(?:\\?[^#]*)?(?:#(.*))?");
        private java.lang.String album;
        private java.lang.String photo;
        private java.lang.String user;

        public Photo(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
            super(com.google.android.util.AbstractMessageParser.Token.Type.PHOTO, str4);
            this.user = str;
            this.album = str2;
            this.photo = str3;
        }

        public java.lang.String getUser() {
            return this.user;
        }

        public java.lang.String getAlbum() {
            return this.album;
        }

        public java.lang.String getPhoto() {
            return this.photo;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isHtml() {
            return false;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isMedia() {
            return true;
        }

        public static com.google.android.util.AbstractMessageParser.Photo matchURL(java.lang.String str, java.lang.String str2) {
            java.util.regex.Matcher matcher = URL_PATTERN.matcher(str);
            if (matcher.matches()) {
                return new com.google.android.util.AbstractMessageParser.Photo(matcher.group(1), matcher.group(2), matcher.group(3), str2);
            }
            return null;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.util.List<java.lang.String> getInfo() {
            java.util.List<java.lang.String> info = super.getInfo();
            info.add(getRssUrl(getUser()));
            info.add(getAlbumURL(getUser(), getAlbum()));
            if (getPhoto() != null) {
                info.add(getPhotoURL(getUser(), getAlbum(), getPhoto()));
            } else {
                info.add(null);
            }
            return info;
        }

        public static java.lang.String getRssUrl(java.lang.String str) {
            return "http://picasaweb.google.com/data/feed/api/user/" + str + "?category=album&alt=rss";
        }

        public static java.lang.String getAlbumURL(java.lang.String str, java.lang.String str2) {
            return "http://picasaweb.google.com/" + str + "/" + str2;
        }

        public static java.lang.String getPhotoURL(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            return "http://picasaweb.google.com/" + str + "/" + str2 + "/photo#" + str3;
        }
    }

    public static class FlickrPhoto extends com.google.android.util.AbstractMessageParser.Token {
        private static final java.lang.String SETS = "sets";
        private static final java.lang.String TAGS = "tags";
        private java.lang.String grouping;
        private java.lang.String groupingId;
        private java.lang.String photo;
        private java.lang.String user;
        private static final java.util.regex.Pattern URL_PATTERN = java.util.regex.Pattern.compile("http://(?:www.)?flickr.com/photos/([^/?#&]+)/?([^/?#&]+)?/?.*");
        private static final java.util.regex.Pattern GROUPING_PATTERN = java.util.regex.Pattern.compile("http://(?:www.)?flickr.com/photos/([^/?#&]+)/(tags|sets)/([^/?#&]+)/?");

        public FlickrPhoto(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
            super(com.google.android.util.AbstractMessageParser.Token.Type.FLICKR, str5);
            if (!TAGS.equals(str)) {
                this.user = str;
                this.photo = android.view.ThreadedRenderer.OVERDRAW_PROPERTY_SHOW.equals(str2) ? null : str2;
                this.grouping = str3;
                this.groupingId = str4;
                return;
            }
            this.user = null;
            this.photo = null;
            this.grouping = TAGS;
            this.groupingId = str2;
        }

        public java.lang.String getUser() {
            return this.user;
        }

        public java.lang.String getPhoto() {
            return this.photo;
        }

        public java.lang.String getGrouping() {
            return this.grouping;
        }

        public java.lang.String getGroupingId() {
            return this.groupingId;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isHtml() {
            return false;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isMedia() {
            return true;
        }

        public static com.google.android.util.AbstractMessageParser.FlickrPhoto matchURL(java.lang.String str, java.lang.String str2) {
            java.util.regex.Matcher matcher = GROUPING_PATTERN.matcher(str);
            if (matcher.matches()) {
                return new com.google.android.util.AbstractMessageParser.FlickrPhoto(matcher.group(1), null, matcher.group(2), matcher.group(3), str2);
            }
            java.util.regex.Matcher matcher2 = URL_PATTERN.matcher(str);
            if (matcher2.matches()) {
                return new com.google.android.util.AbstractMessageParser.FlickrPhoto(matcher2.group(1), matcher2.group(2), null, null, str2);
            }
            return null;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.util.List<java.lang.String> getInfo() {
            java.util.List<java.lang.String> info = super.getInfo();
            info.add(getUrl());
            info.add(getUser() != null ? getUser() : "");
            info.add(getPhoto() != null ? getPhoto() : "");
            info.add(getGrouping() != null ? getGrouping() : "");
            info.add(getGroupingId() != null ? getGroupingId() : "");
            return info;
        }

        public java.lang.String getUrl() {
            if (SETS.equals(this.grouping)) {
                return getUserSetsURL(this.user, this.groupingId);
            }
            if (TAGS.equals(this.grouping)) {
                if (this.user != null) {
                    return getUserTagsURL(this.user, this.groupingId);
                }
                return getTagsURL(this.groupingId);
            }
            if (this.photo != null) {
                return getPhotoURL(this.user, this.photo);
            }
            return getUserURL(this.user);
        }

        public static java.lang.String getRssUrl(java.lang.String str) {
            return null;
        }

        public static java.lang.String getTagsURL(java.lang.String str) {
            return "http://flickr.com/photos/tags/" + str;
        }

        public static java.lang.String getUserURL(java.lang.String str) {
            return "http://flickr.com/photos/" + str;
        }

        public static java.lang.String getPhotoURL(java.lang.String str, java.lang.String str2) {
            return "http://flickr.com/photos/" + str + "/" + str2;
        }

        public static java.lang.String getUserTagsURL(java.lang.String str, java.lang.String str2) {
            return "http://flickr.com/photos/" + str + "/tags/" + str2;
        }

        public static java.lang.String getUserSetsURL(java.lang.String str, java.lang.String str2) {
            return "http://flickr.com/photos/" + str + "/sets/" + str2;
        }
    }

    public static class Smiley extends com.google.android.util.AbstractMessageParser.Token {
        public Smiley(java.lang.String str) {
            super(com.google.android.util.AbstractMessageParser.Token.Type.SMILEY, str);
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isHtml() {
            return false;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.util.List<java.lang.String> getInfo() {
            java.util.List<java.lang.String> info = super.getInfo();
            info.add(getRawText());
            return info;
        }
    }

    public static class Acronym extends com.google.android.util.AbstractMessageParser.Token {
        private java.lang.String value;

        public Acronym(java.lang.String str, java.lang.String str2) {
            super(com.google.android.util.AbstractMessageParser.Token.Type.ACRONYM, str);
            this.value = str2;
        }

        public java.lang.String getValue() {
            return this.value;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isHtml() {
            return false;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.util.List<java.lang.String> getInfo() {
            java.util.List<java.lang.String> info = super.getInfo();
            info.add(getRawText());
            info.add(getValue());
            return info;
        }
    }

    public static class Format extends com.google.android.util.AbstractMessageParser.Token {
        private char ch;
        private boolean matched;
        private boolean start;

        public Format(char c, boolean z) {
            super(com.google.android.util.AbstractMessageParser.Token.Type.FORMAT, java.lang.String.valueOf(c));
            this.ch = c;
            this.start = z;
        }

        public void setMatched(boolean z) {
            this.matched = z;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean isHtml() {
            return true;
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.lang.String toHtml(boolean z) {
            return this.matched ? this.start ? getFormatStart(this.ch) : getFormatEnd(this.ch) : this.ch == '\"' ? "&quot;" : java.lang.String.valueOf(this.ch);
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public java.util.List<java.lang.String> getInfo() {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean controlCaps() {
            return this.ch == '^';
        }

        @Override // com.google.android.util.AbstractMessageParser.Token
        public boolean setCaps() {
            return this.start;
        }

        private java.lang.String getFormatStart(char c) {
            switch (c) {
                case '\"':
                    return "<font color=\"#999999\">“";
                case '*':
                    return "<b>";
                case '^':
                    return "<b><font color=\"#005FFF\">";
                case '_':
                    return "<i>";
                default:
                    throw new java.lang.AssertionError("unknown format '" + c + "'");
            }
        }

        private java.lang.String getFormatEnd(char c) {
            switch (c) {
                case '\"':
                    return "”</font>";
                case '*':
                    return "</b>";
                case '^':
                    return "</font></b>";
                case '_':
                    return "</i>";
                default:
                    throw new java.lang.AssertionError("unknown format '" + c + "'");
            }
        }
    }

    private void addToken(com.google.android.util.AbstractMessageParser.Token token) {
        this.tokens.add(token);
    }

    public java.lang.String toHtml() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.util.Iterator<com.google.android.util.AbstractMessageParser.Part> it = this.parts.iterator();
        while (it.hasNext()) {
            com.google.android.util.AbstractMessageParser.Part next = it.next();
            sb.append("<p>");
            java.util.Iterator<com.google.android.util.AbstractMessageParser.Token> it2 = next.getTokens().iterator();
            boolean z = false;
            while (it2.hasNext()) {
                com.google.android.util.AbstractMessageParser.Token next2 = it2.next();
                if (next2.isHtml()) {
                    sb.append(next2.toHtml(z));
                } else {
                    switch (next2.getType()) {
                        case LINK:
                            sb.append("<a href=\"");
                            sb.append(((com.google.android.util.AbstractMessageParser.Link) next2).getURL());
                            sb.append("\">");
                            sb.append(next2.getRawText());
                            sb.append("</a>");
                            break;
                        case SMILEY:
                            sb.append(next2.getRawText());
                            break;
                        case ACRONYM:
                            sb.append(next2.getRawText());
                            break;
                        case MUSIC:
                            sb.append(((com.google.android.util.AbstractMessageParser.MusicTrack) next2).getTrack());
                            break;
                        case GOOGLE_VIDEO:
                            sb.append("<a href=\"");
                            sb.append(com.google.android.util.AbstractMessageParser.Video.getURL(((com.google.android.util.AbstractMessageParser.Video) next2).getDocID()));
                            sb.append("\">");
                            sb.append(next2.getRawText());
                            sb.append("</a>");
                            break;
                        case YOUTUBE_VIDEO:
                            sb.append("<a href=\"");
                            sb.append(com.google.android.util.AbstractMessageParser.YouTubeVideo.getURL(((com.google.android.util.AbstractMessageParser.YouTubeVideo) next2).getDocID()));
                            sb.append("\">");
                            sb.append(next2.getRawText());
                            sb.append("</a>");
                            break;
                        case PHOTO:
                            sb.append("<a href=\"");
                            com.google.android.util.AbstractMessageParser.Photo photo = (com.google.android.util.AbstractMessageParser.Photo) next2;
                            sb.append(com.google.android.util.AbstractMessageParser.Photo.getAlbumURL(photo.getUser(), photo.getAlbum()));
                            sb.append("\">");
                            sb.append(next2.getRawText());
                            sb.append("</a>");
                            break;
                        case FLICKR:
                            sb.append("<a href=\"");
                            sb.append(((com.google.android.util.AbstractMessageParser.FlickrPhoto) next2).getUrl());
                            sb.append("\">");
                            sb.append(next2.getRawText());
                            sb.append("</a>");
                            break;
                        default:
                            throw new java.lang.AssertionError("unknown token type: " + next2.getType());
                    }
                }
                if (next2.controlCaps()) {
                    z = next2.setCaps();
                }
            }
            sb.append("</p>\n");
        }
        return sb.toString();
    }

    protected static java.lang.String reverse(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int length = str.length() - 1; length >= 0; length--) {
            sb.append(str.charAt(length));
        }
        return sb.toString();
    }

    public static class TrieNode {
        private final java.util.HashMap<java.lang.Character, com.google.android.util.AbstractMessageParser.TrieNode> children;
        private java.lang.String text;
        private java.lang.String value;

        public TrieNode() {
            this("");
        }

        public TrieNode(java.lang.String str) {
            this.children = new java.util.HashMap<>();
            this.text = str;
        }

        public final boolean exists() {
            return this.value != null;
        }

        public final java.lang.String getText() {
            return this.text;
        }

        public final java.lang.String getValue() {
            return this.value;
        }

        public void setValue(java.lang.String str) {
            this.value = str;
        }

        public com.google.android.util.AbstractMessageParser.TrieNode getChild(char c) {
            return this.children.get(java.lang.Character.valueOf(c));
        }

        public com.google.android.util.AbstractMessageParser.TrieNode getOrCreateChild(char c) {
            java.lang.Character valueOf = java.lang.Character.valueOf(c);
            com.google.android.util.AbstractMessageParser.TrieNode trieNode = this.children.get(valueOf);
            if (trieNode == null) {
                com.google.android.util.AbstractMessageParser.TrieNode trieNode2 = new com.google.android.util.AbstractMessageParser.TrieNode(this.text + java.lang.String.valueOf(c));
                this.children.put(valueOf, trieNode2);
                return trieNode2;
            }
            return trieNode;
        }

        public static void addToTrie(com.google.android.util.AbstractMessageParser.TrieNode trieNode, java.lang.String str, java.lang.String str2) {
            for (int i = 0; i < str.length(); i++) {
                trieNode = trieNode.getOrCreateChild(str.charAt(i));
            }
            trieNode.setValue(str2);
        }
    }

    private static boolean matches(com.google.android.util.AbstractMessageParser.TrieNode trieNode, java.lang.String str) {
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 1;
            trieNode = trieNode.getChild(str.charAt(i));
            if (trieNode == null) {
                break;
            }
            if (!trieNode.exists()) {
                i = i2;
            } else {
                return true;
            }
        }
        return false;
    }

    private static com.google.android.util.AbstractMessageParser.TrieNode longestMatch(com.google.android.util.AbstractMessageParser.TrieNode trieNode, com.google.android.util.AbstractMessageParser abstractMessageParser, int i) {
        return longestMatch(trieNode, abstractMessageParser, i, false);
    }

    private static com.google.android.util.AbstractMessageParser.TrieNode longestMatch(com.google.android.util.AbstractMessageParser.TrieNode trieNode, com.google.android.util.AbstractMessageParser abstractMessageParser, int i, boolean z) {
        com.google.android.util.AbstractMessageParser.TrieNode trieNode2 = null;
        while (i < abstractMessageParser.getRawText().length()) {
            int i2 = i + 1;
            trieNode = trieNode.getChild(abstractMessageParser.getRawText().charAt(i));
            if (trieNode == null) {
                break;
            }
            if (trieNode.exists() && (abstractMessageParser.isWordBreak(i2) || (z && abstractMessageParser.isSmileyBreak(i2)))) {
                trieNode2 = trieNode;
            }
            i = i2;
        }
        return trieNode2;
    }

    public static class Part {
        private java.lang.String meText;
        private java.util.ArrayList<com.google.android.util.AbstractMessageParser.Token> tokens = new java.util.ArrayList<>();

        public java.lang.String getType(boolean z) {
            return (z ? android.app.blob.XmlTags.TAG_SESSION : "r") + getPartType();
        }

        private java.lang.String getPartType() {
            if (isMedia()) {
                return android.app.blob.XmlTags.ATTR_DESCRIPTION;
            }
            if (this.meText != null) {
                return "m";
            }
            return "";
        }

        public boolean isMedia() {
            return this.tokens.size() == 1 && this.tokens.get(0).isMedia();
        }

        public com.google.android.util.AbstractMessageParser.Token getMediaToken() {
            if (isMedia()) {
                return this.tokens.get(0);
            }
            return null;
        }

        public void add(com.google.android.util.AbstractMessageParser.Token token) {
            if (isMedia()) {
                throw new java.lang.AssertionError("media ");
            }
            this.tokens.add(token);
        }

        public void setMeText(java.lang.String str) {
            this.meText = str;
        }

        public java.lang.String getRawText() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            if (this.meText != null) {
                sb.append(this.meText);
            }
            for (int i = 0; i < this.tokens.size(); i++) {
                sb.append(this.tokens.get(i).getRawText());
            }
            return sb.toString();
        }

        public java.util.ArrayList<com.google.android.util.AbstractMessageParser.Token> getTokens() {
            return this.tokens;
        }
    }
}
