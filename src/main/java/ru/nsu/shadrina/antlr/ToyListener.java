// Generated from D:/GitHub/toy-language-interpreter/src/main/antlr/ru/nsu/antlr\Toy.g4 by ANTLR 4.7.2
package ru.nsu.shadrina.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ToyParser}.
 */
public interface ToyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code opExpr}
	 * labeled alternative in {@link ToyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOpExpr(ToyParser.OpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opExpr}
	 * labeled alternative in {@link ToyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOpExpr(ToyParser.OpExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link ToyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(ToyParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link ToyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(ToyParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link ToyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(ToyParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link ToyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(ToyParser.ParenExprContext ctx);
}