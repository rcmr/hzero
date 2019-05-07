package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_calendar.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_calendar") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_calendar_s', startValue:"1")
        }
        createTable(tableName: "hpfm_calendar", remarks: "日历表") {
            column(name: "calendar_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "calendar_name", type: "varchar(" + 150 * weight + ")",  remarks: "平台级日历名称")  {constraints(nullable:"false")}  
            column(name: "country_id", type: "bigint(20)",  remarks: "国家ID,hpfm_country.country_id")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",  remarks: "启用标识")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"country_id",tableName:"hpfm_calendar",constraintName: "hpfm_calendar_u1")
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_calendar"){
        addColumn(tableName: 'hpfm_calendar') {
            column(name: "tenant_id", type: "bigint(20)", defaultValue: "0", remarks: "租户ID,hpfm_tenant.tenant_id") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_calendar_1"){
        dropUniqueConstraint(tableName: "hpfm_calendar",constraintName: "hpfm_calendar_u1")
        addUniqueConstraint(columnNames:"country_id,tenant_id",tableName:"hpfm_calendar",constraintName: "hpfm_calendar_u1")
    }
}