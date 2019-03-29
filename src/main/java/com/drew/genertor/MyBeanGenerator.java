package com.drew.genertor;

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
 * @Date:2019/3/28 16:52
 */
public class MyBeanGenerator {
    protected Engine engine;
    protected String template = "/com/jfinal/plugin/activerecord/generator/model_template.jf";
    protected String modelBeanPackageName;
    protected String modelPackageName;
    protected String baseModelPackageName;
    protected String modelBeanOutputDir;


    public MyBeanGenerator(String modelBeanPackageName, String modelBeanOutputDir, String modelPackageName) {
        if (StrKit.isBlank(modelBeanPackageName)) {
            throw new IllegalArgumentException("modelDaoPackageName can not be blank.");
        }
        if (modelBeanPackageName.contains("/") || modelBeanPackageName.contains("\\")) {
            throw new IllegalArgumentException("modelDaoPackageName error : " + modelBeanPackageName);
        }

        if (StrKit.isBlank(modelBeanOutputDir)) {
            throw new IllegalArgumentException("modelBeanOutputDir can not be blank.");
        }
        this.modelBeanPackageName = modelBeanPackageName;
        this.modelBeanOutputDir = modelBeanOutputDir;
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
        System.out.println("Model Bean Output Dir: " + modelBeanOutputDir);

        for (TableMeta tableMeta : tableMetas) {
            genModelContent(tableMeta);
        }
        writeToFile(tableMetas);
    }

    protected void genModelContent(TableMeta tableMeta) {
        Kv data = Kv.by("modelBeanPackageName", modelBeanPackageName);
        data.set("tableMeta", tableMeta);
        data.set("modelPackageName", modelPackageName);
        data.set("modelBeanPackageName", modelPackageName);

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
        File dir = new File(modelBeanOutputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String target = modelBeanOutputDir + File.separator + tableMeta.modelName + "Bean.java";

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
