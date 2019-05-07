package script.db

databaseChangeLog(logicalFilePath: 'script/db/hsgp_zuul_rate_limit_line.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-02-hsgp_zuul_rate_limit_line") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hsgp_zuul_rate_limit_line_s', startValue:"1")
        }
        createTable(tableName: "hsgp_zuul_rate_limit_line", remarks: "Zuul限流设置行明细") {
            column(name: "rate_limit_line_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "rate_limit_id", type: "bigint(20)",  remarks: "Zuul限流设置ID")  {constraints(nullable:"false")}  
            column(name: "route_key", type: "varchar(" + 240 * weight + ")",  remarks: "路由，default代表默认设置")  {constraints(nullable:"false")}  
            column(name: "max_limit", type: "int(11)",  remarks: "请求数限制")  {constraints(nullable:"false")}  
            column(name: "quota", type: "int(11)",  remarks: "请求时间限制")   
            column(name: "refresh_interval", type: "int(11)",  remarks: "时间刷新窗口")   
            column(name: "rate", type: "int(11)",  remarks: "令牌产生速率")   
            column(name: "consume_rate", type: "int(11)",  remarks: "令牌消耗速率")   
            column(name: "user", type: "varchar(" + 360 * weight + ")",  remarks: "用户维度")   
            column(name: "tenant", type: "varchar(" + 360 * weight + ")",  remarks: "租户维度")   
            column(name: "origin", type: "varchar(" + 360 * weight + ")",  remarks: "源请求地址味道")   
            column(name: "url", type: "varchar(" + 360 * weight + ")",  remarks: "URL维度")   
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用。1启用，0未启用")  {constraints(nullable:"false")}  
            column(name: "remark", type: "longtext",  remarks: "备注说明")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }
   createIndex(tableName: "hsgp_zuul_rate_limit_line", indexName: "hsgp_zuul_rate_limit_line_n1") {
            column(name: "rate_limit_id")
            column(name: "route_key")
        }

    }
}