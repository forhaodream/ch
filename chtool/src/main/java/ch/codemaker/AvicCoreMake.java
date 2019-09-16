package ch.codemaker;

import java.io.File;

/**
 * Created by CH
 * on 2019/9/16 0016 15:19
 * 用于自动生成MVP模式, Model,View,Presenter类
 */
public class AvicCoreMake {
    public static void main(String[] args) throws Exception {


        String moudleName = "license";  //模块名
        String fileName = "LicenseDetail"; //Activity名称 若名称为XxxxxActivity.java 输入Xxxx 即可
        String type = CodeSpriteApplication.FILE_TYPE_ACTIVITY;
        //String type = CodeSpriteApplication.FILE_TYPE_FRAGMENT;
        String packageName = "com.avicsafety.law_enforce"; //核心模块
        String outPath = System.getProperty("user.dir") + File.separator + "LawEnforce";  //输出路径
        CodeSpriteApplication application = new CodeSpriteApplication(fileName, packageName, moudleName, outPath);
        application.type = type;
        application.make();
    }
}
