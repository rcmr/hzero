package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_pur_agent_es.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_pur_agent_es") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_pur_agent_es_s', startValue:"1")
        }
        createTable(tableName: "hpfm_pur_agent_es", remarks: "采购员关联表") {
            column(name: "pur_agent_es_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "external_system_code", type: "varchar(" + 30 * weight + ")",  remarks: "外部系统代码")  {constraints(nullable:"false")}  
            column(name: "pur_agent_id", type: "bigint(20)",  remarks: "hzero平台采购员id")  {constraints(nullable:"false")}  
            column(name: "es_pur_org_id", type: "varchar(" + 50 * weight + ")",  remarks: "erp采购组织id")   
            column(name: "es_pur_org_code", type: "varchar(" + 30 * weight + ")",  remarks: "erp采购组织代码")   
            column(name: "es_pur_agent_id", type: "varchar(" + 50 * weight + ")",  remarks: "erp采购员id")   
            column(name: "es_pur_agent_code", type: "varchar(" + 30 * weight + ")",  remarks: "erp采购员代码")   
            column(name: "data_version", type: "bigint(20)",  remarks: "erp采购员版本号")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"external_system_code,pur_agent_id",tableName:"hpfm_pur_agent_es",constraintName: "hpfm_pur_agent_es_u1")
        addUniqueConstraint(columnNames:"external_system_code,es_pur_org_id,es_pur_org_code,es_pur_agent_id,es_pur_agent_code",tableName:"hpfm_pur_agent_es",constraintName: "hpfm_pur_agent_es_u2")
    }
}