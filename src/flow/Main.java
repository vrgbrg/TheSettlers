package flow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Main extends JFrame implements MainContract.View {

    private ActionListener actionListener;

    public static void main(String... args) {
        EventQueue.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }

    private MainContract.Presenter presenter;

    private JPanel root;

    private JRadioButton toBuild;
    JRadioButton soldierRecruitment;
    String selectedBuilding;
    JComboBox<String> buildingStore;
    ButtonGroup group;

    public Main() {
        setTitle("The Settlers");
        setSize(1400, 750);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        root = new JPanel();
        root.setLayout(null);
        add(root);

        actionListener = e -> {

            JComboBox<String> combo = buildingStore;
            selectedBuilding = (String) combo.getSelectedItem();

            String[] s = e.getActionCommand().split(" ");

            int x = Integer.valueOf(s[0]);
            int y = Integer.valueOf(s[1]);

            presenter.onTableItemClicked(new Position(x, y));

            group.clearSelection();

            repaint();
        };

        presenter = new MainPresenter(this);
    }

    @Override
    public void showTable(Cell[][] table) {
        root.removeAll();

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                JButton btn = new JButton();

                btn.setActionCommand(i + " " + j);
                btn.addActionListener(actionListener);
                btn.setBounds(j * 14 + 12, i * 14 + 12, 14, 14);

                root.add(btn);

                btn.setOpaque(true);

                Cell player = table[i][j];
                if (player != null) {
                    btn.setText(player.toString());
                }
            }
        }

        JLabel roundTitle = new JLabel("Tedd vagy ne tedd!");

        toBuild = new JRadioButton("Építés");
        soldierRecruitment = new JRadioButton("Katona toborzás");
        JRadioButton soldierMove = new JRadioButton("Katona mozgatás");
        group = new ButtonGroup();
        group.add(toBuild);
        group.add(soldierRecruitment);
        toBuild.setBounds(750, 330, 100, 40);
        soldierRecruitment.setBounds(860, 330, 150, 40);

        roundTitle.setBounds(750, 300, 200, 20);

        root.add(roundTitle);
        root.add(toBuild);
        root.add(soldierRecruitment);

        JButton doIt = new JButton("Építs - Toborozz!");

        JPanel playerField = new JPanel();
        playerField.setBounds(750, 40, 520, 200);
        playerField.setBackground(Color.WHITE);
        playerField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        playerField.setLayout(null);
        root.add(playerField);
        JLabel playerOne = new JLabel("Player 1: ");
        playerOne.setBounds(750, 15, 200, 20);
        root.add(playerOne);

        buildingStore = new JComboBox<String>();
        buildingStore.addItem("Városháza");
        buildingStore.addItem("Fegyverkovács műhely");
        buildingStore.addItem("Kaszárnya");
        buildingStore.addItem("Kiképzőhely");
        buildingStore.addItem("Bank");
        buildingStore.addItem("Szállás");
        buildingStore.addItem("Katonaszállás");
        buildingStore.setBounds(750, 360, 150, 50);
        buildingStore.setVisible(false);
        root.add(buildingStore);


        toBuild.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                buildingStore.setVisible(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
    }

    @Override
    public void setSelection(Position position, boolean selection) {
        Component component = root.getComponent(position.x * 50 + position.y);

        component.setBackground(selection ? Color.RED : null);
    }

    @Override
    public void updateCell(Position position, Cell player) {
        JButton btn = (JButton) root.getComponent(position.x * 50 + position.y);

        btn.setText(player != null ? player.toString() : null);
    }

    public JRadioButton getToBuild() {
        return toBuild;
    }

    public JRadioButton getSoldierRecruitment() {
        return soldierRecruitment;
    }

    public String getSelectedBuilding() {
        return selectedBuilding;
    }
}