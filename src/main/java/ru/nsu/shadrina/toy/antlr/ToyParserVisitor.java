// Generated from D:/GitHub/toy-language-interpreter/src/main/antlr\ToyParser.g4 by ANTLR 4.7.2
package ru.nsu.shadrina.toy.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ToyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ToyParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ToyParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(ToyParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ToyParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(ToyParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(ToyParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyParser#printExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintExpression(ToyParser.PrintExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ToyParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyParser#operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperation(ToyParser.OperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyParser#atomic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomic(ToyParser.AtomicContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyParser#literalConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralConstant(ToyParser.LiteralConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link ToyParser#lineStringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineStringLiteral(ToyParser.LineStringLiteralContext ctx);
}