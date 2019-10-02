parser grammar ToyParser;

options { tokenVocab = ToyLexer; }

file
    : (expression SEMICOLON)* EOF
    ;

expression
    : atomic (operation atomic)*
    ;

operation
    : MULT | MOD | DIV | ADD | SUB
    ;

atomic
    : parenthesizedExpression
    | printExpression
    | lineStringLiteral
    | literalConstant
    ;

parenthesizedExpression
    : LPAREN expression RPAREN
    ;

printExpression
    : PRINT LPAREN expression RPAREN
    ;

literalConstant
    : BooleanLiteral
    | IntegerLiteral
    | HexLiteral
    | BinLiteral
    | RealLiteral
    | NullLiteral
    | LongLiteral
    ;

lineStringLiteral
    : QUOTE_OPEN LineStringText* QUOTE_CLOSE
    ;