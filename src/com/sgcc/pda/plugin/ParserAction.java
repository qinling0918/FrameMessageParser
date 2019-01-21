package com.sgcc.pda.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jf.util.SparseArray;

public class ParserAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        FrameParserDialog dialog = new FrameParserDialog();
        dialog.setVisible(true);
        // 设置大小
        //dialog.setSize(810, 405);
        // 自适应大小
        // pack();
        // 设置居中，放在setSize后面
       // dialog.setLocationRelativeTo(null);
        // 显示最前
        //dialog.setAlwaysOnTop(true);
        // TODO: insert action logic here
        //Messages.showDialog("Hello,火线！", "Selected Element:", new String[]{"OK"}, -1, null);
        //String inputValue = JOptionPane.showInputDialog("Please input a value");
       /* JFrame frame=new JFrame("帧报文");//实例化窗体对象
        JTextArea editArea=new JTextArea(3, 20);//构造一个文本域
        JTextArea textArea=new JTextArea(3, 20);//构造一个文本域
        textArea.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
        JScrollPane pane=new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.setLayout(new GridLayout(2, 1));
        frame.add(pane);
        frame.setSize(500,300);
        frame.setLocation(100, 100);
        frame.setVisible(true);
*/



}
}
