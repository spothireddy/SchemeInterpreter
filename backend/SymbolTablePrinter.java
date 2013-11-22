package backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import intermediate.*;

/**
 * Schemer symbol table printer.
 * @author Ronald Mak
 */
public class SymbolTablePrinter 
{ 
	
	
	public void printScopes(ArrayList<SymbolTable> symbolTables)
	{
		System.out.println("\n==== Print Scopes ====\n");
		System.out.println("SIZE: " + symbolTables.size());
		for(SymbolTable st: symbolTables){
			st.print();
		}
	}
	
}
