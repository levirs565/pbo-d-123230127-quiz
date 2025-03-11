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
//        setContentPane(Box.createVerticalBox());
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        var gridPanel = new JPanel(new GridLayout(2, 2));
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

        mDivisiLabel = new JLabel("Divisi");
        mDivisiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mDivisiLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(mDivisiLabel);

        mDivisiComboBox = new JComboBox<>(mDivisiArray);
        mDivisiComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(mDivisiComboBox);

        var kelaminBox = Box.createHorizontalBox();
        kelaminBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(kelaminBox);

        mButtonGroup = new ButtonGroup();

        kelaminBox.add(Box.createHorizontalGlue());

        mJenisKelaminLabel = new JLabel("Jenis Kelamin");
        kelaminBox.add(mJenisKelaminLabel);
        ;

        mPriaRadio = new JRadioButton("Pria");
        kelaminBox.add(mPriaRadio);
        mButtonGroup.add(mPriaRadio);

        mWanitaRadio = new JRadioButton("Wanita");
        kelaminBox.add(mWanitaRadio);
        mButtonGroup.add(mWanitaRadio);

        kelaminBox.add(Box.createHorizontalGlue());

        mSaveButton = new JButton("Simpan Data");
        mSaveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(mSaveButton);

        mListLabel = new JLabel("List Data");
        mListLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(mListLabel);

        mTextArea = new JTextArea();
        mScrollPane = new JScrollPane(mTextArea);
        mScrollPane.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        mScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(mScrollPane);

        mCheckBox = new JCheckBox("Ingin melakukan Import / Export Data?");
        mCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(mCheckBox);

        var bottomBox = Box.createHorizontalBox();
        bottomBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomBox.setVisible(false);
        add(bottomBox);

        bottomBox.add(Box.createHorizontalGlue());

        mImportButton = new JButton("Import From Txt");
        bottomBox.add(mImportButton);

        mExportButton = new JButton("Export To Txt");
        bottomBox.add(mExportButton);

        bottomBox.add(Box.createHorizontalGlue());

        pack();

        makeComponentNotExpand(gridPanel);
        makeComponentNotExpand(mDivisiComboBox);
        makeComponentNotExpand(mSaveButton);
        makeComponentNotExpand(bottomBox);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(new Dimension(300, 450));
        setTitle("Input Member by Levi Rizki Saputra");

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
        });

        mCheckBox.addItemListener((e) -> {
            bottomBox.setVisible(mCheckBox.isSelected());
        });

        mImportButton.addActionListener((e) -> {
            try {
                mTextArea.read(new FileReader("result.txt"), null);
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
