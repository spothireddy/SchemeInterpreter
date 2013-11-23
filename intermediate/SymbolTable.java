package intermediate;

import java.util.Map.Entry;
import java.util.TreeMap;

public class SymbolTable {

	private TreeMap<String, SymtabEntry> symtab;
	private int nestingCount;
	private Node lambdaReference;
	
	public SymbolTable(int nestingLevel){
		this.symtab = new TreeMap<String, SymtabEntry>();
		this.nestingCount = nestingLevel;
		this.lambdaReference = null;
	}
	
	/**
	 * Adds a symbol entry to the symbol table
	 */
	public void addSymbEntry(String t, SymtabEntry s){
		symtab.put(t, new SymtabEntry(t));
	}
	
	public int size(){
		return symtab.size();
	}
	
	public SymtabEntry getSymbEntry(String t){
		return symtab.get(t);
	}
	
	public int getNestingCount(){
		return nestingCount;
	}
	
	public void setLambdaReference(Node n){
		lambdaReference = n;
	}
	
	public Node getLambdaReference(){
		return lambdaReference;
	}
	
	/**
	 * Print a symbol table.
	 * @param symtab the symbol table to print.
	 */
	public void print()
	{
		System.out.println("\n==== SYMBOL TABLE ==NESTING COUNT: " + this.getNestingCount()+"==\n");
		for (Entry<String, SymtabEntry> entry : symtab.entrySet()) {
		    String key = entry.getKey();

		    System.out.println(key);
		}
		/**
		Set<String> names = symtab.keySet();
		names.toString();
		
		Iterator<String> it = names.iterator();
		
		// Iterate over the alphabetized contents.
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		**/

	}
}
