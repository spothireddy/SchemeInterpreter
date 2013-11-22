package frontend;

import java.util.ArrayList;
import java.util.TreeMap;

import intermediate.*;
import backend.*;

/**
 * A simple Scheme parser.
 * @author Ronald Mak
 */
public class Parser3
{
	private Scanner scanner;
	private ArrayList<Node> trees;
	private ArrayList<SymbolTable> symbolTables;
	private int stackNumPrev;
	private int stackNumNow;
	private SymbolTable table;
	
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
		this.table = new SymbolTable();
		table.addSymbEntry("car", x);
		stackNumPrev = 0;
		stackNumNow = 0;
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
		symbolTables.add(table);
		// Print the symbol table.
		SymbolTablePrinter symtabPrinter = new SymbolTablePrinter();
		symtabPrinter.printScopes(symbolTables);
		
		// Print the parse trees.
		TreePrinter treePrinter = new TreePrinter();
		for (Node tree : trees) treePrinter.print(tree);
	}
	
	/**
	 * Get and return the next token from the scanner.
	 * Enter identifiers and symbols into the symbol table.
	 * @return the next token.
	 */
	private Token nextToken()
	{
		Token token = scanner.nextToken();
		TokenType tokenType = token.getType();
		if(token.getText() != null && (token.getText().equals("define") 
				|| token.getText().equals("lambda") || token.getText().equals("let")
				|| token.getText().equals("letrec") || token.getText().equals("let*")) ){
			stackNumNow = stackNumNow + 1;
		}
		
		// Enter identifiers and symbols into the symbol table.
		if ((tokenType == TokenType.IDENTIFIER) ||
			(tokenType == TokenType.SYMBOL)) 
		{
			String text = token.getText();
			this.addToSymbolTable(text);
		}
		
		return token;
	}
	
	public void addToSymbolTable(String text){
		if(stackNumNow != stackNumPrev){
			symbolTables.add(table);
			stackNumPrev = stackNumNow;
			table = new SymbolTable();
		}
		SymtabEntry x = new SymtabEntry(text);
		table.addSymbEntry(text, x);

		
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
			
			// Left parenthesis: Parse a sublist and return the root
			// of the subtree which is set as the car of the current node.
			// Otherwise, set the token as the data of the current node.
			if (tokenType == TokenType.SS_LPAREN) {
				currentNode.setCar(parseList());
			}
			else {
				currentNode.setToken(token);
			}
			
			// Get the next token for the next time around the loop.
			token = nextToken();
			tokenType = token.getType();
		}
		
		return root;  // of the parse tree
	}
}
