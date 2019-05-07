package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_operation_unit.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_operation_unit") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_operation_unit_s', startValue:"1")
        }
        createTable(tableName: "hpfm_operation_unit", remarks: "业务实体") {
            column(name: "ou_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "ou_code", type: "varchar(" + 30 * weight + ")",  remarks: "平台编码")  {constraints(nullable:"false")}  
            column(name: "ou_name", type: "varchar(" + 90 * weight + ")",  remarks: "业务实体名称")  {constraints(nullable:"false")}  
            column(name: "company_id", type: "bigint(20)",  remarks: "公司,取hpfm_company.company_id")   
            column(name: "source_code", type: "varchar(" + 30 * weight + ")",  remarks: "来源,值集：HPFM.DATA_SOURCE")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID,hpfm_tenant.tenant_id")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标识")  {constraints(nullable:"false")}  
            column(name: "external_system_code", type: "varchar(" + 30 * weight + ")",  remarks: "外部来源系统代码")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }
   createIndex(tableName: "hpfm_operation_unit", indexName: "hpfm_operation_unit_n1") {
            column(name: "ou_code")
        }

    }
}