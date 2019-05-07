package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_calendar_holiday.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_calendar_holiday") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_calendar_holiday_s', startValue:"1")
        }
        createTable(tableName: "hpfm_calendar_holiday", remarks: "日历假期表") {
            column(name: "holiday_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "calendar_id", type: "bigint(20)",  remarks: "日历表ID")  {constraints(nullable:"false")}  
            column(name: "holiday_type", type: "varchar(" + 30 * weight + ")",  remarks: "公共假期类型,HMDM.HOLIDAY_TYPE")  {constraints(nullable:"false")}  
            column(name: "holiday_name", type: "varchar(" + 30 * weight + ")",  remarks: "公共假期名称")  {constraints(nullable:"false")}  
            column(name: "start_date", type: "date",  remarks: "有效期从")  {constraints(nullable:"false")}  
            column(name: "end_date", type: "date",  remarks: "有效期至")  {constraints(nullable:"false")}  
            column(name: "key_date", type: "date",  remarks: "假期具体日期")   
            column(name: "remark", type: "longtext",  remarks: "备注说明")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }
   createIndex(tableName: "hpfm_calendar_holiday", indexName: "hpfm_calendar_holiday_n1") {
            column(name: "calendar_id")
        }

    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_calendar_holiday"){
        addColumn(tableName: 'hpfm_calendar_holiday') {
            column(name: "tenant_id", type: "bigint(20)", defaultValue: "0", remarks: "租户ID,hpfm_tenant.tenant_id") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_calendar_holiday_1"){
        dropIndex(tableName: "hpfm_calendar_holiday",indexName: "hpfm_calendar_holiday_n1")
        createIndex(tableName: "hpfm_calendar_holiday", indexName: "hpfm_calendar_holiday_n1") {
            column(name: "calendar_id")
            column(name: "tenant_id")
        }
    }
}