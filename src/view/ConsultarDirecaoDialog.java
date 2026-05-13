package view;

import database.dao.DirecaoDAO;
import database.model.TB_REPLICACAO_DIRECAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ConsultarDirecaoDialog extends JDialog {

	private JTable table;
	private JButton btnSelecionar;
	private JButton btnCancelar;

	private TB_REPLICACAO_DIRECAO selecionado;

	public ConsultarDirecaoDialog(JFrame parent, DirecaoDAO dao) throws Exception {
		super(parent, "Consulta Direção");
		setSize(700, 500);
		setLocationRelativeTo(parent);
		setResizable(false);
		setLayout(null);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("PROCESSO");
		model.addColumn("DIRECAO ORIGEM");
		model.addColumn("DIRECAO DESTINO");
		model.addColumn("HABILITADO");

		ArrayList<TB_REPLICACAO_DIRECAO> lista = dao.selectAll();
		for (TB_REPLICACAO_DIRECAO p : lista){
			model.addRow(new Object[]{
					p.getId(),
					p.getProcesso_id(),
					p.getDirecao_origem(),
					p.getDirecao_destino(),
					p.isHabilitado()});
		}

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 10, 680, 400);
		add(scrollPane);

		btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setBounds(10, 420, 140, 30);
		add(btnSelecionar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(170, 420, 140, 30);
		add(btnCancelar);

		btnCancelar.addActionListener(e -> {
			selecionado = null;
			dispose();
		});

		btnSelecionar.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(null, "Selecione um processo");
				return;
			}

			TB_REPLICACAO_DIRECAO p = new TB_REPLICACAO_DIRECAO();
			p.setId(Integer.parseInt(table.getValueAt(selectedRow, 0).toString()));
			p.setProcesso_id(Integer.parseInt(table.getValueAt(selectedRow, 1).toString()));
			p.setDirecao_origem(table.getValueAt(selectedRow, 2).toString());
			p.setDirecao_destino(table.getValueAt(selectedRow, 3).toString());
			p.setHabilitado(Boolean.parseBoolean(table.getValueAt(selectedRow, 8).toString()));

			selecionado = p;
			dispose();
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					btnSelecionar.doClick();
				}
			}
		});
	}

	public TB_REPLICACAO_DIRECAO getSelecionado(){
		return selecionado;
	}
}
