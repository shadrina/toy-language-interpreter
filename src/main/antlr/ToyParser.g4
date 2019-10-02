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
    : atomic (operation expression)?
    ;

operation
    : MULT | MOD | DIV | ADD | SUB
    ;

atomic
    : literalConstant
    // | lineStringLiteral
    ;

literalConstant
    : IntegerLiteral
    // | NullLiteral
    ;

lineStringLiteral
    : QUOTE_OPEN LineStringText QUOTE_CLOSE
    ;