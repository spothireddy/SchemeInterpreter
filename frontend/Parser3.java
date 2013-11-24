package frontend;

import java.util.ArrayList;
import java.util.Stack;


import intermediate.*;
import backend.*;

/**
 * A simple Scheme parser.
 * @author Ronald Mak and Team LiSiSa
 */
public class Parser3
{
	private Scanner scanner;
	private ArrayList<Node> trees;
	private ArrayList<SymbolTable> symbolTables;
	private SymbolTable table;
	Stack<SymbolTable> stack;
	private int nestedCount;
	private int referenceID;
	boolean addTableReference;
	String currEntry;
	SymbolTable symEntryTableLambdaReference;
	SymbolTable specialReferenceTable;
	SymbolTable globalTable;
	
	public Parser3(){
		
	}
	
	/**
	 * Constructor.
	 * @param scanner the simple Scheme scanner.
	 */
	public Parser3(Scanner scanner)
	{
		this.scanner = scanner;
		this.trees = new ArrayList<Node>();
		this.symbolTables = new ArrayList<SymbolTable>();
		SymtabEntry x = new SymtabEntry("car");
		nestedCount = 0;
		this.table = new SymbolTable(nestedCount);
		table.addSymbEntry("car", x);
		table.addSymbEntry("*", x);
		table.addSymbEntry("+", x);
		table.addSymbEntry("cdr", x);
		table.addSymbEntry("equal?", x);
		table.addSymbEntry("null?", x);


		stack = new Stack<SymbolTable>();
		referenceID = 0;
		addTableReference = false;
		currEntry = "";
		globalTable = table;
		for(SymbolTable s : symbolTables){
			if(s.getNestingCount() == 2){
				
			}
			
		}
		
		
		
	}
	
	public ArrayList<SymbolTable> getSymbolTables(){
		return symbolTables;
	}
	
	public SymbolTable getGlobalTable(){
		return symbolTables.get(symbolTables.size() - 1);
	}
	
	

	/**
	 * The parse method.
	 * This version also builds parse trees.
	 */
	public void parse()
	{
		Token token;
		
		// Loop to get tokens until the end of file.
		while ((token = nextToken()).getType() != TokenType.EOF) {
			TokenType tokenType = token.getType();
			
			if (tokenType == TokenType.SS_LPAREN) {
				trees.add(parseList());
			}
		}
		stack.push(table);
		while(!stack.empty()){
			symbolTables.add(stack.pop());
		}
		
		
		// Print the parse trees.
		TreePrinter treePrinter = new TreePrinter();
		for (Node tree : trees){
			treePrinter.print(tree);
			//treePrinter.printScopSymbolTable();
		}
		
		// Print the symbol table.
				SymbolTablePrinter symtabPrinter = new SymbolTablePrinter();
				symtabPrinter.printScopes(symbolTables);
		
				for (Node tree : trees){
					treePrinter.print(tree);
					//treePrinter.printScopSymbolTable();
				}
		
				
				symtabPrinter.printScopes(symbolTables);
		
		
	}
	
	/**
	 * Get and return the next token from the scanner.
	 * Enter identifiers and symbols into the symbol table.
	 * @return the next token.
	 */
	private Token nextToken()
	{
		Token token = scanner.nextToken();
		
		
		return token;
	}
	
	
	
	/**
	 * Parse a list and build a parse tree.
	 * @return the root of the tree.
	 */
	private Node parseList()
	{
		Node root = new Node();
		Node currentNode = null;
		
		
		// Get the first token after the opening left parenthesis.
		Token token = nextToken();
		TokenType tokenType = token.getType();
		
		// Loop to get tokens until the closing right parenthesis.
		while (tokenType != TokenType.SS_RPAREN) {
			
			
			
			if(token.getText() != null && (token.getText().equals("define"))){
				nestedCount = 1;
				stack.push(table);
				System.out.println("*******************PUSHED TABLEvFROM DEFINE********");
				while(stack.size() > 2){
					symbolTables.add(stack.pop());
				}
				
				if(stack.size() == 2){
					table = stack.pop();
				}
				else if(stack.size() == 1){
					table = new SymbolTable(nestedCount);
				}
				
				
				
			}
			
			if(token.getText() != null && (token.getText().equals("lambda") || token.getText().equals("let")
					|| token.getText().equals("letrec") || token.getText().equals("let*")) ){
				
				if(token.getText().equals("lambda")){
					symEntryTableLambdaReference = table;
				}
				stack.push(table);
				System.out.println("*******************PUSHED TABLE********");
				nestedCount = nestedCount + 1;
				table = new SymbolTable(nestedCount);
				specialReferenceTable = table;
				
			}

			
			// Set currentNode initially to the root,
			// then move it down the cdr links.
			if (currentNode == null) {
				currentNode = root;
			}
			else {
				Node newNode = new Node();
				currentNode.setCdr(newNode);
				currentNode = newNode;
		
				
				
			}
			
			if(tokenType == TokenType.SYMBOL){
				
			}
			
			
			// Enter identifiers and symbols into the symbol table.
			if ((tokenType == TokenType.IDENTIFIER && stack.get(0).getSymbEntry(token.getText()) == null)) 
			{
				if(stack.size() > 1 && stack.get(1).getSymbEntry(token.getText()) != null){
					
				}else{
					String text = token.getText();
					SymtabEntry x = new SymtabEntry(text);
					table.addSymbEntry(text, x);
				}
				
				
				//System.out.println("SIZeeEEE: " + symTabLambdaReference.size());
				//System.out.println("ADDED symbol to table********************* ::       "+ text);
				//System.out.println("SIZeeEEE: " + symTabLambdaReference.size());
			}
			
			// Left parenthesis: Parse a sublist and return the root
			// of the subtree which is set as the car of the current node.
			// Otherwise, set the token as the data of the current node.
			if (tokenType == TokenType.SS_LPAREN) {
				currentNode.setCar(parseList());
		
			}
			else {
				currentNode.setToken(token);
				
				if(addTableReference){
					
					//currentNode.setReference(table.getSymbEntry(currentNode.getToken().getText()));
					currEntry = currentNode.getToken().getText();
					//System.out.println("__________StuPID SHIt: "+ currentNode.getToken().getText());
					SymtabEntry x = table.getSymbEntry(currEntry);
					currentNode.setEntryReferecne(x);
					addTableReference = false;
					//System.out.println("+++++++++++++++++++++++++++++++++++++++++++++Added reference");
				}
				if(currentNode.getToken().getText().equals("lambda")){
					SymtabEntry x = table.getSymbEntry(currEntry);
					//System.out.println("__________StuPID SHIt LMABDA: "+ symTabLambdaReference.size());
					//System.out.println("__________StuPID SHIt LMABDA: "+ currentNode.getToken().getText());
					symEntryTableLambdaReference.getSymbEntry(currEntry).setLambdaReference(currentNode);
					currentNode.setEntryReferecne(symEntryTableLambdaReference.getSymbEntry(currEntry));
					//System.out.println("SET THE REFERENCE FOR LABDA **********************" + 					symTabLambdaReference.getSymbEntry(currEntry).getName());
				}
				if(currentNode.getToken().getText().equals("lambda") || currentNode.getToken().getText().equals("let")
						|| currentNode.getToken().getText().equals("letrec") || currentNode.getToken().getText().equals("let*")){
					currentNode.setTableReference(specialReferenceTable);
				}
				if(currentNode != null && currentNode.getToken().getText().equals("define")){
					System.out.println("------------------------------------IT IS EQUAL TO DEFINE BEFORE-------------------");
					addTableReference = true;
				}
				if(currentNode != null && currentNode.getToken().getType().equals(TokenType.SYMBOL) || stack.get(0).getSymbEntry(currentNode.getToken().getText()) != null){
					System.out.println("------------------------------------SHIT IN TABLE GLOBAL");
					SymbolTable global = stack.get(0);
					SymtabEntry e = global.getSymbEntry(currentNode.getToken().getText());
					currentNode.setEntryReferecne(e);
					
					
					
				}
				if(currentNode != null && currentNode.getToken().getType().equals(TokenType.IDENTIFIER)){
					
					if(stack.size() > 1){
						//System.out.println("STACK SIZEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE: " + stack.size());
						SymbolTable methods = stack.get(1);
						SymtabEntry e = methods.getSymbEntry(currentNode.getToken().getText());
						if(e != null){
							//System.out.println("THIS IS IN THE FIRST THING&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
							currentNode.setEntryReferecne(e);
						}
					}
					
					
					
					
				}
				//if(currentNode != null && currentNode.getToken().getText().equals("lambda")){
					//System.out.println("------------------------------------IT IS EQUAL TO LAMBDA-------------------");
					//table.setLambdaReference(currentNode);
				//}
				
			}
			
			// Get the next token for the next time around the loop.
			token = nextToken();
			tokenType = token.getType();
		}
		
		
		
		return root;  // of the parse tree
	}
}
