package com.drew.generator;

import com.drew.generator.common.FileUtils;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;


/**
 * 在数据库表有任何变动时，运行一下 main 方法，极速响应变化进行代码重构
 */
public class GeneratorMain {

    public static DataSource getDataSource() {
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"), PropKit.get("dbcDriver"));
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

    public static void main(String[] args) {
//---------------------------------------------- 参数获取 开始---------------------------------------------------
        PropKit.use("maven_generate.properties");
        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = PropKit.get("modelPackageName");
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = PathKit.getWebRootPath() + PropKit.get("modelOutputDir");

        // baseModel 所使用的包名
        String baseModelPackageName = PropKit.get("baseModelPackageName");
        // baseModel 文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath() + PropKit.get("baseModelOutputDir");

        // model dao 所使用的包名 (MappingKit 默认使用的包名)
        String modeldaoPackageName = PropKit.get("modelDaoPackageName");
        // model dao 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelDaoOutputDir = PathKit.getWebRootPath() + PropKit.get("modelDaoOutputDir");

        // model bean 所使用的包名 (MappingKit 默认使用的包名)
        String modelBeanPackageName = PropKit.get("modelBeanPackageName");
        // model baan 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelbeanOutputDir = PathKit.getWebRootPath() + PropKit.get("modelbeanOutputDir");

//---------------------------------------------- 参数获取 结束---------------------------------------------------


//---------------------------------------------- 属性设置 开始---------------------------------------------------

        // 创建生成器
        MyGenerator gernerator = new MyGenerator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName,
                modelOutputDir, modeldaoPackageName, modelDaoOutputDir, modelBeanPackageName, modelbeanOutputDir);
        // 添加不需要生成的表名
        gernerator.addExcludedTable("adv");
        // 设置是否在 Model 中生成 dao 对象
        gernerator.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        gernerator.setGenerateDataDictionary(false);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        gernerator.setRemovedTableNamePrefixes("t_");
        //设置数据库方言
//        gernerator.setDialect(new MysqlDialect());
        gernerator.setDialect(new OracleDialect());
        //设置用于生成 Model 的模板文件，模板引擎将在 class path 与 jar 包内寻找模板文件
        gernerator.setModelTemplate(PropKit.get("modelTemplate"));
        gernerator.setBaseModelTemplate(PropKit.get("baseModelTemplate"));
        gernerator.setModelDaoTemplate(PropKit.get("modelDaoTemplate"));
        gernerator.setModelBeanTemplate(PropKit.get("modelBeanTemplate"));
//---------------------------------------------- 属性设置 结束---------------------------------------------------


//---------------------------------------------- 开始 生成---------------------------------------------------
        gernerator.generate();

//---------------------------------------------- 删除 不需要的文件---------------------------------------------------
        FileUtils.delAllFiles(baseModelOutputDir);

    }
}





