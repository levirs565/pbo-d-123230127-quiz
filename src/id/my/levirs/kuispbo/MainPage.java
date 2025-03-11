package id.my.levirs.kuispbo;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.*;

public class MainPage extends JFrame {
    private JLabel mNamaDepanLabel, mNamaBelakangLabel, mDivisiLabel, mJenisKelaminLabel;
    private JTextField mNamaDepanField, mNamaBelakangField;
    private String[] mDivisiArray = {"IT", "HRD", "Finance", "Marketing"};
    private JComboBox<String> mDivisiComboBox;
    private JLabel mListLabel;
    private JButton mSaveButton, mImportButton, mExportButton;
    private JCheckBox mCheckBox;
    private JScrollPane mScrollPane;
    private JTextArea mTextArea;
    private ButtonGroup mButtonGroup;
    private JRadioButton mPriaRadio, mWanitaRadio;
    private ArrayList<String> mDataList;

    public MainPage() {
        var contentPage = Box.createVerticalBox();
        contentPage.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPage);

        var gridPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        gridPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(gridPanel);

        mNamaDepanLabel = new JLabel("Nama Depan");
        gridPanel.add(mNamaDepanLabel);

        mNamaBelakangLabel = new JLabel("Nama Belakang");
        gridPanel.add(mNamaBelakangLabel);

        mNamaDepanField = new JTextField();
        gridPanel.add(mNamaDepanField);

        mNamaBelakangField = new JTextField();
        gridPanel.add(mNamaBelakangField);

        add(Box.createVerticalStrut(5));

        mDivisiLabel = new JLabel("Divisi");
        mDivisiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mDivisiLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(mDivisiLabel);

        add(Box.createVerticalStrut(5));

        mDivisiComboBox = new JComboBox<>(mDivisiArray);
        mDivisiComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        mDivisiComboBox.setSelectedIndex(-1);
        add(mDivisiComboBox);

        var kelaminBox = Box.createHorizontalBox();
        kelaminBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(kelaminBox);

        mButtonGroup = new ButtonGroup();

        kelaminBox.add(Box.createHorizontalGlue());

        mJenisKelaminLabel = new JLabel("Jenis Kelamin");
        kelaminBox.add(mJenisKelaminLabel);

        mPriaRadio = new JRadioButton("Pria");
        kelaminBox.add(Box.createHorizontalStrut(5));
        kelaminBox.add(mPriaRadio);
        mButtonGroup.add(mPriaRadio);

        mWanitaRadio = new JRadioButton("Wanita");
        kelaminBox.add(Box.createHorizontalStrut(5));
        kelaminBox.add(mWanitaRadio);
        mButtonGroup.add(mWanitaRadio);

        kelaminBox.add(Box.createHorizontalGlue());

        add(Box.createVerticalStrut(5));
        mSaveButton = new JButton("Simpan Data");
        mSaveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(mSaveButton);

        add(Box.createVerticalStrut(5));
        mListLabel = new JLabel("List Data");
        mListLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(mListLabel);

        add(Box.createVerticalStrut(5));
        mTextArea = new JTextArea();
        mTextArea.setEditable(false);
        mScrollPane = new JScrollPane(mTextArea);
        mScrollPane.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        mScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(mScrollPane);

        add(Box.createVerticalStrut(5));
        mCheckBox = new JCheckBox("Ingin melakukan Import / Export Data?");
        mCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(mCheckBox);

        add(Box.createVerticalStrut(5));
        var bottomBox = Box.createHorizontalBox();
        bottomBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomBox.setVisible(false);
        add(bottomBox);

        bottomBox.add(Box.createHorizontalGlue());

        mImportButton = new JButton("Import From Txt");
        bottomBox.add(mImportButton);

        mExportButton = new JButton("Export To Txt");
        bottomBox.add(Box.createHorizontalStrut(5));
        bottomBox.add(mExportButton);

        bottomBox.add(Box.createHorizontalGlue());

        pack();

        makeComponentNotExpand(gridPanel);
        makeComponentNotExpand(mDivisiComboBox);
        makeComponentNotExpand(kelaminBox);
        makeComponentNotExpand(mSaveButton);
        makeComponentNotExpand(bottomBox);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(400, 550));
        setLocationRelativeTo(null);
        setTitle("Input Member by " + Auth.getInstance().getLoggedAs());

        mDataList = new ArrayList<>();
        mSaveButton.addActionListener((e) -> {
            var namaDepan = mNamaDepanField.getText().trim();
            var namaBelakang = mNamaBelakangField.getText().trim();
            var divisi = mDivisiComboBox.getSelectedItem();
            var jenisKelamin = mPriaRadio.isSelected() ? "Pria" : mWanitaRadio.isSelected() ? "Wanita" : null;
            if (namaDepan.isEmpty() || namaBelakang.isEmpty() || divisi == null || jenisKelamin == null) {
                JOptionPane.showMessageDialog(this,
                        "Data belum lengkap",
                        "Kesalahan",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            mDataList.add(String.format("%s %s | %s | %s", namaDepan, namaBelakang, divisi, jenisKelamin));
            syncDataListToTextArea();

            mNamaDepanField.setText("");
            mNamaBelakangField.setText("");
            mButtonGroup.clearSelection();
            mDivisiComboBox.setSelectedIndex(-1);
        });

        mCheckBox.addItemListener((e) -> {
            bottomBox.setVisible(mCheckBox.isSelected());
        });

        mImportButton.addActionListener((e) -> {
            var chooser = new JFileChooser();
            
            var result = chooser.showOpenDialog(this);
            
            if(result != JFileChooser.APPROVE_OPTION) {
                return;
            }
            
            try {
                mTextArea.read(new FileReader(chooser.getSelectedFile()), null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Gagal Membaca File",
                        "Kesalahan",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            mDataList = Arrays.stream(mTextArea.getText().split("\n")).collect(Collectors.toCollection(ArrayList::new));
        });

        mExportButton.addActionListener((e) -> {
            try {
                mTextArea.write(new FileWriter("result.txt"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Gagal Menulis File",
                        "Kesalahan",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    void syncDataListToTextArea() {
        mTextArea.setText(
                String.join("\n", mDataList)
        );
    }

    void makeComponentNotExpand(Component c) {
        c.setMaximumSize(new Dimension(Short.MAX_VALUE, c.getSize().height));
    }
}
