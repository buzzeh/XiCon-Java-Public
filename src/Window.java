import java.awt.EventQueue;
import java.awt.FileDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Frame;
import java.awt.Image;

/**
 * 
 * @author Cristhian Benitez
 * @version 1/2/2018
 *
 */
public class Window {

    private JFrame frame;
    JLabel lblBanner;
    private boolean loaded = false;
    JButton btnConvertImage;
    JLabel lblStatus;
    JLabel lblNone;
    String fileName;
    Image image;
    JCheckBox iPhoneCheck;
    JCheckBox iPadCheck;
    JCheckBox iMessagesCheck;
    int[] ptSizeArrayiPhone;
    int[] ptSizeArrayiPad;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Window window = new Window();
                    window.frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the application.
     */
    public Window() {
        initialize();
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        ptSizeArrayiPhone = new int[] { 20, 29, 40, 60, };
        ptSizeArrayiPad = new int[] { 20, 29, 40, 76, 84 };

        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 450, 315);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("XiCon - Icons for Xcode!");

        setUpButtons();

        iPhoneCheck = new JCheckBox("iPhone Icons");
        iPhoneCheck.setBounds(6, 208, 128, 23);
        frame.getContentPane().add(iPhoneCheck);

        iPadCheck = new JCheckBox("iPad Icons");
        iPadCheck.setBounds(162, 208, 128, 23);
        frame.getContentPane().add(iPadCheck);

        iMessagesCheck = new JCheckBox("iMessage Icons");
        iMessagesCheck.setBounds(316, 208, 128, 23);
        frame.getContentPane().add(iMessagesCheck);

        lblBanner = new JLabel("");
        lblBanner.setBounds(169, 100, 438, 87);
        frame.getContentPane().add(lblBanner);

        lblStatus = new JLabel("Status: Please select an image to reize");
        lblStatus.setBounds(16, 270, 428, 16);
        frame.getContentPane().add(lblStatus);

        JLabel lblLogoImage = new JLabel("");
        ImageIcon logoIcon = new ImageIcon(new ImageIcon("logo.png").getImage()
            .getScaledInstance(450, 140, Image.SCALE_SMOOTH));
        lblLogoImage.setIcon(logoIcon);
        lblLogoImage.setBounds(0, 0, 450, 140);
        frame.getContentPane().add(lblLogoImage);

        lblNone = new JLabel("");
        lblNone.setIcon(new ImageIcon("none.png"));
        lblNone.setBounds(169, 95, 100, 100);
        frame.getContentPane().add(lblNone);
    }


    /**
     * Method is responsible for the actions that take place when you click the
     * select button.
     * That is, it opens up the window
     * 
     * @throws IOException
     */
    private void select() throws IOException {
        FileDialog myDialog = new FileDialog((Frame)null,
            "Select File to Open");
        myDialog.setMode(FileDialog.LOAD);
        myDialog.setFilenameFilter((dir, name) -> name.endsWith(".png") || name
            .endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(
                ".JPEG") || name.endsWith(".PNG") || name.endsWith(".JPG"));
        myDialog.setVisible(true);
        fileName = myDialog.getFile();

        // ************ This section is for disabling the covert button, if
        // there is no image loaded then the button will be grayed out
        // ************
        if (fileName != null) {
            loaded = true;
        }
        else {
            loaded = false;
        }

        if (loaded) {
            btnConvertImage.setEnabled(true);
            image = ImageIO.read(new File(myDialog.getDirectory() + fileName));

            ImageManipulator imgMani = new ImageManipulator(image, 100, 100);
            lblBanner.setIcon(new ImageIcon(imgMani.getNewImage()));
            lblStatus.setText(
                "Status: Select the type of icons you wish and click convert: PNG or JPG");
            lblNone.setVisible(false);
            lblBanner.setVisible(true);
        }
        else {
            btnConvertImage.setEnabled(false);
            lblStatus.setText("Status: Loading Canceled");
            lblBanner.setVisible(false);
            lblNone.setVisible(true);
        }
        // System.out.println(fileName + " chosen.");
    }


    /**
     * Here is where the important stuff happens, this method is responsible for
     * saving all the icons, it is called when you press the convert button
     * 
     * @throws IOException
     */
    private void saveFiles() throws IOException {

        // Get the save directory
        JFileChooser myFileChooser = new JFileChooser(FileSystemView
            .getFileSystemView().getHomeDirectory());
        myFileChooser.setDialogTitle("Choose a directory to save your file: ");
        myFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        myFileChooser.showSaveDialog(myFileChooser);
        // Print the save directory
        System.out.println("You selected the directory: " + myFileChooser
            .getCurrentDirectory());

        // Make sure that you didn't cancel
        if (myFileChooser.getSelectedFile() != null) {

            // Create all the iPhone related icons here
            if (iPhoneCheck.isSelected()) {
                for (int x = 0; x < ptSizeArrayiPhone.length; x++) {
                    String outFileName2x = ("Icon-iPhone" + ptSizeArrayiPhone[x]
                        + "pt@2x.png");
                    String outFileName3x = ("Icon-iPhone" + ptSizeArrayiPhone[x]
                        + "pt@3x.png");

                    File outputfile2x = new File((myFileChooser
                        .getCurrentDirectory()) + "/" + outFileName2x);
                    File outputfile3x = new File((myFileChooser
                        .getCurrentDirectory()) + "/" + outFileName3x);

                    ImageManipulator imgMani2x = new ImageManipulator(image,
                        ptSizeArrayiPhone[x] * 2, ptSizeArrayiPhone[x] * 2);
                    ImageManipulator imgMani3x = new ImageManipulator(image,
                        ptSizeArrayiPhone[x] * 3, ptSizeArrayiPhone[x] * 3);

                    ImageIO.write(imgMani2x.getBufferedImage(), "png",
                        outputfile2x);
                    ImageIO.write(imgMani3x.getBufferedImage(), "png",
                        outputfile3x);
                }
            }

            // Create all the iPad related icons here
            if (iPhoneCheck.isSelected()) {
                for (int y = 0; y < ptSizeArrayiPad.length; y++) {
                    String outFileName2x = ("Icon-iPad-" + ptSizeArrayiPad[y]
                        + "pt@2x.png");
                    String outFileName3x = ("Icon-iPad" + ptSizeArrayiPad[y]
                        + "pt@3x.png");

                    File outputfile2x = new File((myFileChooser
                        .getCurrentDirectory()) + "/" + outFileName2x);
                    File outputfile3x = new File((myFileChooser
                        .getCurrentDirectory()) + "/" + outFileName3x);

                    ImageManipulator imgMani2x = new ImageManipulator(image,
                        ptSizeArrayiPad[y] * 2, ptSizeArrayiPad[y] * 2);
                    ImageManipulator imgMani3x = new ImageManipulator(image,
                        ptSizeArrayiPad[y] * 3, ptSizeArrayiPad[y] * 3);

                    ImageIO.write(imgMani2x.getBufferedImage(), "png",
                        outputfile2x);
                    ImageIO.write(imgMani3x.getBufferedImage(), "png",
                        outputfile3x);
                }
            }

            // Messages Icons here
            if (iMessagesCheck.isSelected()) {
                String output60x452x = ("Icon-iMessages-iPhone-60x45@2x.png");
                String output60x453x = ("Icon-iMessages-iPhone-60x45@3x.png");
                String output67x502x = ("Icon-iMessages-iPad-67x50@2x.png");
                String output67x503x = ("Icon-iMessages-iPad-67x50@3x.png");
                String output74x552x = ("Icon-iMessages-iPadPro-74x55@2x.png");
                String output74x553x = ("Icon-iMessages-iPadPro-74x55@3x.png");

                String outputStore = ("Icon-iMessages-Store-1024x768.png");

                String outputMessages27x202x = ("Icon-iMessages-27x20@2x.png");
                String outputMessages27x203x = ("Icon-iMessages-27x20@3x.png");
                String outputMessages32x242x = ("Icon-iMessages-32x24@2x.png");
                String outputMessages32x243x = ("Icon-iMessages-32x24@3x.png");

                // -----------------

                File outputFile60x452x = new File((myFileChooser
                    .getCurrentDirectory()) + "/" + output60x452x);
                File outputFile60x453x = new File((myFileChooser
                    .getCurrentDirectory()) + "/" + output60x453x);
                File outputFile67x502x = new File((myFileChooser
                    .getCurrentDirectory()) + "/" + output67x502x);
                File outputFile67x503x = new File((myFileChooser
                    .getCurrentDirectory()) + "/" + output67x503x);
                File outputFile74x552x = new File((myFileChooser
                    .getCurrentDirectory()) + "/" + output74x552x);
                File outputFile74x553x = new File((myFileChooser
                    .getCurrentDirectory()) + "/" + output74x553x);

                File outputFileStore = new File((myFileChooser
                    .getCurrentDirectory()) + "/" + outputStore);

                File outputMessagesFile27x202x = new File((myFileChooser
                    .getCurrentDirectory()) + "/" + outputMessages27x202x);
                File outputMessagesFile27x203x = new File((myFileChooser
                    .getCurrentDirectory()) + "/" + outputMessages27x203x);
                File outputMessagesFile32x242x = new File((myFileChooser
                    .getCurrentDirectory()) + "/" + outputMessages32x242x);
                File outputMessagesFile32x243x = new File((myFileChooser
                    .getCurrentDirectory()) + "/" + outputMessages32x243x);

                // -----------------

                ImageManipulator output60x452xMani = new ImageManipulator(image,
                    60 * 2, 45 * 2);
                ImageManipulator output60x453xMani = new ImageManipulator(image,
                    60 * 3, 45 * 3);
                ImageManipulator output67x502xMani = new ImageManipulator(image,
                    67 * 2, 50 * 2);
                ImageManipulator output67x503xMani = new ImageManipulator(image,
                    67 * 3, 50 * 3);
                ImageManipulator outputFile74x552xMani = new ImageManipulator(
                    image, 74 * 2, 52 * 2);
                ImageManipulator outputFile74x553xMani = new ImageManipulator(
                    image, 74 * 3, 52 * 3);

                ImageManipulator outputFileStoreMani = new ImageManipulator(
                    image, 1024, 768);

                ImageManipulator outputMessages27x202xMani =
                    new ImageManipulator(image, 27 * 2, 20 * 2);
                ImageManipulator outputMessages27x203xMani =
                    new ImageManipulator(image, 27 * 3, 20 * 3);
                ImageManipulator outputMessages32x242xMani =
                    new ImageManipulator(image, 32 * 2, 24 * 2);
                ImageManipulator outputMessages32x243xMani =
                    new ImageManipulator(image, 32 * 3, 24 * 3);

                // -----------

                ImageIO.write(output60x452xMani.getBufferedImage(), "png",
                    outputFile60x452x);
                ImageIO.write(output60x453xMani.getBufferedImage(), "png",
                    outputFile60x453x);
                ImageIO.write(output67x502xMani.getBufferedImage(), "png",
                    outputFile67x502x);
                ImageIO.write(output67x503xMani.getBufferedImage(), "png",
                    outputFile67x503x);
                ImageIO.write(outputFile74x552xMani.getBufferedImage(), "png",
                    outputFile74x552x);
                ImageIO.write(outputFile74x553xMani.getBufferedImage(), "png",
                    outputFile74x553x);

                ImageIO.write(outputFileStoreMani.getBufferedImage(), "png",
                    outputFileStore);

                ImageIO.write(outputMessages27x202xMani.getBufferedImage(),
                    "png", outputMessagesFile27x202x);
                ImageIO.write(outputMessages27x203xMani.getBufferedImage(),
                    "png", outputMessagesFile27x203x);
                ImageIO.write(outputMessages32x242xMani.getBufferedImage(),
                    "png", outputMessagesFile32x242x);
                ImageIO.write(outputMessages32x243xMani.getBufferedImage(),
                    "png", outputMessagesFile32x243x);
            }
            // lblStatus.setText("When no checks, only the App Store Icon
            // generated");
        }
        // Canceled
    }


    /**
     * This method inits the buttons you see on screen
     */
    public void setUpButtons() {
        JButton btnSelect = new JButton("Select Image");
        btnSelect.setBounds(6, 238, 220, 29);
        btnSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        frame.getContentPane().add(btnSelect);

        btnConvertImage = new JButton("Convert Image");
        btnConvertImage.setEnabled(false);
        btnConvertImage.setBounds(224, 238, 220, 29);
        frame.getContentPane().add(btnConvertImage);

        // **************************************Actions Below
        btnSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello");
                try {
                    select();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Convert & Save here
        btnConvertImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveFiles();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("You have pressed convert");
            }
        });

    }
}
