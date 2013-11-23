package intermediate;

/**
 * Schemer symbol table entry.
 * @author Ronald Mak and Team LiSiSa
 */
public class SymtabEntry 
{
	private String name;
	private Node lambdaReference;
	
	/**
	 * Constructor.
	 * @param the name of what to enter.
	 */
	public SymtabEntry(String name)
	{
		this.name = name;
		
	}
	
	public void setLambdaReference(Node n){
		lambdaReference = n;
	}
	
	public Node getLambdaReference(){
		return lambdaReference;
	}
	
	
	public String getName() { return name; }
	
	public Node getRoot(){
		return lambdaReference;
	}
	

}
