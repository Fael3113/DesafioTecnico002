package view;

import database.model.TB_REPLICACAO_DIRECAO;

import javax.swing.*;
import java.awt.*;

public class TelaReplicacaoProcessoTabelaView extends JFrame {

	private JTextField txfId;
	private JComboBox<TB_REPLICACAO_DIRECAO> cbProcesso;
	private JTextField txfTabelaOrigem;
	private JTextField txfTabelaDestino;
	private JTextField txfOrdem;
	private JTextArea txtDsWhere;
	private JCheckBox chkHabilitado;

	private JButton btnSalvar;
	private JButton btnAdicionar;
	private JButton btnBuscar;
	private JButton btnExcluir;

	public TelaReplicacaoProcessoTabelaView() {
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
	}

	public static void main(String[] args) {
		new TelaReplicacaoProcessoTabelaView().setVisible(true);
	}
}
