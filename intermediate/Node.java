package intermediate;

import frontend.*;

/**
 * Schemer parse tree node.
 * @author Ronald Mak
 */
public class Node 
{
	private Token token;  // data field
	private Node car;     // left child
	private Node cdr;     // right child
	private SymbolTable tableReference;
	private SymtabEntry entryReference;
	
	public Node()
	{
		this.token = null;
		this.car  = null;
		this.cdr = null;
		this.tableReference = null;
		this.entryReference = null;
	}
	
	public Token getToken() { return token; }
	public Node  getCar()   { return car;  }
	public Node  getCdr()   { return cdr; }
	public SymbolTable getTableReference(){return tableReference;}
	public SymtabEntry getEntryReferecne(){return entryReference;}
	
	public void setToken(Token token) { this.token = token; }
	public void setCar(Node left)     { this.car  = left;  }
	public void setCdr(Node right)    { this.cdr = right; }
	public void setTableReference(SymbolTable s)    { this.tableReference = s; }
	public void setEntryReferecne(SymtabEntry se){this.entryReference = se;}
}
