package com.sgcc.pda.plugin;

import com.sgcc.pda.framelibrary.protocol698.Frame698;
import com.sgcc.pda.framelibrary.utils.TextUtils;

import javax.swing.*;
import java.awt.event.*;

public class FrameParserDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JEditorPane etFrame;
    private JEditorPane etFormatFrame;
    private JButton btClear;

    public FrameParserDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("帧报文"); // 设置title 2017/3/18 09:50
        setSize(500, 300); // 设置窗口大小 2017/3/18 09:50

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        btClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                etFrame.setText("");
            }
        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        String frameStr = etFrame.getText();
        String formatFrameStr =TextUtils.isEmpty(frameStr)? "请先输入帧报文":new Frame698().format(frameStr);
        etFormatFrame.setText(formatFrameStr);
        // dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        FrameParserDialog dialog = new FrameParserDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
