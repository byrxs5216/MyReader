package com.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {
    //辅助文件生成的相对路径
    public static final String GENERATE_PATH = "../LifeHelper/app/src/main/java-gen";
    //辅助文件的包名
    public static final String PACKAGE_NAME = "com.bupt.greendao";
    //数据库的版本号
    public static final int DATA_VERSION_CODE = 1;

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(DATA_VERSION_CODE, PACKAGE_NAME);
        addProperties(schema, "TableNews");
        addProperties(schema, "TableAnimaJokes");
        addProperties(schema, "TablePicJokes");
        addProperties(schema, "TableTextJokes");
        addProperties(schema, "TableBeautyPics");
        //生成Dao文件路径
        DaoGenerator generator = new DaoGenerator();
        generator.generateAll(schema, GENERATE_PATH);
    }

    /**
     * 添加表属性
     * @param schema
     * @param tableName
     */
    private static void addProperties(Schema schema, String tableName) {

        Entity entity = schema.addEntity(tableName);

        //主键id自增长
        entity.addIdProperty().primaryKey().autoincrement();
        //请求结果
        entity.addStringProperty("result");
        //当前页
        entity.addIntProperty("page");
        //插入时间
        entity.addLongProperty("time");

    }
}
