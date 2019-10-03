parser grammar ToyParser;

options { tokenVocab = ToyLexer; }

file
    : statements EOF
    ;

statements
    : (statement SEMICOLON)*
    ;

statement
    : declaration
    | assignment
    | printExpression
    | ifExpression
    | whileExpression
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

ifExpression
    : IF LPAREN expression RPAREN THEN LCURL statements RCURL (ELSE LCURL statements RCURL)?
    ;

whileExpression
    : whileDoExpression
    | doWhileExpression
    ;

whileDoExpression
    : WHILE LPAREN expression RPAREN DO LCURL statements RCURL
    ;

doWhileExpression
    : DO LCURL statements RCURL WHILE LPAREN expression RPAREN
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