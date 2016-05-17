package menus;

import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.*;

import java.util.Vector;

import constants.CConstants.EMenuItem;
import shapes.CShapeManager;

public class CFileMenu extends CMenu {
	private static final long serialVersionUID = 1L;
	private File file;

	public CFileMenu(EMenuItem[] eMenuItems) {
		super(eMenuItems);
		file = null;
	}
	
	@SuppressWarnings("unchecked")
	private void inputStream() {
		try {
			ObjectInputStream inputStream = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(file)));
			drawingPanel.setShapeManagers((Vector<CShapeManager>)inputStream.readObject());
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	private void outputStream() {
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(file)));
			outputStream.writeObject(drawingPanel.getShapeManagers());
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int showOpenDialog() {
		JFileChooser fileChooser = new JFileChooser(new File("."));
		int reply;
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter("GraphicEditor", "gps");
		fileChooser.setFileFilter(filter);
		reply = fileChooser.showOpenDialog(null);
		file = fileChooser.getSelectedFile();
		return reply;
	}
	
	private int showSaveDialog() {
		JFileChooser fileChooser = new JFileChooser(new File("."));
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter("GraphicEditor", "gps");
		fileChooser.setFileFilter(filter);
		int reply = fileChooser.showSaveDialog(null);
		File renameFile = fileChooser.getSelectedFile();
		String extension = ".gps";
		if(renameFile != null) {
			if(renameFile.getName().contains(extension))
				file = new File(renameFile.getName());
			else
				file = new File(renameFile.getName() + extension);
		}
		return reply;
	}
	
	private void saveOrNot() {
		int reply = 0;
		if(drawingPanel.isDirty()) {
			reply = JOptionPane.showConfirmDialog(null ,"변경내용을 저장하시겠습니까?");
			if(reply == JOptionPane.OK_OPTION) {
				showSaveDialog();
				drawingPanel.setDirty(false);
				outputStream();
			}
		}
	}
	public void newFile() {
		saveOrNot();
		drawingPanel.initPanel();
	}

	public void open( ) {
		int reply;
		saveOrNot();
		reply = showOpenDialog();
		if(reply == JOptionPane.OK_OPTION) {
			inputStream();
		}
	}

	public void save() {
		int reply = JOptionPane.OK_OPTION;
		if(drawingPanel.isDirty()) {
			if(file == null) {
				reply = showSaveDialog();
			}
			if(reply == JOptionPane.OK_OPTION) {
				drawingPanel.setDirty(false);
				outputStream();
			}
		}
	}	
	
	public void saveAs() {
		int reply = showSaveDialog();
		if(reply == JOptionPane.OK_OPTION) {
			outputStream();
		}
	}
	
	public void exit() {
		int reply = JOptionPane.OK_OPTION;
		if(drawingPanel.isDirty()) {
			reply = JOptionPane.showConfirmDialog(null ,"변경내용을 저장하시겠습니까?");
			if(reply == JOptionPane.OK_OPTION) {
				if(file == null) {
					reply = showSaveDialog();
				}
				drawingPanel.setDirty(false);
				outputStream();
			}
		}
		System.exit(1);
	}
}


