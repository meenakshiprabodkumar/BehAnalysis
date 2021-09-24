package ProgTools;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * Scoring_Assistant_0.java
 *
 * Created on Apr 19, 2011, 4:48:42 PM
 */
import ij.IJ;
import ij.ImageListener;
import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import ij.gui.StackWindow;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.ImageCalculator;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import static org.opencv.videoio.Videoio.CAP_PROP_FPS;
import static org.opencv.videoio.Videoio.CAP_PROP_FRAME_COUNT;
import static org.opencv.videoio.Videoio.CAP_PROP_FRAME_HEIGHT;
import static org.opencv.videoio.Videoio.CAP_PROP_FRAME_WIDTH;
import static org.opencv.videoio.Videoio.CAP_PROP_POS_FRAMES;

/**
 *
 * @author Every one
 */
public class Scoring_Assistant_0 extends java.awt.Frame implements MouseListener, KeyListener, Runnable {

    File DataFile, ImgFile = null;
    ImageStack Stack = null;
    ImagePlus imp = null;
    boolean Error = false;
    private int nFrames = 0;  //Total number of frames in the image seq
    private int nScoringFrames = 30;  //Number of consequitive frames to score
    private File BgdFile;       // Place to store the background image file
    private ImagePlus bgdimp;
    private ImagePlus Signal;
    private ResultsTable rt;
    private ImageCanvas previousCanvas = null;
    private boolean mFlag = false;
    private boolean kFlag = false;
    private boolean reentry = false;
    private VideoReader vr = null;
//    private long memSize;
//    private double mainChunkSize;
    private int buffFrames;
    static boolean runningStatus = false;
//    private int frameMax = 0;

    /**
     * Creates new form Scoring_Assistant
     */
    public Scoring_Assistant_0() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Error setting native LAF: " + e);
        }
        initComponents();

        //WindowManager.addWindow(this);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Progress = new javax.swing.JFrame();
        SeqProgress = new javax.swing.JProgressBar();
        OverAllProgressBar = new javax.swing.JProgressBar();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        NxtSeq = new javax.swing.JButton();
        AutoAdvStat = new javax.swing.JCheckBox();
        DoneScoring = new javax.swing.JButton();
        nImgs = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nImgAdv = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        mThreshold = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        nConsecutive = new javax.swing.JTextField();
        DataBrowse = new javax.swing.JButton();
        ResultBrowse = new javax.swing.JButton();
        Score = new javax.swing.JButton();
        nPeople = new javax.swing.JSpinner();
        SetBgd = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        SubBgd = new javax.swing.JButton();

        Progress.setTitle("Progress..");
        Progress.setBounds(new java.awt.Rectangle(0, 0, 300, 250));

        jLabel6.setText("Over All Progress");

        jLabel7.setText("Progress in sequence..");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Seq. Navigation"));

        NxtSeq.setText("Next Seq");
        NxtSeq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NxtSeqActionPerformed(evt);
            }
        });

        AutoAdvStat.setText("Auto Advance");
        AutoAdvStat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AutoAdvStatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(AutoAdvStat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(NxtSeq, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AutoAdvStat, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NxtSeq, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        DoneScoring.setText("Done Scoring");
        DoneScoring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoneScoringActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ProgressLayout = new javax.swing.GroupLayout(Progress.getContentPane());
        Progress.getContentPane().setLayout(ProgressLayout);
        ProgressLayout.setHorizontalGroup(
            ProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProgressLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OverAllProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(SeqProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addGroup(ProgressLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(DoneScoring)))
                .addContainerGap())
        );
        ProgressLayout.setVerticalGroup(
            ProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProgressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OverAllProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(11, 11, 11)
                .addComponent(SeqProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(ProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ProgressLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(27, 27, 27))
                    .addGroup(ProgressLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(DoneScoring, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        setBackground(new java.awt.Color(236, 233, 216));
        setTitle("Scoring Assistant");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jLabel1.setText("Number of Images to score");

        jLabel2.setText("Motion Threshold");

        jLabel3.setText("Number of frames to advance");

        nImgAdv.setText("5");

        jLabel4.setText("Number of people scoring");

        jLabel5.setText("Number of consecutive frames");

        DataBrowse.setText("Select Data file");
        DataBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataBrowseActionPerformed(evt);
            }
        });

        ResultBrowse.setText("Export Results File");
        ResultBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResultBrowseActionPerformed(evt);
            }
        });

        Score.setText("Start_Scoring");
        Score.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScoreActionPerformed(evt);
            }
        });

        nPeople.setModel(new javax.swing.SpinnerNumberModel(1, 0, 2, 1));

        SetBgd.setText("Select Background");
        SetBgd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetBgdActionPerformed(evt);
            }
        });

        jButton1.setText("Generate Background");

        SubBgd.setText("Substract BackGround");
        SubBgd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubBgdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(nConsecutive, 0, 0, Short.MAX_VALUE)
                        .addComponent(mThreshold, 0, 0, Short.MAX_VALUE)
                        .addComponent(nImgAdv, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(nImgs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nPeople, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DataBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SetBgd, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Score, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResultBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SubBgd)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nImgs, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nImgAdv, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nPeople, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mThreshold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nConsecutive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DataBrowse)
                    .addComponent(SetBgd)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Score)
                    .addComponent(ResultBrowse)
                    .addComponent(SubBgd))
                .addGap(47, 47, 47))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    private void DataBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DataBrowseActionPerformed

        JFileChooser Fc = new JFileChooser();
        Fc.setMultiSelectionEnabled(false);
        Fc.setVisible(true);
        int result = Fc.showOpenDialog(null);

        if (result == Fc.APPROVE_OPTION) {
            ImgFile = Fc.getSelectedFile();
            if (ImgFile.exists() && ImgFile.isFile()) {
                // imp = new ImagePlus(ImgFile.getPath());
                //estimate size of memory available in imagej
                //initialise buffer size
                double memSize = IJ.maxMemory() - IJ.currentMemory();
                double mainChunkSize = 0.5 * memSize;
                // open file using opencv
                vr = new VideoReader(ImgFile);
                Error = !vr.initMainStack((int) mainChunkSize);
            } else {
                Error = true;
            }
            if (!Error) {
                imp = new ImagePlus();
                Stack = imp.createEmptyStack();
                addPostChunk(vr.getNextChunk());
                imp.setStack(Stack);
                nFrames = Stack.getSize();
                buffFrames = (int) (0.4 * nFrames);
                Calibration cal = imp.getCalibration();
                cal.fps = vr.fps;
                System.out.println("imageplus fps: " + imp.getCalibration().fps);
                imp.show();
            }
        } else {
            Error = true;
        }
    }//GEN-LAST:event_DataBrowseActionPerformed

    private void ResultBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResultBrowseActionPerformed

        JFileChooser res = new JFileChooser();
        res.setDialogType(res.SAVE_DIALOG);
        res.setVisible(true);
        res.showSaveDialog(res);
        //File

    }//GEN-LAST:event_ResultBrowseActionPerformed

    private void ScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScoreActionPerformed

        Progress.toFront();

        Progress.setVisible(true);
        OverAllProgressBar.setMinimum(0);
        OverAllProgressBar.setMaximum((int) vr.totalFrames);
        OverAllProgressBar.setValue(imp.getCurrentSlice());
        
        SeqProgress.setMinimum(0);
        SeqProgress.setMaximum(nFrames); //should this be nFrames or advance?

        rt = new ResultsTable();
        rt.show("Score");
        reentry = false;
        //for(int count = 0 ; count < rt.addColumns();

    }//GEN-LAST:event_ScoreActionPerformed

    private void AutoAdvStatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AutoAdvStatActionPerformed
        NxtSeq.setEnabled(!AutoAdvStat.isSelected());
        if (AutoAdvStat.isSelected()) {

            if (imp != null) {
                ImageWindow win = imp.getWindow();
                win.toFront();
                win.removeKeyListener(IJ.getInstance());
                win.removeKeyListener(IJ.getInstance());
                win.addKeyListener(this);
                ImageCanvas canvas = imp.getCanvas();
                canvas.disablePopupMenu(true);
                if (canvas != previousCanvas) {
                    if (previousCanvas != null) {
                        previousCanvas.removeMouseListener(this);
                    }
                    canvas.addMouseListener(this);
                    // canvas.addKeyListener(this);
                    previousCanvas = canvas;
                }
            } else {
                if (previousCanvas != null) {
                    previousCanvas.removeMouseListener(this);
                }
                previousCanvas = null;
            }
            this.NxtSeqActionPerformed(null);
        }


    }//GEN-LAST:event_AutoAdvStatActionPerformed

    private void SetBgdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetBgdActionPerformed
        JFileChooser FC = new JFileChooser();
        int result = FC.showOpenDialog(null);

        if (result == FC.APPROVE_OPTION) {
            BgdFile = FC.getSelectedFile();
            if (BgdFile.exists() && BgdFile.isFile()) {
                bgdimp = new ImagePlus(BgdFile.getPath());

            }
            if (bgdimp == null) {
                Error = true;
            } else {

                Stack = bgdimp.getImageStack();
                int bgdSz = Stack.getSize();
                if (nFrames < bgdSz) {
                    ij.IJ.showMessage("Error: Stack mismatch", "The number of frames in background does not match the image stack. Using the first slice and ignoring the rest");
                    while (Stack.getSize() > 1) {
                        Stack.deleteLastSlice();
                    }
                }
                bgdimp.show();
            }
        } else {
            Error = true;
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_SetBgdActionPerformed

    private void NxtSeqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NxtSeqActionPerformed
        // TODO add your handling code here:
//        OverAllProgressBar.setValue(imp.getCurrentSlice());
        int advance = Integer.parseInt(nImgAdv.getText());
        OverAllProgressBar.setValue(OverAllProgressBar.getValue() + advance);

        rt.incrementCounter();

        //rt.show("Score");
        reentry = true;
        (new Thread(this)).start();
    }//GEN-LAST:event_NxtSeqActionPerformed

    private void SubBgdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubBgdActionPerformed

        int bSlices = bgdimp.getCurrentSlice();
        ImagePlus curSlice;
        ImageStack Stack = imp.getStack();
        ImageStack resStack = new ImageStack(Stack.getWidth(), Stack.getHeight());
        ImageCalculator Calc = new ImageCalculator();
        String imageTitle = imp.getTitle();
        Calibration cal = imp.getCalibration();
        int maxSlice = Stack.getSize();
        boolean status = true;
        if (bSlices == 1) {
            for (int count = 1; count < maxSlice && status; count++) {
                curSlice = new ImagePlus(imageTitle, Stack.getProcessor(count));
                curSlice = Calc.run("Difference create", curSlice, bgdimp);

                resStack.addSlice(imageTitle, curSlice.getProcessor());

            }
            while (imp.getCurrentSlice() > 1) {
                Stack.deleteLastSlice();
            }
            imp.setStack(resStack);
            imp.show();
        } else {
            new ImageCalculator().run("Difference stack", imp, bgdimp);
        }
    }//GEN-LAST:event_SubBgdActionPerformed

    private void DoneScoringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoneScoringActionPerformed
        // TODO add your handling code here:
        ImageWindow win = imp.getWindow();
        win.removeMouseListener(this);
        win.removeKeyListener(this);
        win.addMouseListener(IJ.getInstance());
        win.addKeyListener(IJ.getInstance());
    }//GEN-LAST:event_DoneScoringActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox AutoAdvStat;
    private javax.swing.JButton DataBrowse;
    private javax.swing.JButton DoneScoring;
    private javax.swing.JButton NxtSeq;
    private javax.swing.JProgressBar OverAllProgressBar;
    private javax.swing.JFrame Progress;
    private javax.swing.JButton ResultBrowse;
    private javax.swing.JButton Score;
    private javax.swing.JProgressBar SeqProgress;
    private javax.swing.JButton SetBgd;
    private javax.swing.JButton SubBgd;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField mThreshold;
    private javax.swing.JTextField nConsecutive;
    private javax.swing.JTextField nImgAdv;
    private javax.swing.JTextField nImgs;
    private javax.swing.JSpinner nPeople;
    // End of variables declaration//GEN-END:variables

    public void mouseClicked(MouseEvent e) {
        int row = rt.getCounter();
        //IJ.showMessage("Mouse Active");
        //is there a current instance 
        if(!runningStatus){
        if (e.getButton() == e.BUTTON1) {
            rt.setValue("Mouse", row - 1, 1);
        } else if (e.getButton() == e.BUTTON2) {
            rt.setValue("Mouse", row - 1, 0);
        }
        rt.show("Score");
        if (AutoAdvStat.isSelected()) {
            mFlag = true;
            runningStatus = true;
            this.NxtSeqActionPerformed(null);
        }
        }
    }

    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyTyped(KeyEvent e) {
        int row = rt.getCounter();
        if (e.getKeyChar() == 'y' || e.getKeyChar() == 'Y') {
            rt.setValue("Key", row - 1, 1);
        } else {
            rt.setValue("Key", row - 1, 0);
        }
        rt.show("Score");
        if (AutoAdvStat.isSelected()) {
            kFlag = true;
            this.NxtSeqActionPerformed(null);
        }

    }

    public void keyPressed(KeyEvent e) {
        IJ.getInstance().keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
        IJ.getInstance().keyReleased(e);
    }

    public void run() {
        Stack = imp.getStack();
        int cSlice = imp.getCurrentSlice();

        // if(cSlice >= nFrames)
        //    return;
        Integer advance = new Integer(nImgAdv.getText());
        if (advance > buffFrames) {

            IJ.showMessage(" Can not advance " + advance + " frames due to limited memory so advancing " + buffFrames);
            advance = buffFrames;
            nImgAdv.setText("" + buffFrames);
        }
        int maxBound = nFrames - advance;
        if (cSlice > maxBound - advance && cSlice < maxBound) {
//        System.out.println("entered nFrames < maxBound + advance if block i.e. fetch next chunk");        
            new Thread(vr).start(); //will fetch the next chunk
        }
        if (cSlice >= maxBound) {
            System.out.println("entered nFrames < maxBound if block");
            if (!vr.isNextChunkReady()) {
                if (vr.isEOF()) {
                    return;
                }
                System.out.println("SR chunkready = FALSE" + vr.isNextChunkReady());
                try {
                    System.out.println("try sleep block");
                    IJ.showStatus("Waiting for next set of video freames.");
                    Thread.currentThread().wait(5000); //timeout = 5s //thread synchronization issue?
                } catch (InterruptedException ex) {
                    Logger.getLogger(Scoring_Assistant_0.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!vr.isNextChunkReady()) {
                    IJ.showStatus("Error fetching video frames.");
                    ij.IJ.showMessage("Error", "Error fetching video frames. Timed out. ");
                    return;
                }
            }
            System.out.println("SR chunkready = TRUE" + vr.isNextChunkReady());
            this.deletePreChunk(); //private method of scoring assistant that deletes the first buffsize number of slices.
            
            this.addPostChunk(vr.getNextChunk()); //private method of scoring assistant that deletes the first buffsize number of slices.
            cSlice = cSlice - vr.getNextChunk().length;
            imp.setSlice(cSlice);
//           return;
            // nFrames = Stack.getSize();
        }

        SeqProgress.setMaximum(advance);

        Calibration cal = imp.getCalibration();
        long frameRate = 1;
        if (cal.fps != 0.0) {
            frameRate = (long) (1000.0 / cal.fps);
        }
        System.out.print("The fps before advance is " + cal.fps);
        long timeInc = frameRate;
        ImageWindow win = imp.getWindow();
        StackWindow swin = (StackWindow) win;
        swin.setAnimate(true);
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        long time = System.currentTimeMillis(), nextime = System.currentTimeMillis();
        // long stoptime = timeInc*advance.intValue();
        long timeDiff = nextime - time;
        advance = cSlice + advance > Stack.getSize() ? Stack.getSize() - cSlice : advance;
        for (int count = 0; count <= advance; count++) {
            SeqProgress.setValue(count);
            IJ.showStatus((int) (cal.fps + 0.5) + " fps");

            time = System.currentTimeMillis();
            if (time < nextime) {
                // IJ.wait((int)(nextime - time));
                try {
                    timeDiff = nextime - time;
                    Thread.currentThread().sleep(timeDiff);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Scoring_Assistant_0.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Thread.currentThread().yield();
                nextime += timeInc;
            }
            swin.showSlice(cSlice + count);
        }
//        OverAllProgressBar.setValue(imp.getCurrentSlice());
        OverAllProgressBar.setValue(OverAllProgressBar.getValue() + advance);
        runningStatus = false;
        return;
    }

    private void deletePreChunk() {
        for (int i = 0; i < buffFrames; i++) {
            Stack.deleteSlice(i + 1);
        }
    }

    private void addPostChunk(ImageProcessor[] nextChunk) {
        for (int i = 0; i < nextChunk.length; i++) {
            if (nextChunk[i] == null) {
                return;
            }
            Stack.addSlice(nextChunk[i]);
           // Stack.trim();
        }
    }

    private class VideoReader extends java.awt.Frame implements Runnable {

        boolean success = false;
        boolean read = true;
        double height = 0.0;
        double width = 0.0;
        double totalFrames = 0.0;
        double posFrame = 0.0;
        double fps = 0.0;
        VideoCapture cap = null;
        private ImageProcessor[] ipArr = null;
        boolean chunkReady = false;
        private boolean eof = false;
        //pixel size and number of pixels

        public VideoReader(File ImgFile) {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            this.openVideo(ImgFile);
            if (SwingUtilities.isEventDispatchThread()) {
                System.out.print("This is Event Dispatch Thread");
            } else {
                System.out.print("This is not a Event Dispatch Thread");
            }
        }

        @Override
        public void run() {
            //reads next chunk
            synchronized (this) {
                chunkReady = false;
                System.out.println("VR chunkready1" + chunkReady);
            }
            //           if (success && read) {
            ipArr = new ImageProcessor[buffFrames];
            readFrames(buffFrames);
            synchronized (this) {
                chunkReady = true;
                System.out.println("VR chunkready2" + chunkReady);
            }
            //           }
//              else {
//                ij.IJ.showMessage("Error", "Error opening/reading video file.");
//            }

        }

        public void openVideo(File ImgFile) {
            cap = new VideoCapture(ImgFile.getAbsolutePath());
            if (cap.isOpened()) {
                System.out.println("Video opened.");
                //read video file using opencv
                height = cap.get(CAP_PROP_FRAME_HEIGHT); //height
                width = cap.get(CAP_PROP_FRAME_WIDTH); //width
                //double format = cap.get(CAP_PROP_FORMAT); //0.0 <- ?
                //double codecPixFormat = cap.get(CAP_PROP_CODEC_PIXEL_FORMAT); //8.08E8 <- ?
                //double buffsize = cap.get(CAP_PROP_BUFFERSIZE); //0
                posFrame = cap.get(CAP_PROP_POS_FRAMES); //0
                fps = cap.get(CAP_PROP_FPS); //25
                totalFrames = cap.get(CAP_PROP_FRAME_COUNT); //3715 <- checks out - got 3700 for 2.28 vid of 25fps         
                System.out.println("Height: " + height + " width: " + width + " FPS: " + fps + "total frame count: " + totalFrames);
                success = true;
            } else {
                success = false;
                //imagej dialog box mentioning error?
            }
        }

        public BufferedImage Mat2BufferedImage(Mat mat) throws IOException {
            //Encoding the image
            MatOfByte matOfByte = new MatOfByte();
            Imgcodecs.imencode(".jpg", mat, matOfByte);
            //Storing the encoded Mat in a byte array
            byte[] byteArray = matOfByte.toArray();
            //Preparing the Buffered Image
            InputStream in = new ByteArrayInputStream(byteArray);
            BufferedImage bufImage = ImageIO.read(in);
            return bufImage;
        }

        private boolean initMainStack(int mainChunkSize) {
            if (posFrame != 0) {
                return false;
            }

            Mat image = new Mat();
            BufferedImage buff = null;
            read = cap.read(image);
            System.out.println("read: " + read);
            System.out.println("posFrame: " + posFrame);

            //calculate required number of frames for init stack
            nFrames = (int) (mainChunkSize / (image.elemSize() * image.width() * image.height()));
            ipArr = new ImageProcessor[nFrames];
            //make ip and store it
            try {
                buff = Mat2BufferedImage(image);
            } catch (IOException ex) {
                Logger.getLogger(Scoring_Assistant_0.class.getName()).log(Level.SEVERE, null, ex);
            }
            ImagePlus tempImp = new ImagePlus(String.valueOf(posFrame + 1), buff);

            ipArr[0] = tempImp.getProcessor();

            if (success && read) {
                readFrames(nFrames);
            } else {
                ij.IJ.showMessage("Error", "Error opening/reading video file.");
                return false;
            }
            return true;
        }

        private synchronized boolean isNextChunkReady() {
            return chunkReady;
        }

        private ImageProcessor[] getNextChunk() {
            return ipArr;
        }

        private void readFrames(int frames) {
            Mat image = null;
            BufferedImage buff = null;
            ImagePlus tempImp = null;
            int idx = 0;
            do {
                image = new Mat();
                read = cap.read(image);
                if (read) {
                    try {
                        buff = Mat2BufferedImage(image);
                    } catch (IOException ex) {
                        Logger.getLogger(Scoring_Assistant_0.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tempImp = new ImagePlus(String.valueOf(idx + 1), buff);
                    ipArr[idx] = tempImp.getProcessor();
                    idx = idx + 1;
                    posFrame = cap.get(CAP_PROP_POS_FRAMES);
                }
            } while (idx < frames && read);
            eof = true;
        }

        private boolean isEOF() {
            return eof;
        }
    }

}
