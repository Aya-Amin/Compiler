package pkj1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CodeGenerator {

	private File assemblyCode = new File("Assembly Code.txt");
	private PrintWriter pw;
	private ArrayList<String> assembly = new ArrayList<>();

	public void writeToFile() {
		try {
			assembly.add("END");
			PrintWriter pw = new PrintWriter(assemblyCode);
			for (int i = 0; i < assembly.size(); i++)
				pw.println(assembly.get(i));
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
		}
	}

	public void program(String progName) {

		assembly.add("Start " + progName + " 0");
	}

	public void var(ArrayList<String> ids) {

		for (int i = 0; i < ids.size(); i++)
			assembly.add(ids.get(i) + " RESW 1");
	}

	public void read(ArrayList<String> st) {

		assembly.add("+JSUB xREAD");
		assembly.add("word " + st.size());
		for (int i = 0; i < st.size(); i++)
			assembly.add("word " + st.get(i));
	}

	public void write(ArrayList<String> st) {

		assembly.add("+JSUB xWRITE");
		assembly.add("word " + st.size());
		for (int i = 0; i < st.size(); i++)
			assembly.add("word " + st.get(i));
	}

	public void assign(ArrayList<String> st) {

		String operation, operation1, operation2;
		String finalRes = st.get(0);
		// st.get(1) = :=
		if (st.get(2) != "(" && !st.get(4).equals("(")) {
			assembly.add("LDA " + st.get(2));
			if (st.get(3) == "+")
				operation = "ADD";
			else
				operation = "MUL";
			if (st.get(4) != "(")
				assembly.add(operation + " " + st.get(4));
			assembly.add("STA " + finalRes);
		}

		else if ( !st.get(2).equals("(" )&& st.get(4).equals("(") ) {
			assembly.add("LDA " + st.get(5));
			if (st.get(6) == "+")
				operation = "ADD";
			else
				operation = "MUL";
			assembly.add(operation + " " + st.get(7));
			if (st.get(3) == "+")
				operation1 = "ADD";
			else
				operation1 = "MUL";
			assembly.add(operation1 + " " + st.get(2));
			assembly.add("STA " + finalRes);
		}
		
		else if (st.get(2).equals("(") && st.get(8).equals("(")) {

			assembly.add("LDA " + st.get(3));
			if (st.get(4) == "+")
				operation = "ADD";
			else 
				operation = "MUL";
			assembly.add(operation + " " + st.get(5));
			assembly.add("STA " + finalRes);
			if (st.get(7) == "+")
				operation1 = "ADD";
			else
				operation1 = "MUL";
			assembly.add("LDA " + st.get(9));
			if (st.get(10) == "+")
				operation2 = "ADD";
			else
				operation2 = "MUL";
			assembly.add(operation + " " + st.get(11));
			assembly.add(operation1 + " " + finalRes);
			assembly.add("STA " + finalRes);
		}


		else if (st.get(2).equals("(") && !st.get(4).equals("(")) {
			assembly.add("LDA " + st.get(3));
			if (st.get(4) == "+")
				operation = "ADD";
			else
				operation = "MUL";
			assembly.add(operation + " " + st.get(5));
			if (st.get(7) == "+")
				operation1 = "ADD";
			else
				operation1 = "MUL";
			assembly.add(operation1 + " " + st.get(8));
			assembly.add("STA " + finalRes);
		}
	}

	public void end() {
		assembly.add("END");
	}

}