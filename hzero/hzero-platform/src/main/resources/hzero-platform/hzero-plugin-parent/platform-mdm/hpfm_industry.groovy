package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_industry.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_industry") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_industry_s', startValue:"1")
        }
        createTable(tableName: "hpfm_industry", remarks: "行业信息") {
            column(name: "industry_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "industry_code", type: "varchar(" + 30 * weight + ")",  remarks: "行业编码")  {constraints(nullable:"false")}  
            column(name: "industry_name", type: "varchar(" + 120 * weight + ")",  remarks: "行业名称")  {constraints(nullable:"false")}  
            column(name: "parent_industry_id", type: "bigint(20)",  remarks: "父行业ID")   
            column(name: "level_path", type: "varchar(" + 480 * weight + ")",  remarks: "等级路径")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用标记")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"industry_code",tableName:"hpfm_industry",constraintName: "hpfm_industry_u1")
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_industry"){
        addColumn(tableName: 'hpfm_industry') {
            column(name: "tenant_id", type: "bigint(20)", defaultValue: "0", remarks: "租户ID,hpfm_tenant.tenant_id") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_industry_1"){
        dropUniqueConstraint(tableName: "hpfm_industry",constraintName: "hpfm_industry_u1")
        addUniqueConstraint(columnNames:"industry_code,tenant_id",tableName:"hpfm_industry",constraintName: "hpfm_industry_u1")
    }
}