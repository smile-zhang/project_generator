package com.drew.generator;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.jfinal.template.Engine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangTao
 * @Date:2019/3/27 17:19
 */
public class MyDaoGenerator {
    protected Engine engine;
    // 默认模板
    protected String template = "";
    protected String modelDaoOutputDir;
    protected String modelDaoPackageName;
    protected String modelPackageName;
    protected String baseModelPackageName;


    public MyDaoGenerator(String modelDaoPackageName, String modelDaoOutputDir, String baseModelPackageName, String modelPackageName) {
        if (StrKit.isBlank(modelDaoPackageName)) {
            throw new IllegalArgumentException("modelDaoPackageName can not be blank.");
        }
        if (modelDaoPackageName.contains("/") || modelDaoPackageName.contains("\\")) {
            throw new IllegalArgumentException("modelDaoPackageName error : " + modelDaoPackageName);
        }

        if (StrKit.isBlank(modelDaoOutputDir)) {
            throw new IllegalArgumentException("modelDaoOutputDir can not be blank.");
        }
        this.modelDaoPackageName = modelDaoPackageName;
        this.modelDaoOutputDir = modelDaoOutputDir;
        this.baseModelPackageName = baseModelPackageName;
        this.modelPackageName = modelPackageName;
        initEngine();

    }

    protected void initEngine() {
        engine = new Engine();
        engine.setToClassPathSourceFactory();
        engine.addSharedMethod(new StrKit());
    }

    /**
     * 使用自定义模板生成 model
     */
    public void setTemplate(String template) {
        this.template = template;
    }


    public void generate(List<TableMeta> tableMetas) {
        System.out.println("Generate model dao...");
        System.out.println("Model dao Output Dir: " + modelDaoOutputDir);

        for (TableMeta tableMeta : tableMetas) {
            genModelContent(tableMeta);
        }
        writeToFile(tableMetas);
    }

    protected void genModelContent(TableMeta tableMeta) {
        Kv data = Kv.by("modelDaoPackageName", modelDaoPackageName);
//         data.set("modelBean", modelBean);
         data.set("tableMeta", tableMeta);
         data.set("baseModelPackageName", baseModelPackageName);
         data.set("modelPackageName", modelPackageName);

        String ret = engine.getTemplate(template).renderToString(data);
        tableMeta.modelContent = ret;
    }

    protected void writeToFile(List<TableMeta> tableMetas) {
        try {
            for (TableMeta tableMeta : tableMetas) {
                writeToFile(tableMeta);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 若 model 文件存在，则不生成，以免覆盖用户手写的代码
     */
    protected void writeToFile(TableMeta tableMeta) throws IOException {
        File dir = new File(modelDaoOutputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String target = modelDaoOutputDir + File.separator + tableMeta.modelName + "Dao.java";

        File file = new File(target);
        if (file.exists()) {
            return ;	// 若 Model 存在，不覆盖
        }

        FileWriter fw = new FileWriter(file);
        try {
            fw.write(tableMeta.modelContent);
        }
        finally {
            fw.close();
        }
    }
}
