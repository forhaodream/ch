package ch.codemaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by CH
 * at 2019-09-16
 */
public class Utils {
    /**
     * 以字符为单位读取文件
     */
    public static String readFileByChars(String fileName) {

        StringBuffer sb = new StringBuffer();

        File file = new File(fileName);
        Reader reader = null;
        try {
            //System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    //System.out.print((char) tempchar);
                    sb.append((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void writeFile(String filepath, String data) throws IOException {

        File file = new File(filepath);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWritter = new FileWriter(file.getAbsoluteFile());
        fileWritter.write(data);
        fileWritter.close();

    }
}
