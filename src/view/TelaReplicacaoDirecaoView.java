package view;

import database.model.TB_REPLICACAO_DIRECAO;

import javax.swing.*;
import java.awt.*;

public class TelaReplicacaoDirecaoView extends JFrame {

	private JTextField txfId;
	private JComboBox<TB_REPLICACAO_DIRECAO> cbProcesso;
	private JTextField txfDirecaoOrigem;
	private JTextField txfDirecaoDestino;
	private JTextField txfUsuarioOrigem;
	private JTextField txfUsuarioDestino;
	private JPasswordField pwfSenhaOrigem;
	private JPasswordField pwfSenhaDestino;
	private JCheckBox chkHabilitado;

	private JButton btnSalvar;
	private JButton btnAdicionar;
	private JButton btnBuscar;
	private JButton btnExcluir;

	public TelaReplicacaoDirecaoView() {
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
	}

	public static void main(String[] args) {
		new TelaReplicacaoDirecaoView().setVisible(true);
	}

}
