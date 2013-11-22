package intermediate;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class SymbolTable {

	private TreeMap<String, SymtabEntry> symtab;
	
	public SymbolTable(){
		this.symtab = new TreeMap<String, SymtabEntry>();
	}
	
	/**
	 * Adds a symbol entry to the symbol table
	 */
	public void addSymbEntry(String t, SymtabEntry s){
		symtab.put(t, new SymtabEntry(t));
	}
	
	
	/**
	 * Print a symbol table.
	 * @param symtab the symbol table to print.
	 */
	public void print()
	{
		System.out.println("\n==== SYMBOL TABLE ====\n");
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
