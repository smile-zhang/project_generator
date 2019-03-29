package com.drew.genertor;

import com.jfinal.plugin.activerecord.generator.BaseModelGenerator;
import com.jfinal.plugin.activerecord.generator.TableMeta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author zhangTao
 * @Date:2019/3/28 9:51
 */
public class MyBaseModelGenerator extends BaseModelGenerator {
    protected String modelPackageName;

    public MyBaseModelGenerator(String baseModelPackageName, String baseModelOutputDir) {
        super(baseModelPackageName, baseModelOutputDir);
        this.modelPackageName = "";
    }

    @Override
    protected void writeToFile(TableMeta tableMeta) throws IOException {
        File dir = new File(baseModelOutputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String target = baseModelOutputDir + File.separator + tableMeta.baseModelName + ".java";
        FileWriter fw = new FileWriter(target);
        try {
            fw.write(tableMeta.baseModelContent);
        }
        finally {
            fw.close();
        }
    }


}
