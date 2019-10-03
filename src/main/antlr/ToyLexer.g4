lexer grammar ToyLexer;

import UnicodeClasses;

DelimitedComment
    : '/*' ( DelimitedComment | . )*? '*/'
      -> channel(HIDDEN)
    ;

LineComment
    : '//' ~[\u000A\u000D]*
      -> channel(HIDDEN)
    ;

WS
    : [\u0020\u0009\u000C]
      -> skip
    ;

NL
    : ('\u000A' | '\u000D' '\u000A')
      -> skip
    ;

// Separators / operations

LPAREN:     '(';
RPAREN:     ')';
LCURL:      '{';
RCURL:      '}';
LSQUARE:    '[';
RSQUARE:    ']';
MULT:       '*';
MOD:        '%';
DIV:        '/';
ADD:        '+';
SUB:        '-';
SEMICOLON:  ';';
ASSIGNMENT: '=';
LANGLE:     '<';
RANGLE:     '>';
LE:         '<=';
GE:         '>=';
EXCL_EQ:    '!=';
EQEQ:       '==';

QUOTE_OPEN: '"' -> pushMode(LineString) ;

// Keywords

PRINT: 'print';
VAL:   'val';
IF:    'if';
THEN:  'then';
ELSE:  'else';
WHILE: 'while';
DO:    'do';

// Literals

IntegerLiteral
    : '0'
      | DecDigitNoZero DecDigit*
    ;

fragment DecDigit
    : UNICODE_CLASS_ND
    ;

fragment DecDigitNoZero
    : UNICODE_CLASS_ND_NoZeros
    ;

fragment UNICODE_CLASS_ND_NoZeros
	: '\u0031'..'\u0039'
	| '\u0661'..'\u0669'
	| '\u06f1'..'\u06f9'
	| '\u07c1'..'\u07c9'
	| '\u0967'..'\u096f'
	| '\u09e7'..'\u09ef'
	| '\u0a67'..'\u0a6f'
	| '\u0ae7'..'\u0aef'
	| '\u0b67'..'\u0b6f'
	| '\u0be7'..'\u0bef'
	| '\u0c67'..'\u0c6f'
	| '\u0ce7'..'\u0cef'
	| '\u0d67'..'\u0d6f'
	| '\u0de7'..'\u0def'
	| '\u0e51'..'\u0e59'
	| '\u0ed1'..'\u0ed9'
	| '\u0f21'..'\u0f29'
	| '\u1041'..'\u1049'
	| '\u1091'..'\u1099'
	| '\u17e1'..'\u17e9'
	| '\u1811'..'\u1819'
	| '\u1947'..'\u194f'
	| '\u19d1'..'\u19d9'
	| '\u1a81'..'\u1a89'
	| '\u1a91'..'\u1a99'
	| '\u1b51'..'\u1b59'
	| '\u1bb1'..'\u1bb9'
	| '\u1c41'..'\u1c49'
	| '\u1c51'..'\u1c59'
	| '\ua621'..'\ua629'
	| '\ua8d1'..'\ua8d9'
	| '\ua901'..'\ua909'
	| '\ua9d1'..'\ua9d9'
	| '\ua9f1'..'\ua9f9'
	| '\uaa51'..'\uaa59'
	| '\uabf1'..'\uabf9'
	| '\uff11'..'\uff19'
	;

NullLiteral
    : 'null'
    ;

Identifier
    : (Letter | '_') (Letter | '_' | DecDigit)*
    ;

fragment Letter
    : UNICODE_CLASS_LL
    | UNICODE_CLASS_LM
    | UNICODE_CLASS_LO
    | UNICODE_CLASS_LT
    | UNICODE_CLASS_LU
    | UNICODE_CLASS_NL
    ;

mode LineString;

QUOTE_CLOSE
    : '"' -> popMode
    ;

LineStringText
    : ~('"')+
    ;
