// Generated from MLP.g4 by ANTLR 4.13.0
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MLPParser}.
 */
public interface MLPListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MLPParser#inicio}.
	 * @param ctx the parse tree
	 */
	void enterInicio(MLPParser.InicioContext ctx);
	/**
	 * Exit a parse tree produced by {@link MLPParser#inicio}.
	 * @param ctx the parse tree
	 */
	void exitInicio(MLPParser.InicioContext ctx);
	/**
	 * Enter a parse tree produced by {@link MLPParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(MLPParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link MLPParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(MLPParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link MLPParser#comando}.
	 * @param ctx the parse tree
	 */
	void enterComando(MLPParser.ComandoContext ctx);
	/**
	 * Exit a parse tree produced by {@link MLPParser#comando}.
	 * @param ctx the parse tree
	 */
	void exitComando(MLPParser.ComandoContext ctx);
	/**
	 * Enter a parse tree produced by {@link MLPParser#condicional}.
	 * @param ctx the parse tree
	 */
	void enterCondicional(MLPParser.CondicionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MLPParser#condicional}.
	 * @param ctx the parse tree
	 */
	void exitCondicional(MLPParser.CondicionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link MLPParser#iterativo}.
	 * @param ctx the parse tree
	 */
	void enterIterativo(MLPParser.IterativoContext ctx);
	/**
	 * Exit a parse tree produced by {@link MLPParser#iterativo}.
	 * @param ctx the parse tree
	 */
	void exitIterativo(MLPParser.IterativoContext ctx);
	/**
	 * Enter a parse tree produced by {@link MLPParser#atribuicao}.
	 * @param ctx the parse tree
	 */
	void enterAtribuicao(MLPParser.AtribuicaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link MLPParser#atribuicao}.
	 * @param ctx the parse tree
	 */
	void exitAtribuicao(MLPParser.AtribuicaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link MLPParser#condicao}.
	 * @param ctx the parse tree
	 */
	void enterCondicao(MLPParser.CondicaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link MLPParser#condicao}.
	 * @param ctx the parse tree
	 */
	void exitCondicao(MLPParser.CondicaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link MLPParser#expressao}.
	 * @param ctx the parse tree
	 */
	void enterExpressao(MLPParser.ExpressaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link MLPParser#expressao}.
	 * @param ctx the parse tree
	 */
	void exitExpressao(MLPParser.ExpressaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link MLPParser#logico}.
	 * @param ctx the parse tree
	 */
	void enterLogico(MLPParser.LogicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link MLPParser#logico}.
	 * @param ctx the parse tree
	 */
	void exitLogico(MLPParser.LogicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link MLPParser#operador}.
	 * @param ctx the parse tree
	 */
	void enterOperador(MLPParser.OperadorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MLPParser#operador}.
	 * @param ctx the parse tree
	 */
	void exitOperador(MLPParser.OperadorContext ctx);
}