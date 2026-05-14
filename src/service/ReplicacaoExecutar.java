package service;

import database.dao.DirecaoDAO;
import database.dao.OrigemDAO;
import database.dao.ProcessoDAO;
import database.dao.ProcessoTabelaDAO;
import database.model.TB_REPLICACAO_DIRECAO;
import database.model.TB_REPLICACAO_PROCESSO;
import database.model.TB_REPLICACAO_PROCESSO_TABELA;

import java.sql.*;
import java.util.ArrayList;

public class ReplicacaoExecutar {

	private Connection connControle;
	private Connection connOrigem;
	private Connection connDestino;

	public ReplicacaoExecutar(Connection connControle){
		this.connControle = connControle;
		System.out.println("Replicação iniciada. Acompanhe pelo console ou log");
		ReplicacaoIniciar();
		System.out.println("Replicação finalizada. Aguardando início da próxima replicação");
	}

	private void ReplicacaoIniciar() {

		ProcessoDAO replicacaoProcessoDAO = null;
		try {
			replicacaoProcessoDAO = new ProcessoDAO(connControle);
			ArrayList<TB_REPLICACAO_PROCESSO> arlProcessos = replicacaoProcessoDAO.selectAll();
			if (arlProcessos != null && !arlProcessos.isEmpty()) {
				for (TB_REPLICACAO_PROCESSO replicacao : arlProcessos) {
					if (replicacao != null && replicacao.isHabilitado()) {
						DirecaoDAO direcaoDAO = new DirecaoDAO(connControle);
						ArrayList<TB_REPLICACAO_DIRECAO> arlDirecao = direcaoDAO.selectByProcessoHabilitado(replicacao.getId());

						for (TB_REPLICACAO_DIRECAO direcao : arlDirecao) {
							if (direcao != null && direcao.isHabilitado()) {
								connOrigem = DriverManager.getConnection(direcao.getDirecao_origem(), direcao.getUsuario_origem(), direcao.getSenha_origem());
								if (connOrigem == null){
									System.out.println("Falha ao conectar no banco origem");
									continue;
								}

								connDestino = DriverManager.getConnection(direcao.getDirecao_destino(), direcao.getUsuario_destino(), direcao.getSenha_destino());
								if (connDestino == null){
									System.out.println("Falha ao conectar no banco destino");
									continue;
								}

								ProcessoTabelaDAO processoTabelaDAO = new ProcessoTabelaDAO(connControle);
								OrigemDAO daoOrigem = new OrigemDAO(connOrigem);

								ArrayList<TB_REPLICACAO_PROCESSO_TABELA> arlTabelas = processoTabelaDAO.selectByProcessoHabilitado(replicacao.getId());
								for(TB_REPLICACAO_PROCESSO_TABELA tabela : arlTabelas){
									if (tabela != null && tabela.isHabilitado()) {
										System.out.println("Origem: "+ direcao.getDirecao_origem()+ " <--> "+direcao.getDirecao_destino()
										+ " - Tabela: "+tabela.getTabela_origem());
										ResultSet resultado = daoOrigem.selectComandoOrigem(tabela.getTabela_origem(), tabela.getDs_where());
										if (resultado != null) {
											ResultSetMetaData metaData = resultado.getMetaData();
											int ln_columns = metaData.getColumnCount();
											String insertSQL = insertGet(tabela.getTabela_destino(), metaData);

											connDestino.setAutoCommit(false);
											try (PreparedStatement pstInsert = connDestino.prepareStatement(insertSQL)) {
												while (resultado.next()) {
													for (int i = 1; i <= ln_columns; i++) {
														pstInsert.setObject(i, resultado.getObject(i));
													}
													pstInsert.addBatch();
												}
												pstInsert.executeBatch();
												System.out.println("Dados replicados com sucesso");
												connDestino.commit();
											} catch (Exception e) {
												System.out.println("DEU PRIMARY KEY. IGNORANDO!");
											}finally {
												connDestino.setAutoCommit(true);
												resultado.close();
											}

										}
									} else {
										System.out.println("Nenhuma tabela habilitada para replicar");
									}
								}

							} else {
								System.out.println("Nenhuma diração habilitado para replicar...");
							}
						}

					} else {
						System.out.println("Nenhum processo habilitado...");
					}
				}
			} else {
				System.out.println("Nenhum processo encontrado...");
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private String insertGet(String tabelaDestino, ResultSetMetaData metaData) throws SQLException {

		String ls_column = "", ls_value = "";

		for (int ln_1 = 0; ln_1 < metaData.getColumnCount(); ls_column += metaData.getColumnName(++ln_1)+ " ,"
		+"\n", ls_value += " ? ,"+"\n");

		return "insert into "+ tabelaDestino + " ("+ ls_column.substring(0, ls_column.length()-2) +") values ("+ ls_value.substring(0, ls_value.length()-2) +")";
	}

}
