package pkj1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LexicalAnalyzer {
		
//	File progCode = new File("hourstomins.txt");
	File progCode;
	File tokensCoding = new File("TokensCoding.txt");
	File tokensTable = new File("TokensTable.txt");
	String str = new String();
	String progName = new String();
	String idlist = new String();
	String readlist = new String();
	String writelist = new String();
	String assignlist = new String();
	StringBuilder sb = new StringBuilder();
	StringBuilder sbName = new StringBuilder();
	StringBuilder sbIdlist = new StringBuilder();
	StringBuilder readList = new StringBuilder();
	StringBuilder writeList = new StringBuilder();
	StringBuilder assignList = new StringBuilder();
	ArrayList<String> tokenArray = new ArrayList<String>();
	String tokenCode = new String();
	
	public void LexicalAnalysis(String fileName){
//	public void LexicalAnalysis(){
	
	try{
		
	PrintWriter pw = new PrintWriter(tokensTable);
	pw.println("Line\t\tToken\t\tToken Specifier");
	
	Scanner in = new Scanner(tokensCoding);
	in.useDelimiter("\n");
	
	while ( in.hasNext() ){
		String tokenline = in.nextLine();
		String[] tokenstr = tokenline.split(" ");
		
		if( tokenstr.length == 2 ){
			tokenArray.add(tokenstr[0]);
			tokenArray.add(tokenstr[1]);
		}
	}
	
	File progCode = new File(fileName);
	Scanner input = new Scanner(progCode);
	String line = input.next();
	char[] charArray = line.toCharArray();
	
	for( int i=0; i<charArray.length; i++ ){
		
		sb.append(charArray[i]);
		str = sb.toString();
		
		if ( str.equalsIgnoreCase("PROGRAM") ){
			for ( int j=i+1; j<line.indexOf("VAR"); j++ ){
				sbName.append(charArray[j]);
				progName = sbName.toString();
			}
			for( int k=0; k<tokenArray.size(); k++ ){
				
				if( str.equalsIgnoreCase(tokenArray.get(k)) ){
					tokenCode = tokenArray.get(k+1);
					pw.println( "1\t\t" + tokenCode );
				}
			}
			
			i = line.indexOf("VAR")-1;
			sb.delete(0, sb.length());
		}
		
		else if ( str.equalsIgnoreCase("VAR") ){
			for ( int j=i+1; j<line.indexOf("BEGIN"); j++ ){
				sbIdlist.append(charArray[j]);
				idlist = sbIdlist.toString();
			}
			
			for( int k=0; k<tokenArray.size(); k++ ){
				
				if( str.equalsIgnoreCase(tokenArray.get(k)) ){
					tokenCode = tokenArray.get(k+1);
					pw.println( "2\t\t" + tokenCode );
				}
			}
			
			String[] id = idlist.split(",");
			pw.println("3\t\t17\t\t" + id[0]);
			for( int s=1; s<id.length; s++ ){
				pw.println("3\t\t17\t\t" + id[s]);
			}
			
			i = line.indexOf("BEGIN")-1;
			sb.delete(0, sb.length());
		}
		
		else if ( str.equalsIgnoreCase("BEGIN") ){
			
			for( int k=0; k<tokenArray.size(); k++ ){
				
				if( str.equalsIgnoreCase(tokenArray.get(k)) ){
					tokenCode = tokenArray.get(k+1);
					pw.println( "4\t\t" + tokenCode );
				}
			}
			
			sb.delete(0, sb.length());
		}
		
		else if ( str.equalsIgnoreCase("READ") ){
			int j;
			readList.delete(0, readList.length());
			for ( j=i+1; j<line.indexOf(")",i)+1; j++ ){
				readList.append(charArray[j]);
				readlist = readList.toString();
			}
			
			for( int k=0; k<tokenArray.size(); k++ ){
				
				if( str.equalsIgnoreCase(tokenArray.get(k)) ){
					tokenCode = tokenArray.get(k+1);
					pw.println( "5\t\t" + tokenCode );
				}
			}
			pw.println("5\t\t15\t\t(");
			String readline = readlist.substring(readlist.indexOf('(')+1, readlist.indexOf(')'));
			String[] read = readline.split(",");
			pw.println("5\t\t17\t\t" + read[0]);
			for( int s=1; s<read.length; s++ ){
				pw.println("5\t\t17\t\t" + read[s]);
			}
			pw.println("5\t\t16\t\t)");
			
			i = j-1;
			sb.delete(0, sb.length());
		}
		
		else if ( str.equalsIgnoreCase("WRITE") ){
			int j;
			writeList.delete(0, writeList.length());
			for ( j=i+1; j<line.indexOf(")",i)+1; j++ ){
				writeList.append(charArray[j]);
				writelist = writeList.toString();
			}
			
			for( int k=0; k<tokenArray.size(); k++ ){
				
				if( str.equalsIgnoreCase(tokenArray.get(k)) ){
					tokenCode = tokenArray.get(k+1);
					pw.println( "10\t\t" + tokenCode );
				}
			}
			
			pw.println("10\t\t15\t\t(");
			String writeline = writelist.substring(writelist.indexOf('(')+1, writelist.indexOf(')'));
			String[] write = writeline.split(",");
			pw.println("10\t\t17\t\t" + write[0]);
			for( int s=1; s<write.length; s++ ){
				pw.println("10\t\t17\t\t" + write[s]);
			}
			pw.println("10\t\t16\t\t)");
			
			i = j-1;
			sb.delete(0, sb.length());
		}
		
		else if ( str.equalsIgnoreCase("END.")){
			
			for( int k=0; k<tokenArray.size(); k++ ){
				
				if( str.equalsIgnoreCase(tokenArray.get(k)) ){
					tokenCode = tokenArray.get(k+1);
					pw.println( "11\t\t" + tokenCode );
				}
			}
			
			sb.delete(0, sb.length());
		}
		
		else if ( str.contains("=") && str.endsWith(";") ){
			
			char[] assignArray = str.toCharArray();
			
			for ( int m = 0; m<=str.indexOf(";"); m++ ){
				assignList.append(assignArray[m]);
				assignlist = assignList.toString();
			}
				pw.println("6\t\t17\t\t" + assignlist.substring(0, assignlist.indexOf('=')));
				pw.println("6\t\t12\t\t");

				if( assignlist.contains("+") ){
					
					if( assignArray[assignlist.indexOf('=')+3] == '(' ){
						pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('=')+1, assignlist.indexOf('+')));
						pw.println("6\t\t13\t\t");
						pw.println("6\t\t15\t\t(");
						
							if( assignArray[assignlist.indexOf('=')+5] == '+' ){
								pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('(')+1, assignlist.indexOf('(')+2));
								pw.println("6\t\t13\t\t");
							}
						
							else if( assignArray[assignlist.indexOf('(')+2] == '*' ){
							pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('(')+1, assignlist.indexOf('*')));
							pw.println("6\t\t18\t\t");
							}
							
						pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf(')')-1, assignlist.indexOf(')')));
						pw.println("6\t\t16\t\t)");
						}
					
					else if ( assignArray[assignlist.indexOf('=')+1] == '(' && assignArray[assignlist.indexOf('=')+7] != '(' ){
						pw.println("6\t\t15\t\t(");
						
						if( assignArray[assignlist.indexOf('=')+3] == '+' ){
							pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('(')+1, assignlist.indexOf('(')+2));
							pw.println("6\t\t13\t\t");
							pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('(')+3, assignlist.indexOf(')')));
							pw.println("6\t\t16\t\t)");
						}
					
						else if( assignArray[assignlist.indexOf('(')+2] == '*' ){
						pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('(')+1, assignlist.indexOf('*')));
						pw.println("6\t\t18\t\t");
						pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('(')+2, assignlist.indexOf(')')));
						pw.println("6\t\t16\t\t)");
						}
						
						if ( assignArray[assignlist.indexOf(')')+1] == '+' ){
							pw.println("6\t\t13\t\t");
							pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf(')')+2, assignlist.indexOf(';')));
						}
						
						else if ( assignArray[assignlist.indexOf(')')+1] == '*' ){
							pw.println("6\t\t18\t\t");
							pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('+'), assignlist.indexOf(';')));
						}
					}
					
					else if ( assignArray[assignlist.indexOf('=')+1] == '(' && assignArray[assignlist.indexOf('=')+7] == '(' ){
						pw.println("6\t\t15\t\t(");
						
						if ( assignArray[assignlist.indexOf('=')+3] == '+' ){
							pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('(')+1, assignlist.indexOf('+')));
							pw.println("6\t\t13\t\t");
							pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('+')+1, assignlist.indexOf(')')));
							pw.println("6\t\t16\t\t)");
							
							if ( assignArray[assignlist.indexOf(')')+1] == '+' )
								pw.println("6\t\t13\t\t");
							else if ( assignArray[assignlist.indexOf(')')+1] == '*' )
								pw.println("6\t\t18\t\t");
							
							if ( assignArray[assignlist.indexOf(')')+4] == '+' ){
								pw.println("6\t\t15\t\t(");
								pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf(')')+3, assignlist.indexOf(')')+4));
								pw.println("6\t\t13\t\t");
								pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf(')')+5, assignlist.indexOf(')')+6));
								pw.println("6\t\t16\t\t)");
							}
							else if ( assignArray[assignlist.indexOf(')')+4] == '*' ){
								pw.println("6\t\t15\t\t(");
								pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf(')')+3, assignlist.indexOf(')')+4));
								pw.println("6\t\t18\t\t");
								pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf(')')+5, assignlist.indexOf(')')+6));
								pw.println("6\t\t16\t\t)");
							}
						}
					}
					
					else{
						pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('=')+1, assignlist.indexOf('+')));
						pw.println("6\t\t13\t\t");
						pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('+')+1, assignlist.length()-1));
					}
					
					pw.println("6\t\t11\t\t");
				}
				
				else if( assignlist.contains("*") ){
					pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('=')+1, assignlist.indexOf('*')));
					pw.println("6\t\t18\t\t");
					
					if( assignArray[assignlist.indexOf('=')+3] == '('){
						pw.println("6\t\t15\t\t(");
						
							if( assignArray[assignlist.indexOf('=')+5] == '+' ){
								pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('(')+1, assignlist.indexOf('(')+2));
								pw.println("6\t\t13\t\t");
							}
						
						else if( assignlist.indexOf('(')+2 == '*' ){
							pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('(')+1, assignlist.indexOf('*')));
							pw.println("6\t\t18\t\t");
							}
							
						pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf(')')-1, assignlist.indexOf(')')));
						pw.println("6\t\t16\t\t)");
						}
					
					else
						pw.println("6\t\t17\t\t" + assignlist.substring(assignlist.indexOf('*')+1, assignlist.length()-1));
					
					pw.println("6\t\t11\t\t");
				}
				
				assignList.delete(0, assignList.length());
				assignlist = assignList.toString();
				sb.delete(0, sb.length());
		}	
	}
	
	pw.close();
	}
	catch(FileNotFoundException ex){
		System.out.println("Unable to open file\n" + "File " + progCode + " Not found!");
	}
	}
	
}