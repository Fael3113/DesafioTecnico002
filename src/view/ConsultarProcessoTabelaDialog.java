package view;

import database.dao.ProcessoTabelaDAO;
import database.model.TB_REPLICACAO_PROCESSO_TABELA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsultarProcessoTabelaDialog extends JDialog {

	private JTable table;
	private JButton btnSelecionar;
	private JButton btnCancelar;

	private TB_REPLICACAO_PROCESSO_TABELA selecionado;

	public ConsultarProcessoTabelaDialog(JFrame parent, ProcessoTabelaDAO dao) throws Exception {
		super(parent, "Consulta Processo Tabela", true);
		setSize(700, 500);
		setLocationRelativeTo(parent);
		setResizable(false);
		setLayout(null);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("PROCESSO");
		model.addColumn("TABELA ORIGEM");
		model.addColumn("TABELA DESTINO");
		model.addColumn("ORDEM");
		model.addColumn("HABILITADO");

		ArrayList<TB_REPLICACAO_PROCESSO_TABELA> lista = dao.selectAll();
		for (TB_REPLICACAO_PROCESSO_TABELA p : lista){
			model.addRow(new Object[]{
					p.getId(),
					p.getProcesso_id(),
					p.getTabela_origem(),
					p.getTabela_destino(),
					p.getOrdem(),
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

			long id = Long.parseLong(table.getValueAt(selectedRow, 0).toString());
			TB_REPLICACAO_PROCESSO_TABELA t = null;
			try {
				t = dao.selectById(id);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}

			selecionado = t;
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

	public TB_REPLICACAO_PROCESSO_TABELA getSelecionado(){
		return selecionado;
	}

}
