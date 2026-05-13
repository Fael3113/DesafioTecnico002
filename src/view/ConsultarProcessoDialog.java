package view;

import database.dao.ProcessoDAO;
import database.model.TB_REPLICACAO_PROCESSO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsultarProcessoDialog extends JDialog {

	private JTable table;
	private JButton btnSelecionar;
	private JButton btnCancelar;

	private TB_REPLICACAO_PROCESSO selecionado;

	public ConsultarProcessoDialog(JFrame parent, ProcessoDAO dao) throws Exception {
		super(parent, "Consulta Processo", true);
		setSize(700, 400);
		setLocationRelativeTo(parent);
		setResizable(false);
		setLayout(null);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("PROCESSO");
		model.addColumn("DESCRICAO");
		model.addColumn("HABILITADO");

		ArrayList<TB_REPLICACAO_PROCESSO> lista = dao.selectAll();
		for (TB_REPLICACAO_PROCESSO p : lista){
			model.addRow(new Object[]{
					p.getId(),
					p.getProcesso(),
					p.getDescricao(),
					p.isHabilitado()});
		}

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 10, 680, 300);
		add(scrollPane);

		btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setBounds(10, 320, 140, 30);
		add(btnSelecionar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(170, 320, 140, 30);
		add(btnCancelar);

		btnCancelar.addActionListener(e -> {
			selecionado = null;
			dispose();
		});

		btnSelecionar.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Selecione um processo");
				return;
			}

			long id = Long.parseLong(table.getValueAt(selectedRow, 0).toString());
			TB_REPLICACAO_PROCESSO p = null;
			try {
				p = dao.selectById(id);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}

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

	public TB_REPLICACAO_PROCESSO getSelecionado(){
		return selecionado;
	}

}
