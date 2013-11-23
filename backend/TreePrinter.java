package backend;

import frontend.*;
import intermediate.*;

/**
 * Schemer parse tree printer.
 * @author Ronald Mak
 */
public class TreePrinter 
{
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
	
	/**
	 * Print a parse tree that represents a list.
	 * @param root the root of the parse tree.
	 * @param nestingLevel the scope nesting level
	 */
	private void printList(Node root, int nestingLevel)
	{
		// Indent according to the nesting level.
		System.out.println();
		for (int i = 0; i < nestingLevel; i++) System.out.print("  ");
		System.out.print("(");
		
		// Start with the tree root.
		Node currentNode = root;
		
		// Loop and print until we fall off the bottom of the tree.
		while (currentNode != null) {
			Node car = currentNode.getCar();
			// Recursively print a car (left) subtree.
			if (car != null) {
				if(car.getToken() != null && car.getToken().getText().equals("lambda")){
					System.out.println("****************************LAMBDA FROM TREE PRINT");
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
						if(currentNode.getTableReference() == null)
							System.out.println("NO REFERENCE");
						SymbolTable table = car.getTableReference();
						currentNode.setTableReference(table);
						car.setTableReference(null);
						currentNode.getTableReference().print();
						if(currentNode.getTableReference() != null)
							System.out.println("YES REFERENCE");

					}
				}
				printList(car, nestingLevel+1);
				
			}
			
			// Print a token.
			else {
				Token token = currentNode.getToken();
				if (token != null) System.out.print(token.getText());
			}
				
			// Use the cdr link to move down the tree.
			currentNode = currentNode.getCdr();
			
			if (currentNode != null) System.out.print(" ");
		}
		
		System.out.print(")");
	}
}
