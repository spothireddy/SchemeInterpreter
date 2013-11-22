package frontend;

/**
 * Schemer token types.
 * @author Ronald Mak
 *
 */
public enum TokenType 
{
	SYMBOL,
	IDENTIFIER,
	NUMBER,
	TRUE, FALSE,
	EOF,
	
	// Keywords
	KW_AND, KW_BEGIN, KW_COND, KW_DEFINE, KW_ELSE, KW_IF, 
	KW_LAMBDA, KW_LET, KW_LETREC, KW_LET_STAR, KW_OR, KW_QUOTE,
	
	// Special symbols
	SS_LPAREN, SS_RPAREN, SS_LBRACKET, SS_RBRACKET, SS_LBRACE, SS_RBRACE,
	SS_SEMICOLON, SS_COMMA, SS_PERIOD, SS_QUOTE, SS_DOUBLE_QUOTE, 
	SS_HASH, SS_BACK_SLASH
}