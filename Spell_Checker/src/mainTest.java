/**
 * this program was designed to be a spell checker
 *
 * @author David kipnis and Gal Toubul
 * @version 1.0
 */

import java.io.File;
import java.io.FileNotFoundException;

public class mainTest {
    public static void main(String[] args)
            throws FileNotFoundException {
        //given text from mmn 15
        String currFolderPath = new File("").getAbsolutePath();

        SpellCheck sp = new SpellCheck();
        String text_path1 = currFolderPath + "\\src\\spell-words.txt";
        sp.run(text_path1);
        //our text
        String text_path2 = currFolderPath + "\\src\\spell-words 2.txt";
        sp.run(text_path2);
    }
}
