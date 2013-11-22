import frontend.*;

public class Schemer3 
{
	public static void main(String[] args) 
	{
		Source source = new Source("input.lisp");
		Scanner scanner = new Scanner(source);
		Parser3 parser = new Parser3(scanner);
		
		parser.parse();
	}
}
