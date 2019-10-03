// Generated from D:/GitHub/toy-language-interpreter/src/main/antlr\ToyParser.g4 by ANTLR 4.7.2
package ru.nsu.shadrina.toy.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ToyParser}.
 */
public interface ToyParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ToyParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(ToyParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(ToyParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(ToyParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(ToyParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ToyParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ToyParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ToyParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ToyParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ToyParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ToyParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#printExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrintExpression(ToyParser.PrintExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#printExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrintExpression(ToyParser.PrintExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void enterIfExpression(ToyParser.IfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void exitIfExpression(ToyParser.IfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ToyParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ToyParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(ToyParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(ToyParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(ToyParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(ToyParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void enterComparisonOperator(ToyParser.ComparisonOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void exitComparisonOperator(ToyParser.ComparisonOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#additiveOperator}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveOperator(ToyParser.AdditiveOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#additiveOperator}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveOperator(ToyParser.AdditiveOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#multiplicativeOperator}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeOperator(ToyParser.MultiplicativeOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#multiplicativeOperator}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeOperator(ToyParser.MultiplicativeOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#atomic}.
	 * @param ctx the parse tree
	 */
	void enterAtomic(ToyParser.AtomicContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#atomic}.
	 * @param ctx the parse tree
	 */
	void exitAtomic(ToyParser.AtomicContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(ToyParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(ToyParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(ToyParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(ToyParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#literalConstant}.
	 * @param ctx the parse tree
	 */
	void enterLiteralConstant(ToyParser.LiteralConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#literalConstant}.
	 * @param ctx the parse tree
	 */
	void exitLiteralConstant(ToyParser.LiteralConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link ToyParser#lineStringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterLineStringLiteral(ToyParser.LineStringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#lineStringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitLineStringLiteral(ToyParser.LineStringLiteralContext ctx);
}