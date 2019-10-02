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
    : additiveExpression (comparisonOperator additiveExpression)?
    ;

additiveExpression
    : (multiplicativeExpression additiveOperator)* multiplicativeExpression
    ;

multiplicativeExpression
    : (atomic multiplicativeOperator)* atomic
    ;

comparisonOperator
    : LANGLE | RANGLE | LE | GE | EXCL_EQ | EQEQ
    ;

additiveOperator
    : ADD | SUB
    ;

multiplicativeOperator
    : MULT | DIV | MOD
    ;

atomic
    : parenthesizedExpression
    | identifier
    | literalConstant
    // | lineStringLiteral
    ;

parenthesizedExpression
    : LPAREN expression RPAREN
    ;

identifier
    : Identifier
    ;

literalConstant
    : SUB? IntegerLiteral
    // | NullLiteral
    ;

lineStringLiteral
    : QUOTE_OPEN LineStringText QUOTE_CLOSE
    ;