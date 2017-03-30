package omnimudplus;

public enum Color {
	
	// 1 - 16 - BASIC SIXTEEN
	
	BLACK("black", "\u001B[30m", "\u001B[38;5;0m"),
	RED("red", "\u001B[31m", "\u001B[38;5;1m"),
	GREEN("green", "\u001B[32m", "\u001B[38;5;2m"),
	YELLOW("yellow", "\u001B[33m", "\u001B[38;5;3m"),
	BLUE("blue", "\u001B[34m", "\u001B[38;5;4m"),
	MAGENTA("magenta", "\u001B[35m", "\u001B[38;5;5m"),
	CYAN("cyan", "\u001B[36m", "\u001B[38;5;6m"),
	WHITE("white", "\u001B[37m", "\u001B[38;5;7m"),
	BOLDBLACK("boldblack", "\u001B[30;1m", "\u001B[38;5;8m"),
	BOLDRED("boldred", "\u001B[31;1m", "\u001B[38;5;9m"),
	BOLDGREEN("boldgreen", "\u001B[32;1m", "\u001B[38;5;10m"),
	BOLDYELLOW("boldyellow", "\u001B[33;1m", "\u001B[38;5;11m"),
	BOLDBLUE("boldblue", "\u001B[34;1m", "\u001B[38;5;12m"),
	BOLDMAGENTA("boldmagenta", "\u001B[35;1m", "\u001B[38;5;13m"),
	BOLDCYAN("boldcyan", "\u001B[36;1m", "\u001B[38;5;14m"),
	BOLDWHITE("boldwhite", "\u001B[37;1m", "\u001B[38;5;15m"),
	
	// 17 - 32
	
	BLACK2("black2", "30", "\u001B[38;5;16m"),
	NAVYBLUE("navyblue", "34", "\u001B[38;5;17m"),
	DARKBLUE("darkblue", "34", "\u001B[38;5;18m"),
	BLUE2("blue2", "34", "\u001B[38;5;19m"),
	BLUE3("blue3", "34", "\u001B[38;5;20m"),
	BLUE4("blue4", "34", "\u001B[38;5;21m"),
	DARKGREEN("darkgreen", "32", "\u001B[38;5;22m"),
	DEEPSKYBLUE("deepskyblue", "34", "\u001B[38;5;23m"),
	DEEPSKYBLUE2("deepskyblue2", "34", "\u001B[38;5;24m"),
	DEEPSKYBLUE3("deepskyblue3", "34", "\u001B[38;5;25m"),
	DODGERBLUE("dodgerblue", "34;1", "\u001B[38;5;26m"),
	DODGERBLUE2("dodgerblue2", "34;1", "\u001B[38;5;27m"),
	GREEN2("green2", "32", "\u001B[38;5;28m"),
	SPRINGGREEN("springgreen", "32", "\u001B[38;5;29m"),
	TURQUOISE("turquoise", "36", "\u001B[38;5;30m"),
	DEEPSKYBLUE4("deepskyblue4", "36", "\u001B[38;5;31m"),
	
	// 33 - 48
	
	DEEPSKYBLUE5("deepskyblue5", "36", "\u001B[38;5;32m"),
	DODGERBLUE3("dodgerblue3", "34", "\u001B[38;5;33m"),
	GREEN3("green3", "32", "\u001B[38;5;34m"),
	SPRINGGREEN2("springgreen2", "32", "\u001B[38;5;35m"),
	DARKCYAN("darkcyan", "36", "\u001B[38;5;36m"),
	LIGHTSEAGREEN("lightseagreen", "36", "\u001B[38;5;37m"),
	DEEPSKYBLUE6("deepskyblue6", "36", "\u001B[38;5;38m"),
	DEEPSKYBLUE7("deepskyblue7", "36", "\u001B[38;5;39m"),
	GREEN4("green4", "32", "\u001B[38;5;40m"),
	SPRINGGREEN3("springgreen3", "32", "\u001B[38;5;41m"),
	SPRINGGREEN4("springgreen4", "32", "\u001B[38;5;42m"),
	CYAN3("cyan3", "36", "\u001B[38;5;43m"),
	DARKTURQUOISE("darkturquoise", "36", "\u001B[38;5;44m"),
	TURQUOISE2("turquoise2", "36", "\u001B[38;5;45m"),
	GREEN5("green5", "32", "\u001B[38;5;46m"),
	SPRINGGREEN5("springgreen5", "32", "\u001B[38;5;47m"),
	
	// 49 - 64
	
	SPRINGGREEN6("springgreen6", "32", "\u001B[38;5;48m"),
	MEDIUMSPRINGGREEN("mediumspringgreen", "32", "\u001B[38;5;49m"),
	CYAN4("cyan4", "36", "\u001B[38;5;50m"),
	CYAN5("cyan5", "36;1", "\u001B[38;5;51m"),
	DARKRED("darkred", "31", "\u001B[38;5;52m"),
	DEEPPINK("deeppink", "35", "\u001B[38;5;53m"),
	PURPLE("purple", "35", "\u001B[38;5;54m"),
	PURPLE2("purple2", "35", "\u001B[38;5;55m"),
	PURPLE3("purple3", "35", "\u001B[38;5;56m"),
	BLUEVIOLET("blueviolet", "35", "\u001B[38;5;57m"),
	ORANGE("orange", "33", "\u001B[38;5;58m"),
	GREY27("grey27", "33", "\u001B[38;5;59m"),
	MEDIUMPURPLE("mediumpurple", "30;1", "\u001B[38;5;60m"),
	SLATEBLUE("slateblue", "30;1", "\u001B[38;5;61m"),
	SLATEBLUE2("slateblue2", "30;1", "\u001B[38;5;62m"),
	ROYALBLUE("royalblue", "34", "\u001B[38;5;63m"),
	
	// 65 - 80
	
	CHARTREUSE("chartreuse", "32", "\u001B[38;5;64m"),
	DARKSEAGREEN("darkseagreen", "32", "\u001B[38;5;65m"),
	PALETURQUOISE4("paleturquoise4", "30;1", "\u001B[38;5;66m"),
	STEELBLUE("steelblue", "36", "\u001B[38;5;67m"),
	STEELBLUE2("steelblue2", "36", "\u001B[38;5;68m"),
	CORNFLOWERBLUE("cornflowerblue", "36", "\u001B[38;5;69m"),
	CHARTREUSE2("chartreuse2", "32", "\u001B[38;5;70m"),
	DARKSEAGREEN2("darkseagreen2", "32", "\u001B[38;5;71m"),
	CADETBLUE("cadetblue", "36", "\u001B[38;5;72m"),
	CADETBLUE2("cadetblue2", "36", "\u001B[38;5;73m"),
	SKYBLUE3("skyblue3", "36", "\u001B[38;5;74m"),
	STEELBLUE3("steelblue3", "36", "\u001B[38;5;75m"),
	CHARTREUSE3("chartreuse3", "32", "\u001B[38;5;76m"),
	PALEGREEN("palegreen", "32", "\u001B[38;5;77m"),
	SEAGREEN("seagreen", "32", "\u001B[38;5;78m"),
	AQUAMARINE("aquamarine", "36", "\u001B[38;5;79m"),
	
	// 81 - 96
	
	MEDIUMTURQUOISE("mediumturquoise", "36", "\u001B[38;5;80m"),
	STEELBLUE4("steelblue4", "36", "\u001B[38;5;81m"),
	CHARTREUSE4("chartreuse4", "32", "\u001B[38;5;82m"),
	SEAGREEN2("seagreen2", "32;1", "\u001B[38;5;83m"),
	SEAGREEN3("seagreen3", "32;1", "\u001B[38;5;84m"),
	SEAGREEN4("seagreen4", "32;1", "\u001B[38;5;85m"),
	AQUAMARINE2("aquamarine2", "36;1", "\u001B[38;5;86m"),
	DARKSLATEGRAY2("darkslategray2", "36;1", "\u001B[38;5;87m"),
	DARKRED2("darkred2", "31", "\u001B[38;5;88m"),
	DEEPPINK2("deeppink2", "31", "\u001B[38;5;89m"),
	DARKMAGENTA("darkmagenta", "35", "\u001B[38;5;90m"),
	DARKMAGENTA2("darkmagenta2", "35", "\u001B[38;5;91m"),
	DARKVIOLET("darkviolet", "35", "\u001B[38;5;92m"),
	PURPLE4("purple4", "35", "\u001B[38;5;93m"),
	ORANGE2("orange2", "33", "\u001B[38;5;94m"),
	LIGHTPINK4("lightpink4", "33", "\u001B[38;5;95m"),
	
	// 97 - 112
	
	PLUM4("plum4", "33", "\u001B[38;5;96m"),
	MEDIUMPURPLE2("mediumpurple2", "35", "\u001B[38;5;97m"),
	MEDIUMPURPLE3("mediumpurple3", "35", "\u001B[38;5;98m"),
	SLATEBLUE3("slateblue3", "35", "\u001B[38;5;99m"),
	YELLOW2("yellow2", "33", "\u001B[38;5;100m"),
	WHEAT4("wheat4", "33", "\u001B[38;5;101m"),
	GREY28("grey28", "30;1", "\u001B[38;5;102m"),
	LIGHTSLATEGREY("lightslategrey", "30;1", "\u001B[38;5;103m"),
	MEDIUMPURPLE4("mediumpurple4", "30;1", "\u001B[38;5;104m"),
	LIGHTSLATEBLUE("lightslateblue", "30;1", "\u001B[38;5;105m"),
	YELLOW3("yellow3", "33", "\u001B[38;5;106m"),
	DARKOLIVEGREEN("darkolivegreen", "33", "\u001B[38;5;107m"),
	DARKSEAGREEN3("darkseagreen3", "33", "\u001B[38;5;108m"),
	LIGHTSKYBLUE("lightskyblue", "30;1", "\u001B[38;5;109m"),
	LIGHTSKYBLUE2("lightskyblue2", "30;1", "\u001B[38;5;110m"),
	SKYBLUE2("skyblue2", "30;1", "\u001B[38;5;111m"),
	
	// 113 - 128
	
	CHARTREUSE5("chartreuse5", "32;1", "\u001B[38;5;112m"),
	DARKOLIVEGREEN2("darkolivegreen2", "32;1", "\u001B[38;5;113m"),
	PALEGREEN2("palegreen2", "32;1", "\u001B[38;5;114m"),
	DARKSEAGREEN4("darkseagreen4", "30;1", "\u001B[38;5;115m"),
	DARKSLATEGRAY3("darkslategray3", "30;1", "\u001B[38;5;116m"),
	SKYBLUE1("skyblue1", "30;1", "\u001B[38;5;117m"),
	CHARTREUSE6("chartreuse6", "32;1", "\u001B[38;5;118m"),
	LIGHTGREEN("lightgreen", "32;1", "\u001B[38;5;119m"),
	LIGHTGREEN2("lightgreen2", "32;1", "\u001B[38;5;120m"),
	PALEGREEN3("palegreen3", "32;1", "\u001B[38;5;121m"),
	AQUAMARINE3("aquamarine3", "36", "\u001B[38;5;122m"),
	DARKSLATEGRAY1("darkslategray1", "36", "\u001B[38;5;123m"),
	RED2("red2", "31", "\u001B[38;5;124m"),
	DEEPPINK3("deeppink3", "31", "\u001B[38;5;125m"),
	MEDIUMVIOLETRED("mediumvioletred", "31", "\u001B[38;5;126m"),
	MAGENTA2("magenta2", "35", "\u001B[38;5;127m"),
	
	// 129 - 144
	
	DARKVIOLET2("darkviolet2", "35", "\u001B[38;5;128m"),
	PURPLE5("purple5", "35", "\u001B[38;5;129m"),
	DARKORANGE("darkorange", "31", "\u001B[38;5;130m"),
	LATERITERED("lateritered", "31", "\u001B[38;5;131m"),
	HOTPINK("hotpink", "35", "\u001B[38;5;132m"),
	MEDIUMORCHID("mediumorchid", "35", "\u001B[38;5;133m"),
	MEDIUMORCHID2("mediumorchid2", "35", "\u001B[38;5;134m"),
	MEDIUMPURPLE5("mediumpurple5", "35", "\u001B[38;5;135m"),
	DARKGOLDENROD("darkgoldenrod", "33", "\u001B[38;5;136m"),
	LIGHTSALMON("lightsalmon", "33", "\u001B[38;5;137m"),
	ROSYBROWN("rosybrown", "33", "\u001B[38;5;138m"),
	GREY29("grey29", "30;1", "\u001B[38;5;139m"),
	MEDIUMPURPLE6("mediumpurple6", "35", "\u001B[38;5;140m"),
	MEDIUMPURPLE7("mediumpurple7", "35", "\u001B[38;5;141m"),
	GOLD("gold", "33", "\u001B[38;5;142m"),
	DARKKHAKI("darkkhaki", "33", "\u001B[38;5;143m"),
	
	// 145 - 160
	
	ORANGEWHITE3("orangewhite1", "33", "\u001B[38;5;144m"),
	GREY30("grey30", "30;1", "\u001B[38;5;145m"),
	LIGHTSTEELBLUE3("lightsteelblue3", "30;1", "\u001B[38;5;146m"),
	LIGHTSTEELBLUE("lightsteelblue", "30;1", "\u001B[38;5;147m"),
	YELLOW4("yellow4", "33;1", "\u001B[38;5;148m"),
	DARKOLIVEGREEN3("darkolivegreen3", "33;1", "\u001B[38;5;149m"),
	DARKSEAGREEN5("darkseagreen5", "30;1", "\u001B[38;5;150m"),
	DARKSEAGREEN6("darkseagreen6", "30;1", "\u001B[38;5;151m"),
	LIGHTCYAN3("lightcyan3", "30;1", "\u001B[38;5;152m"),
	LIGHTSKYBLUE3("lightskyblue3", "30;1", "\u001B[38;5;153m"),
	GREENYELLOW("greenyellow", "33;1", "\u001B[38;5;154m"),
	DARKOLIVEGREEN4("darkolivegreen4", "33;1", "\u001B[38;5;155m"),
	PALEGREEN4("palegreen4", "33;1", "\u001B[38;5;156m"),
	DARKSEAGREEN7("darkseagreen7", "30;1", "\u001B[38;5;157m"),
	DARKSEAGREEN8("darkseagreen8", "30;1", "\u001B[38;5;158m"),
	PALETURQUOISE1("paleturquoise1", "30;1", "\u001B[38;5;159m"),
	
	// 161 - 176
	
	RED3("red3", "31", "\u001B[38;5;160m"),
	DEEPPINK4("deeppink4", "31", "\u001B[38;5;161m"),
	DEEPPINK5("deeppink5", "31", "\u001B[38;5;162m"),
	MAGENTA3("magenta3", "35", "\u001B[38;5;163m"),
	MAGENTA4("magenta4", "35", "\u001B[38;5;164m"),
	MAGENTA5("magenta5", "35", "\u001B[38;5;165m"),
	DARKORANGE2("darkorange2", "31", "\u001B[38;5;166m"),
	LATERITERED2("lateritered2", "31", "\u001B[38;5;167m"),
	HOTPINK2("hotpink2", "31", "\u001B[38;5;168m"),
	HOTPINK3("hotpink3", "31", "\u001B[38;5;169m"),
	ORCHID("orchid", "35", "\u001B[38;5;170m"),
	MEDIUMORCHID3("mediumorchid3", "35", "\u001B[38;5;171m"),
	ORANGE3("orange3", "31", "\u001B[38;5;172m"),
	LIGHTSALMON2("lightsalmon2", "33", "\u001B[38;5;173m"),
	LIGHTPINK3("lightpink3", "33", "\u001B[38;5;174m"),
	PINK3("pink3", "31", "\u001B[38;5;175m"),
	
	// 177 - 192
	
	PLUM3("plum3", "31", "\u001B[38;5;176m"),
	VIOLET("violet", "35", "\u001B[38;5;177m"),
	GOLD2("gold2", "33", "\u001B[38;5;178m"),
	LIGHTGOLDENROD("lightgoldenrod", "33", "\u001B[38;5;179m"),
	TAN("tan", "33", "\u001B[38;5;180m"),
	MISTYROSE3("mistyrose3", "33", "\u001B[38;5;181m"),
	THISTLE3("thistle3", "33", "\u001B[38;5;182m"),
	PLUM2("plum2", "35", "\u001B[38;5;183m"),
	YELLOW5("yellow5", "33", "\u001B[38;5;184m"),
	KHAKI3("khaki3", "33", "\u001B[38;5;185m"),
	LIGHTGOLDENROD2("lightgoldenrod2", "33", "\u001B[38;5;186m"),
	LIGHTYELLOW3("lightyellow3", "33;1", "\u001B[38;5;187m"),
	GREY31("grey31", "30;1", "\u001B[38;5;188m"),
	LIGHTSTEELBLUE1("lightsteelblue1", "30;1", "\u001B[38;5;189m"),
	YELLOW6("yellow6", "33;1", "\u001B[38;5;190m"),
	DARKOLIVEGREEN5("darkolivegreen5", "33;1", "\u001B[38;5;191m"),
	
	// 193 - 208
	
	DARKOLIVEGREEN6("darkolivegreen6", "33;1", "\u001B[38;5;192m"),
	DARKSEAGREEN9("darkseagreen9", "33;1", "\u001B[38;5;193m"),
	HONEYDEW2("honeydew2", "30;1", "\u001B[38;5;194m"),
	LIGHTCYAN1("lightcyan1", "30;1", "\u001B[38;5;195m"),
	RED4("red4", "31;1", "\u001B[38;5;196m"),
	DEEPPINK6("deeppink6", "31;1", "\u001B[38;5;197m"),
	DEEPPINK7("deeppink7", "31;1", "\u001B[38;5;198m"),
	DEEPPINK8("deeppink8", "31;1", "\u001B[38;5;199m"),
	MAGENTA6("magenta6", "35;1", "\u001B[38;5;200m"),
	MAGENTA7("magenta7", "35;1", "\u001B[38;5;201m"),
	ORANGERED1("orangered1", "31;1", "\u001B[38;5;202m"),
	LATERITERED3("lateritered3", "31;1", "\u001B[38;5;203m"),
	LATERITERED4("lateritered4", "31;1", "\u001B[38;5;204m"),
	HOTPINK4("hotpink4", "35;1", "\u001B[38;5;205m"),
	HOTPINK5("hotpink5", "35;1", "\u001B[38;5;206m"),
	MEDIUMORCHID4("mediumorchid4", "35;1", "\u001B[38;5;207m"),
	
	// 209 - 224
	
	DARKORANGE3("darkorange3", "33", "\u001B[38;5;208m"),
	SALMON1("salmon1", "33", "\u001B[38;5;209m"),
	LIGHTCORAL("lightcoral", "33", "\u001B[38;5;210m"),
	PALEVIOLETRED1("palevioletred1", "33", "\u001B[38;5;211m"),
	ORCHID2("orchid2", "35", "\u001B[38;5;212m"),
	ORCHID1("orchid1", "35", "\u001B[38;5;213m"),
	ORANGE4("orange4", "33", "\u001B[38;5;214m"),
	SANDYBROWN("sandybrown", "33", "\u001B[38;5;215m"),
	LIGHTSALMON3("lightsalmon3", "33", "\u001B[38;5;216m"),
	LIGHTPINK1("lightpink1", "33", "\u001B[38;5;217m"),
	PINK1("pink1", "33", "\u001B[38;5;218m"),
	PLUM1("plum1", "35;1", "\u001B[38;5;219m"),
	GOLD3("gold3", "33", "\u001B[38;5;220m"),
	LIGHTGOLDENROD3("lightgoldenrod3", "33", "\u001B[38;5;221m"),
	LIGHTGOLDENROD4("lightgoldenrod4", "33", "\u001B[38;5;222m"),
	ORANGEWHITE1("orangewhite1", "33", "\u001B[38;5;223m"),
	
	// 225 - 231
	
	MISTYROSE1("mistyrose1", "33", "\u001B[38;5;224m"),
	THISTLE1("thistle1", "35", "\u001B[38;5;225m"),
	YELLOW7("yellow7", "33;1", "\u001B[38;5;226m"),
	LIGHTGOLDENROD5("lightgoldenrod5", "33;1", "\u001B[38;5;227m"),
	KHAKI1("khaki1", "33;1", "\u001B[38;5;228m"),
	WHEAT1("wheat1", "33;1", "\u001B[38;5;229m"),
	CORNSILK1("cornsilk1", "30;1", "\u001B[38;5;230m"),
	
	// 232 - 256 (grayscale)
	
	GREY2("grey2", "30", "\u001B[38;5;231m"),
	GREY3("grey3", "30", "\u001B[38;5;232m"),
	GREY4("grey4", "30", "\u001B[38;5;233m"),
	GREY5("grey5", "30", "\u001B[38;5;234m"),
	GREY6("grey6", "30", "\u001B[38;5;235m"),
	GREY7("grey7", "30", "\u001B[38;5;236m"),
	GREY8("grey8", "30", "\u001B[38;5;237m"),
	GREY9("grey9", "30", "\u001B[38;5;238m"),
	GREY10("grey10", "30", "\u001B[38;5;239m"),
	GREY11("grey11", "30", "\u001B[38;5;240m"),
	GREY12("grey12", "30", "\u001B[38;5;241m"),
	GREY13("grey13", "30", "\u001B[38;5;242m"),
	GREY14("grey14", "30", "\u001B[38;5;243m"),
	GREY15("grey15", "30;1", "\u001B[38;5;244m"),
	GREY16("grey16", "30;1", "\u001B[38;5;245m"),
	GREY17("grey17", "30;1", "\u001B[38;5;246m"),
	GREY18("grey18", "30;1", "\u001B[38;5;247m"),
	GREY19("grey19", "30;1", "\u001B[38;5;248m"),
	GREY20("grey20", "30;1", "\u001B[38;5;249m"),
	GREY21("grey21", "30;1", "\u001B[38;5;250m"),
	GREY22("grey22", "30;1", "\u001B[38;5;251m"),
	GREY23("grey23", "30;1", "\u001B[38;5;252m"),
	GREY24("grey24", "30;1", "\u001B[38;5;253m"),
	GREY25("grey25", "30;1", "\u001B[38;5;254m"),
	GREY26("grey26", "30;1", "\u001B[38;5;255m");
	
	private String name;
	private String lowcode;
	private String highcode;
	
    private Color(String name, String lowcode, String highcode) {
        
        this.name = name;
        this.lowcode = lowcode;
        this.highcode = highcode;
     
    }
    
    public String getName() {
    	
    	return name;
    	
    }
    
    public String getLowCode() {
    	
    	return lowcode;
    	
    }
    
    public String getHighCode() {
    	
    	return highcode;
    	
    }
    
    public String getBackgroundCode() {
    	
    	return highcode.replace("38;5", "48;5");
    	
    }
    
    public static String findCode(String color, char input) {
    	
    	if (input == 'h') {
    		
    		for (Color c : Color.values()) {
    			
    			if (c.getName().equals(color)) {
    				
    				return c.getHighCode();
    				
    			}
    			
    		}
    		
    	} else if (input == 'l') {
    		
    		for (Color c : Color.values()) {
    			
    			if (c.getName().equals(color)) {
    				
    				return c.getLowCode();
    				
    			}
    			
    		}
    		
    	}
    	
    	return "31";
    	
    }

}
