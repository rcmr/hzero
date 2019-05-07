package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_uom.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_uom") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_uom_s', startValue:"1")
        }
        createTable(tableName: "hpfm_uom", remarks: "计量单位定义") {
            column(name: "uom_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "uom_code", type: "varchar(" + 30 * weight + ")",  remarks: "计量单位代码")  {constraints(nullable:"false")}  
            column(name: "uom_name", type: "varchar(" + 60 * weight + ")",  remarks: "计量单位名称")  {constraints(nullable:"false")}  
            column(name: "uom_type_code", type: "varchar(" + 30 * weight + ")",  remarks: "计量单位类型代码，hmdm_uom_type.uom_type_code")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标识")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"uom_code",tableName:"hpfm_uom",constraintName: "hpfm_uom_u1")
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_uom"){
        addColumn(tableName: 'hpfm_uom') {
            column(name: "tenant_id", type: "bigint(20)", defaultValue: "0", remarks: "租户ID,hpfm_tenant.tenant_id") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_uom_1"){
        dropUniqueConstraint(tableName: "hpfm_uom",constraintName: "hpfm_uom_u1")
        addUniqueConstraint(columnNames:"uom_code,tenant_id",tableName:"hpfm_uom",constraintName: "hpfm_uom_u1")
    }
}