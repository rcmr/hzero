package script.db

databaseChangeLog(logicalFilePath: 'script/db/hsgp_service.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-02-hsgp_service") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hsgp_service_s', startValue: "1")
        }
        createTable(tableName: "hsgp_service", remarks: "服务") {
            column(name: "service_id", type: "bigint(20)", autoIncrement: true, remarks: "表ID，主键") {
                constraints(primaryKey: true)
            }
            column(name: "service_code", type: "varchar(" + 30 * weight + ")", remarks: "服务编码") {
                constraints(nullable: "false")
            }
            column(name: "service_name", type: "varchar(" + 90 * weight + ")", remarks: "服务名称") {
                constraints(nullable: "false")
            }
            column(name: "service_logo", type: "varchar(" + 255 * weight + ")", remarks: "服务图标")
            column(name: "app_source_id", type: "bigint(20)", defaultValue: "0", remarks: "应用来源ID") {
                constraints(nullable: "false")
            }
            column(name: "service_source_config", type: "varchar(" + 1000 * weight + ")", remarks: "服务来源配置")
            column(name: "object_version_number", type: "bigint(20)", defaultValue: "1", remarks: "行版本号，用来处理锁") {
                constraints(nullable: "false")
            }
            column(name: "creation_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "created_by", type: "bigint(20)", defaultValue: "-1", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "last_updated_by", type: "bigint(20)", defaultValue: "-1", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "last_update_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") {
                constraints(nullable: "false")
            }
        }
        addUniqueConstraint(columnNames: "service_code,app_source_id", tableName: "hsgp_service", constraintName: "hsgp_service_u1")
    }
}