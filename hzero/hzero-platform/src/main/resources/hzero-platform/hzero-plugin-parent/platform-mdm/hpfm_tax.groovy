package script.db

databaseChangeLog(logicalFilePath: 'script/db/hpfm_tax.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-03-01-hpfm_tax") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hpfm_tax_s', startValue:"1")
        }
        createTable(tableName: "hpfm_tax", remarks: "税率定义") {
            column(name: "tax_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "tax_code", type: "varchar(" + 30 * weight + ")",  remarks: "税种代码")  {constraints(nullable:"false")}
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "描述")  {constraints(nullable:"false")}  
            column(name: "tax_rate", type: "decimal(10,2)",  remarks: "税率")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(1)",  remarks: "启用标识")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"tax_code",tableName:"hpfm_tax",constraintName: "hpfm_tax_u1")
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_tax"){
        addColumn(tableName: 'hpfm_tax') {
            column(name: "tenant_id", type: "bigint(20)", defaultValue: "0", remarks: "租户ID,hpfm_tenant.tenant_id") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "zhiying.dong@hand-china.com", id: "2019-04-03-hpfm_tax_1"){
        dropUniqueConstraint(tableName: "hpfm_tax",constraintName: "hpfm_tax_u1")
        addUniqueConstraint(columnNames:"tax_code,tenant_id",tableName:"hpfm_tax",constraintName: "hpfm_tax_u1")
    }
}