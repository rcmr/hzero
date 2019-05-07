package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_unit.groovy') {
    def weight = 1
    if(helper.isSqlServer()){
        weight = 2
    } else if(helper.isOracle()){
        weight = 3
    }
    if(helper.dbType().isSupportSequence()){
        createSequence(sequenceName: 'hpfm_unit_s', startValue:"1")
    }
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_unit") {
        createTable(tableName: "hpfm_unit", remarks: "组织") {
            column(name: "unit_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "unit_code", type: "varchar(" + 30 * weight + ")",  remarks: "部门代码")  {constraints(nullable:"false")}  
            column(name: "unit_name", type: "varchar(" + 120 * weight + ")",  remarks: "部门名称")  {constraints(nullable:"false")}  
            column(name: "unit_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "类型,代码 HPFM.HR.UNIT_TYPE")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")   
            column(name: "order_seq", type: "int(11)",  remarks: "排序号")  {constraints(nullable:"false")}  
            column(name: "parent_unit_id", type: "bigint(20)",  remarks: "上级部门ID,hpfm_hr_unit.unit_id")   
            column(name: "unit_company_id", type: "bigint(20)",  remarks: "部门所属公司ID,hpfm_unit.unit_id")   
            column(name: "supervisor_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "是否主管部门")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标识")  {constraints(nullable:"false")}  
            column(name: "level_path", type: "varchar(" + 480 * weight + ")",  remarks: "层级路径")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "company_id", type: "bigint(20)",  remarks: "关联企业ID,hpfm_company.company_id")   

        }

        addUniqueConstraint(columnNames:"unit_code,tenant_id",tableName:"hpfm_unit",constraintName: "hpfm_unit_u1")
    }

    changeSet(author: "xiaoyu.zhao@hand-china.com", id: "2019-04-23-hpfm_unit") {
        addColumn(tableName: "hpfm_unit"){
            column(name: "quick_index", type: "varchar(" + 30 * weight + ")",  remarks: "快速检索")
        }
        addColumn(tableName: "hpfm_unit"){
            column(name: "phoneticize", type: "varchar(" + 60 * weight + ")",  remarks: "拼音")
        }
    }
}