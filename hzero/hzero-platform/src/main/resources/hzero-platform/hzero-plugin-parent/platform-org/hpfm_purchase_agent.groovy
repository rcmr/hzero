package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_purchase_agent.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_purchase_agent") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_purchase_agent_s', startValue:"1")
        }
        createTable(tableName: "hpfm_purchase_agent", remarks: "采购员") {
            column(name: "purchase_agent_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID，hpfm_tenant.tenant_id")  {constraints(nullable:"false")}  
            column(name: "purchase_org_id", type: "bigint(20)",  remarks: "采购组织ID，hpfm_purchase_organization.purchase_org_id")   
            column(name: "purchase_agent_code", type: "varchar(" + 30 * weight + ")",  remarks: "平台编码")  {constraints(nullable:"false")}  
            column(name: "purchase_agent_name", type: "varchar(" + 30 * weight + ")",  remarks: "采购员名称")  {constraints(nullable:"false")}  
            column(name: "contact_info", type: "varchar(" + 240 * weight + ")",  remarks: "联系方式")   
            column(name: "user_id", type: "bigint(20)",  remarks: "指定用户,iam_user.user_id")   
            column(name: "source_code", type: "varchar(" + 30 * weight + ")",  remarks: "数据来源 值集：HPFM.DATA_SOURCE")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用。1启用，0未启用")  {constraints(nullable:"false")}  
            column(name: "external_system_code", type: "varchar(" + 30 * weight + ")",  remarks: "外部来源系统代码")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }
   createIndex(tableName: "hpfm_purchase_agent", indexName: "hpfm_purchase_agent_n1") {
            column(name: "purchase_org_id")
            column(name: "purchase_agent_code")
            column(name: "tenant_id")
        }

    }

    changeSet(id: "2019-03-11-hpfm_purchase_agent", author: "zhiying.dong@hand-china.com") {
        dropColumn(tableName: "hpfm_purchase_agent", columnName: "user_id")
    }
}