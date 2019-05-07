package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_region.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_region") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_region_s', startValue:"1")
        }
        createTable(tableName: "hpfm_region", remarks: "地区定义") {
            column(name: "region_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表id，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "country_id", type: "bigint(20)",  remarks: "国家id")  {constraints(nullable:"false")}  
            column(name: "region_code", type: "varchar(" + 30 * weight + ")",  remarks: "区域编码")  {constraints(nullable:"false")}  
            column(name: "region_name", type: "varchar(" + 120 * weight + ")",  remarks: "区域名称")  {constraints(nullable:"false")}  
            column(name: "quick_index", type: "varchar(" + 30 * weight + ")",  remarks: "快速检索")  {constraints(nullable:"false")}  
            column(name: "parent_region_id", type: "bigint(20)",  remarks: "父区域id")   
            column(name: "level_path", type: "varchar(" + 480 * weight + ")",  remarks: "等级路径")  {constraints(nullable:"false")}  
            column(name: "level_number", type: "int(11)",  remarks: "层级深度")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用标记")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "记录创建人")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "记录创建日期")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "记录更新人")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "记录更新日期")  {constraints(nullable:"false")}  

        }
   createIndex(tableName: "hpfm_region", indexName: "hpfm_region_n1") {
            column(name: "country_id")
        }
   createIndex(tableName: "hpfm_region", indexName: "hpfm_region_n2") {
            column(name: "region_code")
        }
   createIndex(tableName: "hpfm_region", indexName: "hpfm_region_n3") {
            column(name: "region_code")
            column(name: "country_id")
        }
   createIndex(tableName: "hpfm_region", indexName: "hpfm_region_n4") {
            column(name: "quick_index")
        }

    }
    changeSet(id: '2019-03-14-hpfm_region', author: 'zhiying.dong@hand-china.com') {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        dropNotNullConstraint(tableName: 'hpfm_region', columnName: 'quick_index', columnDataType: "varchar(" + 30 * weight + ")")
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_region"){
        addColumn(tableName: 'hpfm_region') {
            column(name: "tenant_id", type: "bigint(20)", defaultValue: "0", remarks: "租户ID,hpfm_tenant.tenant_id") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_region_1"){
        dropIndex(tableName: "hpfm_region",indexName: "hpfm_region_n1")
        dropIndex(tableName: "hpfm_region",indexName: "hpfm_region_n2")
        dropIndex(tableName: "hpfm_region",indexName: "hpfm_region_n3")
        dropIndex(tableName: "hpfm_region",indexName: "hpfm_region_n4")
        createIndex(tableName: "hpfm_region", indexName: "hpfm_region_n1") {
            column(name: "country_id")
            column(name: "tenant_id")
        }
        createIndex(tableName: "hpfm_region", indexName: "hpfm_region_n2") {
            column(name: "region_code")
            column(name: "tenant_id")
        }
        createIndex(tableName: "hpfm_region", indexName: "hpfm_region_n3") {
            column(name: "region_code")
            column(name: "country_id")
            column(name: "tenant_id")
        }
        createIndex(tableName: "hpfm_region", indexName: "hpfm_region_n4") {
            column(name: "quick_index")
            column(name: "tenant_id")
        }
    }
}