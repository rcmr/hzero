package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_inventory_es.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_inventory_es") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_inventory_es_s', startValue:"1")
        }
        createTable(tableName: "hpfm_inventory_es", remarks: "库存关联表") {
            column(name: "inventory_es_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "external_system_code", type: "varchar(" + 30 * weight + ")",  remarks: "外部系统编码")  {constraints(nullable:"false")}  
            column(name: "inventory_id", type: "bigint(20)",  remarks: "hzero平台库存id")  {constraints(nullable:"false")}  
            column(name: "es_organization_id", type: "varchar(" + 50 * weight + ")",  remarks: "erp平台库存组织id")   
            column(name: "es_organization_code", type: "varchar(" + 30 * weight + ")",  remarks: "erp平台库存组织代码")   
            column(name: "es_ou_id", type: "varchar(" + 50 * weight + ")",  remarks: "erp平台业务实体id")   
            column(name: "es_ou_code", type: "varchar(" + 30 * weight + ")",  remarks: "erp平台业务实体代码")   
            column(name: "es_inventory_id", type: "varchar(" + 50 * weight + ")",  remarks: "erp平台库存id")   
            column(name: "es_inventory_code", type: "varchar(" + 30 * weight + ")",  remarks: "erp平台库存代码")   
            column(name: "data_version", type: "bigint(20)",  remarks: "erp数据版本号")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"external_system_code,inventory_id",tableName:"hpfm_inventory_es",constraintName: "hpfm_inventory_es_u1")
        addUniqueConstraint(columnNames:"external_system_code,es_organization_id,es_organization_code,es_ou_id,es_ou_code,es_inventory_id,es_inventory_code",tableName:"hpfm_inventory_es",constraintName: "hpfm_inventory_es_u2")
    }
}