package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_inventory.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_inventory") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_inventory_s', startValue:"1")
        }
        createTable(tableName: "hpfm_inventory", remarks: "库存") {
            column(name: "inventory_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "inventory_code", type: "varchar(" + 30 * weight + ")",  remarks: "库房编码")  {constraints(nullable:"false")}  
            column(name: "inventory_name", type: "varchar(" + 120 * weight + ")",  remarks: "库房名称")  {constraints(nullable:"false")}  
            column(name: "organization_id", type: "bigint(20)",  remarks: "库存组织ID,hpfm_inv_organization.organization_id")  {constraints(nullable:"false")}  
            column(name: "ou_id", type: "bigint(20)",  remarks: "业务实体ID,hpfm_operation_unit.ou_id")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID,hpfm_tenant.tenant_id")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用。1启用，0未启用")  {constraints(nullable:"false")}  
            column(name: "source_code", type: "varchar(" + 30 * weight + ")",  remarks: "数据来源 值集：HPFM.DATA_SOURCE")  {constraints(nullable:"false")}  
            column(name: "external_system_code", type: "varchar(" + 30 * weight + ")",  remarks: "外部来源系统代码")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }
   createIndex(tableName: "hpfm_inventory", indexName: "hpfm_inventory_n1") {
            column(name: "inventory_code")
            column(name: "tenant_id")
        }
   createIndex(tableName: "hpfm_inventory", indexName: "hpfm_inventory_n2") {
            column(name: "ou_id")
        }

    }
}