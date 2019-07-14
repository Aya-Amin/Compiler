package pkj1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

	private int index = 0;
	
//	private LexicalAnalyzer lx = new LexicalAnalyzer();
	private CodeGenerator cg = new CodeGenerator();
	private String progName = new String(); 

	private ArrayList<Integer> tokens = new ArrayList<>();
	private ArrayList<String> specifiers = new ArrayList<>();
	private File tokensTable = new File("TokensTable.txt");

	public Parser(String s){
		progName = s;
	}
	
	public void readTokenTable() {
		try {
			Scanner input = new Scanner(tokensTable);
			String line = new String();
			String token = new String();
			String specifier = new String();
			line = input.nextLine();
			
			while (input.hasNext()) {
				line = input.nextLine();
				String[] strArr = line.split("\t\t");
				if (strArr.length == 3) {
					token = strArr[1];
					specifier = strArr[2];
				} else if (strArr.length == 2) {
					token = strArr[1];
					specifier = null;
				}
				tokens.add(Integer.parseInt(token));
				specifiers.add(specifier);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void program() {
		boolean found = false;
		ArrayList<String> varIds = new ArrayList<>();
		
		if (tokens.get(index) == 1) {
			cg.program(progName);
			index++;
			varIds = var();
			if (varIds.size() != 0) {
				cg.var(varIds);
				if (tokens.get(index) == 3) {
					index++;
					if (stList())
						if (tokens.get(index) == 5) {
							cg.end();
							found = true;
						}
				}
			}
		}
		cg.writeToFile();
	}

	public ArrayList<String> var() {
		boolean found = false;
		ArrayList<String> ids = new ArrayList<>();
		if (tokens.get(index) == 2) {
			index++;
			ids = idList();
			if (ids.size() != 0)
				found = true;
		}
		return ids;
	}

	public ArrayList<String> idList() {
		boolean found = false;
		ArrayList<String> ids = new ArrayList<>();
		if (tokens.get(index) == 17) { // token = id
			ids.add(specifiers.get(index));
			found = true;
			index++;
			while (tokens.get(index) == 17 && found == true) {
				ids.add(specifiers.get(index));
				index++;
			}
		}
		return ids;
	}

	public boolean stList() {
		boolean found = false;
		ArrayList<String> st = new ArrayList<>();
		st = read();
		if (st.size() != 0) {
			cg.read(st);
			found = true;
		} else {
			st.clear();
			st = write();
			if (st.size() != 0) {
				cg.write(st);
				found = true;
			} else {
				st.clear();
				st = assign();
				if (st.size() != 0) {
					cg.assign(st);
					found = true;
				}
			}
		}
		
		do {
			st.clear();
			st = read();
			if (st.size() != 0)
				cg.read(st);
			else {
				st = write();
				if (st.size() != 0)
					cg.write(st);
				else {
					st = assign();
					if (st.size() != 0)
						cg.assign(st);
					else
						found = false;
				}
			}
		} while (st != null && found == true);
		return found;
	}

	public ArrayList<String> read() {
		boolean found = false;
		ArrayList<String> ids = new ArrayList<>();
		if (tokens.get(index) == 7) { // token = READ
			index++;
			if (tokens.get(index) == 15) { // token = (
				index++;
				ids = idList();
				if (ids.size() != 0)
					if (tokens.get(index) == 16) { // token = )
						found = true;
						index++;
					}
			}
		}
		return ids;
	}

	public ArrayList<String> write() {
		boolean found = false;
		ArrayList<String> ids = new ArrayList<>();
		if (tokens.get(index) == 8) { // token = Write
			index++;
			if (tokens.get(index) == 15) { // token = (
				index++;
				ids = idList();
				if (ids.size() != 0)
					if (tokens.get(index) == 16) { // token = )
						found = true;
						index++;
					}
			}
		}
		return ids;
	}

	public ArrayList<String> assign() {
		boolean found = false;
		ArrayList<String> assignList = new ArrayList<>();
		ArrayList<String> expList = new ArrayList<>();
		if (tokens.get(index) == 17) { // token = id
			assignList.add(specifiers.get(index));
			index++;
			if (tokens.get(index) == 12) { // token = :=
				assignList.add(":=");
				index++;
				expList = exp();
				if (expList.size() != 0) {
					assignList.addAll(expList);
					found = true;
					index++;
				}
			}
		}
		return assignList;
	}

	public ArrayList<String> exp() {
		boolean found = false;
		ArrayList<String> expList = new ArrayList<>();
		ArrayList<String> factorList = new ArrayList<>();
		factorList = factor();
		if (factorList.size() != 0) {
			expList.addAll(factorList);
			found = true;
			while ((tokens.get(index) == 13 || tokens.get(index) == 18) && found == true) { // token
				// =
				// +
				// or
				// *
				if (tokens.get(index)==13)
					expList.add("+");
				else if (tokens.get(index)==18)
					expList.add("*");
				index++;
				factorList = factor();
				if (factorList.size() != 0) {
					expList.addAll(factorList);
				} else
					found = false;
			}
		}
		return expList;
	}

	public ArrayList<String> factor() {
		boolean found = false;
		ArrayList<String> factorList = new ArrayList<>();
		ArrayList<String> expList = new ArrayList<>();
		if (tokens.get(index) == 17) { // token = id
			factorList.add(specifiers.get(index));
			found = true;
			index++;
		} else if (tokens.get(index) == 15) { // token = (
			factorList.add("(");
			index++;
			expList = exp();
			if (expList.size() != 0) {
				factorList.addAll(expList);
				if (tokens.get(index) == 16) { // token )
					factorList.add(")");
					found = true;
					index++;
				}
			}
		}
		return factorList;
	}
}
