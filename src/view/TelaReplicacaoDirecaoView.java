package view;

import database.dao.DirecaoDAO;
import database.dao.ProcessoDAO;
import database.model.TB_REPLICACAO_DIRECAO;
import database.model.TB_REPLICACAO_PROCESSO;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaReplicacaoDirecaoView extends JFrame {

	private enum ModoTela{NENHUM, INSERT, UPDATE}
	private TelaReplicacaoDirecaoView.ModoTela modoTela = ModoTela.NENHUM;

	private final Connection conn;
	private final DirecaoDAO daoDirecao;
	private final ProcessoDAO daoProcesso;

	private final JTextField txfId;
	private final JComboBox<TB_REPLICACAO_PROCESSO> cbProcesso;
	private final JTextField txfDirecaoOrigem;
	private final JTextField txfDirecaoDestino;
	private final JTextField txfUsuarioOrigem;
	private final JTextField txfUsuarioDestino;
	private final JPasswordField pwfSenhaOrigem;
	private final JPasswordField pwfSenhaDestino;
	private final JCheckBox chkHabilitado;

	private final JButton btnSalvar;
	private final JButton btnAdicionar;
	private final JButton btnBuscar;
	private final JButton btnExcluir;

	public TelaReplicacaoDirecaoView(Connection conn) throws SQLException {

		this.conn = conn;
		this.daoDirecao = new DirecaoDAO(conn);
		this.daoProcesso = new ProcessoDAO(conn);

		setTitle("Cadastro de Direção");
		setSize(600, 560);
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
		JLabel lblProcessoId = new JLabel("Processo:");
		lblProcessoId.setBounds(10, 110, 120, 25);
		getContentPane().add(lblProcessoId);
		cbProcesso = new JComboBox<>();
		cbProcesso.setBounds(140, 110, 120, 25);
		getContentPane().add(cbProcesso);

		// --- Direcao Origem ---
		JLabel lblDirecaoOrigem = new JLabel("Direção Origem:");
		lblDirecaoOrigem.setBounds(10, 150, 120, 25);
		lblDirecaoOrigem.setForeground(Color.RED);
		getContentPane().add(lblDirecaoOrigem);
		txfDirecaoOrigem = new JTextField();
		txfDirecaoOrigem.setBounds(140, 150, 400, 25);
		getContentPane().add(txfDirecaoOrigem);

		// --- Direcao Destino ---
		JLabel lblDirecaoDestino = new JLabel("Direção Destino:");
		lblDirecaoDestino.setBounds(10, 190, 120, 25);
		getContentPane().add(lblDirecaoDestino);
		txfDirecaoDestino = new JTextField();
		txfDirecaoDestino.setBounds(140, 190, 400, 25);
		getContentPane().add(txfDirecaoDestino);

		// --- Usuario Origem ---
		JLabel lblUsuarioOrigem = new JLabel("Usuário Origem:");
		lblUsuarioOrigem.setBounds(10, 230, 120, 25);
		lblUsuarioOrigem.setForeground(Color.RED);
		getContentPane().add(lblUsuarioOrigem);
		txfUsuarioOrigem = new JTextField();
		txfUsuarioOrigem.setBounds(140, 230, 400, 25);
		getContentPane().add(txfUsuarioOrigem);

		// --- Usuario Destino ---
		JLabel lblUsuarioDestino = new JLabel("Usuário Destino:");
		lblUsuarioDestino.setBounds(10, 270, 120, 25);
		getContentPane().add(lblUsuarioDestino);
		txfUsuarioDestino = new JTextField();
		txfUsuarioDestino.setBounds(140, 270, 400, 25);
		getContentPane().add(txfUsuarioDestino);

		// --- Senha Origem ---
		JLabel lblSenhaOrigem = new JLabel("Senha Origem:");
		lblSenhaOrigem.setBounds(10, 310, 120, 25);
		lblSenhaOrigem.setForeground(Color.RED);
		getContentPane().add(lblSenhaOrigem);
		pwfSenhaOrigem = new JPasswordField();
		pwfSenhaOrigem.setBounds(140, 310, 400, 25);
		getContentPane().add(pwfSenhaOrigem);

		// --- Senha Destino ---
		JLabel lblSenhaDestino = new JLabel("Senha Destino:");
		lblSenhaDestino.setBounds(10, 350, 120, 25);
		getContentPane().add(lblSenhaDestino);
		pwfSenhaDestino = new JPasswordField();
		pwfSenhaDestino.setBounds(140, 350, 400, 25);
		getContentPane().add(pwfSenhaDestino);

		// --- Habilitado ---
		chkHabilitado = new JCheckBox("Habilitado");
		chkHabilitado.setBounds(10, 400, 120, 30);
		getContentPane().add(chkHabilitado);

		// --- CB processo ---
		cbProcesso.removeAll();
		ArrayList<TB_REPLICACAO_PROCESSO> processos = daoProcesso.selectAll();
		for(TB_REPLICACAO_PROCESSO p : processos){
			cbProcesso.addItem(p);
		}

		txfId.setEnabled(false);
		cbProcesso.setEnabled(false);
		txfDirecaoOrigem.setEnabled(false);
		txfDirecaoDestino.setEnabled(false);
		txfUsuarioOrigem.setEnabled(false);
		txfUsuarioDestino.setEnabled(false);
		pwfSenhaOrigem.setEnabled(false);
		pwfSenhaDestino.setEnabled(false);

		btnSalvar.setEnabled(false);
		btnExcluir.setEnabled(false);

		btnAdicionar.addActionListener(e -> {
			modoTela = ModoTela.INSERT;

			txfId.setText("");

			if (cbProcesso.getItemCount() > 0) {
				cbProcesso.setSelectedIndex(0);
			}

			chkHabilitado.setSelected(true);

			txfDirecaoOrigem.setText("");
			txfDirecaoDestino.setText("");
			txfUsuarioOrigem.setText("");
			txfUsuarioDestino.setText("");
			pwfSenhaOrigem.setText("");
			pwfSenhaDestino.setText("");

			cbProcesso.setEnabled(true);
			chkHabilitado.setEnabled(true);
			txfUsuarioOrigem.setEnabled(true);
			txfUsuarioDestino.setEnabled(true);
			txfDirecaoOrigem.setEnabled(true);
			txfDirecaoDestino.setEnabled(true);
			pwfSenhaOrigem.setEnabled(true);
			pwfSenhaDestino.setEnabled(true);


			btnSalvar.setEnabled(true);
			btnExcluir.setEnabled(false);

		});

		btnSalvar.addActionListener(e -> {
			try {
				if (cbProcesso.getSelectedItem() == null){
					JOptionPane.showMessageDialog(this, "Selecione um processo");
					return;
				}

				if (txfDirecaoOrigem.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "Digite uma direcao origem");
					return;
				}

				if (txfDirecaoDestino.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "Digite uma direcao destino");
					return;
				}

				if (new String(pwfSenhaOrigem.getPassword()).trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "Digite uma senha origem");
					return;
				}

				if (new String(pwfSenhaDestino.getPassword()).trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "Digite uma senha destino");
					return;
				}

				if (txfUsuarioOrigem.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "Digite um usuario origem");
					return;
				}

				if (txfUsuarioDestino.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "Digite um usuario destino");
					return;
				}

				TB_REPLICACAO_PROCESSO pSel = (TB_REPLICACAO_PROCESSO) cbProcesso.getSelectedItem();

				TB_REPLICACAO_DIRECAO d = new TB_REPLICACAO_DIRECAO();
				d.setProcesso_id(pSel.getId());
				d.setHabilitado(chkHabilitado.isSelected());
				d.setDirecao_origem(txfDirecaoOrigem.getText());
				d.setDirecao_destino(txfDirecaoDestino.getText());
				d.setUsuario_origem(txfUsuarioOrigem.getText());
				d.setUsuario_destino(txfUsuarioDestino.getText());
				d.setSenha_origem(new String(pwfSenhaOrigem.getPassword()));
				d.setSenha_destino(new String(pwfSenhaDestino.getPassword()));
				
				if (modoTela == ModoTela.INSERT) {
					daoDirecao.insert(d);
					JOptionPane.showMessageDialog(this, "Inserido com sucesso");
				} else if (modoTela == ModoTela.UPDATE) {
					if (txfId.getText().trim().isEmpty()){
						JOptionPane.showMessageDialog(this, "Digite o id do processo");
						return;
					}
					d.setId(Integer.parseInt(txfId.getText()));
					daoDirecao.update(d);
					JOptionPane.showMessageDialog(this, "Atualizado com sucesso");
				} else {
					JOptionPane.showMessageDialog(this, "Clique em Adicionar ou Buscar antes de salvar");
				}

				modoTela = ModoTela.NENHUM;

				txfId.setEnabled(false);
				cbProcesso.setEnabled(false);
				txfDirecaoOrigem.setEnabled(false);
				txfDirecaoDestino.setEnabled(false);
				txfUsuarioOrigem.setEnabled(false);
				txfUsuarioDestino.setEnabled(false);
				pwfSenhaOrigem.setEnabled(false);
				pwfSenhaDestino.setEnabled(false);

				btnSalvar.setEnabled(false);
				btnExcluir.setEnabled(false);

			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erro ao atualizar:"+ex.getMessage());
			}
		});

		btnExcluir.addActionListener(e -> {
			try {
				if (txfId.getText().trim().isEmpty()){
					JOptionPane.showMessageDialog(this, "Digite o id do processo");
					return;
				}

				int op = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?", "Excluir", JOptionPane.YES_NO_OPTION);
				if (op != JOptionPane.YES_OPTION) return;

				long id = Long.parseLong(txfId.getText());
				daoDirecao.delete(id);
				JOptionPane.showMessageDialog(this, "Processo excluído!");

				modoTela = ModoTela.NENHUM;

				txfId.setText("");
				txfDirecaoOrigem.setText("");
				txfDirecaoDestino.setText("");
				txfUsuarioOrigem.setText("");
				txfUsuarioDestino.setText("");
				pwfSenhaOrigem.setText("");
				pwfSenhaDestino.setText("");
				chkHabilitado.setSelected(false);

				txfId.setEnabled(false);
				cbProcesso.setEnabled(false);
				txfDirecaoOrigem.setEnabled(false);
				txfDirecaoDestino.setEnabled(false);
				txfUsuarioOrigem.setEnabled(false);
				txfUsuarioDestino.setEnabled(false);
				pwfSenhaOrigem.setEnabled(false);
				pwfSenhaDestino.setEnabled(false);
				chkHabilitado.setEnabled(false);

				btnBuscar.setEnabled(false);
				btnAdicionar.setEnabled(false);
				btnSalvar.setEnabled(false);
				btnExcluir.setEnabled(false);

			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erro ao excluir:"+ex.getMessage());
			}
		});

		btnBuscar.addActionListener(e -> {
			try {
				ConsultarDirecaoDialog dlg = new ConsultarDirecaoDialog(this, daoDirecao);
				dlg.setVisible(true);

				TB_REPLICACAO_DIRECAO sel = dlg.getSelecionado();

				if (sel == null) return;

				modoTela = ModoTela.UPDATE;

				txfId.setText(String.valueOf(sel.getId()));
				txfDirecaoOrigem.setText(sel.getDirecao_origem());
				txfDirecaoDestino.setText(sel.getDirecao_destino());
				cbProcesso.setSelectedItem(sel.getProcesso_id());
				chkHabilitado.setSelected(sel.isHabilitado());
				txfUsuarioOrigem.setText(sel.getUsuario_origem());
				txfUsuarioDestino.setText(sel.getUsuario_destino());
				pwfSenhaOrigem.setText(sel.getSenha_origem());
				pwfSenhaDestino.setText(sel.getSenha_destino());

				long id = sel.getProcesso_id();
				for (int i = 0; i < cbProcesso.getItemCount(); i++) {
					TB_REPLICACAO_PROCESSO item = cbProcesso.getItemAt(i);
					if (item.getId() == id) {
						cbProcesso.setSelectedIndex(i);
						break;
					}
				}

				cbProcesso.setEnabled(true);
				chkHabilitado.setEnabled(true);
				txfUsuarioOrigem.setEnabled(true);
				txfUsuarioDestino.setEnabled(true);
				txfDirecaoOrigem.setEnabled(true);
				txfDirecaoDestino.setEnabled(true);
				pwfSenhaOrigem.setEnabled(true);
				pwfSenhaDestino.setEnabled(true);

				btnSalvar.setEnabled(true);
				btnExcluir.setEnabled(true);

			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Erro ao buscar:"+ex.getMessage());
			}
		});

	}
}
