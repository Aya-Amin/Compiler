package pkj1;


public class Test {

	public static void main(String[] args) {
		
		LexicalAnalyzer lx = new LexicalAnalyzer();
		lx.LexicalAnalysis(args[0]);
//		lx.LexicalAnalysis();
		
		Parser ps = new Parser(lx.progName);
		ps.readTokenTable();
		ps.program();
		
	}

}