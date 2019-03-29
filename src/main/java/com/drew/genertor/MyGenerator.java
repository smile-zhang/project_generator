package com.drew.genertor;

import com.jfinal.plugin.activerecord.generator.BaseModelGenerator;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.ModelGenerator;
import com.jfinal.plugin.activerecord.generator.TableMeta;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author zhangTao
 * @Date:2019/3/27 17:11
 */
public class MyGenerator extends Generator {

    protected MyBeanGenerator beanGenerator;
    protected MyDaoGenerator myModelGenerator;
//---------------------------------------------- 构造器 及属性赋值 开始---------------------------------------------------

    public MyGenerator(DataSource dataSource, String baseModelPackageName, String baseModelOutputDir,
                       String modelPackageName, String modelOutputDir, String modelDaoPackageName, String modelDaoOutputDir,
                       String modelBeanPackageName, String modelBeanOutputDir) {
        //调用父类构造器，不影响原有功能，还能生成 Model 与 BaseModel。
        super(dataSource, new BaseModelGenerator(baseModelPackageName, baseModelOutputDir),
                new ModelGenerator(modelPackageName, baseModelPackageName, modelOutputDir));
        //调用 MyModelGenerator 的构造器，为了创建 ModelDao 类的生成器。
        myModelGenerator = new MyDaoGenerator(modelDaoPackageName, modelDaoOutputDir, baseModelPackageName, modelPackageName);
        //调用 MyModelBeanGenerator 的构造器，为了创建 ModelBean 类的生成器。
        beanGenerator = new MyBeanGenerator(modelBeanPackageName, modelBeanOutputDir, modelPackageName);
        // 指定自己的MetaBuidler，为了能够指定BaseModel的生成类名称，由原先的BaseModel.java -->ModelBean.java
    }
//----------------------------------------------  构造器 及属性赋值 结束---------------------------------------------------


//----------------------------------------------  指定自定义模板 开始---------------------------------------------------

    //指定 ModelDao 的模板。
    public void setModelDaoTemplate(String modelTemplate) {
        if (myModelGenerator != null) {
            myModelGenerator.setTemplate(modelTemplate);
        }
    }

    //指定 ModelBean 的模板。
    public void setModelBeanTemplate(String modelTemplate) {
        if (beanGenerator != null) {
            beanGenerator.setTemplate(modelTemplate);
        }
    }
//----------------------------------------------  指定自定义模板 结束---------------------------------------------------


//----------------------------------------------  构建自定义对象 开始 ----------------------------------------------------

    public void generate() {
        // -------------------------------------  完成原有功能，--------------------------------------
        super.generate();
        // -------------------------------------  开始构建 model Dao --------------------------------
        List<TableMeta> tableMetas = metaBuilder.build();
        if (tableMetas.size() == 0) {
            System.out.println("TableMeta 数量为 0，不生成任何 ModelDao文件");
            return;
        }
        long beanGenStart = System.currentTimeMillis();
        myModelGenerator.generate(tableMetas);
        long beanGenUsedTime = (System.currentTimeMillis() - beanGenStart) / 1000;
        System.out.println("Generate model dao complete in " + beanGenUsedTime + " seconds.");

        //------------------------------------- 开始构建 modelBean ------------------------------------------------------
        List<TableMeta> beanTableMetas = metaBuilder.build();
        if (beanTableMetas.size() == 0) {
            System.out.println("TableMeta 数量为 0，不生成任何 ModelDao文件");
            return;
        }
        long start = System.currentTimeMillis();
        beanGenerator.generate(beanTableMetas);
        long usedTime = (System.currentTimeMillis() - start) / 1000;
        System.out.println("Generate model dao complete in " + usedTime + " seconds.");
    }
//----------------------------------------------  构建自定义对象 结束-----------------------------------------------------
}
