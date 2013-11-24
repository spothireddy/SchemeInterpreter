package backend;

import java.util.ArrayList;

import frontend.*;
import intermediate.*;

/**
 * Schemer parse tree printer.
 * @author Ronald Mak
 */
public class TreePrinter 
{
	
	public Parser3 parser = new Parser3();
	public ArrayList<SymbolTable> scopeSymbolTables;
	/**
	 * Print a parse tree.
	 * @param root the parse tree root.
	 */
	public void print(Node root)
	{
		System.out.println("\n==== TREE ====");
		printList(root, 0);
		System.out.println();
	}
	
	public SymtabEntry dealWithSymbolReferences(String indentifier){
		ArrayList<SymbolTable> newTable = new ArrayList<SymbolTable>();
		int size = scopeSymbolTables.size();
		if(size > 1){
			for(int i = 0; i < size - 1; i++){
				newTable.add(scopeSymbolTables.get(i));
			}
		}


		for(SymbolTable st: newTable){
			if(st.getSymbEntry(indentifier) != null){
				//System.out.println("THE TABLE HAS THE SYMBOLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
				return st.getSymbEntry(indentifier);
			}
		}
		
		return null;
	}
	
	public void printScopSymbolTable(){
		SymbolTablePrinter symtabPrinter = new SymbolTablePrinter();
		symtabPrinter.printScopes(scopeSymbolTables);
	}
	

	/**
	 * Print a parse tree that represents a list.
	 * @param root the root of the parse tree.
	 * @param nestingLevel the scope nesting level
	 */
	private void printList(Node root, int nestingLevel)
	{
		// Indent according to the nesting level.
		if(nestingLevel == 0){
			scopeSymbolTables = new ArrayList<SymbolTable>();
		}
		//System.out.println("NESTING LEVELLLLLLLLLLLLLLLLLLLLLL" + nestingLevel);
		System.out.println();
		for (int i = 0; i < nestingLevel; i++) System.out.print("  ");
		//System.out.println("NESTING LEVEL: " + nestingLevel);
		System.out.print("(");
		
		// Start with the tree root.
		Node currentNode = root;
		
		// Loop and print until we fall off the bottom of the tree.
		while (currentNode != null) {
			Node car = currentNode.getCar();
			// Recursively print a car (left) subtree.
			if (car != null) {
					
				if(car.getToken() != null && car.getToken().getText().equals("lambda")){
					//System.out.println("****************************LAMBDA FROM TREE PRINT");
					if(car.getEntryReferecne() != null){
						SymtabEntry j = car.getEntryReferecne();
						j.setLambdaReference(currentNode);
						car.setEntryReferecne(null);
					}
				}
				
				if(car.getToken() != null && (car.getToken().getText().equals("lambda")
						|| car.getToken().getText().equals("let") || car.getToken().getText().equals("letrec")
						|| car.getToken().getText().equals("let*"))){
					if(car.getTableReference() != null){
						//if(currentNode.getTableReference() == null)
							//System.out.println("NO REFERENCE");
						SymbolTable table = car.getTableReference();
						currentNode.setTableReference(table);
						car.setTableReference(null);
						//currentNode.getTableReference().print();
						scopeSymbolTables.add(table);
						//System.out.println("ADDED TABLE TO SCOPEEEEEEEEEEEEE");
						//if(currentNode.getTableReference() != null)
							//System.out.println("YES REFERENCE");

					}
				}
				
				if(car.getToken() != null && car.getToken().getType().equals(TokenType.IDENTIFIER)){
					//System.out.println("IDENTIEFERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRCAAAAAAAAAAAAAAAAAAR");
				}
				Node cdr = currentNode.getCdr();
				//if(cdr.getToken() != null && cdr.getToken().getType().equals(TokenType.IDENTIFIER)){
					//System.out.println("IDENTIEFERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRCDDDDDDDDDDRRRR");
				//}
				printList(car, nestingLevel+1);
				
				
			}
			
			// Print a token.
			else {
				Token token = currentNode.getToken();
				if (token != null) System.out.print(token.getText());

				if(token != null && token.getType().equals(TokenType.IDENTIFIER)){
					if(currentNode.getEntryReferecne() == null){
						//System.out.println("THIIIIIIIIIIIIIIIIIIS IS NULL");
						//System.out.println("   IDENTIEFERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRCDDDDDDDDDDRRRR is    " + currentNode.getToken().getText());
						SymtabEntry e = dealWithSymbolReferences(currentNode.getToken().getText());
						if(e != null && scopeSymbolTables.size() > 1){
							//System.out.println("Table has entry");
							SymbolTable table = scopeSymbolTables.get(scopeSymbolTables.size() - 1);
							table.deleteSymbolEntry(currentNode.getToken().getText());
							currentNode.setEntryReferecne(e);
							//System.out.println("THIS REFERENCEEEESSSS   " + e.getName());
							
						}
						else if(scopeSymbolTables.size() <= 1){
							//System.out.println("TreePrinter.....");
						}
						else{
							
						}
					}
				}
				
			}
				
			// Use the cdr link to move down the tree.
			currentNode = currentNode.getCdr();
			
			if (currentNode != null) System.out.print(" ");
		}
		
		System.out.print(")");
	}
}
