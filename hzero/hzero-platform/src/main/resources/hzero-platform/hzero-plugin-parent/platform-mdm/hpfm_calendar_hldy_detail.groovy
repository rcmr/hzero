package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_calendar_hldy_detail.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_calendar_hldy_detail") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_calendar_hldy_detail_s', startValue:"1")
        }
        createTable(tableName: "hpfm_calendar_hldy_detail", remarks: "日历假期明细表") {
            column(name: "holiday_detail_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "calendar_id", type: "bigint(20)",  remarks: "日历表ID")  {constraints(nullable:"false")}  
            column(name: "holiday_id", type: "bigint(20)",  remarks: "日历假期ID")  {constraints(nullable:"false")}  
            column(name: "holiday_date", type: "date",  remarks: "日期")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"calendar_id,holiday_date",tableName:"hpfm_calendar_hldy_detail",constraintName: "hpfm_calendar_hldy_detail_u1")
    }
}