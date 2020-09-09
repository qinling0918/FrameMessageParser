package com.sgcc.pda.plugin;

import com.sgcc.pda.framelibrary.protocol698.Frame698;
import com.sgcc.pda.framelibrary.protocol698.apdu.APDUParser;
import com.sgcc.pda.framelibrary.protocol698.apdu.ActionAPDUParser;
import com.sgcc.pda.framelibrary.protocol698.apdu.GetAPDUParser;
import com.sgcc.pda.framelibrary.protocol698.apdu.SetAPDUParser;
import com.sgcc.pda.framelibrary.utils.TextUtils;

import javax.swing.*;
import java.awt.event.*;

import static com.sgcc.pda.framelibrary.protocol698.apdu.APDU.GET_RESPONSE;
import static com.sgcc.pda.framelibrary.protocol698.apdu.ActionAPDUParser.ACTION_RESPONSE;
import static com.sgcc.pda.framelibrary.protocol698.apdu.GetAPDUParser.PARSER_SUCCESS;
import static com.sgcc.pda.framelibrary.protocol698.apdu.SetAPDUParser.SET_RESPONSE;

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
        String frameStr = etFrame.getText().trim();

        String formatResult = "";
        Frame698.Parser parser = new Frame698.Parser(frameStr);
        if (parser.parseCode==0){
            formatResult = parser.toFormatString();
            String apdu = parser.getLinkDataStr();
            String apduformatResult = formatApdu(apdu);
            formatResult = TextUtils.isEmpty(apduformatResult) ? formatResult : formatResult + "\n" + apduformatResult;
        }else{
            //直接当成链路层解析
            formatResult = formatApdu(frameStr);
        }

        String formatFrameStr = TextUtils.isEmpty(frameStr) ? "请先输入帧报文" : formatResult;

        etFormatFrame.setText(formatFrameStr);
        // dispose();
    }

    private String formatApdu(String serverApdu) {
        if (TextUtils.isEmpty(serverApdu)) {
            return "";
        }
        int classify = Integer.parseInt(serverApdu.substring(0, 2), 16);
        APDUParser parser = null;
        switch (classify) {
            case GET_RESPONSE:
                parser = new GetAPDUParser(serverApdu);
                break;
            case ACTION_RESPONSE:
                parser = new ActionAPDUParser(serverApdu);
                break;
            case SET_RESPONSE:
                parser = new SetAPDUParser(serverApdu);
                break;
            default:
                parser = APDUParser.ERROR_PARSER(APDUParser.ParseResult.ERROR_PARSER_NOT_MATCH);
                break;
        }
        if (parser.parseCode == PARSER_SUCCESS) {
            return parser.toFormatString();
        }
        return "";

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
