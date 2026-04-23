package view;

import javax.swing.*;

public class TelaReplicacaoProcessoView extends JFrame {

	private JTextField txfId;
	private JTextField txfProcesso;
	private JTextField txfDescricao;
	private JCheckBox chkHabilitado;

	private JButton btnSalvar;
	private JButton btnAdicionar;
	private JButton btnBuscar;
	private JButton btnExcluir;

	public TelaReplicacaoProcessoView() {
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
		lblProcesso.setBounds(10, 140, 120, 25);
		getContentPane().add(lblProcesso);
		txfProcesso = new JTextField();
		txfProcesso.setBounds(140, 140, 400, 25);
		getContentPane().add(txfProcesso);

		// --- Descrição ---
		JLabel lblDescricao = new JLabel("Descricao:");
		lblDescricao.setBounds(10, 210, 120, 25);
		getContentPane().add(lblDescricao);
		txfDescricao = new JTextField();
		txfDescricao.setBounds(140, 210, 400, 25);
		getContentPane().add(txfDescricao);

		// --- Habilitado ---
		JCheckBox chckHabilitado = new JCheckBox("Habilitado");
		chckHabilitado.setBounds(10, 280, 100, 30);
		getContentPane().add(chckHabilitado);
	}

	public static void main(String[] args) {
		new TelaReplicacaoProcessoView().setVisible(true);
	}

}
