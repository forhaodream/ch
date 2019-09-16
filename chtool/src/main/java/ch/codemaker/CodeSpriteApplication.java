package ch.codemaker;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by CH
 * on 2019/9/16 0016 15:21
 */
public class CodeSpriteApplication {
    public String type = "Activity";

    private String fileName;

    private String packageName;

    private String moudleName = "main";

    private String projectPath;

    /**
     * 生成Activity
     */
    public static final String FILE_TYPE_ACTIVITY = "Activity";
    /**
     * 生成Fragment
     */
    public static final String FILE_TYPE_FRAGMENT = "Fragment";

    private static final String PROJECT_NAME = "CodeSprite";
    private static final String TEMPLATE_PATH = "template";

    private static final String ACTIVITY_FILE = "activity.tl";
    private static final String FRAGMENT_FILE = "fragment.tl";
    private static final String XML_FILE = "xml.tl";
    private static final String VIEW_FILE = "view.tl";
    private static final String PERSENTER_FILE = "presenter.tl";

    /**
     * 构造方法
     *
     * @param fileName    设置filename名称
     * @param packageName 包名 例如 com.avicsafety.grid_examine
     * @param moudleName  模块名 例如 examine 不设置则为默认模块
     * @param projectPath 安卓工程所在路径 例如 D:\AndroidProject\SyntApp\GridExamine
     */
    public CodeSpriteApplication(String fileName, String packageName, String moudleName, String projectPath) {
        this.fileName = fileName;
        this.packageName = packageName;
        this.moudleName = moudleName;
        this.projectPath = projectPath;
    }

    /**
     * 生成文件
     */
    public void make() throws Exception {
        validate();
        if (type.equals(FILE_TYPE_ACTIVITY)) {
            makeActivity();
            addManifestXml();
        } else if (type.equals(FILE_TYPE_FRAGMENT)) {
            makeFragment();
        }

        makeXml();
        makeView();
        makePersenter();
    }


    private void validate() throws Exception {
        if (fileName == null || packageName == null || moudleName == null || moudleName == null) {
            throw new Exception("validate error!");
        }
    }

    /**
     * 生成Activity
     **/
    private void makeActivity() {
        String code = loadTeamplateToString(ACTIVITY_FILE);
        code = replaceText(code);
        String filepath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + packageName.replace(".", File.separator) + File.separator + moudleName + File.separator + "activity";
        String filename = fileName + "Activity.java";
        output(filepath, filename, code);
    }

    /**
     * 生成Fragment
     **/
    private void makeFragment() {
        String code = loadTeamplateToString(FRAGMENT_FILE);
        code = replaceText(code);
        String filepath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + packageName.replace(".", File.separator) + File.separator + moudleName + File.separator + "fragment";
        String filename = fileName + "Fragment.java";
        output(filepath, filename, code);
    }

    /**
     * 生成XML
     **/
    private void makeXml() {
        String code = loadTeamplateToString(XML_FILE);
        code = replaceText(code);
        String filepath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "res" + File.separator + "layout";
        String filename = moudleName.toLowerCase() + "_activity_" + fileName.toLowerCase() + ".xml";
        if (type.equals(FILE_TYPE_FRAGMENT)) {
            filename = moudleName.toLowerCase() + "_fragment_" + fileName.toLowerCase() + ".xml";
        }
        output(filepath, filename, code);
    }

    /**
     * 生成view
     */
    private void makeView() {
        String code = loadTeamplateToString(VIEW_FILE);
        code = replaceText(code);
        String filepath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + packageName.replace(".", File.separator) + File.separator + moudleName + File.separator + "view";
        String filename = fileName + "View.java";
        output(filepath, filename, code);
    }

    /**
     * 生成persenter
     */
    private void makePersenter() {
        String code = loadTeamplateToString(PERSENTER_FILE);
        code = replaceText(code);
        String filepath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + packageName.replace(".", File.separator) + File.separator + moudleName + File.separator + "presenter";
        String filename = fileName + "Presenter.java";
        output(filepath, filename, code);
    }

    /**
     * 插入到xml中
     */
    private void addManifestXml() throws ParserConfigurationException, SAXException, IOException {
        String filepath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "AndroidManifest.xml";
        File file = new File(filepath);
        if (!file.exists()) {
            System.out.println("error, AndroidManifest.xml file not exists :" + filepath);
            return;
        }
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(filepath);
        NodeList list = document.getElementsByTagName("activity");

        String currentActivity = "." + moudleName + ".activity." + fileName + "Activity";

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            String value = node.getAttributes().getNamedItem("android:name").getNodeValue();
            if (value.equals(currentActivity)) {
                System.out.println("error, The activity file already exists in AndroidManifest.xml.");
                return;
            }
        }


        try {
            //没有则插入
            Element element = document.createElement("activity");
            element.setAttribute("android:name", currentActivity);
            element.setAttribute("android:screenOrientation", "portrait");
            Node node = document.getElementsByTagName("application").item(0);
            node.appendChild(element);
            TransformerFactory formFactory = TransformerFactory.newInstance();
            Transformer former = formFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            former.transform(domSource, new StreamResult(filepath));
        } catch (TransformerException e) {
            e.printStackTrace();
            System.out.println("error, " + e.getMessage());
        }

    }


    /**
     * 输出文件
     */
    private void output(String filepath, String filename, String code) {
        String path = filepath + File.separator + filename;
        File dir = new File(filepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path);
        if (file.exists()) {
            System.out.println("error, file already exists :" + file.getAbsolutePath());
            return;
        }
        try {
            Utils.writeFile(path, code);
            System.out.println("successful, file path is:" + path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error, IOException: " + e.getMessage());
        }
    }

    private String replaceText(String code) {
        code = code.replace("<%PACKAGE_NAME%>", packageName);
        code = code.replace("<%MOUDLE_NAME%>", moudleName);
        code = code.replace("<%MOUDLE_NAME_LOWERCASE%>", moudleName.toLowerCase());
        code = code.replace("<%FILE_NAME%>", fileName);
        code = code.replace("<%FILE_NAME_LOWERCASE%>", fileName.toLowerCase());
        return code;
    }

    private String loadTeamplateToString(String filePath) {
        File directory = new File(PROJECT_NAME);
        String templateFile = directory.getAbsolutePath() + File.separator + TEMPLATE_PATH + File.separator + filePath;
        String code = Utils.readFileByChars(templateFile);
        return code;
    }

    /**
     * 设置file名称
     * 如果想建立名称为 CompanyListActivity 输入 CompanyList 即可
     *
     * @param fileName
     */
    public void setActivityName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 包名 例如 com.avicsafety.grid_examine
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * 模块名 例如 examine 不设置则为默认模块
     */
    public void setMoudleName(String moudleName) {
        this.moudleName = moudleName;
    }

    /**
     * 安卓工程所在路径 例如 D:\AndroidProject\SyntApp\GridExamine
     */
    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}

