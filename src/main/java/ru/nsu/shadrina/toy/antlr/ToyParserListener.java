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
	 * Enter a parse tree produced by {@link ToyParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterOperation(ToyParser.OperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ToyParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitOperation(ToyParser.OperationContext ctx);
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