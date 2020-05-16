package rewirteJList;

/**
 * @author ���
 * ��Ⱦ��������FileLabel��������Ⱦ
 */
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import editor.FileLabel;

//�ο���https://blog.csdn.net/qq_21808961/article/details/80658063
@SuppressWarnings({ "rawtypes", "serial" })
public class FriListCellRenderer extends JLabel implements ListCellRenderer {
	public FriListCellRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		FileLabel fileLabel = (FileLabel) value;// ת��ΪFileLabel
		String text = fileLabel.name;
		setText(text);// ��ȡ���ֲ�����
		// ѹ��ͼƬ������
		Image img = fileLabel.u.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		setIcon(new ImageIcon(img));
		setIconTextGap(30);
		//ѡ��Ļ�isSelected����1�������ñ���
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			// ����ѡȡ��ȡ��ѡȡ��ǰ���뱳����ɫ.
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		return this;
	}
}