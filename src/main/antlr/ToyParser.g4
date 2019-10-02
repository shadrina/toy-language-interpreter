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
    : (term additiveOperator)* term
    ;

term
    : (atomic multiplicativeOperator)* atomic
    ;

additiveOperator
    : ADD | SUB
    ;

multiplicativeOperator
    : MULT | DIV | MOD
    ;

atomic
    : parenthesizedExpression
    | literalConstant
    // | lineStringLiteral
    ;

parenthesizedExpression
    : LPAREN expression RPAREN
    ;

literalConstant
    : IntegerLiteral
    // | NullLiteral
    ;

lineStringLiteral
    : QUOTE_OPEN LineStringText QUOTE_CLOSE
    ;