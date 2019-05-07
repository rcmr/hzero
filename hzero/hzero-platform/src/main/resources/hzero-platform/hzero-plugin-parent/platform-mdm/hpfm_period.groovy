package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_period.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_period") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_period_s', startValue:"1")
        }
        createTable(tableName: "hpfm_period", remarks: "期间定义") {
            column(name: "period_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "order_seq", type: "int(11)",  remarks: "序号")  {constraints(nullable:"false")}  
            column(name: "period_name", type: "varchar(" + 30 * weight + ")",  remarks: "期间")  {constraints(nullable:"false")}  
            column(name: "period_set_id", type: "bigint(20)",  remarks: "会计期ID,hpfm_period_set.period_set_id")  {constraints(nullable:"false")}  
            column(name: "period_year", type: "int(11)",  remarks: "年度")  {constraints(nullable:"false")}  
            column(name: "period_quarter", type: "int(11)",  remarks: "季度")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",  remarks: "启用标识")   
            column(name: "start_date", type: "date",  remarks: "日期从")   
            column(name: "end_date", type: "date",  remarks: "日期至")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"period_set_id,period_name",tableName:"hpfm_period",constraintName: "hpfm_period_u1")
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_period"){
        addColumn(tableName: 'hpfm_period') {
            column(name: "tenant_id", type: "bigint(20)", defaultValue: "0", remarks: "租户ID,hpfm_tenant.tenant_id") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_period_1"){
        dropUniqueConstraint(tableName: "hpfm_period",constraintName: "hpfm_period_u1")
        addUniqueConstraint(columnNames:"period_set_id,period_name,tenant_id",tableName:"hpfm_period",constraintName: "hpfm_period_u1")
    }
}