package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_inv_organization_es.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_inv_organization_es") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_inv_organization_es_s', startValue:"1")
        }
        createTable(tableName: "hpfm_inv_organization_es", remarks: "库存组织关联表") {
            column(name: "inv_org_es_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "external_system_code", type: "varchar(" + 30 * weight + ")",  remarks: "外部系统代码")  {constraints(nullable:"false")}  
            column(name: "organization_id", type: "bigint(20)",  remarks: "hzero平台库存组织id")  {constraints(nullable:"false")}  
            column(name: "es_organization_id", type: "varchar(" + 50 * weight + ")",  remarks: "erp平台库存组织id")   
            column(name: "es_organization_code", type: "varchar(" + 30 * weight + ")",  remarks: "erp平台库存组织代码")   
            column(name: "data_version", type: "bigint(20)",  remarks: "erp数据版本号")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"external_system_code,organization_id",tableName:"hpfm_inv_organization_es",constraintName: "hpfm_inv_organization_es_u1")
        addUniqueConstraint(columnNames:"external_system_code,es_organization_id,es_organization_code",tableName:"hpfm_inv_organization_es",constraintName: "hpfm_inv_organization_es_u2")
    }
}