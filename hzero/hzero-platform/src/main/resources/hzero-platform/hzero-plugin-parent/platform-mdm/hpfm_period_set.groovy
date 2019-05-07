package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_period_set.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_period_set") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_period_set_s', startValue:"1")
        }
        createTable(tableName: "hpfm_period_set", remarks: "会计期定义") {
            column(name: "period_set_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "period_set_code", type: "varchar(" + 30 * weight + ")",  remarks: "会计期代码")  {constraints(nullable:"false")}  
            column(name: "period_set_name", type: "varchar(" + 240 * weight + ")",  remarks: "会计期名称")  {constraints(nullable:"false")}  
            column(name: "period_total_count", type: "int(11)",  remarks: "期间总数")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",  remarks: "启用标识")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"period_set_code",tableName:"hpfm_period_set",constraintName: "hpfm_period_set_u1")
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_period_set"){
        addColumn(tableName: 'hpfm_period_set') {
            column(name: "tenant_id", type: "bigint(20)", defaultValue: "0", remarks: "租户ID,hpfm_tenant.tenant_id") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_period_set_1"){
        dropUniqueConstraint(tableName: "hpfm_period_set",constraintName: "hpfm_period_set_u1")
        addUniqueConstraint(columnNames:"period_set_code,tenant_id",tableName:"hpfm_period_set",constraintName: "hpfm_period_set_u1")
    }
}