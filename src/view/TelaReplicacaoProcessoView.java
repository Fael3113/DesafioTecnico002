package view;

import database.dao.ProcessoDAO;
import database.model.TB_REPLICACAO_PROCESSO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TelaReplicacaoProcessoView extends JFrame {

	private enum ModoTela {NENHUM, INSERT, UPDATE}
	private ModoTela modoTela = ModoTela.NENHUM;

	private final Connection conn;
	private final ProcessoDAO dao;

	private JTextField txfId;
	private JTextField txfProcesso;
	private JTextField txfDescricao;
	private JCheckBox chkHabilitado;

	private JButton btnSalvar;
	private JButton btnAdicionar;
	private JButton btnBuscar;
	private JButton btnExcluir;

	public TelaReplicacaoProcessoView(Connection conn) throws SQLException {

		this.conn = conn;
		this.dao = new ProcessoDAO(conn);

		setTitle("Cadastro de Processos");
		setSize(600, 400);
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

		// --- Processo ---
		JLabel lblProcesso = new JLabel("Processo:");
		lblProcesso.setBounds(10, 105, 120, 25);
		getContentPane().add(lblProcesso);
		txfProcesso = new JTextField();
		txfProcesso.setBounds(140, 105, 400, 25);
		getContentPane().add(txfProcesso);

		// --- Descrição ---
		JLabel lblDescricao = new JLabel("Descricao:");
		lblDescricao.setBounds(10, 140, 120, 25);
		getContentPane().add(lblDescricao);
		txfDescricao = new JTextField();
		txfDescricao.setBounds(140, 140, 400, 25);
		getContentPane().add(txfDescricao);

		// --- Habilitado ---
		JLabel lblHabilitado = new JLabel("HABILITADO:");
		lblHabilitado.setBounds(10, 175, 120, 25);
		getContentPane().add(lblHabilitado);
		chkHabilitado = new JCheckBox("Sim");
		chkHabilitado.setBounds(140, 175, 80, 25);
		getContentPane().add(chkHabilitado);

		txfId.setEnabled(false);
		txfProcesso.setEnabled(false);
		txfDescricao.setEnabled(false);
		chkHabilitado.setEnabled(false);
		btnSalvar.setEnabled(false);
		btnExcluir.setEnabled(false);

		btnAdicionar.addActionListener(e ->{
			modoTela = ModoTela.INSERT;

			txfId.setText("");
			txfProcesso.setText("");
			txfDescricao.setText("");
			chkHabilitado.setSelected(false);

			txfId.setEnabled(false);
			txfProcesso.setEnabled(true);
			txfDescricao.setEnabled(true);
			chkHabilitado.setEnabled(true);

			btnSalvar.setEnabled(true);
			btnExcluir.setEnabled(false);
		});

		btnSalvar.addActionListener(e ->{
			try {
				if (txfProcesso.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "Informe o processo");
					return;
				}

				TB_REPLICACAO_PROCESSO p = new TB_REPLICACAO_PROCESSO();
				p.setProcesso(txfProcesso.getText().trim());
				p.setDescricao(txfDescricao.getText().trim());
				p.setHabilitado(chkHabilitado.isSelected());

				if (modoTela == ModoTela.INSERT){
					dao.insert(p);
					JOptionPane.showMessageDialog(this, "Processo cadastrado!");
				} else if (modoTela == ModoTela.UPDATE) {
					if (txfId.getText().trim().isEmpty()){
						JOptionPane.showMessageDialog(this, "ID não carregado para update");
						return;
					}
					p.setId(Integer.parseInt(txfId.getText()));
					dao.update(p);
					JOptionPane.showMessageDialog(this, "Processo atualizado!");
				} else {
					JOptionPane.showMessageDialog(this, "Clique em ADICIONAR ou BUSCAR antes");
					return;
				}

				modoTela = ModoTela.NENHUM;
				txfProcesso.setEnabled(false);
				txfDescricao.setEnabled(false);
				chkHabilitado.setEnabled(false);

				btnSalvar.setEnabled(false);

			} catch (Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
			}
		});

		btnExcluir.addActionListener(e ->{
			try {
				if (txfId.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "ID não carregado para excluir");
					return;
				}

				int op = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?", "Excluir", JOptionPane.YES_NO_OPTION);
				if (op != JOptionPane.YES_OPTION) return;

				long id = Long.parseLong(txfId.getText());
				dao.delete(id);
				JOptionPane.showMessageDialog(this, "Processo excluído!");

				modoTela = ModoTela.NENHUM;

				txfId.setText("");
				txfProcesso.setText("");
				txfDescricao.setText("");
				chkHabilitado.setSelected(false);

				txfProcesso.setEnabled(false);
				txfDescricao.setEnabled(false);
				chkHabilitado.setEnabled(false);

				btnSalvar.setEnabled(false);
				btnExcluir.setEnabled(false);

			} catch (Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex.getMessage());
			}
		});

		btnBuscar.addActionListener(b ->{
			try {
				ConsultarProcessoDialog dlg = new ConsultarProcessoDialog(this, dao);
				dlg.setVisible(true);

				TB_REPLICACAO_PROCESSO selecionado = dlg.getSelecionado();
				if (selecionado == null) return;

				modoTela = ModoTela.UPDATE;
				txfId.setText(String.valueOf(selecionado.getId()));
				txfProcesso.setText(selecionado.getProcesso());
				txfDescricao.setText(selecionado.getDescricao());
				chkHabilitado.setSelected(selecionado.isHabilitado());

				txfProcesso.setEnabled(true);
				txfDescricao.setEnabled(true);
				chkHabilitado.setEnabled(true);

				btnSalvar.setEnabled(true);
				btnExcluir.setEnabled(true);

			} catch (Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex.getMessage());
			}
		});
	}

}
