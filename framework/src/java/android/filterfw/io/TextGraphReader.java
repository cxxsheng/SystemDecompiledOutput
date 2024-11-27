package android.filterfw.io;

/* loaded from: classes.dex */
public class TextGraphReader extends android.filterfw.io.GraphReader {
    private android.filterfw.core.KeyValueMap mBoundReferences;
    private java.util.ArrayList<android.filterfw.io.TextGraphReader.Command> mCommands = new java.util.ArrayList<>();
    private android.filterfw.core.Filter mCurrentFilter;
    private android.filterfw.core.FilterGraph mCurrentGraph;
    private android.filterfw.core.FilterFactory mFactory;
    private android.filterfw.core.KeyValueMap mSettings;

    private interface Command {
        void execute(android.filterfw.io.TextGraphReader textGraphReader) throws android.filterfw.io.GraphIOException;
    }

    private class ImportPackageCommand implements android.filterfw.io.TextGraphReader.Command {
        private java.lang.String mPackageName;

        public ImportPackageCommand(java.lang.String str) {
            this.mPackageName = str;
        }

        @Override // android.filterfw.io.TextGraphReader.Command
        public void execute(android.filterfw.io.TextGraphReader textGraphReader) throws android.filterfw.io.GraphIOException {
            try {
                textGraphReader.mFactory.addPackage(this.mPackageName);
            } catch (java.lang.IllegalArgumentException e) {
                throw new android.filterfw.io.GraphIOException(e.getMessage());
            }
        }
    }

    private class AddLibraryCommand implements android.filterfw.io.TextGraphReader.Command {
        private java.lang.String mLibraryName;

        public AddLibraryCommand(java.lang.String str) {
            this.mLibraryName = str;
        }

        @Override // android.filterfw.io.TextGraphReader.Command
        public void execute(android.filterfw.io.TextGraphReader textGraphReader) {
            android.filterfw.core.FilterFactory unused = textGraphReader.mFactory;
            android.filterfw.core.FilterFactory.addFilterLibrary(this.mLibraryName);
        }
    }

    private class AllocateFilterCommand implements android.filterfw.io.TextGraphReader.Command {
        private java.lang.String mClassName;
        private java.lang.String mFilterName;

        public AllocateFilterCommand(java.lang.String str, java.lang.String str2) {
            this.mClassName = str;
            this.mFilterName = str2;
        }

        @Override // android.filterfw.io.TextGraphReader.Command
        public void execute(android.filterfw.io.TextGraphReader textGraphReader) throws android.filterfw.io.GraphIOException {
            try {
                textGraphReader.mCurrentFilter = textGraphReader.mFactory.createFilterByClassName(this.mClassName, this.mFilterName);
            } catch (java.lang.IllegalArgumentException e) {
                throw new android.filterfw.io.GraphIOException(e.getMessage());
            }
        }
    }

    private class InitFilterCommand implements android.filterfw.io.TextGraphReader.Command {
        private android.filterfw.core.KeyValueMap mParams;

        public InitFilterCommand(android.filterfw.core.KeyValueMap keyValueMap) {
            this.mParams = keyValueMap;
        }

        @Override // android.filterfw.io.TextGraphReader.Command
        public void execute(android.filterfw.io.TextGraphReader textGraphReader) throws android.filterfw.io.GraphIOException {
            try {
                textGraphReader.mCurrentFilter.initWithValueMap(this.mParams);
                textGraphReader.mCurrentGraph.addFilter(android.filterfw.io.TextGraphReader.this.mCurrentFilter);
            } catch (android.filterfw.core.ProtocolException e) {
                throw new android.filterfw.io.GraphIOException(e.getMessage());
            }
        }
    }

    private class ConnectCommand implements android.filterfw.io.TextGraphReader.Command {
        private java.lang.String mSourceFilter;
        private java.lang.String mSourcePort;
        private java.lang.String mTargetFilter;
        private java.lang.String mTargetName;

        public ConnectCommand(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
            this.mSourceFilter = str;
            this.mSourcePort = str2;
            this.mTargetFilter = str3;
            this.mTargetName = str4;
        }

        @Override // android.filterfw.io.TextGraphReader.Command
        public void execute(android.filterfw.io.TextGraphReader textGraphReader) {
            textGraphReader.mCurrentGraph.connect(this.mSourceFilter, this.mSourcePort, this.mTargetFilter, this.mTargetName);
        }
    }

    @Override // android.filterfw.io.GraphReader
    public android.filterfw.core.FilterGraph readGraphString(java.lang.String str) throws android.filterfw.io.GraphIOException {
        android.filterfw.core.FilterGraph filterGraph = new android.filterfw.core.FilterGraph();
        reset();
        this.mCurrentGraph = filterGraph;
        parseString(str);
        applySettings();
        executeCommands();
        reset();
        return filterGraph;
    }

    private void reset() {
        this.mCurrentGraph = null;
        this.mCurrentFilter = null;
        this.mCommands.clear();
        this.mBoundReferences = new android.filterfw.core.KeyValueMap();
        this.mSettings = new android.filterfw.core.KeyValueMap();
        this.mFactory = new android.filterfw.core.FilterFactory();
    }

    private void parseString(java.lang.String str) throws android.filterfw.io.GraphIOException {
        java.util.regex.Pattern pattern;
        java.lang.String str2;
        java.lang.String str3;
        java.util.regex.Pattern pattern2;
        android.filterfw.io.PatternScanner patternScanner;
        java.util.regex.Pattern pattern3;
        java.util.regex.Pattern pattern4;
        java.util.regex.Pattern pattern5;
        java.util.regex.Pattern compile = java.util.regex.Pattern.compile("@[a-zA-Z]+");
        java.util.regex.Pattern compile2 = java.util.regex.Pattern.compile("\\}");
        java.util.regex.Pattern compile3 = java.util.regex.Pattern.compile("\\{");
        java.util.regex.Pattern compile4 = java.util.regex.Pattern.compile("(\\s+|//[^\\n]*\\n)+");
        java.util.regex.Pattern compile5 = java.util.regex.Pattern.compile("[a-zA-Z\\.]+");
        java.util.regex.Pattern compile6 = java.util.regex.Pattern.compile("[a-zA-Z\\./:]+");
        java.util.regex.Pattern compile7 = java.util.regex.Pattern.compile("\\[[a-zA-Z0-9\\-_]+\\]");
        java.util.regex.Pattern compile8 = java.util.regex.Pattern.compile("=>");
        java.lang.String str4 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR;
        java.util.regex.Pattern compile9 = java.util.regex.Pattern.compile(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
        java.util.regex.Pattern compile10 = java.util.regex.Pattern.compile("[a-zA-Z0-9\\-_]+");
        android.filterfw.io.PatternScanner patternScanner2 = new android.filterfw.io.PatternScanner(str, compile4);
        java.lang.String str5 = null;
        java.lang.String str6 = null;
        java.lang.String str7 = null;
        java.lang.String str8 = null;
        char c = 0;
        while (true) {
            java.util.regex.Pattern pattern6 = compile;
            if (!patternScanner2.atEnd()) {
                switch (c) {
                    case 0:
                        pattern = compile9;
                        str2 = str4;
                        android.filterfw.io.PatternScanner patternScanner3 = patternScanner2;
                        str3 = str5;
                        pattern2 = compile6;
                        patternScanner = patternScanner3;
                        java.util.regex.Pattern pattern7 = compile5;
                        pattern3 = compile10;
                        pattern4 = pattern7;
                        pattern5 = pattern6;
                        java.lang.String eat = patternScanner.eat(pattern5, "<command>");
                        if (eat.equals("@import")) {
                            c = 1;
                            break;
                        } else if (eat.equals("@library")) {
                            c = 2;
                            break;
                        } else if (eat.equals("@filter")) {
                            c = 3;
                            break;
                        } else if (eat.equals("@connect")) {
                            c = '\b';
                            break;
                        } else if (eat.equals("@set")) {
                            c = '\r';
                            break;
                        } else if (eat.equals("@external")) {
                            c = 14;
                            break;
                        } else if (eat.equals("@setting")) {
                            c = 15;
                            break;
                        } else {
                            throw new android.filterfw.io.GraphIOException("Unknown command '" + eat + "'!");
                        }
                    case 1:
                        pattern = compile9;
                        java.util.regex.Pattern pattern8 = compile5;
                        str2 = str4;
                        pattern3 = compile10;
                        android.filterfw.io.PatternScanner patternScanner4 = patternScanner2;
                        str3 = str5;
                        pattern2 = compile6;
                        patternScanner = patternScanner4;
                        pattern4 = pattern8;
                        this.mCommands.add(new android.filterfw.io.TextGraphReader.ImportPackageCommand(patternScanner.eat(pattern4, "<package-name>")));
                        pattern5 = pattern6;
                        c = 16;
                        break;
                    case 2:
                        pattern = compile9;
                        java.util.regex.Pattern pattern9 = compile5;
                        java.util.regex.Pattern pattern10 = compile6;
                        str2 = str4;
                        patternScanner = patternScanner2;
                        pattern3 = compile10;
                        str3 = str5;
                        pattern2 = pattern10;
                        this.mCommands.add(new android.filterfw.io.TextGraphReader.AddLibraryCommand(patternScanner.eat(pattern2, "<library-name>")));
                        pattern5 = pattern6;
                        pattern4 = pattern9;
                        c = 16;
                        break;
                    case 3:
                        pattern = compile9;
                        java.util.regex.Pattern pattern11 = compile5;
                        java.util.regex.Pattern pattern12 = compile6;
                        str2 = str4;
                        patternScanner = patternScanner2;
                        pattern3 = compile10;
                        str3 = patternScanner.eat(pattern3, "<class-name>");
                        c = 4;
                        pattern5 = pattern6;
                        pattern4 = pattern11;
                        pattern2 = pattern12;
                        break;
                    case 4:
                        pattern = compile9;
                        java.util.regex.Pattern pattern13 = compile5;
                        java.util.regex.Pattern pattern14 = compile6;
                        str2 = str4;
                        patternScanner = patternScanner2;
                        pattern3 = compile10;
                        str3 = str5;
                        this.mCommands.add(new android.filterfw.io.TextGraphReader.AllocateFilterCommand(str3, patternScanner.eat(pattern3, "<filter-name>")));
                        c = 5;
                        pattern5 = pattern6;
                        pattern4 = pattern13;
                        pattern2 = pattern14;
                        break;
                    case 5:
                        java.lang.String str9 = str5;
                        pattern = compile9;
                        java.util.regex.Pattern pattern15 = compile5;
                        java.util.regex.Pattern pattern16 = compile6;
                        str2 = str4;
                        patternScanner = patternScanner2;
                        pattern3 = compile10;
                        patternScanner.eat(compile3, "{");
                        c = 6;
                        pattern5 = pattern6;
                        pattern4 = pattern15;
                        pattern2 = pattern16;
                        str3 = str9;
                        break;
                    case 6:
                        java.lang.String str10 = str5;
                        pattern = compile9;
                        java.util.regex.Pattern pattern17 = compile5;
                        java.util.regex.Pattern pattern18 = compile6;
                        str2 = str4;
                        patternScanner = patternScanner2;
                        pattern3 = compile10;
                        this.mCommands.add(new android.filterfw.io.TextGraphReader.InitFilterCommand(readKeyValueAssignments(patternScanner, compile2)));
                        c = 7;
                        pattern5 = pattern6;
                        pattern4 = pattern17;
                        pattern2 = pattern18;
                        str3 = str10;
                        break;
                    case 7:
                        java.lang.String str11 = str5;
                        pattern = compile9;
                        java.util.regex.Pattern pattern19 = compile5;
                        java.util.regex.Pattern pattern20 = compile6;
                        str2 = str4;
                        patternScanner = patternScanner2;
                        pattern3 = compile10;
                        patternScanner.eat(compile2, "}");
                        c = 0;
                        pattern5 = pattern6;
                        pattern4 = pattern19;
                        pattern2 = pattern20;
                        str3 = str11;
                        break;
                    case '\b':
                        java.lang.String str12 = str5;
                        pattern = compile9;
                        java.util.regex.Pattern pattern21 = compile5;
                        java.util.regex.Pattern pattern22 = compile6;
                        str2 = str4;
                        patternScanner = patternScanner2;
                        pattern3 = compile10;
                        str6 = patternScanner.eat(pattern3, "<source-filter-name>");
                        c = '\t';
                        pattern5 = pattern6;
                        pattern4 = pattern21;
                        pattern2 = pattern22;
                        str3 = str12;
                        break;
                    case '\t':
                        java.lang.String str13 = str5;
                        pattern = compile9;
                        java.util.regex.Pattern pattern23 = compile5;
                        java.util.regex.Pattern pattern24 = compile6;
                        str2 = str4;
                        patternScanner = patternScanner2;
                        pattern3 = compile10;
                        java.lang.String eat2 = patternScanner.eat(compile7, "[<source-port-name>]");
                        str7 = eat2.substring(1, eat2.length() - 1);
                        c = '\n';
                        pattern5 = pattern6;
                        pattern4 = pattern23;
                        pattern2 = pattern24;
                        str3 = str13;
                        break;
                    case '\n':
                        java.lang.String str14 = str5;
                        pattern = compile9;
                        java.util.regex.Pattern pattern25 = compile5;
                        java.util.regex.Pattern pattern26 = compile6;
                        str2 = str4;
                        patternScanner = patternScanner2;
                        pattern3 = compile10;
                        patternScanner.eat(compile8, "=>");
                        c = 11;
                        pattern5 = pattern6;
                        pattern4 = pattern25;
                        pattern2 = pattern26;
                        str3 = str14;
                        break;
                    case 11:
                        java.lang.String str15 = str5;
                        pattern = compile9;
                        java.util.regex.Pattern pattern27 = compile5;
                        java.util.regex.Pattern pattern28 = compile6;
                        str2 = str4;
                        patternScanner = patternScanner2;
                        pattern3 = compile10;
                        str8 = patternScanner.eat(pattern3, "<target-filter-name>");
                        c = '\f';
                        pattern5 = pattern6;
                        pattern4 = pattern27;
                        pattern2 = pattern28;
                        str3 = str15;
                        break;
                    case '\f':
                        str2 = str4;
                        java.util.regex.Pattern pattern29 = compile5;
                        java.lang.String str16 = str5;
                        java.util.regex.Pattern pattern30 = compile6;
                        patternScanner = patternScanner2;
                        pattern3 = compile10;
                        pattern = compile9;
                        this.mCommands.add(new android.filterfw.io.TextGraphReader.ConnectCommand(str6, str7, str8, patternScanner2.eat(compile7, "[<target-port-name>]").substring(1, r0.length() - 1)));
                        pattern5 = pattern6;
                        pattern4 = pattern29;
                        pattern2 = pattern30;
                        str3 = str16;
                        c = 16;
                        break;
                    case '\r':
                        this.mBoundReferences.putAll(readKeyValueAssignments(patternScanner2, compile9));
                        pattern = compile9;
                        str2 = str4;
                        pattern5 = pattern6;
                        c = 16;
                        android.filterfw.io.PatternScanner patternScanner5 = patternScanner2;
                        str3 = str5;
                        pattern2 = compile6;
                        patternScanner = patternScanner5;
                        java.util.regex.Pattern pattern31 = compile5;
                        pattern3 = compile10;
                        pattern4 = pattern31;
                        break;
                    case 14:
                        bindExternal(patternScanner2.eat(compile10, "<external-identifier>"));
                        pattern = compile9;
                        str2 = str4;
                        pattern5 = pattern6;
                        c = 16;
                        android.filterfw.io.PatternScanner patternScanner6 = patternScanner2;
                        str3 = str5;
                        pattern2 = compile6;
                        patternScanner = patternScanner6;
                        java.util.regex.Pattern pattern32 = compile5;
                        pattern3 = compile10;
                        pattern4 = pattern32;
                        break;
                    case 15:
                        this.mSettings.putAll(readKeyValueAssignments(patternScanner2, compile9));
                        pattern = compile9;
                        str2 = str4;
                        pattern5 = pattern6;
                        c = 16;
                        android.filterfw.io.PatternScanner patternScanner7 = patternScanner2;
                        str3 = str5;
                        pattern2 = compile6;
                        patternScanner = patternScanner7;
                        java.util.regex.Pattern pattern33 = compile5;
                        pattern3 = compile10;
                        pattern4 = pattern33;
                        break;
                    case 16:
                        patternScanner2.eat(compile9, str4);
                        pattern = compile9;
                        str2 = str4;
                        c = 0;
                        pattern5 = pattern6;
                        android.filterfw.io.PatternScanner patternScanner8 = patternScanner2;
                        str3 = str5;
                        pattern2 = compile6;
                        patternScanner = patternScanner8;
                        java.util.regex.Pattern pattern34 = compile5;
                        pattern3 = compile10;
                        pattern4 = pattern34;
                        break;
                    default:
                        pattern = compile9;
                        str2 = str4;
                        pattern5 = pattern6;
                        android.filterfw.io.PatternScanner patternScanner9 = patternScanner2;
                        str3 = str5;
                        pattern2 = compile6;
                        patternScanner = patternScanner9;
                        java.util.regex.Pattern pattern35 = compile5;
                        pattern3 = compile10;
                        pattern4 = pattern35;
                        break;
                }
                compile = pattern5;
                str4 = str2;
                compile9 = pattern;
                android.filterfw.io.PatternScanner patternScanner10 = patternScanner;
                compile6 = pattern2;
                str5 = str3;
                patternScanner2 = patternScanner10;
                java.util.regex.Pattern pattern36 = pattern3;
                compile5 = pattern4;
                compile10 = pattern36;
            } else {
                if (c != 16 && c != 0) {
                    throw new android.filterfw.io.GraphIOException("Unexpected end of input!");
                }
                return;
            }
        }
    }

    @Override // android.filterfw.io.GraphReader
    public android.filterfw.core.KeyValueMap readKeyValueAssignments(java.lang.String str) throws android.filterfw.io.GraphIOException {
        return readKeyValueAssignments(new android.filterfw.io.PatternScanner(str, java.util.regex.Pattern.compile("\\s+")), null);
    }

    private android.filterfw.core.KeyValueMap readKeyValueAssignments(android.filterfw.io.PatternScanner patternScanner, java.util.regex.Pattern pattern) throws android.filterfw.io.GraphIOException {
        java.lang.String str;
        java.lang.Object obj;
        java.util.regex.Pattern compile = java.util.regex.Pattern.compile("=");
        java.lang.String str2 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR;
        java.util.regex.Pattern compile2 = java.util.regex.Pattern.compile(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
        java.util.regex.Pattern compile3 = java.util.regex.Pattern.compile("[a-zA-Z]+[a-zA-Z0-9]*");
        java.util.regex.Pattern compile4 = java.util.regex.Pattern.compile("'[^']*'|\\\"[^\\\"]*\\\"");
        java.util.regex.Pattern compile5 = java.util.regex.Pattern.compile("[0-9]+");
        java.util.regex.Pattern compile6 = java.util.regex.Pattern.compile("[0-9]*\\.[0-9]+f?");
        java.util.regex.Pattern compile7 = java.util.regex.Pattern.compile("\\$[a-zA-Z]+[a-zA-Z0-9]");
        java.util.regex.Pattern compile8 = java.util.regex.Pattern.compile("true|false");
        android.filterfw.core.KeyValueMap keyValueMap = new android.filterfw.core.KeyValueMap();
        java.lang.String str3 = null;
        char c = 0;
        while (!patternScanner.atEnd() && (pattern == null || !patternScanner.peek(pattern))) {
            switch (c) {
                case 0:
                    str = str2;
                    str3 = patternScanner.eat(compile3, "<identifier>");
                    c = 1;
                    break;
                case 1:
                    str = str2;
                    patternScanner.eat(compile, "=");
                    c = 2;
                    break;
                case 2:
                    java.lang.String tryEat = patternScanner.tryEat(compile4);
                    if (tryEat != null) {
                        str = str2;
                        keyValueMap.put(str3, tryEat.substring(1, tryEat.length() - 1));
                    } else {
                        str = str2;
                        java.lang.String tryEat2 = patternScanner.tryEat(compile7);
                        if (tryEat2 != null) {
                            java.lang.String substring = tryEat2.substring(1, tryEat2.length());
                            if (this.mBoundReferences != null) {
                                obj = this.mBoundReferences.get(substring);
                            } else {
                                obj = null;
                            }
                            if (obj == null) {
                                throw new android.filterfw.io.GraphIOException("Unknown object reference to '" + substring + "'!");
                            }
                            keyValueMap.put(str3, obj);
                        } else {
                            java.lang.String tryEat3 = patternScanner.tryEat(compile8);
                            if (tryEat3 != null) {
                                keyValueMap.put(str3, java.lang.Boolean.valueOf(java.lang.Boolean.parseBoolean(tryEat3)));
                            } else {
                                java.lang.String tryEat4 = patternScanner.tryEat(compile6);
                                if (tryEat4 != null) {
                                    keyValueMap.put(str3, java.lang.Float.valueOf(java.lang.Float.parseFloat(tryEat4)));
                                } else {
                                    java.lang.String tryEat5 = patternScanner.tryEat(compile5);
                                    if (tryEat5 != null) {
                                        keyValueMap.put(str3, java.lang.Integer.valueOf(java.lang.Integer.parseInt(tryEat5)));
                                    } else {
                                        throw new android.filterfw.io.GraphIOException(patternScanner.unexpectedTokenMessage("<value>"));
                                    }
                                }
                            }
                        }
                    }
                    c = 3;
                    break;
                case 3:
                    patternScanner.eat(compile2, str2);
                    str = str2;
                    c = 0;
                    break;
                default:
                    str = str2;
                    break;
            }
            str2 = str;
        }
        if (c != 0 && c != 3) {
            throw new android.filterfw.io.GraphIOException("Unexpected end of assignments on line " + patternScanner.lineNo() + "!");
        }
        return keyValueMap;
    }

    private void bindExternal(java.lang.String str) throws android.filterfw.io.GraphIOException {
        if (this.mReferences.containsKey(str)) {
            this.mBoundReferences.put(str, this.mReferences.get(str));
            return;
        }
        throw new android.filterfw.io.GraphIOException("Unknown external variable '" + str + "'! You must add a reference to this external in the host program using addReference(...)!");
    }

    private void checkReferences() throws android.filterfw.io.GraphIOException {
        for (java.lang.String str : this.mReferences.keySet()) {
            if (!this.mBoundReferences.containsKey(str)) {
                throw new android.filterfw.io.GraphIOException("Host program specifies reference to '" + str + "', which is not declared @external in graph file!");
            }
        }
    }

    private void applySettings() throws android.filterfw.io.GraphIOException {
        for (java.lang.String str : this.mSettings.keySet()) {
            java.lang.Object obj = this.mSettings.get(str);
            if (str.equals("autoBranch")) {
                expectSettingClass(str, obj, java.lang.String.class);
                if (obj.equals("synced")) {
                    this.mCurrentGraph.setAutoBranchMode(1);
                } else if (obj.equals("unsynced")) {
                    this.mCurrentGraph.setAutoBranchMode(2);
                } else if (obj.equals("off")) {
                    this.mCurrentGraph.setAutoBranchMode(0);
                } else {
                    throw new android.filterfw.io.GraphIOException("Unknown autobranch setting: " + obj + "!");
                }
            } else if (str.equals("discardUnconnectedOutputs")) {
                expectSettingClass(str, obj, java.lang.Boolean.class);
                this.mCurrentGraph.setDiscardUnconnectedOutputs(((java.lang.Boolean) obj).booleanValue());
            } else {
                throw new android.filterfw.io.GraphIOException("Unknown @setting '" + str + "'!");
            }
        }
    }

    private void expectSettingClass(java.lang.String str, java.lang.Object obj, java.lang.Class cls) throws android.filterfw.io.GraphIOException {
        if (obj.getClass() != cls) {
            throw new android.filterfw.io.GraphIOException("Setting '" + str + "' must have a value of type " + cls.getSimpleName() + ", but found a value of type " + obj.getClass().getSimpleName() + "!");
        }
    }

    private void executeCommands() throws android.filterfw.io.GraphIOException {
        java.util.Iterator<android.filterfw.io.TextGraphReader.Command> it = this.mCommands.iterator();
        while (it.hasNext()) {
            it.next().execute(this);
        }
    }
}
