parser grammar ToyParser;

options { tokenVocab = ToyLexer; }

file
    : (statement SEMICOLON)* EOF
    ;

statement
    : declaration
    | assignment
    | printExpression
    ;

declaration
    : VAL Identifier ASSIGNMENT expression
    ;

assignment
    : Identifier ASSIGNMENT expression
    ;

printExpression
    : PRINT LPAREN expression RPAREN
    ;

expression
    : atomic (operation atomic)*
    ;

operation
    : MULT | MOD | DIV | ADD | SUB
    ;

atomic
    : parenthesizedExpression
    | lineStringLiteral
    | literalConstant
    ;

parenthesizedExpression
    : LPAREN expression RPAREN
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