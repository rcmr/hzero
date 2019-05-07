package script.db

databaseChangeLog(logicalFilePath: 'script/db/hsgp_service_route.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-02-hsgp_service_route") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hsgp_service_route_s', startValue: "1")
        }
        createTable(tableName: "hsgp_service_route", remarks: "服务路由配置") {
            column(name: "service_route_id", type: "bigint(20)", autoIncrement: true, remarks: "表ID，主键") {
                constraints(primaryKey: true)
            }
            column(name: "product_id", type: "bigint(20)", defaultValue: "0", remarks: "产品ID") {
                constraints(nullable: "false")
            }
            column(name: "product_env_id", type: "bigint(20)", defaultValue: "0", remarks: "产品环境ID") {
                constraints(nullable: "false")
            }
            column(name: "service_id", type: "bigint(20)", remarks: "服务ID") { constraints(nullable: "false") }
            column(name: "service_code", type: "varchar(" + 30 * weight + ")", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "name", type: "varchar(" + 60 * weight + ")", remarks: "服务id，zuulRoute的标识，对应zuulRoute的id字段") {
                constraints(nullable: "false")
            }
            column(name: "path", type: "varchar(" + 120 * weight + ")", remarks: "服务路径") {
                constraints(nullable: "false")
            }
            column(name: "url", type: "varchar(" + 240 * weight + ")", remarks: "物理路径")
            column(name: "strip_prefix", type: "tinyint(1)", defaultValue: "1", remarks: "是否去前缀")
            column(name: "retryable", type: "tinyint(1)", defaultValue: "0", remarks: "是否支持路由重试")
            column(name: "custom_sensitive_headers", type: "tinyint(1)", defaultValue: "0", remarks: "是否自定义敏感头")
            column(name: "sensitive_headers", type: "varchar(" + 240 * weight + ")", remarks: "敏感头部列表")
            column(name: "helper_service", type: "varchar(" + 30 * weight + ")", remarks: "配置经过的gateway heler服务名")
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
        addUniqueConstraint(columnNames: "product_id,product_env_id,service_code", tableName: "hsgp_service_route", constraintName: "hsgp_service_route_u1")
    }
}