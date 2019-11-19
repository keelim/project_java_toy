package Cotroller.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminBookManagement extends JFrame {

    private static AdminBookManagement instance;
    private JTable table1;
    private JButton 뒤로가기Button;
    private JPanel user_panel;
    private JButton 도서등록Button;
    private JButton 도서정보수정Button;
    private JButton 도서삭제Button;

    public AdminBookManagement() {
        setContentPane(user_panel);
        pack();
        setTitle("도서 정보 관리");
        setVisible(true);
        initTable();
        setLocationRelativeTo(null);
        뒤로가기Button.addActionListener(e -> {
            AdminActivity activity = AdminActivity.getInstance();
            setVisible(false);
            activity.setVisible(true);
        });
        도서삭제Button.addActionListener(e -> {
            JOptionPane.showConfirmDialog(null, "도서를 삭제 하겠습니까?");
        });
        도서등록Button.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "도서를 등록을 합니다.");
            //새로운 창을 뛰어서 진행을 해야 하나?
            // 창을 띄어서 값이 반영이면 테이블을 업데이트를 함.
            table1.updateUI();
        });
        도서정보수정Button.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "도서 정보를 수정을 합니다.");
            //새로운 창을 띄어서 정보를 수정을 합니다. --> 기존에 있는 정보를 라벨로 불러와서 사용을 하는 것이 좋을 것 같다.
            // 창을 띄어서 값이 반영이면 테이블을 업데이트를 함.
            table1.updateUI();
        });
    }


    public static void main(String[] args) {
        new AdminBookManagement();
    }

    public static AdminBookManagement getInstance() {
        if (instance == null) {
            return new AdminBookManagement();
        } else {
            return instance;
        }
    }

    private void initTable() {
        String[] a = {"a", "b", "c", "d"};
        String[][] b = {{"a1", "a2", "a3", "sd"},
                {"b1", "b2", "b3", "sd"},
                {"c1", "c2", "c3", "sd"}};
        DefaultTableModel model = new DefaultTableModel(b, a){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(model);
        table1.updateUI();
    }


}
