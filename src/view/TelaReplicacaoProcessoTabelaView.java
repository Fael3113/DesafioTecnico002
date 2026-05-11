package view;

import database.dao.ProcessoDAO;
import database.dao.ProcessoTabelaDAO;
import database.model.TB_REPLICACAO_PROCESSO;
import database.model.TB_REPLICACAO_PROCESSO_TABELA;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaReplicacaoProcessoTabelaView extends JFrame {

	private enum ModoTela{NENHUM, INSERT, UPDATE}
	private TelaReplicacaoProcessoTabelaView.ModoTela modoTela = TelaReplicacaoProcessoTabelaView.ModoTela.NENHUM;

	private final Connection conn;
	private final ProcessoTabelaDAO daoTabela;
	private final ProcessoDAO daoProcesso;

	private final JTextField txfId;
	private final JComboBox<TB_REPLICACAO_PROCESSO> cbProcesso;
	private final JTextField txfTabelaOrigem;
	private final JTextField txfTabelaDestino;
	private final JTextField txfOrdem;
	private final JTextArea txtDsWhere;
	private final JCheckBox chkHabilitado;

	private final JButton btnSalvar;
	private final JButton btnAdicionar;
	private final JButton btnBuscar;
	private final JButton btnExcluir;

	public TelaReplicacaoProcessoTabelaView(Connection conn) throws SQLException {

		this.conn = conn;
		this.daoTabela = new ProcessoTabelaDAO(conn);
		this.daoProcesso = new ProcessoDAO(conn);

		setTitle("Cadastro de Replicação");
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);

		// --- Botões de ação ---
		btnBuscar = new JButton("Buscar");
		btnAdicionar = new JButton("Adicionar");
		btnSalvar = new JButton("Salvar");
		btnExcluir = new JButton("Excluir");

		btnBuscar.setBounds(10, 10, 100, 30);
		btnAdicionar.setBounds(150, 10, 100, 30);
		btnSalvar.setBounds(300, 10, 100, 30);
		btnExcluir.setBounds(450, 10, 100, 30);

		getContentPane().add(btnBuscar);
		getContentPane().add(btnAdicionar);
		getContentPane().add(btnSalvar);
		getContentPane().add(btnExcluir);

		// --- Id ---
		JLabel lblId = new JLabel("Id:");
		lblId.setBounds(10, 70, 120, 25);
		getContentPane().add(lblId);
		txfId = new JTextField();
		txfId.setBounds(140, 70, 120, 25);
		getContentPane().add(txfId);

		// --- Processo Id ---
		JLabel lblProcessoId = new JLabel("Processo Id:");
		lblProcessoId.setBounds(10, 110, 120, 25);
		getContentPane().add(lblProcessoId);
		cbProcesso = new JComboBox<>();
		cbProcesso.setBounds(140, 110, 120, 25);
		getContentPane().add(cbProcesso);

		// --- Tabela Origem ---
		JLabel lblTabelaOrigem = new JLabel("Tabela Origem:");
		lblTabelaOrigem.setBounds(10, 150, 120, 25);
		lblTabelaOrigem.setForeground(Color.RED);
		getContentPane().add(lblTabelaOrigem);
		txfTabelaOrigem = new JTextField();
		txfTabelaOrigem.setBounds(140, 150, 400, 25);
		getContentPane().add(txfTabelaOrigem);

		// --- Tabela Destino ---
		JLabel lblTabelaDestino = new JLabel("Tabela Destino:");
		lblTabelaDestino.setBounds(10, 190, 120, 25);
		getContentPane().add(lblTabelaDestino);
		txfTabelaDestino = new JTextField();
		txfTabelaDestino.setBounds(140, 190, 400, 25);
		getContentPane().add(txfTabelaDestino);

		// --- Ordem ---
		JLabel lblOrdem = new JLabel("Ordem:");
		lblOrdem.setBounds(10, 230, 120, 25);
		getContentPane().add(lblOrdem);
		txfOrdem = new JTextField();
		txfOrdem.setBounds(140, 230, 120, 25);
		getContentPane().add(txfOrdem);

		// --- DS Where ---
		JLabel lblDsWhere = new JLabel("DS Where:");
		lblDsWhere.setBounds(10, 270, 120, 25);
		getContentPane().add(lblDsWhere);
		txtDsWhere = new JTextArea();
		txtDsWhere.setBounds(140, 270, 400, 80);
		getContentPane().add(txtDsWhere);

		// --- Habilitado ---
		chkHabilitado = new JCheckBox("Habilitado");
		chkHabilitado.setBounds(10, 350, 120, 30);
		getContentPane().add(chkHabilitado);

		// --- CB processo ---
		cbProcesso.removeAll();
		ArrayList<TB_REPLICACAO_PROCESSO> processos = daoProcesso.selectAll();
		for(TB_REPLICACAO_PROCESSO p : processos){
			cbProcesso.addItem(p);
		}

		txfId.setEnabled(false);
		cbProcesso.setEnabled(false);
		chkHabilitado.setEnabled(false);
		txfOrdem.setEnabled(false);
		txfTabelaOrigem.setEnabled(false);
		txfTabelaDestino.setEnabled(false);
		txtDsWhere.setEnabled(false);

		btnSalvar.setEnabled(false);
		btnExcluir.setEnabled(false);

		btnAdicionar.addActionListener(e -> {
			modoTela = ModoTela.INSERT;

			txfId.setText("");

			if(cbProcesso.getItemCount() > 0){
				cbProcesso.setSelectedIndex(0);
			}

			chkHabilitado.setSelected(false);
			txfTabelaOrigem.setText("");
			txfTabelaDestino.setText("");
			txfOrdem.setText("");
			txtDsWhere.setText("");

			cbProcesso.setEnabled(true);
			chkHabilitado.setEnabled(true);
			txtDsWhere.setEnabled(true);
			txfTabelaOrigem.setEnabled(true);
			txfTabelaDestino.setEnabled(true);
			txfOrdem.setEnabled(true);

			btnSalvar.setEnabled(true);
			btnExcluir.setEnabled(false);
		});

		btnSalvar.addActionListener(e -> {
			try{
				if (cbProcesso.getSelectedItem() == null){
					JOptionPane.showMessageDialog(this, "Selecione um processo");
					return;
				}
				if (txfTabelaOrigem.getText().trim().isEmpty() || txfTabelaDestino.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "Informe a tabela origem e tabela destino");
					return;
				}
				if (txfOrdem.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "Informe a ordem e tabela destino");
					return;
				}

				int ordem;
				try {
					ordem = Integer.parseInt(txfOrdem.getText());
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(this, "Ordem deve ser um número");
					return;
				}

				TB_REPLICACAO_PROCESSO pSel = (TB_REPLICACAO_PROCESSO) cbProcesso.getSelectedItem();

				TB_REPLICACAO_PROCESSO_TABELA t = new TB_REPLICACAO_PROCESSO_TABELA();
				t.setProcesso_id(pSel.getId());
				t.setTabela_origem(txfTabelaOrigem.getText());
				t.setTabela_destino(txfTabelaDestino.getText());
				t.setHabilitado(chkHabilitado.isSelected());
				t.setOrdem(ordem);
				t.setDs_where(txtDsWhere.getText());

				if (modoTela == TelaReplicacaoProcessoTabelaView.ModoTela.INSERT) {
					daoTabela.insert(t);
					JOptionPane.showMessageDialog(this, "Inserido com sucesso");
				} else if (modoTela == TelaReplicacaoProcessoTabelaView.ModoTela.UPDATE) {
					if (txfId.getText().trim().isEmpty()){
						JOptionPane.showMessageDialog(this, "Digite o id do processo");
						return;
					}
					t.setId(Integer.parseInt(txfId.getText()));
					daoTabela.update(t);
					JOptionPane.showMessageDialog(this, "Atualizado com sucesso");
				} else {
					JOptionPane.showMessageDialog(this, "Clique em Adicionar ou Buscar antes de salvar");
				}

				modoTela = ModoTela.NENHUM;

				txfId.setEnabled(false);
				cbProcesso.setEnabled(false);
				chkHabilitado.setEnabled(false);
				txfOrdem.setEnabled(false);
				txfTabelaOrigem.setEnabled(false);
				txfTabelaDestino.setEnabled(false);
				txtDsWhere.setEnabled(false);

				btnSalvar.setEnabled(false);

			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erro ao salvar:"+ex.getMessage());
			}
		});

		btnExcluir.addActionListener(e -> {
			try{
				if (txtDsWhere.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "Digite o id do processo");
					return;
				}

				int op = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?", "Excluir", JOptionPane.YES_NO_OPTION);
				if (op != JOptionPane.YES_OPTION) return;

				long id = Long.parseLong(txfId.getText());
				daoTabela.delete(id);
				JOptionPane.showMessageDialog(this, "Processo excluído!");

				modoTela = ModoTela.NENHUM;

				txfId.setText("");

				if(cbProcesso.getItemCount() > 0){
					cbProcesso.setSelectedIndex(0);
				}

				chkHabilitado.setSelected(false);
				txfTabelaOrigem.setText("");
				txfTabelaDestino.setText("");
				txfOrdem.setText("");
				txtDsWhere.setText("");

				txfId.setEnabled(false);
				cbProcesso.setEnabled(false);
				chkHabilitado.setEnabled(false);
				txfOrdem.setEnabled(false);
				txfTabelaOrigem.setEnabled(false);
				txfTabelaDestino.setEnabled(false);
				txtDsWhere.setEnabled(false);

				btnSalvar.setEnabled(false);
				btnExcluir.setEnabled(false);

			} catch (Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erro ao excluir:"+ex.getMessage());
			}
		});

		btnBuscar.addActionListener(e -> {
			try{
				ConsultarProcessoTabelaDialog dlg = new ConsultarProcessoTabelaDialog(this, daoTabela);
				dlg.setVisible(true);

				TB_REPLICACAO_PROCESSO_TABELA sel = dlg.getSelecionado();
				if (sel == null) return;

				modoTela = ModoTela.UPDATE;

				txfId.setText(String.valueOf(sel.getId()));
				txfTabelaOrigem.setText(sel.getTabela_origem());
				txfTabelaDestino.setText(sel.getTabela_destino());
				txtDsWhere.setText(sel.getDs_where());
				chkHabilitado.setSelected(sel.isHabilitado());
				txfOrdem.setText(String.valueOf(sel.getOrdem()));

				long id = sel.getProcesso_id();
				for (int i = 0; cbProcesso.getItemCount() > i; i++){
					TB_REPLICACAO_PROCESSO item = cbProcesso.getItemAt(i);
					if (item.getId() == id){
						cbProcesso.setSelectedIndex(i);
						break;
					}
				}

				cbProcesso.setEnabled(true);
				chkHabilitado.setEnabled(true);
				txtDsWhere.setEnabled(true);
				txfTabelaOrigem.setEnabled(true);
				txfTabelaDestino.setEnabled(true);
				txfOrdem.setEnabled(true);

				btnSalvar.setEnabled(true);
				btnExcluir.setEnabled(true);

			} catch (Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erro ao buscar:"+ex.getMessage());
			}
		});

	}
}
